package process;

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
