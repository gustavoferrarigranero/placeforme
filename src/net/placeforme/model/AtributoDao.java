package net.placeforme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.util.Conv;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class AtributoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] ALLCOLUMNS = new String[] { DbHelper.TABLE_ATRIBUTO_ID,
            DbHelper.TABLE_ATRIBUTO_TITULO, DbHelper.TABLE_ATRIBUTO_PADRAO,DbHelper.TABLE_ATRIBUTO_STATUS };


    public AtributoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public void add(Atributo atributo) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_ATRIBUTO_TITULO,atributo.getTitulo());
        values.put(DbHelper.TABLE_ATRIBUTO_PADRAO,atributo.getPadrao());
        values.put(DbHelper.TABLE_ATRIBUTO_STATUS,atributo.getStatus());
        database.insert(DbHelper.TABLE_ATRIBUTO,null,values);
        dbHelper.close();

    }

    public Atributo get(int id) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_ATRIBUTO, new String[] { DbHelper.TABLE_ATRIBUTO_ID,
                        DbHelper.TABLE_ATRIBUTO_TITULO, DbHelper.TABLE_ATRIBUTO_PADRAO,DbHelper.TABLE_ATRIBUTO_STATUS }, DbHelper.TABLE_ATRIBUTO_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Atributo atributo = new Atributo();
        atributo.setAtributoId(cursor.getInt(0));
        atributo.setTitulo(cursor.getString(1));
        atributo.setPadrao(cursor.getInt(2));
        atributo.setStatus(cursor.getInt(3));

        dbHelper.close();
        return atributo;
    }

    
    

    public List<Atributo> getAll() {
        List<Atributo> atributoList = new ArrayList<Atributo>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_ATRIBUTO,
                ALLCOLUMNS, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Atributo atributo = new Atributo();
            atributo.setAtributoId(cursor.getInt(0));
            atributo.setTitulo(cursor.getString(1));
            atributo.setPadrao(cursor.getInt(2));
            atributo.setStatus(cursor.getInt(3));
            atributoList.add(atributo);  
            cursor.moveToNext();
        }
        cursor.close();
        dbHelper.close();
        
        return atributoList;
    }


    public int update(Atributo atributo) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_ATRIBUTO_TITULO, atributo.getTitulo());
        values.put(DbHelper.TABLE_ATRIBUTO_PADRAO, atributo.getPadrao());
        values.put(DbHelper.TABLE_ATRIBUTO_STATUS, atributo.getStatus());
        int ret = database.update(DbHelper.TABLE_ATRIBUTO, values, DbHelper.TABLE_ATRIBUTO_ID + " = ?",
                new String[] { String.valueOf(atributo.getAtributoId()) });

        dbHelper.close();

        return ret;
    }


    public void delete(Atributo atributo) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_ATRIBUTO, DbHelper.TABLE_ATRIBUTO_ID + " = ?",
                new String[] { String.valueOf(atributo.getAtributoId()) });
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT  * FROM " + DbHelper.TABLE_ATRIBUTO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }


}
