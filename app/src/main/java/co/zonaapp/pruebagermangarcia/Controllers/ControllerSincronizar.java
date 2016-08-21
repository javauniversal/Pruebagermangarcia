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
public class ControllerSincronizar {

    private SQLiteDatabase database;

    public ControllerSincronizar(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertSincronizar(String data) {

        ContentValues values = new ContentValues();
        try {
            values.put("date", data);
            database.insert("sincronizar", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public String getSincronizar() {
        String date = "";
        Cursor cursor;
        String sql = "SELECT * FROM sincronizar LIMIT 1";
        cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            date = cursor.getString(0);
        }

        return date;

    }

}
