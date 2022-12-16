package process;

import java.lang.Math;

public abstract class Function {
	private double min;
	private double max;
	private double delta;
	
	public Function(double min, double max, double delta) {
		this.min = min;
		this.max = max;
		this.delta = delta;
	}
	
	public abstract double eval() {
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
	
	  N = (min-max)/this.delta;
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
	public abstract double f(double x);
	
	public String toString() {
		return "Function | min = " + min + " ; max = " + max + " ; delta = " + delta + " |";
	}
}
