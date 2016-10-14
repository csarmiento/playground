package co.runtime.excelreader.sheethandlers;

import co.runtime.clustering.types.Cluster;
import co.runtime.clustering.types.ClusterId;
import co.runtime.utils.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Custom handler to read the <b>first two columns</b> of the <b>first spreadsheet</b> from an Excel file.
 *
 * @author Camilo Sarmiento
 */
public class ClusterLoaderFromExcelSheet1 extends AbstractSheetContentsHandler {
    private static final String SHEET_ID = "rId1";

    private Map<String, Cluster> existingClusters;
    private String currentGroupName, keyword;

    public ClusterLoaderFromExcelSheet1() {
        super(SHEET_ID);
        existingClusters = new HashMap<String, Cluster>();
    }

    @Override
    public void startRow(int rowNum) {
        currentRow = rowNum;
        processedCells = 0;
    }

    @Override
    public void endRow() {
        boolean abort = false;
        if (isRangeActive()) {
            if (currentRow < range.getFirstRow()
                    && currentRow > range.getLastRow()) {
                abort = true;
            }
        }
        if (!abort && processedCells >= 2) {
            // Add the keyword to a cluster
            Cluster c = existingClusters.get(currentGroupName);
            if (c != null) {
                c.addElement(StringUtils.deduplicateTokens(keyword));
            } else {
                ClusterId cId = new ClusterId(currentRow, currentGroupName);
                Set<String> elements = new TreeSet<String>();
                elements.add(keyword);
                c = new Cluster(cId, elements);
                existingClusters.put(currentGroupName, c);
            }
        }
    }

    @Override
    public void cell(String cellReference, String formattedValue) {
        boolean updateRow = true;
        if (isRangeActive()) {
            if (currentRow < range.getFirstRow()
                    || currentRow > range.getLastRow()) {
                updateRow = false;
            }
        }
        if (updateRow) {
            switch (cellReference.charAt(0)) {
                case 'A': // Group Name
                    currentGroupName = formattedValue;
                    break;
                case 'B': // Keyword
                    keyword = formattedValue;
                    break;
            }
            processedCells++;
        }
    }

    @Override
    public void headerFooter(String text, boolean isHeader, String tagName) {
        // Not necessary to implement for this class
    }

    /**
     * Returns the loaded clusters
     *
     * @return A Map of Clusters from the Excel file
     */
    public Map<String, Cluster> getExistingClusters() {
        return existingClusters;
    }
}
