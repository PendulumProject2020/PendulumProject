package chronosPack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.DataSet;
import main.ObjectPiece;
import main.Piece;
import main.PieceTreeCompiler;
import main.SlotPath;

public class RoundSignallerContinuous extends ObjectPiece{
	
	private static boolean roundSignalOngoing = false;
	
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
				PieceTreeCompiler.getPieceClassToProjectionMap().get(RoundSignallerContinuous.class)
				.getHostPiece().getOrigin().getMethod("signalAll").invoke(null);
			} catch (NoSuchMethodException | SecurityException 
					| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void proceedToNextRound(){
		System.out.println("RoundSignallerContinuous: proceedToNextRound begins.");
		if(roundSignalOngoing){
		try {
			PieceTreeCompiler.getPieceClassToProjectionMap().get(RoundSignallerContinuous.class)
			.getHostPiece().getOrigin().getMethod("signalAll").invoke(null);
		} catch (NoSuchMethodException | SecurityException 
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
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
		RoundSignallerContinuous.roundSignalOngoing = roundSignalOngoing;
	}
}
