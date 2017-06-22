package com.tryon.xuanhong.tryon.object3D.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tryon.xuanhong.tryon.GlassesData;
import com.tryon.xuanhong.tryon.Manager;
import com.tryon.xuanhong.tryon.R;
import com.tryon.xuanhong.tryon.object3D.services.LoaderObjects;
import com.tryon.xuanhong.tryon.object3D.services.LoaderScenes;
import com.tryon.xuanhong.tryon.object3D.utils.Utils;

import java.io.File;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.tryon.xuanhong.tryon.HomeActivity.IDGLASS;
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
	ImageButton btnBackFromModel;
	ImageButton btnFavorite;

	LinearLayout linearLayoutFavorite;
	LinearLayout linearLayoutBackFromModel;
	boolean isPressed = true;
	LinearLayout linearLayoutAddToCart;
    LinearLayout linearLayout;

	Manager mManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//setContentView(R.layout.activity_demo);

		// Try to get input parameters
		Bundle b = getIntent().getExtras();
		if (b != null) {
			this.paramAssetDir = b.getString("assetDir");
			this.paramAssetFilename = b.getString("assetFilename");
			this.paramFilename = b.getString("uri");
		}
		Log.i("Renderer", "Params: assetDir '" + paramAssetDir + "', assetFilename '" + paramAssetFilename + "', uri '"
				+ paramFilename + "'");

		handler = new Handler(getMainLooper());


		Intent mychose = getIntent();
		String contentIdGlass = mychose.getStringExtra("ID_Glasses");
		Toast.makeText(ModelActivity.this, contentIdGlass, Toast.LENGTH_LONG).show();

		// Create a GLSurfaceView instance and set it
		// as the ContentView for this Activity.
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
							if(response.isSuccessful()){
								Toast.makeText(ModelActivity.this, mainUser.getId() + " == " +IDGLASS, Toast.LENGTH_LONG).show();

							}
							else {
								Toast.makeText(ModelActivity.this, "FAILL", Toast.LENGTH_LONG).show();

							}
						}

						@Override
						public void onFailure(Call<Integer> call, Throwable t) {
							Toast.makeText(ModelActivity.this, "faill", Toast.LENGTH_LONG).show();

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
							if(response.isSuccessful()){
								Toast.makeText(ModelActivity.this, mainUser.getId() + " == " +IDGLASS, Toast.LENGTH_LONG).show();

							}
							else {
								Toast.makeText(ModelActivity.this, "FAILL", Toast.LENGTH_LONG).show();

							}
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
		btnXUP.setText("X ++");
		btnXUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posBBBBB += 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "X ++ ^^", Toast.LENGTH_SHORT).show();
			}
		});


		btnXDOWN = new Button(this);
		btnXDOWN.setText("X --");
		btnXDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posBBBBB -= 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "X -- ^^", Toast.LENGTH_SHORT).show();
			}
		});

		btnYUP = new Button(this);
		btnYUP.setText("Y ++");
		btnYUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posNNNNN += 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "Y ++ ^^", Toast.LENGTH_SHORT).show();
			}
		});


		btnYDOWN = new Button(this);
		btnYDOWN.setText("Y --");
		btnYDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posNNNNN -= 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "Y -- ^^", Toast.LENGTH_SHORT).show();
			}
		});

		btnZUP = new Button(this);
		btnZUP.setText("Z ++");
		btnZUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posMMMMM += 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "Z ++ ^^", Toast.LENGTH_SHORT).show();
			}
		});

		btnZDOWN = new Button(this);
		btnZDOWN.setText("Z --");
		btnZDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float posBBBBB = LoaderObjects.objGlass.getPositionX();
				float posNNNNN = LoaderObjects.objGlass.getPositionY();
				float posMMMMM = LoaderObjects.objGlass.getPositionZ();
				posMMMMM -= 0.1f;
				LoaderObjects.objGlass.setPosition(new float[] {posBBBBB, posNNNNN, posMMMMM});
				//Toast.makeText(ModelActivity.this, "Z -- ^^", Toast.LENGTH_SHORT).show();
			}
		});


		btnRotateXUP = new Button(this);
		btnRotateXUP.setText("Rotate X ++");
		btnRotateXUP.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float scale1[] = new float[3];
				scale1 = LoaderObjects.objGlass.getRotation();
				scale1[0] += 1.5;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale1);
			}
		});


		btnRotateXDOWN = new Button(this);
		btnRotateXDOWN.setText("Rotate X --");
		btnRotateXDOWN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				float scale2[] = new float[3];
				scale2 = LoaderObjects.objGlass.getRotation();
				scale2[0] -= 1.5f;
				LoaderObjects.objGlass.getRotationZ();
				LoaderObjects.objGlass.setRotation(scale2);
			}
		});


		linearLayoutFavorite = new LinearLayout(this);
		linearLayoutFavorite.setGravity(Gravity.TOP | Gravity.RIGHT);
		linearLayoutFavorite.addView(btnFavorite);

		linearLayoutAddToCart = new LinearLayout(this);
		linearLayoutAddToCart.setGravity(Gravity.BOTTOM | Gravity.RIGHT);
		linearLayoutAddToCart.setOrientation(LinearLayout.HORIZONTAL);

		//linearLayoutAddToCart.addView(btnTakePicture);
		//linearLayoutAddToCart.addView(btnAddToCart);

        linearLayout = new LinearLayout(this);
        linearLayout.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        linearLayout.addView(btnXUP);
        linearLayout.addView(btnXDOWN);
		linearLayout.addView(btnYUP);
		linearLayout.addView(btnYDOWN);
		linearLayout.addView(btnZUP);
		linearLayout.addView(btnZDOWN);
		linearLayout.addView(btnRotateXUP);
		linearLayout.addView(btnRotateXDOWN);


		setContentView(gLView);
		addContentView(linearLayoutFavorite, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

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
