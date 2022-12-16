package process;

public class ExponentialFunction extends Function{

	public ExponentialFunction(double min, double max, double delta) {
		super(min, max, delta);
		// TODO Auto-generated constructor stub
	}

	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		return Math.exp(x);
	}
}
