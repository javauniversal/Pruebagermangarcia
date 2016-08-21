package co.zonaapp.pruebagermangarcia.Activities;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerProspectos;
import co.zonaapp.pruebagermangarcia.R;
import dmax.dialog.SpotsDialog;

/**
 * Created by hp_gergarga on 19/08/2016.
 */
public class BaseActivity extends AppCompatActivity {

    public RequestQueue rq;
    public SpotsDialog alertDialog;
    public ControllerLogin controllerLogin;
    public ControllerProspectos controllerProspectos;
    private static final int MY_WRITE_EXTERNAL_STORAGE = 0;
    public String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/PruebaGerman";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rq = Volley.newRequestQueue(this);
        alertDialog = new SpotsDialog(this, R.style.Custom);

        File dir = new File(path);
        dir.mkdirs();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void verifyPermission() {
        //WRITE_EXTERNAL_STORAGE tiene implícito READ_EXTERNAL_STORAGE porque pertenecen al mismo
        //grupo de permisos
        int writePermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (writePermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission();
        } else {
            //Guardamos log
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        //shouldShowRequestPermissionRationale es verdadero solamente si ya se había mostrado
        //anteriormente el dialogo de permisos y el usuario lo negó
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            openSettings();
        } else {
            //si es la primera vez se solicita el permiso directamente
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si el requestCode corresponde al que usamos para solicitar el permiso y
        //la respuesta del usuario fue positiva
        if (requestCode == MY_WRITE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //saveComments();
            } else {
                openSettings();
            }
        }
    }

    public void openSettings() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public boolean isValidString(String dato) { return dato == null || dato.length() == 0; }

    public boolean isValiEmail(String dato) { return !dato.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+"); }

    public void appendLog(File file, String text) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
