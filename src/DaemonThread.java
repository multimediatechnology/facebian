import org.opencv.core.*;
import org.opencv.highgui.Highgui;
import org.opencv.highgui.VideoCapture;
import org.opencv.objdetect.CascadeClassifier;

class DaemonThread implements Runnable {

  private volatile boolean runnable = false;

  private VideoCapture webSource = null;
  private Mat image = new Mat();
  private CascadeClassifier faceDetector = new CascadeClassifier("assets/haarcascade_frontalface_alt.xml");
  private MatOfRect faceDetections = new MatOfRect();
  private MatOfByte buffer = new MatOfByte();

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
          for (Rect rect : rects) {
            Core.rectangle(
              image,
              new Point(rect.x, rect.y),
              new Point(rect.x + rect.width, rect.y + rect.height),
              new Scalar(0, 255, 0)
            );
          }
          Highgui.imencode(".jpg", image, buffer);
          try {
            System.out.write(buffer.toArray());
            System.out.flush();
          } catch (Exception ex) {
            //System.err.println(ex);
          }
        } else {
          //System.err.println("Cannot grab image");
        }
      }
    }
  }
}
