package com.tryon.xuanhong.tryon.object3D.services;

import android.opengl.GLES20;
import android.opengl.Matrix;
import android.os.SystemClock;
import android.widget.Toast;

import com.tryon.xuanhong.tryon.object3D.models.Object3DBuilder;
import com.tryon.xuanhong.tryon.object3D.models.Object3DBuilder.Callback;
import com.tryon.xuanhong.tryon.object3D.models.Object3DData;
import com.tryon.xuanhong.tryon.object3D.views.ModelActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class loads a 3D scena as an example of what can be done with the app
 * 
 * @author andresoviedo
 *
 */
public class LoaderScenes {

	/**
	 * Parent component
	 */
	protected final ModelActivity parent;
	/**
	 * List of data objects containing info for building the opengl objects
	 */
	private List<Object3DData> objects = new ArrayList<Object3DData>();
	/**
	 * Whether to draw objects as wireframes
	 */
	private boolean drawWireframe = false;
	/**
	 * Whether to draw bounding boxes around objects
	 */
	private boolean drawBoundingBox = false;
	/**
	 * Whether to draw face normals. Normally used to debug models
	 */
	private boolean drawNormals = false;
	/**
	 * Whether to draw using textures
	 */
	private boolean drawTextures = true;
	/**
	 * Whether to draw using lights
	 */
	private boolean drawLighting = false;
	/**
	 * Default draw mode when loading models from files
	 */
	private int defaultDrawMode = GLES20.GL_TRIANGLE_FAN;
	/**
	 * Light position
	 */
	private float[] lightPos = new float[] { 0, 0, 3, 1 };
	/**
	 * Object selected by the user
	 */
	private Object3DData selectedObject = null;

	public LoaderScenes(ModelActivity main) {
		this.parent = main;
	}

	public void init() {


		// Load object
		if (parent.getParamFile() != null || parent.getParamAssetDir() != null) {
			Object3DBuilder.loadV5Async(parent, parent.getParamFile(), parent.getParamAssetDir(),
					parent.getParamAssetFilename(), new Callback() {

						@Override
						public void onLoadComplete(Object3DData data) {
							data.centerAndScale(5.0f);
							addObject(data);

						}

						@Override
						public void onLoadError(Exception ex) {
							Toast.makeText(parent.getApplicationContext(),
									"There was a problem building the model: " + ex.getMessage(), Toast.LENGTH_LONG)
									.show();
						}
					});
		}
	}

	public void draw(Object3DData obj, float[] pMatrix, float[] vMatrix, int drawMode, int drawSize) {
		float[] mLightPosInEyeSpace = new float[4];
		// Do a complete rotation every 10 seconds.
		long time = SystemClock.uptimeMillis() % 10000L;
		float angleInDegrees = (360.0f / 10000.0f) * ((int) time);

		// calculate light matrix
		float[] mMatrixLight = new float[16];
		// Calculate position of the light. Rotate and then push into the distance.
		Matrix.setIdentityM(mMatrixLight, 0);
		// Matrix.translateM(mMatrixLight, 0, lightPos[0], lightPos[1], lightPos[2]);
		Matrix.rotateM(mMatrixLight, 0, angleInDegrees, 0.0f, 1.0f, 0.0f);
		// Matrix.translateM(mMatrixLight, 0, 0.0f, 0.0f, 2.0f);
		float[] mLightPosInWorldSpace = new float[4];
		Matrix.multiplyMV(mLightPosInWorldSpace, 0, mMatrixLight, 0, lightPos, 0);

		Matrix.multiplyMV(mLightPosInEyeSpace, 0, vMatrix, 0, mLightPosInWorldSpace, 0);
		float[] mvMatrixLight = new float[16];
		Matrix.multiplyMM(mvMatrixLight, 0, vMatrix, 0, mMatrixLight, 0);
		float[] mvpMatrixLight = new float[16];
		Matrix.multiplyMM(mvpMatrixLight, 0, pMatrix, 0, mvMatrixLight, 0);

	}

	protected synchronized void addObject(Object3DData obj) {

		List<Object3DData> newList = new ArrayList<Object3DData>(objects);
		newList.add(obj);
		this.objects = newList;
		requestRender();
	}

	private void requestRender() {
		parent.getgLView().requestRender();

	}

	public synchronized List<Object3DData> getObjects() {
		return objects;
	}

	public void toggleWireframe() {
		this.drawWireframe = !this.drawWireframe;
		requestRender();
	}

	public boolean isDrawWireframe() {
		return this.drawWireframe;
	}

	public void toggleBoundingBox() {
		this.drawBoundingBox = !drawBoundingBox;
		requestRender();
	}

	public boolean isDrawBoundingBox() {
		return drawBoundingBox;
	}

	public boolean isDrawNormals() {
		return drawNormals;
	}

	public float[] getLightPos() {
		return lightPos;
	}

	public void toggleTextures() {
		this.drawTextures = !drawTextures;
	}

	public void toggleLighting() {
		this.drawLighting = !drawLighting;
	}

	public boolean isDrawTextures() {
		return drawTextures;
	}

	public boolean isDrawLighting() {
		return drawLighting;
	}

	public Object3DData getSelectedObject() {
		return selectedObject;
	}

	public void setSelectedObject(Object3DData selectedObject) {
		this.selectedObject = selectedObject;
	}

}
