package com.visualize.file;

import com.visualize.view.*;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;

import java.io.IOException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.stream.Collectors;

import javafx.scene.paint.Color;

public class ProjectSL {

    private final Properties properties;

    // Constructor
    public ProjectSL() {
        properties = new Properties();
    }

    public void save(ProjectFormat format, String filepath) throws IOException {
        properties.setProperty("Project_Name", format.getProjectName());

        properties.setProperty("Width", String.format("%d", format.getWidth()));
        properties.setProperty("Height", String.format("%d", format.getHeight()));

        properties.setProperty("Advance", String.format("%b", format.isAdvanced()));

        properties.setProperty("Equalizer_Type", format.getView().toString());
        properties.setProperty("Equalizer_Side", format.getSide().toString());
        properties.setProperty("Equalizer_Direct", format.getDirect().toString());
        properties.setProperty("Equalizer_Stereo", format.getStereo().toString());

        // Music
        properties.setProperty("Music", format.getFilepath());

        // Equalizer
        properties.setProperty("Bar_Number", String.format("%d", format.getBarNum()));
        properties.setProperty("Size", String.format("%d", format.getSize()));
        properties.setProperty("Rotation", String.format("%f", format.getRotation()));
        properties.setProperty("Gap", String.format("%d", format.getGap()));
        properties.setProperty("Radius", String.format("%d", format.getRadius()));
        properties.setProperty("Position_X", String.format("%f", format.getPosX()));
        properties.setProperty("Position_Y", String.format("%f", format.getPosY()));
        properties.setProperty("Color", format.getColor().toString());
        properties.setProperty("Color_Shadow", format.getColorShadow().toString());
        properties.setProperty("Color_Shadow_Radius", String.format("%d", format.getColorShadowRadius()));
        properties.setProperty("Color_Shadow_Spread", String.format("%f", format.getColorShadowSpread()));
        properties.setProperty("Color_Shadow_Offset_X", String.format("%f", format.getColorShadowOffsetX()));
        properties.setProperty("Color_Shadow_Offset_Y", String.format("%f", format.getColorShadowOffsetY()));
        properties.setProperty("Sensitivity", String.format("%f", format.getSensitivity()));
        properties.setProperty("Min_Frequency", String.format("%f", format.getMinFreq()));
        properties.setProperty("Max_Frequency", String.format("%f", format.getMaxFreq()));

        // Background
        properties.setProperty("Background_Color", format.getBackgroundColor().toString());
        properties.setProperty("Background_Image", (format.getBackgroundImage() == null ? "None" : format.getBackgroundImage()));
        properties.setProperty("Background_Image_Position_X", String.format("%d", format.getBackgroundImagePosX()));
        properties.setProperty("Background_Image_Position_Y", String.format("%d", format.getBackgroundImagePosY()));

        // Image
        int nums = format.getImageFormat().size();
        properties.setProperty("Image_Numbers", String.format("%d", nums));
        for (int i = 1; i <= nums; i++) {
            ImageFormat imageFormat = format.getImageFormat().get(i - 1);

            if (imageFormat == null)
                properties.setProperty("Image_Equalizer", String.format("%d", i));
            else {
                properties.setProperty("Image_Path_" + i, imageFormat.getFilepath());
                properties.setProperty("Image_Position_X_" + i, String.format("%f", imageFormat.getPosX()));
                properties.setProperty("Image_Position_Y_" + i, String.format("%f", imageFormat.getPosY()));
                properties.setProperty("Image_Rotation_" + i, String.format("%f", imageFormat.getRotation()));
                properties.setProperty("Image_Scale_X_" + i, String.format("%f", imageFormat.getScaleX()));
                properties.setProperty("Image_Scale_Y_" + i, String.format("%f", imageFormat.getScaleY()));
            }
        }
        if (nums == 0)
            properties.setProperty("Image_Equalizer", String.format("%d", -1));

        try {
            properties.storeToXML(new FileOutputStream(filepath), "Saved by Ɐudio Ʌisualizer Ǝditor");
        } catch (IOException e) {
            throw new IOException();
        } finally {
            properties.clear();
        }
    }

    public ProjectFormat load(String filepath) throws IOException {
        try {
            properties.loadFromXML(new FileInputStream(filepath));

            String projectName = properties.getProperty("Project_Name");

            int width = Integer.parseInt(properties.getProperty("Width"));
            int height = Integer.parseInt(properties.getProperty("Height"));

            boolean advanced = Boolean.parseBoolean(properties.getProperty("Advance"));

            VisualizeMode.View view = Arrays.stream(VisualizeMode.View.values()).filter(prop -> properties.getProperty("Equalizer_Type").equals(prop.toString())).collect(Collectors.toList()).get(0);
            VisualizeMode.Side side = Arrays.stream(VisualizeMode.Side.values()).filter(prop -> properties.getProperty("Equalizer_Side").equals(prop.toString())).collect(Collectors.toList()).get(0);
            VisualizeMode.Direct direct = Arrays.stream(VisualizeMode.Direct.values()).filter(prop -> properties.getProperty("Equalizer_Direct").equals(prop.toString())).collect(Collectors.toList()).get(0);
            VisualizeMode.Stereo stereo = Arrays.stream(VisualizeMode.Stereo.values()).filter(prop -> properties.getProperty("Equalizer_Stereo").equals(prop.toString())).collect(Collectors.toList()).get(0);

            // Music
            String musicpath = properties.getProperty("Music");

            // Equalizer
            int barNum = Integer.parseInt(properties.getProperty("Bar_Number"));
            int size = Integer.parseInt(properties.getProperty("Size"));
            double rotation = Double.parseDouble(properties.getProperty("Rotation"));
            int gap = Integer.parseInt(properties.getProperty("Gap"));
            int radius = Integer.parseInt(properties.getProperty("Radius"));
            double posX = Double.parseDouble(properties.getProperty("Position_X"));
            double posY = Double.parseDouble(properties.getProperty("Position_Y"));
            Color color = Color.web(properties.getProperty("Color"));
            Color colorShadow = Color.web(properties.getProperty("Color_Shadow"));
            int colorShadowRadius = Integer.parseInt(properties.getProperty("Color_Shadow_Radius"));
            double colorShadowSpread = Double.parseDouble(properties.getProperty("Color_Shadow_Spread"));
            double colorShadowOffsetX = Double.parseDouble(properties.getProperty("Color_Shadow_Offset_X"));
            double colorShadowOffsetY = Double.parseDouble(properties.getProperty("Color_Shadow_Offset_Y"));
            double sensitivity = Double.parseDouble(properties.getProperty("Sensitivity"));
            double minFreq = Double.parseDouble(properties.getProperty("Min_Frequency"));
            double maxFreq = Double.parseDouble(properties.getProperty("Max_Frequency"));

            // Background
            Color backgroundColor = Color.web(properties.getProperty("Background_Color"));
            String backgroundImage = (properties.getProperty("Background_Image").equals("None") ? null : properties.getProperty("Background_Image"));
            int backgroundImagePosX = Integer.parseInt(properties.getProperty("Background_Image_Position_X"));
            int backgroundImagePosY = Integer.parseInt(properties.getProperty("Background_Image_Position_Y"));

            // Image
            List<ImageFormat> imageFormat = new ArrayList<>();
            int nums = Integer.parseInt(properties.getProperty("Image_Numbers"));
            int equalizer = Integer.parseInt(properties.getProperty("Image_Equalizer"));
            for (int i = 1; i <= nums; i++) {
                if (i == equalizer)
                    imageFormat.add(null);
                else {
                    String imagepath = properties.getProperty("Image_Path_" + i);
                    double imagePosX = Double.parseDouble(properties.getProperty("Image_Position_X_" + i));
                    double imagePosY = Double.parseDouble(properties.getProperty("Image_Position_Y_" + i));
                    double imageRotation = Double.parseDouble(properties.getProperty("Image_Rotation_" + i));
                    double imageScaleX = Double.parseDouble(properties.getProperty("Image_Scale_X_" + i));
                    double imageScaleY = Double.parseDouble(properties.getProperty("Image_Scale_Y_" + i));

                    imageFormat.add(new ImageFormat(imagepath, imagePosX, imagePosY, imageRotation, imageScaleX, imageScaleY));
                }
            }

            return new ProjectFormat(
                    projectName, width, height, advanced,
                    view, side, direct, stereo, musicpath,
                    barNum, size, gap, radius, posX, posY, rotation,
                    color, colorShadow, colorShadowRadius, colorShadowSpread, colorShadowOffsetX, colorShadowOffsetY,
                    sensitivity, minFreq, maxFreq,
                    backgroundColor, backgroundImage, backgroundImagePosX, backgroundImagePosY,
                    imageFormat);
        } catch (IOException | IndexOutOfBoundsException | NumberFormatException e) {
            throw new IOException();
        } finally {
            properties.clear();
        }
    }

}
