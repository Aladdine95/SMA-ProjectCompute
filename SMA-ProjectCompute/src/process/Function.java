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
		 //methode de simpson ou rectangles ?
		//rectangles :
//		float tmp = 0;
//		float sum = 0;
//		while(tmp<(this.max-this.min)) {
//			sum += f(min + tmp) * this.delta;
//			tmp += this.delta;
//		}
//		return sum;
//	}
	/*
	 * simpson method:
     *double h = (b - a) / (N - 1);     // step size = delta
     */
      // 1/3 terms
	
	  double N = (min-max)/this.delta;
      double sum = 1.0 / 3.0 * (f(min) + f(max));
	 
      // 4/3 terms
      for (int i = 1; i < N - 1; i += 2) {
         double x = min + delta * i;
         sum += 4.0 / 3.0 * f(x);
      }
     	
      // 2/3 terms
      for (int i = 2; i < N - 1; i += 2) {
         double x = min + delta * i;
         sum += 2.0 / 3.0 * f(x);
      }
     return sum * delta;
	 
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
