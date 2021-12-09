package FipaContractNet;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.clipsrules.jni.Environment;
import net.sf.clipsrules.jni.FactAddressValue;
import net.sf.clipsrules.jni.MultifieldValue;

public class Seller extends Agent {
    Environment clips = new Environment();

    
	protected void setup() {
		System.out.println("Agent "+getLocalName()+" esperando por CFP...");
		MessageTemplate template = MessageTemplate.and(
				MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
				MessageTemplate.MatchPerformative(ACLMessage.CFP) );

		addBehaviour(new ContractNetResponder(this, template) {
			@Override
			protected ACLMessage handleCfp(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
                                System.out.println("Agente "+getLocalName()+": CFP recibio de "+cfp.getSender().getName()+" la orden  "+cfp.getContent());
                                String[] orderPayment = cfp.getContent().split("-");
                                String order = orderPayment[0];
                                String payment = null;
                                if(orderPayment.length==2){
                                    payment = orderPayment[1];
                                }
                                clips.load("facts-template.clp");
                                if(getLocalName().equals("amazon1")){
                                    clips.load("facts-amazon1.clp");
                                    clips.load("rules-amazon1.clp");
                                }else if(getLocalName().equals("amazon2")){
                                    clips.load("facts-amazon2.clp");
                                    clips.load("rules-amazon2.clp");
                                }else if(getLocalName().equals("alibaba")){
                                    clips.load("facts-alibaba.clp");
                                    clips.load("rules-alibaba.clp");
                                }else{
                                    clips.clear();
                                    throw new RefuseException("El agente "+getLocalName()+" no es un vendedor participante");
                                }
                                clips.reset();
                                clips.eval("(assert("+order+"))");
                                if(orderPayment.length==2){
                                    clips.eval("(assert("+payment+"))");
                                }else{
                                    clips.eval("(assert (payorder(method contado)))");
                                }
				clips.run();
                                MultifieldValue mv = (MultifieldValue) clips.eval("(find-all-facts ((?d offer)) TRUE)");
                                List<FactAddressValue> fact = mv.multifieldValue();
                                String precio = null, item = null;
                                String promocion = null;
                            if (fact.size()>0) {
                                try {
                                   precio = fact.get(0).getFactSlot("price").toString();
                                   promocion = fact.get(0).getFactSlot("promotion").toString();
                                   item = fact.get(0).getFactSlot("item").toString();
                                } catch (Exception ex) {
                                    Logger.getLogger(Seller.class.getName()).log(Level.SEVERE, null, ex);
                                }

                                // We provide a proposal
                                System.out.println("Agente "+getLocalName()+": proponiendo "+order+" a un precio de "+precio+" con la promocion "+promocion+" y ademas "+item);
                                ACLMessage propose = cfp.createReply();
                                propose.setPerformative(ACLMessage.PROPOSE);
                                propose.setContent((precio+"-"+promocion+"-"+item));
                                
                                clips.clear();
                                return propose;
                            }
                                    System.out.println("Agente "+getLocalName()+": se rehusó");
                                    
                                clips.clear();
                                    throw new RefuseException("evaluation-failed");
                        }

			@Override
			protected ACLMessage handleAcceptProposal(ACLMessage cfp, ACLMessage propose,ACLMessage accept) throws FailureException {
                            System.out.println("Agente "+getLocalName()+": accion realizada exitosamente");
                            ACLMessage inform = accept.createReply();
                            inform.setPerformative(ACLMessage.INFORM);
                            return inform;	
			}

			protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
				System.out.println("Agente "+getLocalName()+": Propuesta rechazada");
			}
		} );
	}


}
