package fairyChessPack1;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import chronosPack.Agent;
import chronosPack.RoundController;
import main.DataSet;
import main.Piece;
import main.SlotPath;
import main.Trilean;

public class EpifyteModifier extends Epifyte{
	//If the same kind of data can be modified by multiple modifiers on the same layer of binding,
	//which one actually acts on the data will simply depend on their position in the Upper ArrayList of
	//the Epifyte they bind to.
	private EpifyteInformationFinder epifyteInformationFinder;
	public EpifyteModifier(){
		super();
		this.setActualClass(EpifyteModifier.class);
		this.setDefaultCommandExecutor();
		this.setDefaultInstanceInformationEvaluator();
		this.setDefaultRoundExecutor();
		this.setDefaultBindingRequesterJudge();
		this.setDefaultBindingRespondentJudge();
		this.setDefaultDetachmentRequesterJudge();
		this.setDefaultDetachmentRespondentJudge();
		this.setDefaultCommandPropagationPermissionJudge();
		this.setDefaultInformationPropagationPermissionJudge();
		this.setDefaultEpifyteInformationFinder();
	}
	
	public static EpifyteModifier makeClone(EpifyteModifier epifyteModifier){
		EpifyteModifier clone = (EpifyteModifier) Epifyte.makeClone(epifyteModifier);
		clone.setEpifyteInformationFinder(epifyteModifier.getEpifyteInformationFinder());
		return clone;
	}
	
	public static String getVersionName(){
		return "0.0.5 Official Pre-release";
	}
	public static ArrayList<SlotPath> getPossibleFits(){
		ArrayList<String> hostPath = new ArrayList<String>(Arrays.asList("Chronos", "Agent"));
		String slotName = "_OBJECTMEGASLOT";
		ArrayList<SlotPath> possibleFits = new ArrayList<SlotPath>(
				Arrays.asList(new SlotPath(hostPath, slotName)));
		return possibleFits;
	}
	
	public Trilean canBindTo(Epifyte epifyte){//To be overridden
		//Whether this can go on top of the given epifyte
		return Trilean.UNDECIDED;
	}
	
	public Trilean canBeBoundBy(Epifyte epifyte){//To be overridden
		//Whether the given epifyte can go on top of this
		return Trilean.UNDECIDED;
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
					}
				}
			}
			else{
				String[] commandArray = command.split(" ");
				if(commandArray[0].equals("epifyteDo")){
					for(EpifyteModifier epifyteModifier : getUpperModifiers()){
						epifyteModifier.propagateCommand(
								commandArray[1], new ArrayList<Epifyte>(Arrays.asList(this)), dataSet);
					}
				}
			}
		});
	}
	
	@Override
	public void setDefaultInstanceInformationEvaluator(){
		//To be overridden
		this.setInstanceInformationEvaluator((target, informationName, dataSet) -> {
			String[] informationNameArray = informationName.split(" ");
			if(informationNameArray[0].equals("epifyteSeek")){
				DataSet propagatedDataSet = null;
				for(EpifyteModifier epifyteModifier : getUpperModifiers()){
					propagatedDataSet = epifyteModifier.propagateInformation(
							informationNameArray[1], propagatedDataSet,
							new ArrayList<Epifyte>(Arrays.asList(this)), dataSet);
				}
				return propagatedDataSet;
			}
			return null;
		});
	}
	
	@Override
	public void setDefaultRoundExecutor(){
		//To be overridden
		this.setRoundExecutor(() -> {
			for(RoundController parentController : this.getParentControllers()){
				parentController.proceedToNextTarget();
			}
		});
	}

	public void setDefaultEpifyteInformationFinder(){//To be overridden
		this.setEpifyteInformationFinder((informationName, propagatedDataSet, 
				informationRoute, supportingDataSet) 
				-> {
					
					//Doesn't return any information yet
					return propagatedDataSet;
				});
	}
	
	public DataSet propagateInformation(String informationName, DataSet propagatedDataSet, 
			ArrayList<Epifyte> informationRoute, DataSet supportDataSet){
		System.out.println("EpifyteModifier: propagateInformation");
		informationRoute.add(this);
		for(Epifyte routeCheckPoint : informationRoute){
			if(routeCheckPoint.getInformationPropagationPermissionJudge()
					.canInformationPropagationContinue(informationName, propagatedDataSet, 
							informationRoute, supportDataSet) == false){
				return propagatedDataSet;//Propagation stops here
			}
		}
		DataSet modifiedDataSet = this.getEpifyteInformationFinder()
				.findInformation(informationName, propagatedDataSet, 
				informationRoute, supportDataSet);
		for(EpifyteModifier modifier : this.getUpperModifiers()){
			modifiedDataSet = modifier.propagateInformation(
					informationName, modifiedDataSet, informationRoute, supportDataSet);
		}
		return modifiedDataSet;
	}
	
	public void propagateCommand(String command,
			ArrayList<Epifyte> commandRoute, DataSet supportDataSet){
		commandRoute.add(this);
		for(Epifyte routeCheckPoint : commandRoute){
			if(routeCheckPoint.getCommandPropagationPermissionJudge()
					.canCommandPropagationContinue(command, 
							commandRoute, supportDataSet) == false){
				return;//Propagation stops here
			}
		}
		if(EpifyteArm.class.isAssignableFrom(this.getActualClass())){
			System.out.println("Arm found.");
			EpifyteArm thisArm = (EpifyteArm) this;
			thisArm.getEpifyteCommandHandler().handle(command, commandRoute, supportDataSet);
		}
		for(EpifyteModifier modifier : this.getUpperModifiers()){
			modifier.propagateCommand(command, commandRoute, supportDataSet);
		}
	}
	

	public EpifyteInformationFinder getEpifyteInformationFinder() {
		return epifyteInformationFinder;
	}

	public void setEpifyteInformationFinder(EpifyteInformationFinder epifyteInformationFinder) {
		this.epifyteInformationFinder = epifyteInformationFinder;
	}

}
