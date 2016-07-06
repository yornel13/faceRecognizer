package org.opencv.javacv.facerecognition.model;

import org.opencv.javacv.facerecognition.db.BDUsuario;

import java.util.ArrayList;

/**
 * Created by Yornel on 26-jun-16.
 */
public class Usuario {

    private Integer id;
    private String nombre;
    private String apellido;
    private String edad;
    private String color;
    private String amigo;
    private String nacimiento;
    private byte[]  face1;
    private byte[]  face2;
    private byte[]  face3;
    private byte[]  face4;
    private byte[]  face5;
    private Boolean boton1;
    private Boolean boton2;
    private Boolean boton3;
    private Boolean boton4;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAmigo() {
        return amigo;
    }

    public void setAmigo(String amigo) {
        this.amigo = amigo;
    }

    public String getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(String nacimiento) {
        this.nacimiento = nacimiento;
    }


    public byte[] getFace1() {
        return face1;
    }

    public void setFace1(byte[] face1) {
        this.face1 = face1;
    }

    public byte[] getFace2() {
        return face2;
    }

    public void setFace2(byte[] face2) {
        this.face2 = face2;
    }

    public byte[] getFace3() {
        return face3;
    }

    public void setFace3(byte[] face3) {
        this.face3 = face3;
    }

    public byte[] getFace4() {
        return face4;
    }

    public void setFace4(byte[] face4) {
        this.face4 = face4;
    }

    public byte[] getFace5() {
        return face5;
    }

    public void setFace5(byte[] face5) {
        this.face5 = face5;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public Boolean getBoton1() {
        return boton1;
    }

    public void setBoton1(Boolean boton1) {
        this.boton1 = boton1;
    }

    public Boolean getBoton2() {
        return boton2;
    }

    public void setBoton2(Boolean boton2) {
        this.boton2 = boton2;
    }

    public Boolean getBoton3() {
        return boton3;
    }

    public void setBoton3(Boolean boton3) {
        this.boton3 = boton3;
    }

    public Boolean getBoton4() {
        return boton4;
    }

    public void setBoton4(Boolean boton4) {
        this.boton4 = boton4;
    }

    // Añade o actualiza datos de familias.
    public void create(){
        BDUsuario.crearUsuario(this);
    }

    public boolean changePermisos() {
        return BDUsuario.updatePermisos(this);
    }

    // Borra datos de familia.
    // Importante, si se está borrando desde una lista,
    // eliminar el objeto de la misma.
    public void delete(){
        BDUsuario.deleteUsuario(this);
    }

    public static Usuario getUsuario(ArrayList<Usuario> usuarios, int id){
        for (Usuario usuario: usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return  null;
    }

}
