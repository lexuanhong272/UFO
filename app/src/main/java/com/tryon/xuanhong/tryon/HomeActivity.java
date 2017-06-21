package com.tryon.xuanhong.tryon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.tryon.xuanhong.tryon.object3D.views.ModelActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private List<Glasses> mGlasses = new ArrayList<>();

    @InjectView(R.id.grid_view)
    GridView gridView;

    @InjectView(R.id.search_edit_text)
    EditText editText;

    ActionBarDrawerToggle drawerToggle;
    DrawerLayout drawerLayout;
    Toolbar toolbar;

    FragmentManager fragmentManager;
    NavigationView navigationView;
    FrameLayout frameLayout;

    private Manager mManager;
    List<Glasses> userGlasses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Intent myIntent = getIntent();
        Bundle myBundle =  myIntent.getBundleExtra("bundle");
        if(myBundle != null) {
            name = myBundle.getString("NAMEUSER");
            email = myBundle.getString("EMAILUSER");
            arr = myBundle.getByteArray("AVATAR");
        }
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        txt_name = (TextView) header.findViewById(R.id.txt_username);
        txt_email = (TextView) header.findViewById(R.id.txt_useremail);
        imv_avatar = (ImageView) header.findViewById(R.id.imgv_avatar);
        txt_name.setText(name);
        txt_email.setText(email);
        //Bitmap bmp = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        //imv_avatar.setImageBitmap(bmp);
        ButterKnife.inject(this);
        fragmentManager = getSupportFragmentManager();
        setupView();

        mManager = new Manager();

        final SearchAdapter mAdapter = new GlassesAdapter(mGlasses, this).registerFilter(Glasses.class, "Id").setIgnoreCase(true);

        Call<List<Glasses>> listCall = mManager.getGlassesService().getAllGlassesInfo();
        listCall.enqueue(new Callback<List<Glasses>>() {
            @Override
            public void onResponse(Call<List<Glasses>> call, Response<List<Glasses>> response) {
                if(response.isSuccessful()){
                    Toast.makeText(HomeActivity.this, "Get glasses successfully", Toast.LENGTH_SHORT).show();
                    userGlasses = response.body();
                    for(int i = 0; i < userGlasses.size(); i++) {
                        Glasses glasses = userGlasses.get(i);
                        mGlasses.add(glasses);
                    }
                    Log.d("mGlasses", mGlasses.toString());
                    gridView.setAdapter(mAdapter);
                    Log.d("mAdapter", mAdapter.toString());
                }
                else {
                    Toast.makeText(HomeActivity.this, "Error on get glasses", Toast.LENGTH_SHORT).show();
                    int sc = response.code();
                    switch (sc){
                    }
                }
            }
            @Override
            public void onFailure(Call<List<Glasses>> call, Throwable t) {
                Toast.makeText(HomeActivity.this, "Fail to get glasses", Toast.LENGTH_SHORT).show();
                return;
            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                mAdapter.filter("");
                mAdapter.notifyDataSetChanged();
            }

            @Override public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.filter(s.toString());
                mAdapter.notifyDataSetChanged();
            }

            @Override public void afterTextChanged(Editable s) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Glasses chose = (Glasses) gridView.getItemAtPosition(position);
                Intent intent = new Intent(HomeActivity.this, ModelActivity.class);
                intent.putExtra("ID_Glasses", chose.getId());
                HomeActivity.this.startActivity(intent);
                //Toast.makeText(Navigation.this, "DA SENT", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        HomeActivity.this.startActivity(new Intent(HomeActivity.this.getApplicationContext(), MainActivity.class));
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    TextView txt_name;
    TextView txt_email;
    ImageView imv_avatar;

    String name;
    String email;
    byte[] arr;


    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //toolbar.setLogo(R.drawable.ic_launcher);
        //frameLayout = (FrameLayout) findViewById(R.id.content_frame);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                //selectDrawerItem(menuItem);
                return true;
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        drawerToggle.syncState();
        super.onPostCreate(savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

}
