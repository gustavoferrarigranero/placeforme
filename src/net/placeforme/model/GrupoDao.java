package net.placeforme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class GrupoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] ALLCOLUMNS = new String[]{DbHelper.TABLE_GRUPO_ID,
            DbHelper.TABLE_GRUPO_TITULO,DbHelper.TABLE_GRUPO_STATUS};


    public GrupoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public void add(Grupo grupo) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_GRUPO_TITULO, grupo.getTitulo());
        values.put(DbHelper.TABLE_GRUPO_STATUS, grupo.getStatus());
        database.insert(DbHelper.TABLE_GRUPO, null, values);
        dbHelper.close();

    }

    public Grupo get(int id) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_GRUPO, ALLCOLUMNS, 
                        DbHelper.TABLE_GRUPO_ID + "=?",new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Grupo grupo = new Grupo();
        grupo.setGrupoId(cursor.getInt(0));
        grupo.setTitulo(cursor.getString(1));
        grupo.setStatus(cursor.getInt(2));

        dbHelper.close();
        return grupo;
    }


    public List<Grupo> getAll() {
        List<Grupo> grupoList = new ArrayList<Grupo>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_ATRIBUTO,
                ALLCOLUMNS, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            	Grupo grupo = new Grupo();
                grupo.setGrupoId(cursor.getInt(0));
                grupo.setTitulo(cursor.getString(1));
                grupo.setStatus(cursor.getInt(2));
                grupoList.add(grupo);
            	cursor.moveToNext();
        }
        cursor.close();
        dbHelper.close();
        return grupoList;
    }


    public int update(Grupo grupo) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_GRUPO_TITULO, grupo.getTitulo());
        values.put(DbHelper.TABLE_GRUPO_STATUS, grupo.getStatus());
        int ret = database.update(DbHelper.TABLE_GRUPO, values, DbHelper.TABLE_GRUPO_ID + " = ?",
                new String[]{String.valueOf(grupo.getGrupoId())});

        dbHelper.close();

        return ret;
    }


    public void delete(Grupo grupo) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_GRUPO, DbHelper.TABLE_GRUPO_ID + " = ?",
                new String[]{String.valueOf(grupo.getGrupoId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_GRUPO_ID + " FROM " + DbHelper.TABLE_GRUPO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }


}
