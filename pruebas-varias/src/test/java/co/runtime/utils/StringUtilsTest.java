package co.runtime.utils;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

    public static final String[] TEST_DEDUP_CASE_1 = new String[]{
            "+affordable +#70 +11 +x +17 +brochure",
            "+affordable +#70 +11 +x +17 +brochures",
            "+affordable +#70 +11x17 +brochure",
            "+affordable +#70 +11x17 +brochures",
            "+Affordable +70 +lb +11 +x +17 +brochure",
            "+Affordable +70 +lb +11 +x +17 +brochures",
            "+affordable +70 +lb +11x17 +brochure",
            "+affordable +70 +lb +11x17 +brochures",
            "+Affordable +70 +lbs +11 +x +17 +brochure",
            "+Affordable +70 +lbs +11 +x +17 +brochures",
            "+affordable +70 +lbs +11x17 +brochure",
            "+affordable +70 +lbs +11x17 +brochures",
            "affordable #70 11 x 17 brochure",
            "affordable #70 11 x 17 brochures",
            "affordable #70 11x17 brochure",
            "affordable #70 11x17 brochures",
            "affordable 70 lb 11 x 17 brochure",
            "affordable 70 lb 11 x 17 brochures",
            "affordable 70 lb 11x17 brochure",
            "affordable 70 lb 11x17 brochures",
            "affordable 70 lbs 11 x 17 brochure",
            "affordable 70 lbs 11 x 17 brochures",
            "affordable 70 lbs 11x17 brochure",
            "affordable 70 lbs 11x17 brochures",};

    public static final String[] TEST_DEDUP_CASE_2 = {
            "+about +zoo +printing",
            "+about +zooprinting",
            "+info +zoo +printing",
            "+info +zooprinting",
            "+information +zoo +printing",
            "+information +zooprinting",
            "+zoo +printing +info",
            "+zoo +printing +information",
            "+zooprinting +info",
            "+zooprinting +information",
            "about zoo printing",
            "about zooprinting",
            "info zoo printing",
            "info zooprinting",
            "information zoo printing",
            "information zooprinting",
            "zoo printing info",
            "zoo printing information",
            "zooprinting info",
            "zooprinting information",
            "+zoo +printing +home",
            "+zoo +printing +online",
            "+zoo +printing +webpage",
            "+zoo +printing +website",
            "+zooprinting +home",
            "+zooprinting +online",
            "+zooprinting +page",
            "+zooprinting +site",
            "+zooprinting +webpage",
            "+zooprinting +website",
            "zoo printing home",
            "zoo printing online",
            "zoo printing webpage",
            "zoo printing website",
            "zooprinting home",
            "zooprinting online",
            "zooprinting page",
            "zooprinting Site",
            "zooprinting webpage",
            "zooprinting website",};

    public void testGetStopWordsCollection() throws Exception {

    }

    public void testSortDelimitedString() throws Exception {

    }

    public void testDeduplicateTokens() throws Exception {
        String result = StringUtils.deduplicateTokens(TEST_DEDUP_CASE_1);

        assertEquals("#70 11 11x17 17 70 affordable brochure brochures lb lbs x", result);
        System.out.println(result);

        result = StringUtils.deduplicateTokens(TEST_DEDUP_CASE_2);

        assertEquals("about home info information online page printing site webpage website zoo zooprinting", result);
        System.out.println(result);
    }
}