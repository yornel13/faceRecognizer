package org.opencv.javacv.facerecognition;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

import org.opencv.javacv.facerecognition.asyncTask.BorrarUsuario;
import org.opencv.javacv.facerecognition.asyncTask.ListarUsuariosParaPermisos;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;

public class UsuariosActivity extends AppCompatActivity implements
        ListarUsuariosParaPermisos.ListarUsuariosParaPermisosCompletado, BorrarUsuario.BorrarUsuarioCompletado {

    ArrayList<Usuario> usuarios;

    ListView listView;
    private ProgressDialog progressDialog;
    private ArrayAdapter<Usuario> adapter;
    Integer indexUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        listView = (ListView) findViewById(R.id.list_view);

        if (savedInstanceState != null) {
            indexUsuario = savedInstanceState.getInt("indexUsuario");
        }

        usuarios = new ArrayList<>();

        progressDialog = ProgressDialog.show(this, "Espere", "Obteniendo usuarios...");
        ListarUsuariosParaPermisos listarUsuariosParaPermisos = new ListarUsuariosParaPermisos(this, this);
        listarUsuariosParaPermisos.execute();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt("indexUsuario", indexUsuario);
        super.onSaveInstanceState(outState);
    }

    public void borrarUsuario(Usuario usuario) {
        progressDialog = ProgressDialog.show(this, "Espere", "Borrando usuario...");
        BorrarUsuario borrarUsuario = new BorrarUsuario(this , usuario, this);
        borrarUsuario.execute();
    }

    public void hacerBorrado(final Usuario usuario) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Opciones de Usuario");
        alertDialog.setMessage("Puedes editar o borrar este usuario");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "editar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UsuariosActivity.this, EditarUsuarioActivity.class);
                        usuario.saveToIntent(intent);
                        startActivityForResult(intent, 888);
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "borrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        borrarUsuario(usuario);
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 999) {
            if(resultCode == Activity.RESULT_OK){
                Usuario usuario = new Usuario(data.getExtras());
                int position = usuarios.indexOf(Usuario.getUsuario(usuarios, usuario.getId()));
                usuarios.set(position, usuario);
                adapter.remove(adapter.getItem(position));
                adapter.insert(usuario, position);

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        } else if (requestCode == 888) {
            if(resultCode == Activity.RESULT_OK){
                Usuario usuario = new Usuario(data.getExtras());
                usuarios.set(indexUsuario, usuario);
                adapter.remove(adapter.getItem(indexUsuario));
                adapter.insert(usuario, indexUsuario);
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

    @Override
    public void borradoCompletado() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapter.remove(usuarios.get(indexUsuario));
                Toast.makeText(UsuariosActivity.this, "Borrado completado", Toast.LENGTH_SHORT).show();
            }
        });
        progressDialog.dismiss();
    }

    @Override
    public void borradoFallido() {
        Toast.makeText(this, "Error de borrado", Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
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
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.usuario_item_row, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            final Usuario usuario = usuarios.get(position);

            rowView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    indexUsuario = position;
                    Intent intent = new Intent(context, PermisosActivity.class);
                    usuario.saveToIntent(intent);
                    ((UsuariosActivity) context).startActivityForResult(intent, 999);
                }
            });

            rowView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    indexUsuario = position;
                    hacerBorrado(usuario);
                    return false;
                }

            });

            textView.setText(usuario.getNombre() + " " + usuario.getApellido());
            imageView.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFace1(), 0,
                    usuario.getFace1().length));

            return rowView;
        }

    }


}
