package co.zonaapp.pruebagermangarcia.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class ResponseLogin implements Serializable {

    @SerializedName("success")
    private boolean success;

    @SerializedName("authToken")
    private String authToken;

    @SerializedName("email")
    private String email;

    private String password;

    private String passwordEncry;

    private static String authTokenStatic;

    private static String usuarioStatic;

    public static String getUsuarioStatic() {
        return usuarioStatic;
    }

    public static void setUsuarioStatic(String usuarioStatic) {
        ResponseLogin.usuarioStatic = usuarioStatic;
    }

    public static String getAuthTokenStatic() {
        return authTokenStatic;
    }

    public static void setAuthTokenStatic(String authTokenStatic) {
        ResponseLogin.authTokenStatic = authTokenStatic;
    }

    public String getPasswordEncry() {
        return passwordEncry;
    }

    public void setPasswordEncry(String passwordEncry) {
        this.passwordEncry = passwordEncry;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
