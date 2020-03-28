package main;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataSet {
	private Map<String, DataSet> dataSubsets;
	private Class<?> entryType = Object.class;
	private Object entry;
	
	public DataSet(){
		
	}
	public DataSet(Class<?> entryType, Object entry){
		this.entry = entry;
		this.entryType = entryType;
	}
	
	public ArrayList<?> presentAsArrayList(){
		return (ArrayList<?>) entry;
	}
	
	public boolean equals(DataSet dataSet){//Not sure if this works properly
		if(dataSubsets.equals(dataSet.getDataSubsets())){
			if(entry.equals(dataSet.getEntry())){
				if(entryType == dataSet.getEntryType()){
					return true;
				}
			}
		}
		return false;
	}
	
	public void addDataSubset(String dataSubsetName, DataSet dataSubset){
		this.dataSubsets.put(dataSubsetName, dataSubset);
	}
	public DataSet findDataSubset(String dataSubsetName){
		return dataSubsets.get(dataSubsetName);
	}
	public DataSet findDataSubsetByPath(ArrayList<String> path){
		if(path.size() == 1){
			return findDataSubset(path.get(0));
		}
		if(path.size() == 0){
			return null;
		}
		else{
			DataSet immediateSubset = dataSubsets.get(path.get(0));
			path.remove(0);
			return(immediateSubset.findDataSubsetByPath(path));
		}
	}
	public Object findEntry(String dataSubsetName){
		return findDataSubset(dataSubsetName).getEntry();
	}
	public Object findEntryByPath(ArrayList<String> path){
		return findDataSubsetByPath(path).getEntry();
	}
	
	public Map<String, DataSet> getDataSubsets(){
		return dataSubsets;
	}
	
	public void setDataSubsets(Map<String, DataSet> dataSubsets){
		this.dataSubsets = dataSubsets;
	}
	
	public Object getEntry() {
		return entry;
	}
	public void setEntry(Object entry) {
		this.entry = entry;
	}
	public Class<?> getEntryType() {
		return entryType;
	}
	public void setEntryType(Class<?> entryType) {
		this.entryType = entryType;
	}
}
