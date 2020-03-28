package fairyChessPack1;

import java.util.ArrayList;

import chronosPack.InitializerDefault;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import main.PieceTreeCompiler;
import pieceTreeDisplayPack.SlotDummy;
import pieceTreeDisplayPack.SlotType;

public class FairyChessApplication extends Application{
	private Pane root;
	private Scene primaryScene;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		root = new Pane();
		this.setPrimaryScene(new Scene(root, 800, 800));
		primaryStage.setScene(primaryScene);
		primaryStage.show();
		root.setStyle("-fx-background-color: #8B4513;");
		InitializerDefault.setFairyChessApplication(this);
		InitializerDefault.initialize2();
	}
	
	public Pane getRoot(){
		return root;
	}
	
	public void setRoot(Pane root){
		this.root = root;
	}
	
	public Scene getPrimaryScene() {
		return primaryScene;
	}

	public void setPrimaryScene(Scene primaryScene) {
		this.primaryScene = primaryScene;
	}
}
