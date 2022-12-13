package process;

public class InverseFunction extends Function{

	public InverseFunction(double min, double max, double delta) {
		super(min, max, delta);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public double f(double x) {
		// TODO Auto-generated method stub
		try {
			return 1/x; //check math lib
		}catch(numericalException e) {
			e.printStackTrace();
		}
	}

	@Override
	public double eval() { //methode de simpson ou rectangles ?
		// TODO Auto-generated method stub
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
      double sum = 1.0 / 3.0 * (f(a) + f(b));
	 
      // 4/3 terms
      for (int i = 1; i < N - 1; i += 2) {
         double x = a + delta * i;
         sum += 4.0 / 3.0 * f(x);
      }
     	
      // 2/3 terms
      for (int i = 2; i < N - 1; i += 2) {
         double x = a + delta * i;
         sum += 2.0 / 3.0 * f(x);
      }
     return sum * delta;
	 
	
}
