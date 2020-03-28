package testPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.DataSet;
import main.ObjectPiece;
import main.Piece;
import main.SlotPath;

public class TestO1 extends ObjectPiece{
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("TestM1"));
		String slotName = "TESTM1OS1";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(
				Arrays.asList(new SlotPath(hostPath, slotName)));
		return possibleFits;
	}
	
	public static Map<String, Integer> getMethodPieceSlots(){
		return Piece.makeSimpleSlotList("TESTO1MS1");//TODO
	}
	public static Map<String, Integer> getObjectPieceSlots(){
		return Piece.makeSimpleSlotList("TESTO1OS1");//TODO
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
