package matrix;

public class ComplexNumber {
    private final double re;
    private final double im;

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public double getRe() { return re; }
    public double getIm() { return im; }

    public ComplexNumber add(ComplexNumber other) {
        return new ComplexNumber(this.re + other.re, this.im + other.im);
    }

    public ComplexNumber sub(ComplexNumber other) {
        return new ComplexNumber(this.re - other.re, this.im - other.im);
    }

    public ComplexNumber mul(ComplexNumber other) {
        double newRe = this.re * other.re - this.im * other.im;
        double newIm = this.re * other.im + this.im * other.re;
        return new ComplexNumber(newRe, newIm);
    }

    public ComplexNumber div(ComplexNumber other) {
        double denominator = other.re * other.re + other.im * other.im;
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        double newRe = (this.re * other.re + this.im * other.im) / denominator;
        double newIm = (this.im * other.re - this.re * other.im) / denominator;
        return new ComplexNumber(newRe, newIm);
    }

    @Override
    public String toString() {
        if (im == 0) return String.valueOf(re);
        if (re == 0) return im + "i";
        if (im < 0) return re + " - " + (-im) + "i";
        return re + " + " + im + "i";
    }
}