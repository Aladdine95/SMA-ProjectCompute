package process;

/**
 * Derived Class for the inverse function in our system. It overrides the f(x) method
 * defined in the super class Function. The inverse function is especially usefull
 * to check if the calculus is right: 
 * the integral between 1 and exp of the function 1/x equals 1.
 * Therefore, we can check if our system is accurate by using this function
 * 
 * @author BEN ROMDHANE DEBART TEIXEIRA
 *
 */
public class InverseFunction extends Function{

	/**
	 * Class constructor, using super class constructor Function()
	 * @param min	lower bound of the interval
	 * @param max	upper bound of the interval
	 * @param delta	step used to compute the integral
	 */
	public InverseFunction(double min, double max, double delta) {
		super(min, max, delta);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double f(double x) throws ArithmeticException{
		// TODO Auto-generated method stub
		try {
			return 1/x; //check math lib
		}catch(ArithmeticException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
