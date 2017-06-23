package com.tryon.xuanhong.tryon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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

        Call<List<Glasses>> call = m.getWishlistService().getWishList(mainUser.getId());
        call.enqueue(new Callback<List<Glasses>>() {
            @Override
            public void onResponse(Call<List<Glasses>> call, Response<List<Glasses>> response) {
                temp = response.body();
                for(int i = 0; i < temp.size(); i++){

                    favo.add(temp.get(i));
                    Toast.makeText(WishListActivity.this, "666 " + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Glasses>> call, Throwable t) {

            }
        });

        GlassesFullAdapter adapterFa = new GlassesFullAdapter(WishListActivity.this, R.layout.row_glasses_full, favo);


        gv.setAdapter(adapterFa);


    }

    @Override
    public void onBackPressed() {
        WishListActivity.this.startActivity(new Intent(WishListActivity.this.getApplicationContext(), HomeActivity.class));
        WishListActivity.this.finish();
    }
}
