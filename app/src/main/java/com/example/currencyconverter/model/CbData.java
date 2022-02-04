package com.example.currencyconverter.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CbData implements Parcelable {
    private java.util.Date Date;
    private Date PreviousDate;
    private String PreviousURL;
    private Date Timestamp;
    private Map<String, Currency> Valute;

    public CbData(java.util.Date date, java.util.Date previousDate, String previousURL, java.util.Date timestamp, HashMap<String, Currency> valute) {
        Date = date;
        PreviousDate = previousDate;
        PreviousURL = previousURL;
        Timestamp = timestamp;
        Valute = valute;
    }

    protected CbData(Parcel in) {
        PreviousURL = in.readString();
    }

    public static final Creator<CbData> CREATOR = new Creator<CbData>() {
        @Override
        public CbData createFromParcel(Parcel in) {
            return new CbData(in);
        }

        @Override
        public CbData[] newArray(int size) {
            return new CbData[size];
        }
    };

    public java.util.Date getDate() {
        return Date;
    }

    public java.util.Date getPreviousDate() {
        return PreviousDate;
    }

    public String getPreviousURL() {
        return PreviousURL;
    }

    public java.util.Date getTimestamp() {
        return Timestamp;
    }

    public Map<String, Currency> getValute() {
        return Valute;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(PreviousURL);
    }
}
