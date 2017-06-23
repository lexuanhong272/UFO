package com.tryon.xuanhong.tryon.object3D.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.GLSurfaceView;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tryon.xuanhong.tryon.GlassesData;
import com.tryon.xuanhong.tryon.Manager;
import com.tryon.xuanhong.tryon.R;
import com.tryon.xuanhong.tryon.object3D.services.LoaderObjects;
import com.tryon.xuanhong.tryon.object3D.services.LoaderScenes;
import com.tryon.xuanhong.tryon.object3D.utils.Utils;

import java.io.File;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tryon.xuanhong.tryon.HomeActivity.IDGLASS;

import static com.tryon.xuanhong.tryon.HomeActivity.myFavochose;
import static com.tryon.xuanhong.tryon.MainActivity.mainUser;

public class ModelActivity extends Activity {

	private String paramAssetDir;
	private String paramAssetFilename;

	private String paramFilename;

	private GLSurfaceView gLView;

	private LoaderScenes scene;

	private Handler handler;

	Button btnXUP;
	Button btnXDOWN;
	Button btnYUP, btnYDOWN, btnZUP, btnZDOWN;
	Button btnRotateXUP, btnRotateXDOWN;
	ImageButton btnFavorite;

	LinearLayout linearLayoutFavorite;
	LinearLayout linearLayoutDetails;
	boolean isPressed = true;
	LinearLayout linearLayoutAddToCart;
    LinearLayout linearLayout;
	LinearLayout linearLayoutSpinner;

	public static float radient = 0.05f;
	public static int buttonWidth = 210;
	public static String colorUp = "#4F9C5F";
	public static String colorDown = "#3C7896";
	Manager mManager;

	TextView txtDetails;

	Spinner spMonHoc;
	ArrayList<Float> arrMonHoc;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		spMonHoc = new Spinner(this);
		arrMonHoc = new ArrayList<Float>();

		arrMonHoc.add(0.05f);
		arrMonHoc.add(0.1f);
		arrMonHoc.add(0.15f);
		arrMonHoc.add(0.2f);
		arrMonHoc.add(0.25f);
		arrMonHoc.add(0.3f);
		arrMonHoc.add(0.35f);
		arrMonHoc.add(0.4f);
		arrMonHoc.add(0.45f);
		arrMonHoc.add(0.5f);

		ArrayAdapter adapter = new ArrayAdapter(ModelActivity.this, android.R.layout.simple_spinner_dropdown_item, arrMonHoc);
		spMonHoc.setAdapter(adapter);
		spMonHoc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				Toast.makeText(ModelActivity.this, "Set gradient = " +arrMonHoc.get(position), Toast.LENGTH_SHORT).show();
				radient = arrMonHoc.get(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		Bundle b = getIntent().getExtras();
		if (b != null) {
			this.paramAssetDir = b.getString("assetDir");
			this.paramAssetFilename = b.getString("assetFilename");
			this.paramFilename = b.getString("uri");
		}
		Log.i("Renderer", "Params: assetDir '" + paramAssetDir + "', assetFilename '" + paramAssetFilename + "', uri '"
				+ paramFilename + "'");

		handler = new Handler(getMainLooper());

		final Intent mychose = getIntent();
		String Id = mychose.getStringExtra("Id");
		String Name = mychose.getStringExtra("Name");
		String Price = mychose.getStringExtra("Price");
		String Producer = mychose.getStringExtra("Producer");
		String Temple = mychose.getStringExtra("Temple");
		String Eye = mychose.getStringExtra("Eye");
		String Bridge = mychose.getStringExtra("Bridge");
		String Status = mychose.getStringExtra("Status");
		String Color = mychose.getStringExtra("Color");

		String details =
		"\n\n       " + Name + "\n\n" + "       " + Price + " $" + "\n\n" + "       " + Producer + "\n\n" +
		"       " + Status;


		Toast.makeText(ModelActivity.this, Id + Price + Producer, Toast.LENGTH_LONG).show();

		txtDetails = new TextView(this);
		//txtDetails.setBackgroundColor(android.graphics.Color.parseColor("#FFEFD5"));
		//txtDetails.setHeight(700);
		//txtDetails.setWidth(450);
		txtDetails.setText(details);
		txtDetails.setTextSize(18);

		gLView = new ModelSurfaceView(this);

		btnFavorite = new ImageButton(this);
		btnFavorite.setBackgroundResource(R.drawable.heartempty);
		btnFavorite.setScaleX(0.5f);
		btnFavorite.setScaleY(0.5f);

		mManager = new Manager();

		btnFavorite.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isPressed){
					v.setBackgroundResource(R.drawable.heartfull);
					Toast.makeText(ModelActivity.this, "Add to Wish List!", Toast.LENGTH_SHORT).show();


					Call<Integer> callAddWL = mManager.getWishlistService().addWishList(mainUser.getId(), IDGLASS);

					callAddWL.enqueue(new Callback<Integer>() {
						@Override
						public void onResponse(Call<Integer> call, Response<Integer> response) {

						}

						@Override
						public void onFailure(Call<Integer> call, Throwable t) {
							//Toast.makeText(ModelActivity.this, "faill", Toast.LENGTH_LONG).show();

						}
					});
					//Call<GlassesData> callGlassesData = mManager.getGlassesDataService().getGlassesData(IDGLASS);


				}else{
					v.setBackgroundResource(R.drawable.heartempty);
					Toast.makeText(ModelActivity.this, "Remove from Wish List!", Toast.LENGTH_SHORT).show();

					Call<Integer> callRemoveWL = mManager.getWishlistService().removeWishList(mainUser.getId(), IDGLASS);

					callRemoveWL.enqueue(new Callback<Integer>() {
						@Override
						public void onResponse(Call<Integer> call, Response<Integer> response) {

						}

						@Override
						public void onFailure(Call<Integer> call, Throwable t) {
							Toast.makeText(ModelActivity.this, "faill", Toast.LENGTH_LONG).show();

						}
					});

                }
				isPressed = !isPressed; // reverse

			}
		});


		btnXUP = new Button(this);
		btnXUP.setBackgroundColor(android.graphics.Color.parseColor(colorUp));
		btnXUP.setWidth(buttonWidth);

		btnXUP.setText("X+");
		btnXUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

                float tem = posBBBBB + radient;
                if(tem <= 1.5f){
                    posBBBBB += radient;
                    LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
                }
                else{
                    Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
                }

			}
		});


		btnXDOWN = new Button(this);
		btnXDOWN.setText("X-");
		btnXDOWN.setBackgroundColor(android.graphics.Color.parseColor(colorDown));
		btnXDOWN.setWidth(buttonWidth);

		btnXDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

				float tem = posBBBBB - radient;
				if(tem >= -1.5f){
					posBBBBB -= radient;
					LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				}
				else{
					Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
				}


			}
		});

		btnYUP = new Button(this);
		btnYUP.setText("Y+");
		btnYUP.setBackgroundColor(android.graphics.Color.parseColor(colorUp));
		btnYUP.setWidth(buttonWidth);

		btnYUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

				float tem = posNNNNN + radient;
				if(tem <= 1.5f){
					posNNNNN += radient;
					LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				}
				else{
					Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
				}


			}
		});


		btnYDOWN = new Button(this);
		btnYDOWN.setText("Y-");
		btnYDOWN.setWidth(buttonWidth);
		btnYDOWN.setBackgroundColor(android.graphics.Color.parseColor(colorDown));

		btnYDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

				float tem = posNNNNN - radient;
				if(tem >= -1.5f){
					posNNNNN -= radient;
					LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				}
				else{
					Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
				}
			}
		});

		btnZUP = new Button(this);
		btnZUP.setText("Z+");
		btnZUP.setWidth(buttonWidth);
		btnZUP.setBackgroundColor(android.graphics.Color.parseColor(colorUp));

		btnZUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

				float tem = posMMMMM + radient;
				if(tem <= 1.5f){
					posMMMMM += radient;
					LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				}
				else{
					Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
				}

			}
		});

		btnZDOWN = new Button(this);
		btnZDOWN.setText("Z-");
		btnZDOWN.setWidth(buttonWidth);
		btnZDOWN.setBackgroundColor(android.graphics.Color.parseColor(colorDown));

		btnZDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();

				float tem = posMMMMM - radient;
				if(tem >= -1.5f){
					posMMMMM -= radient;
					LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				}
				else{
					Toast.makeText(ModelActivity.this, "Vuot qua gioi han cho phep ^^ ", Toast.LENGTH_LONG).show();
				}

			}
		});


		btnRotateXUP = new Button(this);
		btnRotateXUP.setText("Rotate X+");
		btnRotateXUP.setWidth(buttonWidth + 50);
		btnRotateXUP.setBackgroundColor(android.graphics.Color.parseColor(colorUp));

		btnRotateXUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float scale1[] = new float[3];
				scale1 = LoaderObjects.objGlass.getRotation();
				scale1[0] += radient * 100 ;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale1);
			}
		});


		btnRotateXDOWN = new Button(this);
		btnRotateXDOWN.setText("Rotate X-");
		btnRotateXDOWN.setWidth(buttonWidth + 50);
		btnRotateXDOWN.setBackgroundColor(android.graphics.Color.parseColor(colorDown));

		btnRotateXDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float scale2[] = new float[3];
				scale2 = LoaderObjects.objGlass.getRotation();
				scale2[0] -= radient * 100;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale2);
			}
		});


		linearLayoutSpinner = new LinearLayout(this);
		linearLayoutSpinner.setGravity(Gravity.CENTER | Gravity.RIGHT);
		linearLayoutSpinner.addView(spMonHoc);

		linearLayoutFavorite = new LinearLayout(this);
		linearLayoutFavorite.setGravity(Gravity.TOP | Gravity.RIGHT);
		linearLayoutFavorite.addView(btnFavorite);

		linearLayoutDetails = new LinearLayout(this);
		linearLayoutDetails.setGravity(Gravity.TOP | Gravity.LEFT);
		linearLayoutDetails.addView(txtDetails);


		linearLayoutAddToCart = new LinearLayout(this);
		linearLayoutAddToCart.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		linearLayoutAddToCart.setOrientation(LinearLayout.HORIZONTAL);

        linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        linearLayout.addView(btnXUP);
        linearLayout.addView(btnXDOWN);
		linearLayout.addView(btnYUP);
		linearLayout.addView(btnYDOWN);
		linearLayout.addView(btnZUP);
		linearLayout.addView(btnZDOWN);
		linearLayoutAddToCart.addView(btnRotateXUP);
		linearLayoutAddToCart.addView(btnRotateXDOWN);


		setContentView(gLView);
		addContentView(linearLayoutFavorite, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		addContentView(linearLayoutDetails, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		addContentView(linearLayoutSpinner, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        addContentView(linearLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		addContentView(linearLayoutAddToCart, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		if (paramFilename == null && paramAssetFilename == null) {
			scene = new LoaderObjects(this);
		} else {
			scene = new LoaderScenes(this);
		}
		scene.init();

		// Show the Up button in the action bar.
		//setupActionBar();

		// TODO: Alert user when there is no multitouch support (2 fingers). He won't be able to rotate or zoom for
		// example
		Utils.printTouchCapabilities(getPackageManager());

		setupOnSystemVisibilityChangeListener();


	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			 getActionBar().setDisplayHomeAsUpEnabled(false);
			 getActionBar().setDisplayUseLogoEnabled(false);
			 getActionBar().setHomeButtonEnabled(false);
		 }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.model, menu);
		getActionBar().setHomeButtonEnabled(true);
		return true;
	}



	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupOnSystemVisibilityChangeListener() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		getWindow().getDecorView().setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
			@Override
			public void onSystemUiVisibilityChange(int visibility) {
				// Note that system bars will only be "visible" if none of the
				// LOW_PROFILE, HIDE_NAVIGATION, or FULLSCREEN flags are set.
				if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
					// TODO: The system bars are visible. Make any desired
					// adjustments to your UI, such as showing the action bar or
					// other navigational controls.
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
						hideSystemUIDelayed(3000);
					}
				} else {
					// TODO: The system bars are NOT visible. Make any desired
					// adjustments to your UI, such as hiding the action bar or
					// other navigational controls.
				}
			}
		});
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		//getActionBar().setHomeButtonEnabled(true);
		if (hasFocus) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
				hideSystemUIDelayed(3000);
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//getActionBar().setHomeButtonEnabled(true);
		switch (item.getItemId()) {

			case R.id.s1:

				float scale1[] = new float[3];
				scale1 = LoaderObjects.objGlass.getRotation();

				scale1[0] += 1.5;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale1);


				break;
			case R.id.s2:
				float scale2[] = new float[3];
				scale2 = LoaderObjects.objGlass.getRotation();

				scale2[0] -= 1.5f;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale2);

				break;

		case R.id.y1:
			//Toast.makeText(ModelActivity.this, "Y++", Toast.LENGTH_SHORT).show();
			float pos3X = LoaderObjects.objGlass.getPositionX();
			float pos3Y = LoaderObjects.objGlass.getPositionY();
			float pos3Z = LoaderObjects.objGlass.getPositionZ();
			pos3Y += 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos3X, pos3Y, pos3Z});

			//Toast.makeText(ModelActivity.this, "Y++ DONE", Toast.LENGTH_SHORT).show();
			break;
		case R.id.y2:
			//Toast.makeText(ModelActivity.this, "Y--", Toast.LENGTH_SHORT).show();
			float pos4X = LoaderObjects.objGlass.getPositionX();
			float pos4Y = LoaderObjects.objGlass.getPositionY();
			float pos4Z = LoaderObjects.objGlass.getPositionZ();
			pos4Y -= 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos4X, pos4Y, pos4Z});

			//Toast.makeText(ModelActivity.this, "Y-- DONE", Toast.LENGTH_SHORT).show();
			break;
		case R.id.x1:
			//Toast.makeText(ModelActivity.this, "X++", Toast.LENGTH_SHORT).show();
			float pos1X = LoaderObjects.objGlass.getPositionX();
			float pos1Y = LoaderObjects.objGlass.getPositionY();
			float pos1Z = LoaderObjects.objGlass.getPositionZ();
			pos1X += 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos1X, pos1Y, pos1Z});

			//Toast.makeText(ModelActivity.this, "X++ DONE", Toast.LENGTH_SHORT).show();
			break;
		case R.id.x2:
			//Toast.makeText(ModelActivity.this, "X--", Toast.LENGTH_SHORT).show();
			float pos2X = LoaderObjects.objGlass.getPositionX();
			float pos2Y = LoaderObjects.objGlass.getPositionY();
			float pos2Z = LoaderObjects.objGlass.getPositionZ();
			pos2X -= 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos2X, pos2Y, pos2Z});
			Log.d("PPPP", "" + pos2X + " /// " + pos2Y + " /// " + pos2Z);
			Toast.makeText(ModelActivity.this, "X-- DONE", Toast.LENGTH_SHORT).show();
			break;

		case R.id.z1:
			//Toast.makeText(ModelActivity.this, "Y++", Toast.LENGTH_SHORT).show();
			float pos5X = LoaderObjects.objGlass.getPositionX();
			float pos5Y = LoaderObjects.objGlass.getPositionY();
			float pos5Z = LoaderObjects.objGlass.getPositionZ();
			pos5Z += 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos5X, pos5Y, pos5Z});

			//Toast.makeText(ModelActivity.this, "Y++ DONE", Toast.LENGTH_SHORT).show();
			break;
		case R.id.z2:
			//Toast.makeText(ModelActivity.this, "Y--", Toast.LENGTH_SHORT).show();
			float pos6X = LoaderObjects.objGlass.getPositionX();
			float pos6Y = LoaderObjects.objGlass.getPositionY();
			float pos6Z = LoaderObjects.objGlass.getPositionZ();
			pos6Z -= 0.1f;
			LoaderObjects.objGlass.setPosition(new float[] {pos6X, pos6Y, pos6Z});

			//Toast.makeText(ModelActivity.this, "Y-- DONE", Toast.LENGTH_SHORT).show();
			break;

		}
		return super.onOptionsItemSelected(item);
	}

	private void hideSystemUIDelayed(long millis) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		handler.postDelayed(new Runnable() {
			public void run() {
				hideSystemUI();
			}
		}, millis);
	}

	private void hideSystemUI() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			hideSystemUIKitKat();
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
			hideSystemUIJellyBean();
		}
	}

	// This snippet hides the system bars.
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private void hideSystemUIKitKat() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}
		// Set the IMMERSIVE flag.
		// Set the content to appear under the system bars so that the content
		// doesn't resize when the system bars hide and show.
		final View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
				| View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
				| View.SYSTEM_UI_FLAG_IMMERSIVE);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void hideSystemUIJellyBean() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			return;
		}
		final View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LOW_PROFILE);
	}

	// This snippet shows the system bars. It does this by removing all the flags
	// except for the ones that make the content appear under the system bars.
	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	private void showSystemUI() {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
			return;
		}
		final View decorView = getWindow().getDecorView();
		decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
	}

	public File getParamFile() {
		return getParamFilename() != null ? new File(getParamFilename()) : null;
	}

	public String getParamAssetDir() {
		return paramAssetDir;
	}

	public String getParamAssetFilename() {
		return paramAssetFilename;
	}

	public String getParamFilename() {
		return paramFilename;
	}

	public LoaderScenes getScene() {
		return scene;
	}

	public GLSurfaceView getgLView() {
		return gLView;
	}

}
