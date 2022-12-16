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

public class TestParallelAgent extends Agent {
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
	
	protected void setup() {
		// public int t = 0;
		// Default parameters
		double lowerBound = 0;
		double upperBound = 1;
		double step = 0.1;
//		Function function = new InverseFunction(lowerBound, upperBound, step);
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
	    
	    System.out.println(function);
		long t0 = System.currentTimeMillis();
		double tpaRes = processTPA(lowerBound, upperBound, step, function);
		System.out.println(getLocalName() + " took " + (System.currentTimeMillis() - t0) + "ms " +
				"for the " + function + " Function and the result was : " + tpaRes);
		
	    // Search for agents with that can compute
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
//		addBehaviour(new CyclicBehaviour(this) {
//	        public void action() {
//	          ACLMessage msg = receive();
//	          if (msg!=null) {
//	            System.out.println( " - " +
//	                                myAgent.getLocalName() + " <- " +
//	                                msg.getContent());
//
//	            ACLMessage reply = msg.createReply();
//	            reply.setPerformative( ACLMessage.INFORM );
//	            reply.setContent(" Pong" );
//	            send(reply);
//	          }
//	          block();
//	        }
//	      });
	}
}

class ListenBehaviour extends SimpleBehaviour {
	protected Agent a;
	protected int nbAgents;
	protected double result = 0;
	protected long t0 = System.currentTimeMillis();
	
	public ListenBehaviour(Agent a, int nbAgents) {
		super(a);
		this.a = a;
		this.nbAgents = nbAgents;
	}
	
	public void action() {
		ACLMessage msg = myAgent.receive();
        if (msg!=null) {
          System.out.println(myAgent.getLocalName() + " gave us : " + msg.getContent() );
          result += Double.parseDouble(msg.getContent());

          nbAgents--;
        }
        block();
	}
	
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
