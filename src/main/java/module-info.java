module com.visualize.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires javafx.swing;
    requires java.desktop;

    //--add-opens=javafx.controls/com.sun.javafx.scene.control.behavior=org.controlsfx.controls
    requires org.controlsfx.controls;

    requires mp3spi;

    requires org.bytedeco.ffmpeg.platform;
    requires org.bytedeco.ffmpeg;
    requires org.bytedeco.javacv;

    requires com.sun.jna;
    requires com.sun.jna.platform;

    opens com.visualize.main to javafx.fxml;
    exports com.visualize.main;
}