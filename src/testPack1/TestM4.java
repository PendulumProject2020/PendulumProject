package testPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import main.DataSet;
import main.MethodPiece;
import main.Piece;
import main.SlotPath;

public class TestM4 extends MethodPiece{
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("TestM1", "TestO1"));
		String slotName = "TESTO1MS1";
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
	public static void executeProcess(String processName){
		//To be rewritten
	}
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}
}
