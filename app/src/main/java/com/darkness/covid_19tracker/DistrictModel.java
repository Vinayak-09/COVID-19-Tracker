package com.darkness.covid_19tracker;

public class DistrictModel {

    String districtName;
    int activePatients;
    int confirmedCases;
    int deceasedPatients;
    int recoveredPatients;

    public DistrictModel() {
    }

    public DistrictModel(String districtName, int activePatients, int confirmedCases, int deceasedPatients, int recoveredPatients) {
        this.districtName = districtName;
        this.activePatients = activePatients;
        this.confirmedCases = confirmedCases;
        this.deceasedPatients = deceasedPatients;
        this.recoveredPatients = recoveredPatients;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public int getActivePatients() {
        return activePatients;
    }

    public void setActivePatients(int activePatients) {
        this.activePatients = activePatients;
    }

    public int getConfirmedCases() {
        return confirmedCases;
    }

    public void setConfirmedCases(int confirmedCases) {
        this.confirmedCases = confirmedCases;
    }

    public int getDeceasedPatients() {
        return deceasedPatients;
    }

    public void setDeceasedPatients(int deceasedPatients) {
        this.deceasedPatients = deceasedPatients;
    }

    public float getDeathRate() {
        if(confirmedCases == 0){
            return 100;
        }else {
            return (((float)recoveredPatients / confirmedCases) * 100);
        }
    }



    public int getRecoveredPatients() {
        return recoveredPatients;
    }

    public void setRecoveredPatients(int recoveredPatients) {
        this.recoveredPatients = recoveredPatients;
    }



}
