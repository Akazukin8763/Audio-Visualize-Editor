package com.visualize.view;

import javafx.scene.layout.Pane;
import javafx.animation.Timeline;

public class VisualizeParameter {

    public static class PaneTimeline {

        private final Pane pane;
        private final Timeline timeline;

        public PaneTimeline(Pane pane, Timeline timeline) {
            this.pane = pane;
            this.timeline = timeline;
        }

        public Pane getPane() {
            return pane;
        }

        public Timeline getTimeline() {
            return timeline;
        }
    }

}
