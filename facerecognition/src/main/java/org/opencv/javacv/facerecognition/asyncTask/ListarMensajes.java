package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.db.BDMensaje;
import org.opencv.javacv.facerecognition.model.Mensaje;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yornel on 26-jun-16.
 */
public class ListarMensajes extends AsyncTask<Void, Integer, Integer> {

    private ArrayList<Mensaje> mensajes;
    private Context context;
    private ListarMensajesCompletado listener;

    public ListarMensajes(Context context, ListarMensajesCompletado listener) {
        this.context = context;
        this.listener = listener;
        mensajes = new ArrayList<>();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        List<Mensaje>  mensajesList = BDMensaje.getTodosLosMensajes();

        if (mensajesList == null) {
            return 0;
        }

        mensajes.addAll(mensajesList);

        if (mensajes.isEmpty())
            return 1;
        else
            return 2;

    }

    @Override
    protected void onProgressUpdate(Integer... values) {

    }

    @Override
    protected void onPreExecute() {

    }

    @Override
    protected void onPostExecute(Integer result) {

        switch (result) {
            case 0:
                listener.fallido();
                break;
            case 1:
                listener.vacio();
                break;
            case 2:
                listener.completado(mensajes);
                break;
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface ListarMensajesCompletado {

        void completado(ArrayList<Mensaje> mensajes);

        void vacio();

        void fallido();
    }
}