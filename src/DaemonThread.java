import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

class DaemonThread implements Runnable {

  private volatile boolean runnable = false;

  private VideoCapture webSource = null;
  private Mat image = new Mat();
  private CascadeClassifier faceDetector = new CascadeClassifier("assets/haarcascade_frontalface_alt.xml");
  private MatOfRect faceDetections = new MatOfRect();

  DaemonThread() {
    webSource = new VideoCapture();
    webSource.open(0);
  }

  void setRunnable(boolean runnable) {
    this.runnable = runnable;
  }

  @Override
  public void run() {
    synchronized (this) {
      while (runnable) {
        if (webSource.grab()) {
          webSource.retrieve(image);
          faceDetector.detectMultiScale(image, faceDetections);
          Rect[] rects = faceDetections.toArray();
          System.out.println(String.format("%s faces detected.", rects.length));
          for (Rect rect : rects) {
            Imgproc.rectangle(
              image,
              new Point(rect.x, rect.y),
              new Point(rect.x + rect.width, rect.y + rect.height),
              new Scalar(0, 255, 0)
            );
          }
          //Imgcodecs.imwrite("test.png", image);
        } else {
          System.err.println("Cannot grab image");
        }
      }
    }
  }
}
