package co.runtime.excelreader.sheethandlers;

import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler.SheetContentsHandler;

/**
 * Abstract handler to read sheets from an Excel file
 *
 * @author Camilo Sarmiento
 */
public abstract class AbstractSheetContentsHandler implements
        SheetContentsHandler {

    private String sheetId;

    protected int currentRow;

    protected ProcessingRange range;
    /**
     * Number of processed cells
     */
    protected int processedCells;
    /**
     * Number of processed rows
     */
    protected int processedRows;

    /**
     * Constructs a sheet contents handler associated with an specific sheet.<br/>
     * The sheet id seems to either be rId# or rSheet#
     *
     * @param sheetId A <tt>String</tt> representing the sheet. Seems to either be
     *                rId# or rSheet#.
     */
    public AbstractSheetContentsHandler(String sheetId) {
        this.sheetId = sheetId;
        range = null;
    }

    /**
     * @return the sheetId
     */
    public String getSheetId() {
        return sheetId;
    }

    /**
     * Allow to limit which rows are going to be read from an Excel sheet
     *
     * @param initial Initial row to read
     * @param end     Final row to read
     */
    public void setProcessingRange(int initial, int end) {
        range = new ProcessingRange(initial, end);
    }

    public boolean isRangeActive() {
        return range != null;
    }

    @Override
    public abstract void startRow(int rowNum);

    @Override
    public abstract void endRow();

    @Override
    public abstract void cell(String cellReference, String formattedValue);

    @Override
    public abstract void headerFooter(String text, boolean isHeader,
                                      String tagName);

    /**
     * Represents the rows to be processed from the Excel sheet
     */
    protected class ProcessingRange {
        private int firstRow;
        private int lastRow;

        public ProcessingRange(int first, int last) {
            firstRow = first;
            lastRow = last;
        }

        /**
         * @return the firstRow
         */
        public int getFirstRow() {
            return firstRow;
        }

        /**
         * @return the lastRow
         */
        public int getLastRow() {
            return lastRow;
        }

    }
}