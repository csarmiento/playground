package co.runtime.dto;

import co.runtime.utils.StringUtils;
import junit.framework.TestCase;

import java.util.Collection;

public class ClusteringConfigTest extends TestCase {

    public void testGetStopWordsCollection() throws Exception {
        ClusteringConfig cfg = new ClusteringConfig();

        String test1 = "hola, amigo, como, estas, el, dia de hoy mi perrito";
        cfg.setStopWords(test1);

        System.out.println(test1);

        Collection<String> stopWordsCollection = StringUtils.getStopWordsCollection(cfg.getStopWords());
        assertEquals(10, stopWordsCollection.size());
        for (String s : stopWordsCollection) {
            System.out.println(s);
        }
    }
}