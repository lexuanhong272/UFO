package com.tryon.xuanhong.tryon;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pinky on 13-Jun-17.
 */

public class Manager {
    private GlassesInfoService mGlassesService;

    public GlassesInfoService getGlassesService() {
        if (mGlassesService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGlassesService = retrofit.create(GlassesInfoService.class);
        }
        return mGlassesService;
    }

    private UserService mUserService;

    public UserService getUserService() {
        if (mUserService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mUserService = retrofit.create(UserService.class);
        }
        return mUserService;
    }



}
