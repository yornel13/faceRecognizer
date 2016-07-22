package org.opencv.javacv.facerecognition;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.opencv.javacv.facerecognition.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class UsuarioDetectadoActivity extends AppCompatActivity {

    private RoundedImageView image;
    private TextView nombre;
    private TextView apellido;
    private TextView color;
    private TextView amigo;
    private TextView fecha;
    private TextView edad;
    private Usuario usuario;

    Calendar newDate;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_detectado);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        image = (RoundedImageView) findViewById(R.id.image);
        nombre = (TextView) findViewById(R.id.nombre);
        apellido = (TextView) findViewById(R.id.apellido);
        color = (TextView) findViewById(R.id.color);
        amigo = (TextView) findViewById(R.id.amigo);
        fecha = (TextView) findViewById(R.id.nacimiento);
        edad = (TextView) findViewById(R.id.edad);

        usuario = new Usuario(getIntent().getExtras());

        getSupportActionBar().setTitle(usuario.getNombre());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        color.setText(usuario.getColor());
        amigo.setText(usuario.getApellido());
        edad.setText(usuario.getEdad());
        image.setImageBitmap(BitmapFactory.decodeByteArray(getIntent().getExtras().getByteArray("faceDetect")
                , 0, getIntent().getExtras().getByteArray("faceDetect").length));

        newDate = Calendar.getInstance();
        newDate.setTimeInMillis(Long.valueOf(usuario.getNacimiento()));
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fecha.setText(dateFormatter.format(newDate.getTime()));
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

    public void seguiente(View view) {
        Intent intent = new Intent(this, OpcionesActivity.class);
        intent.putExtra("id", usuario.getId());
        intent.putExtra("nombre", usuario.getNombre());
        intent.putExtra("apellido", usuario.getApellido());
        intent.putExtra("color", usuario.getColor());
        intent.putExtra("amigo", usuario.getAmigo());
        intent.putExtra("nacimiento", usuario.getNacimiento());
        intent.putExtra("face", usuario.getFace1());
        intent.putExtra("boton1", usuario.getBoton1());
        intent.putExtra("boton2", usuario.getBoton2());
        intent.putExtra("boton3", usuario.getBoton3());
        intent.putExtra("boton4", usuario.getBoton4());
        startActivity(intent);
    }
}
