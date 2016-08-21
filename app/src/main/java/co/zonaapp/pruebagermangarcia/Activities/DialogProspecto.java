package co.zonaapp.pruebagermangarcia.Activities;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import co.zonaapp.pruebagermangarcia.R;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class DialogProspecto extends Dialog {

    String message;
    TextView messageTextView;
    String title;
    TextView titleTextView;

    EditText editNombre;
    EditText editApellido;
    EditText editTelefono;
    EditText editIdentificacion;
    EditText editDireccion;

    Button buttonAccept;
    Button buttonCancel;

    View.OnClickListener onAcceptButtonClickListener;
    View.OnClickListener onCancelButtonClickListener;

    public DialogProspecto(Context context, String title) {
        super(context);
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_prospecto);

        this.titleTextView = (TextView) findViewById(R.id.title);
        setTitle(title);

        this.editNombre = (EditText) findViewById(R.id.editNombre);
        setEditNombre(editNombre);

        this.editApellido = (EditText) findViewById(R.id.editApellido);
        setEditApellido(editApellido);

        this.editTelefono = (EditText) findViewById(R.id.editTelefono);
        setEditTelefono(editTelefono);

        this.editIdentificacion = (EditText) findViewById(R.id.editIdentificacion);
        setEditIdentificacion(editIdentificacion);

        this.editDireccion = (EditText) findViewById(R.id.editDireccion);
        setEditDireccion(editDireccion);

        this.buttonAccept = (Button) findViewById(R.id.button_accept);
        buttonAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (onAcceptButtonClickListener != null)
                    onAcceptButtonClickListener.onClick(v);
            }
        });

        this.buttonCancel = (Button) findViewById(R.id.button_cancel);
        buttonCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dismiss();
                if (onCancelButtonClickListener != null)
                    onCancelButtonClickListener.onClick(v);
            }
        });
    }

    // GETERS & SETTERS
    public EditText getEditNombre() {
        return editNombre;
    }

    public EditText getEditApellido() {
        return editApellido;
    }

    public EditText getEditTelefono() {
        return editTelefono;
    }

    public EditText getEditIdentificacion() {
        return editIdentificacion;
    }

    public EditText getEditDireccion() {
        return editDireccion;
    }

    public void setEditNombre(EditText editNombre) {
        this.editNombre = editNombre;
    }

    public void setEditApellido(EditText editApellido) {
        this.editApellido = editApellido;
    }

    public void setEditTelefono(EditText editTelefono) {
        this.editTelefono = editTelefono;
    }

    public void setEditIdentificacion(EditText editIdentificacion) {
        this.editIdentificacion = editIdentificacion;
    }

    public void setEditDireccion(EditText editDireccion) {
        this.editDireccion = editDireccion;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        messageTextView.setText(message);
    }

    public TextView getMessageTextView() {
        return messageTextView;
    }

    public void setMessageTextView(TextView messageTextView) {
        this.messageTextView = messageTextView;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        if (title == null)
            titleTextView.setVisibility(View.GONE);
        else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }
    }

    public TextView getTitleTextView() {
        return titleTextView;
    }

    public void setTitleTextView(TextView titleTextView) {
        this.titleTextView = titleTextView;
    }

    public Button getButtonAccept() {
        return buttonAccept;
    }

    public void setButtonAccept(Button buttonAccept) {
        this.buttonAccept = buttonAccept;
    }

    public Button getButtonCancel() {
        return buttonCancel;
    }

    public void setButtonCancel(Button buttonCancel) {
        this.buttonCancel = buttonCancel;
    }

    public void setOnAcceptButtonClickListener(
            View.OnClickListener onAcceptButtonClickListener) {
        this.onAcceptButtonClickListener = onAcceptButtonClickListener;
        if (buttonAccept != null)
            buttonAccept.setOnClickListener(onAcceptButtonClickListener);
    }

    public void setOnCancelButtonClickListener(
            View.OnClickListener onCancelButtonClickListener) {
        this.onCancelButtonClickListener = onCancelButtonClickListener;
        if (buttonCancel != null)
            buttonCancel.setOnClickListener(onAcceptButtonClickListener);
    }
}
