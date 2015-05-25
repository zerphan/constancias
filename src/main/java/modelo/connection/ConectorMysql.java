/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.connection;

import java.sql.*;
import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 *
 * @author neko
 */
public class ConectorMysql {

    public static void main(String[] args) {
        ConectorMysql test = new ConectorMysql();
    }
    
    private String driver;
    private String url;
    private String user;
    private String password;
    private String recurso;

    public ConectorMysql() {
        this.driver ="com.mysql.jdbc.Driver";
        this.url="jdbc:mysql://localhost/constancias";
        this.user="root";
        this.password="root";
        recurso="jndi_ConstanciasSeminarios";
        
    }

    public ConectorMysql(String driver, String server,String database, String user, String password) {
        this.driver = driver;
        this.url = "jdbc:mysql://"+server+"/"+database;
        this.user = user;
        this.password = password;
    }
    

    public Connection getConexion() {
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error: No se puede conectar con la Base de Datos");
        } catch (ClassNotFoundException ee) {
            System.out.println("Error: No se puede cargar la Clase " + ee);
        }
        return con;
    }

    public void closeConexion(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println("Error: No se puede cerrar el Objeto Connection");
            }
        }
    }
     public Connection getConexionJNDI() {

        Connection con = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup(recurso);
            
            con = ds.getConnection();
        } catch (Exception e) {
            System.out.println("Error JNDI BASE DATOS POOL:" + e.toString());
        }
        return con;
    }

    public void closeConexion(CallableStatement cs) {
        if (cs != null) {
            try {
                cs.close();
            } catch (SQLException e) {
                System.out.println("Error: No se puede cerrar el Objeto CallableStatement");
            }
        }

    }

    public void closeConexion(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                System.out.println("Error: No se puede cerrar el Objeto ResultSet");
            }
        }
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String server,String database) {
        this.url = "jdbc:mysql://"+server+"/"+database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
}
