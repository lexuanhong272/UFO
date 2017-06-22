package com.tryon.xuanhong.tryon;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pinky on 21-Jun-17.
 */

public interface EmailLoginService {
        @GET("/api/emaillogin")
        Call<User> emailLogin(@Query("email") String email, @Query("password") String password);

}
