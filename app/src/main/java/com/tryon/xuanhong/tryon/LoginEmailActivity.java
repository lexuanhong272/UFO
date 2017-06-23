package com.tryon.xuanhong.tryon;

import android.content.Intent;
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
                String e = txtEmail.getText().toString();
                String p = txtPass.getText().toString();

                mManager = new Manager();


                Call<User> callUser = mManager.getEmailLoginService().emailLogin(e, p);

                callUser.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(LoginEmailActivity.this, "Log in successfully", Toast.LENGTH_SHORT).show();
                            mainUser = response.body();


                            byte[] bytearrayMTL = Base64.decode(mainUser.getAvatar(), Base64.DEFAULT);

                            File root1 = android.os.Environment.getExternalStorageDirectory();
                            File dir1 = new File(root1.getAbsolutePath() + "/DCIM/");
                            dir1.mkdirs();
                            File file1 = new File(dir1, "mainUser" + ".jpg");
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

                            LoginEmailActivity.this.startActivity(new Intent(LoginEmailActivity.this.getApplicationContext(), HomeActivity.class));
                            LoginEmailActivity.this.finish();

                        }

                        else {
                            Toast.makeText(LoginEmailActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
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
                    }
                });


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
