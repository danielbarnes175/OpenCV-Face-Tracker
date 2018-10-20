package application;

import java.io.ByteArrayInputStream;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import utilities.Utils;

public class FXController {
	@FXML
	private Button button;
	@FXML
	private ImageView currentFrame;
	
	private ScheduledExecutorService timer;
	private VideoCapture capture = new VideoCapture();
	private boolean isActive = false;
	private static int cameraID = 0;
	
	@FXML
	protected void startCamera(ActionEvent event) {
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
			
			this.timer = Executors.newSingleThreadScheduledExecutor();
			this.timer.scheduleAtFixedRate(frameGrabber, 0, 33, TimeUnit.MILLISECONDS);
			
			this.button.setText("Stop");
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
			}
		
		} catch(Exception e) {
			System.err.println("grabFrame gets an exception: " + e);
			}
		}
		return frame;
	}
	
	protected void setClosed() {
		this.stopAcquisition();
	}
}
