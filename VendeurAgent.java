package agents;

import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
import jade.core.behaviours.ParallelBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class VendeurAgent extends GuiAgent{
	
	protected VendeurGui gui;
	@Override
		protected void setup() {
		if(getArguments().length==1)
		{
			gui=(VendeurGui) getArguments()[0];
			gui.vendeurAgent=this;
		
			
		}
		ParallelBehaviour parrallelBehaviour = new ParallelBehaviour();
		addBehaviour(parrallelBehaviour);
		parrallelBehaviour.addSubBehaviour(new OneShotBehaviour() {
			
			@Override
			public void action() {
				DFAgentDescription  agentdescription=new DFAgentDescription();
				agentdescription.setName(getAID());
				ServiceDescription serviceDescription=new ServiceDescription();
				serviceDescription.setType("transaction");
				serviceDescription.setName("vente-livres");
				agentdescription.addServices(serviceDescription);
				try {
					DFService.register(myAgent, agentdescription);
				} catch (FIPAException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		parrallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
			
			@Override
			public void action() {
				ACLMessage aclMessage=receive();
				if(aclMessage!=null)
				{
					gui.logMessage(aclMessage);
					switch(aclMessage.getPerformative()) {
					case ACLMessage.CFP:
						ACLMessage reply=aclMessage.createReply();
						reply.setPerformative(ACLMessage.PROPOSE);
						reply.setContent(String.valueOf(500+new Random().nextInt(1000)));
						send(reply);
						break;
					case ACLMessage.ACCEPT_PROPOSAL:
						ACLMessage aclMessage2=aclMessage.createReply();
						aclMessage2.setPerformative(ACLMessage.AGREE);
						send(aclMessage2);
						break;
					default:
						break;
					}
				}
				else block();
			}
		});
		}	
	@Override
		protected void onGuiEvent(GuiEvent arg0) {
			// TODO Auto-generated method stub
			
		}
	@Override
	protected void takeDown() {
		try {
			DFService.deregister(this);
		} catch (FIPAException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
