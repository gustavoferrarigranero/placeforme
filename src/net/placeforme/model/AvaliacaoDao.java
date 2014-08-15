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
public class AvaliacaoDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;


    public AvaliacaoDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    public void add(Avaliacao avaliacao) {

        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_AVALIACAO_NOTA, avaliacao.getNota());
        values.put(DbHelper.TABLE_AVALIACAO_TEXTO, avaliacao.getTexto());
        values.put(DbHelper.TABLE_AVALIACAO_EVENTO_ID, avaliacao.getEventoId());
        values.put(DbHelper.TABLE_AVALIACAO_STATUS, avaliacao.getStatus());
        database.insert(DbHelper.TABLE_AVALIACAO, null, values);
        dbHelper.close();

    }

    public Avaliacao get(int id) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_AVALIACAO, new String[]{DbHelper.TABLE_AVALIACAO_ID,
                        DbHelper.TABLE_AVALIACAO_NOTA, DbHelper.TABLE_AVALIACAO_TEXTO, DbHelper.TABLE_AVALIACAO_EVENTO_ID,
                        DbHelper.TABLE_AVALIACAO_STATUS}, DbHelper.TABLE_AVALIACAO_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAvaliacaoId(cursor.getInt(0));
        avaliacao.setNota(cursor.getInt(1));
        avaliacao.setTexto(cursor.getString(2));
        avaliacao.setEventoId(cursor.getInt(3));
        avaliacao.setStatus(cursor.getInt(4));

        dbHelper.close();
        return avaliacao;
    }


    public List<Avaliacao> getAll() {
        List<Avaliacao> avaliacaoList = new ArrayList<Avaliacao>();
        String selectQuery = "SELECT  * FROM " + DbHelper.TABLE_AVALIACAO;
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Avaliacao avaliacao = new Avaliacao();
                avaliacao.setAvaliacaoId(cursor.getInt(0));
                avaliacao.setNota(cursor.getInt(1));
                avaliacao.setTexto(cursor.getString(2));
                avaliacao.setEventoId(cursor.getInt(3));
                avaliacao.setStatus(cursor.getInt(4));
                avaliacaoList.add(avaliacao);
            } while (cursor.moveToNext());
        }
        dbHelper.close();
        return avaliacaoList;
    }


    public int update(Avaliacao avaliacao) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_AVALIACAO_NOTA, avaliacao.getNota());
        values.put(DbHelper.TABLE_AVALIACAO_TEXTO, avaliacao.getTexto());
        values.put(DbHelper.TABLE_AVALIACAO_EVENTO_ID, avaliacao.getEventoId());
        values.put(DbHelper.TABLE_AVALIACAO_STATUS, avaliacao.getStatus());
        int ret = database.update(DbHelper.TABLE_AVALIACAO, values, DbHelper.TABLE_AVALIACAO_ID + " = ?",
                new String[]{String.valueOf(avaliacao.getAvaliacaoId())});

        dbHelper.close();

        return ret;
    }


    public void delete(Avaliacao avaliacao) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_AVALIACAO, DbHelper.TABLE_AVALIACAO_ID + " = ?",
                new String[]{String.valueOf(avaliacao.getAvaliacaoId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_AVALIACAO_ID + " FROM " + DbHelper.TABLE_AVALIACAO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }


}
