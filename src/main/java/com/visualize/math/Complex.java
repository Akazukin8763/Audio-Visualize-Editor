package com.visualize.math;

public class Complex {

    private final double real;
    private final double imaginary;

    // Constructor
    public Complex() {
        this(0, 0);
    }

    public Complex(double real) {
        this(real, 0);
    }

    public Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    // Methods
    public Complex plus(Complex val) {
        double real = this.real + val.real;
        double imaginary = this.imaginary + val.imaginary;
        return new Complex(real, imaginary);
    }

    public Complex minus(Complex val) {
        double real = this.real - val.real;
        double imaginary = this.imaginary - val.imaginary;
        return new Complex(real, imaginary);
    }

    public Complex times(Complex val) {
        double real = this.real * val.real - this.imaginary * val.imaginary;
        double imaginary = this.real * val.imaginary + this.imaginary * val.real;
        return new Complex(real, imaginary);
    }

    public double abs() {
        return Math.sqrt(this.real * this.real + this.imaginary * this.imaginary);
    }

    @Override
    public String toString() {
        return String.format("(%.2f, %.2f)", real, imaginary);
    }
}
