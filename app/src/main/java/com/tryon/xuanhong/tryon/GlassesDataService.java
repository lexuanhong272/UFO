package com.tryon.xuanhong.tryon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Pinky on 21-Jun-17.
 */

public interface GlassesDataService {
    @GET("/api/glassesdata")
    Call<GlassesData> getGlassesData(@Query("id") String ID);
}
