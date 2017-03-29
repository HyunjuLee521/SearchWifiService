package com.example.user.searchwifiservice.models;

import io.realm.RealmObject;

/**
 * Created by USER on 2017-03-29.
 */

public class Info extends RealmObject {

    String PLACE_NAME;

    String CATEGORY;

    String INSTL_X;

    String INSTL_Y;

    String GU_NM;

    String INSTL_DIV;

    int id;


    public String getPLACE_NAME() {
        return PLACE_NAME;
    }

    public void setPLACE_NAME(String PLACE_NAME) {
        this.PLACE_NAME = PLACE_NAME;
    }

    public String getCATEGORY() {
        return CATEGORY;
    }

    public void setCATEGORY(String CATEGORY) {
        this.CATEGORY = CATEGORY;
    }

    public String getINSTL_X() {
        return INSTL_X;
    }

    public void setINSTL_X(String INSTL_X) {
        this.INSTL_X = INSTL_X;
    }

    public String getINSTL_Y() {
        return INSTL_Y;
    }

    public void setINSTL_Y(String INSTL_Y) {
        this.INSTL_Y = INSTL_Y;
    }

    public String getGU_NM() {
        return GU_NM;
    }

    public void setGU_NM(String GU_NM) {
        this.GU_NM = GU_NM;
    }

    public String getINSTL_DIV() {
        return INSTL_DIV;
    }

    public void setINSTL_DIV(String INSTL_DIV) {
        this.INSTL_DIV = INSTL_DIV;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Info{" +
                "PLACE_NAME='" + PLACE_NAME + '\'' +
                ", CATEGORY='" + CATEGORY + '\'' +
                ", INSTL_X='" + INSTL_X + '\'' +
                ", INSTL_Y='" + INSTL_Y + '\'' +
                ", GU_NM='" + GU_NM + '\'' +
                ", INSTL_DIV='" + INSTL_DIV + '\'' +
                ", id=" + id +
                '}';
    }
}
