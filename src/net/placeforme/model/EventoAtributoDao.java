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
public class EventoAtributoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] ALLCOLUMNS = new String[]{DbHelper.TABLE_EVENTOATRIBUTO_ID,
            DbHelper.TABLE_EVENTOATRIBUTO_TEXTO, DbHelper.TABLE_EVENTOATRIBUTO_EVENTO_ID,
            DbHelper.TABLE_EVENTOATRIBUTO_ATRIBUTO_ID};


    public EventoAtributoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public void add(EventoAtributo eventoAtributo) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_TEXTO, eventoAtributo.getTexto());
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_EVENTO_ID, eventoAtributo.getEventoId());
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_ATRIBUTO_ID, eventoAtributo.getAtributoId());
        database.insert(DbHelper.TABLE_EVENTOATRIBUTO, null, values);
        dbHelper.close();

    }

    public EventoAtributo get(int id) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_EVENTOATRIBUTO, ALLCOLUMNS, DbHelper.TABLE_EVENTOATRIBUTO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        EventoAtributo eventoAtributo = new EventoAtributo();
        eventoAtributo.setEventoAtributoId(cursor.getInt(0));
        eventoAtributo.setTexto(cursor.getString(1));
        eventoAtributo.setEventoId(cursor.getInt(2));
        eventoAtributo.setAtributoId(cursor.getInt(3));

        dbHelper.close();
        return eventoAtributo;
    }


    public List<EventoAtributo> getAll() {
        List<EventoAtributo> eventoAtributoList = new ArrayList<EventoAtributo>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_EVENTOATRIBUTO,
                ALLCOLUMNS, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                EventoAtributo eventoAtributo = new EventoAtributo();
                eventoAtributo.setEventoAtributoId(cursor.getInt(0));
                eventoAtributo.setTexto(cursor.getString(1));
                eventoAtributo.setEventoId(cursor.getInt(2));
                eventoAtributo.setAtributoId(cursor.getInt(3));
                eventoAtributoList.add(eventoAtributo);
            	cursor.moveToNext();
        }
        cursor.close();
        dbHelper.close();
        return eventoAtributoList;
    }


    public int update(EventoAtributo eventoAtributo) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_TEXTO, eventoAtributo.getTexto());
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_EVENTO_ID, eventoAtributo.getEventoId());
        values.put(DbHelper.TABLE_EVENTOATRIBUTO_ATRIBUTO_ID, eventoAtributo.getAtributoId());
        int ret = database.update(DbHelper.TABLE_EVENTOATRIBUTO, values, DbHelper.TABLE_EVENTOATRIBUTO_ID + " = ?",
                new String[]{String.valueOf(eventoAtributo.getEventoAtributoId())});

        dbHelper.close();

        return ret;
    }


    public void delete(EventoAtributo eventoAtributo) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_EVENTOATRIBUTO, DbHelper.TABLE_EVENTOATRIBUTO_ID + " = ?",
                new String[]{String.valueOf(eventoAtributo.getEventoAtributoId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_EVENTOATRIBUTO_ID + " FROM " + DbHelper.TABLE_EVENTOATRIBUTO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }

	public ArrayList<EventoAtributo> getAllByEvento(Evento evento) {
		ArrayList<EventoAtributo> eventoAtributoList = new ArrayList<EventoAtributo>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_EVENTOATRIBUTO, ALLCOLUMNS, DbHelper.TABLE_EVENTOATRIBUTO_EVENTO_ID + "=?",
                new String[]{String.valueOf(evento.getEventoId())}, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                EventoAtributo eventoAtributo = new EventoAtributo();
                eventoAtributo.setEventoAtributoId(cursor.getInt(0));
                eventoAtributo.setTexto(cursor.getString(1));
                eventoAtributo.setEventoId(cursor.getInt(2));
                eventoAtributo.setAtributoId(cursor.getInt(3));
                eventoAtributoList.add(eventoAtributo);
            	cursor.moveToNext();
        }
        cursor.close();
        dbHelper.close();
        return eventoAtributoList;
	}


}
