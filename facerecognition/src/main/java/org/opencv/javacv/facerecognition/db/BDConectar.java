package org.opencv.javacv.facerecognition.db;

import java.sql.*;

public class BDConectar {

	//static String host      = "181.196.141.66:3306";
    static String host = "192.168.0.2:3306";
    static String dataBase = "dbface";
    static String user   = "root";
    static String password  = "@Arcadia2015@";
    static String cadCon	= "jdbc:mysql://"+host+"/"+dataBase;

    public static Connection conn;
    public static Statement stat;

    /**
     * Crea la conexion con la BBD
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws SQLException
     */
    public static void crearConexion() throws InstantiationException,
            IllegalAccessException, ClassNotFoundException {

    	try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("intentanto conectar");
            System.out.println(cadCon);
            System.out.println(user);
            System.out.println(password);
            conn = DriverManager.getConnection(cadCon, user, password);
            System.out.println("intentanto crear statement");
            stat = conn.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    /**
     * Cierra la conexion con la BBDD
     */
    public static void cerrarConexion() {
        try {
            if (stat != null) {
                stat.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
        }
    }

}
