package org.opencv.javacv.facerecognition;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.SparseArray;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;

/**
 * Created by Yornel on 20-jun-16.
 */
class RecognizerFaceDetector extends Detector<Face> {
    private Detector<Face> mDelegate;
    private Boolean capture;
    private RecognizerDetectorListener listener;

    public void setCapture(Boolean capture) {
        this.capture = capture;
    }

    RecognizerFaceDetector(Detector<Face> delegate, RecognizerDetectorListener listener) {
        this.listener = listener;
        mDelegate = delegate;
        capture = false;
    }

    public SparseArray<Face> detect(Frame frame) {
        SparseArray<Face> facesGoogle = mDelegate.detect(frame);

        int position = 0;
        if (facesGoogle.size() > 0)
            position = facesGoogle.keyAt(facesGoogle.size() - 1);
        if (facesGoogle.size() > 0
                && facesGoogle.get(position).getIsLeftEyeOpenProbability() > 0.6
                && facesGoogle.get(position).getIsRightEyeOpenProbability() > 0.6
                ) {
            if (listener != null && capture) {
                capture = false;
                listener.onDetect(frame);
            }
        }
        return facesGoogle;
    }

    public boolean isOperational() {
        return mDelegate.isOperational();
    }

    public boolean setFocus(int id) {
        return mDelegate.setFocus(id);
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

    public interface RecognizerDetectorListener {

        void onDetect(Frame frame);

    }


}
