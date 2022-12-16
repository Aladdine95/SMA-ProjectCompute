package process;

public class InverseFunction extends Function{

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
