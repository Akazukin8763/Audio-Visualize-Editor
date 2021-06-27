module com.visualize.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires java.desktop;

    requires mp3spi;

    requires org.bytedeco.ffmpeg.platform;
    requires org.bytedeco.ffmpeg;
    requires org.bytedeco.javacv;

    requires com.sun.jna;
    requires com.sun.jna.platform;

    opens com.visualize.main to javafx.fxml;
    exports com.visualize.main;
    exports com.visualize.file;
    opens com.visualize.file to javafx.fxml;
}