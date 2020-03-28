package main;

import java.util.ArrayList;
import java.util.List;

public class SlotPath {//"Full slot name" means this.
	private ArrayList<String> piecePath;
	private String slotName;
	
	public SlotPath(ArrayList<String> piecePath, String slotName){
		this.piecePath = piecePath;
		this.slotName = slotName;
	}
	
	public SlotPath(){//Root slot path constructor
		this.piecePath = new ArrayList<String>();
		this.slotName = "_ROOTSLOT";
	}
	
	public boolean equals(SlotPath slotPath){
		if(this.piecePath.equals(slotPath.getPiecePath()) && this.slotName.equals(slotPath.getSlotName())){
			return true;
		}
		else{
			return false;
		}
	}
	
	public SlotPath(String slotName){
		this.piecePath = new ArrayList<String>();
		this.slotName = slotName;
	}

	public ArrayList<String> getPiecePath() {
		return piecePath;
	}

	public void setPiecePath(ArrayList<String> piecePath) {
		this.piecePath = piecePath;
	}

	public String getSlotName() {
		return slotName;
	}

	public void setSlotName(String slotName) {
		this.slotName = slotName;
	}
}
