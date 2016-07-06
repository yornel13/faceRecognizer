package org.opencv.javacv.facerecognition.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.asyncTask.ListarMensajes;
import org.opencv.javacv.facerecognition.asyncTask.NuevoMensaje;
import org.opencv.javacv.facerecognition.R;
import org.opencv.javacv.facerecognition.model.Mensaje;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;
import java.util.Date;

public class HelloBubblesActivity extends AppCompatActivity implements NuevoMensaje.NuevoMensajeCompletado, ListarMensajes.ListarMensajesCompletado {
	private DiscussArrayAdapter adapter;
	private ListView lv;
	private EditText editText1;
	private Usuario usuario;
	ArrayList<Mensaje> mensajes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chat);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		lv = (ListView) findViewById(R.id.listView1);

		Bundle extras = getIntent().getExtras();
		usuario = new Usuario();
		usuario.setId(extras.getInt("id"));
		usuario.setFace1(extras.getByteArray("face"));
		usuario.setNombre(extras.getString("nombre"));
		usuario.setApellido(extras.getString("apellido"));
		usuario.setColor(extras.getString("color"));
		usuario.setAmigo(extras.getString("amigo"));
		usuario.setNacimiento(extras.getString("nacimiento"));

		mensajes = new ArrayList<>();
		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.chat_list_item, usuario);

		lv.setAdapter(adapter);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					// Perform action on key press
					Mensaje mensaje = new Mensaje(usuario.getId(), usuario.getNombre(),
							editText1.getText().toString(), String.valueOf(new Date().getTime()));
					adapter.add(mensaje);
					mensajes.add(mensaje);
					editText1.setText("");
					NuevoMensaje nuevoMensaje = new NuevoMensaje(mensaje,
							HelloBubblesActivity.this, HelloBubblesActivity.this);
					nuevoMensaje.execute();
					return true;
				}
				return false;
			}
		});

		ListarMensajes listarMensajes = new ListarMensajes(this, this);
		listarMensajes.execute();
		Toast.makeText(this, "Buscando Mensajes", Toast.LENGTH_SHORT).show();

		lv.requestFocus();
		editText1.clearFocus();
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
	public void completado(Mensaje mensaje) {

	}

	@Override
	public void fallido(Mensaje mensaje) {
		mensajes.remove(mensajes.indexOf(mensaje));
	}

	@Override
	public void completado(ArrayList<Mensaje> mensajes) {
		adapter.addAll(mensajes);
	}

	@Override
	public void vacio() {
		System.out.println("sin mensajes");
	}

	@Override
	public void fallido() {
		Toast.makeText(this, "Error de conexion", Toast.LENGTH_SHORT).show();
	}
}