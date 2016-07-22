package org.opencv.javacv.facerecognition;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.asyncTask.UpdateUsuarioPermisos;
import org.opencv.javacv.facerecognition.model.Usuario;

public class PermisosActivity extends AppCompatActivity implements UpdateUsuarioPermisos.UpdateUsuarioCompletado {

    CheckBox checkBox1;
    CheckBox checkBox2;
    CheckBox checkBox3;
    CheckBox checkBox4;
    private Usuario usuario;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permisos);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        checkBox1 = (CheckBox) findViewById(R.id.check_1);
        checkBox2 = (CheckBox) findViewById(R.id.check_2);
        checkBox3 = (CheckBox) findViewById(R.id.check_3);
        checkBox4 = (CheckBox) findViewById(R.id.check_4);

        Bundle extras = getIntent().getExtras();
        usuario = new Usuario(extras);

        getSupportActionBar().setTitle(usuario.getNombre() + " " + usuario.getApellido());

        checkBox1.setChecked(usuario.getBoton1());
        checkBox2.setChecked(usuario.getBoton2());
        checkBox3.setChecked(usuario.getBoton3());
        checkBox4.setChecked(usuario.getBoton4());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void onClick(View view) {
        usuario.setBoton2(checkBox2.isChecked());
        usuario.setBoton3(checkBox3.isChecked());
        usuario.setBoton4(checkBox4.isChecked());
        progressDialog = ProgressDialog.show(this, "Espere", "Actualizando permisos...");
        UpdateUsuarioPermisos updateUsuarioPermisos = new UpdateUsuarioPermisos(usuario, this, this);
        updateUsuarioPermisos.execute();

    }

    @Override
    public void completado(Usuario usuario) {
        progressDialog.dismiss();
        Toast.makeText(this, "Permisos actualizados con exito.", Toast.LENGTH_SHORT).show();
        Intent returnIntent = getIntent();
        usuario.saveToIntent(returnIntent);
        setResult(RESULT_OK, returnIntent);
        finish();
    }

    @Override
    public void fallido() {
        progressDialog.dismiss();
        Toast.makeText(this, "Fallo de actualizacion.", Toast.LENGTH_SHORT).show();
    }
}
