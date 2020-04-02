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
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class VendeurGui extends Application {
protected VendeurAgent vendeurAgent;
ObservableList<String>observableList;
AgentContainer agentContainer;
	public static void main(String[] args) {
	launch(args);
}
@Override
public void start(Stage primaryStage) throws Exception {
	// TODO Auto-generated method stub
	startContainer();
	primaryStage.setTitle("vendeur");
	HBox hbox=new HBox();
	hbox.setPadding(new Insets(10));
	hbox.setSpacing(10);
	Label label=new Label("Agent name : ");
	TextField textFieldAgentName=new TextField();
	Button buttonDeploy=new Button("Deploy");
	hbox.getChildren().addAll(label,textFieldAgentName,buttonDeploy);
	BorderPane borderPane=new BorderPane();
	VBox vbox=new VBox();
	observableList=FXCollections.observableArrayList();
	ListView<String>listview=new ListView<String>(observableList);
	vbox.getChildren().add(listview);
	borderPane.setTop(hbox);
	borderPane.setCenter(vbox);
	Scene scene=new Scene(borderPane,400,300);
	primaryStage.setScene(scene);
	primaryStage.show();
	buttonDeploy.setOnAction((evt)->{
		AgentController agentController;
		try {
			String name=textFieldAgentName.getText();
			agentController = agentContainer.
					createNewAgent(name, "agents.VendeurAgent", new Object[] {this});
			agentController.start();
		} catch (StaleProxyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	});
}
private void startContainer() throws Exception {
	Runtime runtime=Runtime.instance();
    ProfileImpl profilImpl=new ProfileImpl();
    profilImpl.setParameter(ProfileImpl.MAIN_HOST,"localhost");
   agentContainer=runtime.createAgentContainer(profilImpl);
    
     agentContainer.start();
}
public void logMessage(ACLMessage aclMessage)
{  Platform.runLater(()->{
	observableList.add(aclMessage.getContent()
			+","+aclMessage.getSender().getName());
});
}
}
