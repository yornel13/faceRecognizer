package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.model.Usuario;

/**
 * Created by Yornel on 29-jun-16.
 */
public class UpdateUsuarioPermisos extends AsyncTask<Void, Integer, Boolean> {
    private Usuario usuario;
    private Context context;
    private UpdateUsuarioCompletado listener;

    public UpdateUsuarioPermisos(Usuario usuario, Context context, UpdateUsuarioCompletado listener) {
        this.usuario = usuario;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        if (usuario.changePermisos())
            return true;
        else
            return false;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result)
            listener.completado(usuario);
        else
            listener.fallido();

    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface UpdateUsuarioCompletado {

        void completado(Usuario usuario);

        void fallido();
    }
}