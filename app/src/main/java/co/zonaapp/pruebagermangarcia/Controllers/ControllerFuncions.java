package co.zonaapp.pruebagermangarcia.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import co.zonaapp.pruebagermangarcia.DataBase.DBHelper;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class ControllerFuncions {

    private SQLiteDatabase database;
    private ControllerLogin controllerLogin;

    public ControllerFuncions(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertIntro(String dato) {
        ContentValues values = new ContentValues();
        try {
            values.put("data_intro", dato);
            database.insert("intro", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        }

        return true;
    }

    public boolean validateLoginUser() {
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT COUNT(*) FROM intro";
        cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }
}
