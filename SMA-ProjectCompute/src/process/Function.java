package process;

public abstract class Function {
	private double min;
	private double max;
	private double delta;
	
	public Function(double min, double max, double delta) {
		this.min = min;
		this.max = max;
		this.delta = delta;
	}
	
	public abstract double eval();
	public abstract double f(double x);
	
}
