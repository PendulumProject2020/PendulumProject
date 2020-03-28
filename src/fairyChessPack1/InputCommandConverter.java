package fairyChessPack1;

import javafx.event.Event;
import main.DataSet;

@FunctionalInterface
public interface InputCommandConverter {
	public void convertInputToCommand(Event event, DataSet supportDataSet);
}
