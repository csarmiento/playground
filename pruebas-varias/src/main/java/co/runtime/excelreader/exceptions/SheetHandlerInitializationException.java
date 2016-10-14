package co.runtime.excelreader.exceptions;

/**
 * Custom Exception for reading Excel Spreadsheets
 *
 * @author Camilo Sarmiento
 */
public class SheetHandlerInitializationException extends Exception {
    /**
     * Default UID
     */
    private static final long serialVersionUID = 1L;

    public SheetHandlerInitializationException(String errorMsg) {
        super(errorMsg);
    }
}
