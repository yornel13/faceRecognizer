package org.opencv.javacv.facerecognition;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Created by Yornel on 20-jun-16.
 */
class TrainFaceDetector extends Detector<Face> {

    private Detector<Face> mDelegate;
    private Context context;
    private Boolean capture;
    TrainDetectorListener listener;

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    TrainFaceDetector(Detector<Face> delegate, Context context, TrainDetectorListener listener) {
        this.context = context;
        this.listener = listener;
        mDelegate = delegate;
        capture = false;
    }

    public SparseArray<Face> detect(Frame frame) {
        SparseArray<Face> facesGoogle = mDelegate.detect(frame);

        /*
        int position = 0;
        if (facesGoogle.size() > 0)
            position = facesGoogle.keyAt(facesGoogle.size() - 1);
        if (facesGoogle.size() > 0
                && facesGoogle.get(position).getIsLeftEyeOpenProbability() > 0.7
                && facesGoogle.get(position).getIsRightEyeOpenProbability() > 0.7
                ) {*/
            if (listener != null && capture) {
                capture = false;
                listener.onDetect(frame);
            }
       // }

        return facesGoogle;
    }

    public boolean isOperational() {
        return mDelegate.isOperational();
    }

    public boolean setFocus(int id) {
        return mDelegate.setFocus(id);
    }

    public static File saveBitmap(Bitmap b, Integer count) {

        b = Bitmap.createScaledBitmap(b, UsuarioRecognizer.WIDTH, UsuarioRecognizer.HEIGHT, false);
        //create directory if not exist
        File dir = new File("/sdcard/recognizer/");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File output = new File(dir, "usuario_capture_" + count + ".jpg");
        File captureFile = output;
        OutputStream os = null;

        try {
            os = new FileOutputStream(output);
            b.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();

        } catch (Exception e) {
            captureFile = null;
            e.printStackTrace();
        }
        return captureFile;
    }

    public static Bitmap RotateBitmap(Bitmap source, int rotation) {
        Matrix matrix = new Matrix();
        switch (rotation) {
            case 0:
                break;
            case 1:
                matrix.postRotate(-270);
                break;
            case 2:
                matrix.postRotate(-180);
                break;
            case 3:
                matrix.postRotate(-90);
                break;
            default:
                break;
        }
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(),
                source.getHeight(), matrix, true);
    }

    public interface TrainDetectorListener {

        void onDetect(Frame frame);

    }
}
