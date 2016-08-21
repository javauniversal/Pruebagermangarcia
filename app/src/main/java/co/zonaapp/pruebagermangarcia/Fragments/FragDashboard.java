package co.zonaapp.pruebagermangarcia.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Model.ResponseLogin;
import co.zonaapp.pruebagermangarcia.R;

import static co.zonaapp.pruebagermangarcia.Model.ResponseLogin.getAuthTokenStatic;
import static co.zonaapp.pruebagermangarcia.Model.ResponseLogin.getUsuarioStatic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragDashboard extends BaseVolleyFragment {

    private TextView txtUsuario;
    private TextView txtToken;
    private TextView txtInfo;

    public FragDashboard() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        txtUsuario = (TextView) view.findViewById(R.id.txtUsuario);
        txtToken = (TextView) view.findViewById(R.id.txtToken);
        txtInfo = (TextView) view.findViewById(R.id.txtInfo);

        controllerLogin = new ControllerLogin(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (controllerLogin.validateLoginUser()) {
            ResponseLogin responseLogin = controllerLogin.getUserLoginAutoTo();
            txtUsuario.setText(String.format("E-mail: %s", responseLogin.getEmail()));
            txtToken.setText(String.format("Token: %s", responseLogin.getAuthToken()));
        } else {
            txtUsuario.setText(String.format("E-mail: %s", getUsuarioStatic()));
            txtToken.setText(String.format("Token: %s", getAuthTokenStatic()));
        }

    }
}
