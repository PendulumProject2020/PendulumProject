package main;

@FunctionalInterface
public interface InstanceInformationEvaluator{
	public DataSet interfaceEvaluateInstanceInformation(ObjectPiece target, String informationName, DataSet dataSet);
	//making instances of this interface will frequently involve invoking target.getActualClass
}
