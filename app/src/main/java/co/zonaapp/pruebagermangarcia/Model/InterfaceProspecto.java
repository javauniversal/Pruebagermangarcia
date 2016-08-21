package co.zonaapp.pruebagermangarcia.Model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.zonaapp.pruebagermangarcia.R;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class InterfaceProspecto extends RecyclerView.ViewHolder {

    public TextView txtNombre;
    public TextView txtApellido;
    public TextView txtCedula;
    public TextView txtTelefono;
    public TextView txtEstado;
    public ImageView imgEstado;

    public InterfaceProspecto(View itemView, Context context) {
        super(itemView);
        this.txtNombre = (TextView) itemView.findViewById(R.id.txtNombre);
        this.txtApellido = (TextView) itemView.findViewById(R.id.txtApellido);
        this.txtCedula = (TextView) itemView.findViewById(R.id.txtCedula);
        this.txtTelefono = (TextView) itemView.findViewById(R.id.txtTelefono);
        this.txtEstado = (TextView) itemView.findViewById(R.id.txtEstado);
        this.imgEstado = (ImageView) itemView.findViewById(R.id.imgEstado);
    }
}
