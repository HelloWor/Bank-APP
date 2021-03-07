package com.example.bankapp;


import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
public interface GetData {
    @GET("accounts")
    Call<List<PlaceHolderPost>> getAccounts();
}