package com.visualize.view;

public final class VisualizeMode {

    public enum View {
        LINE(0), CIRCLE(1), ANALOGY(3);

        private final int value;

        View (int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum Side {
        OUT(0), IN(1), BOTH(2);

        private final int value;

        Side (int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum Direct {
        NORMAL(0), INVERSE(1);

        private final int value;

        Direct (int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

    public enum Stereo {
        SINGLE(0), LEFT(1), RIGHT(2), BOTH(3);

        private final int value;

        Stereo (int value) {
            this.value = value;
        }

        public int value() {
            return value;
        }
    }

}
