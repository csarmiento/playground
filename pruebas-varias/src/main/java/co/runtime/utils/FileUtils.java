package co.runtime.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Some utilities for loading text files to memory
 *
 * @author Camilo Sarmiento
 */
public class FileUtils {

    /**
     * Load the lines of the input file to an array.<br/>
     * <b>BEWARE</b>: This method could cause an {@link java.lang.OutOfMemoryError}
     * if the input file size exceeds the maximum Heap size
     *
     * @param f Input file
     * @return An array with the file contents
     * @throws IOException
     */
    public static String[] loadFileInArray(File f) throws IOException {
        List<String> list = loadFileInList(f);
        return list.toArray(new String[list.size()]);
    }

    /**
     * Load the lines of the input file to a list.<br/>
     * <b>BEWARE</b>: This method could cause an {@link java.lang.OutOfMemoryError}
     * if the input file size exceeds the maximum Heap size
     *
     * @param f Input file
     * @return A list with the file contents
     * @throws IOException
     */
    public static List<String> loadFileInList(File f) throws IOException {
        List<String> list = new ArrayList<String>();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);

        String line = br.readLine();
        while (line != null) {
            list.add(new String(line));
            line = br.readLine();
        }
        br.close();
        fr.close();

        return list;
    }

    /**
     * Writes the contents of a String collection to an Excel file in the first column of the first sheet
     *
     * @param c    Collection
     * @param file Excel file to be written
     */
    public static void writeToExcel(Collection<String> c, File file) throws IOException {
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sh = wb.createSheet();

        int rownum = 0;
        for (String s : c) {
            Row row = sh.createRow(rownum);
            Cell cell1 = row.createCell(0);
            cell1.setCellValue(s);
            rownum++;
        }

        boolean fileOk = true;
        if (!file.exists()) {
            fileOk = file.createNewFile();
        }
        if (fileOk) {
            FileOutputStream out = new FileOutputStream(file);
            wb.write(out);
            out.close();
        } else {
            throw new IOException("Could not create file: " + file.getAbsolutePath());
        }

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}