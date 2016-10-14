package co.runtime.clustering.types;

/**
 * Cluster identifier where the name of the cluster could be compound of the cluster terms.
 * This will allow similarity comparisons mainly for merging keywords into existing clusters.
 */
public class ClusterId {
    private int id;
    private String name;

    /**
     * Class constructor
     *
     * @param id   An integer that identifies the cluster
     * @param name Name of the cluster. Could be compound of the cluster terms for allowing
     *             similarity comparisons
     */
    public ClusterId(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
