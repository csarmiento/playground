package co.runtime.clustering;


import co.runtime.clustering.types.Cluster;
import co.runtime.clustering.types.ClusterId;
import co.runtime.utils.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queries.mlt.MoreLikeThis;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Bits;
import org.apache.lucene.util.Version;

import java.io.*;
import java.util.*;

/**
 * Perform keyword clustering and merging operations using <b>Apache Lucene 4</b>
 */
public class LuceneKeywordClustering {
    public static final Version LUCENE_VERSION = Version.LUCENE_48;
    public static final int MIN_TERM_FREQ = 1;
    public static final int MIN_DOC_FREQ = 1;
    public static final String CONTENTS_FIELD_NAME = "contents";

    private static Logger logger = LogManager.getLogger();

    private final Directory directory;
    private final float scoreThreshold;
    private final Analyzer analyzer; // Represents a policy for extracting index terms from text.
    private final int contentsSize;

    /**
     * Class constructor from an array in memory containing the keywords to be grouped.<br/>
     * <br/>
     * <b>BEWARE</b>: The heap size has to be enough to store the array and a big index, since it is using
     * {@link org.apache.lucene.store.RAMDirectory}. Otherwise, could end in an {@link java.lang.OutOfMemoryError}.<br/>
     * <br/>
     * This class uses a {@link org.apache.lucene.store.RAMDirectory} because the clustering operations are temporary,
     * and is not required to persist the indexes.
     *
     * @param keywords       The keywords to be grouped
     * @param scoreThreshold Is a similarity normalization between keywords. It is an input parameter to
     *                       tell the system how similar the keywords in the clusters be, from 0 to 100%.
     * @param analyzer       Represents a policy for extracting index terms from text.
     * @param randomize      An indicator to shuffle the keywords array before do clustering operations.
     *                       In general shuffling could improve the clustering results, but it only could be
     *                       done if the keywords fits in memory.
     * @throws IOException
     * @throws ParseException
     */
    public LuceneKeywordClustering(String[] keywords, float scoreThreshold, Analyzer analyzer, boolean randomize)
            throws IOException, ParseException {
        // Store the index in memory:
        this.directory = new RAMDirectory();
        // Set score threshold
        this.scoreThreshold = scoreThreshold;
        // Set analyzer
        this.analyzer = analyzer;
        // Set keywords size
        this.contentsSize = keywords.length;

        IndexWriter iwriter = getIndexWriter();

        if (randomize) {
            ArrayUtils.shuffle(keywords);
        }

        // Add all keywords to lucene index
        for (String s : keywords) {
            Document doc = new Document();
            Field field = new Field(CONTENTS_FIELD_NAME, s, TextField.TYPE_STORED);
            doc.add(field);
            iwriter.addDocument(doc);
        }
        iwriter.close();
    }

    /**
     * Class constructor from a file containing the keywords to be grouped.<br/>
     * <br/>
     * <b>BEWARE</b>: The heap size has to be enough to store a big index, since it is using
     * {@link org.apache.lucene.store.RAMDirectory}. Otherwise, could end in an {@link java.lang.OutOfMemoryError}.<br/>
     * <br/>
     * This class uses a {@link org.apache.lucene.store.RAMDirectory} because the clustering operations are temporary,
     * and is not required to persist the indexes.
     *
     * @param keywordsFile   The file containing the keywords to be grouped
     * @param scoreThreshold Is a similarity normalization between keywords. It is an input parameter to
     *                       tell the system how similar the keywords in the clusters be, from 0 to 100%.
     * @param analyzer       Represents a policy for extracting index terms from text.
     * @throws IOException
     */
    public LuceneKeywordClustering(File keywordsFile, float scoreThreshold, Analyzer analyzer) throws IOException {
        // Store the index in memory:
        this.directory = new RAMDirectory();
        // Set score threshold
        this.scoreThreshold = scoreThreshold;
        // Set analyzer
        this.analyzer = analyzer;

        IndexWriter iwriter = getIndexWriter();

        FileReader fr = new FileReader(keywordsFile);
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        int totalLines = 0;

        // Add all contents to lucene index
        while (line != null) {
            Document doc = new Document();
            Field field = new Field(CONTENTS_FIELD_NAME, line, TextField.TYPE_STORED);
            doc.add(field);
            iwriter.addDocument(doc);
            line = br.readLine();
            totalLines++;
        }
        iwriter.close();
        br.close();
        fr.close();

        // Set contents size
        this.contentsSize = totalLines;
    }

    /**
     * Returns the <tt>IndexWriter</tt> to update the lucene index
     *
     * @return The <tt>IndexWriter</tt> to update the lucene index
     * @throws IOException
     */
    private IndexWriter getIndexWriter() throws IOException {
        IndexWriterConfig config = new IndexWriterConfig(LUCENE_VERSION, analyzer);
        return new IndexWriter(directory, config);
    }

    /**
     * Returns a list of deduplicated keywords, grouping according to the scoreThreshold and the group size.
     *
     * @param groupSize Desired group size for similar documents
     * @return A list of deduplicated keywords
     * @throws IOException
     */
    public List<String> removeDuplicateEntries(int groupSize) throws IOException {
        int totalKeywordsGrouped = 0, totalClusters = 0;
        List<String> results = new ArrayList<String>();
        IndexWriter indexWriter = getIndexWriter();
        DirectoryReader directoryReader = DirectoryReader.open(indexWriter, false);

        for (int docNum = 0; docNum < directoryReader.maxDoc(); docNum++) {
            Bits liveDocs = getLiveDocs(directoryReader);
            // this document is not deleted...
            if (liveDocs == null || liveDocs.get(docNum)) {
                // Save doc to result list
                Document doc = directoryReader.document(docNum);
                String content = doc.get(CONTENTS_FIELD_NAME);
                results.add(content);

                int similarDocs = removeSimilarDocs(groupSize, indexWriter, directoryReader, liveDocs,
                        content, scoreThreshold, true);
                totalKeywordsGrouped += similarDocs;
                totalClusters++;

                directoryReader = updateDirectoryReader(directoryReader);
            }
        }
        logger.info("Total Keywords Grouped: " + totalKeywordsGrouped);
        logger.info("Total Clusters: " + totalClusters);
        directoryReader.close();
        indexWriter.close();

        return results;
    }

    /**
     * Generate N clusters based on the <tt>maxClusterSize</tt> input parameter. Currently the <tt>minClusterSize</tt>
     * is not used, but exists for future implementation.
     *
     * @param minClusterSize Minimun size of clusters
     * @param maxClusterSize Maximum size of clusters
     * @return A <tt>Map</tt> of keyword clusters
     * @throws IOException
     */
    public List<Cluster> generateClusters(int minClusterSize, int maxClusterSize) throws IOException {
        int totalKeywordsGrouped = 0;
        List<Cluster> clusters = new ArrayList<Cluster>();
        IndexWriter indexWriter = getIndexWriter();
        DirectoryReader directoryReader = DirectoryReader.open(indexWriter, false);

        for (int docNum = 0; docNum < directoryReader.maxDoc(); docNum++) {
            Bits liveDocs = getLiveDocs(directoryReader);
            // this document is not deleted...
            if (liveDocs == null || liveDocs.get(docNum)) {
                // Save doc to result list
                String content = getDocumentContents(directoryReader, docNum);

                // retrieve the similar docs
                Set<String> similarDocs = retrieveSimilarDocs(maxClusterSize, indexWriter, directoryReader, liveDocs,
                        content, scoreThreshold, true);
                // Add to the map the cluster
                clusters.add(new Cluster(new ClusterId(docNum, content), similarDocs));
                totalKeywordsGrouped += similarDocs.size();
                // Updates the directory reader
                directoryReader = updateDirectoryReader(directoryReader);
            }
        }
        logger.info("Total Keywords Grouped: " + totalKeywordsGrouped);
        logger.info("Total Clusters: " + clusters.size());
        directoryReader.close();
        indexWriter.close();

        return clusters;
    }

    /**
     * Retrieve the document content related to the <tt>docNum</tt>
     *
     * @param directoryReader The lucene index reader
     * @param docNum          Document number
     * @return The document content
     * @throws IOException
     */
    private String getDocumentContents(DirectoryReader directoryReader, int docNum) throws IOException {
        return directoryReader.document(docNum).get(CONTENTS_FIELD_NAME);
    }

    /**
     * Retrieves all similar documents according to <tt>content</tt> input parameter and removes them fron the lucene index
     *
     * @param groupSize       Desired group size for similar documents
     * @param indexWriter     The <tt>IndexWriter</tt> to update the lucene index
     * @param directoryReader The lucene index reader
     * @param liveDocs        The Bits representing live (not deleted) docs. A set bit indicates the doc ID has not been deleted.
     * @param content         The document content
     * @param threshold       The similarity threshold, its meaning depends on the context. For clustering it could be
     *                        normalized, but for merging this threshold has a very different meaning
     * @param normalize       If the threshold must be or not normalized
     * @return A <tt>Set</tt> of documents similar to the <tt>content</tt> according to the <tt>threshold</tt>
     * @throws IOException
     */
    private Set<String> retrieveSimilarDocs(int groupSize, IndexWriter indexWriter, DirectoryReader directoryReader,
                                            Bits liveDocs, String content, double threshold, boolean normalize)
            throws IOException {
        TopDocs topDocs = getTopDocs(groupSize, directoryReader, content);
        Set<String> similarDocs = new TreeSet<String>();

        double maxScore;
        if (normalize) {
            maxScore = (Float.isNaN(topDocs.getMaxScore())) ? 1f : topDocs.getMaxScore();
        } else {
            maxScore = 1f;
        }

        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            if (scoreDoc.score / maxScore > threshold) {
                // check if not deleted
                if (liveDocs == null || liveDocs.get(scoreDoc.doc)) {
                    // Delete all similar docs
                    indexWriter.tryDeleteDocument(directoryReader, scoreDoc.doc);
                    similarDocs.add(getDocumentContents(directoryReader, scoreDoc.doc));
                }
            }
        }
        return similarDocs;
    }

    /**
     * Retrieves all similar documents according to <tt>content</tt> input parameter and removes them fron the
     * lucene index. This method is used to merge new keywords into existing clusters, represented as a
     * <tt>content</tt> string.
     *
     * @param groupSize           Desired group size for similar documents
     * @param indexWriter         The <tt>IndexWriter</tt> to update the lucene index
     * @param directoryReader     The lucene index reader
     * @param liveDocs            The Bits representing live (not deleted) docs. A set bit indicates the doc ID has
     *                            not been deleted.
     * @param content              The document content
     * @param minRelevance         Minimum relevance defined as # of hits over # of keywords in the index for the
     *                            <tt>content</tt> input parameter. Its a limiter to include similar docs where
     *                            relevance becomes higher than <tt>minRelevance</tt>
     * @param inclusionPercentage The percentage of {@link org.apache.lucene.search.TopDocs} to inlcude as similar
     *                            documents
     * @return A <tt>Set</tt> of documents similar to the <tt>content</tt> according the to the <tt>minRelevance</tt>
     * and the <tt>inclusionPercentage</tt>
     * @throws IOException
     */
    private Set<String> retrieveSimilarDocs(int groupSize, IndexWriter indexWriter, DirectoryReader directoryReader,
                                            Bits liveDocs, String content, float minRelevance, float inclusionPercentage)
            throws IOException {
        TopDocs topDocs = getTopDocs(groupSize, directoryReader, content);
        Set<String> similarDocs = new TreeSet<String>();

        float maxScore = topDocs.getMaxScore(); // maxScore couuld be NaN
        float clusterRelevance = (float) topDocs.totalHits / contentsSize * 100;

        // Cluster considered for merging if the relevance is higher than minimum expected
        if (clusterRelevance > minRelevance) {
            // Calculate threshold based in cluster relevance and maxScore...
            float asd = (float) (Math.pow(clusterRelevance / 100, 3) * inclusionPercentage / 100);
            float threshold = (1 - asd) * maxScore;
            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                if (scoreDoc.score / maxScore > threshold) {
                    // check if not deleted
                    if (liveDocs == null || liveDocs.get(scoreDoc.doc)) {
                        // Delete all similar docs
                        indexWriter.tryDeleteDocument(directoryReader, scoreDoc.doc);
                        similarDocs.add(getDocumentContents(directoryReader, scoreDoc.doc));
                    }
                }
            }
            logger.info("Relevance: " + String.format("%,.2f", clusterRelevance) +
                    "%, Max Score: " + maxScore + ", Threshold: " + threshold +
                    ", Merged Kewywords: " + similarDocs.size() +
                    ", Cluster: " + content);
        }
        return similarDocs;
    }

    /**
     * Removes from the index those documents similar to the <tt>content</tt>
     *
     * @param groupSize       Desired group size for similar documents
     * @param indexWriter     The <tt>IndexWriter</tt> to update the lucene index
     * @param directoryReader The lucene index reader
     * @param liveDocs        The Bits representing live (not deleted) docs. A set bit indicates the doc ID has not been deleted.
     * @param content         The document content
     * @param threshold       The similarity threshold, its meaning depends on the context. For clustering it could be
     *                        normalized, but for merging this threshold has a very different meaning
     * @param normalize       Flag to adjust the behavior of the method
     * @return The number of similar documents removed from the index
     * @throws IOException
     */
    private int removeSimilarDocs(int groupSize, IndexWriter indexWriter, DirectoryReader directoryReader,
                                  Bits liveDocs, String content, double threshold, boolean normalize) throws IOException {
        TopDocs topDocs = getTopDocs(groupSize, directoryReader, content);

        double maxScore;
        if (normalize) {
            maxScore = (Float.isNaN(topDocs.getMaxScore())) ? 1f : topDocs.getMaxScore();
        } else {
            maxScore = 1f;
        }

        int similarDocs = 0;
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            if (scoreDoc.score / maxScore > threshold) {
                // check if not deleted
                if (liveDocs == null || liveDocs.get(scoreDoc.doc)) {
                    // Delete all similar docs
                    indexWriter.tryDeleteDocument(directoryReader, scoreDoc.doc);
                    indexWriter.commit();
                    similarDocs++;
                }
            }
        }
        return similarDocs;
    }

    /**
     * Returns the <b>n</b> more relevant {@link org.apache.lucene.search.TopDocs} according to the <tt>limit</tt>
     * parameter
     *
     * @param limit           Number of similar documents to be returned
     * @param directoryReader The lucene index reader
     * @param content          The document content
     * @return The <b>n</b> more relevant {@link org.apache.lucene.search.TopDocs} according to the <tt>limit</tt>
     * parameter
     * @throws IOException
     */
    private TopDocs getTopDocs(int limit, DirectoryReader directoryReader, String content) throws IOException {
        MoreLikeThis moreLikeThis = prepareMoreLikeThis(directoryReader);
        IndexSearcher searcher = new IndexSearcher(directoryReader);

        // Find Similar doc
        Reader reader = new StringReader(content);
        Query query = moreLikeThis.like(reader, CONTENTS_FIELD_NAME);

        return searcher.search(query, limit);
    }

    /**
     * Prepares the similarity query
     *
     * @param directoryReader The lucene index reader
     * @return The similarity query as an <tt>MoreLikeThis</tt> instance
     */
    private MoreLikeThis prepareMoreLikeThis(DirectoryReader directoryReader) {
        MoreLikeThis moreLikeThis = new MoreLikeThis(directoryReader);
        moreLikeThis.setAnalyzer(analyzer);
        // Lower the frequency since content is short
        moreLikeThis.setMinTermFreq(MIN_TERM_FREQ);
        moreLikeThis.setMinDocFreq(MIN_DOC_FREQ);
        return moreLikeThis;
    }

    /**
     * Returns the Bits representing live (not deleted) docs. A set bit indicates the doc ID has not been deleted.
     *
     * @param directoryReader The lucene index reader
     * @return the live (not deleted) docs.
     */
    private Bits getLiveDocs(DirectoryReader directoryReader) {
        List<AtomicReaderContext> leaves = directoryReader.leaves();
        Bits liveDocs = null;
        if (leaves.size() == 1) {
            AtomicReader atomicReader = leaves.get(0).reader();
            liveDocs = atomicReader.getLiveDocs();
        }
        return liveDocs;
    }

    /**
     * Merges the keywords in the lucene index with a Map of existing clusters. Only clusters with relevance higher
     * than <tt>minClusterRelevance</tt> will be considered to merge new keywords in them.
     *
     * @param existingClusters    Map of existing clusters
     * @param minClusterRelevance Minimum cluster relevance defined as # of hits over # of keywords in the index
     *                            for all clusters. Only clusters with relevance higher than <tt>minClusterRelevance</tt>
     *                            will be considered to merge new keywords in it
     * @param inclusionPercentage The percentage to inlcude of similar documents
     * @return A Set of orphan keywords that could not be merged with an existing cluster
     * @throws IOException
     */
    public Set<String> merge(Map<String, Cluster> existingClusters, float minClusterRelevance, float inclusionPercentage)
            throws IOException {
        Set<String> orphanKeywords = new TreeSet<String>();
        int totalKeywordsMerged = 0;
        IndexWriter indexWriter = getIndexWriter();
        DirectoryReader directoryReader = DirectoryReader.open(indexWriter, false);

        // for each existing cluster extract the similar documents according the similarity threshold
        for (String key : existingClusters.keySet()) {
            Cluster cluster = existingClusters.get(key);

            Bits liveDocs = getLiveDocs(directoryReader);
            // TODO think in a way to retrieve similar docs based on cluster relevance
            // retrieve the similar docs
            Set<String> similarDocs = retrieveSimilarDocs(contentsSize, indexWriter, directoryReader,
                    liveDocs, cluster.getId().getName(), minClusterRelevance, inclusionPercentage);
            cluster.getElements().addAll(similarDocs);
            totalKeywordsMerged += similarDocs.size();
            // Updates the directory reader
            directoryReader = updateDirectoryReader(directoryReader);
        }
        logger.info("Total Keywords Merged: " + totalKeywordsMerged);
        // Calculate and return the orphan keywords....
        Bits liveDocs = getLiveDocs(directoryReader);
        for (int docNum = 0; docNum < directoryReader.maxDoc(); docNum++) {
            // this document is not deleted...
            if (liveDocs == null || liveDocs.get(docNum)) {
                orphanKeywords.add(getDocumentContents(directoryReader, docNum));
            }
        }

        directoryReader.close();
        indexWriter.close();

        return orphanKeywords;
    }

    /**
     * If the index has changed since the provided reader was opened, open and return a new reader.
     *
     * @param directoryReader The lucene index reader
     * @return An updated Directory Reader
     * @throws IOException
     */
    private DirectoryReader updateDirectoryReader(DirectoryReader directoryReader) throws IOException {
        if (!directoryReader.isCurrent()) {
            directoryReader = DirectoryReader.openIfChanged(directoryReader);
        }
        return directoryReader;
    }
}
