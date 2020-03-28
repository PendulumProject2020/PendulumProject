package fairyChessPack1;

import java.util.ArrayList;

import main.DataSet;

@FunctionalInterface
public interface EpifyteInformationFinder {
	public DataSet findInformation(String informationName, 
			DataSet propagatedDataSet, ArrayList<Epifyte> informationRoute, DataSet SupportDataSet);
}
