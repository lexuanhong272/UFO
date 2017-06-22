package com.tryon.xuanhong.tryon;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.util.Base64;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tryon.xuanhong.tryon.MainActivity.mainUser;

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

    private Manager mManager;
    List<Glasses> userGlasses;

    public static GlassesData mGlassesData;
    public static Head mHead;
    public static String IDGLASS = "glasses1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mManager = new Manager();


        Call<Head> callDataHead = mManager.getUserService().getHeadData(mainUser.getId());
        callDataHead.enqueue(new Callback<Head>() {
            @Override
            public void onResponse(Call<Head> call, Response<Head> response) {
                if(response.isSuccessful()){
                    mHead = response.body();

                    File root = android.os.Environment.getExternalStorageDirectory();
                    File dir = new File(root.getAbsolutePath() + "/Heads/");
                    dir.mkdirs();

                    byte[] OBJ = Base64.decode(mHead.getObj(), Base64.DEFAULT);

                    File fileOBJ = new File(dir, mainUser.getEmail() + ".obj");
                    try {
                        FileOutputStream f = new FileOutputStream(fileOBJ);
                        f.write(OBJ);
                        //Toast.makeText(HomeActivity.this, "Saved OBJ " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                    }


                    byte[] MTL = Base64.decode(mHead.getMtl(), Base64.DEFAULT);

                    File fileMTL = new File(dir, mainUser.getEmail() + ".mtl");
                    try {
                        FileOutputStream f = new FileOutputStream(fileMTL);
                        f.write(MTL);
                        //Toast.makeText(HomeActivity.this, "Saved MTL " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                    }

                    byte[] PNG1 = Base64.decode(mHead.getPng1(), Base64.DEFAULT);

                    File filePNG1 = new File(dir, mainUser.getEmail() + "_face.png");
                    try {
                        FileOutputStream f = new FileOutputStream(filePNG1);
                        f.write(PNG1);
                        //Toast.makeText(HomeActivity.this, "Saved PNG 1 " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                    }

                    byte[] PNG2 = Base64.decode(mHead.getPng2(), Base64.DEFAULT);

                    File filePNG2 = new File(dir, mainUser.getEmail() + "_hair.png");
                    try {
                        FileOutputStream f = new FileOutputStream(filePNG2);
                        f.write(PNG2);
                        Toast.makeText(HomeActivity.this, "Saved data head success " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<Head> call, Throwable t) {

            }
        });


        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        View header = navigationView.getHeaderView(0);
        txt_name = (TextView) header.findViewById(R.id.txt_username);
        txt_email = (TextView) header.findViewById(R.id.txt_useremail);
        imv_avatar = (ImageView) header.findViewById(R.id.imgv_avatar);
        txt_name.setText(mainUser.getName());
        txt_email.setText(mainUser.getEmail());


        File root = android.os.Environment.getExternalStorageDirectory();
        File img = new File(root.getAbsolutePath() + "/DCIM/AVATAR/mainUser.jpg");

        if(img.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(img.getAbsolutePath());

            imv_avatar.setImageBitmap(myBitmap);

        }

        ButterKnife.inject(this);
        fragmentManager = getSupportFragmentManager();
        setupView();



        final SearchAdapter mAdapter = new GlassesAdapter(mGlasses, this).registerFilter(Glasses.class, "Id").setIgnoreCase(true);

        Call<List<Glasses>> listCall = mManager.getGlassesService().getAllGlassesInfo();
        listCall.enqueue(new Callback<List<Glasses>>() {
            @Override
            public void onResponse(Call<List<Glasses>> call, Response<List<Glasses>> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(HomeActivity.this, "Get glasses successfully", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(HomeActivity.this, "Fail on get glasses", Toast.LENGTH_LONG).show();
                return;
            }

        });

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                selectDrawerItem(menuItem);
                return true;
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
                IDGLASS = chose.getId();

                File x = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Glasses/" + IDGLASS + ".obj");

                if(x.exists() == false){
                    Call<GlassesData> callGlassesData = mManager.getGlassesDataService().getGlassesData(IDGLASS);

                    callGlassesData.enqueue(new Callback<GlassesData>() {
                        @Override
                        public void onResponse(Call<GlassesData> call, Response<GlassesData> response) {
                            mGlassesData = response.body();
                            File root = android.os.Environment.getExternalStorageDirectory();
                            File dir = new File(root.getAbsolutePath() + "/Glasses/");
                            dir.mkdirs();
                            byte[] OBJ = Base64.decode(mGlassesData.getObj(), Base64.DEFAULT);
                            File file1 = new File(dir, IDGLASS + ".obj");
                            try {
                                FileOutputStream f = new FileOutputStream(file1);

                                f.write(OBJ);
                                //Toast.makeText(MainActivity.this, "Saved avatar " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //Toast.makeText(MainActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                //Toast.makeText(MainActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                            }

                            byte[] MTL = Base64.decode(mGlassesData.getMtl(), Base64.DEFAULT);

                            File file2 = new File(dir, IDGLASS + ".mtl");
                            try {
                                FileOutputStream f = new FileOutputStream(file2);

                                f.write(MTL);
                                //Toast.makeText(MainActivity.this, "Saved avatar " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                                //Toast.makeText(MainActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                //Toast.makeText(MainActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                            }

                            Toast.makeText(HomeActivity.this, "Get glassses suuuuccccesss " + IDGLASS, Toast.LENGTH_LONG).show();

                        }

                        @Override
                        public void onFailure(Call<GlassesData> call, Throwable t) {
                            Toast.makeText(HomeActivity.this, "Faillllll", Toast.LENGTH_LONG).show();

                        }
                    });
                }

                Intent intent = new Intent(HomeActivity.this, ModelActivity.class);
                intent.putExtra("Name", chose.getName());
                intent.putExtra("Id", chose.getId());
                intent.putExtra("Brigde", chose.getBridge());
                intent.putExtra("Eye", chose.getEye());
                intent.putExtra("Temple", chose.getTemple());
                intent.putExtra("Price", chose.getPrice());
                intent.putExtra("Producer", chose.getProducer());
                intent.putExtra("Status", chose.getStatus());
                intent.putExtra("Color", chose.getColor());

                HomeActivity.this.startActivity(intent);
                //Toast.makeText(Navigation.this, "DA SENT", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void selectDrawerItem(MenuItem menuItem) {

        switch (menuItem.getItemId()) {
            case R.id.drawer_home:
                //Toast.makeText(Navigation.this, "Click on Home", Toast.LENGTH_SHORT).show();
//                HomeActivity.this.startActivity(new Intent(HomeActivity.this.getApplicationContext(), HomeActivity.class));
//                HomeActivity.this.finish();
                break;
            case R.id.drawer_wishlist:
                //Toast.makeText(Navigation.this, "Click on Log out", Toast.LENGTH_SHORT).show();
                HomeActivity.this.startActivity(new Intent(HomeActivity.this.getApplicationContext(), WishListActivity.class));
                HomeActivity.this.finish();
                break;
            case R.id.drawer_logout:
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
                break;
        }

        try {
            //Fragment fragment = (Fragment) fragmentClass.newInstance();
            //fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //setToolbarElevation(specialToolbarBehaviour);
        menuItem.setChecked(true);
        setTitle(menuItem.getTitle());
        drawerLayout.closeDrawers();
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

    private void setupView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
