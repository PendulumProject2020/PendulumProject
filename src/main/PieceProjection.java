package main;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class PieceProjection {//A "drawing" of the piece dependency; Used when compiling pieces
	
	private Class<Piece> origin;//The piece that this object projects
	private PieceProjection hostPiece;//The projection of the piece that the origin is attached to
	private String hostSlot;//Use simple slot name
	private Map<String, Integer> freeObjectSlots;//Use simple slot name
	private Map<String, Integer> freeMethodSlots;//Use simple slot name
	private Map<String, ArrayList<PieceProjection> > occupiedObjectSlots;//Use simple slot name
	private Map<String, ArrayList<PieceProjection> > occupiedMethodSlots;//Use simple slot name
	private ArrayList<PieceProjection> objectMegaSlot;
	private ArrayList<PieceProjection> methodMegaSlot;
	private ArrayList<SlotPath> possibleFits;//Long name
	private ArrayList<String> dependentPieceNames;
	public PieceProjection(Class<Piece> origin){
		this.origin = origin;
		try {
			Method originClassGetPossibleFitsMethod = origin.getMethod("getPossibleFits");
			if(originClassGetPossibleFitsMethod == null){
				System.out.println("Problem1");
			}
			possibleFits = (ArrayList<SlotPath>) originClassGetPossibleFitsMethod.invoke(null);
			Method originClassGetMethodPieceSlotsMethod = origin.getMethod("getMethodPieceSlots");
			freeMethodSlots = (Map<String, Integer>) originClassGetMethodPieceSlotsMethod.invoke(null);
			Method originClassGetObjectPieceSlotsMethod = origin.getMethod("getObjectPieceSlots");
			freeObjectSlots = (Map<String, Integer>) originClassGetObjectPieceSlotsMethod.invoke(null);
			Method originClassGetDependenciesMethod = origin.getMethod("getDependentPieceNames");
			dependentPieceNames = (ArrayList<String>) originClassGetDependenciesMethod.invoke(null);
			//Initialization
			hostPiece = null;
			hostSlot = null;
			occupiedObjectSlots = new HashMap<String, ArrayList<PieceProjection>>();
			occupiedMethodSlots = new HashMap<String, ArrayList<PieceProjection>>();
			objectMegaSlot = new ArrayList<PieceProjection>();
			methodMegaSlot = new ArrayList<PieceProjection>();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public boolean areAllObjectSlotsFilled(){
		for(Integer numberOfSuchSlots : freeObjectSlots.values()){
			if(numberOfSuchSlots != 0){
				return false;
			}
		}
		return true;
	}
	public boolean areAllMethodSlotsFilled(){
		for(Integer numberOfSuchSlots : freeMethodSlots.values()){
			if(numberOfSuchSlots != 0){
				return false;
			}
		}
		return true;
	}
	public boolean attemptToHost(String slotNameSimple, PieceProjection pieceProjection){
		//Make sure that the code here is complete

		System.out.println("Trying to host " + 
		pieceProjection.getOrigin().getSimpleName() + " to " + slotNameSimple);
		if(pieceProjection == this){
			return false;
		}
		if(checkFitOf(slotNameSimple, pieceProjection) == true 
				&& pieceProjection.checkFitTo(this.traceSlotPathOnThis(slotNameSimple)) == true){
			if(ObjectPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//Whether it is a projection of Object-type piece
				if(freeObjectSlots.containsKey(slotNameSimple)){
					Integer noOfFreeSlots = freeObjectSlots.get(slotNameSimple);
					if(noOfFreeSlots == 0){
						return false;//No more free slot of this name
					}
					else{
						freeObjectSlots.replace(slotNameSimple, noOfFreeSlots-1);//Reduced number of free slots by 1
						addAnOccupiedObjectSlot(slotNameSimple, pieceProjection);
						pieceProjection.setHostPiece(this);
						pieceProjection.setHostSlot(slotNameSimple);
						System.out.println("host attempt successful: " + 
						pieceProjection.getOrigin().getSimpleName() + " to " + slotNameSimple);
						return true;//There is a free slot of this name
					}
				}
				else{
					return false;//No such slot
				}
			}
			else if(MethodPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//Whether it is a projection of Method-type piece
				if(freeMethodSlots.containsKey(slotNameSimple)){
					Integer noOfFreeSlots = freeMethodSlots.get(slotNameSimple);
					if(freeMethodSlots.get(slotNameSimple) == 0){
						return false;//No more free slot of this name
					}
					else{
						freeMethodSlots.replace(slotNameSimple, noOfFreeSlots-1);//Reduced number of free slots by 1
						addAnOccupiedMethodSlot(slotNameSimple, pieceProjection);
						pieceProjection.setHostPiece(this);
						pieceProjection.setHostSlot(slotNameSimple);
						System.out.println("host attempt successful: " + 
								pieceProjection.getOrigin().getSimpleName() + " to " + slotNameSimple);
						return true;//There is a free slot of this name
					}
				}
				else{
					return false;//No such slot
				}
			}
			else{
				return false;//Piece to be fit is of unexpected type
			}
		}
		else{
			return false;
		}
	}
	private void addAnOccupiedObjectSlot(String slotNameSimple, PieceProjection pieceProjection) {
		if(occupiedObjectSlots.containsKey(slotNameSimple)){
			occupiedObjectSlots.get(slotNameSimple).add(pieceProjection);
		}
		else{
			ArrayList<PieceProjection> singlePieceProjection = new ArrayList<PieceProjection>(Arrays.asList(pieceProjection));
			occupiedObjectSlots.put(slotNameSimple, singlePieceProjection);
		}
	}
	private void addAnOccupiedMethodSlot(String slotNameSimple, PieceProjection pieceProjection) {
		if(occupiedMethodSlots.containsKey(slotNameSimple)){
			occupiedMethodSlots.get(slotNameSimple).add(pieceProjection);
		}
		else{
			ArrayList<PieceProjection> singlePieceProjection = new ArrayList<PieceProjection>(Arrays.asList(pieceProjection));
			occupiedMethodSlots.put(slotNameSimple, singlePieceProjection);
		}
	}
	//Before hosting a piece with a slot, both thisPiece.checkFitOf() and thatPiece.checkFitTo() must return true
	public boolean checkFitOf(String slotNameSimple, PieceProjection pieceProjection){//Check whether something fits to this slot
		//Make sure that the code here is complete
		if(pieceProjection == this){
			return false;
		}
		if(this.traceSlotPath() == null){//This means that this PieceProjection is not on the Piece tree yet
			return false;//Cannot host when not on Piece tree
		}
		if(pieceProjection.getHostSlot() != null){
			return false;//Cannot fit because piece to be fit already has a host
		}
		else{
			//Check whether or not the slot shape is correct
			if(ObjectPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//Whether it is a projection of Object-type piece
				if(freeObjectSlots.containsKey(slotNameSimple)){
					if(freeObjectSlots.get(slotNameSimple) == 0){
						return false;//No more free slot of this name
					}
					else{
						return true;//There is a free slot of this name
					}
				}
				else{
					return false;//No such slot
				}
			}
			else if(MethodPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//Whether it is a projection of Method-type piece
				if(freeMethodSlots.containsKey(slotNameSimple)){
					if(freeMethodSlots.get(slotNameSimple) == 0){
						return false;//No more free slot of this name
					}
					else{
						return true;//There is a free slot of this name
					}
				}
				else{
					return false;//No such slot
				}
			}
			else{
				return false;//Piece to be fit is of unexpected type
			}
		}
	}
	//The slotPath of the host slot of this PieceProjection
	public SlotPath traceSlotPath(){
		if(this.origin == null){
			return null;
		}
		else if(this.origin == PieceTreeCompiler.getRootClass()){//Not confirmed to work
			return new SlotPath("_ROOTSLOT");
		}
		else if(this.hostPiece == null){
			return null;
		}
		else if(this.hostPiece.getOrigin() == null){
			return null;
		}
		else{

			System.out.println("Flag: " + this.getOrigin().getSimpleName() + " has host " + 
					this.hostPiece.getOrigin().getSimpleName());
			if(this.hostPiece.traceSlotPath() == null){
				return null;
			}
			else if(this.hostSlot == null){
				return null;
			}
			else{
				ArrayList<String> previousPiecePath = this.hostPiece.traceSlotPath().getPiecePath();
				ArrayList<String> currentPiecePath = (ArrayList<String>) previousPiecePath.clone();
				currentPiecePath.add(this.getHostPiece().getOrigin().getSimpleName());
				return new SlotPath(currentPiecePath, this.hostSlot);
			}
		}
	}
	//The slotPath of a slot on this PieceProjection
	public SlotPath traceSlotPathOnThis(String simpleSlotName){
		SlotPath hostSlotPath = this.traceSlotPath();
		if(hostSlotPath == null){
			return null;
		}
		else{
			ArrayList<String> previousPiecePath = hostSlotPath.getPiecePath();
			ArrayList<String> currentPiecePath = (ArrayList<String>) previousPiecePath.clone();
			currentPiecePath.add(this.getOrigin().getSimpleName());
			return new SlotPath(currentPiecePath, simpleSlotName);
		}
	}
	public boolean checkFitTo(SlotPath slotPath){//Check whether this piece fits to some slot
		if(this.hostPiece != null){
			return false;//It already has a host piece
		}
		else if(this.hostSlot != null){
			return false;//It already occupies a host slot
		}
		else if(this.origin == null){
			return false;//Somehow it does not have an origin; This should not happen.
		}
		else{
			for(SlotPath possibleFit : possibleFits){
				if(possibleFit.equals(slotPath)){
					return true;
				}
			}
			return false;
		}
	}
	public boolean attemptToHostInMegaSlot(PieceProjection pieceProjection){
		if(pieceProjection.checkFitToMegaSlot(this)){
			if(ObjectPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//This piece is an ObjectPiece
				pieceProjection.setHostPiece(this);
				pieceProjection.setHostSlot("_OBJECTMEGASLOT");
				this.objectMegaSlot.add(pieceProjection);//The piece needs to be on the tree first,
				//before we try to fill the slots
				if(PieceTreeCompiler.tryToFillAllSlots(pieceProjection) == false){
					//but if it turns out we cannot load it, we have to remove it.
					pieceProjection.setHostPiece(null);
					pieceProjection.setHostSlot(null);
					this.objectMegaSlot.remove(pieceProjection);
				}
				return true;
			}
			else if(MethodPiece.class.isAssignableFrom(pieceProjection.getOrigin())){//This piece is a MethodPiece
				pieceProjection.setHostPiece(this);
				pieceProjection.setHostSlot("_METHODMEGASLOT");
				this.methodMegaSlot.add(pieceProjection);//The piece needs to be on the tree first,
				//before we try to fill the slots
				if(PieceTreeCompiler.tryToFillAllSlots(pieceProjection) == false){
					//but if it turns out we cannot load it, we have to remove it.
					pieceProjection.setHostPiece(null);
					pieceProjection.setHostSlot(null);
					this.methodMegaSlot.remove(pieceProjection);
				}
				return true;
			}
			else{//This piece is neither an ObjectPiece nor a MethodPiece. This should not happen.
				return false;
			}
		}
		else{
			return false;
		}
	}
	public boolean checkFitToMegaSlot(PieceProjection megaSlotHost){//Check whether this piece fits to the
		//megaslot of some PieceProjection
		if(megaSlotHost.traceSlotPath() == null){//This means that the host is not on the Piece tree yet
			return false;//Cannot host when not on Piece tree
		}
		if(this.hostPiece != null){
			return false;//It already has a host piece
		}
		else if(this.hostSlot != null){
			return false;//It already occupies a host slot
		}
		else if(this.origin == null){
			return false;//Somehow it does not have an origin; This should not happen.
		}
		if(ObjectPiece.class.isAssignableFrom(this.origin)){//This piece is an ObjectPiece
			SlotPath megaSlotPath = megaSlotHost.traceSlotPathOnThis("_OBJECTMEGASLOT");
			for(SlotPath possibleFit : possibleFits){
				if(possibleFit.equals(megaSlotPath)){
					return true;
				}
			}
			return false;
		}
		else if(MethodPiece.class.isAssignableFrom(this.origin)){//This piece is a MethodPiece
			SlotPath megaSlotPath = megaSlotHost.traceSlotPathOnThis("_METHODMEGASLOT");
			for(SlotPath possibleFit : possibleFits){
				if(possibleFit.equals(megaSlotPath)){
					return true;
				}
			}
			return false;
		}
		else{//This piece is neither an ObjectPiece nor a MethodPiece. This should not happen.
			return false;
		}
	}
	//Getters and setters
	public Class<Piece> getOrigin() {
		return origin;
	}
	public void setOrigin(Class<Piece> origin) {
		this.origin = origin;
	}
	public PieceProjection getHostPiece() {
		return hostPiece;
	}
	public void setHostPiece(PieceProjection hostPiece) {
		this.hostPiece = hostPiece;
	}
	public String getHostSlot() {
		return hostSlot;
	}
	public void setHostSlot(String hostSlot) {
		this.hostSlot = hostSlot;
	}
	public Map<String, Integer> getFreeObjectSlots() {
		return freeObjectSlots;
	}
	public void setFreeObjectSlots(Map<String, Integer> freeObjectSlots) {
		this.freeObjectSlots = freeObjectSlots;
	}
	public Map<String, Integer> getFreeMethodSlots() {
		return freeMethodSlots;
	}
	public void setFreeMethodSlots(Map<String, Integer> freeMethodSlots) {
		this.freeMethodSlots = freeMethodSlots;
	}
	public Map<String, ArrayList<PieceProjection> > getOccupiedObjectSlots() {
		return occupiedObjectSlots;
	}
	public void setOccupiedObjectSlots(Map<String, ArrayList<PieceProjection> > occupiedObjectSlots) {
		this.occupiedObjectSlots = occupiedObjectSlots;
	}
	public Map<String, ArrayList<PieceProjection> > getOccupiedMethodSlots() {
		return occupiedMethodSlots;
	}
	public void setOccupiedMethodSlots(Map<String, ArrayList<PieceProjection> > occupiedMethodSlots) {
		this.occupiedMethodSlots = occupiedMethodSlots;
	}
	public ArrayList<PieceProjection> getObjectMegaSlot() {
		return objectMegaSlot;
	}
	public void setObjectMegaSlot(ArrayList<PieceProjection> objectMegaSlot) {
		this.objectMegaSlot = objectMegaSlot;
	}
	public ArrayList<PieceProjection> getMethodMegaSlot() {
		return methodMegaSlot;
	}
	public void setMethodMegaSlot(ArrayList<PieceProjection> methodMegaSlot) {
		this.methodMegaSlot = methodMegaSlot;
	}
	public ArrayList<SlotPath> getPossibleFits() {
		return possibleFits;
	}
	public void setPossibleFits(ArrayList<SlotPath> possibleFits) {
		this.possibleFits = possibleFits;
	}
	public ArrayList<String> getDependentPieceNames() {
		return dependentPieceNames;
	}
	public void setDependentPieceNames(ArrayList<String> dependentPieceNames) {
		this.dependentPieceNames = dependentPieceNames;
	}
}
