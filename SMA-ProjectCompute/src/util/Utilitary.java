package util;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

public class Utilitary {
	
	public static String[] operatorsArray = {"Inverse", "Exponential", "NeperianLogarithm"};
	public static String isAgentGivenType(Agent agent) throws FIPAException {
		DFAgentDescription dfd = new DFAgentDescription();
	    ServiceDescription sd1  = new ServiceDescription();
	    String type = "";
	    for(int index = 0; index < operatorsArray.length; index++) {
	    	type = operatorsArray[index];
	    	sd1.setType(type);
			dfd.addServices(sd1);
			DFAgentDescription[] resultDF = DFService.search(agent, dfd);
			for(int indexRes=0; indexRes < resultDF.length; indexRes++) 
			{
				if(resultDF[indexRes].getName().toString().contains(agent.getName())) {
					return type;
				}
				
			}
	    }
		return null;
	}
}
