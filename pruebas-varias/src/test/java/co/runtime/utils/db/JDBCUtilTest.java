package co.runtime.utils.db;

import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class JDBCUtilTest extends TestCase {
    private static Logger logger = LogManager.getLogger();

    public void testGetConnection() {
        ResourceBundle rb = ResourceBundle.getBundle("testconndata");

        Map<DBConnectionProperties, String> dbConfig = new HashMap<DBConnectionProperties, String>();

        dbConfig.put(DBConnectionProperties.CONNECTION_URL,
                rb.getString(DBConnectionProperties.CONNECTION_URL.toString()));
        dbConfig.put(DBConnectionProperties.DRIVER_CLASS,
                rb.getString(DBConnectionProperties.DRIVER_CLASS.toString()));
        dbConfig.put(DBConnectionProperties.PASSWORD,
                rb.getString(DBConnectionProperties.PASSWORD.toString()));
        dbConfig.put(DBConnectionProperties.USER_NAME,
                rb.getString(DBConnectionProperties.USER_NAME.toString()));

        Connection conn = null;
        conn = obtainConnection(dbConfig, conn);
        assertNotNull(conn);
        performMySqlQuery(conn);
        closeConnection(conn);
    }

    public void testGetConnectionSqlServer() {
        ResourceBundle rb = ResourceBundle.getBundle("testconndatasqlserver");

        Map<DBConnectionProperties, String> dbConfig = new HashMap<DBConnectionProperties, String>();

        dbConfig.put(DBConnectionProperties.CONNECTION_URL,
                rb.getString(DBConnectionProperties.CONNECTION_URL.toString()));
        dbConfig.put(DBConnectionProperties.DRIVER_CLASS,
                rb.getString(DBConnectionProperties.DRIVER_CLASS.toString()));
        dbConfig.put(DBConnectionProperties.PASSWORD,
                rb.getString(DBConnectionProperties.PASSWORD.toString()));
        dbConfig.put(DBConnectionProperties.USER_NAME,
                rb.getString(DBConnectionProperties.USER_NAME.toString()));

        Connection conn = null;
        conn = obtainConnection(dbConfig, conn);
        assertNotNull(conn);
        performMsSqlQuery(conn);
        closeConnection(conn);
    }

    private void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    private Connection obtainConnection(
            Map<DBConnectionProperties, String> dbConfig, Connection conn) {
        try {
            conn = JDBCUtil.getConnection(dbConfig);
        } catch (ClassNotFoundException e) {
            fail(e.getMessage());
        } catch (SQLException e) {
            fail(e.getMessage());
        }
        return conn;
    }

    private void performMsSqlQuery(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT getdate()");
            assertNotNull(ps);
            ResultSet rs = ps.executeQuery();
            assertNotNull(rs);
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            while (rs.next()) {
                for (int i = 0; i < columnCount; i++) {
                    Object object = rs.getObject(i + 1);
                    System.out.print(object + " ");
                }
                System.out.println();
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.error("No pudo ejecutar la consulta", e);
            fail(e.getMessage());
        }
    }

    private void performMySqlQuery(Connection conn) {
        try {
            PreparedStatement ps = conn.prepareStatement("SELECT now()");
            assertNotNull(ps);
            ResultSet rs = ps.executeQuery();
            assertNotNull(rs);
            while (rs.next()) {
                System.out.println(rs.getTimestamp(1));
            }
            rs.close();
            ps.close();
        } catch (SQLException e) {
            logger.error("No pudo ejecutar la consulta", e);
            fail(e.getMessage());
        }
    }
}