package co.runtime.dto;

import java.io.File;

/**
 * Class ...
 * <p/>
 * Creation: 5/26/14, 7:01 PM.
 */
public class ClusteringConfig {
    private File inputFile, outputFile;
    private Integer minClusterSize, maxClusterSize;
    private Float similarityThreshold;
    private String stopWords;

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

    public void setMinClusterSize(Integer minClusterSize) {
        this.minClusterSize = minClusterSize;
    }

    public void setMaxClusterSize(Integer maxClusterSize) {
        this.maxClusterSize = maxClusterSize;
    }

    public void setSimilarityThreshold(Float similarityThreshold) {
        this.similarityThreshold = similarityThreshold;
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public Integer getMinClusterSize() {
        return minClusterSize;
    }

    public Integer getMaxClusterSize() {
        return maxClusterSize;
    }

    public Float getSimilarityThreshold() {
        return similarityThreshold;
    }

    public String getStopWords() {
        return stopWords;
    }

    public void setStopWords(String stopWords) {
        this.stopWords = stopWords;
    }
}
