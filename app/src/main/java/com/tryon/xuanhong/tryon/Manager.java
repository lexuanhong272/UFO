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

    private FaceLoginService mFaceLoginService;

    public FaceLoginService getFaceLoginService() {
        if (mFaceLoginService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mFaceLoginService = retrofit.create(FaceLoginService.class);
        }
        return mFaceLoginService;
    }

    private EmailLoginService mEmailLoginService;

    public EmailLoginService getEmailLoginService() {
        if (mEmailLoginService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mEmailLoginService = retrofit.create(EmailLoginService.class);
        }
        return mEmailLoginService;
    }

    private GlassesDataService mGlassesDataService;

    public GlassesDataService getGlassesDataService() {
        if (mGlassesDataService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.HTTP.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mGlassesDataService = retrofit.create(GlassesDataService.class);
        }
        return mGlassesDataService;
    }

}
