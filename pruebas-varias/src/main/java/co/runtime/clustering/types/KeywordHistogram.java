package co.runtime.clustering.types;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * Represents a histogram of keywords
 *
 * @author Camilo Sarmiento
 */
public class KeywordHistogram {
    private Map<String, Integer> histogram;

    /**
     * Class constructor from an array of keywords
     *
     * @param keywords Array of keywords
     */
    public KeywordHistogram(String[] keywords) {
        histogram = new HashMap<String, Integer>();

        StringTokenizer st;
        for (String kw : keywords) {
            st = new StringTokenizer(kw, "+ ");
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                Integer occurs = histogram.get(word);
                if (occurs == null) {
                    histogram.put(word, 1);
                } else {
                    histogram.put(word, occurs + 1);
                }
            }
        }
    }

    /**
     * Class constructor from a Collection of keywords
     *
     * @param keywords Collection of keywords
     */
    public KeywordHistogram(Collection<String> keywords) {
        histogram = new HashMap<String, Integer>();

        StringTokenizer st;
        for (String kw : keywords) {
            st = new StringTokenizer(kw, "+ ");
            while (st.hasMoreTokens()) {
                String word = st.nextToken();
                Integer occurs = histogram.get(word);
                if (occurs == null) {
                    histogram.put(word, 1);
                } else {
                    histogram.put(word, occurs + 1);
                }
            }
        }
    }

    /**
     * Returns the average number of occurrences for the keywords
     *
     * @return The average number of occurrences for the keywords
     */
    public double getAverage() {
        double avg = 0;
        for (String word : histogram.keySet()) {
            avg += histogram.get(word);
        }
        return avg / histogram.size();
    }

    /**
     * Returns a String with the keywords that appear more times than the average
     *
     * @return A String with the keywords that appear more times than the average
     */
    public String cutByAverage() {
        return cutByOccurrences(getAverage());
    }

    /**
     * Returns a String with the keywords that appear more times than the <tt>occurs</tt> parameter
     *
     * @return A String with the keywords that appear more times than the <tt>occurs</tt> parameter
     */
    public String cutByOccurrences(double occurs) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String word : histogram.keySet()) {
            if (histogram.get(word) > occurs) {
                sb.append(word);
                if (i + 1 < histogram.size()) {
                    sb.append(' ');
                }
            }
            i++;
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (String word : histogram.keySet()) {
            sb.append(word);
            sb.append(':');
            sb.append(histogram.get(word));
            sb.append(System.getProperty("line.separator"));
        }
        return sb.toString();
    }
}

