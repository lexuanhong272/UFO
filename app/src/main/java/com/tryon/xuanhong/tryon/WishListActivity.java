package com.tryon.xuanhong.tryon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WishListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
    }

    @Override
    public void onBackPressed() {
        WishListActivity.this.startActivity(new Intent(WishListActivity.this.getApplicationContext(), HomeActivity.class));
        WishListActivity.this.finish();
    }
}
