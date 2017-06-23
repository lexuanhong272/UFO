package com.tryon.xuanhong.tryon;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.tryon.xuanhong.tryon.Constant.HTTP.BASE_URL;

public class MainActivity extends Activity {


    public static final int PICK_IMAGE = 100;
    Button btnLoginFace, btnLoginEmail;
    private Uri mUriPhotoTaken;

    public static User mainUser;
    private Manager mManager;

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Log out")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginFace = (Button) findViewById(R.id.btnLoginFace);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);

        mManager = new Manager();

        if (btnLoginFace != null) {
            btnLoginFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                        try {
                            File file = File.createTempFile("temp", ".png", storageDir);
                            mUriPhotoTaken = Uri.fromFile(file);
                            intent.putExtra("data", mUriPhotoTaken);
                            startActivityForResult(intent, PICK_IMAGE);
                        } catch (IOException e) {
                        }
                    }
                }
            });
        }

        if(btnLoginEmail != null){
            btnLoginEmail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), LoginEmailActivity.class));
                MainActivity.this.finish();

                }
            });
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap bitmap = (Bitmap) extras.get("data");
            String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
            OutputStream outStream = null;
            String filename = "temp";
            File file = new File(extStorageDirectory, filename + ".png");
            if (file.exists()) {
                file.delete();
                file = new File(extStorageDirectory, filename + ".png");
            }
            try {
                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part body = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "upload_test");


            Call<User> callUser = mManager.getFaceLoginService().faceLogin(body, name);


            callUser.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Log in successfully", Toast.LENGTH_SHORT).show();
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

                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), HomeActivity.class));
                        MainActivity.this.finish();

                    }

                    else {
                        Toast.makeText(MainActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(MainActivity.this, "LOG IN FAIL", Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
