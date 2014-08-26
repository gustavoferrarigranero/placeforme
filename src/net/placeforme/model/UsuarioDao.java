package net.placeforme.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import net.placeforme.util.Conv;

/**
 * Created by Gustavo on 13/08/2014.
 */
public class UsuarioDao {

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    private String[] ALLCOLUMNS = new String[]{
    		DbHelper.TABLE_USUARIO_ID,DbHelper.TABLE_USUARIO_NOME,DbHelper.TABLE_USUARIO_FOTO,DbHelper.TABLE_USUARIO_EMAIL,
    		DbHelper.TABLE_USUARIO_SENHA,DbHelper.TABLE_USUARIO_TIPO,DbHelper.TABLE_USUARIO_STATUS};


    public UsuarioDao(Context ctx) {
        this.dbHelper = new DbHelper(ctx);
    }

    
    public long add(Usuario usuario) {
    	Usuario userReturn = null;
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_USUARIO_NOME, usuario.getNome());
        values.put(DbHelper.TABLE_USUARIO_FOTO, Conv.BitMapToString(usuario.getFoto()));
        values.put(DbHelper.TABLE_USUARIO_EMAIL, usuario.getEmail());
        values.put(DbHelper.TABLE_USUARIO_SENHA, usuario.getSenha());
        values.put(DbHelper.TABLE_USUARIO_TIPO, usuario.getTipo());
        values.put(DbHelper.TABLE_USUARIO_STATUS, usuario.getStatus());
        long ret = database.insert(DbHelper.TABLE_USUARIO, null, values);

        return ret;
    }

    public Usuario get(long id) {

        database = dbHelper.getReadableDatabase();
        
        Cursor cursor = database.rawQuery("SELECT * FROM "+DbHelper.TABLE_USUARIO+" WHERE "+DbHelper.TABLE_USUARIO_ID+" = "+id, null);
        
        Usuario usuario = null;
               
        if (cursor.moveToFirst()){

        	usuario = new Usuario();
	        usuario.setUsuarioId(cursor.getInt(0));
	        usuario.setNome(cursor.getString(1));
	        usuario.setFoto(Conv.StringToBitMap(cursor.getString(2)));
	        usuario.setEmail(cursor.getString(3));
	        usuario.setSenha(cursor.getString(4));
	        usuario.setTipo(cursor.getInt(5));
	        usuario.setStatus(cursor.getInt(6));
        }

        dbHelper.close();
        return usuario;
    }


    public List<Usuario> getAll() {
        List<Usuario> usuarioList = new ArrayList<Usuario>();
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_ATRIBUTO,
                ALLCOLUMNS, null, null, null, null, null);
        
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            	Usuario usuario = new Usuario();
                usuario.setUsuarioId(cursor.getInt(0));
                usuario.setNome(cursor.getString(1));
                usuario.setFoto(Conv.StringToBitMap(cursor.getString(2)));
                usuario.setEmail(cursor.getString(3));
                usuario.setSenha(cursor.getString(4));
                usuario.setTipo(cursor.getInt(5));
                usuario.setStatus(cursor.getInt(6));
                usuarioList.add(usuario);
            	cursor.moveToNext();
        }
        cursor.close();
        dbHelper.close();
        return usuarioList;
    }


    public int update(Usuario usuario) {
        database = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.TABLE_USUARIO_NOME, usuario.getNome());
        values.put(DbHelper.TABLE_USUARIO_FOTO, Conv.BitMapToString(usuario.getFoto()));
        values.put(DbHelper.TABLE_USUARIO_EMAIL, usuario.getEmail());
        values.put(DbHelper.TABLE_USUARIO_SENHA, usuario.getSenha());
        values.put(DbHelper.TABLE_USUARIO_TIPO, usuario.getTipo());
        values.put(DbHelper.TABLE_USUARIO_STATUS, usuario.getStatus());
        int ret = database.update(DbHelper.TABLE_USUARIO, values, DbHelper.TABLE_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getUsuarioId())});

        dbHelper.close();

        return ret;
    }


    public void delete(Usuario usuario) {

        database = dbHelper.getWritableDatabase();
        database.delete(DbHelper.TABLE_USUARIO, DbHelper.TABLE_USUARIO_ID + " = ?",
                new String[]{String.valueOf(usuario.getUsuarioId())});
        dbHelper.close();
    }


    public int count() {
        String countQuery = "SELECT " + DbHelper.TABLE_USUARIO_ID + " FROM " + DbHelper.TABLE_USUARIO;
        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(countQuery, null);
        cursor.close();
        dbHelper.close();
        return cursor.getCount();
    }
    
    public Usuario login(String email,String senha) {

        database = dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_USUARIO, ALLCOLUMNS, DbHelper.TABLE_USUARIO_EMAIL + " =? AND "+DbHelper.TABLE_USUARIO_SENHA + " =? ",
                new String[]{email,senha}, null, null, null, null);

        Usuario usuario = null;
        
        if (cursor.getCount()>0){
            cursor.moveToNext();
            usuario = new Usuario();
	        usuario.setUsuarioId(cursor.getInt(0));
	        usuario.setNome(cursor.getString(1));
	        usuario.setFoto(Conv.StringToBitMap(cursor.getString(2)));
	        usuario.setEmail(cursor.getString(3));
	        usuario.setSenha(cursor.getString(4));
	        usuario.setTipo(cursor.getInt(5));
	        usuario.setStatus(cursor.getInt(6));
        }

        cursor.close();
        dbHelper.close();
        
        return usuario;
    }


}
