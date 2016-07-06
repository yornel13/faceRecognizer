package org.opencv.javacv.facerecognition;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.asyncTask.NuevoUsuario;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener, NuevoUsuario.NuevoUsuarioCompletado {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView nombre;
    private TextView apellido;
    private TextView edad;
    private TextView color;
    private TextView amigo;
    private TextView fecha;
    private RelativeLayout containerFecha;
    private Button button;
    Calendar newDate;
    private ProgressDialog progressDialog;
    ArrayList<File> files;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        image5 = (ImageView) findViewById(R.id.image5);

        nombre = (TextView) findViewById(R.id.input_nombre);
        apellido = (TextView) findViewById(R.id.input_apellido);
        edad = (TextView) findViewById(R.id.input_edad);
        color = (TextView) findViewById(R.id.input_color);
        amigo = (TextView) findViewById(R.id.input_amigo);
        fecha = (TextView) findViewById(R.id.input_fecha);
        button = (Button) findViewById(R.id.button);
        containerFecha = (RelativeLayout) findViewById(R.id.container_fecha);
        button.setOnClickListener(this);
        containerFecha.setOnClickListener(this);

        files = new ArrayList<>();
        files.add( new File(getIntent().getExtras().getString("url1")));
        files.add( new File(getIntent().getExtras().getString("url2")));
        files.add( new File(getIntent().getExtras().getString("url3")));
        files.add( new File(getIntent().getExtras().getString("url4")));
        files.add( new File(getIntent().getExtras().getString("url5")));

        image1.setImageBitmap(BitmapFactory.decodeFile(files.get(0).getAbsolutePath()));
        image2.setImageBitmap(BitmapFactory.decodeFile(files.get(1).getAbsolutePath()));
        image3.setImageBitmap(BitmapFactory.decodeFile(files.get(2).getAbsolutePath()));
        image4.setImageBitmap(BitmapFactory.decodeFile(files.get(3).getAbsolutePath()));
        image5.setImageBitmap(BitmapFactory.decodeFile(files.get(4).getAbsolutePath()));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fecha.setText(dateFormatter.format(newDate.getTime()));
                fecha.setError(null);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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

    @Override
    public void onClick(View view) {
        if (view == containerFecha) {
            hide_keyboard();
            datePickerDialog.show();
        } else if (view == button) {
            save();
        }
    }

    public void save() {
        if (nombre.getText().toString().isEmpty()) {
            nombre.setError("Debes ingresar un nombre");
            nombre.requestFocus();
        } else if (apellido.getText().toString().isEmpty()) {
            apellido.setError("Debes ingresar un apellido");
            apellido.requestFocus();
        } else if (edad.getText().toString().isEmpty()) {
            edad.setError("Debes ingresar tu edad");
            edad.requestFocus();
        } else if (color.getText().toString().isEmpty()) {
            color.setError("¿Cual es tu color favorito?");
            color.requestFocus();
        } else if (amigo.getText().toString().isEmpty()) {
            amigo.setError("¿No tienes amigos?");
            amigo.requestFocus();
        } else if (fecha.getText().toString().isEmpty()) {
            fecha.setError("Selecciona tu fecha de nacimiento");
            fecha.requestFocus();
            hide_keyboard();
        } else {
            hide_keyboard();

            Usuario usuario = new Usuario();
            usuario.setNombre(nombre.getText().toString());
            usuario.setApellido(apellido.getText().toString());
            usuario.setEdad(edad.getText().toString());
            usuario.setColor(color.getText().toString());
            usuario.setAmigo(amigo.getText().toString());
            usuario.setNacimiento(String.valueOf(newDate.getTimeInMillis()));
            {
                File file  = files.get(0);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                usuario.setFace1(bFile);
            }
            {
                File file  = files.get(1);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                usuario.setFace2(bFile);
            }
            {
                File file  = files.get(2);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                usuario.setFace3(bFile);
            }
            {
                File file  = files.get(3);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                usuario.setFace4(bFile);
            }
            {
                File file  = files.get(4);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                usuario.setFace5(bFile);
            }

            progressDialog = ProgressDialog.show(this, "Espere",
                    "Guardando usuario...", true);

            NuevoUsuario nuevoUsuario = new NuevoUsuario(usuario, this, this);
            nuevoUsuario.execute();
        }
    }

    private void hide_keyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(nombre.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(apellido.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(edad.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(color.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(amigo.getWindowToken(), 0);
    }

    @Override
    public void completado() {
        progressDialog.dismiss();
        Toast.makeText(this, "Usuario Guardado", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    public void fallido() {
        progressDialog.dismiss();
        Toast.makeText(this, "Error de guardado", Toast.LENGTH_LONG).show();
    }
}

