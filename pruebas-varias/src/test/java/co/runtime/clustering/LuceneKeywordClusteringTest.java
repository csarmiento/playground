package co.runtime.clustering;

import co.runtime.clustering.types.Cluster;
import co.runtime.utils.ClusterUtils;
import co.runtime.utils.FileUtils;
import junit.framework.TestCase;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LuceneKeywordClusteringTest extends TestCase {
    String[] contents1 = {
            "I love Emacs, since Emacs is awesome",
            "Emacs is awesome, I love it",
            "Oh my way home. Long day ahead",
            "It's a long day, I have to admit it",
            "Good artists copy, great artists steal",
            "Something interesting, Good artists copy, great artists steal",
            "Great artists steal, Good artists copy",
            "I really think Emacs is awesome, and love it"
    };

    String[] contents = {
            "california state university fresno ca",
            "california state university hayward ca",
            "assistance for single parents",
            "california college for health sciences at independence university",
            "financial help for single parents",
            "educational grants for single parents",
            "bryan caplan",
            "government grants for single parents",
            "paine college in augusta ga",
            "grants for children of single parents",
            "troy university dothan al",
            "texas southern university houston tx",
            "student grants for single parents"
    };

    public void testDedupSmallData() throws IOException, ParseException {

        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);
        LuceneKeywordClustering lc = new LuceneKeywordClustering(contents, 0.5f, analyzer, false);

        List<String> results = lc.removeDuplicateEntries(100);

        assertTrue(results.size() < contents.length);

        for (String s : results) {
            System.out.println(s);
        }
    }

    public void testDedupSmallData1() throws IOException, ParseException {

        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);
        LuceneKeywordClustering lc = new LuceneKeywordClustering(contents1, 0.45f, analyzer, false);

        List<String> results = lc.removeDuplicateEntries(100);

        assertEquals(4, results.size());
        assertEquals(contents1[0], results.get(0));
        assertEquals(contents1[2], results.get(1));
        assertEquals(contents1[3], results.get(2));
        assertEquals(contents1[4], results.get(3));

        for (String s : results) {
            System.out.println(s);
        }
    }

    public void testDedup() throws Exception {
        String[] fileContents = FileUtils.loadFileInArray(new
                File("IV6 - RAW keywords.txt"));

        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);
        LuceneKeywordClustering lc = new LuceneKeywordClustering(fileContents, 0.5f, analyzer, true);

        List<String> results = lc.removeDuplicateEntries(200);

        for (String s : results) {
            System.out.println(s);
        }

    }

    public void testGenerateClustersSmallData() throws Exception {
        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);
        LuceneKeywordClustering lc = new LuceneKeywordClustering(contents1, 0.4f, analyzer, true);
        List<Cluster> clusters = lc.generateClusters(0, 200);

        for (Cluster c : clusters) {
            System.out.println("-------------------- Cluster ID #" + c.getId().getId() +
                    " (" + c.getId().getName() + ") - " +
                    "{" + c.getElements().size() + "} --------------------");
            for (String kw : c.getElements()) {
                System.out.println(kw);
            }
        }
    }

    public void testGenerateClusters() throws Exception {
        File inputFile = new
                File("IV6 - RAW keywords.txt");
        String[] contentList = FileUtils.loadFileInArray(inputFile);

        FileWriter fw = new FileWriter("IV6 - Clustered keywords.txt");

        // TODO look for the scoreThreshold influence
        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);
        LuceneKeywordClustering lc = new LuceneKeywordClustering(contentList, 0.45f, analyzer, false);
        List<Cluster> clusters = lc.generateClusters(0, 100);

        assertTrue(clusters.size() > 0 && clusters.size() < contentList.length);

        for (Cluster c : clusters) {
            fw.write("-------------------- Cluster ID #" + c.getId().getId() +
                    " (" + c.getId().getName() + ") - " +
                    "{" + c.getElements().size() + "} --------------------");
            fw.write(System.getProperty("line.separator"));
            for (String kw : c.getElements()) {
                fw.write(kw);
                fw.write(System.getProperty("line.separator"));
            }
        }
        fw.close();
    }

    public void testMergeClusters() throws IOException, SAXException, OpenXML4JException {
        String existingClustersExcelInputFile = "preexcisting cluster.xlsx";
        String newKeyWordsFile = "newkeywords.txt";
        Map<String, Cluster> existingClusters = ClusterUtils.loadClustersFromExcel(existingClustersExcelInputFile, 1, 300000);

        int totalElementsPrev = 0;
        for (String key : existingClusters.keySet()) {
            totalElementsPrev += existingClusters.get(key).getElements().size();
        }
        System.out.println("Groups: " + existingClusters.size() + ", for " + totalElementsPrev + " elements");
        ClusterUtils.writeClustersToExcel(existingClusters, new File("filtered_existing_clusters.xlsx"));


        StandardAnalyzer analyzer = new StandardAnalyzer(LuceneKeywordClustering.LUCENE_VERSION);

        // The threshold sent in constructor is meaningless for merge
        LuceneKeywordClustering lc = new LuceneKeywordClustering(new File(newKeyWordsFile), 0.85f, analyzer);

        // TODO look at the cluster relevance and inclussion factor expressed as percentages [0-100]
        Set<String> orphans = lc.merge(existingClusters, 75, 30);

        int totalElements = 0;
        for (String key : existingClusters.keySet()) {
            totalElements += existingClusters.get(key).getElements().size();
        }
        System.out.println("Groups: " + existingClusters.size() + ", for " + totalElementsPrev + " elements");
        System.out.println("Merged elements: " + (totalElements - totalElementsPrev));
        ClusterUtils.writeClustersToExcel(existingClusters, new File("merged_clusters.xlsx"));

        FileUtils.writeToExcel(orphans, new File("orphan_keywords.xlsx"));
    }

}