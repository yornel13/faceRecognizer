package org.opencv.javacv.facerecognition;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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

public class EditarUsuarioActivity extends AppCompatActivity
        implements View.OnClickListener, NuevoUsuario.NuevoUsuarioCompletado {

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
    Usuario usuario;
    private ArrayList<String> files;


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

        if (savedInstanceState == null)
            usuario = new Usuario(getIntent().getExtras());
        else
            usuario = new Usuario(savedInstanceState);

        cedula.setText(usuario.getCedula());
        nombre.setText(usuario.getNombre());
        apellido.setText(usuario.getApellido());
        edad.setText(usuario.getEdad());
        color.setText(usuario.getColor());
        amigo.setText(usuario.getEdad());
        newDate = Calendar.getInstance();
        if (savedInstanceState == null)
            newDate.setTimeInMillis(Long.valueOf(usuario.getNacimiento()));
        else
            newDate.setTimeInMillis(Long.valueOf(savedInstanceState.getString("newDate")));
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        fecha.setText(dateFormatter.format(newDate.getTime()));

        usuario.setFaces(new ArrayList<byte[]>());

        if (usuario.getFace1() != null)
            usuario.getFaces().add(usuario.getFace1());
        if (usuario.getFace2() != null)
            usuario.getFaces().add(usuario.getFace2());
        if (usuario.getFace3() != null)
            usuario.getFaces().add(usuario.getFace3());
        if (usuario.getFace4() != null)
            usuario.getFaces().add(usuario.getFace4());
        if (usuario.getFace5() != null)
            usuario.getFaces().add(usuario.getFace5());
        if (usuario.getFace6() != null)
            usuario.getFaces().add(usuario.getFace6());
        if (usuario.getFace7() != null)
            usuario.getFaces().add(usuario.getFace7());
        if (usuario.getFace8() != null)
            usuario.getFaces().add(usuario.getFace8());
        if (usuario.getFace9() != null)
            usuario.getFaces().add(usuario.getFace9());
        if (usuario.getFace10() != null)
            usuario.getFaces().add(usuario.getFace10());
        if (usuario.getFace11() != null)
            usuario.getFaces().add(usuario.getFace11());
        if (usuario.getFace12() != null)
            usuario.getFaces().add(usuario.getFace12());
        if (usuario.getFace13() != null)
            usuario.getFaces().add(usuario.getFace13());
        if (usuario.getFace14() != null)
            usuario.getFaces().add(usuario.getFace14());
        if (usuario.getFace15() != null)
            usuario.getFaces().add(usuario.getFace15());

        setImagenes();

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate.set(year, monthOfYear, dayOfMonth);
                fecha.setText(dateFormatter.format(newDate.getTime()));
                fecha.setError(null);
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(0);
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(1);
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(2);
            }
        });
        image4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(3);
            }
        });
        image5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(4);
            }
        });
        image6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(5);
            }
        });
        image7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(6);
            }
        });
        image8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(7);
            }
        });
        image9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(8);
            }
        });
        image10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(9);
            }
        });
        image11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(10);
            }
        });
        image12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(11);
            }
        });
        image13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(12);
            }
        });
        image14.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(13);
            }
        });
        image15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarFoto(14);
            }
        });
    }

    private void setImagenes() {

        image1.setImageDrawable(null);
        image2.setImageDrawable(null);
        image3.setImageDrawable(null);
        image4.setImageDrawable(null);
        image5.setImageDrawable(null);
        image6.setImageDrawable(null);
        image7.setImageDrawable(null);
        image8.setImageDrawable(null);
        image9.setImageDrawable(null);
        image10.setImageDrawable(null);
        image11.setImageDrawable(null);
        image12.setImageDrawable(null);
        image13.setImageDrawable(null);
        image14.setImageDrawable(null);
        image15.setImageDrawable(null);

        if (usuario.getFaces().size() >= 1)
            image1.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(0), 0,
                    usuario.getFaces().get(0).length));
        if (usuario.getFaces().size() >= 2)
            image2.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(1), 0,
                    usuario.getFaces().get(1).length));
        if (usuario.getFaces().size() >= 3)
            image3.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(2), 0,
                    usuario.getFaces().get(2).length));
        if (usuario.getFaces().size() >= 4)
            image4.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(3), 0,
                    usuario.getFaces().get(3).length));
        if (usuario.getFaces().size() >= 5)
            image5.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(4), 0,
                    usuario.getFaces().get(4).length));
        if (usuario.getFaces().size() >= 6)
            image6.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(5), 0,
                    usuario.getFaces().get(5).length));
        if (usuario.getFaces().size() >= 7)
            image7.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(6), 0,
                    usuario.getFaces().get(6).length));
        if (usuario.getFaces().size() >= 8)
            image8.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(7), 0,
                    usuario.getFaces().get(7).length));
        if (usuario.getFaces().size() >= 9)
            image9.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(8), 0,
                    usuario.getFaces().get(8).length));
        if (usuario.getFaces().size() >= 10)
            image10.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(9), 0,
                    usuario.getFaces().get(9).length));
        if (usuario.getFaces().size() >= 11)
            image11.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(10), 0,
                    usuario.getFaces().get(10).length));
        if (usuario.getFaces().size() >= 12)
            image12.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(11), 0,
                    usuario.getFaces().get(11).length));
        if (usuario.getFaces().size() >= 13)
            image13.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(12), 0,
                    usuario.getFaces().get(12).length));
        if (usuario.getFaces().size() >= 14)
            image14.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(13), 0,
                    usuario.getFaces().get(13).length));
        if (usuario.getFaces().size() >= 15)
            image15.setImageBitmap(BitmapFactory.decodeByteArray(usuario.getFaces().get(14), 0,
                    usuario.getFaces().get(14).length));
    }

    public void borrarFoto(final int faceIndex) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle("Borrar foto");
        alertDialog.setMessage("¿Desea borrar esta foto?");
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Borrar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        usuario.getFaces().remove(faceIndex);

                        usuario.setFace1(null);
                        usuario.setFace2(null);
                        usuario.setFace3(null);
                        usuario.setFace4(null);
                        usuario.setFace5(null);
                        usuario.setFace6(null);
                        usuario.setFace7(null);
                        usuario.setFace8(null);
                        usuario.setFace9(null);
                        usuario.setFace10(null);
                        usuario.setFace11(null);
                        usuario.setFace12(null);
                        usuario.setFace13(null);
                        usuario.setFace14(null);
                        usuario.setFace15(null);

                        if (usuario.getFaces().size() >= 1)
                            usuario.setFace1(usuario.getFaces().get(0));
                        if (usuario.getFaces().size() >= 2)
                            usuario.setFace2(usuario.getFaces().get(1));
                        if (usuario.getFaces().size() >= 3)
                            usuario.setFace3(usuario.getFaces().get(2));
                        if (usuario.getFaces().size() >= 4)
                            usuario.setFace4(usuario.getFaces().get(3));
                        if (usuario.getFaces().size() >= 5)
                            usuario.setFace5(usuario.getFaces().get(4));
                        if (usuario.getFaces().size() >= 6)
                            usuario.setFace6(usuario.getFaces().get(5));
                        if (usuario.getFaces().size() >= 7)
                            usuario.setFace7(usuario.getFaces().get(6));
                        if (usuario.getFaces().size() >= 8)
                            usuario.setFace8(usuario.getFaces().get(7));
                        if (usuario.getFaces().size() >= 9)
                            usuario.setFace9(usuario.getFaces().get(8));
                        if (usuario.getFaces().size() >= 10)
                            usuario.setFace10(usuario.getFaces().get(9));
                        if (usuario.getFaces().size() >= 11)
                            usuario.setFace11(usuario.getFaces().get(10));
                        if (usuario.getFaces().size() >= 12)
                            usuario.setFace12(usuario.getFaces().get(11));
                        if (usuario.getFaces().size() >= 13)
                            usuario.setFace13(usuario.getFaces().get(12));
                        if (usuario.getFaces().size() >= 14)
                            usuario.setFace14(usuario.getFaces().get(13));
                        if (usuario.getFaces().size() >= 15)
                            usuario.setFace15(usuario.getFaces().get(14));
                        setImagenes();
                        dialog.dismiss();
                    }
                });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 111) {
            if(resultCode == Activity.RESULT_OK){
                files = new ArrayList<>();
                files.addAll(data.getExtras().getStringArrayList("files"));
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
                    usuario.getFaces().add(bFile);
                    if (usuario.getFaces().size() == 1)
                        usuario.setFace1(bFile);
                    else if (usuario.getFaces().size() == 2)
                        usuario.setFace2(bFile);
                    else if (usuario.getFaces().size() == 3)
                        usuario.setFace3(bFile);
                    else if (usuario.getFaces().size() == 4)
                        usuario.setFace4(bFile);
                    else if (usuario.getFaces().size() == 5)
                        usuario.setFace5(bFile);
                    else if (usuario.getFaces().size() == 6)
                        usuario.setFace6(bFile);
                    else if (usuario.getFaces().size() == 7)
                        usuario.setFace7(bFile);
                    else if (usuario.getFaces().size() == 8)
                        usuario.setFace8(bFile);
                    else if (usuario.getFaces().size() == 9)
                        usuario.setFace9(bFile);
                    else if (usuario.getFaces().size() == 10)
                        usuario.setFace10(bFile);
                    else if (usuario.getFaces().size() == 11)
                        usuario.setFace11(bFile);
                    else if (usuario.getFaces().size() == 12)
                        usuario.setFace12(bFile);
                    else if (usuario.getFaces().size() == 13)
                        usuario.setFace13(bFile);
                    else if (usuario.getFaces().size() == 14)
                        usuario.setFace14(bFile);
                    else if (usuario.getFaces().size() == 15)
                        usuario.setFace15(bFile);
                }

                setImagenes();

            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        usuario.saveToIntent(outState);
        outState.putString("newDate", String.valueOf(newDate.getTimeInMillis()));
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menu_editar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_settings:
                if (usuario.getFaces().size() >= 15) {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Maximo de fotos alcanzado");
                    alertDialog.setMessage("Ya hay 15 fotos");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent intent = new Intent(this, AddFaceTrackerActivity.class);
                    intent.putExtra("count", usuario.getFaces().size());
                    startActivityForResult(intent, 111);
                }
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

            usuario.setCedula(cedula.getText().toString());
            usuario.setNombre(nombre.getText().toString());
            usuario.setApellido(apellido.getText().toString());
            usuario.setEdad(edad.getText().toString());
            usuario.setColor(color.getText().toString());
            usuario.setAmigo(amigo.getText().toString());
            usuario.setNacimiento(String.valueOf(newDate.getTimeInMillis()));

            if (usuario.getFaces().size() >= 3) {

                progressDialog = ProgressDialog.show(this, "Espere",
                        "Guardando usuario...", true);

                NuevoUsuario nuevoUsuario = new NuevoUsuario(usuario, this, this);
                nuevoUsuario.execute();

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Minimo de fotos no alcanzado");
                alertDialog.setMessage("El minimo de fotos por usuario es de 3");
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
            }
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
        Intent returnIntent = getIntent();
        usuario.saveToIntent(returnIntent);
        setResult(RESULT_OK, returnIntent);
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

