package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.db.BDUsuario;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yornel on 26-jun-16.
 */
public class ListarUsuarios extends AsyncTask<Void, Integer, Integer> {

    private ArrayList<Usuario> usuarios;
    private Context context;
    private ListarUsuariosCompletado listener;

    public ListarUsuarios(Context context, ListarUsuariosCompletado listener) {
        this.context = context;
        this.listener = listener;
        usuarios = new ArrayList<>();
    }

    @Override
    protected Integer doInBackground(Void... params) {

        List<Usuario>  users = BDUsuario.getTodosLosUsuario();

        if (users == null) {
            return 0;
        }

        usuarios.addAll(users);

        if (usuarios.isEmpty())
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
                //((RecognizerFaceTrackerActivity) context).personRecognizer = new UsuarioRecognizer(usuarios);
                //((RecognizerFaceTrackerActivity) context).personRecognizer.train();
                listener.completado();
                break;
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface ListarUsuariosCompletado {

        void completado();

        void vacio();

        void fallido();
    }
}