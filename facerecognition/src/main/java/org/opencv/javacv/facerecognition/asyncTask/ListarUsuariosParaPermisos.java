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
public class ListarUsuariosParaPermisos extends AsyncTask<Void, Integer, Integer> {

    private ArrayList<Usuario> usuarios;
    private Context context;
    private ListarUsuariosParaPermisosCompletado listener;

    public ListarUsuariosParaPermisos(Context context, ListarUsuariosParaPermisosCompletado listener) {
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

        return 1;
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
                listener.completado(usuarios);
                break;
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface ListarUsuariosParaPermisosCompletado {

        void completado(ArrayList<Usuario> usuarios);

        void fallido();
    }
}