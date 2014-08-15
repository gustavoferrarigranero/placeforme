package net.placeforme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.util.Conv;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class FotoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public FotoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    
    public void add(Foto foto) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_FOTO_LEGENDA, foto.getLegenda());
        values.put(DbHelper.TABLE_FOTO_FOTO, Conv.BitMapToString(foto.getFoto()));
        values.put(DbHelper.TABLE_FOTO_EVENTO_ID, foto.getEventoId());
        values.put(DbHelper.TABLE_FOTO_STATUS, foto.getStatus());
        database.insert(DbHelper.TABLE_FOTO, null, values);
        dbHelper.close();

    }

    public Foto get(int id) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_FOTO, new String[]{DbHelper.TABLE_FOTO_ID,
        		DbHelper.TABLE_FOTO_LEGENDA,DbHelper.TABLE_FOTO_FOTO, DbHelper.TABLE_FOTO_EVENTO_ID,
                        DbHelper.TABLE_FOTO_STATUS}, DbHelper.TABLE_FOTO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Foto foto = new Foto();
        foto.setFotoId(cursor.getInt(0));
        foto.setLegenda(cursor.getString(1));
        foto.setFoto(Conv.StringToBitMap(cursor.getString(2)));
        foto.setEventoId(cursor.getInt(3));
        foto.setStatus(cursor.getInt(4));

        dbHelper.close();
        return foto;
    }


    public List<Foto> getAll() {
        List<Foto> fotoList = new ArrayList<Foto>();
        String selectQuery = "SELECT  * FROM " + DbHelper.TABLE_FOTO;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
            	Foto foto = new Foto();
                foto.setFotoId(cursor.getInt(0));
                foto.setLegenda(cursor.getString(1));
                foto.setFoto(Conv.StringToBitMap(cursor.getString(2)));
                foto.setEventoId(cursor.getInt(3));
                foto.setStatus(cursor.getInt(4));
                fotoList.add(foto);
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return fotoList;
    }


    public int update(Foto foto) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_FOTO_LEGENDA, foto.getLegenda());
        values.put(DbHelper.TABLE_FOTO_FOTO, Conv.BitMapToString(foto.getFoto()));
        values.put(DbHelper.TABLE_FOTO_EVENTO_ID, foto.getEventoId());
        values.put(DbHelper.TABLE_FOTO_STATUS, foto.getStatus());
        int ret = database.update(DbHelper.TABLE_FOTO, values, DbHelper.TABLE_FOTO_ID + " = ?",
                new String[]{String.valueOf(foto.getFotoId())});

        dbHelper.close();

        return ret;
    }


    public void delete(Foto foto) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_FOTO, DbHelper.TABLE_FOTO_ID + " = ?",
                new String[]{String.valueOf(foto.getFotoId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_FOTO_ID + " FROM " + DbHelper.TABLE_FOTO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }


}
