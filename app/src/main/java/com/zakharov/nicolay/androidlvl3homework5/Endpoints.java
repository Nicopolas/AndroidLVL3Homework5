package com.zakharov.nicolay.androidlvl3homework5;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface Endpoints {

    @GET("users")
    Call<List<Model>> loadUsers();
}
