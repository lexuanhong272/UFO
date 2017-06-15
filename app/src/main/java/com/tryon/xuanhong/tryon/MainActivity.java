package com.tryon.xuanhong.tryon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

public class MainActivity extends Activity {

    public String USER_ID = "-1";
    public static final String BASE_URL = "http://192.168.137.1:2055";
    public static final int PICK_IMAGE = 100;
    Button btnLoginFace, btnLoginEmail;
    FaceLoginService service;
    private Uri mUriPhotoTaken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnLoginFace = (Button) findViewById(R.id.btnLoginFace);
        btnLoginEmail = (Button) findViewById(R.id.btnLoginEmail);

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
            req.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Success log in", Toast.LENGTH_SHORT).show();
                        ResponseBody res = response.body();
                        Log.d("res", res.toString() + " == " + res.contentType() + " ++ " +res.contentLength() + " ** " + res.source().toString());
                        USER_ID = res.source() + "";
                        Toast.makeText(MainActivity.this, USER_ID, Toast.LENGTH_SHORT).show();

                        MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), HomeActivity.class));
                        MainActivity.this.finish();

                    }
                    else{
                        Toast.makeText(MainActivity.this, "New person! Please sign up first and then try to log in again", Toast.LENGTH_SHORT).show();
                        int sc = response.code();
                        Log.d("ERRORR", "" + sc);
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(MainActivity.this, "Fail log in task, check your wifi connection and try again", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

