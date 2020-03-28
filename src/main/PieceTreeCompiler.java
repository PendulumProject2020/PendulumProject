package main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import chronosPack.Chronos;

public class PieceTreeCompiler {
	//This can be considered a "God Class".
	//During Piece compilation, the compiler needs to conduct three steps:
	//Step 1: Add - find the piece classes by name and record them down.
	//Step 2: Load - construct a piece tree and check that all slots are filled. All slots must be filled
	//for the next step to begin.
	//Step 3: Activate - check that all dependent pieces are added and loaded. All pieces must be activated
	//for this step to finish.
	//All three steps need to be complete for main program to run.
	private static ArrayList<Class<Piece>> objectPieceList = new ArrayList<Class<Piece>>();
	//Sometimes I am not sure whether I should use Class<MethodPiece> and Class<ObjectPiece>, or just Class<Piece>
	//We will see how it works out.
	private static ArrayList<Class<Piece>> methodPieceList = new ArrayList<Class<Piece>>();
	private static Map<String, Class<Piece>> objectPieceNameToClassMap = new HashMap<String, Class<Piece>>();  
	private static Map<String, Class<Piece>> methodPieceNameToClassMap = new HashMap<String, Class<Piece>>();
	private static Map<Class<Piece>, Map<Class<Piece>, DataSet>> pieceClassToStaticDataSupersetMap = 
			new HashMap<Class<Piece>, Map<Class<Piece>, DataSet>>();
	private static Map<Class<?>, Map<String, DataSet>> pieceClassToStaticNamedDataSupersetMap = 
			new HashMap<Class<?>, Map<String, DataSet>>();
	private static Class<Piece> rootClass = null;
	//Data superset refers to the collection of all piece-specific data sets
	private static Map<Class<Piece>, PieceProjection> pieceClassToProjectionMap 
	= new HashMap<Class<Piece>, PieceProjection>();
	
	public static ArrayList<Class<Piece>> findAndAddObjectPieces(ArrayList<String> 
	objectPieceNames){
		ArrayList<Class<Piece>> tentativeList = new ArrayList<Class<Piece>>();
		for(String objectPieceName : objectPieceNames){
			try{
			Class<Piece> objectPieceClass = (Class<Piece>) 
					Class.forName(objectPieceName);
			if(ObjectPiece.class.isAssignableFrom(objectPieceClass)){
				if(objectPieceList.contains(objectPieceClass)){
					System.out.println("The Object Piece " + objectPieceClass.getSimpleName() + " has already been added");
				}
				else{
					objectPieceList.add(objectPieceClass);
					objectPieceNameToClassMap.put(objectPieceClass.getSimpleName(), (Class<Piece>) objectPieceClass);
					pieceClassToProjectionMap.put(objectPieceClass, new PieceProjection(objectPieceClass));
					System.out.println("The Object Piece " + objectPieceClass.getSimpleName() + " was added successfully");
				}
			}
			else{
				System.out.println("The Object Piece " + objectPieceClass.getSimpleName() + " failed to be added.");
			}
			}
			catch(Exception e){
				System.out.println("The Object Piece " + objectPieceName + " failed to be added.");
			}
		}
		return tentativeList;
	}
	
	public static ArrayList<Class<Piece>> findAndAddObjectPieces(String...objectPieceNames){
		ArrayList<String> tentativeList = new ArrayList<String>();
		for(String objectPieceName : objectPieceNames){
			tentativeList.add(objectPieceName);
		}
		return findAndAddObjectPieces(tentativeList);
	}
	public static ArrayList<Class<Piece>> findAndAddMethodPieces(ArrayList<String> 
	methodPieceNames){
		ArrayList<Class<Piece>> tentativeList = new ArrayList<Class<Piece>>();
		for(String methodPieceName : methodPieceNames){
			try{
			Class<Piece> methodPieceClass = (Class<Piece>) 
					Class.forName(methodPieceName);
			if(MethodPiece.class.isAssignableFrom(methodPieceClass)){
				if(methodPieceList.contains(methodPieceClass)){
					System.out.println("The Method Piece " + methodPieceClass.getSimpleName() + " has already been added");
				}
				else{
					methodPieceList.add(methodPieceClass);
					methodPieceNameToClassMap.put(methodPieceClass.getSimpleName(), 
							(Class<Piece>) methodPieceClass);
					pieceClassToProjectionMap.put(methodPieceClass, new PieceProjection(methodPieceClass));
					System.out.println("The Method Piece " + methodPieceClass.getSimpleName() + " was added successfully");
				}
			}
			else{
				System.out.println("The Method Piece " + methodPieceClass.getSimpleName() + " failed to be added.");
			}
			}
			catch(Exception e){
				System.out.println("The Method Piece " + methodPieceName + " failed to be added.");
			}
		}
		return tentativeList;
	}
	
	public static ArrayList<Class<Piece>> findAndAddMethodPieces(String...methodPieceNames){
		ArrayList<String> tentativeList = new ArrayList<String>();
		for(String methodPieceName : methodPieceNames){
			tentativeList.add(methodPieceName);
		}
		return findAndAddMethodPieces(tentativeList);
	}
	
	public static boolean loadPieceTree(Class<Piece> rootClass1){//The relevant piece classes need to be added before this
		rootClass = rootClass1;
		pieceClassToProjectionMap.get(rootClass).setHostSlot("_ROOTSLOT");
		if(tryToFillAllSlots(pieceClassToProjectionMap.get(rootClass)) == true){
			while(tryHostingAnyFreePieceToAMegaSlot()){
				//nothing here
			}
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean tryHostingAnyFreePieceToAMegaSlot(){
		//This is a complete search.
		for(PieceProjection pieceProjection : PieceTreeCompiler.pieceClassToProjectionMap.values()){
			if(pieceProjection.traceSlotPath() == null){//This is a free piece.
				for(PieceProjection pieceProjection1 : PieceTreeCompiler.pieceClassToProjectionMap.values()){
					if(pieceProjection1.traceSlotPath() != null){//The piece is on the tree.
						return pieceProjection1.attemptToHostInMegaSlot(pieceProjection);
						//Need to end here, because it affects the parameters involved in the for loop
					}
				}
			}
		}
		return false;
	}
	
	public static boolean loadPieceTree(String rootClassName){
		if(methodPieceNameToClassMap.containsKey(rootClassName)){
			return loadPieceTree(methodPieceNameToClassMap.get(rootClassName));
		}
		else{
			return false;
		}
	}
	
	public static boolean tryToFillAllSlots(PieceProjection pieceProjection){
		//Try to fill all slots of a pieceProjection
		//This is a DFS
		//Check this code carefully
		if(pieceProjection == null){
			return false;
		}
		while(pieceProjection.areAllObjectSlotsFilled() == false){
			for(Entry<String, Integer> slotGroup : pieceProjection.getFreeObjectSlots().entrySet()){
				if(slotGroup.getValue() != 0){
					PieceProjection candidate = GetFitFor(pieceProjection, slotGroup.getKey());
					if(candidate == null){
						return false;//There is nothing available that can fit this slot
					}
					else{
						if(tryToFillAllSlots(candidate) == false){
							return false;
						}
					}
				}
			}
		}
		while(pieceProjection.areAllMethodSlotsFilled() == false){
			for(Entry<String, Integer> slotGroup : pieceProjection.getFreeMethodSlots().entrySet()){
				if(slotGroup.getValue() != 0){
					PieceProjection candidate = GetFitFor(pieceProjection, slotGroup.getKey());
					if(candidate == null){
						return false;//There is nothing available that can fit this slot
					}
					else{
						if(tryToFillAllSlots(candidate) == false){
							return false;
						}
					}
				}
			}
		}
		return true;
	}
	
	public static PieceProjection GetFitFor(PieceProjection pieceProjection, String slotName){
		for(PieceProjection candidate : pieceClassToProjectionMap.values()){
			if(pieceProjection.attemptToHost(slotName, candidate)){
				return candidate;
			}
		}
		return null;
	}

	public static ArrayList<Class<Piece>> getObjectPieceList() {
		return objectPieceList;
	}
	
	public static Class<Piece> getSingleMethodSlotOccupant(Class<?> host, String slotName){
		return PieceTreeCompiler.getPieceClassToProjectionMap()
				.get(host).
				getOccupiedMethodSlots().
				get(slotName)
				.get(0).getOrigin();
	}
	
	public static Class<Piece> getSingleObjectSlotOccupant(Class<?> host, String slotName){
		return PieceTreeCompiler.getPieceClassToProjectionMap()
				.get(host).
				getOccupiedObjectSlots().
				get(slotName)
				.get(0).getOrigin();
	}
	
	public static DataSet extractAsDataSet(Class<?> requester, Class<?> target){
		return PieceTreeCompiler.getPieceClassToStaticDataSupersetMap().get(requester).get(target);
	}
	
	public static DataSet extractAsDataSet(Class<?> requester, String parameterName){
		return PieceTreeCompiler.getPieceClassToStaticNamedDataSupersetMap().get(requester)
				.get(parameterName);
	}

	public static void setObjectPieceList(ArrayList<Class<Piece>> objectPieceList) {
		PieceTreeCompiler.objectPieceList = objectPieceList;
	}
	
	public static Class<Piece> getObjectPieceClass(String objectPieceName){
		return PieceTreeCompiler.objectPieceNameToClassMap.get(objectPieceName);
	}
	
	public static Class<Piece> getMethodPieceClass(String methodPieceName){
		return PieceTreeCompiler.methodPieceNameToClassMap.get(methodPieceName);
	}

	public static ArrayList<Class<Piece>> getMethodPieceList() {
		return methodPieceList;
	}

	public static void setMethodPieceList(ArrayList<Class<Piece>> methodPieceList) {
		PieceTreeCompiler.methodPieceList = methodPieceList;
	}

	public static Map<Class<Piece>, Map<Class<Piece>, DataSet>> getPieceClassToStaticDataSupersetMap() {
		return pieceClassToStaticDataSupersetMap;
	}

	public static void setPieceClassToStaticDataSupersetMap(Map<Class<Piece>, Map<Class<Piece>, DataSet>> pieceClassToStaticDataSupersetMap) {
		PieceTreeCompiler.pieceClassToStaticDataSupersetMap = pieceClassToStaticDataSupersetMap;
	}

	public static Class<Piece> getRootClass() {
		return rootClass;
	}

	public static void setRootClass(Class<Piece> rootClass) {
		PieceTreeCompiler.rootClass = rootClass;
	}
	
	public static void setPieceClassToProjectionMap(Map<Class<Piece>, PieceProjection> pieceClassToProjectionMap ){
		PieceTreeCompiler.pieceClassToProjectionMap = pieceClassToProjectionMap;
	}
	
	public static Map<Class<Piece>, PieceProjection> getPieceClassToProjectionMap(){
		return PieceTreeCompiler.pieceClassToProjectionMap;
	}

	public static Map<Class<?>, Map<String, DataSet>> getPieceClassToStaticNamedDataSupersetMap() {
		return pieceClassToStaticNamedDataSupersetMap;
	}

	public static void setPieceClassToStaticNamedDataSupersetMap(Map<Class<?>, Map<String, DataSet>> pieceClassToStaticNamedDataSupersetMap) {
		PieceTreeCompiler.pieceClassToStaticNamedDataSupersetMap = pieceClassToStaticNamedDataSupersetMap;
	}
}
