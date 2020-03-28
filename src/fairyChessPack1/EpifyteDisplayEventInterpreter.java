package fairyChessPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import chronosPack.Agent;
import javafx.event.Event;
import main.DataSet;
import main.MethodPiece;
import main.Pair;
import main.SlotPath;

public class EpifyteDisplayEventInterpreter extends MethodPiece{
	private static Map<Epifyte, InputCommandConverter> interpreter 
	= new HashMap<Epifyte, InputCommandConverter>();
	private static ArrayList<Agent> activeAgents = new ArrayList<Agent>();
	private static ArrayList<Epifyte> selectedEpifytes = new ArrayList<Epifyte>();
	public static void addConverter(Epifyte source, InputCommandConverter inputCommandConverter){
		interpreter.put(source, inputCommandConverter);
	}

	public static Map<Epifyte, InputCommandConverter> getInterpreter() {
		return interpreter;
	}
	
	public static void executeConverter(Epifyte source, Event event, DataSet supportDataSet){
		interpreter.get(source).convertInputToCommand(event, supportDataSet);
	}

	public static void setInterpreter(Map<Epifyte, InputCommandConverter> interpreter) {
		EpifyteDisplayEventInterpreter.interpreter = interpreter;
	}
	

	public static ArrayList<Epifyte> getSelectedEpifytes() {
		return selectedEpifytes;
	}

	public static void setSelectedEpifytes(ArrayList<Epifyte> selectedEpifytes) {
		EpifyteDisplayEventInterpreter.selectedEpifytes = selectedEpifytes;
	}
	
	public static void addSelectedEpifyte(Epifyte selectedEpifyte){
		getSelectedEpifytes().add(selectedEpifyte);
	}

	public static ArrayList<Agent> getActiveAgents() {
		return activeAgents;
	}

	public static void setActiveAgents(ArrayList<Agent> activeAgents) {
		EpifyteDisplayEventInterpreter.activeAgents = activeAgents;
	}
	
	//What slots it can fit in
		public static ArrayList<SlotPath> getPossibleFits(){//Basically an override-able static final
			ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos", "Agent", "Epifyte"));
			String slotName = "EVENTINTERPRETERSLOT";
			ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(Arrays.asList(new SlotPath(hostPath, slotName)));
			return possibleFits;
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


	
}
