package main;

@FunctionalInterface
public interface CommandExecutor{
	public void interfaceExecuteCommand(ObjectPiece target, String command, DataSet dataset);
	//making instances of this interface will frequently involve invoking target.getActualClass
}
