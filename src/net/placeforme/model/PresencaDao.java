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
public class PresencaDao {

	private SQLiteDatabase database;
	private DbHelper dbHelper;
	private String[] ALLCOLUMNS = new String[] { DbHelper.TABLE_PRESENCA_ID,
			DbHelper.TABLE_PRESENCA_EVENTO_ID,
			DbHelper.TABLE_PRESENCA_USUARIO_ID, };

	public PresencaDao(Context ctx) {
		this.dbHelper = new DbHelper(ctx);
	}

	public void add(Presenca presenca) {

		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbHelper.TABLE_PRESENCA_EVENTO_ID, presenca.getEventoId());
		values.put(DbHelper.TABLE_PRESENCA_USUARIO_ID, presenca.getUsuarioId());
		database.insert(DbHelper.TABLE_PRESENCA, null, values);
		dbHelper.close();

	}

	public Presenca get(int id) {

		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DbHelper.TABLE_PRESENCA, ALLCOLUMNS,
				DbHelper.TABLE_PRESENCA_ID + "=?",
				new String[] { String.valueOf(id) }, null, null, null, null);

		if (cursor != null)
			cursor.moveToFirst();

		Presenca presenca = new Presenca();
		presenca.setPresencaId(cursor.getInt(0));
		presenca.setEventoId(cursor.getInt(1));
		presenca.setUsuarioId(cursor.getInt(2));

		dbHelper.close();
		return presenca;
	}

	public List<Presenca> getAll() {
		List<Presenca> presencaList = new ArrayList<Presenca>();
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.query(DbHelper.TABLE_ATRIBUTO, ALLCOLUMNS,
				null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			Presenca presenca = new Presenca();
			presenca.setPresencaId(cursor.getInt(0));
			presenca.setEventoId(cursor.getInt(1));
			presenca.setUsuarioId(cursor.getInt(2));
			presencaList.add(presenca);
			cursor.moveToNext();
		}
		cursor.close();
		dbHelper.close();
		return presencaList;
	}

	public int update(Presenca presenca) {
		database = dbHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbHelper.TABLE_PRESENCA_EVENTO_ID, presenca.getEventoId());
		values.put(DbHelper.TABLE_PRESENCA_USUARIO_ID, presenca.getUsuarioId());
		int ret = database.update(DbHelper.TABLE_PRESENCA, values,
				DbHelper.TABLE_PRESENCA_ID + " = ?",
				new String[] { String.valueOf(presenca.getPresencaId()) });

		dbHelper.close();

		return ret;
	}

	public void delete(Presenca presenca) {

		database = dbHelper.getWritableDatabase();
		database.delete(DbHelper.TABLE_PRESENCA, DbHelper.TABLE_PRESENCA_ID
				+ " = ?",
				new String[] { String.valueOf(presenca.getPresencaId()) });
		dbHelper.close();
	}

	public int count() {
		String countQuery = "SELECT " + DbHelper.TABLE_PRESENCA_ID + " FROM "
				+ DbHelper.TABLE_PRESENCA;
		database = dbHelper.getReadableDatabase();
		Cursor cursor = database.rawQuery(countQuery, null);
		cursor.close();
		dbHelper.close();
		return cursor.getCount();
	}

}
