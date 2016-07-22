package org.opencv.javacv.facerecognition;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.asyncTask.BuscaUsuarioPorCedula;
import org.opencv.javacv.facerecognition.model.Usuario;

public class MainActivity extends AppCompatActivity implements BuscaUsuarioPorCedula.BuscarUsuarioCompletado {

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void inTrain(View view) {
        startActivity(new Intent(this, TrainFaceTrackerActivity.class));
    }

    public void inDetection(View view) {

        final Dialog record = new Dialog(this);

        record.setContentView(R.layout.cedula_dialog);
        record.setTitle("Ingrese su cedula");

        Button btnRecord = (Button) record.findViewById(R.id.btnLogin);
        final Button btnCancel = (Button) record.findViewById(R.id.btnCancel);
        final EditText cedula = (EditText) record.findViewById(R.id.cedula);

        btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!cedula.getText().toString().isEmpty()) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(cedula.getWindowToken(), 0);
                    record.dismiss();

                    progressDialog = ProgressDialog.show(MainActivity.this, "Espere",
                            "Cargando información...", true);

                    BuscaUsuarioPorCedula buscaUsuarioPorCedula = new BuscaUsuarioPorCedula(
                            MainActivity.this, MainActivity.this, cedula.getText().toString());
                    buscaUsuarioPorCedula.execute();
                } else {
                    Toast.makeText(MainActivity.this, "ingrese la cedula",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                record.dismiss();
            }
        });

        record.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.admin:
                login();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void login(){
        // Create Object of Dialog class
        final Dialog login = new Dialog(this);

        login.setContentView(R.layout.login_dialog);
        login.setTitle("Ingrese su usuario");

        Button btnLogin = (Button) login.findViewById(R.id.btnLogin);
        final Button btnCancel = (Button) login.findViewById(R.id.btnCancel);
        final EditText user = (EditText) login.findViewById(R.id.txtUsername);
        final EditText pass = (EditText) login.findViewById(R.id.txtPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user.getText().toString() != null && pass.getText().toString() != null) {
                    if (user.getText().toString().equals("admin")
                            && user.getText().toString().equals("admin")) {
                        startActivity(new Intent(MainActivity.this, UsuariosActivity.class));
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(user.getWindowToken(), 0);
                        imm.hideSoftInputFromWindow(pass.getWindowToken(), 0);
                        login.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "Datos Invalidos",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Los campos no puede estar vacios",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.dismiss();
            }
        });

        login.show();
    }

    @Override
    public void buscarUsuarioCompletado(final Usuario usuario) {
        progressDialog.dismiss();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, RecognizerFaceTrackerActivity.class);
                usuario.saveToIntent(intent);
                startActivity(intent);
            }
        });

    }

    @Override
    public void buscarUsuarioNoEncontrado() {
        runOnUiThread(new Runnable() {
              @Override
              public void run() {
                  Toast.makeText(MainActivity.this, "Cedula no encontrada", Toast.LENGTH_SHORT).show();
              }
        });
        progressDialog.dismiss();
    }

    @Override
    public void buscarUsuarioFallido() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
    }
}
