package co.zonaapp.pruebagermangarcia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.melnykov.fab.ObservableScrollView;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import co.zonaapp.pruebagermangarcia.Controllers.ControllerProspectos;
import co.zonaapp.pruebagermangarcia.Model.EntEstandar;
import co.zonaapp.pruebagermangarcia.Model.ResponseProspectos;
import co.zonaapp.pruebagermangarcia.R;

public class ActEditProspecto extends BaseActivity {

    private ResponseProspectos mDescribable;
    private Spinner spinner_estado;
    private int idEstado;
    private EditText editNombre;
    private EditText editApellido;
    private EditText editTelefono;
    private EditText editIdentificacion;
    private EditText editDireccion;
    private EditText editCodeZona;
    private EditText editCodeBarrio;
    private EditText editCodeCity;
    private EditText EditComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_prospecto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        verifyPermission();

        controllerProspectos = new ControllerProspectos(this);

        ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.scrollViewPros);
        spinner_estado = (Spinner) findViewById(R.id.spinner_estado);

        final List<EntEstandar> entEstandar = new ArrayList<>();
        entEstandar.add(new EntEstandar(0, "Pendiente"));
        entEstandar.add(new EntEstandar(1, "Aprobado"));
        entEstandar.add(new EntEstandar(2, "Aceptado"));
        entEstandar.add(new EntEstandar(3, "Rechazado"));
        entEstandar.add(new EntEstandar(4, "Desactivado"));
        loadEstados(entEstandar);

        editNombre = (EditText) findViewById(R.id.editNombre);
        editApellido = (EditText) findViewById(R.id.editApellido);
        editTelefono = (EditText) findViewById(R.id.editTelefono);
        editIdentificacion = (EditText) findViewById(R.id.editIdentificacion);
        editDireccion = (EditText) findViewById(R.id.editDireccion);
        editCodeZona = (EditText) findViewById(R.id.editCodeZona);
        editCodeBarrio = (EditText) findViewById(R.id.editCodeBarrio);
        editCodeCity = (EditText) findViewById(R.id.editCodeCity);
        EditComment = (EditText) findViewById(R.id.EditComment);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabEdit);
        fab.attachToScrollView(scrollView);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validarCampos()) {
                    updateProspecto();
                }
            }

        });

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            mDescribable = (ResponseProspectos) bundle.getSerializable("value");
            editNombre.setText(mDescribable.getName());
            editApellido.setText(mDescribable.getSurname());
            editTelefono.setText(mDescribable.getTelephone());
            editIdentificacion.setText(mDescribable.getSchProspectIdentification());
            editDireccion.setText(mDescribable.getAddress());
            editCodeZona.setText(mDescribable.getZoneCode());
            editCodeBarrio.setText(mDescribable.getNeighborhoodCode());
            editCodeCity.setText(mDescribable.getCityCode());
            EditComment.setText(mDescribable.getObservation());
            selectSpinnerValue(entEstandar, spinner_estado, mDescribable.getStatusCd());
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ActEditProspecto.this, ActMainMenu.class));
                finish();
            }
        });
    }

    private void updateProspecto() {

        ResponseProspectos responseProspectos = new ResponseProspectos();
        responseProspectos.setId(mDescribable.getId());
        responseProspectos.setName(editNombre.getText().toString());
        responseProspectos.setSurname(editApellido.getText().toString());
        responseProspectos.setTelephone(editTelefono.getText().toString());
        responseProspectos.setSchProspectIdentification(editIdentificacion.getText().toString());
        responseProspectos.setAddress(editDireccion.getText().toString());
        responseProspectos.setStatusCd(idEstado);
        responseProspectos.setZoneCode(editCodeZona.getText().toString());
        responseProspectos.setNeighborhoodCode(editCodeBarrio.getText().toString());
        responseProspectos.setCityCode(editCodeCity.getText().toString());
        responseProspectos.setObservation(EditComment.getText().toString());

        if (controllerProspectos.uptadeCabezaPedidoLocal(responseProspectos)) {
            TastyToast.makeText(this, "El prospecto fue actualizado", TastyToast.LENGTH_LONG, TastyToast.SUCCESS);

            File file = new File (path + "/savedFile.txt");

            String log = String.format("* FECHA: %1$s \n ID: %2$s \n IDENTIFICACION: %3$s",
                    (DateFormat.format("yyyy-MM-dd HH:mm:ss", new java.util.Date()).toString()),
                    mDescribable.getId(),
                    editIdentificacion.getText().toString());

            appendLog(file, log);

            startActivity(new Intent(this, ActMainMenu.class));
            finish();

        } else {
            TastyToast.makeText(this, "Problemas al actualizar el prospecto", TastyToast.LENGTH_LONG, TastyToast.ERROR);
        }

    }

    private void loadEstados(final List<EntEstandar> entEstandar) {

        ArrayAdapter<EntEstandar> prec3 = new ArrayAdapter<>(this, R.layout.textview_spinner, entEstandar);
        spinner_estado.setAdapter(prec3);
        spinner_estado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idEstado = entEstandar.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }

        });
    }

    private void selectSpinnerValue(List<EntEstandar> ListaEstado, Spinner spinner, int id) {
        for (int i = 0; i < ListaEstado.size(); i++) {
            if (ListaEstado.get(i).getId() == id) {
                spinner.setSelection(i);
                break;
            }
        }
    }

    public boolean validarCampos() {

        boolean indicadorValidate = false;

        if (isValidString(editNombre.getText().toString().trim())) {
            editNombre.setFocusable(true);
            editNombre.setFocusableInTouchMode(true);
            editNombre.requestFocus();
            editNombre.setText("");
            editNombre.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editApellido.getText().toString().trim())) {
            editApellido.setFocusable(true);
            editApellido.setFocusableInTouchMode(true);
            editApellido.requestFocus();
            editApellido.setText("");
            editApellido.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editTelefono.getText().toString().trim())) {
            editTelefono.setFocusable(true);
            editTelefono.setFocusableInTouchMode(true);
            editTelefono.requestFocus();
            editTelefono.setText("");
            editTelefono.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editDireccion.getText().toString().trim())) {
            editDireccion.setFocusable(true);
            editDireccion.setFocusableInTouchMode(true);
            editDireccion.requestFocus();
            editDireccion.setText("");
            editDireccion.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editCodeZona.getText().toString().trim())) {
            editCodeZona.setFocusable(true);
            editCodeZona.setFocusableInTouchMode(true);
            editCodeZona.requestFocus();
            editCodeZona.setText("");
            editCodeZona.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editCodeBarrio.getText().toString().trim())) {
            editCodeBarrio.setFocusable(true);
            editCodeBarrio.setFocusableInTouchMode(true);
            editCodeBarrio.requestFocus();
            editCodeBarrio.setText("");
            editCodeBarrio.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(editCodeCity.getText().toString().trim())) {
            editCodeCity.setFocusable(true);
            editCodeCity.setFocusableInTouchMode(true);
            editCodeCity.requestFocus();
            editCodeCity.setText("");
            editCodeCity.setError("Este campo es obligatorio");
            indicadorValidate = true;
        } else if (isValidString(EditComment.getText().toString().trim())) {
            EditComment.setFocusable(true);
            EditComment.setFocusableInTouchMode(true);
            EditComment.requestFocus();
            EditComment.setText("");
            EditComment.setError("Este campo es obligatorio");
            indicadorValidate = true;
        }

        return indicadorValidate;

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ActMainMenu.class));
        finish();
        super.onBackPressed();
    }

}
