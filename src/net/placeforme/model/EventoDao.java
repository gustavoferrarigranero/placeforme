package net.placeforme.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import net.placeforme.util.Utils;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class EventoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] ALLCOLUMNS = { DbHelper.TABLE_EVENTO_ID,DbHelper.TABLE_EVENTO_TITULO,
    		DbHelper.TABLE_EVENTO_DATA_INICIO,DbHelper.TABLE_EVENTO_HORARIO,DbHelper.TABLE_EVENTO_GRUPO_ID,
    		DbHelper.TABLE_EVENTO_USUARIO_ID,DbHelper.TABLE_EVENTO_STATUS};


    public EventoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }


    public long add(Evento evento) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_EVENTO_TITULO, evento.getTitulo());
        values.put(DbHelper.TABLE_EVENTO_DATA_INICIO, Utils.sqlDateToString(evento.getDataInicio()));
        values.put(DbHelper.TABLE_EVENTO_HORARIO, Utils.sqlTimeToString(evento.getHorario()));
        values.put(DbHelper.TABLE_EVENTO_GRUPO_ID, evento.getGrupoId());
        values.put(DbHelper.TABLE_EVENTO_USUARIO_ID, evento.getUsuarioId());
        values.put(DbHelper.TABLE_EVENTO_STATUS, evento.getStatus());
        long ret = database.insert(DbHelper.TABLE_EVENTO, null, values);

        dbHelper.close();
        
        return ret;

    }


    public Evento get(int id) throws ParseException {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_EVENTO, ALLCOLUMNS, DbHelper.TABLE_EVENTO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Evento evento = new Evento();
        evento.setEventoId(cursor.getInt(0));
        evento.setTitulo(cursor.getString(1));
        evento.setDataInicio(Utils.stringToSqlDate(cursor.getString(2)));
        evento.setHorario(Utils.stringToSqlTime(cursor.getString(3)));
        evento.setGrupoId(cursor.getInt(4));
        evento.setUsuarioId(cursor.getInt(5));
        evento.setStatus(cursor.getInt(6));

        dbHelper.close();
        return evento;
    }


    public List<Evento> getAll() {
        List<Evento> eventoList = new ArrayList<Evento>();
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_EVENTO,
                ALLCOLUMNS, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                Evento evento = new Evento();
                evento.setEventoId(cursor.getInt(0));
                evento.setTitulo(cursor.getString(1));
                evento.setDataInicio(Utils.stringToSqlDate(cursor.getString(2)));
                evento.setHorario(Utils.stringToSqlTime(cursor.getString(3)));
                evento.setGrupoId(cursor.getInt(4));
                evento.setUsuarioId(cursor.getInt(5));
                evento.setStatus(cursor.getInt(6));
                eventoList.add(evento);
                
                cursor.moveToNext();
        }

        cursor.close();
        dbHelper.close();
        return eventoList;
    }
    
    public List<Evento> getAllFiltros(String titulo, String data, int grupo_id) {
        List<Evento> eventoList = new ArrayList<Evento>();
        
        String countQuery = "SELECT * FROM " + DbHelper.TABLE_EVENTO + " WHERE "+DbHelper.TABLE_EVENTO_ID+" != 0";
                
        if(titulo!=""){
        	countQuery += " AND "+DbHelper.TABLE_EVENTO_TITULO+" like '%"+titulo+"%' ";
        }
        
        if(data!=""){
        	countQuery += " AND "+DbHelper.TABLE_EVENTO_DATA_INICIO+" = '"+data+"' ";
        }
        
        if(grupo_id!=0){
        	countQuery += " AND "+DbHelper.TABLE_EVENTO_GRUPO_ID+" = "+grupo_id+" ";
        }
        
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                Evento evento = new Evento();
                evento.setEventoId(cursor.getInt(0));
                evento.setTitulo(cursor.getString(1));
                evento.setDataInicio(Utils.stringToSqlDate(cursor.getString(2)));
                evento.setHorario(Utils.stringToSqlTime(cursor.getString(3)));
                evento.setGrupoId(cursor.getInt(4));
                evento.setUsuarioId(cursor.getInt(5));
                evento.setStatus(cursor.getInt(6));
                eventoList.add(evento);
                
                cursor.moveToNext();
        }

        cursor.close();
        dbHelper.close();
        return eventoList;
    }


    public int update(Evento evento) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_EVENTO_TITULO, evento.getTitulo());
        values.put(DbHelper.TABLE_EVENTO_DATA_INICIO, String.valueOf(evento.getDataInicio()));
        values.put(DbHelper.TABLE_EVENTO_HORARIO, Utils.sqlTimeToString(evento.getHorario()));
        values.put(DbHelper.TABLE_EVENTO_GRUPO_ID, evento.getGrupoId());
        values.put(DbHelper.TABLE_EVENTO_USUARIO_ID, evento.getUsuarioId());
        values.put(DbHelper.TABLE_EVENTO_STATUS, evento.getStatus());
        int ret = database.update(DbHelper.TABLE_EVENTO, values, DbHelper.TABLE_EVENTO_ID + " = ?",
                new String[]{String.valueOf(evento.getEventoId())});

        dbHelper.close();

        return ret;
    }


    public void delete(Evento evento) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_EVENTO, DbHelper.TABLE_EVENTO_ID + " = ?",
                new String[]{String.valueOf(evento.getEventoId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_EVENTO_ID + " FROM " + DbHelper.TABLE_EVENTO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }


}
