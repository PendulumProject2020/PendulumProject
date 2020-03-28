package fairyChessPack1;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

import chronosPack.Agent;
import chronosPack.RoundController;
import main.*;

public class Epifyte extends Agent{
	
	private String label = "";
	private ArrayList<String> tags = new ArrayList<String>();
	private ArrayList<Epifyte> upper = new ArrayList<Epifyte>();
	private ArrayList<EpifyteModifier> upperModifiers = new ArrayList<EpifyteModifier>();
	private ArrayList<EpifyteArm> upperArms = new ArrayList<EpifyteArm>();
	private ArrayList<Epifyte> lower = new ArrayList<Epifyte>();
	private BindingRequesterJudge bindingRequesterJudge;
	private BindingRespondentJudge bindingRespondentJudge;
	private DetachmentRequesterJudge detachmentRequesterJudge;
	private DetachmentRespondentJudge detachmentRespondentJudge;
	private CommandPropagationPermissionJudge commandPropagationPermissionJudge;
	private InformationPropagationPermissionJudge informationPropagationPermissionJudge;
	private static Map<String, EpifytePrototype> epifyteCreationProtocol;
	
	public Epifyte(){
		super();
		this.setActualClass(Epifyte.class);
		this.setDefaultCommandExecutor();
		this.setDefaultInstanceInformationEvaluator();
		this.setDefaultRoundExecutor();
		this.setDefaultBindingRequesterJudge();
		this.setDefaultBindingRespondentJudge();
		this.setDefaultDetachmentRequesterJudge();
		this.setDefaultDetachmentRespondentJudge();
		this.setDefaultCommandPropagationPermissionJudge();
		this.setDefaultInformationPropagationPermissionJudge();
	}
	
	public static Epifyte makeClone(Epifyte epifyte){
		Epifyte clone = (Epifyte) Agent.makeClone(epifyte);
		clone.setLabel(epifyte.getLabel());
		ArrayList<String> cloneTags = new ArrayList<String>(epifyte.getTags());
		clone.setTags(cloneTags);
		clone.setBindingRequesterJudge(epifyte.getBindingRequesterJudge());
		clone.setBindingRespondentJudge(epifyte.getBindingRespondentJudge());
		clone.setDetachmentRequesterJudge(epifyte.getDetachmentRequesterJudge());
		clone.setDetachmentRespondentJudge(epifyte.getDetachmentRespondentJudge());
		clone.setCommandPropagationPermissionJudge(epifyte.getCommandPropagationPermissionJudge());
		clone.setInformationPropagationPermissionJudge(epifyte.getInformationPropagationPermissionJudge());
		return clone;
	}
	
	/*public Epifyte makeCopy(){//To be overridden
		//Important: Make sure that the code here works correctly
		//Everything gets copied except lower
		Epifyte epifyteCopy = new Epifyte();
		epifyteCopy.setLabel(this.label);
		epifyteCopy.setTags((ArrayList<String>) this.tags.clone());
		ArrayList<Epifyte> upperCopy = (ArrayList<Epifyte>) this.getUpper().clone();
		for(int i = 0; i < upperCopy.size(); i++){
			upperCopy.set(i, upperCopy.get(i).makeCopy());
		}
		ArrayList<EpifyteModifier> upperModifiersCopy = (ArrayList<EpifyteModifier>) 
				this.getUpperModifiers().clone();
		for(int i = 0; i < upperModifiersCopy.size(); i++){
			upperModifiersCopy.set(i, (EpifyteModifier) upperModifiersCopy.get(i).makeCopy());
		}
		ArrayList<EpifyteArm> upperArmsCopy = (ArrayList<EpifyteArm>) this.getUpperArms().clone();
		for(int i = 0; i < upperArmsCopy.size(); i++){
			upperArmsCopy.set(i, (EpifyteArm) upperArmsCopy.get(i).makeCopy());
		}
		epifyteCopy.setBindingRequesterJudge(this.bindingRequesterJudge);
		epifyteCopy.setBindingRespondentJudge(this.bindingRespondentJudge);
		epifyteCopy.setDetachmentRequesterJudge(this.detachmentRequesterJudge);
		epifyteCopy.setDetachmentRespondentJudge(this.detachmentRespondentJudge);
		epifyteCopy.setCommandPropagationPermissionJudge(this.commandPropagationPermissionJudge);
		epifyteCopy.setInformationPropagationPermissionJudge(this.informationPropagationPermissionJudge);
		return epifyteCopy;
	}*/
	
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
	
	public Trilean canBindTo(Epifyte epifyte){
		//Whether this can go on top of the given epifyte
		return this.getBindingRequesterJudge().canBindTo(epifyte);
	}
	
	public void setDefaultBindingRequesterJudge(){//To be overridden
		this.setBindingRequesterJudge((epifyte) -> Trilean.UNDECIDED);
	}
	
	public Trilean canBeBoundBy(Epifyte epifyte){
		//Whether the given epifyte can go on top of this
		return this.getBindingRespondentJudge().canBeBoundBy(epifyte);
	}
	
	public void setDefaultBindingRespondentJudge(){//To be overridden
		this.setBindingRespondentJudge((epifyte) -> Trilean.UNDECIDED);
	}
	
	public Trilean canDetachFrom(Epifyte epifyte){
		//Whether this can unbind from the given epifyte
		return this.getDetachmentRequesterJudge().canDetachFrom(epifyte);
	}
	
	public void setDefaultDetachmentRequesterJudge(){//To be overridden
		this.setDetachmentRequesterJudge((epifyte) -> {if(this.lower.contains(epifyte)){
			//It wasn't bound in the first place
			return Trilean.FALSE;
		}
		else{
			return Trilean.TRUE;
		}});
	}
	
	public Trilean canBeDetachedBy(Epifyte epifyte){
		//Whether the given epifyte can unbind from this
		return this.getDetachmentRespondentJudge().canBeDetachedBy(epifyte);
	}
	
	public void setDefaultDetachmentRespondentJudge(){//To be overridden
		this.setDetachmentRespondentJudge((epifyte) -> {if(this.upper.contains(epifyte)){
			//It wasn't bound in the first place
			return Trilean.FALSE;
		}
		else{
			return Trilean.TRUE;
		}});
	}
	
	public static void attemptBind(Epifyte binder, Epifyte target){//Binder would go on top of the target
		if(evaluateBindingValidity(binder, target) == true){
			forceBind(binder, target);
		}
	}
	
	public static void forceBind(Epifyte binder, Epifyte target){
		binder.getLower().add(target);
		target.getUpper().add(binder);
		if(EpifyteModifier.class.isAssignableFrom(binder.getActualClass())){
			target.getUpperModifiers().add((EpifyteModifier) binder);
			if(EpifyteArm.class.isAssignableFrom(binder.getActualClass())){
				target.getUpperArms().add((EpifyteArm) binder);
			}
		}
	}
	
	public static void attemptDetach(Epifyte detacher, Epifyte host){
		if(evaluateDetachmentValidity(detacher, host) == true){
			forceDetach(detacher, host);
		}
	}
	
	public static void forceDetach(Epifyte detacher, Epifyte host){
		detacher.getLower().remove(host);
		host.getUpper().remove(detacher);
		if(EpifyteModifier.class.isAssignableFrom(detacher.getActualClass())){
			host.getUpperModifiers().remove((EpifyteModifier) detacher);
			if(EpifyteArm.class.isAssignableFrom(detacher.getActualClass())){
				host.getUpperArms().remove((EpifyteArm) detacher);
			}
		}
	}
	
	public static void forceMove(Epifyte traveller, Epifyte lower1, Epifyte lower2){
		forceDetach(traveller, lower1);
		forceBind(traveller, lower2);
	}
	
	public static void forceReplace(Epifyte host, Epifyte upper1, Epifyte upper2){
		forceDetach(upper1, host);
		forceBind(upper2, host);
	}
	
	public static void forceInsertAbove(Epifyte newcomer, Epifyte lower) {
		ArrayList<Epifyte> lowerUpper = (ArrayList<Epifyte>) lower.getUpper().clone();
		for(Epifyte epifyte : lowerUpper) {
			forceDetach(epifyte, lower);
			forceBind(epifyte, newcomer);
		}
		forceBind(newcomer, lower);
	}
	
	public static void forceInsertBelow(Epifyte newcomer, Epifyte upper) {
		ArrayList<Epifyte> upperLower = (ArrayList<Epifyte>) upper.getLower().clone();
		for(Epifyte epifyte : upperLower) {
			forceDetach(upper, epifyte);
			forceBind(newcomer, epifyte);
		}
		forceBind(upper, newcomer);
	}
	
	public static void forceSlideOutFromAbove(Epifyte middle, Epifyte lower) {
		ArrayList<Epifyte> middleUpper = (ArrayList<Epifyte>) middle.getUpper().clone();
		for(Epifyte epifyte : middleUpper) {
			forceDetach(epifyte, middle);
			forceBind(epifyte, lower);
		}
		forceDetach(middle, lower);
	}
	
	public static void forceSlideOutFromBelow(Epifyte middle, Epifyte upper) {
		ArrayList<Epifyte> middleLower = (ArrayList<Epifyte>) middle.getLower().clone();
		for(Epifyte epifyte : middleLower) {
			forceDetach(middle, epifyte);
			forceBind(upper, epifyte);
		}
		forceDetach(upper, middle);
	}
	
	public void setDefaultCommandPropagationPermissionJudge(){//To be overridden
		this.setCommandPropagationPermissionJudge((command, commandRoute, supportDataSet) -> true);
	}
	
	public void setDefaultInformationPropagationPermissionJudge(){//To be overridden
		this.setInformationPropagationPermissionJudge((informationName, 
				propagatedDataSet, informationRoute, supportDataSet) -> true);
	}
	
	public static boolean evaluateBindingValidity(Epifyte binder, Epifyte target){//Helper method
		try {
			Method method = PieceTreeCompiler.getMethodPieceClass("BINDINGVALIDITYEVALUATORSLOT")
					.getMethod("evaluateTrilean", Trilean.class, Trilean.class);
			return (boolean) method.invoke(null, binder.canBindTo(target), target.canBeBoundBy(binder));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean evaluateDetachmentValidity(Epifyte detacher, Epifyte host){//Helper method
		try {
			Method method = PieceTreeCompiler.getMethodPieceClass("DETACHMENTVALIDITYEVALUATORSLOT")
					.getMethod("evaluateTrilean", Trilean.class, Trilean.class);
			return (boolean) method.invoke(null, detacher.canDetachFrom(host), host.canBeDetachedBy(detacher));
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public static Map<String, Integer> getMethodPieceSlots(){
		return Piece.makeSimpleSlotList("BINDINGVALIDITYEVALUATORSLOT", 
				"DETACHMENTVALIDITYEVALUATORSLOT", "EVENTINTERPRETERSLOT");//TODO
	}
	public static Map<String, Integer> getObjectPieceSlots(){
		return Piece.makeSimpleSlotList();//TODO
	}
	public static ArrayList<String> getDependentPieceNames(){
		return new ArrayList<String>(Arrays.asList("EpifyteModifier", "EpifyteArm"));
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
					for(EpifyteModifier epifyteModifier : upperModifiers){
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
			System.out.println(informationNameArray[0]);
			if(informationNameArray[0].equals("epifyteSeek")){
				System.out.println("Check");
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
	
	public static void addPrototype(String prototypeName, EpifytePrototype epifytePrototype){
		Epifyte.getEpifyteCreationProtocol().put(prototypeName, epifytePrototype);
	}
	
	public static EpifytePrototype getPrototype(String prototypeName) {
		return Epifyte.getEpifyteCreationProtocol().get(prototypeName);
	}
	
	public static Epifyte makeFromPrototype(String prototypeName, DataSet dataSet) {
		return Epifyte.getPrototype(prototypeName).make(dataSet);
	}
	
	public Epifyte getSingleLowerOfTag(String tag){
		for(Epifyte lowerEpifyte : this.getLower()){
			if(lowerEpifyte.getTags().contains(tag)){
				return lowerEpifyte;
			}
		}
		return null;
	}
	
	public Epifyte traceLowerTillTag(String tag){
		for(Epifyte lowerEpifyte : this.getLower()){
			if(lowerEpifyte.getTags().contains(tag)){
				return lowerEpifyte;
			}
			else{
				if(lowerEpifyte.traceLowerTillTag(tag) != null){
					return lowerEpifyte.traceLowerTillTag(tag);
				}
			}
		}
		return null;
	}
	
	public Epifyte getSingleUpperOfTag(String tag){
		for(Epifyte upperEpifyte : this.getUpper()){
			if(upperEpifyte.getTags().contains(tag)){
				return upperEpifyte;
			}
		}
		return null;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public ArrayList<Epifyte> getUpper() {
		return upper;
	}

	public void setUpper(ArrayList<Epifyte> upper) {
		this.upper = upper;
	}

	public ArrayList<Epifyte> getLower() {
		return lower;
	}

	public void setLower(ArrayList<Epifyte> lower) {
		this.lower = lower;
	}

	public ArrayList<EpifyteModifier> getUpperModifiers() {
		return upperModifiers;
	}

	public void setUpperModifiers(ArrayList<EpifyteModifier> upperModifiers) {
		this.upperModifiers = upperModifiers;
	}

	public ArrayList<EpifyteArm> getUpperArms() {
		return upperArms;
	}

	public void setUpperArms(ArrayList<EpifyteArm> upperArms) {
		this.upperArms = upperArms;
	}

	public BindingRequesterJudge getBindingRequesterJudge() {
		return bindingRequesterJudge;
	}

	public void setBindingRequesterJudge(BindingRequesterJudge bindingRequesterJudge) {
		this.bindingRequesterJudge = bindingRequesterJudge;
	}

	public BindingRespondentJudge getBindingRespondentJudge() {
		return bindingRespondentJudge;
	}

	public void setBindingRespondentJudge(BindingRespondentJudge bindingRespondentJudge) {
		this.bindingRespondentJudge = bindingRespondentJudge;
	}

	public DetachmentRequesterJudge getDetachmentRequesterJudge() {
		return detachmentRequesterJudge;
	}

	public void setDetachmentRequesterJudge(DetachmentRequesterJudge detachmentRequesterJudge) {
		this.detachmentRequesterJudge = detachmentRequesterJudge;
	}

	public DetachmentRespondentJudge getDetachmentRespondentJudge() {
		return detachmentRespondentJudge;
	}

	public void setDetachmentRespondentJudge(DetachmentRespondentJudge detachmentRespondentJudge) {
		this.detachmentRespondentJudge = detachmentRespondentJudge;
	}

	public CommandPropagationPermissionJudge getCommandPropagationPermissionJudge() {
		return commandPropagationPermissionJudge;
	}

	public void setCommandPropagationPermissionJudge(CommandPropagationPermissionJudge commandPropagationPermissionJudge) {
		this.commandPropagationPermissionJudge = commandPropagationPermissionJudge;
	}

	public InformationPropagationPermissionJudge getInformationPropagationPermissionJudge() {
		return informationPropagationPermissionJudge;
	}

	public void setInformationPropagationPermissionJudge(InformationPropagationPermissionJudge informationPropagationPermissionJudge) {
		this.informationPropagationPermissionJudge = informationPropagationPermissionJudge;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	
	public void setTags(String...tags){
		this.setTags(new ArrayList<String>());
		for(String tag: tags){
			this.tags.add(tag);
		}
	}

	public static Map<String, EpifytePrototype> getEpifyteCreationProtocol() {
		return epifyteCreationProtocol;
	}

	public static void setEpifyteCreationProtocol(Map<String, EpifytePrototype> epifyteCreationProtocol) {
		Epifyte.epifyteCreationProtocol = epifyteCreationProtocol;
	}
}
