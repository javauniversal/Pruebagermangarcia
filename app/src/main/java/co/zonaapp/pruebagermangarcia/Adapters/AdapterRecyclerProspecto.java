package co.zonaapp.pruebagermangarcia.Adapters;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import co.zonaapp.pruebagermangarcia.Model.InterfaceProspecto;
import co.zonaapp.pruebagermangarcia.Model.ResponseProspectos;
import co.zonaapp.pruebagermangarcia.R;

public class AdapterRecyclerProspecto extends RecyclerView.Adapter<InterfaceProspecto> {

    private Activity context;
    private List<ResponseProspectos> responseProspectosList;

    public AdapterRecyclerProspecto(Activity context, List<ResponseProspectos> responseProspectosList) {
        super();
        this.context = context;
        this.responseProspectosList = responseProspectosList;
    }

    @Override
    public InterfaceProspecto onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_prospecto, parent, false);
        return new InterfaceProspecto(v, context);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public void onBindViewHolder(InterfaceProspecto holder, final int position) {

        ResponseProspectos items = responseProspectosList.get(position);
        holder.txtNombre.setText(items.getName());
        holder.txtApellido.setText(items.getSurname());
        holder.txtCedula.setText(String.format("Cédula: %s", items.getSchProspectIdentification()));
        holder.txtTelefono.setText(String.format("Teléfono: %s", items.getTelephone()));

        /*  • pendiente = 0
            • aprobado = 1
            • aceptado = 2
            • rechazado = 3
            • desactivada = 4 */

        holder.txtEstado.setText("");
        holder.imgEstado.setColorFilter(null);

        if (items.getStatusCd() == 0) {
            holder.imgEstado.setColorFilter(Color.rgb(255, 154, 0));
            holder.txtEstado.setText("Pendiente");
        }

        if (items.getStatusCd() == 1) {
            holder.imgEstado.setColorFilter(Color.rgb(160, 255, 50));
            holder.txtEstado.setText("Aprobado");
        }

        if (items.getStatusCd() == 2) {
            holder.imgEstado.setColorFilter(Color.rgb(160, 255, 50));
            holder.txtEstado.setText("Aceptado");
        }

        if (items.getStatusCd() == 3) {
            holder.imgEstado.setColorFilter(Color.rgb(16, 98, 138));
            holder.txtEstado.setText("Rechazado");
        }

        if (items.getStatusCd() == 4) {
            holder.imgEstado.setColorFilter(Color.rgb(0, 0, 0));
            holder.txtEstado.setText("Desactivado");
        }

    }

    @Override
    public int getItemCount() {
        if (responseProspectosList == null) {
            return 0;
        } else {
            return responseProspectosList.size();
        }
    }

}
