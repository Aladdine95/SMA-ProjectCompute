package process;

import java.util.Arrays;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import util.Utilitary;

/**
 * 
 * To setup the wanted function, define the first argument of the agent as the function, three function are available : 
 * 	- InverseFunction (1/x) -> setup argument would be : Inverse
 *  - ExponentialFunction (e^x) -> setup argument would be : Exponential
 *  - NeperianLogarithmFunction (ln(x)) -> setup argument would be : NeperianLogarithm
 * If no argument is defined or the argument is unknown, the default one would be : InverseFunction
 * More arguments will lead nowhere as they will not be used.
 * 
 * This agent's behavior is to receive a certain type of message with 3 components to do a certain type of calculation and send the result.
 * 	- The Agent is supposed to receive a message with the following format (separated with commas): UpperBoundary, LowerBoundary and the Step
 *  - Once the message is received, calculation begins
 *  - Once the calculation ends, it is returned to the transmitter
 * 
 * 	@param f Function
 *	@author BEN ROMDHANE TEIXEIRA DÉBART
 *	@version 1.0
 */
public class ComputeAgent extends Agent{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Function f;

	protected void setup() {
		// Setting up the service descriptor as for the compute agent (First argument is going to be the selected function)
	    ServiceDescription sd  = new ServiceDescription();
	    try {
	        sd.setType(getArguments()[0].toString());
	    }
	    catch(Exception e){
	        sd.setType("Inverse");
	    }
	    sd.setName(getLocalName());
	    register(sd);
	    
		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 2201275969106691134L;
			public void action() {
				ACLMessage msg = receive();
				double result = -1;
				if (msg!=null) {
					String recievedMessage = msg.getContent().toString();
					String[] parameters = recievedMessage.split(",");
					double upper = 0;
					double lower = 0;
					double step = 0;
					String agentSDType = null;
					try {
						upper = Double.valueOf(parameters[0]);
						lower = Double.valueOf(parameters[1]);
						step = Double.valueOf(parameters[2]);
						agentSDType = Utilitary.isAgentGivenType(this.getAgent());
						Function func = null;
						if(agentSDType.equals("Inverse")) {
							func = new InverseFunction(lower, upper, step);
						}
						else if(agentSDType.equals("NeperianLogarithm")) {
							func = new NeperianLogarithmFunction(lower, upper, step);
						}
						else if(agentSDType.equals("Exponential")) {
							func = new ExponentialFunction(lower, upper, step);
						}
						else {
							System.err.println("Function is not recognized.");
						}
						
						if(func != null) {
							result = func.eval();
						}
							
					}
					catch(Exception e) {
						System.err.println("Wrong format was recieved, aborting the calculations");
					}
					
					ACLMessage reply = msg.createReply();
					reply.setPerformative( ACLMessage.INFORM );
					reply.setContent(Double.toString(result));
					send(reply);
				}
				block();
			}
		});
	}

	protected void takeDown() {
		if(isAlive()) {
			super.takeDown();
			System.out.println("Taking down agent " + getAID().getName());
		
		}
		else {
			System.out.println("Agent " + getAID().getName() + " is already deleted.");
		}
	}

	protected void register(ServiceDescription sd) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(getAID());
		dfd.addServices(sd);

		try {
			DFService.register(this, dfd);
		}
		catch(FIPAException ex) { 
			ex.printStackTrace(); 
		}
	}
}
