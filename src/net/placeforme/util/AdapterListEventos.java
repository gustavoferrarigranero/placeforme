package net.placeforme.util;

import java.util.List;

import net.placeforme.MainActivity;
import net.placeforme.R;
import net.placeforme.ShowEventoActivity;
import net.placeforme.TabTreeActivity;
import net.placeforme.TabTwoActivity;
import net.placeforme.model.Evento;
import net.placeforme.model.EventoDao;
import net.placeforme.model.Usuario;
import net.placeforme.model.UsuarioDao;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListEventos extends BaseAdapter {

	private LayoutInflater inflater;

	private List<Evento> eventos;
	private UsuarioDao usuarioDao;
	private Usuario usuarioEvento;

	private TextView campotitulo;
	private TextView campoData;
	private ImageView campoFoto;

	public AdapterListEventos(Context context, List<Evento> eventos) {

		this.eventos = eventos;

	}

	@Override
	public int getCount() {

		return eventos.size();

	}

	@Override
	public Object getItem(int position) {

		return eventos.get(position);

	}

	@Override
	public long getItemId(int position) {

		return position;

	}

	@Override
	public View getView(int position, View convertView, final ViewGroup parent) {

		inflater = (LayoutInflater) parent.getContext().getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		convertView = inflater.inflate(R.layout.list_eventos, null);

		final Evento evento = eventos.get(position);

		usuarioDao = new UsuarioDao(parent.getContext());
		usuarioEvento = new Usuario();
		usuarioEvento = usuarioDao.get(evento.getUsuarioId());

		campotitulo = (TextView) convertView.findViewById(R.id.text);
		campoFoto = (ImageView) convertView.findViewById(R.id.imageUsuario);

		campotitulo.setText(evento.getTitulo());

		campoData = (TextView) convertView.findViewById(R.id.datainicio);

		if (null != evento.getDataInicio()) {
			campoData.setText("Data: "
					+ Utils.sqlDateToString(evento.getDataInicio())
					+ " - Horário: "
					+ Utils.sqlTimeToString(evento.getHorario()) + "hs");
		}
		if (null != usuarioEvento.getFoto()) {
			campoFoto
					.setImageBitmap(Utils.circleImage(usuarioEvento.getFoto()));
		}

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent show = new Intent(parent.getContext(),
						ShowEventoActivity.class);
				Bundle params = new Bundle();
				params.putInt("evento_id", evento.getEventoId());
				show.putExtras(params);
				parent.getContext().startActivity(show);
			}
		});

		convertView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub

				if (evento.getUsuarioId() == MainActivity.usuarioLogado
						.getUsuarioId()) {
					new AlertDialog.Builder(MainActivity.mainActivity)
							.setTitle("Excluir")
							.setMessage("Você deseja excluir este evento?")
							.setIcon(android.R.drawable.ic_menu_delete)
							.setPositiveButton(android.R.string.yes,
									new DialogInterface.OnClickListener() {

										public void onClick(
												DialogInterface dialog,
												int whichButton) {
											EventoDao eventoDao = new EventoDao(
													parent.getContext());
											eventoDao.delete(evento);
											MainActivity.mainActivity
													.recreate();
										}
									})
							.setNegativeButton(android.R.string.no, null)
							.show();

				}

				return false;
			}
		});

		return convertView;

	}

}