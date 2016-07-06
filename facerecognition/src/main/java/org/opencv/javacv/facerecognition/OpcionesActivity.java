package org.opencv.javacv.facerecognition;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.chat.HelloBubblesActivity;
import org.opencv.javacv.facerecognition.model.Usuario;

public class OpcionesActivity extends AppCompatActivity implements View.OnClickListener {

    private Usuario usuario;

    Button button1;
    Button button2;
    Button button3;
    Button button4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        button4 = (Button) findViewById(R.id.button4);

        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
        button3.setOnClickListener(this);
        button4.setOnClickListener(this);

        Bundle extras = getIntent().getExtras();
        usuario = new Usuario();
        usuario.setId(extras.getInt("id"));
        usuario.setNombre(extras.getString("nombre"));
        usuario.setApellido(extras.getString("apellido"));
        usuario.setColor(extras.getString("color"));
        usuario.setAmigo(extras.getString("amigo"));
        usuario.setNacimiento(extras.getString("nacimiento"));
        usuario.setFace1(extras.getByteArray("face"));
        usuario.setBoton1(extras.getBoolean("boton1"));
        usuario.setBoton2(extras.getBoolean("boton2"));
        usuario.setBoton3(extras.getBoolean("boton3"));
        usuario.setBoton4(extras.getBoolean("boton4"));

        button1.setEnabled(usuario.getBoton1());
        button2.setEnabled(usuario.getBoton2());
        button3.setEnabled(usuario.getBoton3());
        button4.setEnabled(usuario.getBoton4());
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

    public void chat() {
        Intent intent = new Intent(this, HelloBubblesActivity.class);
        intent.putExtra("id", usuario.getId());
        intent.putExtra("nombre", usuario.getNombre());
        intent.putExtra("apellido", usuario.getApellido());
        intent.putExtra("color", usuario.getColor());
        intent.putExtra("amigo", usuario.getAmigo());
        intent.putExtra("nacimiento", usuario.getNacimiento());
        intent.putExtra("face", usuario.getFace1());
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                chat();
                break;
            case R.id.button2:
                Toast.makeText(this, "Boton Hrm esta habilitado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button3:
                Toast.makeText(this, "Boton Frm esta habilitado", Toast.LENGTH_SHORT).show();
                break;
            case R.id.button4:
                Toast.makeText(this, "Boton Mrp esta habilitado", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
