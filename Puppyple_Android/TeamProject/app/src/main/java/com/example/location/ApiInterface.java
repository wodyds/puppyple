package com.example.location;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface ApiInterface {
    //장소이름으로 검색
    @GET("v2/local/search/keyword.json")
    Call<CategoryResult> getSearchLocation(
            @Header("Authorization") String token,
            @Query("query") String query,
            @Query("x") String x,
            @Query("y") String y,
            @Query("radius") int radius
    );

    @GET("v2/local/search/keyword.json")
    Call<CategoryResult> getSearchLocationDetail(
            @Header("Authorization") String token,
            @Query("query") String query,
            @Query("x") String x,
            @Query("y") String y,
            @Query("size") int size
    );
}