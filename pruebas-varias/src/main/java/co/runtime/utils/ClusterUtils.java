package co.runtime.utils;

import co.runtime.clustering.types.Cluster;
import co.runtime.excelreader.sheethandlers.ClusterLoaderFromExcelSheet1;
import co.runtime.excelreader.sheetreaders.GenericSheetReader;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * Clustering utilities
 *
 * @author Camilo Sarmiento
 */
public class ClusterUtils {

    /**
     * Given the path of an Excel file with one sheet and two columns, where the first one represents the
     * cluster identifier and the second one represents the keyword belonging to that cluster, this method loads
     * the clusters as a Map using the identifier as the key.
     *
     * @param filename   Excel file path
     * @param initialRow First row to read
     * @param endRow     Last row to read
     * @return A Map of clusters with the identifier as the key.
     * @throws OpenXML4JException
     * @throws SAXException
     * @throws IOException
     */
    public static Map<String, Cluster> loadClustersFromExcel(String filename, int initialRow, int endRow)
            throws OpenXML4JException, SAXException, IOException {
        ClusterLoaderFromExcelSheet1 sheetHandler = new ClusterLoaderFromExcelSheet1();
        sheetHandler.setProcessingRange(initialRow, endRow);
        GenericSheetReader reader = new GenericSheetReader(filename, sheetHandler);
        reader.processSheet();

        return sheetHandler.getExistingClusters();
    }

    /**
     * Writes the contents of a Clusters set to an Excel file in the first two columns of the first sheet,
     * where the first one represents the cluster identifier and the second represents the keyword
     * belonging to that cluster.
     *
     * @param clusters Clusters set
     * @param destFile Target file
     * @throws IOException
     */
    public static void writeClustersToExcel(Map<String, Cluster> clusters, File destFile) throws IOException {
        // keep 100 rows in memory, exceeding rows will be flushed to disk
        SXSSFWorkbook wb = new SXSSFWorkbook(100);
        Sheet sh = wb.createSheet();

        int rownum = 0;
        for (String key : clusters.keySet()) {
            Cluster c = clusters.get(key);
            for (String elem : c.getElements()) {
                Row row = sh.createRow(rownum);
                Cell cell1 = row.createCell(0);
                cell1.setCellValue(key);
                Cell cell2 = row.createCell(1);
                cell2.setCellValue(elem);
                Cell cell3 = row.createCell(2);
                cell3.setCellValue(c.getId().getName());
                rownum++;
            }
        }

        boolean fileOk = true;
        if (!destFile.exists()) {
            fileOk = destFile.createNewFile();
        }
        if (fileOk) {
            FileOutputStream out = new FileOutputStream(destFile);
            wb.write(out);
            out.close();
        } else {
            throw new IOException("Could not create file: " + destFile.getAbsolutePath());
        }

        // dispose of temporary files backing this workbook on disk
        wb.dispose();
    }
}
