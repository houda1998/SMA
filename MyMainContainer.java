package containers;


import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.ControllerException;
public class MyMainContainer {
public static void main(String[] args) {
	Runtime runtime=Runtime.instance();
	ProfileImpl profilImpl=new ProfileImpl();
	profilImpl.setParameter(ProfileImpl.GUI, "true");
	AgentContainer mainContainer=runtime.createMainContainer(profilImpl);
	try {
		mainContainer.start();
	} catch (ControllerException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
}
