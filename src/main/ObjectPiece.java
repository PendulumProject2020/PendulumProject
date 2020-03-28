package main;

import java.util.HashMap;
import java.util.Map;

public class ObjectPiece extends Piece{
	
	private Map<Class<Piece>, DataSet> pieceSpecificDataSets = new HashMap<Class<Piece>, DataSet>();
	//Only an ObjectPiece can have this as an instance object 
	private Map<String, DataSet> nameSpecificDataSets = new HashMap<String, DataSet>();//Basically like normal instance variables
	private Class<?> actualClass;//Avoids confusion between superclass and subclass
	//Important: Make sure you invoke setActualClass whenever you make a constructor of an ObjectPiece subclass
	private CommandExecutor commandExecutor;
	private InstanceInformationEvaluator instanceInformationEvaluator;
	//Be aware that for both evaluators, the target is passed as an objectPiece regardless of its actual class

	public ObjectPiece(){
		this.setActualClass(ObjectPiece.class);
		this.setDefaultCommandExecutor();
		this.setDefaultInstanceInformationEvaluator();
	}
	
	public static ObjectPiece makeClone(ObjectPiece objectPiece){
		ObjectPiece clone = new ObjectPiece();
		Map<Class<Piece>, DataSet> pieceSpecificDataSetsClone 
		= new HashMap<Class<Piece>, DataSet>(objectPiece.pieceSpecificDataSets);
		clone.setPieceSpecificDataSets(pieceSpecificDataSetsClone);
		Map<String, DataSet> nameSpecificDataSetsClone 
		= new HashMap<String, DataSet>(objectPiece.nameSpecificDataSets);
		clone.setNameSpecificDataSets(nameSpecificDataSetsClone);
		clone.setActualClass(objectPiece.getActualClass());
		clone.setCommandExecutor(objectPiece.getCommandExecutor());
		clone.setInstanceInformationEvaluator(objectPiece.getInstanceInformationEvaluator());
		return clone;
	}
	
	public void setDefaultCommandExecutor(){
		//To be overridden
		this.setCommandExecutor((target, command, dataSet) -> {
			//Do nothing
		});
	}
	
	public void setDefaultInstanceInformationEvaluator(){
		//To be overridden
		this.setInstanceInformationEvaluator((target, command, dataSet) -> {
			return null;
		});
	}
	
	public void executeCommand(String command){
		this.getCommandExecutor()
		.interfaceExecuteCommand(this, command, null);
	}
	public void executeCommandWithDataSet(String command, DataSet dataSet){
		this.getCommandExecutor()
		.interfaceExecuteCommand(this, command, dataSet);
	}
	
	
	public DataSet evaluateInstanceInformation(String informationName){
		return this.getInstanceInformationEvaluator()
				.interfaceEvaluateInstanceInformation(this, informationName, null);
	}
	public DataSet evaluateInstanceInformationWithDataSet(String informationName, DataSet dataSet){
		return this.getInstanceInformationEvaluator()
				.interfaceEvaluateInstanceInformation(this, informationName, dataSet);
	}
	
	public DataSet getDataSet(Class<Piece> pieceClass){
		return pieceSpecificDataSets.get(pieceClass);
	}
	
	public void setDataSet(Class<Piece> pieceClass, DataSet dataSet){
		pieceSpecificDataSets.put(pieceClass, dataSet);
	}
	
	public DataSet getDataSet(String dataSetName){
		return nameSpecificDataSets.get(dataSetName);
	}
	
	public void setDataSet(String dataSetName, DataSet dataSet){
		nameSpecificDataSets.put(dataSetName, dataSet);
	}
	
	public Map<Class<Piece>, DataSet> getPieceSpecificDataSets() {
		return pieceSpecificDataSets;
	}

	public void setPieceSpecificDataSets(Map<Class<Piece>, DataSet> pieceSpecificDataSets) {
		this.pieceSpecificDataSets = pieceSpecificDataSets;
	}
	public Map<String, DataSet> getNameSpecificDataSets() {
		return nameSpecificDataSets;
	}
	public void setNameSpecificDataSets(Map<String, DataSet> nameSpecificDataSets) {
		this.nameSpecificDataSets = nameSpecificDataSets;
	}
	public Class<?> getActualClass() {
		return actualClass;
	}
	public void setActualClass(Class<?> actualClass) {
		this.actualClass = actualClass;
	}
	public CommandExecutor getCommandExecutor() {
		return commandExecutor;
	}
	public void setCommandExecutor(CommandExecutor commandExecutor) {
		this.commandExecutor = commandExecutor;
	}
	public InstanceInformationEvaluator getInstanceInformationEvaluator() {
		return instanceInformationEvaluator;
	}
	public void setInstanceInformationEvaluator(InstanceInformationEvaluator instanceInformationEvaluator) {
		this.instanceInformationEvaluator = instanceInformationEvaluator;
	}
}
