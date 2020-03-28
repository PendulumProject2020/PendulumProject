package fairyChessPack1;

import java.util.ArrayList;

import main.DataSet;

@FunctionalInterface
public interface InformationPropagationPermissionJudge {
	public boolean canInformationPropagationContinue(String informationName, 
			DataSet propagatedDataSet, ArrayList<Epifyte> informationRoute, DataSet SupportDataSet);
}
