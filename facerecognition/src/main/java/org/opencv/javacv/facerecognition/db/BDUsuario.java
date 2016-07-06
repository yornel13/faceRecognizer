package org.opencv.javacv.facerecognition.db;

import org.opencv.javacv.facerecognition.model.Usuario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BDUsuario extends BDConectar {

	public static List<Usuario> getTodosLosUsuario(){
		
		List<Usuario> f = new ArrayList<>();
		String q = "SELECT * FROM usuario";
		
        try {
            crearConexion();

            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApellido(rs.getString("apellido"));
                usuario.setEdad(rs.getString("edad"));
                usuario.setColor(rs.getString("color"));
                usuario.setAmigo(rs.getString("amigo"));
                usuario.setNacimiento(rs.getString("nacimiento"));
                usuario.setFace1(rs.getBytes("face1"));
                usuario.setFace2(rs.getBytes("face2"));
                usuario.setFace3(rs.getBytes("face3"));
                usuario.setFace4(rs.getBytes("face4"));
                usuario.setFace5(rs.getBytes("face5"));
                usuario.setBoton1(rs.getBoolean("boton1"));
                usuario.setBoton2(rs.getBoolean("boton2"));
                usuario.setBoton3(rs.getBoolean("boton3"));
                usuario.setBoton4(rs.getBoolean("boton4"));
                f.add(usuario);
            }
            rs.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarConexion();
        }
        return f;
		
	}

	// Actualiza datos de la tabla familia en la BD.
	// Recibe como par�metro un objeto Familia.
	public static void crearUsuario(Usuario usuario){
		
		String q;
		
		if (usuario.getId() == null) {
            // Si el Id del usuario es cero es una nueva fila.
			q = "INSERT INTO usuario (nombre, apellido, edad, color, amigo, nacimiento, " +
                    "`face1`, `face2`, `face3`, `face4`, `face5`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?)";

            System.out.println("nuevo registro: " + q);
		} else {
			// Si el id es distinto de cero es una actualizacion del registro.
			q = "UPDATE usuario SET " +
				 "nombre = " + usuario.getNombre() + " " +
                 "apellido = " + usuario.getApellido() + " " +
                 "edad = " + usuario.getEdad() + " " +
                 "color = " + usuario.getColor() + " " +
                 "amigo = " + usuario.getAmigo() + " " +
                 "nacimiento = " + usuario.getNacimiento() + " " +
                    "face1 = " + usuario.getFace1() + " " +
                    "face2 = " + usuario.getFace2() + " " +
                    "face3 = " + usuario.getFace3() + " " +
                    "face4 = " + usuario.getFace4() + " " +
                    "face5 = " + usuario.getFace5() + " " +
				"WHERE id = " + usuario.getId();
		}
		
        try {
            crearConexion();

            PreparedStatement statement = conn.prepareStatement(q);
            statement.setString(1, usuario.getNombre());
            statement.setString(2, usuario.getApellido());
            statement.setString(3, usuario.getEdad());
            statement.setString(4, usuario.getColor());
            statement.setString(5, usuario.getAmigo());
            statement.setString(6, usuario.getNacimiento());
            statement.setBytes(7, usuario.getFace1());
            statement.setBytes(8, usuario.getFace2());
            statement.setBytes(9, usuario.getFace3());
            statement.setBytes(10, usuario.getFace4());
            statement.setBytes(11, usuario.getFace5());
            statement.execute();
            //Cuando es una nueva fila, averiguo el ultimo Id adsignado por la BD y lo pongo en el objeto Familia. 
            if (usuario.getId() == null){
                ResultSet rs = statement.executeQuery("SELECT LAST_INSERT_ID()");
                rs.first();
                usuario.setId( rs.getInt(1));
                rs.close();            	
            }
            statement.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarConexion();
        }
        return;
	}

    public static boolean updatePermisos(Usuario usuario) {

        String q = "UPDATE usuario SET " +
                "boton1 = " + usuario.getBoton1() + ", " +
                "boton2 = " + usuario.getBoton2() + ", " +
                "boton3 = " + usuario.getBoton3() + ", " +
                "boton4 = " + usuario.getBoton4() + " " +
                "WHERE id = " + usuario.getId();

        try {
            crearConexion();
            stat.executeUpdate( q );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        } finally {
            cerrarConexion();
        }
        return true;
    }

	// Borra datos de la tabla familia en la BD.
	// Recibe como par�metro un objeto Familia.
	public static void deleteUsuario(Usuario usuario){
		
		String q;
		q = "DELETE FROM usuario " +
			"WHERE id = " + usuario.getId();
		
        try {
            crearConexion();
            stat.executeUpdate( q );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            cerrarConexion();
        }
        return;
	}

}
