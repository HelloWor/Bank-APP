package com.example.bankapp;
import com.google.gson.annotations.SerializedName;
public class PlaceHolderPost {


    private String id;
    private String amount;
    private String iban;
    private String currency;

    @SerializedName("accountName")
    private String text;

    public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getAmount() {
            return amount;
        }
        public String getIban() {
            return iban;
        }
        public String getCurrency() {
            return currency;
        }







    }



