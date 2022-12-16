package util;

import jade.core.Agent;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * Utilitary class for methods usable everywhere.
 * 
 * @author aladd
 *
 */
public class Utilitary {
	
	/**
	 * Little enumeration of all the possible functions, could be betterly written but do the job good enough.
	 */
	public static String[] operatorsArray = {"Inverse", "Exponential", "NeperianLogarithm"};
	
	/**
	 * isAgentGivenType checks out if agent belongs to a given type. Really useful to dynamically understand 
	 * a given agent type without storing the given arguments to type it.
	 * @param agent
	 * @return
	 * @throws FIPAException
	 */
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
