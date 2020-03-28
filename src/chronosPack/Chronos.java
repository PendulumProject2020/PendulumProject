package chronosPack;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import main.DataSet;
import main.MethodPiece;
import main.Piece;
import main.PieceTreeCompiler;
import main.SlotPath;
import main.World;

public class Chronos extends MethodPiece{

	private static ArrayList<RoundController> roundControllers = new ArrayList<RoundController>();
	private static LinkedList<RoundController> roundControllerQueue = new LinkedList<RoundController>();
	public static String getVersionName(){
		return "0.0.5 Official Pre-release";
	}
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(Arrays.asList(new SlotPath()));
		return possibleFits;
	}
	
	public static Map<String, Integer> getMethodPieceSlots(){
		return Piece.makeSimpleSlotList("INITIALIZERSLOT");//TODO
	}
	public static Map<String, Integer> getObjectPieceSlots(){
		return Piece.makeSimpleSlotList("ROUNDSIGNALLERSLOT", "AGENTSLOT");//TODO
	}
	public static ArrayList<String> getDependentPieceNames(){
		return new ArrayList<String>();//No dependency because it is a root piece
	}
	public static void run(){
		Class<Piece> initializerClass = PieceTreeCompiler
				.getSingleMethodSlotOccupant(Chronos.class, "INITIALIZERSLOT");
		Method initialize;
		try {
			initialize = initializerClass.getMethod("initialize");
			initialize.invoke(null);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException |
				NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void addRoundController(RoundController roundController){
		//Not sure if this works
		Chronos.getRoundControllers().add(roundController);
	}
	public static void removeRoundController(RoundController roundController){
		//Not sure if this works
		Chronos.getRoundControllers().remove(roundController);
	}
	public static ArrayList<RoundController> getRoundControllers(){
		//Not sure if this works
		return Chronos.roundControllers;
	}
	public static void setRoundControllers(ArrayList<RoundController> roundControllers){
		Chronos.roundControllers = roundControllers;
	}
	
	public static void signalAll(){
		System.out.println("Chronos: signalAll begins.");
		roundControllerQueue = new LinkedList<RoundController>();
		for(RoundController roundController : roundControllers){
			roundControllerQueue.add(roundController);
			System.out.println("Chronos: Added a roundController to the Queue.");
		}
		proceedToNextController();
	}
	public static void proceedToNextController(){
		System.out.println("Chronos: proceedToNextController begins.");
		System.out.println("Chronos: size of roundControllerQueue: " 
		+ Integer.toString(roundControllerQueue.size()));
		World.setUserInteraction(false);
			if(roundControllerQueue.isEmpty() == true){
				System.out.println("Chronos: roundControllerQueue is empty.");
				Class<Piece> roundSignallerClass 
				= PieceTreeCompiler.getSingleObjectSlotOccupant(Chronos.class, "ROUNDSIGNALLERSLOT");
				Method proceedToNextRound;
				try {
					proceedToNextRound = roundSignallerClass.getMethod("proceedToNextRound");
					proceedToNextRound.invoke(null);
				} catch (IllegalAccessException | IllegalArgumentException 
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.exit(0);
				}
			}
			else{
				Agent element = roundControllerQueue.element();
				System.out.println("Chronos: removing an element from roundControllerQueue.");
				roundControllerQueue.remove();
				System.out.println("Chronos: an element has been removed from roundControllerQueue.");
				System.out.println("Chronos: size of roundControllerQueue: " 
						+ Integer.toString(roundControllerQueue.size()));
				element.executeRound();
			}
	}
	public static void executeProcess(String processName){
		//To be rewritten
	}
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}
	public static LinkedList<RoundController> getRoundControllerQueue() {
		return roundControllerQueue;
	}
	public static void setRoundControllerQueue(LinkedList<RoundController> roundControllerQueue) {
		Chronos.roundControllerQueue = roundControllerQueue;
	}
	
}
