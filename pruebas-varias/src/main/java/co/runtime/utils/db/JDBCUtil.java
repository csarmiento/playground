package co.runtime.utils.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class JDBCUtil {

    private static Logger logger = LogManager.getLogger();

    /**
     * Returns a SQL connection from a properties map which holds the database
     * properties
     * 
     * @param connProperties
     *            Connection properties
     * @return a SQL connection from a properties map which holds the database
     *         properties
     * @throws ClassNotFoundException
     * @throws java.sql.SQLException
     */
    public static Connection getConnection(
            Map<DBConnectionProperties, String> connProperties)
            throws ClassNotFoundException, SQLException {
        Connection conn = null;
        Class.forName(connProperties.get(DBConnectionProperties.DRIVER_CLASS));
        conn = DriverManager.getConnection(
                connProperties.get(DBConnectionProperties.CONNECTION_URL),
                connProperties.get(DBConnectionProperties.USER_NAME),
                connProperties.get(DBConnectionProperties.PASSWORD));
        return conn;
    }

    /**
     * Closes a SQL Connection managing the SQLException
     * 
     * @param conn
     *            Connection to be closed
     */
    public static void closeConnection(Connection conn) {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    /**
     * Closes a Statement managing the SQLException
     * 
     * @param st
     *            Statement to be closed
     */
    public static void closeStatement(Statement st) {
        try {
            if (st != null && !st.isClosed()) {
                st.close();
            }
        } catch (SQLException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

}
