package chronosPack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import main.DataSet;
import main.ObjectPiece;
import main.Piece;
import main.SlotPath;

public class Agent extends ObjectPiece{
	
	private RoundExecutor roundExecutor;
	private ArrayList<RoundController> parentControllers = new ArrayList<RoundController>();
	public Agent(){
		super();
		this.setActualClass(Agent.class);
		this.setDefaultCommandExecutor();
		this.setDefaultInstanceInformationEvaluator();
		this.setDefaultRoundExecutor();
	}
	
	public static Agent makeClone(Agent agent){
		Agent clone = (Agent) ObjectPiece.makeClone(agent);
		clone.setRoundExecutor(agent.getRoundExecutor());
		ArrayList<RoundController> cloneParentControllers = 
				new ArrayList<RoundController>(agent.getParentControllers());
		clone.setParentControllers(cloneParentControllers);
		return clone;
	}
	
	public static Agent makeDetachedClone(Agent agent) {
		Agent clone = (Agent) ObjectPiece.makeClone(agent);
		return clone;
	}
	
	public static String getVersionName(){
		return "0.0.5 Official Pre-release";
	}
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos"));
		String slotName = "AGENTSLOT";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(Arrays.asList(new SlotPath(hostPath, slotName)));
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
	
	@Override
	public void setDefaultCommandExecutor(){
		//To be overridden
		this.setCommandExecutor((target, command, dataSet) -> {
			if(command.equals("executeRound")){
				if(target.getActualClass() == Agent.class){
					try {
						Agent.class.getMethod("executeRound").invoke(target);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						System.exit(0);
					}
				}
			}
		});
	}
	
	@Override
	public void setDefaultInstanceInformationEvaluator(){
		//To be overridden
		this.setInstanceInformationEvaluator((target, command, dataSet) -> {
			return null;
		});
	}
	
	public void setDefaultRoundExecutor(){
		//To be overridden
		this.setRoundExecutor(() -> {
			for(RoundController parentController : this.getParentControllers()){
				parentController.proceedToNextTarget();
			}
		});
	}
	
	public void executeRound(){
		this.getRoundExecutor().interfaceExecuteRound();
	}

	public RoundExecutor getRoundExecutor() {
		return roundExecutor;
	}

	public void setRoundExecutor(RoundExecutor roundExecutor) {
		this.roundExecutor = roundExecutor;
	}
	
	public void addParentController(RoundController parentController){
		this.parentControllers.add(parentController);
	}

	public ArrayList<RoundController> getParentControllers() {
		return parentControllers;
	}

	public void setParentControllers(ArrayList<RoundController> parentControllers) {
		this.parentControllers = parentControllers;
	}
}
