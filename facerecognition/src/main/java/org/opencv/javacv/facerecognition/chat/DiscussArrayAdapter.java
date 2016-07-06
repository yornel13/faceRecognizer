package org.opencv.javacv.facerecognition.chat;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.opencv.javacv.facerecognition.R;
import org.opencv.javacv.facerecognition.model.Mensaje;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DiscussArrayAdapter extends ArrayAdapter<Mensaje> {

	private TextView comment;
	private TextView name;
	private LinearLayout container;
	private List<Mensaje> mensajes = new ArrayList<>();
	private LinearLayout wrapper;
	private Usuario usuario;

	@Override
	public void add(Mensaje object) {
		mensajes.add(object);
		super.add(object);
	}

	@Override
	public void addAll(Collection<? extends Mensaje> collection) {
		mensajes.addAll(collection);
		super.addAll(collection);
	}

	public DiscussArrayAdapter(Context context, int textViewResourceId, Usuario usuario) {
		super(context, textViewResourceId);
		this.usuario = usuario;
	}

	public int getCount() {
		return this.mensajes.size();
	}

	public Mensaje getItem(int index) {
		return this.mensajes.get(index);
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.chat_list_item, parent, false);
		}

		wrapper = (LinearLayout) row.findViewById(R.id.wrapper);

		Mensaje mensaje = getItem(position);

		comment = (TextView) row.findViewById(R.id.comment);
		name = (TextView) row.findViewById(R.id.name);
		container = (LinearLayout) row.findViewById(R.id.container);

		comment.setText(mensaje.getMensaje());
		name.setText(mensaje.getUsuarioNombre() + " dijo:");

		Boolean condition;
		if (!mensaje.getUsuarioId().equals(usuario.getId())) {
			condition = true;
		} else {
			condition = false;
		}
		container.setBackgroundResource(!mensaje.getUsuarioId().equals(usuario.getId()) ? R.drawable.bubble_yellow : R.drawable.bubble_green);
		wrapper.setGravity(!mensaje.getUsuarioId().equals(usuario.getId()) ? Gravity.LEFT : Gravity.RIGHT);
		container.setGravity(!mensaje.getUsuarioId().equals(usuario.getId()) ? Gravity.LEFT : Gravity.RIGHT);
		name.setVisibility(!mensaje.getUsuarioId().equals(usuario.getId()) ? View.VISIBLE : View.GONE);

		return row;
	}

	public Bitmap decodeToBitmap(byte[] decodedByte) {
		return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
	}

}