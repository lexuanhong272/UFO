package com.tryon.xuanhong.tryon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
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
    FaceLoginService service;
    private Uri mUriPhotoTaken;

    public User mainUser;
    private Manager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginFace = (Button) findViewById(R.id.btnLoginFace);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);

        manager = new Manager();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        service = new Retrofit.Builder().baseUrl(BASE_URL).client(client).build().create(FaceLoginService.class);

        if (btnLoginFace != null) {
            btnLoginFace.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if(intent.resolveActivity(getPackageManager()) != null) {
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

            retrofit2.Call<okhttp3.ResponseBody> req = service.postImage(body, name);
            Toast.makeText(MainActivity.this, "Please wait a few seconds while authenticating", Toast.LENGTH_LONG).show();
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Success log in", Toast.LENGTH_SHORT).show();
                        final ResponseBody res = response.body();
                        Log.d("res", res.toString() + " == " + res.contentType() + " ++ " +res.contentLength() + " ** " + res.source().toString());

                        String content = res.source().toString().substring(0, res.source().toString().length() - 1);

                        Log.d("content", content);
                        int id_pos = content.indexOf("Id");
                        int name_pos = content.indexOf("Name");
                        int email_pos = content.indexOf("Email");
                        Log.d("id_pos", id_pos + "");
                        Log.d("name_pos", name_pos + "");
                        Log.d("email_pos", email_pos + "");


//                        mainUser = (User) res.source();
//                        Log.d("mainUser", "" + mainUser);




//                        int len = res.source().toString().length();
//
//                        //USER_ID = Integer.parseInt(String.valueOf(res.source().toString().substring(7, len - 2)));
//                        Log.d("userid",USER_ID + "");
//                        Log.d("id", res.source().toString().substring(7, len - 2) + "");
//
//
//
//
//                        Call<User> callUserId = manager.getUserService().getUserWithId(USER_ID);
//
//                        callUserId.enqueue(new Callback<User>() {
//                            @Override
//                            public void onResponse(Call<User> call, Response<User> response) {
//                                mainUser = response.body();
//                                byte[] bytearrayMTL = Base64.decode(mainUser.getAvatar(), Base64.DEFAULT);
//
//
//                                File root1 = android.os.Environment.getExternalStorageDirectory();
//                                File dir1 = new File(root1.getAbsolutePath() + "/DCIM/AVATAR/");
//                                dir1.mkdirs();
//                                File file1 = new File(dir1, "mainUser" + ".jpg");
//                                try {
//                                    FileOutputStream f = new FileOutputStream(file1);
//
//                                    f.write(bytearrayMTL);
//                                    Toast.makeText(MainActivity.this, "Saved avatar " + mainUser.getId(), Toast.LENGTH_SHORT).show();
//                                } catch (FileNotFoundException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(MainActivity.this, "fffffff", Toast.LENGTH_SHORT).show();
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                    Toast.makeText(MainActivity.this, "fffffffdddd", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<User> call, Throwable t) {
//                                Toast.makeText(MainActivity.this, "fail get user id", Toast.LENGTH_SHORT).show();
//                            }
//                        });

                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), HomeActivity.class));
                        MainActivity.this.finish();

                    }
                    else{
                        Toast.makeText(MainActivity.this, "New person or can not detect face!", Toast.LENGTH_LONG).show();
                        Toast.makeText(MainActivity.this, "Please sign up at PC first then try to log in again", Toast.LENGTH_LONG).show();

                        int sc = response.code();
                        Log.d("ERRORR", "" + sc);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(MainActivity.this, "Fail log in task, check your wifi connection and try again", Toast.LENGTH_LONG).show();
                }
            });



        }
    }
}

