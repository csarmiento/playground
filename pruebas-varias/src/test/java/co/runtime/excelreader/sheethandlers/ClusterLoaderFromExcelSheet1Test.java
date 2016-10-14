package co.runtime.excelreader.sheethandlers;

import co.runtime.clustering.types.Cluster;
import co.runtime.excelreader.sheetreaders.GenericSheetReader;
import junit.framework.TestCase;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Map;

public class ClusterLoaderFromExcelSheet1Test extends TestCase {

    public void testReader() throws OpenXML4JException, SAXException, IOException {
        String filename = "preexcisting cluster.xlsx";
        ClusterLoaderFromExcelSheet1 sheetHandler = new ClusterLoaderFromExcelSheet1();
        sheetHandler.setProcessingRange(1, 300000);
        GenericSheetReader reader = new GenericSheetReader(filename, sheetHandler);
        reader.processSheet();

        Map<String, Cluster> existingClusters = sheetHandler.getExistingClusters();

        assertNotNull(existingClusters);
        assertTrue(existingClusters.size() > 0);

        int totalElements = 0;
        for (String key : existingClusters.keySet()) {
            totalElements += existingClusters.get(key).getElements().size();
        }

        System.out.println("Groups: " + existingClusters.size() + ", for " + totalElements + " elements");
    }
}