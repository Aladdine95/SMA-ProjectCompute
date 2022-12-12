package intro_sma;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.*;

public class TestParallelAgent extends Agent {
	protected void setup() {
		public int t = 0;
		// Default parameters
		double lowerBound = 0;
		double upperBound = 1;
		double step = 0.1;
//		Function function = new InverseFunction(lowerBound, upperBound, step);
		String function = "InverseFunction";
		
		System.out.println(getLocalName() + " initialized.");
		
		// Retrieve arguments
		Object[] args = getArguments();
	    if (args != null && args.length == 3) {
	    	lowerBound = Double.parseDouble((String) args[0]);
	    	upperBound = Double.parseDouble((String) args[1]);
	    	step = Double.parseDouble((String) args[2]);
	    	
//	    	function = new InverseFunction(lowerBound, upperBound, step);
	    }
	    
	    // Search for agents with that can compute
		DFAgentDescription template = new DFAgentDescription();
		ServiceDescription sd = new ServiceDescription();
		sd.setType("compute");
		template.addServices(sd);
		
		AID[] agents = null;
		try {
			DFAgentDescription[] result = DFService.search(this, template);
			System.out.println(" - " + getLocalName() + ": found " + result.length + " agents.");
			agents = new AID[result.length];
			for(int i = 0; i < result.length; i++) {
				agents[i] = result[i].getName();
			}
		} catch(FIPAException fe) {
			fe.printStackTrace();
		}
		
		// Divide work for all agents
		double interval = upperBound - lowerBound;
		double dInterval = interval / agents.length;
		double low = lowerBound;
		double high;
		
		// Send a message to found agents
		for(AID aid : agents) {
			high = low + dInterval;
			
			ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
			msg.addReceiver(aid);
			msg.setContent(function + ", " + low + ", " + high + ", " + step);
			send(msg);
			
			low = high;
		}
		
//		addBehaviour(new ListenBehaviour(this, agents.length));
		addBehaviour(new CyclicBehaviour(this) {
	        public void action() {
	          ACLMessage msg = receive();
	          if (msg!=null) {
	            System.out.println( " - " +
	                                myAgent.getLocalName() + " <- " +
	                                msg.getContent());

	            ACLMessage reply = msg.createReply();
	            reply.setPerformative( ACLMessage.INFORM );
	            reply.setContent(" Pong" );
	            send(reply);
	          }
	          block();
	        }
	      });
	}
}

class ListenBehaviour extends SimpleBehaviour {
	int nbAgents;
	Agent a;
	
	public ListenBehaviour(Agent a, int nbAgents) {
		super(a);
		this.a = a;
		this.nbAgents = nbAgents
	}
	
	public void action() {
		ACLMessage msg = a.receive();
        	if (msg!=null) {
          	System.out.println( " - " + a.getLocalName() + " <- " + msg.getContent() );

          	nbAgents--;
        	}
       		block();
	}
	
	public boolean done() {
		return nbAgents == 0;
	}
}
