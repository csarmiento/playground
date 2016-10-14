package co.runtime.excelreader.sheetreaders;

import co.runtime.excelreader.sheethandlers.AbstractSheetContentsHandler;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.ReadOnlySharedStringsTable;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.model.StylesTable;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class that uses Apache POI XSSF and SAX (Event API) to read and process big Excel Spreadsheets,
 * and SXSSF (Streaming Usermodel API) to write Excel Spreadsheets.
 *
 * @author Camilo Sarmiento
 */
public class GenericSheetReader {

    private XMLReader parser;
    private XSSFReader reader;
    private String sheetId;
    private AbstractSheetContentsHandler sheetHandler;

    /**
     * Class constructor
     *
     * @param filename     Path to Excel file
     * @param sheetHandler Sheet handler instance
     * @throws IOException
     * @throws OpenXML4JException
     * @throws SAXException
     */
    public GenericSheetReader(String filename,
                              AbstractSheetContentsHandler sheetHandler) throws IOException,
            OpenXML4JException, SAXException {
        this.sheetId = sheetHandler.getSheetId();
        this.sheetHandler = sheetHandler;

        OPCPackage pkg = OPCPackage.open(filename);
        reader = new XSSFReader(pkg);

        parser = fetchSheetParser(pkg);
    }

    /**
     * Returns a XMLReader object to process the Excel file
     *
     * @param pkg A container that can store multiple data objects.
     * @return A XMLReader object to process the Excel file
     * @throws SAXException
     * @throws InvalidFormatException
     * @throws IOException
     */
    private XMLReader fetchSheetParser(OPCPackage pkg)
            throws SAXException, InvalidFormatException, IOException {
        XMLReader parser = XMLReaderFactory
                .createXMLReader("org.apache.xerces.parsers.SAXParser");

        StylesTable styles = reader.getStylesTable();
        ReadOnlySharedStringsTable sharedStringsTable = new ReadOnlySharedStringsTable(
                pkg);

        ContentHandler handler = new XSSFSheetXMLHandler(styles,
                sharedStringsTable, this.sheetHandler, false);
        parser.setContentHandler(handler);
        return parser;
    }

    /**
     * Reads and processes information in the Excel file for the specific sheet according the handler
     *
     * @throws InvalidFormatException
     * @throws IOException
     * @throws SAXException
     */
    public void processSheet() throws InvalidFormatException, IOException,
            SAXException {
        InputStream sheet = reader.getSheet(sheetId);
        InputSource sheetSource = new InputSource(sheet);
        parser.parse(sheetSource);
        sheet.close();
    }
}