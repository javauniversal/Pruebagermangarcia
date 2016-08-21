package co.zonaapp.pruebagermangarcia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.Map;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Model.ResponseLogin;
import co.zonaapp.pruebagermangarcia.R;

import static co.zonaapp.pruebagermangarcia.Model.ResponseLogin.setAuthTokenStatic;
import static co.zonaapp.pruebagermangarcia.Model.ResponseLogin.setUsuarioStatic;

public class ActLogin extends BaseActivity implements View.OnClickListener {

    private EditText editEmail;
    private EditText editPassword;
    private CheckBox checkBoxPaEm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        checkBoxPaEm = (CheckBox) findViewById(R.id.checkBoxPaEm);

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);

        controllerLogin = new ControllerLogin(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogin:

                // Validate Login EditText
                if (isValidString(editEmail.getText().toString().trim())) {
                    editEmail.setError("Campo requerido");
                    editEmail.requestFocus();
                } else if (isValiEmail(editEmail.getText().toString().trim())) {
                    editEmail.setError("El E-mail no es un formato correcto");
                    editEmail.requestFocus();
                } else if (isValidString(editPassword.getText().toString().trim())) {
                    editPassword.setError("Campo requerido");
                    editPassword.requestFocus();
                } else {
                    // Validate ok
                    loginServices();
                }

                break;
        }
    }

    private void loginServices() {

        alertDialog.show();

        String url = String.format("%1$s%2$s?email=%3$s&password=%4$s",
                getString(R.string.url_base),
                "application/login",
                editEmail.getText().toString().trim(),  editPassword.getText().toString().trim());

        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // response
                        parseJsonLogin(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            TastyToast.makeText(ActLogin.this, "Error de tiempo de espera", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof AuthFailureError) {
                            TastyToast.makeText(ActLogin.this, "Error Servidor", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ServerError) {
                            TastyToast.makeText(ActLogin.this, "Contraseña incorrecta", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof NetworkError) {
                            TastyToast.makeText(ActLogin.this, "Error de red", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ParseError) {
                            TastyToast.makeText(ActLogin.this, "Error al serializar los datos", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        jsonRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonRequest);

    }

    private void parseJsonLogin(String response) {

        Gson gson = new Gson();
        final ResponseLogin responseLogin = gson.fromJson(response, ResponseLogin.class);

        if (responseLogin.isSuccess()) {
            // Login Ok
            if (checkBoxPaEm.isChecked()) {
                new Thread(new Runnable() {
                    public void run() {
                        responseLogin.setPassword(editPassword.getText().toString());
                        controllerLogin.insertLoginUser(responseLogin);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                changeActivity();
                            }
                        });
                    }
                }).start();
            } else {

                setAuthTokenStatic(responseLogin.getAuthToken());
                setUsuarioStatic(responseLogin.getEmail());

                changeActivity();
            }
        }
    }

    private void changeActivity() {
        startActivity(new Intent(ActLogin.this, ActMainMenu.class));
        finish();
        alertDialog.dismiss();
    }
}
