package pieceTreeDisplayPack;
import java.util.ArrayList;
import java.util.Map.Entry;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import main.PieceProjection;
import main.PieceTreeCompiler;

public class PieceTreeDisplay extends Application{

	private double radius = 150;
	private double center_x = 500;
	private double center_y = 500;
	private ArrayList<SlotDummy> slotDummyList;
	private Group root;
	private Scene primaryScene;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		slotDummyList = new ArrayList<SlotDummy>();
		root = new Group();
		generateDummyAndSubDummies(this, null, PieceTreeCompiler.getPieceClassToProjectionMap()
				.get(PieceTreeCompiler.getRootClass()), 1, 1, "_ROOTSLOT", SlotType.FILLED_SQUARE);
		//Generate dummies starting from the root class
		//Second argument is null because there is no parent dummy
		for(SlotDummy slotDummy : slotDummyList){
			drawSlot(slotDummy);
		}
		this.setPrimaryScene(new Scene(root, 1000, 1000));
		primaryStage.setScene(primaryScene);
		primaryStage.show();
	}
	
	private void generateDummyAndSubDummies(PieceTreeDisplay pieceTreeDisplay, SlotDummy parentDummy, 
			PieceProjection pieceProjection,
			Integer a, Integer b, String slotDiscription, SlotType slotType) {
		// TODO Auto-generated method stub
		SlotDummy slotDummy = new SlotDummy(pieceTreeDisplay, parentDummy, 
				pieceProjection,
				a, b, slotDiscription, slotType);
		slotDummyList.add(slotDummy);
		if(pieceProjection == null){
			return;
		}
		int slotNumberCounter = 0;
		for(Entry<String, Integer> methodSlotGroup : pieceProjection.getFreeMethodSlots().entrySet()){
			slotNumberCounter += methodSlotGroup.getValue();
		}
		for(Entry<String, Integer> objectSlotGroup : pieceProjection.getFreeObjectSlots().entrySet()){
			slotNumberCounter += objectSlotGroup.getValue();
		}
		for(Entry<String, ArrayList<PieceProjection>> methodSlotGroup 
				: pieceProjection.getOccupiedMethodSlots().entrySet()){
			slotNumberCounter += methodSlotGroup.getValue().size();
		}
		for(Entry<String, ArrayList<PieceProjection>> objectSlotGroup 
				: pieceProjection.getOccupiedObjectSlots().entrySet()){
			slotNumberCounter += objectSlotGroup.getValue().size();
		}
		slotNumberCounter += pieceProjection.getMethodMegaSlot().size();
		slotNumberCounter += pieceProjection.getObjectMegaSlot().size();
		int slotIterator = 0;
		for(Entry<String, Integer> methodSlotGroup : pieceProjection.getFreeMethodSlots().entrySet()){
			for(int i = 1; i <= methodSlotGroup.getValue(); i++){
				slotIterator++;
				generateDummyAndSubDummies(this, slotDummy, null, slotNumberCounter
						, slotIterator, methodSlotGroup.getKey(), SlotType.EMPTY_SQUARE);
			}
		}
		for(Entry<String, Integer> objectSlotGroup : pieceProjection.getFreeObjectSlots().entrySet()){
			for(int i = 1; i <= objectSlotGroup.getValue(); i++){
				slotIterator++;
				generateDummyAndSubDummies(this, slotDummy, null, slotNumberCounter
						, slotIterator, objectSlotGroup.getKey(), SlotType.EMPTY_CIRCLE);
			}
		}
		for(Entry<String, ArrayList<PieceProjection>> methodSlotGroup 
				: pieceProjection.getOccupiedMethodSlots().entrySet()){
			for(PieceProjection methodSlotFill : methodSlotGroup.getValue()){
				slotIterator++;
				generateDummyAndSubDummies(this, slotDummy, methodSlotFill, slotNumberCounter
						, slotIterator, methodSlotGroup.getKey(), SlotType.FILLED_SQUARE);
			}
		}
		for(Entry<String, ArrayList<PieceProjection>> objectSlotGroup 
				: pieceProjection.getOccupiedObjectSlots().entrySet()){
			for(PieceProjection objectSlotFill : objectSlotGroup.getValue()){
				slotIterator++;
				generateDummyAndSubDummies(this, slotDummy, objectSlotFill, slotNumberCounter
						, slotIterator, objectSlotGroup.getKey(), SlotType.FILLED_CIRCLE);
			}
		}
		for(PieceProjection greenMethod : pieceProjection.getMethodMegaSlot()){
			slotIterator++;
			generateDummyAndSubDummies(this, slotDummy, greenMethod, slotNumberCounter
					, slotIterator, "_METHODMEGASLOT", SlotType.GREEN_SQUARE);
		}
		for(PieceProjection greenObject : pieceProjection.getObjectMegaSlot()){
			slotIterator++;
			generateDummyAndSubDummies(this, slotDummy, greenObject, slotNumberCounter
					, slotIterator, "_OBJECTMEGASLOT", SlotType.GREEN_CIRCLE);
		}
	}

	public void drawSlot(SlotDummy slotDummy){
		if(slotDummy.getParentDummy() != null){//Draw line
			double startX = slotDummy.getParentDummy().getX() + center_x;
			double startY = -slotDummy.getParentDummy().getY() + center_y;//Remember that y-axis is inverted
			double endX = slotDummy.getX() + center_x;
			double endY = -slotDummy.getY() + center_y;//Remember that y-axis is inverted
			Line line = new Line(startX, startY, endX, endY);
			root.getChildren().add(line);
		}
		{
			switch(slotDummy.getSlotType()){
			case EMPTY_CIRCLE:
				//Remember that y-axis is inverted
				Circle circle = new Circle(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
						, SlotDummy.getCircleBoxRadius());
				circle.setStroke(Color.BLACK);
				circle.setFill(Color.WHITE);
				root.getChildren().add(circle);
				break;
			case EMPTY_SQUARE:
				Rectangle rectangle = new Rectangle(slotDummy.getX() + center_x
						- SlotDummy.getSquareBoxHalfWidth(), -slotDummy.getY() + center_y
						- SlotDummy.getSquareBoxHalfWidth(), 2*SlotDummy.getSquareBoxHalfWidth()
						, 2*SlotDummy.getSquareBoxHalfWidth());
				rectangle.setStroke(Color.BLACK);
				rectangle.setFill(Color.WHITE);
				root.getChildren().add(rectangle);
				break;
			case FILLED_CIRCLE:
				Circle circle1 = new Circle(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
						, SlotDummy.getCircleBoxRadius());
				circle1.setStroke(Color.BLACK);
				circle1.setFill(Color.WHITE);
				root.getChildren().add(circle1);
				Circle circle2 = new Circle(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
						, SlotDummy.getCircleFilledRadius(), Color.BLACK);
				circle2.setStroke(Color.BLACK);
				circle2.setFill(Color.BLACK);
				root.getChildren().add(circle2);
				break;
			case FILLED_SQUARE:
				Rectangle rectangle1 = new Rectangle(slotDummy.getX() + center_x
						- SlotDummy.getSquareBoxHalfWidth(), -slotDummy.getY() + center_y
						- SlotDummy.getSquareBoxHalfWidth(), 2*SlotDummy.getSquareBoxHalfWidth()
						, 2*SlotDummy.getSquareBoxHalfWidth());
				rectangle1.setStroke(Color.BLACK);
				rectangle1.setFill(Color.WHITE);
				root.getChildren().add(rectangle1);
				Rectangle rectangle2 = new Rectangle(slotDummy.getX() + center_x
						- SlotDummy.getSquareFilledHalfWidth(), -slotDummy.getY() + center_y
						- SlotDummy.getSquareFilledHalfWidth(), 2*SlotDummy.getSquareFilledHalfWidth()
						, 2*SlotDummy.getSquareFilledHalfWidth());
				rectangle2.setStroke(Color.BLACK);
				rectangle2.setFill(Color.BLACK);
				root.getChildren().add(rectangle2);
				break;
			case GREEN_CIRCLE:
				Circle circle3 = new Circle(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
						, SlotDummy.getCircleFilledRadius(), Color.GREEN);
				circle3.setStroke(Color.GREEN);
				circle3.setFill(Color.GREEN);
				root.getChildren().add(circle3);
				break;
			case GREEN_SQUARE:
				Rectangle rectangle3 = new Rectangle(slotDummy.getX() + center_x
						- SlotDummy.getSquareFilledHalfWidth(), -slotDummy.getY() + center_y
						- SlotDummy.getSquareFilledHalfWidth(), 2*SlotDummy.getSquareFilledHalfWidth()
						, 2*SlotDummy.getSquareFilledHalfWidth());
				rectangle3.setStroke(Color.GREEN);
				rectangle3.setFill(Color.GREEN);
				root.getChildren().add(rectangle3);
				break;
			default:
				break;
			}
		}
		{
			Text t1 = new Text(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
					- SlotDummy.getSquareBoxHalfWidth() - SlotDummy.getTextToSlotSpacing()
					, slotDummy.getSlotDescription());
			t1.setFont(Font.font (SlotDummy.getFontName(), SlotDummy.getFontSize()));
			t1.setX(t1.getX() - 0.5*t1.getLayoutBounds().getWidth());
			Text t2 = new Text(slotDummy.getX() + center_x, -slotDummy.getY() + center_y
					+ SlotDummy.getSquareBoxHalfWidth() + SlotDummy.getTextHeight() 
					+ SlotDummy.getTextToSlotSpacing()
					, slotDummy.getMemberDescription());
			t2.setFont(Font.font (SlotDummy.getFontName(), SlotDummy.getFontSize()));
			t2.setX(t2.getX() - 0.5*t2.getLayoutBounds().getWidth());
			root.getChildren().add(t1);
			root.getChildren().add(t2);
		}
	}
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public ArrayList<SlotDummy> getSlotDummyList() {
		return slotDummyList;
	}
	public void setSlotDummyList(ArrayList<SlotDummy> slotDummyList) {
		this.slotDummyList = slotDummyList;
	}
	public double getCenter_x() {
		return center_x;
	}
	public void setCenter_x(double center_x) {
		this.center_x = center_x;
	}
	public double getCenter_y() {
		return center_y;
	}
	public void setCenter_y(double center_y) {
		this.center_y = center_y;
	}

	public Scene getPrimaryScene() {
		return primaryScene;
	}

	public void setPrimaryScene(Scene primaryScene) {
		this.primaryScene = primaryScene;
	}



}
