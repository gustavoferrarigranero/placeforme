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
        Cursor cursor = database.query(DbHelper.TABLE_GRUPO, new String[]{DbHelper.TABLE_GRUPO_ID,
                        DbHelper.TABLE_GRUPO_TITULO,DbHelper.TABLE_GRUPO_STATUS}, 
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
        String selectQuery = "SELECT  * FROM " + DbHelper.TABLE_GRUPO;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	Grupo grupo = new Grupo();
                grupo.setGrupoId(cursor.getInt(0));
                grupo.setTitulo(cursor.getString(1));
                grupo.setStatus(cursor.getInt(2));
                grupoList.add(grupo);
            } while (cursor.moveToNext());
        }
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
