package com.tryon.xuanhong.tryon;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tryon.xuanhong.tryon.MainActivity.mainUser;

public class LoginEmailActivity extends AppCompatActivity {

    Button btnLoginEmail, btnBack;
    EditText txtEmail, txtPass;

    Manager mManager;
    String e;
    String p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_email);

        btnBack = (Button) findViewById(R.id.btnBack);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);
        txtEmail = (EditText) findViewById(R.id.edtEmail);
        txtPass = (EditText) findViewById(R.id.edtPassword);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailActivity.this.startActivity(new Intent(LoginEmailActivity.this.getApplicationContext(), MainActivity.class));
                LoginEmailActivity.this.finish();
            }
        });

        btnLoginEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                e = txtEmail.getText().toString();
                p = txtPass.getText().toString();

                mManager = new Manager();


                new AsyncTask<Void, Void, Void>() {

                    @Override
                    protected void onPreExecute() {
                        super.onPreExecute();
                        Toast.makeText(LoginEmailActivity.this, "Please wait a few seconds while authenticating", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    protected Void doInBackground(Void... params) {

                        Call<User> callUser = mManager.getEmailLoginService().emailLogin(e, p);

                        callUser.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {

                                    mainUser = response.body();


                                    byte[] bytearrayMTL = Base64.decode(mainUser.getAvatar(), Base64.DEFAULT);

                                    File root1 = android.os.Environment.getExternalStorageDirectory();
                                    File dir1 = new File(root1.getAbsolutePath());
                                    dir1.mkdirs();
                                    File file1 = new File(dir1, "temp" + ".png");
                                    try {
                                        FileOutputStream f = new FileOutputStream(file1);

                                        f.write(bytearrayMTL);
                                        //Toast.makeText(MainActivity.this, "Saved avatar " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                        //Toast.makeText(MainActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                        //Toast.makeText(MainActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                                    }

                                    // Load head
                                    //mManager = new Manager();

                                    File aa = new File(android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Heads/" + mainUser.getEmail() + ".obj");

                                    if(aa.exists() == false){

                                        Call<Head> callDataHead = mManager.getUserService().getHeadData(mainUser.getId());
                                        callDataHead.enqueue(new Callback<Head>() {
                                            @Override
                                            public void onResponse(Call<Head> call, Response<Head> response) {
                                                if(response.isSuccessful()){
                                                    Head mHead = response.body();

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
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                                                    }


                                                    byte[] MTL = Base64.decode(mHead.getMtl(), Base64.DEFAULT);

                                                    File fileMTL = new File(dir, mainUser.getEmail() + ".mtl");
                                                    try {
                                                        FileOutputStream f = new FileOutputStream(fileMTL);
                                                        f.write(MTL);
                                                        //Toast.makeText(HomeActivity.this, "Saved MTL " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                                                    }

                                                    byte[] PNG1 = Base64.decode(mHead.getPng1(), Base64.DEFAULT);

                                                    File filePNG1 = new File(dir, mainUser.getEmail() + "_face.png");
                                                    try {
                                                        FileOutputStream f = new FileOutputStream(filePNG1);
                                                        f.write(PNG1);
                                                        //Toast.makeText(HomeActivity.this, "Saved PNG 1 " + mainUser.getId(), Toast.LENGTH_SHORT).show();
                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                                                    }

                                                    byte[] PNG2 = Base64.decode(mHead.getPng2(), Base64.DEFAULT);

                                                    File filePNG2 = new File(dir, mainUser.getEmail() + "_hair.png");
                                                    try {
                                                        FileOutputStream f = new FileOutputStream(filePNG2);
                                                        f.write(PNG2);


                                                    } catch (FileNotFoundException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        //Toast.makeText(LoginEmailActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
                                                    }
                                                    Toast.makeText(LoginEmailActivity.this, "New head cached " + mainUser.getId(), Toast.LENGTH_LONG).show();
                                                    Toast.makeText(LoginEmailActivity.this, "Log in success", Toast.LENGTH_SHORT).show();
                                                    LoginEmailActivity.this.startActivity(new Intent(LoginEmailActivity.this.getApplicationContext(), HomeActivity.class));
                                                    LoginEmailActivity.this.finish();

                                                }
                                                else {
                                                    Toast.makeText(LoginEmailActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
                                                    Toast.makeText(LoginEmailActivity.this, "PLEASE SIGN UP OR TRY AGAIN", Toast.LENGTH_LONG).show();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<Head> call, Throwable t) {
                                                Toast.makeText(LoginEmailActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
                                                Toast.makeText(LoginEmailActivity.this, "PLEASE SIGN UP OR TRY AGAIN", Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                    else {
                                        Toast.makeText(LoginEmailActivity.this, "Old head", Toast.LENGTH_LONG).show();
                                        Toast.makeText(LoginEmailActivity.this, "Log in success", Toast.LENGTH_SHORT).show();
                                        LoginEmailActivity.this.startActivity(new Intent(LoginEmailActivity.this.getApplicationContext(), HomeActivity.class));
                                        LoginEmailActivity.this.finish();
                                    }


                                }

                                else {
                                    Toast.makeText(LoginEmailActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
                                    Toast.makeText(LoginEmailActivity.this, "PLEASE SIGN UP OR TRY AGAIN", Toast.LENGTH_LONG).show();
                                }
//                    int sc = response.code();
//                    switch (sc) {
//                        case 404:
//                            Toast.makeText(MainActivity.this, "Can not find your data", Toast.LENGTH_LONG).show();
//                            Toast.makeText(MainActivity.this, "Please sign up", Toast.LENGTH_LONG).show();
//                            break;
//                    }

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                                //Toast.makeText(MainActivity.this, "SERVER NOT RESPONSE OR TIME CONSUMING", Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginEmailActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
                                Toast.makeText(LoginEmailActivity.this, "PLEASE SIGN UP OR TRY AGAIN", Toast.LENGTH_LONG).show();
                            }
                        });

                        return null;
                    }
                }.execute();



            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        LoginEmailActivity.this.startActivity(new Intent(LoginEmailActivity.this.getApplicationContext(), MainActivity.class));
        LoginEmailActivity.this.finish();
    }
}
