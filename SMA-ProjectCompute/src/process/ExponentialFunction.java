package process;

public class ExponentialFunction extends Function{

	public ExponentialFunction(double min, double max, double delta) {
		super(min, max, delta);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double f(double x) throws ArithmeticException {
		// TODO Auto-generated method stub
		try {
			return Math.exp(x);
		}catch(ArithmeticException e) {
			e.printStackTrace();
			return 0;
		}
		
	}
}
