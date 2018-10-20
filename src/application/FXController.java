package application;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.opencv.videoio.VideoCapture;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import utilities.Utils;

public class FXController {
/*Instance Variables*/
	//Variables for FXML functions
	@FXML
	private Button button;
	@FXML
	private ImageView currentFrame;
	
	//Variables for video
	private ScheduledExecutorService timer;
	private VideoCapture capture = new VideoCapture();
	private boolean isActive = false;
	private static int cameraID = 0;
	
	//Variables for outputstream
	public SerialOutput main = new SerialOutput();
	
	//Variables for facial recognition
	private CascadeClassifier cascade = new CascadeClassifier();
	private int faceSize = 0;
/*End Instance Variables*/
	/**
	 * Initializes the frame with width and preserves the ratio
	 */
	protected void init() {
		currentFrame.setFitWidth(600);
		currentFrame.setPreserveRatio(true);
		


	}
	/**
	 * Starts the camera
	 * @param event
	 */
	@FXML
	protected void startCamera(ActionEvent event) {
<<<<<<< HEAD
		//Loads the Haar Cascade to detect faces
=======
		main.initialize();
>>>>>>> 2a02d826f8f0cb9d0931744a2c951e468fad2f31
		this.cascade.load("resources/haarcascades/haarcascade_frontalface_alt.xml");
		if (!this.isActive) {
		this.capture.open(cameraID);
		if (this.capture.isOpened()) {
			this.isActive = true;
			Runnable frameGrabber = new Runnable() {
	
				@Override
				public void run() {
					Mat frame = grabFrame();
					Image imageToShow = Utils.mat2Image(frame);
					updateImageView(currentFrame, imageToShow);
				}};
			//Displays the video at 30FPS
			this.timer = Executors.newSingleThreadScheduledExecutor();
			this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
			//Changes test of Button to correspond with the status of the camera
			this.button.setText("Stop Camera");
			}
			else {
				System.err.println("Can't open camera connection.");
			}
		}
		else {
			isActive = false;
			this.button.setText("Start Camera");
			this.stopAcquisition();
		}
		
}
	
	/**
	 * Detects the face in the Frame and 
	 * @param frame
	 */
	private void detectAndDisplayFace(Mat frame) {
		
		MatOfRect faces = new MatOfRect();
		Mat grayFrame = new Mat();
		
		Imgproc.cvtColor(frame, grayFrame, Imgproc.COLOR_BGR2GRAY);
		
		if (this.faceSize == 0) {
			int height = grayFrame.rows();
			if (Math.round(height * 0.2f) > 0) {
				this.faceSize = Math.round(height * 0.2f);
			}
		}
		this.cascade.detectMultiScale(grayFrame, faces,1.1,2,0 | Objdetect.CASCADE_SCALE_IMAGE,
				new Size(this.faceSize,this.faceSize), new Size());
	
		Rect[] facesArray = faces.toArray();
		
		for (int i = 0; i < facesArray.length; i++) {
			Imgproc.rectangle(frame,facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 255, 0), 3);
			System.out.println(facesArray[i]);
			byte[] theBytes = rectToBytes(facesArray[i]);
			setBytes(theBytes);
			System.out.println(main.getBytes());
			main.run();
		}
	}
	
	protected void updateImageView(ImageView view, Image image) {
		// TODO Auto-generated method stub
		Utils.onFXThread(view.imageProperty(), image);
	}

	private void stopAcquisition() {
		// TODO Auto-generated method stub
		try {
			this.timer.shutdown();
			this.timer.awaitTermination(33, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				System.err.println("Exception while stopping frame capture" + e);
			}
			if (this.capture.isOpened()) {
				this.capture.release();
			}
	}

	protected Mat grabFrame() {
		Mat frame = new Mat();
		if (capture.isOpened()) {
		try {
		this.capture.read(frame);
		if (!frame.empty()) {
			Imgproc.cvtColor(frame, frame, Imgproc.COLOR_RGBA2RGB);
			this.detectAndDisplayFace(frame);
			}
		
		} catch(Exception e) {
			System.err.println("grabFrame gets an exception: " + e);
			}
		}
		return frame;
	}
	
	private byte[] rectToBytes(Rect rect) {
<<<<<<< HEAD
		String string = "u";
=======
		String string = "d";
>>>>>>> 2a02d826f8f0cb9d0931744a2c951e468fad2f31
		//Do calculations for figuring out how many directions we want to input.
		//Ask Ryan if the arduino can take say a R and a L on the same line

		char[] chars = string.toCharArray();
		return new String(chars).getBytes();
	}
	
	private void setBytes(byte[] givBytes) {
		main.setBytes(givBytes);
	}

	protected void setClosed() {
		this.stopAcquisition();
		main.close();
	}
}
