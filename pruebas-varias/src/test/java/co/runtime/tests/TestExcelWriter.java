package co.runtime.tests;

import junit.framework.Assert;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Class ...
 * <p/>
 * Creation: 5/28/14, 6:19 PM.
 */
public class TestExcelWriter {
    public static void main(String[] args) throws Throwable {
        SXSSFWorkbook wb = new SXSSFWorkbook(100); // keep 100 rows in memory, exceeding rows will be flushed to disk
        Sheet sh = wb.createSheet();
        for (int rownum = 0; rownum < 1000; rownum++) {
            Row row = sh.createRow(rownum);
            for (int cellnum = 0; cellnum < 10; cellnum++) {
                Cell cell = row.createCell(cellnum);
                String address = new CellReference(cell).formatAsString();
                cell.setCellValue(address);
            }

        }

        // Rows with rownum < 900 are flushed and not accessible
        for (int rownum = 0; rownum < 900; rownum++) {
            Assert.assertNull(sh.getRow(rownum));
        }

        // ther last 100 rows are still in memory
        for (int rownum = 900; rownum < 1000; rownum++) {
            Assert.assertNotNull(sh.getRow(rownum));
        }

        boolean fileOk = false;
        File testFile = new File("sxssf.xlsx");
        if (!testFile.exists()) {
            fileOk = testFile.createNewFile();
        }
        if (fileOk) {
            FileOutputStream out = new FileOutputStream(testFile);
            wb.write(out);
            out.close();
        } else {
            System.err.println("FAILED!");
        }

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}
