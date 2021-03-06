package com.tryon.xuanhong.tryon;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import static com.tryon.xuanhong.tryon.HomeActivity.IDGLASS;
import static com.tryon.xuanhong.tryon.MainActivity.mainUser;

public class WishListActivity extends AppCompatActivity {


    List<Glasses> favo = new ArrayList<>();
    Manager m = new Manager();
    List<Glasses> temp;

    GridView gv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);


        gv = (GridView) findViewById(R.id.grid_view);


        new AsyncTask<Void, Void, Void>() {
            ProgressDialog dialog = new ProgressDialog(WishListActivity.this);

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //Toast.makeText(WishListActivity.this, "Please wait a few senconds while load wish list", Toast.LENGTH_LONG).show();
            }

            @Override
            protected Void doInBackground(Void... params) {

                Call<List<Glasses>> call = m.getWishlistService().getWishList(mainUser.getId());
                call.enqueue(new Callback<List<Glasses>>() {
                    @Override
                    public void onResponse(Call<List<Glasses>> call, Response<List<Glasses>> response) {
                        if(response.isSuccessful()){
                            temp = response.body();
                            for (int i = 0; i < temp.size(); i++) {
                                favo.add(temp.get(i));
                                //Toast.makeText(WishListActivity.this, "666 " + i, Toast.LENGTH_SHORT).show();
                            }
                            GlassesFullAdapter adapterFa = new GlassesFullAdapter(WishListActivity.this, R.layout.row_glasses_full, favo);
                            gv.setAdapter(adapterFa);
                        }
                        else {
                            Toast.makeText(WishListActivity.this, "Fail on get wish list", Toast.LENGTH_LONG).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<List<Glasses>> call, Throwable t) {
                        Toast.makeText(WishListActivity.this, "Fail on get wish list", Toast.LENGTH_LONG).show();

                    }
                });
                return null;
            }

        }.execute();


    }

    @Override
    public void onBackPressed() {
        WishListActivity.this.startActivity(new Intent(WishListActivity.this.getApplicationContext(), HomeActivity.class));
        WishListActivity.this.finish();
    }



}


/*



Call<List<Glasses>> call = m.getWishlistService().getWishList(mainUser.getId());
                call.enqueue(new Callback<List<Glasses>>() {
                    @Override
                    public void onResponse(Call<List<Glasses>> call, Response<List<Glasses>> response) {
                        temp = response.body();
                        for (int i = 0; i < temp.size(); i++) {

                            favo.add(temp.get(i));
                            Toast.makeText(WishListActivity.this, "666 " + i, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Glasses>> call, Throwable t) {
                        Toast.makeText(WishListActivity.this, "fail", Toast.LENGTH_LONG).show();

                    }
                });
 */