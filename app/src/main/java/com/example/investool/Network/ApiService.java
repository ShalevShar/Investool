package com.example.investool.Network;

import com.example.investool.Command.MiniAppCommandBoundary;
import com.example.investool.Superapp.SuperAppBoundary;
import com.example.investool.User.NewUser;
import com.example.investool.User.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/superapp/users/login/{superapp}/{email}")
    Call<User> getUser(@Path("superapp") String superapp, @Path("email") String email);

    @POST("/superapp/miniapp/{miniAppName}")
    Call<List<MiniAppCommandBoundary>> createMiniAppCommand(@Body MiniAppCommandBoundary miniAppCommand, @Path("miniAppName") String miniAppName);

    @POST("/superapp/users")
    Call<User> createUser(@Body NewUser userBoundary);

    @POST("/superapp/objects")
    Call<SuperAppBoundary> createSuperApp(@Body SuperAppBoundary superAppBoundary);
}