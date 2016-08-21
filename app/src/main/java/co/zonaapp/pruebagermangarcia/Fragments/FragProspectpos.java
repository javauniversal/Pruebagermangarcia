package co.zonaapp.pruebagermangarcia.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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
import com.melnykov.fab.FloatingActionButton;
import com.sdsmdg.tastytoast.TastyToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import co.zonaapp.pruebagermangarcia.Activities.ActEditProspecto;
import co.zonaapp.pruebagermangarcia.Activities.DialogProspecto;
import co.zonaapp.pruebagermangarcia.Adapters.AdapterRecyclerProspecto;
import co.zonaapp.pruebagermangarcia.Adapters.RecyclerItemClickListener;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerProspectos;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerSincronizar;
import co.zonaapp.pruebagermangarcia.Model.ListProspecto;
import co.zonaapp.pruebagermangarcia.Model.ResponseProspectos;
import co.zonaapp.pruebagermangarcia.R;

import static co.zonaapp.pruebagermangarcia.Model.ResponseLogin.getAuthTokenStatic;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragProspectpos extends BaseVolleyFragment implements View.OnClickListener {


    private RecyclerView recycler;
    private FloatingActionButton fab;

    public FragProspectpos() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_prospectpos, container, false);
        recycler = (RecyclerView) view.findViewById(R.id.recycler_prospecto);
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.attachToRecyclerView(recycler);
        fab.setOnClickListener(this);
        RecyclerView.LayoutManager lManager = new LinearLayoutManager(getActivity());
        recycler.setLayoutManager(lManager);

        controllerLogin = new ControllerLogin(getActivity());
        controllerProspectos = new ControllerProspectos(getActivity());
        controllerSincronizar = new ControllerSincronizar(getActivity());

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getProspecto();

    }

    private void getProspecto() {
        alertDialog.show();
        String url = String.format("%1$s%2$s", getString(R.string.url_base), "sch/prospects.json");
        StringRequest jsonRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        parseJSON(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                            TastyToast.makeText(getActivity(), "La aplicación ingreso sin acceso a internet.", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                            loadRecycleView();
                        } else if (error instanceof AuthFailureError) {
                            TastyToast.makeText(getActivity(), "Error Servidor", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ServerError) {
                            TastyToast.makeText(getActivity(), "Contraseña incorrecta", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof NetworkError) {
                            TastyToast.makeText(getActivity(), "Error de red", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        } else if (error instanceof ParseError) {
                            TastyToast.makeText(getActivity(), "Error al serializar los datos", TastyToast.LENGTH_LONG, TastyToast.ERROR);
                        }

                        alertDialog.dismiss();
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();

                if (controllerLogin.validateLoginUser()) {
                    headers.put("token", controllerLogin.getUserLoginAutoTo().getAuthToken());
                } else {
                    headers.put("token", getAuthTokenStatic());
                }

                return headers;
            }
        };
        addToQueue(jsonRequest);
    }

    private void parseJSON(String response) {
        Gson gson = new Gson();

        ListProspecto listProspecto = gson.fromJson(response, ListProspecto.class);

        String dateActual = controllerSincronizar.getSincronizar();

        if (controllerProspectos.validateProspectos()) {
            for (int i = 0; i < listProspecto.size(); i++) {
                if(listProspecto.get(i).getCreatedAt().split(Pattern.quote("."))[0].compareTo(dateActual) > 0) {
                    // Insert prospecto.
                    controllerProspectos.insertProspectos(listProspecto.get(i));
                } else if(listProspecto.get(i).getUpdatedAt().split(Pattern.quote("."))[0].compareTo(dateActual) > 0) {
                    //Update prospecto.
                }
            }
        } else {
            for (int i = 0; i < listProspecto.size(); i++) {
                controllerProspectos.insertProspectos(listProspecto.get(i));
            }
        }

        controllerSincronizar.insertSincronizar((DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", new java.util.Date()).toString()));

        loadRecycleView();

        alertDialog.dismiss();
    }

    private void loadRecycleView() {
        final List<ResponseProspectos> prospectosList = controllerProspectos.listProspectos();
        // Set RecyclerView...
        AdapterRecyclerProspecto adapter = new AdapterRecyclerProspecto(getActivity(), prospectosList);
        recycler.setAdapter(adapter);
        recycler.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getActivity(), ActEditProspecto.class);
                bundle.putSerializable("value", prospectosList.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().finish();

            }

        }));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                TastyToast.makeText(getActivity(), "Opción en desarrollo", TastyToast.LENGTH_LONG, TastyToast.WARNING);
                break;
        }
    }

}
