package co.zonaapp.pruebagermangarcia.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import co.zonaapp.pruebagermangarcia.R;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "prueba_german.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlLoginUsuario = context.getResources().getString(R.string.tabla_login);
        String sqlIntro = context.getResources().getString(R.string.tabla_intro);
        String sqlPospectos = context.getResources().getString(R.string.tabla_prospectos);
        String sqlSincronizar = context.getResources().getString(R.string.tabla_sincronizar);

        // Create Table
        db.execSQL(sqlLoginUsuario);
        db.execSQL(sqlIntro);
        db.execSQL(sqlPospectos);
        db.execSQL(sqlSincronizar);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS login");
        db.execSQL("DROP TABLE IF EXISTS intro");
        db.execSQL("DROP TABLE IF EXISTS prospectos");
        db.execSQL("DROP TABLE IF EXISTS sincronizar");
    }
}
