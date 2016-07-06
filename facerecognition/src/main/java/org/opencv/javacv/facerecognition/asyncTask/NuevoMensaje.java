package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.model.Mensaje;

/**
 * Created by Yornel on 26-jun-16.
 */
public class NuevoMensaje extends AsyncTask<Void, Integer, Boolean> {

    private Mensaje mensaje;
    private Context context;
    private NuevoMensajeCompletado listener;

    public NuevoMensaje(Mensaje mensaje, Context context, NuevoMensajeCompletado listener) {
        this.mensaje = mensaje;
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        mensaje.save();
        if (mensaje.getId() == null)
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
            listener.completado(mensaje);
        else
            listener.fallido(mensaje);

    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido(mensaje);
    }

    public interface NuevoMensajeCompletado {

        void completado(Mensaje mensaje);

        void fallido(Mensaje mensaje);
    }
}