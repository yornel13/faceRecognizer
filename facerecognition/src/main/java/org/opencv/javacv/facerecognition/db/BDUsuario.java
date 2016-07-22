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
                usuario.setCedula(rs.getString("cedula"));
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
                usuario.setFace6(rs.getBytes("face6"));
                usuario.setFace7(rs.getBytes("face7"));
                usuario.setFace8(rs.getBytes("face8"));
                usuario.setFace9(rs.getBytes("face9"));
                usuario.setFace10(rs.getBytes("face10"));
                usuario.setFace11(rs.getBytes("face11"));
                usuario.setFace12(rs.getBytes("face12"));
                usuario.setFace13(rs.getBytes("face13"));
                usuario.setFace14(rs.getBytes("face14"));
                usuario.setFace15(rs.getBytes("face15"));
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

    public static Usuario getUsuarioByCedula(String cedula){

        Usuario usuario = new Usuario();

        String q = "SELECT * FROM usuario where cedula = "+ Integer.valueOf(cedula);

        try {
            crearConexion();

            ResultSet rs = stat.executeQuery(q);
            while (rs.next()) {
                usuario.setId(rs.getInt("id"));
                usuario.setCedula(rs.getString("cedula"));
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
                usuario.setFace6(rs.getBytes("face6"));
                usuario.setFace7(rs.getBytes("face7"));
                usuario.setFace8(rs.getBytes("face8"));
                usuario.setFace9(rs.getBytes("face9"));
                usuario.setFace10(rs.getBytes("face10"));
                usuario.setFace11(rs.getBytes("face11"));
                usuario.setFace12(rs.getBytes("face12"));
                usuario.setFace13(rs.getBytes("face13"));
                usuario.setFace14(rs.getBytes("face14"));
                usuario.setFace15(rs.getBytes("face15"));
                usuario.setBoton1(rs.getBoolean("boton1"));
                usuario.setBoton2(rs.getBoolean("boton2"));
                usuario.setBoton3(rs.getBoolean("boton3"));
                usuario.setBoton4(rs.getBoolean("boton4"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            usuario = null;
        } finally {
            cerrarConexion();
        }
        return usuario;

    }

	// Actualiza datos de la tabla familia en la BD.
	// Recibe como par�metro un objeto Familia.
	public static int crearUsuario(Usuario usuario){

        int code = 0;
		
		String q;
		
		if (usuario.getId() == null) {
            // Si el Id del usuario es cero es una nueva fila.
			q = "INSERT INTO usuario (cedula, nombre, apellido, edad, color, amigo, nacimiento, " +
                    "`face1`, `face2`, `face3`, `face4`, `face5`, `face6`, `face7`, `face8`, " +
                    "`face9`, `face10`, `face11`, `face12`, `face13`, `face14`, `face15`) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            System.out.println("nuevo registro: " + q);
		} else {
			// Si el id es distinto de cero es una actualizacion del registro.
			q = "UPDATE usuario SET " +
                 "cedula = ?, " +
				 "nombre = ?, " +
                 "apellido = ?, " +
                 "edad = ?, " +
                 "color = ?, " +
                 "amigo = ?, " +
                 "nacimiento = ?, " +
                    "face1 = ?, " +
                    "face2 = ?, " +
                    "face3 = ?, " +
                    "face4 = ?, " +
                    "face5 = ?, " +
                    "face6 = ?, " +
                    "face7 = ?, " +
                    "face8 = ?, " +
                    "face9 = ?, " +
                    "face10 = ?, " +
                    "face11 = ?, " +
                    "face12 = ?, " +
                    "face13 = ?, " +
                    "face14 = ?, " +
                    "face15 = ? " +
				"WHERE id = " + usuario.getId();

            System.out.println("edicion de registro: " + q);
		}
		
        try {
            crearConexion();

            PreparedStatement statement = conn.prepareStatement(q);
            statement.setString(1, usuario.getCedula());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getApellido());
            statement.setString(4, usuario.getEdad());
            statement.setString(5, usuario.getColor());
            statement.setString(6, usuario.getAmigo());
            statement.setString(7, usuario.getNacimiento());
            statement.setBytes(8, usuario.getFace1());
            statement.setBytes(9, usuario.getFace2());
            statement.setBytes(10, usuario.getFace3());
            statement.setBytes(11, usuario.getFace4());
            statement.setBytes(12, usuario.getFace5());
            statement.setBytes(13, usuario.getFace6());
            statement.setBytes(14, usuario.getFace7());
            statement.setBytes(15, usuario.getFace8());
            statement.setBytes(16, usuario.getFace9());
            statement.setBytes(17, usuario.getFace10());
            statement.setBytes(18, usuario.getFace11());
            statement.setBytes(19, usuario.getFace12());
            statement.setBytes(20, usuario.getFace13());
            statement.setBytes(21, usuario.getFace14());
            statement.setBytes(22, usuario.getFace15());
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
            if (e.getMessage().contains("cedula_UNIQUE"))
                code = 99;
            else {
                code = 1;
                e.printStackTrace();
            }

        } finally {
            cerrarConexion();
        }
        return code;
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

	public static int deleteUsuario(Usuario usuario){

        int code = 0;
		
		String q;
		q = "DELETE FROM usuario " +
			"WHERE id = " + usuario.getId();
		
        try {
            crearConexion();
            stat.executeUpdate( q );
        } catch (Exception e) {
            System.out.println(e.getMessage());
            code = 1;
        } finally {
            cerrarConexion();
        }
        return code;
	}

}
