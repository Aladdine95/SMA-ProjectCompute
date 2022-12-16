package process;
/**
 * 
 * Derived Class for the logarithm function in our system. It overrides the f(x) method
 * defined in the super class Function
 * 
 * @author DEBART TEIXEIRA BEN ROMDHANE
 *
 */
public class NeperianLogarithmFunction extends Function{


	/**
	 * Class constructor, using super class constructor Function()
	 * @param min	lower bound of the interval
	 * @param max	upper bound of the interval
	 * @param delta	step used to compute the integral
	 */
	public NeperianLogarithmFunction(double min, double max, double delta) {
		super(min, max, delta);
		// TODO Auto-generated constructor stub
	}


	@Override
	public double f(double x) throws ArithmeticException{
		// TODO Auto-generated method stub
		try {
			return Math.log(x);

		}catch(ArithmeticException e) {
			e.printStackTrace();
			return 0;
		}
	}

}
