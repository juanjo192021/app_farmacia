package com.app.farmacia_fameza.services;

import com.app.farmacia_fameza.models.request.AuthRequest;
import com.app.farmacia_fameza.models.response.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthService {

    @POST("auth/login")
    Call<ApiResponse> login(@Body AuthRequest authRequest);
}
