package org.opencv.javacv.facerecognition.model;

import android.content.Intent;
import android.os.Bundle;

import org.opencv.javacv.facerecognition.db.BDUsuario;

import java.util.ArrayList;

/**
 * Created by Yornel on 26-jun-16.
 */
public class Usuario {

    private Integer id;
    private String cedula;
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
    private byte[]  face6;
    private byte[]  face7;
    private byte[]  face8;
    private byte[]  face9;
    private byte[]  face10;
    private byte[]  face11;
    private byte[]  face12;
    private byte[]  face13;
    private byte[]  face14;
    private byte[]  face15;
    private ArrayList<byte[]> faces;
    private Boolean boton1;
    private Boolean boton2;
    private Boolean boton3;
    private Boolean boton4;

    public Usuario() {
    }

    public Usuario(Bundle extras) {

        setId(extras.getInt("id"));
        setCedula(extras.getString("cedula"));
        setNombre(extras.getString("nombre"));
        setEdad(extras.getString("edad"));
        setApellido(extras.getString("apellido"));
        setColor(extras.getString("color"));
        setAmigo(extras.getString("amigo"));
        setNacimiento(extras.getString("nacimiento"));
        setBoton1(extras.getBoolean("boton1"));
        setBoton2(extras.getBoolean("boton2"));
        setBoton3(extras.getBoolean("boton3"));
        setBoton4(extras.getBoolean("boton4"));

        face1 = extras.getByteArray("face1");
        face2 = extras.getByteArray("face2");
        face3 = extras.getByteArray("face3");
        face4 = extras.getByteArray("face4");
        face5 = extras.getByteArray("face5");
        face6 = extras.getByteArray("face6");
        face7 = extras.getByteArray("face7");
        face8 = extras.getByteArray("face8");
        face9 = extras.getByteArray("face9");
        face10 = extras.getByteArray("face10");
        face11 = extras.getByteArray("face11");
        face12 = extras.getByteArray("face12");
        face13 = extras.getByteArray("face13");
        face14 = extras.getByteArray("face14");
        face15 = extras.getByteArray("face15");

    }

    public void saveToIntent(Intent intent) {
        intent.putExtra("id", this.getId());
        intent.putExtra("cedula", this.getCedula());
        intent.putExtra("nombre", this.getNombre());
        intent.putExtra("apellido", this.getApellido());
        intent.putExtra("edad", this.getEdad());
        intent.putExtra("color", this.getColor());
        intent.putExtra("amigo", this.getAmigo());
        intent.putExtra("nacimiento", this.getNacimiento());
        intent.putExtra("boton1", this.getBoton1());
        intent.putExtra("boton2", this.getBoton2());
        intent.putExtra("boton3", this.getBoton3());
        intent.putExtra("boton4", this.getBoton4());

        intent.putExtra("face1", this.getFace1());
        intent.putExtra("face2", this.getFace2());
        intent.putExtra("face3", this.getFace3());
        intent.putExtra("face4", this.getFace4());
        intent.putExtra("face5", this.getFace5());
        intent.putExtra("face6", this.getFace6());
        intent.putExtra("face7", this.getFace7());
        intent.putExtra("face8", this.getFace8());
        intent.putExtra("face9", this.getFace9());
        intent.putExtra("face10", this.getFace10());
        intent.putExtra("face11", this.getFace11());
        intent.putExtra("face12", this.getFace12());
        intent.putExtra("face13", this.getFace13());
        intent.putExtra("face14", this.getFace14());
        intent.putExtra("face15", this.getFace15());
    }

    public void saveToIntent(Bundle bundle) {
        bundle.putInt("id", this.getId());
        bundle.putString("cedula", this.getCedula());
        bundle.putString("nombre", this.getNombre());
        bundle.putString("apellido", this.getApellido());
        bundle.putString("edad", this.getEdad());
        bundle.putString("color", this.getColor());
        bundle.putString("amigo", this.getAmigo());
        bundle.putString("nacimiento", this.getNacimiento());
        bundle.putBoolean("boton1", this.getBoton1());
        bundle.putBoolean("boton2", this.getBoton2());
        bundle.putBoolean("boton3", this.getBoton3());
        bundle.putBoolean("boton4", this.getBoton4());

        bundle.putByteArray("face1", this.getFace1());
        bundle.putByteArray("face2", this.getFace2());
        bundle.putByteArray("face3", this.getFace3());
        bundle.putByteArray("face4", this.getFace4());
        bundle.putByteArray("face5", this.getFace5());
        bundle.putByteArray("face6", this.getFace6());
        bundle.putByteArray("face7", this.getFace7());
        bundle.putByteArray("face8", this.getFace8());
        bundle.putByteArray("face9", this.getFace9());
        bundle.putByteArray("face10", this.getFace10());
        bundle.putByteArray("face11", this.getFace11());
        bundle.putByteArray("face12", this.getFace12());
        bundle.putByteArray("face13", this.getFace13());
        bundle.putByteArray("face14", this.getFace14());
        bundle.putByteArray("face15", this.getFace15());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
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

    public byte[] getFace6() {
        return face6;
    }

    public void setFace6(byte[] face6) {
        this.face6 = face6;
    }

    public byte[] getFace7() {
        return face7;
    }

    public void setFace7(byte[] face7) {
        this.face7 = face7;
    }

    public byte[] getFace8() {
        return face8;
    }

    public void setFace8(byte[] face8) {
        this.face8 = face8;
    }

    public byte[] getFace9() {
        return face9;
    }

    public void setFace9(byte[] face9) {
        this.face9 = face9;
    }

    public byte[] getFace10() {
        return face10;
    }

    public void setFace10(byte[] face10) {
        this.face10 = face10;
    }

    public byte[] getFace11() {
        return face11;
    }

    public void setFace11(byte[] face11) {
        this.face11 = face11;
    }

    public byte[] getFace12() {
        return face12;
    }

    public void setFace12(byte[] face12) {
        this.face12 = face12;
    }

    public byte[] getFace13() {
        return face13;
    }

    public void setFace13(byte[] face13) {
        this.face13 = face13;
    }

    public byte[] getFace14() {
        return face14;
    }

    public void setFace14(byte[] face14) {
        this.face14 = face14;
    }

    public byte[] getFace15() {
        return face15;
    }

    public void setFace15(byte[] face15) {
        this.face15 = face15;
    }

    public ArrayList<byte[]> getFaces() {
        return faces;
    }

    public void setFaces(ArrayList<byte[]> faces) {
        this.faces = faces;
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
    public int create(){
        return BDUsuario.crearUsuario(this);
    }

    public boolean changePermisos() {
        return BDUsuario.updatePermisos(this);
    }

    // Borra datos de familia.
    // Importante, si se está borrando desde una lista,
    // eliminar el objeto de la misma.
    public int delete(){
        return BDUsuario.deleteUsuario(this);
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
