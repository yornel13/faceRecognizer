package org.opencv.javacv.facerecognition.db;

import org.opencv.javacv.facerecognition.model.Mensaje;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BDMensaje extends BDConectar {

	public static List<Mensaje> getTodosLosMensajes(){
		
		List<Mensaje> f = new ArrayList<>();
		String q = "SELECT * FROM mensaje";
		
        try {
            crearConexion();

            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                Mensaje mensaje = new Mensaje();
                mensaje.setId(rs.getInt("id"));
                mensaje.setUsuarioId(rs.getInt("usuario_id"));
                mensaje.setUsuarioNombre(rs.getString("usuario_nombre"));
                mensaje.setMensaje(rs.getString("mensaje"));
                mensaje.setFecha(rs.getString("fecha"));
                f.add(mensaje);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarConexion();
        }
        return f;
		
	}

	public static void saveMensaje(Mensaje mensaje){
		
		String q = "INSERT INTO mensaje (usuario_id, usuario_nombre, mensaje, fecha) " +
                "VALUES (?,?,?,?)";

        System.out.println("nuevo registro: " + q);

        try {
            crearConexion();

            PreparedStatement statement = conn.prepareStatement(q);
            statement.setInt(1, mensaje.getUsuarioId());
            statement.setString(2, mensaje.getUsuarioNombre());
            statement.setString(3, mensaje.getMensaje());
            statement.setString(4, mensaje.getFecha());

            statement.execute();

            ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
            rs.first();
            mensaje.setId( rs.getInt(1));
            rs.close();

            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarConexion();
        }
        return;
	}

}
