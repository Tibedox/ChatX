package ru.tibedox.chatx;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MyApi {
    @GET("/chatx.php")
    Call<List<DataFromBase>> sendQuery(@Query("name") String name, @Query("message") String message);

    @GET("/chatx.php")
    Call<List<DataFromBase>> sendQuery(@Query("q") String word);
}
