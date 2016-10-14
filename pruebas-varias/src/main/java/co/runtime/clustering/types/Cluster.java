package co.runtime.clustering.types;

import java.util.Set;

/**
 * Represents a cluster of objects
 *
 * @author Camilo Sarmiento
 */
public class Cluster {
    private ClusterId id;
    private Set<String> elements;
    private KeywordHistogram histogram;

    /**
     * Class constructor
     *
     * @param id       Cluster identifier of type {@link ClusterId}
     * @param elements A set of elements conforming the current cluster
     */
    public Cluster(ClusterId id, Set<String> elements) {
        this.id = id;
        this.elements = elements;
        recalculateName();
    }

    public ClusterId getId() {
        return id;
    }

    public Set<String> getElements() {
        return elements;
    }

    /**
     * Allows to add an existing element to the cluster if it is not already present.
     *
     * @param elem Element to be added to this set
     */
    public void addElement(String elem) {
        elements.add(elem);
        recalculateName();
    }

    /**
     * Updates the internal cluster id name used for merging operations
     */
    private void recalculateName() {
        histogram = new KeywordHistogram(elements);
        this.id.setName(histogram.cutByOccurrences(0));
    }
}
