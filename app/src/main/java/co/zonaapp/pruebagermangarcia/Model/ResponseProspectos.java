package co.zonaapp.pruebagermangarcia.Model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by hp_gergarga on 20/08/2016.
 */
public class ResponseProspectos implements Serializable {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("surname")
    private String surname;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("schProspectIdentification")
    private String schProspectIdentification;

    @SerializedName("address")
    private String address;

    @SerializedName("createdAt")
    private String createdAt;

    @SerializedName("updatedAt")
    private String updatedAt;

    @SerializedName("statusCd")
    private int statusCd;

    @SerializedName("zoneCode")
    private String zoneCode;

    @SerializedName("neighborhoodCode")
    private String neighborhoodCode;

    @SerializedName("cityCode")
    private String cityCode;

    @SerializedName("sectionCode")
    private String sectionCode;

    @SerializedName("roleId")
    private int roleId;

    @SerializedName("appointableId")
    @Nullable
    private int appointableId;

    @SerializedName("rejectedObservation")
    @Nullable
    private String rejectedObservation;

    @SerializedName("observation")
    private String observation;

    @SerializedName("disable")
    private boolean disable;

    @SerializedName("visited")
    private boolean visited;

    @SerializedName("callcenter")
    private boolean callcenter;

    @SerializedName("acceptSearch")
    private boolean acceptSearch;

    @SerializedName("campaignCode")
    private int campaignCode;

    @SerializedName("userId")
    @Nullable
    private int userId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSchProspectIdentification() {
        return schProspectIdentification;
    }

    public void setSchProspectIdentification(String schProspectIdentification) {
        this.schProspectIdentification = schProspectIdentification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public int getStatusCd() {
        return statusCd;
    }

    public void setStatusCd(int statusCd) {
        this.statusCd = statusCd;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    public String getNeighborhoodCode() {
        return neighborhoodCode;
    }

    public void setNeighborhoodCode(String neighborhoodCode) {
        this.neighborhoodCode = neighborhoodCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getAppointableId() {
        return appointableId;
    }

    public void setAppointableId(int appointableId) {
        this.appointableId = appointableId;
    }

    public String getRejectedObservation() {
        return rejectedObservation;
    }

    public void setRejectedObservation(String rejectedObservation) {
        this.rejectedObservation = rejectedObservation;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public boolean isDisable() {
        return disable;
    }

    public void setDisable(boolean disable) {
        this.disable = disable;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isCallcenter() {
        return callcenter;
    }

    public void setCallcenter(boolean callcenter) {
        this.callcenter = callcenter;
    }

    public boolean isAcceptSearch() {
        return acceptSearch;
    }

    public void setAcceptSearch(boolean acceptSearch) {
        this.acceptSearch = acceptSearch;
    }

    public int getCampaignCode() {
        return campaignCode;
    }

    public void setCampaignCode(int campaignCode) {
        this.campaignCode = campaignCode;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
