package com.tryon.xuanhong.tryon;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Pinky on 13-Jun-17.
 */

public interface UserService {

    @GET("/api/headdata")
    Call<Head> getHeadData(@Query("userid") int ID);

}
