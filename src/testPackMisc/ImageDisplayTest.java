package testPackMisc;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import main.PieceTreeCompiler;
import pieceTreeDisplayPack.SlotDummy;
import pieceTreeDisplayPack.SlotType;

public class ImageDisplayTest extends Application{

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		Group root = new Group();
		Image image1 = new Image("fairyChessPack1/imageFolder1/Chess_bdt60.png", 100, 100, false, false);
		ImageView imageView1 = new ImageView(image1);
		root.getChildren().add(imageView1);
		primaryStage.setScene(new Scene(root, 1000, 1000));
		primaryStage.show();
	}
}
