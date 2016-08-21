package co.zonaapp.pruebagermangarcia.Controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import co.zonaapp.pruebagermangarcia.DataBase.DBHelper;
import co.zonaapp.pruebagermangarcia.Model.ResponseProspectos;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class ControllerProspectos {

    private SQLiteDatabase database;

    public ControllerProspectos(Context context) {
        DBHelper dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public boolean insertProspectos(ResponseProspectos data) {

        ContentValues values = new ContentValues();
        try {
            values.put("id", data.getId());
            values.put("name", data.getName());
            values.put("surname", data.getSurname());
            values.put("telephone", data.getTelephone());
            values.put("schProspectIdentification", data.getSchProspectIdentification());
            values.put("address", data.getAddress());
            values.put("createdAt", data.getCreatedAt());
            values.put("updatedAt", data.getUpdatedAt());
            values.put("statusCd", data.getStatusCd());
            values.put("zoneCode", data.getZoneCode());
            values.put("neighborhoodCode", data.getNeighborhoodCode());
            values.put("cityCode", data.getCityCode());
            values.put("sectionCode", data.getSectionCode());
            values.put("roleId", data.getRoleId());
            values.put("appointableId", data.getAppointableId());
            values.put("rejectedObservation", data.getRejectedObservation());
            values.put("observation", data.getObservation());
            values.put("disable", data.isDisable());
            values.put("visited", data.isVisited());
            values.put("callcenter", data.isCallcenter());
            values.put("acceptSearch", data.isAcceptSearch());
            values.put("campaignCode", data.getCampaignCode());
            values.put("userId", data.getUserId());

            database.insert("prospectos", null, values);
        } catch (SQLiteConstraintException e) {
            Log.d("data", "failure to insert word,", e);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }

    public boolean validateProspectos() {
        Cursor cursor;
        boolean indicador = false;
        String sql = "SELECT * FROM prospectos";
        cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            indicador = true;
        }
        return indicador;
    }

    public List<ResponseProspectos> listProspectos() {

        List<ResponseProspectos> responseProspectosList = new ArrayList<>();
        String sql = "SELECT * FROM prospectos";
        Cursor cursor = database.rawQuery(sql, null);
        ResponseProspectos responseProspectos;
        if (cursor.moveToFirst()) {
            do {
                responseProspectos = new ResponseProspectos();
                responseProspectos.setId(cursor.getString(0));
                responseProspectos.setName(cursor.getString(1));
                responseProspectos.setSurname(cursor.getString(2));
                responseProspectos.setTelephone(cursor.getString(3));
                responseProspectos.setSchProspectIdentification(cursor.getString(4));
                responseProspectos.setAddress(cursor.getString(5));
                responseProspectos.setCreatedAt(cursor.getString(6));
                responseProspectos.setUpdatedAt(cursor.getString(7));
                responseProspectos.setStatusCd(cursor.getInt(8));
                responseProspectos.setZoneCode(cursor.getString(9));
                responseProspectos.setNeighborhoodCode(cursor.getString(10));
                responseProspectos.setCityCode(cursor.getString(11));
                responseProspectos.setSectionCode(cursor.getString(12));
                responseProspectos.setRoleId(cursor.getInt(13));
                responseProspectos.setAppointableId(cursor.getInt(14));
                responseProspectos.setRejectedObservation(cursor.getString(15));
                responseProspectos.setObservation(cursor.getString(16));

                /*responseProspectos.setDisable(cursor.getDouble(16));

                values.put("disable", data.isDisable());
                values.put("visited", data.isVisited());
                values.put("callcenter", data.isCallcenter());
                values.put("acceptSearch", data.isAcceptSearch());
                values.put("campaignCode", data.getCampaignCode());
                values.put("userId", data.getUserId());*/

                responseProspectosList.add(responseProspectos);

            } while (cursor.moveToNext());
        }

        return responseProspectosList;
    }

    public boolean uptadeCabezaPedidoLocal(ResponseProspectos data){

        ContentValues values = new ContentValues();
        values.put("id", data.getId());
        values.put("name", data.getName());
        values.put("surname", data.getSurname());
        values.put("telephone", data.getTelephone());
        values.put("address", data.getAddress());
        values.put("updatedAt", (DateFormat.format("yyyy-MM-dd'T'HH:mm:ss", new java.util.Date()).toString()));
        values.put("statusCd", data.getStatusCd());
        values.put("zoneCode", data.getZoneCode());
        values.put("neighborhoodCode", data.getNeighborhoodCode());
        values.put("cityCode", data.getCityCode());
        values.put("observation", data.getObservation());

        String[] args = new String[]{String.valueOf(data.getId())};
        int p = database.update("prospectos", values, "id = ?", args);

        return p > 0;
    }
}
