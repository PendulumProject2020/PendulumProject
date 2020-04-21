package chronosPack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import main.DataSet;
import main.ObjectPiece;
import main.Piece;
import main.PieceTreeCompiler;
import main.SlotPath;

public class RoundSignallerTimed extends ObjectPiece{
	
	private static boolean roundSignalOngoing = false;
	private static Integer roundInterval = 1000;
	
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos"));
		String slotName = "ROUNDSIGNALLERSLOT";
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
	public static void beginRoundSignalLoop(){
		roundSignalOngoing = true;
			try {
				PieceTreeCompiler.getPieceClassToProjectionMap().get(RoundSignallerTimed.class)
				.getHostPiece().getOrigin().getMethod("signalAll").invoke(null);
			} catch (NoSuchMethodException | SecurityException 
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void proceedToNextRound(){
		System.out.println("RoundSignallerTimed: proceedToNextRound begins.");
		if(roundSignalOngoing){
			Timer timer = new Timer();
		    TimerTask task = new TimerTask() {
		        @Override
		        public void run() {
		            Platform.runLater(() -> {
		            	try {
			    			PieceTreeCompiler.getPieceClassToProjectionMap().get(RoundSignallerTimed.class)
			    			.getHostPiece().getOrigin().getMethod("signalAll").invoke(null);
			    		} catch (NoSuchMethodException | SecurityException 
			    				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			    			// TODO Auto-generated catch block
			    			e.printStackTrace();
			    			System.exit(0);
			    		}
		            });
		        }
		    };
		    timer.schedule(task, roundInterval);
		}
	}
	public static void executeProcess(String processName){
		//To be rewritten
		if(processName.equals("nextRound") || processName.equals("proceedToNextRound")
				|| processName.equals("endRound")){
			if(roundSignalOngoing) proceedToNextRound();
		}
	}
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}

	public static boolean isRoundSignalOngoing() {
		return roundSignalOngoing;
	}

	public static void setRoundSignalOngoing(boolean roundSignalOngoing) {
		RoundSignallerTimed.roundSignalOngoing = roundSignalOngoing;
	}

	public static Integer getRoundInterval() {
		return roundInterval;
	}

	public static void setRoundInterval(Integer roundInterval) {
		RoundSignallerTimed.roundInterval = roundInterval;
	}
}