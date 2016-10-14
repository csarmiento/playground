package co.runtime.clustering.types;

import junit.framework.TestCase;

public class KeywordHistogramTest extends TestCase {

    final static String[] testCase1 = {"business credit card",
            "business credit card 0 apr",
            "business credit card account",
            "business credit card application",
            "business credit card applications",
            "business credit card bad credit",
            "business credit card bank of america",
            "business credit card cash back",
            "business credit card comparison",
            "business credit card deals",
            "business credit card debt",
            "business credit card fees",
            "business credit card for bad credit",
            "business credit card for new business",
            "business credit card for new business with no credit",
            "business credit card for personal use",
            "business credit card fraud",
            "business credit card instant approval",
            "business credit card machine",
            "business credit card machines",
            "business credit card no annual fee",
            "business credit card no credit",
            "business credit card no personal credit check",
            "business credit card no personal guarantee",
            "business credit card offer",
            "business credit card offers",
            "business credit card policy",
            "business credit card processing",
            "business credit card review",
            "business credit card reviews",
            "business credit card rewards",
            "business credit card rewards comparison",
            "business credit card services",
            "business credit card with bad credit",
            "business credit card with no annual fee",
            "business credit card with no personal guarantee",
            "business credit card with reward",
            "business credit card with rewards",
            "business credit card without personal guarantee",
            "business credit cards",
            "business credit cards bad credit",
            "business credit cards cash back",
            "business credit cards chase",
            "business credit cards comparison",
            "business credit cards fair credit",
            "business credit cards for bad credit",
            "business credit cards for bad credit people",
            "business credit cards for fair credit",
            "business credit cards for new business",
            "business credit cards for new business with bad credit",
            "business credit cards for new business with no credit",
            "business credit cards for new businesses",
            "business credit cards for new businesses with no credit",
            "business credit cards for people with bad credit",
            "business credit cards for poor credit",
            "business credit cards for small business",
            "business credit cards for start ups",
            "business credit cards instant approval",
            "business credit cards no annual fee",
            "business credit cards no credit",
            "business credit cards no credit check",
            "business credit cards no credit history",
            "business credit cards no personal credit check",
            "business credit cards no personal guarantee",
            "business credit cards reviews",
            "business credit cards rewards",
            "business credit cards using ein only",
            "business credit cards with bad credit",
            "business credit cards with bad personal credit",
            "business credit cards with cashback",
            "business credit cards with cashback rewards",
            "business credit cards with ein only",
            "business credit cards with no annual fee",
            "business credit cards with no credit",
            "business credit cards with no personal guarantee",
            "business credit cards with rewards",
            "business credit cards with rewards program",
            "business credit cards without personal guarantee",};

    final static String[] testCase2 = {"+make +event +folded +card",
            "business card event",
            "business cards event",
            "card custom event folded",
            "card custom event folded made",
            "card custom event folded make",
            "card custom event made rack",
            "card custom event make rack",
            "card custom event rack",
            "card event folded",
            "card event folded maker",
            "card event folded online",
            "card event for post",
            "card event make rack",
            "card event maker rack",
            "card event online rack",
            "card event post",
            "card event rack",
            "cards custom event folded",
            "cards custom event folded made",
            "cards custom event folded make",
            "cards custom event made rack",
            "cards custom event make rack",
            "cards custom event rack",
            "cards event folded",
            "cards event folded make",
            "cards event folded maker",
            "cards event folded online",
            "cards event for post",
            "cards event make rack",
            "cards event maker rack",
            "cards event online rack",
            "cards event post",
            "cards event rack",
            "cd custom event insert",
            "cd custom event insert made",
            "cd custom event insert make",
            "cd custom event inserts",
            "cd custom event inserts made",
            "cd custom event inserts make",
            "cd event insert",
            "cd event insert make",
            "cd event insert maker",
            "cd event insert online",
            "cd event inserts",
            "cd event inserts make",
            "cd event inserts maker",
            "cd event inserts online",
            "cling event for printing window",
            "cling event for window",
            "cling event printing window",
            "cling event window",
            "clings event for printing window",
            "clings event for window",
            "clings event printing window",
            "clings event window",
            "custom envelope event",
            "custom envelope event made",
            "custom envelopes event",
            "custom envelopes event made",
            "custom event made sell sheet",
            "custom event made sell sheets",
            "custom event made table tent",
            "custom event made table tents",
            "custom event made ticket",
            "custom event made tickets",
            "custom event make sell sheet",
            "custom event make sell sheets",
            "custom event make ticket",
            "custom event make tickets",
            "custom event sell sheet",
            "custom event sell sheets",
            "custom event table tent",
            "custom event table tents",
            "custom event ticket",
            "custom event tickets",
            "envelope event",
            "envelope event make",
            "envelope event maker",
            "envelope event online",
            "envelopes event",
            "envelopes event make",
            "envelopes event maker",
            "envelopes event online",
            "event flier",
            "event fliers",
            "event flyer",
            "event flyers",
            "event for postcard",
            "event for postcards",
            "event leaflets",
            "event make sell sheet",
            "event make sell sheets",
            "event make table tent",
            "event make table tents",
            "event make ticket",
            "event make tickets",
            "event maker sell sheet",
            "event maker sell sheets",
            "event maker table tent",
            "event maker table tents",
            "event maker ticket",
            "event maker tickets",
            "event online sell sheet",
            "event online sell sheets",
            "event online table tent",
            "event online table tents",
            "event online ticket",
            "event online tickets",
            "event pamphlets",
            "event postcard",
            "event postcards",
            "event sell sheet",
            "event sell sheets",
            "event table tent",
            "event table tents",
            "event ticket",
            "event tickets",};

    public void testGetAverage() throws Exception {
        KeywordHistogram histogram = new KeywordHistogram(testCase1);
        double avg = histogram.getAverage();
        assertTrue(avg >= 7.3275);
        assertTrue(avg <= 7.3276);
    }

    public void testToString() throws Exception {
        KeywordHistogram histogram = new KeywordHistogram(testCase1);

        System.out.println(histogram);
    }

    public void testCutByAverage() {
        KeywordHistogram histogram = new KeywordHistogram(testCase1);

        System.out.println(histogram.cutByAverage());
    }

    public void testCutByOccurrences() {
        KeywordHistogram histogram = new KeywordHistogram(testCase1);

        System.out.println(histogram.cutByOccurrences(0));
        System.out.println();
        System.out.println(histogram.cutByOccurrences(1));
        System.out.println();
        System.out.println(histogram.cutByOccurrences(2));
        System.out.println();
        System.out.println(histogram.cutByAverage());
    }

    public void testCutByOccurrences1() {
        KeywordHistogram histogram = new KeywordHistogram(testCase2);

        System.out.println(histogram.cutByOccurrences(0));
        System.out.println();
        System.out.println(histogram.cutByOccurrences(1));
        System.out.println();
        System.out.println(histogram.cutByOccurrences(2));
        System.out.println();
        System.out.println(histogram.cutByAverage());
    }
}