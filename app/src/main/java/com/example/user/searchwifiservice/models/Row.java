package com.example.user.searchwifiservice.models;

/**
 * Created by USER on 2017-03-27.
 */

public class Row {
    private String PLACE_NAME;

    private String CATEGORY;

    private String INSTL_X;

    private String INSTL_Y;

    private String GU_NM;

    private String INSTL_DIV;

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

    @Override
    public String toString() {
        return "ClassPojo [PLACE_NAME = " + PLACE_NAME + ", CATEGORY = " + CATEGORY + ", INSTL_X = " + INSTL_X + ", INSTL_Y = " + INSTL_Y + ", GU_NM = " + GU_NM + ", INSTL_DIV = " + INSTL_DIV + "]";
    }

}