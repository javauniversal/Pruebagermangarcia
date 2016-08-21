package co.zonaapp.pruebagermangarcia.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerLogin;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerProspectos;
import co.zonaapp.pruebagermangarcia.Controllers.ControllerSincronizar;
import co.zonaapp.pruebagermangarcia.R;
import dmax.dialog.SpotsDialog;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class BaseVolleyFragment extends Fragment {

    private VolleyS volley;
    protected RequestQueue fRequestQueue;
    public ControllerLogin controllerLogin;
    public ControllerProspectos controllerProspectos;
    public ControllerSincronizar controllerSincronizar;
    public SpotsDialog alertDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        volley = VolleyS.getInstance(getActivity().getApplicationContext());
        fRequestQueue = volley.getRequestQueue();
        alertDialog = new SpotsDialog(getActivity(), R.style.Custom);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (fRequestQueue != null) {
            fRequestQueue.cancelAll(this);
        }
    }

    public void addToQueue(Request request) {
        if (request != null) {
            request.setTag(this);
            if (fRequestQueue == null)
                fRequestQueue = volley.getRequestQueue();
            request.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            ));

            //onPreStartConnection(alertDialog);

            fRequestQueue.add(request);
        }
    }

}
