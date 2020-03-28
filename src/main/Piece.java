package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Piece {
	//When making actual pieces, extend to ObjectPiece or MethodPiece, not directly to here
	//Note that most or all static methods of the pieces should be invoked using a reflection on the specific Class object.
	
	//What slots it can fit in
	public static ArrayList<SlotPath> getPossibleFits(){//Basically an override-able static final
		return new ArrayList<SlotPath>();//Rewrite this when you make actual pieces
		//Use full slot names
	}
	//What method slots it has
	public static Map<String, Integer> getMethodPieceSlots(){//Basically an override-able static final
		return new HashMap<String, Integer>();//Rewrite this when you make actual pieces
		//Use simple slot names
	}
	//What object slots it has
	public static Map<String, Integer> getObjectPieceSlots(){//Basically an override-able static final
		return new HashMap<String, Integer>();//Rewrite this when you make actual pieces
		//Use simple slot names
	}
	//It needs to have these pieces in order to be activated.
	public static ArrayList<String> getDependentPieceNames(){//Basically an override-able static final
		return new ArrayList<String>();//Rewrite this when you make actual pieces
	}
	public static String getVersionName(){//Basically an override-able static final
		return "";//Rewrite this when you make actual pieces
	}
	public static void executeProcess(String processName){
		//To be rewritten
	}
	public static void executeProcessWithDataSet(String processName, DataSet dataSet){
		//To be rewritten
	}
	
	public static DataSet evaluateStaticInformation(String informationName){
		return null;
		//To be rewritten
	}
	public static DataSet evaluateStaticInformationWithDataSet(String informationName, DataSet dataSet){
		return null;
		//To be rewritten
	}

	//Helper method
	public static Map<String, Integer> makeSimpleSlotList(String... slotNames){
		Map<String, Integer> slotList = new HashMap<String, Integer>();
		for(String slotName : slotNames){
			slotList.put(slotName, 1);
		}
		return slotList;
	}
}
