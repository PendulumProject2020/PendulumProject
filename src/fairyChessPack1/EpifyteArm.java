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

public class EpifyteArm extends EpifyteModifier{
	
	private EpifyteCommandHandler epifyteCommandHandler;
	public EpifyteArm(){
		super();
		this.setActualClass(EpifyteArm.class);
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

	@Override
	public void setDefaultEpifyteInformationFinder(){//To be overridden
		this.setEpifyteInformationFinder((informationName, propagatedDataSet, 
				informationRoute, supportingDataSet) 
				-> {
					
					//Doesn't return any information yet
					return propagatedDataSet;
				});
	}
	
	public void setDefaultEpifyteCommandHandler(){//To be overridden
		this.setEpifyteCommandHandler((command, 
				commandRoute, supportingDataSet) 
				-> {
					
					//Do nothing
				});
	}

	public EpifyteCommandHandler getEpifyteCommandHandler() {
		return epifyteCommandHandler;
	}

	public void setEpifyteCommandHandler(EpifyteCommandHandler epifyteCommandHandler) {
		this.epifyteCommandHandler = epifyteCommandHandler;
	}
}
