package org.opencv.javacv.facerecognition.asyncTask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.RecognizerFaceTrackerActivity;
import org.opencv.javacv.facerecognition.UsuarioRecognizerNew;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;

/**
 * Created by Yornel on 26-jun-16.
 */
public class CargarUsuarioParaReconocimiento extends AsyncTask<Void, Integer, Integer> {

    private Usuario usuario;
    private Context context;
    private CargarUsuarioCompletado listener;

    public CargarUsuarioParaReconocimiento(Context context, CargarUsuarioCompletado listener, Usuario usuario) {
        this.context = context;
        this.listener = listener;
        this.usuario = usuario;
    }

    @Override
    protected Integer doInBackground(Void... params) {

        usuario.setFaces(new ArrayList<byte[]>());

        if (usuario.getFace1() != null)
            usuario.getFaces().add(usuario.getFace1());
        if (usuario.getFace2() != null)
            usuario.getFaces().add(usuario.getFace2());
        if (usuario.getFace3() != null)
            usuario.getFaces().add(usuario.getFace3());
        if (usuario.getFace4() != null)
            usuario.getFaces().add(usuario.getFace4());
        if (usuario.getFace5() != null)
            usuario.getFaces().add(usuario.getFace5());
        if (usuario.getFace6() != null)
            usuario.getFaces().add(usuario.getFace6());
        if (usuario.getFace7() != null)
            usuario.getFaces().add(usuario.getFace7());
        if (usuario.getFace8() != null)
            usuario.getFaces().add(usuario.getFace8());
        if (usuario.getFace9() != null)
            usuario.getFaces().add(usuario.getFace9());
        if (usuario.getFace10() != null)
            usuario.getFaces().add(usuario.getFace10());
        if (usuario.getFace11() != null)
            usuario.getFaces().add(usuario.getFace11());
        if (usuario.getFace12() != null)
            usuario.getFaces().add(usuario.getFace12());
        if (usuario.getFace13() != null)
            usuario.getFaces().add(usuario.getFace13());
        if (usuario.getFace14() != null)
            usuario.getFaces().add(usuario.getFace14());
        if (usuario.getFace15() != null)
            usuario.getFaces().add(usuario.getFace15());

        try {
            ((RecognizerFaceTrackerActivity) context).personRecognizer = new UsuarioRecognizerNew(usuario);
            ((RecognizerFaceTrackerActivity) context).personRecognizer.train();
        } catch (Exception e) {
            return 1;
        }

        return 0;

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
                listener.completado();
                break;
            case 1:
                listener.fallido();
                break;
        }
    }

    @Override
    protected void onCancelled() {
        Toast.makeText(context, "Tarea cancelada!",
                Toast.LENGTH_SHORT).show();
        listener.fallido();
    }

    public interface CargarUsuarioCompletado {

        void completado();

        void fallido();
    }
}