package org.opencv.javacv.facerecognition;

import android.content.Intent;
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

        Bundle extras = getIntent().getExtras();
        usuario = new Usuario();
        usuario.setId(extras.getInt("id"));
        usuario.setFace1(extras.getByteArray("face"));
        usuario.setNombre(extras.getString("nombre"));
        usuario.setEdad(extras.getString("edad"));
        usuario.setApellido(extras.getString("apellido"));
        usuario.setColor(extras.getString("color"));
        usuario.setAmigo(extras.getString("amigo"));
        usuario.setNacimiento(extras.getString("nacimiento"));
        usuario.setBoton1(extras.getBoolean("boton1"));
        usuario.setBoton2(extras.getBoolean("boton2"));
        usuario.setBoton3(extras.getBoolean("boton3"));
        usuario.setBoton4(extras.getBoolean("boton4"));

        getSupportActionBar().setTitle(usuario.getNombre());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        color.setText(usuario.getColor());
        amigo.setText(usuario.getApellido());
        edad.setText(usuario.getEdad());

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
