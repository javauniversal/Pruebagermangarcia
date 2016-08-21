package co.zonaapp.pruebagermangarcia.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import co.zonaapp.pruebagermangarcia.DataBase.DBHelper;
import co.zonaapp.pruebagermangarcia.Model.ResponseLogin;
import se.simbio.encryption.Encryption;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class ControllerLogin {

    private final Encryption encryption;
    private SQLiteDatabase database;

    public ControllerLogin(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
        encryption = Encryption.getDefault("SomeKey", "SomeSalt", new byte[16]);
    }

    public boolean insertLoginUser(ResponseLogin data) {

        ContentValues values = new ContentValues();
        try {

            values.put("authToken", data.getAuthToken());
            values.put("email", data.getEmail());
            values.put("password", encryption.encryptOrNull(data.getPassword()));

            database.insert("login", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;

    }

    public boolean validateLoginUser(String email, String password ) {
        Cursor cursor;
        boolean indicador = false;
        String[] args = new String[] {email, password};
        String sql = "SELECT * FROM login WHERE email =? AND password =?";
        cursor = database.rawQuery(sql, args);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }

    public boolean validateLoginUser() {
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT * FROM login";
        cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }

    public ResponseLogin getUserLogin() {

        Cursor cursor;
        ResponseLogin responseLogin = new ResponseLogin();
        String sql = "SELECT * FROM login LIMIT 1";
        cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            responseLogin.setAuthToken(cursor.getString(0));
            responseLogin.setEmail(cursor.getString(1));
            responseLogin.setPasswordEncry(encryption.decryptOrNull(cursor.getString(2)));
            responseLogin.setPassword(cursor.getString(2));
        }

        return responseLogin;

    }

    public ResponseLogin getUserLoginAutoTo() {

        Cursor cursor;
        ResponseLogin responseLogin = new ResponseLogin();
        String sql = "SELECT * FROM login LIMIT 1";
        cursor = database.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            responseLogin.setAuthToken(cursor.getString(0));
            responseLogin.setEmail(cursor.getString(1));
            responseLogin.setPassword(cursor.getString(2));
        }

        return responseLogin;

    }

    public boolean deleteObject(String name_database) {
        int a = database.delete(name_database, null, null);
        return a > 0;

    }
}
