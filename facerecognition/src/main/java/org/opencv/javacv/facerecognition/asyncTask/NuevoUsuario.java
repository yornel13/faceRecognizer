package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.model.Usuario;

/**
 * Created by Yornel on 26-jun-16.
 */
public class NuevoUsuario extends AsyncTask<Void, Integer, Boolean> {

    private Usuario usuario;
    private Context context;
    private NuevoUsuarioCompletado listener;
    private int code;

    public NuevoUsuario(Usuario usuario, Context context, NuevoUsuarioCompletado listener) {
        this.usuario = usuario;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        code = usuario.create();
        if (usuario.getId() == null)
            return false;
         else
            return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if(result)
            listener.completado();
        else {
            if (code == 99)
                listener.errorCedula();
            else
                listener.fallido();
        }

    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface NuevoUsuarioCompletado {

        void completado();

        void fallido();

        void errorCedula();
    }
}