package com.tryon.xuanhong.tryon.object3D.services;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.tryon.xuanhong.tryon.object3D.models.Object3DBuilder;
import com.tryon.xuanhong.tryon.object3D.models.Object3DData;
import com.tryon.xuanhong.tryon.object3D.views.ModelActivity;

import java.util.ArrayList;
import java.util.List;

import static com.tryon.xuanhong.tryon.HomeActivity.IDGLASS;
import static com.tryon.xuanhong.tryon.MainActivity.mainUser;

public class LoaderObjects extends LoaderScenes {

	public LoaderObjects(ModelActivity modelActivity) {
		super(modelActivity);
	}


	public static Object3DData objFace;
	public static Object3DData objGlass;

	public void init() {
		super.init();
		new AsyncTask<Void, Void, Void>() {

			ProgressDialog dialog = new ProgressDialog(parent);
			List<Exception> errors = new ArrayList<Exception>();

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				dialog.setCancelable(false);
				dialog.setMessage("Please wait a few minutes, program is loading objects...");
				dialog.show();
			}

			@Override
			protected Void doInBackground(Void... params) {

				try {
					// 3D Axis
					Object3DData axis = Object3DBuilder.buildAxis().setId("axis");
					axis.setColor(new float[] { 0.0f, 0.0f, 0.0f, 1.0f });
					axis.centerAndScale(4);
					addObject(axis);

					try {
						objFace = Object3DBuilder.loadFromSD("Heads/", mainUser.getEmail() + ".obj");
						objFace.setPosition(new float[] { 0f, 0f, 0f });
						objFace.centerAndScale(2);
						addObject(objFace);
					} catch (Exception ex) {
						errors.add(ex);
					}


					try {
						objGlass = Object3DBuilder.loadFromSD("Glasses/", IDGLASS + ".obj");
						objGlass.setPosition(new float[] { 0f, 0.3f, -0.2f });
						addObject(objGlass);
					} catch (Exception ex) {
						errors.add(ex);
					}

				} catch (Exception ex) {
					errors.add(ex);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				super.onPostExecute(result);
				if (dialog.isShowing()) {
					dialog.dismiss();
				}
				if (!errors.isEmpty()) {
					StringBuilder msg = new StringBuilder("There was a problem loading the data");
					for (Exception error : errors) {
						Log.e("Example", error.getMessage(), error);
						msg.append("\n" + error.getMessage());
					}
					Toast.makeText(parent.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				}
			}
		}.execute();
	}
}
