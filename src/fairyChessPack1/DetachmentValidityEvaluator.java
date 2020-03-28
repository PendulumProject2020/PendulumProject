package fairyChessPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.DataSet;
import main.MethodPiece;
import main.Piece;
import main.SlotPath;
import main.Trilean;

public class DetachmentValidityEvaluator extends MethodPiece{
	public static String getVersionName(){
		return "0.0.5 Official Pre-release";
	}
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos", "Agent", "Epifyte"));
		String slotName = "DETACHMENTVALIDITYEVALUATORSLOT";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(Arrays.asList(new SlotPath(hostPath, slotName)));
		return possibleFits;
	}
	public static boolean evaluateTrilean(Trilean detacherAcceptance, Trilean hostAcceptance){//Helper method
		if(detacherAcceptance == Trilean.TRUE && hostAcceptance == Trilean.TRUE){
			return true;
		}
		else if(detacherAcceptance == Trilean.TRUE && hostAcceptance == Trilean.UNDECIDED){
			return true;
		}
		else if(detacherAcceptance == Trilean.UNDECIDED && hostAcceptance == Trilean.TRUE){
			return true;
		}
		else{
			return false;
		}
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
	public static void executeProcess(String processName){
		//To be rewritten
	}
	
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}
}
