package FipaContractNet;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.domain.FIPANames;

import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;

/**
   This example shows how to implement the initiator role in 
   a FIPA-contract-net interaction protocol. In this case in particular 
   we use a <code>ContractNetInitiator</code>  
   to assign a dummy task to the agent that provides the best offer
   among a set of agents (whose local
   names must be specified as arguments).
   @author Giovanni Caire - TILAB
 */
public class Customer extends Agent {
	private int nResponders;
	protected void setup() { 
  	// Read names of responders as arguments
  	Object[] args = getArguments();
  	if (args != null && args.length > 0) {
  		
  		// Fill the CFP message
  		ACLMessage msg = new ACLMessage(ACLMessage.CFP);
                msg.addReceiver(new AID((String) "amazon1", AID.ISLOCALNAME)); 
                msg.addReceiver(new AID((String) "amazon2", AID.ISLOCALNAME));
                msg.addReceiver(new AID((String) "alibaba", AID.ISLOCALNAME));
                msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                // We want to receive a reply in 10 secs
                msg.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                String[] paymentIncluded = args[0].toString().split("-");
                if(paymentIncluded.length==2){
                    msg.setContent(paymentIncluded[0]+"-"+paymentIncluded[1]);
                }else{
                    msg.setContent((String) args[0]);
                }
			
			addBehaviour(new ContractNetInitiator(this, msg) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("El agente "+propose.getSender().getName()+" proposed "+propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("El agente "+refuse.getSender().getName()+" no tiene el producto");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("El agente "+failure.getSender().getName()+" fallo");
					}
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("Tiempo de espera terminado: se perdieron "+(nResponders - responses.size())+" respuestas");
					}
					int bestProposal = 8000000;
                                        String promocion = null, item = null;
					AID bestProposer = null;
					ACLMessage accept = null;
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
                                                        String[] partsProposal = msg.getContent().split("-");
							int proposal = Integer.parseInt(partsProposal[0]);
							if (proposal < bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
                                                                promocion = partsProposal[1];
                                                                item = partsProposal[2];
								accept = reply;
							}
						}
					}
					// Accept the proposal of the best proposer
					if (accept != null) {
						System.out.println("Aceptando "+paymentIncluded[0]+" con un precio de "+bestProposal+" con la promocion de "+promocion+" "+item+" de "+bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}						
				}
				
				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
				}
			} );
            if(args.length == 2){
                ACLMessage msg2 = new ACLMessage(ACLMessage.CFP);
                msg2.addReceiver(new AID((String) "amazon1", AID.ISLOCALNAME)); 
                msg2.addReceiver(new AID((String) "amazon2", AID.ISLOCALNAME));
                msg2.addReceiver(new AID((String) "alibaba", AID.ISLOCALNAME));
                msg2.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
                // We want to receive a reply in 10 secs
                msg2.setReplyByDate(new Date(System.currentTimeMillis() + 10000));
                String[] paymentIncluded2 = args[1].toString().split("-");
                if(paymentIncluded2.length==2){
                    msg2.setContent(paymentIncluded2[0]+"-"+paymentIncluded2[1]);
                }else{
                    msg2.setContent((String) args[1]);
                }
			
			addBehaviour(new ContractNetInitiator(this, msg2) {
				
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("El agente "+propose.getSender().getName()+" propone "+propose.getContent());
				}
				
				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("El agente "+refuse.getSender().getName()+" no tiene el producto");
				}
				
				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						System.out.println("Responder does not exist");
					}
					else {
						System.out.println("El agente "+failure.getSender().getName()+" fallo");
					}
				}
				
				protected void handleAllResponses(Vector responses, Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified timeout
						System.out.println("Tiempo de espera terminado: se perdieron "+(nResponders - responses.size())+" respuestas");
					}
					// Evaluate proposals.
					int bestProposal = 800000;
                                        String promocion = null, item = null;
					AID bestProposer = null;
					ACLMessage accept = null;
					Enumeration e = responses.elements();
					while (e.hasMoreElements()) {
						ACLMessage msg = (ACLMessage) e.nextElement();
						if (msg.getPerformative() == ACLMessage.PROPOSE) {
							ACLMessage reply = msg.createReply();
							reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
							acceptances.addElement(reply);
                                                        String[] partsProposal = msg.getContent().split("-");
							int proposal = Integer.parseInt(partsProposal[0]);
							if (proposal < bestProposal) {
								bestProposal = proposal;
								bestProposer = msg.getSender();
                                                                promocion = partsProposal[1];
                                                                item = partsProposal[2];
								accept = reply;
							}
						}
					}
					// Accept the proposal of the best proposer
					if (accept != null) {
						System.out.println("Aceptando "+paymentIncluded[1]+" con un precio de "+bestProposal+" con la promocion de "+promocion+" "+item+" de "+bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}						
				}
				
				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent "+inform.getSender().getName()+" successfully performed the requested action");
				}
			} );
            }
  	}
  	else {
  		System.out.println("No arguments specified.");
  	}
  } 
}

