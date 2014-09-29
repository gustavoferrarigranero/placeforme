package net.placeforme.model;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class AvaliacaoDao {

	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] ALLCOLUMNS = new String[] { DbHelper.TABLE_AVALIACAO_ID,
			DbHelper.TABLE_AVALIACAO_NOTA, DbHelper.TABLE_AVALIACAO_TEXTO,
			DbHelper.TABLE_AVALIACAO_EVENTO_ID,
			DbHelper.TABLE_AVALIACAO_USUARIO_ID,
			DbHelper.TABLE_AVALIACAO_STATUS };

	public AvaliacaoDao(Context ctx) {
		this.dbHelper = new DbHelper(ctx);
	}

	public void add(Avaliacao avaliacao) {

		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbHelper.TABLE_AVALIACAO_NOTA, avaliacao.getNota());
		values.put(DbHelper.TABLE_AVALIACAO_TEXTO, avaliacao.getTexto());
		values.put(DbHelper.TABLE_AVALIACAO_EVENTO_ID, avaliacao.getEventoId());
		values.put(DbHelper.TABLE_AVALIACAO_USUARIO_ID,
				avaliacao.getUsuarioId());
		values.put(DbHelper.TABLE_AVALIACAO_STATUS, avaliacao.getStatus());
		database.insert(DbHelper.TABLE_AVALIACAO, null, values);
		dbHelper.close();

	}

	public Avaliacao get(int id) {

		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DbHelper.TABLE_AVALIACAO, ALLCOLUMNS,
				DbHelper.TABLE_AVALIACAO_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Avaliacao avaliacao = new Avaliacao();
		avaliacao.setAvaliacaoId(cursor.getInt(0));
		avaliacao.setNota(cursor.getInt(1));
		avaliacao.setTexto(cursor.getString(2));
		avaliacao.setEventoId(cursor.getInt(3));
		avaliacao.setUsuarioId(cursor.getInt(4));
		avaliacao.setStatus(cursor.getInt(5));

		dbHelper.close();
		return avaliacao;
	}

	public List<Avaliacao> getAll() {
		List<Avaliacao> avaliacaoList = new ArrayList<Avaliacao>();
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DbHelper.TABLE_AVALIACAO, ALLCOLUMNS,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Avaliacao avaliacao = new Avaliacao();
			avaliacao.setAvaliacaoId(cursor.getInt(0));
			avaliacao.setNota(cursor.getInt(1));
			avaliacao.setTexto(cursor.getString(2));
			avaliacao.setEventoId(cursor.getInt(3));
			avaliacao.setUsuarioId(cursor.getInt(4));
			avaliacao.setStatus(cursor.getInt(5));
			avaliacaoList.add(avaliacao);
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.close();
		return avaliacaoList;
	}

	public int update(Avaliacao avaliacao) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbHelper.TABLE_AVALIACAO_NOTA, avaliacao.getNota());
		values.put(DbHelper.TABLE_AVALIACAO_TEXTO, avaliacao.getTexto());
		values.put(DbHelper.TABLE_AVALIACAO_EVENTO_ID, avaliacao.getEventoId());
		values.put(DbHelper.TABLE_AVALIACAO_USUARIO_ID,
				avaliacao.getUsuarioId());
		values.put(DbHelper.TABLE_AVALIACAO_STATUS, avaliacao.getStatus());
		int ret = database.update(DbHelper.TABLE_AVALIACAO, values,
				DbHelper.TABLE_AVALIACAO_ID + " = ?",
				new String[] { String.valueOf(avaliacao.getAvaliacaoId()) });

		dbHelper.close();

		return ret;
	}

	public void delete(Avaliacao avaliacao) {

		database = dbHelper.getWritableDatabase();
		database.delete(DbHelper.TABLE_AVALIACAO, DbHelper.TABLE_AVALIACAO_ID
				+ " = ?",
				new String[] { String.valueOf(avaliacao.getAvaliacaoId()) });
		dbHelper.close();
	}

	public int count() {
		String countQuery = "SELECT " + DbHelper.TABLE_AVALIACAO_ID + " FROM "
				+ DbHelper.TABLE_AVALIACAO;
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(countQuery, null);
		cursor.close();
		dbHelper.close();
		return cursor.getCount();
	}

	public ArrayList<Avaliacao> getAllByEvento(Evento evento) {
		ArrayList<Avaliacao> avaliacaoList = new ArrayList<Avaliacao>();
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DbHelper.TABLE_AVALIACAO, ALLCOLUMNS,
				DbHelper.TABLE_AVALIACAO_EVENTO_ID + "=?",
				new String[] { String.valueOf(evento.getEventoId()) }, null,
				null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Avaliacao avaliacao = new Avaliacao();
			avaliacao.setAvaliacaoId(cursor.getInt(0));
			avaliacao.setNota(cursor.getInt(1));
			avaliacao.setTexto(cursor.getString(2));
			avaliacao.setEventoId(cursor.getInt(3));
			avaliacao.setUsuarioId(cursor.getInt(4));
			avaliacao.setStatus(cursor.getInt(5));
			avaliacaoList.add(avaliacao);
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.close();
		return avaliacaoList;
	}

}
