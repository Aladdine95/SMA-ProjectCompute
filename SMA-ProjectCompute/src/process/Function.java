package process;

import java.lang.Math;

public abstract class Function {
	private double min;
	private double max;
	private double delta;

	/**
	 * Class constructor
	 * @param min	lower bound of the interval
	 * @param max	upper bound of the interval
	 * @param delta	step used to compute the integral
	 */
	public Function(double min, double max, double delta) {
		this.min = min;
		this.max = max;
		this.delta = delta;
	}

	/**
	 * Method used to compute the integral using the bounds and the step 
	 * of the object
	 * @return the value of the integral
	 */

	public double eval() {
		int n = (int) ((max - min) / delta); // number of intervals
		double sum = 0;
		for (int i = 0; i < n; i++) {
			double x0 = min + i * delta;
			double x1 = x0 + delta;
			sum += (delta / 6) * (f(x0) + 4 * f((x0 + x1) / 2) + f(x1));
		}
		return sum;
	}

/**
 * Method used to compute the value of the function f at x
 * @param x		double, the number you want the image of through the function f
 * @return		f(x) given the derived class calling it
 */
public abstract double f(double x);

/**
 * toString method to print the different fields of the object
 */
public String toString() {
	return "Function | min = " + min + " ; max = " + max + " ; delta = " + delta + " |";
}
}
