package net.placeforme.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Gustavo on 13/08/2014.
 * refs: http://pplware.sapo.pt/smartphones-tablets/android/tutorial-utilizao-do-sqlite-no-android-parte-i/
 * refs: http://tutsandroid.weebly.com/tutorial-7---base-de-dados-sqlite-fonte--androidhiveinfo.html#/
 */
public class DbHelper extends SQLiteOpenHelper {

    private SQLiteDatabase database;
    private static final String DATABASE_NAME = "placeforme";
    private static final int DATABASE_VERSION = 1;

    //atributos
    public static final String TABLE_ATRIBUTO = "atributos";
    public static final String TABLE_ATRIBUTO_ID = "atributo_id";
    public static final String TABLE_ATRIBUTO_TITULO = "titulo";
    public static final String TABLE_ATRIBUTO_PADRAO = "padrao";
    public static final String TABLE_ATRIBUTO_STATUS = "status";
    private static final String CREATE_TABLE_ATRIBUTO = "create table " + TABLE_ATRIBUTO + " ( "+
            TABLE_ATRIBUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_ATRIBUTO_TITULO + " TEXT," +
            TABLE_ATRIBUTO_PADRAO + " INTEGER," +
            TABLE_ATRIBUTO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_ATRIBUTO = "DROP TABLE IF EXISTS " + TABLE_ATRIBUTO ;

    //avaliacao
    public static final String TABLE_AVALIACAO = "avaliacoes";
    public static final String TABLE_AVALIACAO_ID = "avaliacao_id";
    public static final String TABLE_AVALIACAO_NOTA = "nota";
    public static final String TABLE_AVALIACAO_TEXTO = "texto";
    public static final String TABLE_AVALIACAO_EVENTO_ID = "evento_id";
    public static final String TABLE_AVALIACAO_STATUS = "status";
    private static final String CREATE_TABLE_AVALIACAO = "create table " + TABLE_AVALIACAO + " ( "+
            TABLE_AVALIACAO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_AVALIACAO_NOTA + " INTEGER," +
            TABLE_AVALIACAO_TEXTO + " TEXT," +
            TABLE_AVALIACAO_EVENTO_ID + " INTEGER," +
            TABLE_AVALIACAO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_AVALIACAO = "DROP TABLE IF EXISTS " + TABLE_AVALIACAO ;

    //evento
    public static final String TABLE_EVENTO = "eventos";
    public static final String TABLE_EVENTO_ID = "evento_id";
    public static final String TABLE_EVENTO_TITULO = "titulo";
    public static final String TABLE_EVENTO_DATA_INICIO = "data_inicio";
    public static final String TABLE_EVENTO_DATA_FIM = "data_fim";
    public static final String TABLE_EVENTO_GRUPO_ID = "grupo_id";
    public static final String TABLE_EVENTO_USUARIO_ID = "usuario_id";
    public static final String TABLE_EVENTO_STATUS = "status";
    private static final String CREATE_TABLE_EVENTO = "create table " + TABLE_EVENTO + " ( "+
            TABLE_EVENTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_EVENTO_TITULO + " TEXT," +
            TABLE_EVENTO_DATA_INICIO + " DATE," +
            TABLE_EVENTO_DATA_FIM + " DATE," +
            TABLE_EVENTO_GRUPO_ID + " INTEGER," +
            TABLE_EVENTO_USUARIO_ID + " INTEGER," +
            TABLE_AVALIACAO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_EVENTO = "DROP TABLE IF EXISTS " + TABLE_EVENTO ;

    //eventoAtributo
    public static final String TABLE_EVENTOATRIBUTO = "evento_atributos";
    public static final String TABLE_EVENTOATRIBUTO_ID = "evento_atributos_id";
    public static final String TABLE_EVENTOATRIBUTO_TEXTO = "texto";
    public static final String TABLE_EVENTOATRIBUTO_EVENTO_ID = "evento_id";
    public static final String TABLE_EVENTOATRIBUTO_ATRIBUTO_ID = "atributo_id";
    private static final String CREATE_TABLE_EVENTOATRIBUTO = "create table " + TABLE_EVENTOATRIBUTO + " ( "+
            TABLE_EVENTOATRIBUTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_EVENTOATRIBUTO_TEXTO + " TEXT," +
            TABLE_EVENTOATRIBUTO_EVENTO_ID + " INTEGER," +
            TABLE_EVENTOATRIBUTO_ATRIBUTO_ID + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_EVENTOATRIBUTO = "DROP TABLE IF EXISTS " + TABLE_EVENTOATRIBUTO ;

    //foto
    public static final String TABLE_FOTO = "fotos";
    public static final String TABLE_FOTO_ID = "foto_id";
    public static final String TABLE_FOTO_LEGENDA = "legenda";
    public static final String TABLE_FOTO_FOTO = "foto";
    public static final String TABLE_FOTO_STATUS = "status";
    private static final String CREATE_TABLE_FOTO = "create table " + TABLE_FOTO + " ( "+
            TABLE_FOTO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_FOTO_LEGENDA + " TEXT," +
            TABLE_FOTO_FOTO + " TEXT," +
            TABLE_FOTO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_FOTO = "DROP TABLE IF EXISTS " + TABLE_FOTO ;

    //grupo
    public static final String TABLE_GRUPO = "grupos";
    public static final String TABLE_GRUPO_ID = "grupo_id";
    public static final String TABLE_GRUPO_TITULO = "titulo";
    public static final String TABLE_GRUPO_STATUS = "status";
    private static final String CREATE_TABLE_GRUPO = "create table " + TABLE_GRUPO + " ( "+
            TABLE_GRUPO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_GRUPO_TITULO + " TEXT," +
            TABLE_GRUPO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_GRUPO = "DROP TABLE IF EXISTS " + TABLE_GRUPO ;

    //presenca
    public static final String TABLE_PRESENCA = "presencas";
    public static final String TABLE_PRESENCA_ID = "presenca_id";
    public static final String TABLE_PRESENCA_EVENTO_ID = "evento_id";
    public static final String TABLE_PRESENCA_USUARIO_ID = "usuario_id";
    private static final String CREATE_TABLE_PRESENCA = "create table " + TABLE_PRESENCA + " ( "+
            TABLE_PRESENCA_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_PRESENCA_EVENTO_ID + " INTEGER," +
            TABLE_PRESENCA_USUARIO_ID + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_PRESENCA = "DROP TABLE IF EXISTS " + TABLE_PRESENCA ;

    //usuario
    public static final String TABLE_USUARIO = "usuarios";
    public static final String TABLE_USUARIO_ID = "usuario_id";
    public static final String TABLE_USUARIO_NOME = "nome";
    public static final String TABLE_USUARIO_EMAIL = "email";
    public static final String TABLE_USUARIO_SENHA = "senha";
    public static final String TABLE_USUARIO_TIPO = "tipo";
    public static final String TABLE_USUARIO_STATUS = "status";
    private static final String CREATE_TABLE_USUARIO = "create table " + TABLE_USUARIO + " ( "+
            TABLE_USUARIO_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            TABLE_USUARIO_NOME + " TEXT," +
            TABLE_USUARIO_EMAIL + " TEXT," +
            TABLE_USUARIO_SENHA + " TEXT," +
            TABLE_USUARIO_TIPO + " INTEGER," +
            TABLE_USUARIO_STATUS + " INTEGER" +
            " ) ";
    private static final String DROP_TABLE_USUARIO = "DROP TABLE IF EXISTS " + TABLE_USUARIO ;


    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ATRIBUTO);
        db.execSQL(CREATE_TABLE_AVALIACAO);
        db.execSQL(CREATE_TABLE_EVENTO);
        db.execSQL(CREATE_TABLE_EVENTOATRIBUTO);
        db.execSQL(CREATE_TABLE_FOTO);
        db.execSQL(CREATE_TABLE_GRUPO);
        db.execSQL(CREATE_TABLE_PRESENCA);
        db.execSQL(CREATE_TABLE_USUARIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_TABLE_ATRIBUTO);
        db.execSQL(DROP_TABLE_AVALIACAO);
        db.execSQL(DROP_TABLE_EVENTO);
        db.execSQL(DROP_TABLE_EVENTOATRIBUTO);
        db.execSQL(DROP_TABLE_FOTO);
        db.execSQL(DROP_TABLE_GRUPO);
        db.execSQL(DROP_TABLE_PRESENCA);
        db.execSQL(DROP_TABLE_USUARIO);
        onCreate(db);
    }

    public SQLiteDatabase getWritableDatabase() {

        this.database = this.getWritableDatabase();

        return this.database;

    }

    public SQLiteDatabase getReadableDatabase() {

        this.database = this.getReadableDatabase();

        return this.database;

    }

    public void close() {

        this.database.close();

    }

}
