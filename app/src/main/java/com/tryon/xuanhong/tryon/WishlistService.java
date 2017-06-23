package com.tryon.xuanhong.tryon;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by Pinky on 22-Jun-17.
 */

public interface WishlistService {

    @GET("/api/wishlist")
    Call<List<Glasses>> getWishList(@Query("userid") int userid);

    @POST("/api/wishlist")
    Call<Integer> addWishList(@Query("userid") int userid, @Query("glassesid") String glassesid);

    @DELETE("/api/wishlist")
    Call<Integer> removeWishList(@Query("userid") int userid, @Query("glassesid") String glassesid);


}
