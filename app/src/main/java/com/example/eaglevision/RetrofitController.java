package com.example.eaglevision;

import com.google.gson.JsonObject;
import com.squareup.okhttp.ResponseBody;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;


class RetrofitController {


    public interface EagleVisionApi
{
    @GET("/{user}")
    Call<ResponseBody> greetUser(@Path("user") String user);


    @Headers("Content-type: application/json")
    @POST("/api/post_some_data")
    Call<ResponseBody> getVectors(@Body JsonObject body);


}
    OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    Retrofit retrofit = new Retrofit.Builder().baseUrl("http://135.222.36.7:5000").addConverterFactory(GsonConverterFactory.create()).build();
    EagleVisionApi service = retrofit.create(EagleVisionApi.class);


}

