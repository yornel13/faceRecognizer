package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;

import org.opencv.javacv.facerecognition.model.Usuario;

/**
 * Created by Yornel on 20-jul-16.
 */
public class BorrarUsuario extends AsyncTask<Void, Integer, Integer> {

    private BorrarUsuarioCompletado listener;
    private Usuario usuario;
    private Context context;

    public BorrarUsuario(BorrarUsuarioCompletado listener, Usuario usuario, Context context) {
        this.listener = listener;
        this.usuario = usuario;
        this.context = context;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        int code = usuario.delete();
        if (code == 0) {
            listener.borradoCompletado();
        } else if (code == 1) {
            listener.borradoFallido();
        }

        return null;
    }

    public interface BorrarUsuarioCompletado {

        void borradoCompletado();

        void borradoFallido();
    }
}
