package agents;

import containers.ConsumerContainer;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;

import jade.core.behaviours.ParallelBehaviour;

import jade.gui.GuiAgent;
import jade.gui.GuiEvent;
import jade.lang.acl.ACLMessage;

public class ConsumerAgent extends GuiAgent {
private transient ConsumerContainer gui;

	@Override
protected void setup() { 
		if(getArguments().length==1) {
		gui=(ConsumerContainer) getArguments()[0];
        gui.setConsumerAgent(this);
		}
System.out.println("*****************");
System.out.println("Agent Initialisation ..."+this.getAID().getName());
if(this.getArguments().length==1)
System.out.println("je vais tenter d'acheter le livre "+getArguments()[0]);
System.out.println("*****************");
ParallelBehaviour parrallelBehaviour=new ParallelBehaviour();
addBehaviour(parrallelBehaviour);
parrallelBehaviour.addSubBehaviour(new CyclicBehaviour() {
	
	@Override
	public void action() {
		ACLMessage aclMessage=receive();
		if(aclMessage!=null) {
			gui.logMessage(aclMessage);
			switch (aclMessage.getPerformative()) {
			case ACLMessage.CONFIRM:
				gui.logMessage(aclMessage);
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
protected void beforeMove() { 
System.out.println("*****************");
System.out.println("Avant Migration ...");
System.out.println("*****************");
}
@Override
protected void afterMove() {
System.out.println("*****************");
System.out.println("Après Migration...");
System.out.println("*****************");
}
@Override
protected void takeDown() {
System.out.println("*****************");
System.out.println("I'm going to die .....");
System.out.println("*****************");
}

@Override
public void onGuiEvent(GuiEvent params) {
	if(params.getType()==1)
	{
		String livre=params.getParameter(0).toString();
		ACLMessage aclMessage=new ACLMessage(ACLMessage.REQUEST);
		aclMessage.setContent(livre);
		aclMessage.addReceiver(new AID("ACHETEUR",AID.ISLOCALNAME));
		send(aclMessage);
	
	}
	
}
}
