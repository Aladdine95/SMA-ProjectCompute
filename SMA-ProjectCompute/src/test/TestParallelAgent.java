package test;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.*;
import process.*;

/**
 * This agent searches for compute agents that match it's needed services. It divides the given interval equally for all agents.
 * Found agents are then tasked with the computation of their respective intervals. This agent waits for the compute agent's response and
 * sums everything. It also computes the integral without the help of the compute agents in order to compare execution time.
 * 
 * @author BEN ROMDHANE TEIXEIRA DÉBART
 * @version 1.0
 */
public class TestParallelAgent extends Agent {
	/**
	 * Computes the value of an integral with the given parameters without the use of multiple agents.
	 * 
	 * @param lower lower bound of the integral
	 * @param upper upper bound of the integral
	 * @param step step used to compute the integral
	 * @param function function used to compute the integral
	 * @return the value of the integral
	 */
	private double processTPA(double lower, double upper, double step, String function) {
		Function func = null;
		if(function.equals("Inverse")) {
			func = new InverseFunction(lower, upper, step);
		}
		else if(function.equals("NeperianLogarithm")) {
			func = new NeperianLogarithmFunction(lower, upper, step);
		}
		else if(function.equals("Exponential")) {
			func = new ExponentialFunction(lower, upper, step);
		}
		else {
			System.err.println("Function is not recognized.");
		}
		
		return func.eval();
	}
	
	/**
	 * Set the agent up. Initialize the parameters, compute the integral with 1 agent. Find compute agents and compute the integral with these agents.
	 */
	protected void setup() {
		// Default parameters
		double lowerBound = 0;
		double upperBound = 1;
		double step = 0.1;
		String function = "Inverse";
		
		System.out.println("------------------------------");
		System.out.println(getLocalName() + " initialized.");
		// Retrieve arguments
		Object[] args = getArguments();
	    if (args != null && args.length == 4) {
	    	lowerBound = Double.parseDouble((String) args[0]);
	    	upperBound = Double.parseDouble((String) args[1]);
	    	step = Double.parseDouble((String) args[2]);
	    	function = (String) args[3];
	    }
	    else if(args != null && args.length == 3) {
	    	lowerBound = Double.parseDouble((String) args[0]);
	    	upperBound = Double.parseDouble((String) args[1]);
	    	step = Double.parseDouble((String) args[2]);
	    }
	    
	    // Compute the integral with only the TestParallelAgent and times it
	    System.out.println(function);
		long t0 = System.currentTimeMillis();
		double tpaRes = processTPA(lowerBound, upperBound, step, function);
		System.out.println(getLocalName() + " took " + (System.currentTimeMillis() - t0) + "ms " +
				"for the " + function + " Function and the result was : " + tpaRes);
		
	    // Search for agents that can compute
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType(function);
		template.addServices(sd);
		
		AID[] agents = null;
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			System.out.println("" + getLocalName() + ": found " + result.length + " agents.");
			agents = new AID[result.length];
			for(int i = 0; i < result.length; i++) {
				agents[i] = result[i].getName();
			}
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		
		// Divide work for all agents
		double interval = upperBound - lowerBound;
		if(step > interval) {
			step = interval;
			System.out.println("[LOG] Step was too high, reducing it to the interval between upper and lower bounds.");
		}
		
		double dInterval = interval / agents.length;
		double low = lowerBound;
		double high;
		
		// Send a message to found agents
		for(AID aid : agents) {
			System.out.println("\tComputeAgent" + aid.getName() + " can compute.\n");
			high = low + dInterval;
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(aid);
			msg.setContent(high + "," + low + "," + step);
			send(msg);
			
			low = high;
		}
		
		addBehaviour(new ListenBehaviour(this, agents.length));
	}
}

/**
 * Behaviour that waits for a certain number of messages. Adds the results from all the compute agents
 */
class ListenBehaviour extends SimpleBehaviour {
	protected Agent a;
	protected int nbAgents;
	protected double result = 0;
	protected long t0 = System.currentTimeMillis();
	
	/**
	 * Behaviour constructor
	 * 
	 * @param a Agent reference
	 * @param nbAgents Number of compute agents
	 */
	public ListenBehaviour(Agent a, int nbAgents) {
		super(a);
		this.a = a;
		this.nbAgents = nbAgents;
	}
	
	/**
	 * Wait for messages from compute agents
	 */
	public void action() {
		ACLMessage msg = myAgent.receive();
        if (msg!=null) {
          System.out.println(myAgent.getLocalName() + " gave us : " + msg.getContent() );
          result += Double.parseDouble(msg.getContent());

          nbAgents--;
        }
        block();
	}
	
	/**
	 * Stops the behaviour when we have received all the messages
	 */
	public boolean done() {
		if(nbAgents == 0) {
			long t = System.currentTimeMillis();
			System.out.println("\nComputation finished in " + (t - t0) + "ms.");
			System.out.println("Result: " + result);
			System.out.println("\n------------------------------");
			return true;
		} else {
			return false;
		}
	}
}
