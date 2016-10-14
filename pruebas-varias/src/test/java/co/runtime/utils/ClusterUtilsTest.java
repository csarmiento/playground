package co.runtime.utils;

import co.runtime.clustering.types.Cluster;
import junit.framework.TestCase;

import java.util.Map;

public class ClusterUtilsTest extends TestCase {

    public void testLoadClustersFromExcel() throws Exception {
        String filename = "preexcisting cluster.xlsx";
        Map<String, Cluster> clusters = ClusterUtils.loadClustersFromExcel(filename, 1, 300000);

        assertNotNull(clusters);
        assertTrue(clusters.size() > 0);

        int totalElements = 0;
        for (String key : clusters.keySet()) {
            totalElements += clusters.get(key).getElements().size();
        }

        System.out.println("Groups: " + clusters.size() + ", for " + totalElements + " elements");
    }
}