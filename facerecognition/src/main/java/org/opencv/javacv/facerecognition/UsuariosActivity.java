package org.opencv.javacv.facerecognition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.opencv.javacv.facerecognition.asyncTask.ListarUsuariosParaPermisos;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity implements ListarUsuariosParaPermisos.ListarUsuariosParaPermisosCompletado {

    ArrayList<Usuario> usuarios;

    ListView listView;
    private ProgressDialog progressDialog;
    private ArrayAdapter<Usuario> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = (ListView) findViewById(R.id.list_view);

        usuarios = new ArrayList<>();

        progressDialog = ProgressDialog.show(this, "Espere", "Obteniendo usuarios...");
        ListarUsuariosParaPermisos listarUsuariosParaPermisos = new ListarUsuariosParaPermisos(this, this);
        listarUsuariosParaPermisos.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if(resultCode == Activity.RESULT_OK){
                Usuario usuario = new Usuario();
                usuario.setId(data.getIntExtra("id", 0));
                usuario.setNombre(data.getStringExtra("nombre"));
                usuario.setEdad(data.getStringExtra("edad"));
                usuario.setApellido(data.getStringExtra("apellido"));
                usuario.setColor(data.getStringExtra("color"));
                usuario.setAmigo(data.getStringExtra("amigo"));
                usuario.setNacimiento(data.getStringExtra("nacimiento"));
                usuario.setFace1(data.getByteArrayExtra("face"));
                usuario.setBoton1(data.getBooleanExtra("boton1", true));
                usuario.setBoton2(data.getBooleanExtra("boton2", true));
                usuario.setBoton3(data.getBooleanExtra("boton3", true));
                usuario.setBoton4(data.getBooleanExtra("boton4", true));
                int position = usuarios.indexOf(Usuario.getUsuario(usuarios, usuario.getId()));
                usuarios.set(position, usuario);
                adapter.remove(adapter.getItem(position));
                adapter.insert(usuario, position);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
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
    public void completado(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
        adapter = new AdapterUsuarios(this, usuarios);
        listView.setAdapter(adapter);
        progressDialog.dismiss();
        if (usuarios.isEmpty()) {
            Toast.makeText(this, "Sin usuarios", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void fallido() {
        Toast.makeText(this, "Error de conexion", Toast.LENGTH_SHORT).show();
    }

    public class AdapterUsuarios extends ArrayAdapter<Usuario> {
        private final Context context;
        private final ArrayList<Usuario> usuarios;

        public AdapterUsuarios(Context context, ArrayList<Usuario> usuarios) {
            super(context, -1, usuarios);
            this.context = context;
            this.usuarios = usuarios;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.usuario_item_row, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            final Usuario usuario = usuarios.get(position);

            rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PermisosActivity.class);
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
                    ((UsuariosActivity) context).startActivityForResult(intent, 999);
                }
            });

            textView.setText(usuario.getNombre() + " " + usuario.getApellido());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFace1(), 0,
                    usuario.getFace1().length));

            return rowView;
        }

    }


}
