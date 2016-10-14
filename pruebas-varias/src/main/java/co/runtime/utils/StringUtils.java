package co.runtime.utils;

import java.util.*;

/**
 * Some handful string utilities needed to process clustering and merging operations
 *
 * @author Camilo Sarmiento
 */
public class StringUtils {
    /**
     * Returns a sorted Collection from a String with space or comma separated values
     *
     * @param stopWords String with space or comma separated StopWords
     * @return A sorted Collection from a String with space or comma separated values
     */
    public static Collection<String> getStopWordsCollection(String stopWords) {
        Set<String> stopWordsSet = new TreeSet<>();
        StringTokenizer st = new StringTokenizer(stopWords, ", ");
        while (st.hasMoreTokens()) {
            stopWordsSet.add(st.nextToken());
        }
        return stopWordsSet;
    }

    /**
     * Sorts a delimited string using natural order over the tokens and eliminates repeated tokens
     *
     * @param delimitedStr String to be sorted
     * @param delimiter    String delimiter
     * @return A reordered string
     */
    public static String sortDelimitedString(String delimitedStr, String delimiter, String newDelimiter) {
        StringTokenizer st = new StringTokenizer(delimitedStr, delimiter);
        TreeSet<String> set = new TreeSet<>();
        while (st.hasMoreTokens()) {
            set.add(st.nextToken());
        }
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String tok : set) {
            sb.append(tok);
            if (i + 1 < set.size()) {
                sb.append(newDelimiter);
                sb.append(" ");
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * Given an array of delimited (by space) strings, this method return a String with all
     * distinct (non-duplicates) words in lower case and any occurrence of '+' sign is
     * removed from the input.
     *
     * @param keywords An array of keywords
     * @return A <tt>String</tt> with unique words in the keywords array
     */
    public static String deduplicateTokens(String[] keywords) {
        StringTokenizer st;
        Set<String> words = new TreeSet<>();
        for (String kw : keywords) {
            st = new StringTokenizer(kw, "+ ");
            while (st.hasMoreTokens()) {
                words.add(st.nextToken().toLowerCase());
            }
        }

        return getString(words);
    }

    /**
     * Given an array of delimited (by space) strings, this method return a String with all
     * words in lower case and any occurrence of '+' sign is removed from the input.
     *
     * @param keywords An array of keywords
     * @return A <tt>String</tt> with all the words in the keywords array
     */
    public static String joinTokens(String[] keywords) {
        StringTokenizer st;
        List<String> words = new ArrayList<>();
        for (String kw : keywords) {
            st = new StringTokenizer(kw, "+ ");
            while (st.hasMoreTokens()) {
                words.add(st.nextToken().toLowerCase());
            }
        }
        return getString(words);
    }

    /**
     * Return a string with all the words in the collection
     *
     * @param words Collection of words
     * @return A string with all the words in the collection
     */
    private static String getString(Collection<String> words) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            if (i + 1 < words.size()) {
                sb.append(' ');
            }
            i++;
        }
        return sb.toString();
    }

    /**
     * Given a space delimited string, this method return a String with all
     * distinct (non-duplicates) words in lower case and any occurrence of '+'
     * sign is removed from the input.
     *
     * @param keyword A keyword
     * @return A <tt>String</tt> with unique words in the keyword
     */
    public static String deduplicateTokens(String keyword) {
        StringTokenizer st;
        Set<String> words = new TreeSet<>();
        st = new StringTokenizer(keyword, "+ ");
        while (st.hasMoreTokens()) {
            words.add(st.nextToken().toLowerCase());
        }

        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            sb.append(word);
            if (i + 1 < words.size()) {
                sb.append(' ');
            }
            i++;
        }
        return sb.toString();
    }
}