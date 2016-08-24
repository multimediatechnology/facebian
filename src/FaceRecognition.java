import org.opencv.core.*;

class FaceRecognition extends javax.swing.JFrame {

  public static void main(String[] args) {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

    DaemonThread daemon = new DaemonThread();
    Thread t = new Thread(daemon);
    //t.setDaemon(true);
    daemon.setRunnable(true);
    t.start();
  }

}
