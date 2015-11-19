package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import DAO.BancoDAOException;
import javax.swing.JOptionPane;
import visao.ConexaoBanco;

public class ConnectionBancoFactory {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost/bomsamaritano";
    //private static String server;
    private ConexaoBanco conexaobanco;
    //private static final String DATABASE_URL = "jdbc:mysql://localhost/bomsamaritano2";
    
    public static Connection getConnection() throws BancoDAOException {
        try {
           // Class.forName(JDBC_DRIVER);
            //System.out.println("conectado com o banco");
            //return DriverManager.getConnection(DATABASE_URL, "root", "");
            return new ConexaoBanco().getConnection();
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha na conexão");
            throw new BancoDAOException(e.getMessage());
        }
    }

    public static void fecharConexao(Connection conn, Statement stmt, ResultSet rs) 
                                                throws BancoDAOException {
        close(conn, stmt, rs);
    }

    public static void fecharConexao(Connection conn, Statement stmt)
                                                throws BancoDAOException {
        close(conn, stmt, null);
    }

    public static void fecharConexao(Connection conn)
                                                throws BancoDAOException {
        close(conn, null, null);
    }

    private static void close(Connection conn, Statement stmt, ResultSet rs)
                                                throws BancoDAOException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            throw new BancoDAOException(e.getMessage());
        }
    }
}
