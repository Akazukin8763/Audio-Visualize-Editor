package com.visualize.object;

import javafx.scene.control.Slider;

public class CustomSlider extends Slider {

    public CustomSlider (int min, int max, int value) {
        super(min, max, value);

        this.valueProperty().addListener((obs, oldValue, newValue) -> {
            try {
                this.lookup(".track").setStyle(sliderTrackStyle((newValue.doubleValue() - getMin()) / (getMax() - getMin()) * 100));
            } catch (NullPointerException ignored) {
                // 不做任何事
            }
        });
    }

    private String sliderTrackStyle(double value) {
        return String.format("-fx-background-color: linear-gradient(to right, lightseagreen %f%%, #444444 %f%%);", value, value);
    }

}
