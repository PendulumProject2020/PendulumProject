package fairyChessPack1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import chronosPack.Agent;
import main.DataSet;
import main.MethodPiece;
import main.PieceTreeCompiler;
import main.SlotPath;
import main.World;

public class ControllerProxyFactory extends MethodPiece{
	
	public static EpifyteModifier makeControllerProxy(String controllerName){
		EpifyteModifier controllerProxy = new EpifyteModifier();
		controllerProxy.setLabel(controllerName);
		controllerProxy.setTags("CONTROLLER_PROXY", "HUMAN_CONTROLLER");
		controllerProxy.setRoundExecutor(() -> {
			controllerProxy.setDataSet("movesLeft", new DataSet(Integer.class, 1));
			EpifyteDisplayEventInterpreter.setSelectedEpifytes(new ArrayList<Epifyte>());
			//The round has just begun, nothing has been selected
			EpifyteDisplayEventInterpreter.setActiveAgents(
					new ArrayList<Agent>(Arrays.asList(controllerProxy)));
			//The active agents are the controllerProxies
			World.setUserInteraction(true);
			System.out.println("controllerProxy round executed");
		});
		controllerProxy.setEpifyteInformationFinder((informationName, propagatedDataSet
				, informationRoute, supportDataSet) -> {
		if(informationName.equals("controllerProxy")){
			return new DataSet(EpifyteModifier.class, controllerProxy);
		}
		return propagatedDataSet;
				});
		controllerProxy.setDataSet("movesLeft", new DataSet(Integer.class, 1));
		return controllerProxy;
		//Check if the codes here are complete
	}
	
	//What slots it can fit in
			public static ArrayList<SlotPath> getPossibleFits(){//Basically an override-able static final
				ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos", "Agent", "Epifyte"));
				String slotName = "CONTROLLERPROXYFACTORYSLOT";
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
