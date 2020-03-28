package fairyChessPack1;

import java.util.ArrayList;

import main.DataSet;

@FunctionalInterface
public interface EpifyteCommandHandler {
	public void handle(String command, ArrayList<Epifyte> commandRoute, DataSet SupportDataSet);
}
