package com.hj.user.searchwifiservice.models;

import java.util.ArrayList;

/**
 * Created by USER on 2017-03-27.
 */

public class PublicWiFiPlaceInfo {
    private RESULT RESULT;

    private String list_total_count;

    private ArrayList<Row> row;

    public RESULT getRESULT() {
        return RESULT;
    }

    public void setRESULT(RESULT RESULT) {
        this.RESULT = RESULT;
    }

    public String getList_total_count() {
        return list_total_count;
    }

    public void setList_total_count(String list_total_count) {
        this.list_total_count = list_total_count;
    }

    public ArrayList<Row> getRow() {
        return row;
    }

    public void setRow(ArrayList<Row> row) {
        this.row = row;
    }

    @Override
    public String toString() {
        return "ClassPojo [RESULT = " + RESULT + ", list_total_count = " + list_total_count + ", row = " + row + "]";
    }


}