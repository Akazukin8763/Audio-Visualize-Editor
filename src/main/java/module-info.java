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

    opens com.visualize.main to javafx.fxml;
    exports com.visualize.main;
}