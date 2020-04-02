package agents;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.lang.acl.ACLMessage;
import jade.util.ExtendedProperties;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;
import jade.wrapper.StaleProxyException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcheteurGui extends Application {
protected AcheteurAgent acheteurAgent;
ObservableList<String>observableList;
	public static void main(String[] args) {
	launch(args);
}

@Override
public void start(Stage primaryStage) throws Exception {
	// TODO Auto-generated method stub
	startContainer();
	primaryStage.setTitle("Acheteur");
	BorderPane borderPane=new BorderPane();
	VBox vbox=new VBox();
	observableList=FXCollections.observableArrayList();
	ListView<String>listview=new ListView<String>(observableList);
	vbox.getChildren().add(listview);
	borderPane.setCenter(vbox);
	Scene scene=new Scene(borderPane,400,300);
	primaryStage.setScene(scene);
	primaryStage.show();
}

private void startContainer() throws Exception {
	Runtime runtime=Runtime.instance();
    ProfileImpl profilImpl=new ProfileImpl();
    profilImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
    AgentContainer agentContainer=runtime.createAgentContainer(profilImpl);
    AgentController agentController=agentContainer.
    		createNewAgent("ACHETEUR", "agents.AcheteurAgent", new Object[] {this});
     agentController.start();
     agentContainer.start();
}
public void logMessage(ACLMessage aclMessage)
{ Platform.runLater(()->{
	observableList.add(aclMessage.getContent()
			+","+aclMessage.getSender().getName());
});
	
}
}
