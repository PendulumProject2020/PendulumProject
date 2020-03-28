package chronosPack;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import fairyChessPack1.ControllerProxyFactory;
import fairyChessPack1.Epifyte;
import fairyChessPack1.EpifyteArm;
import fairyChessPack1.EpifyteDisplayEventInterpreter;
import fairyChessPack1.EpifyteModifier;
import fairyChessPack1.FairyChessApplication;
import fairyChessPack1.InputCommandConverter;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import main.CommandExecutor;
import main.DataSet;
import main.MethodPiece;
import main.Pair;
import main.Piece;
import main.PieceTreeCompiler;
import main.Quintuple;
import main.SlotPath;
import main.World;
import pieceTreeDisplayPack.SlotDummy;
import pieceTreeDisplayPack.SlotType;

public class InitializerDefault extends MethodPiece{
	
	private static EpifyteModifier whiteControllerProxy;
	private static EpifyteModifier blackControllerProxy;
	private static FairyChessApplication fairyChessApplication;
	private static Epifyte gameBoard;
	private static RoundController epifyteMasterController = new RoundController();
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos"));
		String slotName = "INITIALIZERSLOT";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(
				Arrays.asList(new SlotPath(hostPath, slotName)));
		return possibleFits;
	}
	
	public static Map<String, Integer> getMethodPieceSlots(){
		return Piece.makeSimpleSlotList();//TODO
	}
	public static Map<String, Integer> getObjectPieceSlots(){
		return Piece.makeSimpleSlotList();//TODO
	}
	public static ArrayList<String> getDependentPieceNames(){
		return new ArrayList<String>();
	}
	public static void initialize(){
		//TODO
		PieceTreeCompiler.getPieceClassToStaticNamedDataSupersetMap()
		.put(PieceTreeCompiler.getPieceClassToProjectionMap().get(InitializerDefault.class)
				.getHostPiece().getOrigin(), new HashMap<String, DataSet>());
		PieceTreeCompiler.getPieceClassToStaticDataSupersetMap()
		.put(PieceTreeCompiler.getPieceClassToProjectionMap().get(InitializerDefault.class)
				.getHostPiece().getOrigin(), new HashMap<Class<Piece>, DataSet>());
		/*PieceTreeCompiler.getPieceClassToStaticNamedDataSupersetMap()
		.get(PieceTreeCompiler.getPieceClassToProjectionMap().get(InitializerDefault.class)
				.getHostPiece().getOrigin()).put("roundControllers", 
						new DataSet(ArrayList.class, new ArrayList<Agent>()));*/
		//Roughly speaking, the above lines serve to prevent NullPointerException
		Application.launch(FairyChessApplication.class, (String[]) null);
	}
	public static void initialize2(){
		setWhiteControllerProxy(ControllerProxyFactory.makeControllerProxy("White"));
		whiteControllerProxy.setDataSet("frontDirection", new DataSet(Integer.class, 1));
		setBlackControllerProxy(ControllerProxyFactory.makeControllerProxy("Black"));
		blackControllerProxy.setDataSet("frontDirection", new DataSet(Integer.class, -1));
		Chronos.addRoundController(epifyteMasterController);
		gameBoard = createCartesianSquareBoardOfDimensions(8, 8, 500.0, 500.0);
		EpifyteModifier identityBundleWR1 = makeIdentityBundle("Rook", "White");
		Epifyte whiteRook1 = makeChessPiece(identityBundleWR1);
		gamePieceSetControllerProxy(whiteRook1, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteRook1, gameBoard, new Pair<Integer, Integer>(1, 1));
		EpifyteModifier identityBundleWR2 = makeIdentityBundle("Rook", "White");
		Epifyte whiteRook2 = makeChessPiece(identityBundleWR2);
		gamePieceSetControllerProxy(whiteRook2, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteRook2, gameBoard, new Pair<Integer, Integer>(8, 1));
		EpifyteModifier identityBundleWB1 = makeIdentityBundle("Bishop", "White");
		Epifyte whiteBishop1 = makeChessPiece(identityBundleWB1);
		gamePieceSetControllerProxy(whiteBishop1, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteBishop1, gameBoard, new Pair<Integer, Integer>(3, 1));
		EpifyteModifier identityBundleWB2 = makeIdentityBundle("Bishop", "White");
		Epifyte whiteBishop2 = makeChessPiece(identityBundleWB2);
		gamePieceSetControllerProxy(whiteBishop2, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteBishop2, gameBoard, new Pair<Integer, Integer>(6, 1));
		EpifyteModifier identityBundleWN1 = makeIdentityBundle("Knight", "White");
		Epifyte whiteKnight1 = makeChessPiece(identityBundleWN1);
		gamePieceSetControllerProxy(whiteKnight1, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteKnight1, gameBoard, new Pair<Integer, Integer>(2, 1));
		EpifyteModifier identityBundleWN2 = makeIdentityBundle("Knight", "White");
		Epifyte whiteKnight2 = makeChessPiece(identityBundleWN2);
		gamePieceSetControllerProxy(whiteKnight2, whiteControllerProxy);
		putGamePieceOnSquareBoard(whiteKnight2, gameBoard, new Pair<Integer, Integer>(7, 1));
		epifyteMasterController.addRoundControlTarget(whiteRook1);
		epifyteMasterController.addRoundControlTarget(whiteRook2);
		epifyteMasterController.addRoundControlTarget(whiteBishop1);
		epifyteMasterController.addRoundControlTarget(whiteBishop2);
		epifyteMasterController.addRoundControlTarget(whiteKnight1);
		epifyteMasterController.addRoundControlTarget(whiteKnight2);
		for(int i = 1; i <= 8; i++) {
			EpifyteModifier identityBundleWP = makeIdentityBundle("Pawn", "White");
			Epifyte whitePawn = makeChessPiece(identityBundleWP);
			gamePieceSetControllerProxy(whitePawn, whiteControllerProxy);
			
			putGamePieceOnSquareBoard(whitePawn, gameBoard, new Pair<Integer, Integer>(i, 2));
			epifyteMasterController.addRoundControlTarget(whitePawn);
		}
		epifyteMasterController.addRoundControlTarget(whiteControllerProxy);
		
		EpifyteModifier identityBundleBR1 = makeIdentityBundle("Rook", "Black");
		Epifyte blackRook1 = makeChessPiece(identityBundleBR1);
		gamePieceSetControllerProxy(blackRook1, blackControllerProxy);
		putGamePieceOnSquareBoard(blackRook1, gameBoard, new Pair<Integer, Integer>(1, 8));
		EpifyteModifier identityBundleBR2 = makeIdentityBundle("Rook", "Black");
		Epifyte blackRook2 = makeChessPiece(identityBundleBR2);
		gamePieceSetControllerProxy(blackRook2, blackControllerProxy);
		putGamePieceOnSquareBoard(blackRook2, gameBoard, new Pair<Integer, Integer>(8, 8));
		EpifyteModifier identityBundleBB1 = makeIdentityBundle("Bishop", "Black");
		Epifyte blackBishop1 = makeChessPiece(identityBundleBB1);
		gamePieceSetControllerProxy(blackBishop1, blackControllerProxy);
		putGamePieceOnSquareBoard(blackBishop1, gameBoard, new Pair<Integer, Integer>(3, 8));
		EpifyteModifier identityBundleBB2 = makeIdentityBundle("Bishop", "Black");
		Epifyte blackBishop2 = makeChessPiece(identityBundleBB2);
		gamePieceSetControllerProxy(blackBishop2, blackControllerProxy);
		putGamePieceOnSquareBoard(blackBishop2, gameBoard, new Pair<Integer, Integer>(6, 8));
		EpifyteModifier identityBundleBN1 = makeIdentityBundle("Knight", "Black");
		Epifyte blackKnight1 = makeChessPiece(identityBundleBN1);
		gamePieceSetControllerProxy(blackKnight1, blackControllerProxy);
		putGamePieceOnSquareBoard(blackKnight1, gameBoard, new Pair<Integer, Integer>(2, 8));
		EpifyteModifier identityBundleBN2 = makeIdentityBundle("Knight", "Black");
		Epifyte blackKnight2 = makeChessPiece(identityBundleBN2);
		gamePieceSetControllerProxy(blackKnight2, blackControllerProxy);
		putGamePieceOnSquareBoard(blackKnight2, gameBoard, new Pair<Integer, Integer>(7, 8));
		epifyteMasterController.addRoundControlTarget(blackRook1);
		epifyteMasterController.addRoundControlTarget(blackRook2);
		epifyteMasterController.addRoundControlTarget(blackBishop1);
		epifyteMasterController.addRoundControlTarget(blackBishop2);
		epifyteMasterController.addRoundControlTarget(blackKnight1);
		epifyteMasterController.addRoundControlTarget(blackKnight2);
		for(int i = 1; i <= 8; i++) {
			EpifyteModifier identityBundleBP = makeIdentityBundle("Pawn", "Black");
			Epifyte blackPawn = makeChessPiece(identityBundleBP);
			gamePieceSetControllerProxy(blackPawn, blackControllerProxy);
			
			putGamePieceOnSquareBoard(blackPawn, gameBoard, new Pair<Integer, Integer>(i, 7));
			epifyteMasterController.addRoundControlTarget(blackPawn);
		}
		epifyteMasterController.addRoundControlTarget(blackControllerProxy);
		setAsComputer(blackControllerProxy);
		RoundSignallerContinuous.beginRoundSignalLoop();
	}
	private static void setAsComputer(Epifyte controllerProxy) {
		controllerProxy.setTags("CONTROLLER_PROXY", "COMPUTER_CONTROLLER");
		controllerProxy.setRoundExecutor(() -> {
			World.setUserInteraction(false);
			while(true) {
				Random rng = new Random();
				Epifyte chessPiece = controllerProxy.getLower()
						.get(rng.nextInt(controllerProxy.getLower().size()));
				if(chessPiece.getTags().contains("CHESS_PIECE")) {
					ArrayList<Epifyte> attackRange = (ArrayList<Epifyte>) chessPiece
							.evaluateInstanceInformation("epifyteSeek attackRange").getEntry();
					if(attackRange.isEmpty() == false) {
						Random rng2 = new Random();
						Epifyte targetCell = attackRange.get(rng2.nextInt(attackRange.size()));
						chessPiece.executeCommandWithDataSet("epifyteDo attack", 
								new DataSet(Epifyte.class, targetCell));
						break;
					}
					else {
						ArrayList<Epifyte> movementRange = (ArrayList<Epifyte>) chessPiece
								.evaluateInstanceInformation("epifyteSeek movementRange").getEntry();
						if(movementRange.isEmpty() == false) {
						Random rng2 = new Random();
						Epifyte targetCell = movementRange.get(rng2.nextInt(movementRange.size()));
						chessPiece.executeCommandWithDataSet("epifyteDo move", 
								new DataSet(Epifyte.class, targetCell));
						break;
						}
					}
				}
			}
			controllerProxy.getParentControllers().get(0).proceedToNextTarget();
			World.setUserInteraction(true);
			System.out.println("controllerProxy round executed");
		});
	}
	private static Epifyte createCartesianSquareBoardOfDimensions(Integer width, Integer height,
			Double displayWidth, Double displayHeight){
		Epifyte squareBoard = new Epifyte();
		squareBoard.setTags("BOARD", "CARTESIAN", "SQUARE");
		squareBoard.setDataSet("width", new DataSet(Integer.class, width));
		squareBoard.setDataSet("height", new DataSet(Integer.class, height));
		squareBoard.setDataSet("displayWidth", new DataSet(Double.class, displayWidth));
		squareBoard.setDataSet("displayHeight", new DataSet(Double.class, displayHeight));
		Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw 
		= new HashMap<Pair<Integer, Integer>, Epifyte>();
		EpifyteModifier boardTextureCase = new EpifyteModifier();
		boardTextureCase.setTags("TEXTURE_CASE");
		boardTextureCase.setEpifyteInformationFinder((informationName, propagatedDataSet, 
				informationRoute, SupportDataSet) -> {
				if(informationName.equals("texture")){
					return new DataSet(ImageView.class, boardTextureCase.getDataSet("texture").getEntry());
				}
				if(informationName.equals("textureCase")){
					return new DataSet(EpifyteModifier.class, boardTextureCase);
				}
				if(informationName.equals("relativeDisplayCoordinates")){
					//Display coordinates relative to the lower texture
					return new DataSet(Pair.class, new Pair<Double, Double>(0.0, 0.0));
				}
				if(informationName.equals("realDisplayCoordinates")){
					if(boardTextureCase.getSingleLowerOfTag("TEXTURE_CASE") == null){
						return new DataSet(Pair.class, new Pair<Double, Double>(400.0, -400.0));
					}
					else{
						EpifyteModifier lowerTextureCase 
						= (EpifyteModifier) boardTextureCase.getSingleLowerOfTag("TEXTURE_CASE");
						Pair<Double, Double> previousDisplayCoordinates
						= (Pair<Double, Double>) 
						lowerTextureCase.getEpifyteInformationFinder()
						.findInformation("realDisplayCoordinates", null, null, null).getEntry();
						Pair<Double, Double> relativeDisplayCoordinates
						= (Pair<Double, Double>) boardTextureCase
						.evaluateInstanceInformation("relativeDisplayCoordinates").getEntry();
						return new DataSet(Pair.class, new Pair<Double, Double>(
								previousDisplayCoordinates.getFirst() 
								+ relativeDisplayCoordinates.getFirst(),
								previousDisplayCoordinates.getSecond() 
								+ relativeDisplayCoordinates.getSecond()));
					}
				}
				return propagatedDataSet;
			}
		);
		
		Epifyte.forceBind(boardTextureCase, squareBoard);
		for(int i = 1; i <= width; i++){
			for(int j = 1; j <= height; j++){
				Integer iConstant = i;
				Integer jConstant = j;
				Epifyte cell = new Epifyte();
				cell.setTags("BOARD_CELL");
				cell.setDataSet("coordinates", new DataSet(Pair.class, new Pair<Integer, Integer>(i, j)));
				Epifyte.forceBind(cell, squareBoard);
				coordinatesToCellMapRaw.put(new Pair<Integer, Integer>(i, j), cell);
				EpifyteModifier textureCase = new EpifyteModifier();
				Double boardWidth = new Double((Integer) squareBoard.getDataSet("width").getEntry());
				Double boardHeight = new Double((Integer) squareBoard.getDataSet("height").getEntry());
				Double boardDisplayWidth = (Double) squareBoard.getDataSet("displayWidth").getEntry();
				Double boardDisplayHeight = (Double) squareBoard.getDataSet("displayHeight").getEntry();
				Double cellWidth = boardDisplayWidth / boardWidth;
				Double cellHeight = boardDisplayHeight / boardHeight;
				Rectangle square = new Rectangle();
				square.setWidth(cellWidth);
				square.setHeight(cellHeight);
				if((iConstant + jConstant) % 2 == 0){
					square.setFill(Color.DARKGREY);
					//black chess pieces need to be visible against background
				}
				else{
					square.setFill(Color.WHITE);
				}
				square.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						System.out.println("Click detected.");
						EpifyteDisplayEventInterpreter.executeConverter(cell
								, arg0, null);
					}
				});
				textureCase.setTags("TEXTURE_CASE");
				textureCase.setDataSet("texture", new DataSet(Rectangle.class, square));
				textureCase.setDataSet("visibility", new DataSet(Boolean.class, true));
				textureCase.setEpifyteInformationFinder((informationName, propagatedDataSet, 
						informationRoute, SupportDataSet) -> {
							Double boardWidth1 = new Double((Integer) textureCase.getSingleLowerOfTag("BOARD_CELL")
									.getSingleLowerOfTag("BOARD").getDataSet("width").getEntry());
							Double boardHeight1 = new Double((Integer) textureCase.getSingleLowerOfTag("BOARD_CELL")
									.getSingleLowerOfTag("BOARD").getDataSet("height").getEntry());
							Double boardDisplayWidth1 = (Double) textureCase.getSingleLowerOfTag("BOARD_CELL")
									.getSingleLowerOfTag("BOARD").getDataSet("displayWidth").getEntry();
							Double boardDisplayHeight1 = (Double) textureCase.getSingleLowerOfTag("BOARD_CELL")
									.getSingleLowerOfTag("BOARD").getDataSet("displayHeight").getEntry();
							Double cellWidth1 = boardDisplayWidth1 / boardWidth1;
							Double cellHeight1 = boardDisplayHeight1 / boardHeight1;
						if(informationName.equals("cellWidth")){
							return new DataSet(Double.class, cellWidth1);
						}
						if(informationName.equals("cellHeight")){
							return new DataSet(Double.class, cellHeight1);
						}
						if(informationName.equals("texture")){
							return new DataSet(Rectangle.class, textureCase.getDataSet("texture").getEntry());
						}
						if(informationName.equals("textureCase")){
							return new DataSet(EpifyteModifier.class, textureCase);
						}
						if(informationName.equals("relativeDisplayCoordinates")){
							Pair<Integer, Integer> coordinates = 
									(Pair<Integer, Integer>) textureCase.getSingleLowerOfTag("BOARD_CELL")
									.getDataSet("coordinates").getEntry();
							Integer x = coordinates.getFirst();
							Integer y = coordinates.getSecond();
							//Display coordinates relative to the lower texture
							return new DataSet(Pair.class, new Pair<Double, Double>(
									cellWidth1*(new Double(x) - ((Double) (boardWidth1 + 1))/2.0)
									, cellHeight1*(new Double(y) - ((Double) (boardHeight1 + 1))/2.0)));
						}
						if(informationName.equals("realDisplayCoordinates")){
							if(textureCase.getSingleLowerOfTag("TEXTURE_CASE") == null){
								return new DataSet(Pair.class, new Pair<Double, Double>(0.0, 0.0));
							}
							else{
								EpifyteModifier lowerTextureCase 
								= (EpifyteModifier) textureCase.getSingleLowerOfTag("TEXTURE_CASE");
								Pair<Double, Double> previousDisplayCoordinates
								= (Pair<Double, Double>) 
								lowerTextureCase.getEpifyteInformationFinder()
								.findInformation("realDisplayCoordinates", null, null, null).getEntry();
								Pair<Double, Double> relativeDisplayCoordinates
								= (Pair<Double, Double>) textureCase
								.getEpifyteInformationFinder().
								findInformation("relativeDisplayCoordinates", null, null, null).getEntry();
								return new DataSet(Pair.class, new Pair<Double, Double>(
										previousDisplayCoordinates.getFirst() 
										+ relativeDisplayCoordinates.getFirst(),
										previousDisplayCoordinates.getSecond() 
										+ relativeDisplayCoordinates.getSecond()));
							}
						}
						if(informationName.equals("visibility")){
							return new DataSet(Boolean.class, textureCase.getDataSet("visibility").getEntry());
						}
						
						return propagatedDataSet;
					});
				CommandExecutor originalCommandExecutor = textureCase.getCommandExecutor();
				textureCase.setCommandExecutor((target, command, dataset) -> {
					originalCommandExecutor.interfaceExecuteCommand(target, command, dataset);
					if(command.equals("update")){
						System.out.println("Cell textureCase is executing update command");
						Rectangle texture = (Rectangle) textureCase.getDataSet("texture").getEntry();
						if(textureCase.getSingleLowerOfTag("TEXTURE_CASE") == null){
							fairyChessApplication.getRoot().getChildren().remove(texture);
						}
						else{
						Pair<Double, Double> realCoordinates 
						= (Pair<Double, Double>) textureCase.getEpifyteInformationFinder()
								.findInformation("realDisplayCoordinates", null, null, null).getEntry();
						Double realX = realCoordinates.getFirst();
						Double realY = realCoordinates.getSecond();
						Double cellWidth2 = (Double) textureCase.getEpifyteInformationFinder()
								.findInformation("cellWidth", null, null, null).getEntry();
						Double cellHeight2 = (Double) textureCase.getEpifyteInformationFinder()
								.findInformation("cellHeight", null, null, null).getEntry();
						texture.setX(realX - 0.5*cellWidth2);
						texture.setY(-realY - 0.5*cellHeight2);
						if(fairyChessApplication.getRoot().getChildren().contains(texture) == false){
							fairyChessApplication.getRoot().getChildren().add(texture);
							System.out.println("Added a cell texture");
						}
						}
					}
				});
				RoundExecutor originalRoundExecutor = textureCase.getRoundExecutor();
				textureCase.setRoundExecutor(() -> {
					textureCase.executeCommand("update");
					originalRoundExecutor.interfaceExecuteRound();
				});
				epifyteMasterController.addRoundControlTarget(textureCase);
				EpifyteDisplayEventInterpreter.addConverter(cell, makeBoardCellInputCommandConverter(cell));
				Epifyte.forceBind(textureCase, cell);
				Epifyte.forceBind(textureCase, boardTextureCase);
				textureCase.executeCommand("update");
			}
		}
		DataSet coordinatesToCellMap = new DataSet(Map.class, coordinatesToCellMapRaw);
		squareBoard.setDataSet("coordinatesToCellMap", coordinatesToCellMap);
		
			PieceTreeCompiler.getPieceClassToStaticNamedDataSupersetMap()
			.putIfAbsent(InitializerDefault.class, new HashMap<String, DataSet>());
		PieceTreeCompiler.getPieceClassToStaticNamedDataSupersetMap().
			get(InitializerDefault.class).put("squareBoardReference", 
							new DataSet(Epifyte.class, squareBoard));
		return squareBoard;
	}
	private static void putGamePieceOnSquareBoard(Epifyte gamePiece, Epifyte squareBoard, 
			Pair<Integer, Integer> coordinates){
		Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw = 
				(Map<Pair<Integer, Integer>, Epifyte>) 
				squareBoard.getDataSet("coordinatesToCellMap").getEntry();
		Epifyte targetCell = coordinatesToCellMapRaw.get(coordinates);
		Epifyte.forceBind(gamePiece, targetCell);
		EpifyteModifier gamePieceTextureCase = 
				(EpifyteModifier) gamePiece.evaluateInstanceInformation("epifyteSeek textureCase").getEntry();
		Epifyte.forceBind((EpifyteModifier) gamePiece.evaluateInstanceInformation("epifyteSeek textureCase").getEntry()
				, (EpifyteModifier) targetCell.evaluateInstanceInformation("epifyteSeek textureCase").getEntry());
		//Consider if attemptBind is better
		gamePieceTextureCase.executeCommand("update");
	}
	private static Epifyte makeChessPiece(EpifyteModifier identityBundle){
		Epifyte chessPiece = new Epifyte();
		chessPiece.setTags("CHESS_PIECE");
		Epifyte.forceBind(identityBundle, chessPiece);
		EpifyteDisplayEventInterpreter.addConverter(chessPiece, makeChessPieceInputCommandConverter(chessPiece));
		return chessPiece;
	}
	private static InputCommandConverter makeChessPieceInputCommandConverter(Epifyte chessPiece){
		//TODO
		InputCommandConverter inputCommandConverter = (event, supportDataSet) -> {
			if(MouseEvent.class.isAssignableFrom(event.getClass())){
				if(World.isUserInteraction() == true){
					World.setUserInteraction(false);
					ArrayList<Agent> activeAgents = EpifyteDisplayEventInterpreter.getActiveAgents();
					for(Agent activeAgent : activeAgents){
						if((EpifyteModifier)
							chessPiece.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry() ==
							(EpifyteModifier) activeAgent){
							if(EpifyteDisplayEventInterpreter.getSelectedEpifytes().isEmpty()){
							//Interprets as the player intending to move this piece
							EpifyteDisplayEventInterpreter.setSelectedEpifytes(
								new ArrayList<Epifyte>(Arrays.asList(chessPiece)));
							event.consume();
							}
							else{
								Epifyte attackingPiece = EpifyteDisplayEventInterpreter.getSelectedEpifytes().get(0);
								
								ArrayList<Epifyte> attackRange
								= (ArrayList<Epifyte>) 
								attackingPiece.evaluateInstanceInformation("epifyteSeek attackRange").getEntry();
								if(attackRange.contains(chessPiece.getSingleLowerOfTag("BOARD_CELL"))){
									attackingPiece.executeCommandWithDataSet(
											"epifyteDo attack", new DataSet(Epifyte.class
													, chessPiece.getSingleLowerOfTag("BOARD_CELL")));
								}
								EpifyteDisplayEventInterpreter.setSelectedEpifytes(
										new ArrayList<Epifyte>());
								Integer movesLeft = (Integer) activeAgent.getDataSet("movesLeft").getEntry();
								if(movesLeft <= 0){
									activeAgent.getParentControllers().get(0).proceedToNextTarget();
									return;
								}
							}
						}
					}
					World.setUserInteraction(true);
				}
			}
		};
		return inputCommandConverter;
	}
	private static InputCommandConverter makeBoardCellInputCommandConverter(Epifyte cell){
		//TODO
		InputCommandConverter inputCommandConverter = (event, supportDataSet) -> {
			if(MouseEvent.class.isAssignableFrom(event.getClass())){
				if(World.isUserInteraction() == true){
					World.setUserInteraction(false);
					ArrayList<Agent> activeAgents = EpifyteDisplayEventInterpreter.getActiveAgents();
					for(Agent activeAgent : activeAgents){
						if(EpifyteDisplayEventInterpreter.getSelectedEpifytes().isEmpty() == false){
							//Interprets as the user wanting to move there
							Epifyte chessPiece = EpifyteDisplayEventInterpreter.getSelectedEpifytes().get(0);
							
							ArrayList<Epifyte> movementRange
							= (ArrayList<Epifyte>) 
							chessPiece.evaluateInstanceInformation("epifyteSeek movementRange").getEntry();
							System.out.println("Movement range: ");
							for(Epifyte validCell : movementRange){
								Pair<Integer, Integer> coordinates = 
										(Pair<Integer, Integer>) validCell.getDataSet("coordinates").getEntry();
								System.out.println("(" + Integer.toString(coordinates.getFirst())
								+ ", " + Integer.toString(coordinates.getSecond()) + ")");
							}
							ArrayList<Epifyte> attackRange
							= (ArrayList<Epifyte>) 
							chessPiece.evaluateInstanceInformation("epifyteSeek attackRange").getEntry();
							if(movementRange.contains(cell)){
								System.out.println("chessPiece beginning move command");
								chessPiece.executeCommandWithDataSet(
										"epifyteDo move", new DataSet(Epifyte.class, cell));
							}
							else if(attackRange.contains(cell)){
								chessPiece.executeCommandWithDataSet(
										"epifyteDo attack", new DataSet(Epifyte.class, cell));
							}
							else{
								EpifyteDisplayEventInterpreter.setSelectedEpifytes(
									new ArrayList<Epifyte>());
							}
						}
						Integer movesLeft = (Integer) activeAgent.getDataSet("movesLeft").getEntry();
						if(movesLeft <= 0){
							activeAgent.getParentControllers().get(0).proceedToNextTarget();
							return;
						}
					}
					World.setUserInteraction(true);
				}
			}
		};
		
		return inputCommandConverter;
	}
	private static void gamePieceSetControllerProxy(Epifyte gamePiece, EpifyteModifier controllerProxy){
		Epifyte.forceBind(controllerProxy, gamePiece);
	}
	private static EpifyteModifier makeIdentityBundle(String pieceType, String colorName){
		EpifyteModifier identityBundle = new EpifyteModifier();
		identityBundle.setTags("IDENTITY_BUNDLE");
		identityBundle.setEpifyteInformationFinder((informationName, propagatedDataSet, informationRoute,
				supportDataSet) -> {
					if(informationName == "identityBundle"){
						return new DataSet(EpifyteModifier.class, identityBundle);
					}
					return propagatedDataSet;
				});
		EpifyteModifier typeCase = makeTypeCase(pieceType);
		Epifyte.forceBind(typeCase, identityBundle);
		EpifyteModifier textureCase = makeTextureCase(pieceType, colorName);
		Epifyte.forceBind(textureCase, identityBundle);
		EpifyteArm moveArm = makeChessMoveArm(pieceType);
		Epifyte.forceBind(moveArm, identityBundle);
		EpifyteArm attackArm = makeChessAttackArm(pieceType);
		Epifyte.forceBind(attackArm, identityBundle);
		return identityBundle;
	}
	private static EpifyteModifier makeTypeCase(String pieceType){
		EpifyteModifier typeCase = new EpifyteModifier();
		typeCase.setTags("TYPE_CASE");
		typeCase.setEpifyteInformationFinder((informationName, propagatedDataSet, 
				informationRoute, SupportDataSet) -> {
				if(informationName == "type"){
					return new DataSet(String.class, pieceType);
				}
				if(informationName == "typeCase"){
					return new DataSet(EpifyteModifier.class, typeCase);
				}
				return propagatedDataSet;
			}
		);
		return typeCase;
	}
	private static EpifyteModifier makeTextureCase(String pieceType, String colorName){
		Image chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_xxx60.png", 60, 60, false, false);
		if(pieceType.equals("Rook") && colorName.equals("White")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_rlt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Rook") && colorName.equals("Black")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_rdt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Bishop") && colorName.equals("White")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_blt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Bishop") && colorName.equals("Black")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_bdt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Knight") && colorName.equals("White")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_nlt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Knight") && colorName.equals("Black")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_ndt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Pawn") && colorName.equals("White")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_plt60.png", 60, 60, false, false);
		}
		else if(pieceType.equals("Pawn") && colorName.equals("Black")){
			chessPieceImage = new Image("fairyChessPack1/imageFolder1/Chess_pdt60.png", 60, 60, false, false);
		}
		ImageView chessPieceImageView = new ImageView(chessPieceImage);
		EpifyteModifier textureCase = new EpifyteModifier();
		textureCase.setTags("TEXTURE_CASE");
		chessPieceImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>()
				{
					@Override
					public void handle(MouseEvent arg0) {
						// TODO Auto-generated method stub
						System.out.println("Click detected.");
						EpifyteDisplayEventInterpreter.executeConverter(textureCase
								.getSingleLowerOfTag("IDENTITY_BUNDLE").getSingleLowerOfTag("CHESS_PIECE")
								, arg0, null);
					}
				});
		textureCase.setDataSet("texture", new DataSet(ImageView.class, chessPieceImageView));
		textureCase.setEpifyteInformationFinder((informationName, propagatedDataSet, 
				informationRoute, SupportDataSet) -> {
				if(informationName.equals("texture")){
					return new DataSet(ImageView.class, textureCase.getDataSet("texture").getEntry());
				}
				if(informationName.equals("textureCase")){
					return new DataSet(EpifyteModifier.class, textureCase);
				}
				if(informationName.equals("relativeDisplayCoordinates")){
					//Display coordinates relative to the lower texture
					return new DataSet(Pair.class, new Pair<Double, Double>(0.0, 0.0));
				}
				if(informationName.equals("realDisplayCoordinates")){
					if(textureCase.getSingleLowerOfTag("TEXTURE_CASE") == null){
						return new DataSet(Pair.class, new Pair<Double, Double>(0.0, 0.0));
					}
					else{
						EpifyteModifier previousTextureCase = 
								(EpifyteModifier) textureCase.getSingleLowerOfTag("TEXTURE_CASE");
						Pair<Double, Double> previousDisplayCoordinates
						= (Pair<Double, Double>) previousTextureCase.getEpifyteInformationFinder()
						.findInformation("realDisplayCoordinates", null, null, null).getEntry();
						Pair<Double, Double> relativeDisplayCoordinates
						= (Pair<Double, Double>) textureCase
						.getEpifyteInformationFinder()
						.findInformation("relativeDisplayCoordinates", null, null, null).getEntry();
						return new DataSet(Pair.class, new Pair<Double, Double>(
								previousDisplayCoordinates.getFirst() 
								+ relativeDisplayCoordinates.getFirst(),
								previousDisplayCoordinates.getSecond() 
								+ relativeDisplayCoordinates.getSecond()));
					}
				}
				return propagatedDataSet;
			}
		);
		epifyteMasterController.addRoundControlTarget(textureCase);
		CommandExecutor originalCommandExecutor = textureCase.getCommandExecutor();
		textureCase.setCommandExecutor((target, command, dataset) -> {
			originalCommandExecutor.interfaceExecuteCommand(target, command, dataset);
			if(command.equals("update")){
				System.out.println("command.equals('update')");
				ImageView texture = (ImageView) textureCase.getDataSet("texture").getEntry();
				if(textureCase.getSingleLowerOfTag("TEXTURE_CASE") == null){
					fairyChessApplication.getRoot().getChildren().remove(texture);
					System.out.println("texture removed");
				}
				else{
				Pair<Double, Double> realCoordinates 
				= (Pair<Double, Double>) textureCase.getEpifyteInformationFinder()
						.findInformation("realDisplayCoordinates", null, null, null).getEntry();
				Double realX = realCoordinates.getFirst();
				Double realY = realCoordinates.getSecond();
				Double pieceWidth2 = (Double) texture.getFitWidth();
				Double pieceHeight2 = (Double) texture.getFitHeight();
				texture.setX(realX -30);
				texture.setY(-realY -30);
				if(fairyChessApplication.getRoot().getChildren().contains(texture) == false){
					fairyChessApplication.getRoot().getChildren().add(texture);
				}
				}
			}
		});
		
		RoundExecutor originalRoundExecutor = textureCase.getRoundExecutor();
		textureCase.setRoundExecutor(() -> {
			textureCase.executeCommand("update");
			originalRoundExecutor.interfaceExecuteRound();
		});
		return textureCase;
	}
	private static EpifyteArm makeChessMoveArm(String pieceType){
		EpifyteArm chessMoveArm = new EpifyteArm();
		chessMoveArm.setTags("MOVE_ARM");
		chessMoveArm.setEpifyteCommandHandler((command, commandRoute, supportDataSet)
				-> {
					if(command.equals("move")){
						System.out.println("command.equals('move')");
						System.out.println("supportDataSet entryType" + supportDataSet.getEntryType().toString());
						if(supportDataSet.getEntryType() == Epifyte.class){
							System.out.println("supportDataSet.getEntryType() == Epifyte.class");
							Epifyte cellPresumed = (Epifyte) supportDataSet.getEntry();
							if(cellPresumed.getTags().contains("BOARD_CELL")){
								boolean flag = false;
								Epifyte currentCell = null;
								
								if(commandRoute.get(0).getSingleLowerOfTag("BOARD_CELL") != null){
									currentCell = commandRoute.get(0).getSingleLowerOfTag("BOARD_CELL");
									flag = true;
								}
								if(flag == true){
								System.out.println("flag == true");
								EpifyteModifier textureCase = (EpifyteModifier) commandRoute.get(0)
										.evaluateInstanceInformation("epifyteSeek textureCase").getEntry();
								Epifyte.forceMove((EpifyteModifier) 
										textureCase, (EpifyteModifier) 
										textureCase.getSingleLowerOfTag("TEXTURE_CASE")
										, (EpifyteModifier) cellPresumed
										.evaluateInstanceInformation("epifyteSeek textureCase").getEntry());
								Epifyte.forceMove(commandRoute.get(0), currentCell, cellPresumed);
								
										textureCase.executeCommand("update");
								EpifyteModifier controllerProxy = (EpifyteModifier) commandRoute.get(0).
										evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry();
								Integer movesLeft = (Integer) 
										controllerProxy.getDataSet("movesLeft").getEntry();
								movesLeft--;
								controllerProxy.setDataSet("movesLeft", new DataSet(Integer.class, movesLeft));
								}
							}
						}
					}
				});
		if(pieceType.equals("Rook")){
			EpifyteModifier basicMovementRange = new EpifyteModifier();
			basicMovementRange.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
						Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
						informationQuintuple = helper1(informationName, propagatedDataSet
								, informationRoute, supportDataSet);
						if(informationQuintuple == null){
							System.out.println("informationQuintuple == null");
							return propagatedDataSet;
						}
						Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
						= informationQuintuple.getFirst();
						int currentX = informationQuintuple.getSecond();
						int currentY = informationQuintuple.getThird();
						ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
						DataSet dataSet = informationQuintuple.getFifth();
						//Horizontal movement
						for(int i = 1; true; i++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i, currentY)) == null){
								break;
							}
							else if(i != currentX){
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(i, currentY)));
							}
						}
						//Vertical movement
						for(int j = 1; true; j++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j)) == null){
								break;
							}
							else if(j != currentY){
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX, j)));
							}
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						if(dataSetArrayList == null){
							System.out.println("basicMovementRange: null dataSetArrayList");
						}
						else if(dataSetArrayList.isEmpty()){
							System.out.println("basicMovementRange: empty dataSetArrayList");
						}
						else{
							System.out.println("basicMovementRange: non-empty dataSetArrayList");
						}
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier obstructionDetector = new EpifyteModifier();
			obstructionDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
						//Left obstruction
						for(int i = currentX - 1; i >= 1; i--){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i, currentY)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i, currentY))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; i2 >= 1; i2--){
									if(coordinatesToCellMapRaw.get(
											new Pair<Integer, Integer>(i2, currentY)) == null){
											break;
										}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(i2, currentY)));
								}
								break;
							}
						}
						//Right obstruction
						for(int i = currentX + 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i, currentY)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i, currentY))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; true; i2++){
								if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i2, currentY)) == null){
									break;
								}
								dataSetArrayList.remove(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i2, currentY)));
								}
								break;
							}
						}
						//Down obstruction
						for(int j = currentY - 1; j >= 1; j--){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int j2 = j; j2 >= 1; j2--){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX, j2)) == null){
										break;
									}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX, j2)));
								}
								break;
							}
						}
						//Up obstruction
						for(int j = currentY + 1; true; j++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int j2 = j; true; j2++){
								if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j2)) == null){
									break;
								}
								dataSetArrayList.remove(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j2)));
								}
								break;
							}
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						return dataSet;
						}
						return propagatedDataSet;
					});
			Epifyte.forceBind(obstructionDetector, basicMovementRange);
			Epifyte.forceBind(basicMovementRange, chessMoveArm);
		}
		else if(pieceType.equals("Bishop")){
			EpifyteModifier basicMovementRange = new EpifyteModifier();
			basicMovementRange.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
						Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
						informationQuintuple = helper1(informationName, propagatedDataSet
								, informationRoute, supportDataSet);
						if(informationQuintuple == null){
							System.out.println("informationQuintuple == null");
							return propagatedDataSet;
						}
						Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
						= informationQuintuple.getFirst();
						int currentX = informationQuintuple.getSecond();
						int currentY = informationQuintuple.getThird();
						ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
						DataSet dataSet = informationQuintuple.getFifth();
						//Up-Left movement
						for(int i = 1; true; i++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - i, currentY + i)) == null){
								break;
							}
							else{
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i, currentY + i)));
							}
						}
						//Up-Right movement
						for(int i = 1; true; i++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + i, currentY + i)) == null){
								break;
							}
							else{
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i, currentY + i)));
							}
						}
						//Down-Left movement
						for(int i = 1; true; i++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - i, currentY - i)) == null){
								break;
							}
							else{
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i, currentY - i)));
							}
						}
						//Down-Right movement
						for(int i = 1; true; i++){
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + i, currentY - i)) == null){
								break;
							}
							else{
								dataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i, currentY - i)));
							}
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						if(dataSetArrayList == null){
							System.out.println("basicMovementRange: null dataSetArrayList");
						}
						else if(dataSetArrayList.isEmpty()){
							System.out.println("basicMovementRange: empty dataSetArrayList");
						}
						else{
							System.out.println("basicMovementRange: non-empty dataSetArrayList");
						}
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier obstructionDetector = new EpifyteModifier();
			obstructionDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
						//Up-Left obstruction
						for(int i = 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - i, currentY + i)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - i, currentY + i))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; true; i2++){
									if(coordinatesToCellMapRaw.get(
											new Pair<Integer, Integer>(currentX - i2, currentY + i2)) == null){
											break;
										}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i2, currentY + i2)));
								}
								break;
							}
						}
						//Up-Right obstruction
						for(int i = 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + i, currentY + i)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + i, currentY + i))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; true; i2++){
									if(coordinatesToCellMapRaw.get(
											new Pair<Integer, Integer>(currentX + i2, currentY + i2)) == null){
											break;
										}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i2, currentY + i2)));
								}
								break;
							}
						}
						//Down-Left obstruction
						for(int i = 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - i, currentY - i)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - i, currentY - i))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; true; i2++){
									if(coordinatesToCellMapRaw.get(
											new Pair<Integer, Integer>(currentX - i2, currentY - i2)) == null){
											break;
										}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i2, currentY - i2)));
								}
								break;
							}
						}
						//Down-Right obstruction
						for(int i = 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + i, currentY - i)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + i, currentY - i))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								for(int i2 = i; true; i2++){
									if(coordinatesToCellMapRaw.get(
											new Pair<Integer, Integer>(currentX + i2, currentY - i2)) == null){
											break;
										}
								dataSetArrayList.remove(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i2, currentY - i2)));
								}
								break;
							}
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						return dataSet;
						}
						return propagatedDataSet;
					});
			Epifyte.forceBind(obstructionDetector, basicMovementRange);
			Epifyte.forceBind(basicMovementRange, chessMoveArm);
		}
		else if(pieceType.equals("Knight")){
			EpifyteModifier basicMovementRange = new EpifyteModifier();
			basicMovementRange.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
						Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
						informationQuintuple = helper1(informationName, propagatedDataSet
								, informationRoute, supportDataSet);
						if(informationQuintuple == null){
							System.out.println("informationQuintuple == null");
							return propagatedDataSet;
						}
						Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
						= informationQuintuple.getFirst();
						int currentX = informationQuintuple.getSecond();
						int currentY = informationQuintuple.getThird();
						ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
						DataSet dataSet = informationQuintuple.getFifth();
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX - 2, currentY - 1)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - 2, currentY - 1)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX - 2, currentY + 1)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - 2, currentY + 1)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX - 1, currentY - 2)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - 1, currentY - 2)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX - 1, currentY + 2)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX - 1, currentY + 2)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX + 1, currentY - 2)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + 1, currentY - 2)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX + 1, currentY + 2)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + 1, currentY + 2)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX + 2, currentY - 1)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + 2, currentY - 1)));
						}
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX + 2, currentY + 1)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX + 2, currentY + 1)));
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						if(dataSetArrayList == null){
							System.out.println("basicMovementRange: null dataSetArrayList");
						}
						else if(dataSetArrayList.isEmpty()){
							System.out.println("basicMovementRange: empty dataSetArrayList");
						}
						else{
							System.out.println("basicMovementRange: non-empty dataSetArrayList");
						}
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier obstructionDetector = new EpifyteModifier();
			obstructionDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							Iterator<Epifyte> iterator = dataSetArrayList.iterator();
							while(iterator.hasNext()){
								Epifyte cell = iterator.next();
								if(cell.getSingleUpperOfTag("CHESS_PIECE") != null) {
									iterator.remove();
								}
							}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						return dataSet;
						}
						return propagatedDataSet;
					});
			Epifyte.forceBind(obstructionDetector, basicMovementRange);
			Epifyte.forceBind(basicMovementRange, chessMoveArm);
		}
		else if(pieceType.equals("Pawn")){
			EpifyteModifier basicMovementRange = new EpifyteModifier();
			basicMovementRange.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
						Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
						informationQuintuple = helper1(informationName, propagatedDataSet
								, informationRoute, supportDataSet);
						if(informationQuintuple == null){
							System.out.println("informationQuintuple == null");
							return propagatedDataSet;
						}
						Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
						= informationQuintuple.getFirst();
						int currentX = informationQuintuple.getSecond();
						int currentY = informationQuintuple.getThird();
						ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
						DataSet dataSet = informationQuintuple.getFifth();
						Epifyte controllerProxy = (Epifyte) informationRoute.get(0)
								.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry();
						Integer frontDirection = (Integer) 
								controllerProxy.getDataSet("frontDirection").getEntry();
						if(coordinatesToCellMapRaw.get(
								new Pair<Integer, Integer>(currentX, currentY + frontDirection)) != null){
							dataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, currentY + frontDirection)));
						}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						if(dataSetArrayList == null){
							System.out.println("basicMovementRange: null dataSetArrayList");
						}
						else if(dataSetArrayList.isEmpty()){
							System.out.println("basicMovementRange: empty dataSetArrayList");
						}
						else{
							System.out.println("basicMovementRange: non-empty dataSetArrayList");
						}
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteArm specialMove = new EpifyteArm();
			specialMove.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							Epifyte controllerProxy = (Epifyte) informationRoute.get(0)
									.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry();
							Integer frontDirection = (Integer) 
									controllerProxy.getDataSet("frontDirection").getEntry();
							Epifyte specialMoveCell = coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, currentY + 2*frontDirection));
							if(specialMoveCell != null && specialMoveCell.getSingleUpperOfTag("CHESS_PIECE") == null) {
								dataSetArrayList.add(specialMoveCell);
							}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						return dataSet;
						}
						return propagatedDataSet;
					});
			specialMove.setEpifyteCommandHandler((command, commandRoute, supportDataSet) -> {
				if(command.equals("move")) {
					Epifyte.forceSlideOutFromAbove(specialMove, specialMove.getLower().get(0));
				}
			});
			EpifyteModifier obstructionDetector = new EpifyteModifier();
			obstructionDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("movementRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							Iterator<Epifyte> iterator = dataSetArrayList.iterator();
							while(iterator.hasNext()){
								Epifyte cell = iterator.next();
								if(cell.getSingleUpperOfTag("CHESS_PIECE") != null) {
									iterator.remove();
								}
							}
						dataSet.setEntry(dataSetArrayList);//Not sure if this line is necessary
						return dataSet;
						}
						return propagatedDataSet;
					});
			Epifyte.forceBind(obstructionDetector, specialMove);
			Epifyte.forceBind(specialMove, basicMovementRange);
			Epifyte.forceBind(basicMovementRange, chessMoveArm);
		}
		//TODO
		return chessMoveArm;
	}
	
	private static Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet> 
	//coordinatesToCellMapRaw, currentX, currentY, dataSetArrayList, dataSet
	helper1(String informationName, DataSet propagatedDataSet
			, ArrayList<Epifyte> informationRoute, DataSet supportDataSet){
		DataSet dataSet = propagatedDataSet;
		if(propagatedDataSet == null){
			dataSet = new DataSet(ArrayList.class, new ArrayList<Epifyte>());
		}
		ArrayList<Epifyte> validCellList = (ArrayList<Epifyte>) dataSet.presentAsArrayList();
		Epifyte hostBoard = informationRoute.get(0).traceLowerTillTag("BOARD");
		if(hostBoard == null){
			System.out.println("hostBoard == null");
			return null;
		}
		Pair<Integer, Integer> coordinates = (Pair<Integer, Integer>) 
				informationRoute.get(0).getSingleLowerOfTag("BOARD_CELL")
				.getDataSet("coordinates").getEntry();
		int currentX = coordinates.getFirst();
		int currentY = coordinates.getSecond();
		DataSet coordinatesToCellMap = hostBoard.getDataSet("coordinatesToCellMap");
		Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
		= (Map<Pair<Integer, Integer>, Epifyte>) coordinatesToCellMap.getEntry();
		ArrayList<Epifyte> dataSetArrayList = 
				(ArrayList<Epifyte>) dataSet.presentAsArrayList();
		return new Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, 
				DataSet>(coordinatesToCellMapRaw, currentX, currentY, dataSetArrayList, dataSet);
	}
	
	private static EpifyteArm makeChessAttackArm(String pieceType){
		EpifyteArm chessAttackArm = new EpifyteArm();
		chessAttackArm.setTags("ATTACK_ARM");
		chessAttackArm.setEpifyteCommandHandler((command, commandRoute, supportDataSet)
				-> {
					if(command.equals("attack")){
						if(supportDataSet.getEntryType() == Epifyte.class){
							Epifyte cellPresumed = (Epifyte) supportDataSet.getEntry();
							if(cellPresumed.getTags().contains("BOARD_CELL")){
								boolean flag = false;
								Epifyte currentCell = null;
								
								if(commandRoute.get(0).getSingleLowerOfTag("BOARD_CELL") != null){
									currentCell = commandRoute.get(0).getSingleLowerOfTag("BOARD_CELL");
									flag = true;
								}
								if(flag == true){
								Epifyte originalPiece = cellPresumed.getSingleUpperOfTag("CHESS_PIECE");
								
								EpifyteModifier textureCase = (EpifyteModifier) commandRoute.get(0)
										.evaluateInstanceInformation("epifyteSeek textureCase").getEntry();
								EpifyteModifier originalTextureCase = (EpifyteModifier) originalPiece.
										evaluateInstanceInformation("epifyteSeek textureCase").getEntry();		
								Epifyte.forceDetach(originalTextureCase
										, originalTextureCase.getSingleLowerOfTag("TEXTURE_CASE"));
								Epifyte.forceMove(textureCase
										, textureCase.getSingleLowerOfTag("TEXTURE_CASE")
										, (EpifyteModifier) cellPresumed.evaluateInstanceInformation("epifyteSeek textureCase").getEntry());
								Epifyte.forceDetach(originalPiece, cellPresumed);
								Epifyte.forceDetach(originalPiece.getSingleUpperOfTag("CONTROLLER_PROXY"),
										originalPiece);
								RoundController originalPieceRoundController 
								= originalPiece.getParentControllers().get(0);
								originalPieceRoundController.getRoundControlTargets().remove(originalPiece);
								Epifyte.forceMove(commandRoute.get(0), currentCell, cellPresumed);
								
								textureCase.executeCommand("update");
								originalTextureCase.executeCommand("update");
								EpifyteModifier controllerProxy = (EpifyteModifier) commandRoute.get(0).
										evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry();
								Integer movesLeft = (Integer) 
										controllerProxy.getDataSet("movesLeft").getEntry();
								movesLeft--;
								controllerProxy.setDataSet("movesLeft", new DataSet(Integer.class, movesLeft));
								}
							}
						}
					}
					
				});
		if(pieceType == "Rook"){
			EpifyteModifier pieceDetector = new EpifyteModifier();
			//Modification to obstructionDetector
			pieceDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("attackRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							ArrayList<Epifyte> newDataSetArrayList
							= new ArrayList<Epifyte>();
						//Left piece detection
						for(int i = currentX - 1; i >= 1; i--){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i, currentY)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i, currentY))
									.getSingleUpperOfTag("CHESS_PIECE") != null){
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(i, currentY)));
								break;
							}
						}
						//Right piece detection
						for(int i = currentX + 1; true; i++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(i, currentY)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i, currentY))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								newDataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(i, currentY)));
								break;
							}
						}
						//Down piece detection
						for(int j = currentY - 1; j >= 1; j--){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX, j)));
								break;
							}
						}
						//Up piece detection
						for(int j = currentY + 1; true; j++){//Check if this line is correct
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX, j)) == null){
								break;
							}
							if(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j))
									.getSingleUpperOfTag("CHESS_PIECE") != null
									){
								newDataSetArrayList.add(coordinatesToCellMapRaw
									.get(new Pair<Integer, Integer>(currentX, j)));
								break;
							}
						}
						dataSet.setEntry(newDataSetArrayList);
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier enemyIdentifier = new EpifyteModifier();
			enemyIdentifier.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
					if(informationName.equals("attackRange")){
						ArrayList<Epifyte> cellList = (ArrayList<Epifyte>) propagatedDataSet.getEntry();
						EpifyteModifier controllerProxy = (EpifyteModifier) informationRoute.get(0)
								.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry(); 
						Iterator<Epifyte> iterator = cellList.iterator();
						while(iterator.hasNext()){
							Epifyte cell = iterator.next();
							if(controllerProxy.getLower().contains(cell.getSingleUpperOfTag("CHESS_PIECE"))){
								iterator.remove();
							}
							
						}
						return new DataSet(ArrayList.class, cellList);
					}
					return propagatedDataSet;
				});
			Epifyte.forceBind(enemyIdentifier, pieceDetector);
			Epifyte.forceBind(pieceDetector, chessAttackArm);
			}
			else if(pieceType == "Bishop"){
				EpifyteModifier pieceDetector = new EpifyteModifier();
				//Modification to obstructionDetector
				pieceDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
						, informationRoute, supportDataSet) -> {
							if(informationName.equals("attackRange")){
								//This should return a dataset containing an arraylist of cells
								//The propagated dataset should also be an arraylist of cells
								Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
								informationQuintuple = helper1(informationName, propagatedDataSet
										, informationRoute, supportDataSet);
								if(informationQuintuple == null){
									return propagatedDataSet;
								}
								Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
								= informationQuintuple.getFirst();
								int currentX = informationQuintuple.getSecond();
								int currentY = informationQuintuple.getThird();
								ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
								DataSet dataSet = informationQuintuple.getFifth();
								ArrayList<Epifyte> newDataSetArrayList
								= new ArrayList<Epifyte>();
							//Up-Left piece detection
							for(int i = 1; true; i++){//Check if this line is correct
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX - i, currentY + i)) == null){
									break;
								}
								if(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i, currentY + i))
										.getSingleUpperOfTag("CHESS_PIECE") != null){
									newDataSetArrayList.add(coordinatesToCellMapRaw
											.get(new Pair<Integer, Integer>(currentX - i, currentY + i)));
									break;
								}
							}
							//Up-Right piece detection
							for(int i = 1; true; i++){//Check if this line is correct
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + i, currentY + i)) == null){
									break;
								}
								if(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i, currentY + i))
										.getSingleUpperOfTag("CHESS_PIECE") != null){
									newDataSetArrayList.add(coordinatesToCellMapRaw
											.get(new Pair<Integer, Integer>(currentX + i, currentY + i)));
									break;
								}
							}
							//Down-Left piece detection
							for(int i = 1; true; i++){//Check if this line is correct
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX - i, currentY - i)) == null){
									break;
								}
								if(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - i, currentY - i))
										.getSingleUpperOfTag("CHESS_PIECE") != null){
									newDataSetArrayList.add(coordinatesToCellMapRaw
											.get(new Pair<Integer, Integer>(currentX - i, currentY - i)));
									break;
								}
							}
							//Down-Right piece detection
							for(int i = 1; true; i++){//Check if this line is correct
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + i, currentY - i)) == null){
									break;
								}
								if(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + i, currentY - i))
										.getSingleUpperOfTag("CHESS_PIECE") != null){
									newDataSetArrayList.add(coordinatesToCellMapRaw
											.get(new Pair<Integer, Integer>(currentX + i, currentY - i)));
									break;
								}
							}
							dataSet.setEntry(newDataSetArrayList);
							return dataSet;
							}
							return propagatedDataSet;
						});
				EpifyteModifier enemyIdentifier = new EpifyteModifier();
				enemyIdentifier.setEpifyteInformationFinder((informationName, propagatedDataSet
						, informationRoute, supportDataSet) -> {
						if(informationName.equals("attackRange")){
							ArrayList<Epifyte> cellList = (ArrayList<Epifyte>) propagatedDataSet.getEntry();
							EpifyteModifier controllerProxy = (EpifyteModifier) informationRoute.get(0)
									.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry(); 
							Iterator<Epifyte> iterator = cellList.iterator();
							while(iterator.hasNext()){
								Epifyte cell = iterator.next();
								if(controllerProxy.getLower().contains(cell.getSingleUpperOfTag("CHESS_PIECE"))){
									iterator.remove();
								}
								
							}
							return new DataSet(ArrayList.class, cellList);
						}
						return propagatedDataSet;
					});
				Epifyte.forceBind(enemyIdentifier, pieceDetector);
				Epifyte.forceBind(pieceDetector, chessAttackArm);
			
		}
		if(pieceType == "Knight"){
			EpifyteModifier pieceDetector = new EpifyteModifier();
			//Modification to obstructionDetector
			pieceDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("attackRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							ArrayList<Epifyte> newDataSetArrayList
							= new ArrayList<Epifyte>();
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 2, currentY - 1)) != null){
								if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 2, currentY - 1))
										.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - 2, currentY - 1)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 2, currentY + 1)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX - 2, currentY + 1))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - 2, currentY + 1)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 1, currentY - 2)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX - 1, currentY - 2))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - 1, currentY - 2)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 1, currentY + 2)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX - 1, currentY + 2))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - 1, currentY + 2)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + 1, currentY - 2)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + 1, currentY - 2))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + 1, currentY - 2)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + 1, currentY + 2)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + 1, currentY + 2))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + 1, currentY + 2)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + 2, currentY - 1)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + 2, currentY - 1))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + 2, currentY - 1)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + 2, currentY + 1)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + 2, currentY + 1))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + 2, currentY + 1)));
								}
							}
						dataSet.setEntry(newDataSetArrayList);
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier enemyIdentifier = new EpifyteModifier();
			enemyIdentifier.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
					if(informationName.equals("attackRange")){
						ArrayList<Epifyte> cellList = (ArrayList<Epifyte>) propagatedDataSet.getEntry();
						EpifyteModifier controllerProxy = (EpifyteModifier) informationRoute.get(0)
								.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry(); 
						Iterator<Epifyte> iterator = cellList.iterator();
						while(iterator.hasNext()){
							Epifyte cell = iterator.next();
							if(controllerProxy.getLower().contains(cell.getSingleUpperOfTag("CHESS_PIECE"))){
								iterator.remove();
							}
							
						}
						return new DataSet(ArrayList.class, cellList);
					}
					return propagatedDataSet;
				});
			Epifyte.forceBind(enemyIdentifier, pieceDetector);
			Epifyte.forceBind(pieceDetector, chessAttackArm);
			}
		if(pieceType == "Pawn"){
			EpifyteModifier pieceDetector = new EpifyteModifier();
			//Modification to obstructionDetector
			pieceDetector.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
						if(informationName.equals("attackRange")){
							//This should return a dataset containing an arraylist of cells
							//The propagated dataset should also be an arraylist of cells
							Quintuple<Map<Pair<Integer, Integer>, Epifyte>, Integer, Integer, ArrayList<Epifyte>, DataSet>
							informationQuintuple = helper1(informationName, propagatedDataSet
									, informationRoute, supportDataSet);
							if(informationQuintuple == null){
								return propagatedDataSet;
							}
							Map<Pair<Integer, Integer>, Epifyte> coordinatesToCellMapRaw
							= informationQuintuple.getFirst();
							int currentX = informationQuintuple.getSecond();
							int currentY = informationQuintuple.getThird();
							ArrayList<Epifyte> dataSetArrayList = informationQuintuple.getFourth();
							DataSet dataSet = informationQuintuple.getFifth();
							ArrayList<Epifyte> newDataSetArrayList
							= new ArrayList<Epifyte>();
							Epifyte controllerProxy = (Epifyte) informationRoute.get(0)
									.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry();
							Integer frontDirection = (Integer) 
									controllerProxy.getDataSet("frontDirection").getEntry();
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 1, currentY + frontDirection)) != null){
								if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX - 1, currentY + frontDirection))
										.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX - 1, currentY + frontDirection)));
								}
							}
							if(coordinatesToCellMapRaw.get(
									new Pair<Integer, Integer>(currentX + 1, currentY + frontDirection)) != null){
								if(coordinatesToCellMapRaw.get(
										new Pair<Integer, Integer>(currentX + 1, currentY + frontDirection))
											.getSingleUpperOfTag("CHESS_PIECE") != null) {
								newDataSetArrayList.add(coordinatesToCellMapRaw
										.get(new Pair<Integer, Integer>(currentX + 1, currentY + frontDirection)));
								}
							}
						dataSet.setEntry(newDataSetArrayList);
						return dataSet;
						}
						return propagatedDataSet;
					});
			EpifyteModifier enemyIdentifier = new EpifyteModifier();
			enemyIdentifier.setEpifyteInformationFinder((informationName, propagatedDataSet
					, informationRoute, supportDataSet) -> {
					if(informationName.equals("attackRange")){
						ArrayList<Epifyte> cellList = (ArrayList<Epifyte>) propagatedDataSet.getEntry();
						EpifyteModifier controllerProxy = (EpifyteModifier) informationRoute.get(0)
								.evaluateInstanceInformation("epifyteSeek controllerProxy").getEntry(); 
						Iterator<Epifyte> iterator = cellList.iterator();
						while(iterator.hasNext()){
							Epifyte cell = iterator.next();
							if(controllerProxy.getLower().contains(cell.getSingleUpperOfTag("CHESS_PIECE"))){
								iterator.remove();
							}
							
						}
						return new DataSet(ArrayList.class, cellList);
					}
					return propagatedDataSet;
				});
			Epifyte.forceBind(enemyIdentifier, pieceDetector);
			Epifyte.forceBind(pieceDetector, chessAttackArm);
			}
		//TODO
		return chessAttackArm;
	}
	public static void executeProcess(String processName){
		//To be rewritten
	}
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}

	public static EpifyteModifier getWhiteControllerProxy() {
		return whiteControllerProxy;
	}

	public static void setWhiteControllerProxy(EpifyteModifier whiteControllerProxy) {
		InitializerDefault.whiteControllerProxy = whiteControllerProxy;
	}

	public static EpifyteModifier getBlackControllerProxy() {
		return blackControllerProxy;
	}

	public static void setBlackControllerProxy(EpifyteModifier blackControllerProxy) {
		InitializerDefault.blackControllerProxy = blackControllerProxy;
	}
	
	public static void setFairyChessApplication(FairyChessApplication fairyChessApplication){
		InitializerDefault.fairyChessApplication = fairyChessApplication;
	}
	
	public static FairyChessApplication getFairyChessApplication(){
		return fairyChessApplication;
	}

	public static Epifyte getGameBoard() {
		return gameBoard;
	}

	public static void setGameBoard(Epifyte gameBoard) {
		InitializerDefault.gameBoard = gameBoard;
	}

	public static RoundController getEpifyteMasterController() {
		return epifyteMasterController;
	}

	public static void setEpifyteMasterController(RoundController epifyteMasterController) {
		InitializerDefault.epifyteMasterController = epifyteMasterController;
	}

}
