package org.opencv.javacv.facerecognition;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.googlecode.javacpp.BytePointer;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;
import com.googlecode.javacv.cpp.opencv_imgproc;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.model.Usuario;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_core.cvCreateImageHeader;
import static com.googlecode.javacv.cpp.opencv_core.cvSetData;
import static com.googlecode.javacv.cpp.opencv_core.cvSize;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvEqualizeHist;

public  class UsuarioRecognizer {

	public final static int MAXIMG = 100;
	FaceRecognizer faceRecognizer;
	String mPath;
	int count = 0;

	 static final int WIDTH = 128;
	 static final int HEIGHT = 128;
	 private int mProb = 999;

	ArrayList<Usuario> usuarios;


    public UsuarioRecognizer(ArrayList<Usuario> usuarios) {
		this.usuarios = usuarios;
		faceRecognizer =  com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer(2,8,8,8,90);
    }
	
	public boolean train() {

        MatVector images = new MatVector(usuarios.size()*5);

        int[] labels = new int[usuarios.size()*5];

        int counter = 0;
        int label;

		IplImage img;
        IplImage grayImg;
   
        for (Usuario usuario : usuarios) {

			Bitmap bitmap;
			label = usuario.getId();

			{
				bitmap = BitmapFactory.decodeByteArray(usuario.getFace1(), 0,
						usuario.getFace1().length);

				grayImg = BitmapToIplImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

				cvEqualizeHist(grayImg, grayImg);

				images.put(counter, grayImg);

				labels[counter] = label;

				counter++;
            }
			{
				bitmap = BitmapFactory.decodeByteArray(usuario.getFace2(), 0,
						usuario.getFace2().length);

				grayImg = BitmapToIplImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

				cvEqualizeHist(grayImg, grayImg);

				images.put(counter, grayImg);

				labels[counter] = label;

				counter++;
			}
			{
				bitmap = BitmapFactory.decodeByteArray(usuario.getFace3(), 0,
						usuario.getFace3().length);

				grayImg = BitmapToIplImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

				cvEqualizeHist(grayImg, grayImg);

				images.put(counter, grayImg);

				labels[counter] = label;

				counter++;
			}
			{
				bitmap = BitmapFactory.decodeByteArray(usuario.getFace4(), 0,
						usuario.getFace4().length);

				grayImg = BitmapToIplImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

				cvEqualizeHist(grayImg, grayImg);

				images.put(counter, grayImg);

				labels[counter] = label;

				counter++;
			}
			{
				bitmap = BitmapFactory.decodeByteArray(usuario.getFace5(), 0,
						usuario.getFace5().length);

				grayImg = BitmapToIplImage(bitmap, bitmap.getWidth(), bitmap.getHeight());

				cvEqualizeHist(grayImg, grayImg);

				images.put(counter, grayImg);

				labels[counter] = label;

				counter++;
			}
        }
        if (counter > 0)
        	if (usuarios.size() > 1)
        		faceRecognizer.train(images, labels);

		System.out.println("train is ready");
		return true;
	}
	
	public boolean canPredict() {
		if (usuarios.size() > 1)
			return true;
		else
			return false;
	}
	
	public int predict(Mat m) {
		if (!canPredict())
			return 0;
		int n[] = new int[1];
		double p[] = new double[1];
		IplImage ipl = MatToIplImage(m,WIDTH, HEIGHT);

		faceRecognizer.predict(ipl, n, p);
		
		if (n[0] != -1)
			mProb=(int)p[0];
		else
			mProb=-1;
		if (n[0] != -1)
			return Usuario.getUsuario(usuarios, n[0]).getId();
		else
			return 0;


	}


	

	IplImage MatToIplImage(Mat m,int width,int heigth) {
		Bitmap bmp = Bitmap
				.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);
	    Utils.matToBitmap(m, bmp);
	    return BitmapToIplImage(bmp, width, heigth);
	}

	IplImage BitmapToIplImage(Bitmap bmp, int width, int height) {

		if ((width != -1) || (height != -1)) {
			Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, false);
			bmp = bmp2;
		}

		IplImage image = IplImage.create(bmp.getWidth(), bmp.getHeight(),
				IPL_DEPTH_8U, 4);

		bmp.copyPixelsToBuffer(image.getByteBuffer());
		
		IplImage grayImg = IplImage.create(image.width(), image.height(),
				IPL_DEPTH_8U, 1);

		cvCvtColor(image, grayImg, opencv_imgproc.CV_BGR2GRAY);

		return grayImg;
	}
	  
	protected void SaveBmp(Bitmap bmp,String path) {
		FileOutputStream file;
		try {
			file = new FileOutputStream(path , true);

		bmp.compress(Bitmap.CompressFormat.JPEG,100,file);
		file.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			Log.e("",e.getMessage()+e.getCause());
			e.printStackTrace();
		}
	}
	

	public void load() {
		train();
	}

	File add(Mat m) {
		Bitmap bmp = Bitmap.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);

		Utils.matToBitmap(m, bmp);
		bmp = Bitmap.createScaledBitmap(bmp, WIDTH, HEIGHT, false);

		File dir = new File("/sdcard/recognizer/");
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File output = new File(dir, "usuario_capture_" + count + ".jpg");
		File captureFile = output;
		FileOutputStream f;
		try {
			f = new FileOutputStream(output, true);
			count++;
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, f);
			f.close();

		} catch (Exception e) {
			Log.e("error", e.getCause() + " " + e.getMessage());
			e.printStackTrace();
		}
		return captureFile;
	}


		public int getProb() {
		// TODO Auto-generated method stub
		return mProb;
	}

	protected IplImage processImage(byte[] data, int width, int height) {
		int f = 1;// SUBSAMPLING_FACTOR;


		// First, downsample our image and convert it into a grayscale IplImage
		IplImage image = IplImage.create(width / f, height / f, IPL_DEPTH_8U, 4);

		int imageWidth = image.width();
		int imageHeight = image.height();
		int dataStride = f * width;
		int imageStride = image.widthStep();
		ByteBuffer imageBuffer = image.getByteBuffer();
		for (int y = 0; y < imageHeight; y++) {
			int dataLine = y * dataStride;
			int imageLine = y * imageStride;
			for (int x = 0; x < imageWidth; x++) {
				imageBuffer.put(imageLine + x, data[dataLine + f * x]);
			}
		}

		return image;
	}

	public IplImage bytesToIplImage(byte[] data) {
		IplImage img = cvCreateImageHeader(cvSize(WIDTH, HEIGHT), IPL_DEPTH_8U, 4);
		BytePointer rawImageData = new BytePointer(data);
		cvSetData(img, rawImageData, WIDTH*4);
		return img;
	}
}
