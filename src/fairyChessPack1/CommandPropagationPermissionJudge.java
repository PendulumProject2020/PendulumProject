package fairyChessPack1;

import java.util.ArrayList;

import main.DataSet;

@FunctionalInterface
public interface CommandPropagationPermissionJudge {
	public boolean canCommandPropagationContinue(String command, ArrayList<Epifyte> commandRoute, DataSet SupportDataSet);
}
