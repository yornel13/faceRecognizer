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

public class NuevoUsuarioActivity extends AppCompatActivity implements View.OnClickListener, NuevoUsuario.NuevoUsuarioCompletado {

    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image4;
    private ImageView image5;
    private ImageView image6;
    private ImageView image7;
    private ImageView image8;
    private ImageView image9;
    private ImageView image10;
    private ImageView image11;
    private ImageView image12;
    private ImageView image13;
    private ImageView image14;
    private ImageView image15;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    private TextView cedula;
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
    ArrayList<String> files;


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
        image6 = (ImageView) findViewById(R.id.image6);
        image7 = (ImageView) findViewById(R.id.image7);
        image8 = (ImageView) findViewById(R.id.image8);
        image9 = (ImageView) findViewById(R.id.image9);
        image10 = (ImageView) findViewById(R.id.image10);
        image11 = (ImageView) findViewById(R.id.image11);
        image12 = (ImageView) findViewById(R.id.image12);
        image13 = (ImageView) findViewById(R.id.image13);
        image14 = (ImageView) findViewById(R.id.image14);
        image15 = (ImageView) findViewById(R.id.image15);

        cedula = (TextView) findViewById(R.id.input_cedula);
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
        files.addAll(getIntent().getExtras().getStringArrayList("files"));

        System.out.println(files.size());

        for (String string: files) {
            System.out.println(string);
        }

        if (files.size() >= 1)
            image1.setImageBitmap(BitmapFactory.decodeFile(files.get(0)));
        if (files.size() >= 2)
            image2.setImageBitmap(BitmapFactory.decodeFile(files.get(1)));
        if (files.size() >= 3)
            image3.setImageBitmap(BitmapFactory.decodeFile(files.get(2)));
        if (files.size() >= 4)
            image4.setImageBitmap(BitmapFactory.decodeFile(files.get(3)));
        if (files.size() >= 5)
            image5.setImageBitmap(BitmapFactory.decodeFile(files.get(4)));
        if (files.size() >= 6)
            image6.setImageBitmap(BitmapFactory.decodeFile(files.get(5)));
        if (files.size() >= 7)
            image7.setImageBitmap(BitmapFactory.decodeFile(files.get(6)));
        if (files.size() >= 8)
            image8.setImageBitmap(BitmapFactory.decodeFile(files.get(7)));
        if (files.size() >= 9)
            image9.setImageBitmap(BitmapFactory.decodeFile(files.get(8)));
        if (files.size() >= 10)
            image10.setImageBitmap(BitmapFactory.decodeFile(files.get(9)));
        if (files.size() >= 11)
            image11.setImageBitmap(BitmapFactory.decodeFile(files.get(10)));
        if (files.size() >= 12)
            image12.setImageBitmap(BitmapFactory.decodeFile(files.get(11)));
        if (files.size() >= 13)
            image13.setImageBitmap(BitmapFactory.decodeFile(files.get(12)));
        if (files.size() >= 14)
            image14.setImageBitmap(BitmapFactory.decodeFile(files.get(13)));
        if (files.size() >= 15)
            image15.setImageBitmap(BitmapFactory.decodeFile(files.get(14)));

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
        if (cedula.getText().toString().isEmpty()) {
            cedula.setError("Debes ingresar tu cedula");
            cedula.requestFocus();
        } else if (nombre.getText().toString().isEmpty()) {
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
            usuario.setCedula(cedula.getText().toString());
            usuario.setNombre(nombre.getText().toString());
            usuario.setApellido(apellido.getText().toString());
            usuario.setEdad(edad.getText().toString());
            usuario.setColor(color.getText().toString());
            usuario.setAmigo(amigo.getText().toString());
            usuario.setNacimiento(String.valueOf(newDate.getTimeInMillis()));

            for (String fileString: files) {
                File file  = new File(fileString);
                byte[] bFile = new byte[(int) file.length()];
                try {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    //convert file into array of bytes
                    fileInputStream.read(bFile);
                    fileInputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if ((files.indexOf(fileString) + 1) == 1)
                    usuario.setFace1(bFile);
                else if ((files.indexOf(fileString) + 1) == 2)
                    usuario.setFace2(bFile);
                else if ((files.indexOf(fileString) + 1) == 3)
                    usuario.setFace3(bFile);
                else if ((files.indexOf(fileString) + 1) == 4)
                    usuario.setFace4(bFile);
                else if ((files.indexOf(fileString) + 1) == 5)
                    usuario.setFace5(bFile);
                else if ((files.indexOf(fileString) + 1) == 6)
                    usuario.setFace6(bFile);
                else if ((files.indexOf(fileString) + 1) == 7)
                    usuario.setFace7(bFile);
                else if ((files.indexOf(fileString) + 1) == 8)
                    usuario.setFace8(bFile);
                else if ((files.indexOf(fileString) + 1) == 9)
                    usuario.setFace9(bFile);
                else if ((files.indexOf(fileString) + 1) == 10)
                    usuario.setFace10(bFile);
                else if ((files.indexOf(fileString) + 1) == 11)
                    usuario.setFace11(bFile);
                else if ((files.indexOf(fileString) + 1) == 12)
                    usuario.setFace12(bFile);
                else if ((files.indexOf(fileString) + 1) == 13)
                    usuario.setFace13(bFile);
                else if ((files.indexOf(fileString) + 1) == 14)
                    usuario.setFace14(bFile);
                else if ((files.indexOf(fileString) + 1) == 15)
                    usuario.setFace15(bFile);

            }

            progressDialog = ProgressDialog.show(this, "Espere",
                    "Guardando usuario...", true);

            NuevoUsuario nuevoUsuario = new NuevoUsuario(usuario, this, this);
            nuevoUsuario.execute();
        }
    }

    private void hide_keyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cedula.getWindowToken(), 0);
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

    @Override
    public void errorCedula() {
        progressDialog.dismiss();
        Toast.makeText(this, "Cedula en uso", Toast.LENGTH_LONG).show();
    }
}

