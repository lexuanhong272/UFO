package com.tryon.xuanhong.tryon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pinky on 13-Jun-17.
 */

public interface UserService {
    @GET("/api/user")
    Call<List<User>> getUser();
}
