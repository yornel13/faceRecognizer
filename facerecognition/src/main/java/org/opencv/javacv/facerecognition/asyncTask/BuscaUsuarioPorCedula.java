package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import org.opencv.javacv.facerecognition.db.BDUsuario;
import org.opencv.javacv.facerecognition.model.Usuario;

/**
 * Created by Yornel on 20-jul-16.
 */
public class BuscaUsuarioPorCedula extends AsyncTask<Void, Integer, Integer> {


    private Usuario usuario;
    private Context context;
    private String cedula;
    private BuscarUsuarioCompletado listener;

    public BuscaUsuarioPorCedula(Context context, BuscarUsuarioCompletado listener, String cedula) {
        this.context = context;
        this.listener = listener;
        this.cedula = cedula;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        usuario = BDUsuario.getUsuarioByCedula(cedula);
         if (usuario != null) {
             if (usuario.getId() != null)
                listener.buscarUsuarioCompletado(usuario);
             else
                listener.buscarUsuarioNoEncontrado();
         } else {
             listener.buscarUsuarioFallido();
         }

        return null;
    }

    public interface BuscarUsuarioCompletado {

         void buscarUsuarioCompletado(Usuario usuario);

        void buscarUsuarioNoEncontrado();

        void buscarUsuarioFallido();

    }
}
