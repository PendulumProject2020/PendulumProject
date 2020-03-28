package chronosPack;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import main.ObjectPiece;
import main.PieceTreeCompiler;
import main.World;

public class RoundController extends Agent{
	private ArrayList<Agent> roundControlTargets = new ArrayList<Agent>();
	private Queue<Agent> targetQueue = new LinkedList<Agent>();
	private boolean turnOngoing = false;
	public RoundController(){
		this.setActualClass(RoundController.class);
		this.setDefaultCommandExecutor();
		this.setDefaultInstanceInformationEvaluator();
		this.setDefaultRoundExecutor();
	}
	@Override
	public void setDefaultCommandExecutor(){
		//To be overridden
		this.setCommandExecutor((target, command, dataSet) -> {
			if(command.equals("executeRound")){
				if(target.getActualClass() == Agent.class){
					try {
						Agent.class.getMethod("executeRound").invoke(target);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
	}
	
	@Override
	public void setDefaultInstanceInformationEvaluator(){
		//To be overridden
		this.setInstanceInformationEvaluator((target, command, dataSet) -> {
			return null;
		});
	}
	
	public void setDefaultRoundExecutor(){
		//To be overridden
		this.setRoundExecutor(() -> {
			turnOngoing = true;
			targetQueue = new LinkedList<Agent>();
			for(Agent agent : roundControlTargets){
				targetQueue.add(agent);
			}
			System.out.println("RoundController: size of targetQueue: " + Integer.toString(targetQueue.size()));
			proceedToNextTarget();
		});
	}
	public void proceedToNextTarget(){
		System.out.println("RoundController: proceedToNextTarget begins.");
		if(turnOngoing){
			World.setUserInteraction(false);
			if(targetQueue.isEmpty() == true){
				System.out.println("RoundController: targetQueue is empty.");
				turnOngoing = false;
				Chronos.proceedToNextController();
			}
			else{
			Agent element = targetQueue.element();
			System.out.println("RoundController: removing an element from targetQueue.");
			targetQueue.remove();
			if(targetQueue.isEmpty() == false){
				System.out.println("RoundController: targetQueue is not empty.");
			}
			System.out.println("RoundController: size of targetQueue: " + Integer.toString(targetQueue.size()));
			element.executeRound();
			System.out.println("RoundController: element has finished executing round.");
			}
		}
	}
	public ArrayList<Agent> getRoundControlTargets() {
		return roundControlTargets;
	}
	public void setRoundControlTargets(ArrayList<Agent> roundControlTargets) {
		this.roundControlTargets = roundControlTargets;
	}
	public void addRoundControlTarget(Agent roundControlTarget){
		roundControlTargets.add(roundControlTarget);
		roundControlTarget.addParentController(this);
	}
	public Queue<Agent> getTargetQueue() {
		return targetQueue;
	}
	public void setTargetQueue(Queue<Agent> targetQueue) {
		this.targetQueue = targetQueue;
	}
	public boolean isTurnOngoing() {
		return turnOngoing;
	}
	public void setTurnOngoing(boolean turnOngoing) {
		this.turnOngoing = turnOngoing;
	}
}
