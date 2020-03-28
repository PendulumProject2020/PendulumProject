package fairyChessPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.DataSet;
import main.MethodPiece;
import main.Piece;
import main.PieceTreeCompiler;
import main.SlotPath;
import main.Trilean;

public class BindingValidityEvaluator extends MethodPiece{
	public static String getVersionName(){
		return "0.0.5 Official Pre-release";
	}
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos", "Agent", "Epifyte"));
		String slotName = "BINDINGVALIDITYEVALUATORSLOT";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(Arrays.asList(new SlotPath(hostPath, slotName)));
		return possibleFits;
	}
	public static boolean evaluateTrilean(Trilean binderAcceptance, Trilean targetAcceptance){//Helper method
		if(binderAcceptance == Trilean.TRUE && targetAcceptance == Trilean.TRUE){
			return true;
		}
		else if(binderAcceptance == Trilean.TRUE && targetAcceptance == Trilean.UNDECIDED){
			return true;
		}
		else if(binderAcceptance == Trilean.UNDECIDED && targetAcceptance == Trilean.TRUE){
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
