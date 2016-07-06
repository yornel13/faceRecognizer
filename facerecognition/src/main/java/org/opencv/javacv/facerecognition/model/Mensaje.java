package org.opencv.javacv.facerecognition.model;

import org.opencv.javacv.facerecognition.db.BDMensaje;

/**
 * Created by Yornel on 26-jun-16.
 */
public class Mensaje {

    private int id;
    private Integer usuarioId;
    private String usuarioNombre;
    private String mensaje;
    private String fecha;

    public Mensaje(Integer usuarioId, String usuarioNombre, String mensaje, String fecha) {
        this.usuarioId = usuarioId;
        this.usuarioNombre = usuarioNombre;
        this.mensaje = mensaje;
        this.fecha = fecha;
    }

    public Mensaje() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void save(){
        BDMensaje.saveMensaje(this);
    }
}
