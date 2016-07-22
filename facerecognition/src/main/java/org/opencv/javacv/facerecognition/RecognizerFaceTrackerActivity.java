/*
 * Copyright (C) The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.opencv.javacv.facerecognition;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.javacv.facerecognition.asyncTask.CargarUsuarioParaReconocimiento;
import org.opencv.javacv.facerecognition.camera.CameraSourcePreview;
import org.opencv.javacv.facerecognition.camera.GraphicOverlay;
import org.opencv.javacv.facerecognition.model.Usuario;
import org.opencv.objdetect.CascadeClassifier;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Activity for the face tracker app.  This app detects faces with the rear facing camera, and draws
 * overlay graphics to indicate the position, size, and ID of each face.
 */
public final class RecognizerFaceTrackerActivity extends AppCompatActivity implements RecognizerFaceDetector.RecognizerDetectorListener, CargarUsuarioParaReconocimiento.CargarUsuarioCompletado {

    private Usuario usuario;

    private static final String TAG = "FaceTracker";

    private CameraSource mCameraSource = null;

    private CameraSourcePreview mPreview;
    public GraphicOverlay mGraphicOverlay;
    private TextView textView;

    private static final int RC_HANDLE_GMS = 9001;
    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    RecognizerFaceDetector recognizerFaceDetector;
    Handler mHandler;
    private ProgressDialog progressDialog;

    public UsuarioRecognizerNew personRecognizer;

    private Mat mRgba;
    private Mat mGray;
    private int mAbsoluteFaceSize = 0;
    private float mRelativeFaceSize = 0.2f;
    private CascadeClassifier mJavaDetector;
    CascadeClassifier mLeftEyeCascade, mRightEyesCascade;
    private File mCascadeFile;
    private String message;
    Integer usuarioId;
    FaceDetector detector;

    private int mPreviousEyesState = -1;
    private boolean mIsEyeClosingDetected = false;
    private boolean mIsDetectionOn = false;
    Bitmap mBitmap;
    ArrayList<Pair> arr_predict_probability = new ArrayList<>();
    Pair user_probability;
    HashMap<String,Integer> mapPredicciones = new HashMap<>();
    CountDownTimer countdown;
    boolean isRunning = false;
    boolean inflated = false;
    int matchCounter = 0;
    //==============================================================================================
    // Activity Methods
    //==============================================================================================

    /**
     * Initializes the UI and initiates the creation of a face detector.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_recognizer);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.faceOverlay);
        textView = (TextView) findViewById(R.id.text);

        usuario = new Usuario(getIntent().getExtras());

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource();
        } else {
            requestCameraPermission();
        }

        progressDialog = ProgressDialog.show(this, "Espere",
                "Cargando informacion necesaria para el reconocimiento...", true);
    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        Toast.makeText(RecognizerFaceTrackerActivity.this, "Permiso de camara", Toast.LENGTH_SHORT).show();
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     */
    private void createCameraSource() {

        Context context = getApplicationContext();
        detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        recognizerFaceDetector = new RecognizerFaceDetector(detector, this);
        recognizerFaceDetector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());
        if (!detector.isOperational()) {
            // Note: The first time that an app using face API is installed on a device, GMS will
            // download a native library to the device in order to do detection.  Usually this
            // completes before the app is run for the first time.  But if that download has not yet
            // completed, then the above call will not detect any faces.
            //
            // isOperational() can be used to check if the required native library is currently
            // available.  The detector will automatically become operational once the library
            // download completes on device.
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        mCameraSource = new CameraSource.Builder(context, recognizerFaceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(CameraSource.CAMERA_FACING_FRONT)
                .setRequestedFps(30.0f)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();

        if (OpenCVLoader.initDebug()) {
            mOpenCVCallBack.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        mPreview.stop();
    }

    /**
     * Releases the resources associated with the camera source, the associated detector, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCameraSource != null) {
            mCameraSource.release();
        }
        if (mGray != null) {
            mGray.release();
        }
        if (mRgba != null) {
            mRgba.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            createCameraSource();
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Face Tracker sample")
                .setMessage("Sin permiso para camara")
                .setPositiveButton("ok", listener)
                .show();
    }

    //==============================================================================================
    // Camera Source Preview
    //==============================================================================================

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() {

        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    @Override
    public void onDetect(Frame frame) {
        int w = frame.getMetadata().getWidth();
        int h = frame.getMetadata().getHeight();
        mRgba = new Mat(w, h, CvType.CV_8UC4);
        mGray = new Mat(w, h, CvType.CV_8UC1);
        ByteBuffer byteBuffer = frame.getGrayscaleImageData();
        byte[] bytes = byteBuffer.array();
        YuvImage yuvimage = new YuvImage(bytes, ImageFormat.NV21, w, h, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        yuvimage.compressToJpeg(new Rect(0, 0, w, h), 100, baos); // Where 100 is the quality of the generated jpeg
        byte[] jpegArray = baos.toByteArray();
        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.length);
        bitmap = recognizerFaceDetector.RotateBitmap(bitmap, frame.getMetadata().getRotation());

        Utils.bitmapToMat(bitmap, mRgba);
        Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_BGR2GRAY);

        Size mMinSize = new Size(mAbsoluteFaceSize, mAbsoluteFaceSize);
        Size mMaxSize = new Size();

        if (mAbsoluteFaceSize == 0) {
            int height = mGray.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
        }

        MatOfRect faces = new MatOfRect();

        if (mJavaDetector != null)
            mJavaDetector.detectMultiScale(mGray, faces, 1.1, 2, 2, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());

        org.opencv.core.Rect[] facesArray = faces.toArray();
        org.opencv.core.Rect[] eyesArray = new org.opencv.core.Rect[0];
        org.opencv.core.Rect[] leftEyesArray = new org.opencv.core.Rect[0];
        org.opencv.core.Rect[] rigtheyesArray = new org.opencv.core.Rect[0];

        if (facesArray.length == 1) {

            MatOfRect mRightEyes = new MatOfRect();
            MatOfRect mLeftEye = new MatOfRect();

            //-- In each face, detect eyes
            mRightEyesCascade.detectMultiScale(mGray, mRightEyes, 1.1, 2, 2, mMinSize, mMaxSize);
            mLeftEyeCascade.detectMultiScale(mGray, mLeftEye, 1.1, 2, 2, mMinSize, mMaxSize);

            rigtheyesArray = mRightEyes.toArray();
            leftEyesArray = mLeftEye.toArray();

            // The condition handle only simple event "two eyes are  -> one (or two) is closed"
            if (eyesArray.length < 2 && mPreviousEyesState == 2) {
                mIsEyeClosingDetected = true;
            } else if (eyesArray.length == 2 && mIsEyeClosingDetected && mIsDetectionOn) {
                mIsEyeClosingDetected = false;
            }
            mPreviousEyesState = eyesArray.length;

            Mat m = new Mat();
            m = mGray.submat(facesArray[0]);

            Imgproc.equalizeHist(m,m);
            mBitmap = Bitmap.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);
            Utils.matToBitmap(m, mBitmap);
            mBitmap = getCroppedBitmap(mBitmap);

            Utils.bitmapToMat(mBitmap, m);

            if (personRecognizer.canPredict()) {
                Integer id = personRecognizer.predict(m);
                Integer probability = personRecognizer.getProb();

                System.out.println("////////////////// id de usuario: " + id);
                System.out.println("/////////////////// probabilidad: " + probability);

                if (id != 0 && probability > 60){
                    usuarioId = id;
                    message = "Usuario: " + personRecognizer.usuario.getNombre();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    final byte[] byteArray = stream.toByteArray();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(message);
                            tryRecognizer(byteArray);
                        }
                    });
                } else if (id != 0){
                    message = "poca coincidencia";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(message);
                        }
                    });
                } else {
                    message = "Comparando...";
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText(message);
                        }
                    });
                }

                user_probability = new Pair(id.toString(), probability);
                arr_predict_probability.add(user_probability);
            } else {
                message = "Se necesitan mas rostros en la Base de Datos!";
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        textView.setText(message);
                    }
                });
            }
            if (usuarioId != null && usuarioId != 0){
                recognizerFaceDetector.setCapture(false);
            } else {
                recognizerFaceDetector.setCapture(true);
            }

        } else if (facesArray.length == 1) {
            message = "Mas de un rostros detectado.";
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    textView.setText(message);
                }
            });
            recognizerFaceDetector.setCapture(true);
        } else {
            recognizerFaceDetector.setCapture(true);
        }
    }

    public static Bitmap getCroppedBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),
                bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final android.graphics.Rect rect = new android.graphics.Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        // canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2,
                bitmap.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        //Bitmap _bmp = Bitmap.createScaledBitmap(output, 60, 60, false);
        //return _bmp;
        return output;
    }

    @Override
    public void completado() {
        progressDialog.dismiss();
        recognizerFaceDetector.setCapture(true);
    }

    @Override
    public void fallido() {
        progressDialog.dismiss();
        Toast.makeText(this, "Error de detector", Toast.LENGTH_SHORT).show();
        finish();
    }

    //==============================================================================================
    // Graphic Face Tracker
    //==============================================================================================

    /**
     * Factory for creating a face tracker to be associated with a new face.  The multiprocessor
     * uses this factory to create face trackers as needed -- one for each individual.
     */
    private class GraphicFaceTrackerFactory implements MultiProcessor.Factory<Face> {
        @Override
        public Tracker<Face> create(Face face) {
            return new GraphicFaceTracker(mGraphicOverlay);
        }
    }

    /**
     * Face tracker for each detected individual. This maintains a face graphic within the app's
     * associated face overlay.
     */
    private class GraphicFaceTracker extends Tracker<Face> {
        private GraphicOverlay mOverlay;
        private FaceGraphic mFaceGraphic;

        GraphicFaceTracker(GraphicOverlay overlay) {
            mOverlay = overlay;
            mFaceGraphic = new FaceGraphic(overlay);
        }

        /**
         * Start tracking the detected face instance within the face overlay.
         */
        @Override
        public void onNewItem(int faceId, Face item) {
            mFaceGraphic.setId(faceId);
        }

        /**
         * Update the position/characteristics of the face within the overlay.
         */
        @Override
        public void onUpdate(FaceDetector.Detections<Face> detectionResults, Face face) {
            mOverlay.add(mFaceGraphic);
            mFaceGraphic.updateFace(face);
        }

        /**
         * Hide the graphic when the corresponding face was not detected.  This can happen for
         * intermediate frames temporarily (e.g., if the face was momentarily blocked from
         * view).
         */
        @Override
        public void onMissing(FaceDetector.Detections<Face> detectionResults) {
            mOverlay.remove(mFaceGraphic);
        }

        /**
         * Called when the face is assumed to be gone for good. Remove the graphic annotation from
         * the overlay.
         */
        @Override
        public void onDone() {
            mOverlay.remove(mFaceGraphic);
        }
    }

    public void onTryCapture(View view) {
        Toast.makeText(this, "Intentando obtener rostro", Toast.LENGTH_SHORT).show();
        recognizerFaceDetector.setCapture(true);
    }

    public void tryRecognizer(byte[] faceDetect) {

        Usuario usuario = personRecognizer.usuario;

        finish();
        Intent intent = new Intent(this, UsuarioDetectadoActivity.class);
        usuario.saveToIntent(intent);
        intent.putExtra("faceDetect", faceDetect);
        startActivity(intent);

    }

    private BaseLoaderCallback mOpenCVCallBack = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                    try {

                        //////////////////////////// Rostro ////////////////////////////////////////
                        InputStream is = getResources().openRawResource(R.raw.haarcascade_frontalface_alt);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        mCascadeFile = new File(cascadeDir, "haarcascade_frontalface_alt.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());

                        cascadeDir.delete();

                        //////////////////////////// Ojo Derecho ///////////////////////////////////
                        InputStream is2 = getResources().openRawResource(R.raw.haarcascade_righteye_2splits);
                        File cascadeDir2 = getDir("cascade", Context.MODE_PRIVATE);
                        File mCascadeFile = new File(cascadeDir2, "haarcascade_righteye_2splits.xml");
                        FileOutputStream os2 = new FileOutputStream(mCascadeFile);
                        byte[] buffer2 = new byte[4096];
                        int bytesRead2;
                        while ((bytesRead2 = is2.read(buffer2)) != -1) {
                            os2.write(buffer2, 0, bytesRead2);
                        }
                        is2.close();
                        os2.close();
                        // Load the cascade classifier
                        mRightEyesCascade = new CascadeClassifier(mCascadeFile.getAbsolutePath());

                        //////////////////////////// Ojo Izquierdo /////////////////////////////////
                        InputStream is3 = getResources().openRawResource(R.raw.haarcascade_lefteye_2splits);
                        File cascadeDir3 = getDir("cascade", Context.MODE_PRIVATE);
                        File mCascadeFileLE = new File(cascadeDir3, "haarcascade_lefteye_2splits.xml");
                        FileOutputStream os3 = new FileOutputStream(mCascadeFileLE);
                        byte[] buffer3 = new byte[4096];
                        int bytesRead3;
                        while ((bytesRead3 = is3.read(buffer3)) != -1) {
                            os3.write(buffer3, 0, bytesRead3);
                        }
                        is3.close();
                        os3.close();
                        // Load the cascade classifier
                        mLeftEyeCascade = new CascadeClassifier(mCascadeFileLE.getAbsolutePath());

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }
                    startCameraSource();
                    CargarUsuarioParaReconocimiento cargarUsuarioParaReconocimiento =
                            new CargarUsuarioParaReconocimiento(RecognizerFaceTrackerActivity.this,
                            RecognizerFaceTrackerActivity.this, usuario);
                    cargarUsuarioParaReconocimiento.execute();
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    public void onChangeCamera(View view) throws IOException {

        mPreview.stop();
        mPreview.release();
        detector.release();
        recognizerFaceDetector.release();

        Context context = getApplicationContext();
        detector = new FaceDetector.Builder(context)
                .setClassificationType(FaceDetector.ALL_CLASSIFICATIONS)
                .build();

        recognizerFaceDetector = new RecognizerFaceDetector(detector, this);
        recognizerFaceDetector.setProcessor(
                new MultiProcessor.Builder<>(new GraphicFaceTrackerFactory())
                        .build());

        if (!detector.isOperational()) {
            Log.w(TAG, "Face detector dependencies are not yet available.");
        }

        int facing;
        if (mCameraSource.getCameraFacing() == CameraSource.CAMERA_FACING_BACK)
            facing = CameraSource.CAMERA_FACING_FRONT;
        else
            facing = CameraSource.CAMERA_FACING_BACK;
        mCameraSource = new CameraSource.Builder(context, recognizerFaceDetector)
                .setRequestedPreviewSize(640, 480)
                .setFacing(facing)
                .setRequestedFps(30.0f)
                .build();

        startCameraSource();

        recognizerFaceDetector.setCapture(true);
    }
}
