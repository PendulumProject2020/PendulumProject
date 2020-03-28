package main;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Application;
import pieceTreeDisplayPack.PieceTreeDisplay;

public class World {

	private static boolean userInteraction = false;
	//Whether the event handlers of the UI will respond to user actions
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*test1();
		test1();
		test2();
		test3();
		test4();
		test5();
		test6();*/
		test7();
		begin();
	}
	
	public static void begin(){//Let PieceTreeCompiler finish compiling all pieces before this
		Method run;
		try {
			run = PieceTreeCompiler.getRootClass().getMethod("run");
			run.invoke(null);
		} catch (NoSuchMethodException | SecurityException e) {
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
	
	private static void test1(){
		PieceTreeCompiler.findAndAddObjectPieces("main.ObjectPieceTestA", 
				"main.ObjectPieceTestB", "main.ObjectPieceTestC");
		System.out.println("The added Object Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getObjectPieceList()){
			System.out.println(oP.getSimpleName());
		}
	}
	
	private static void test2(){
		PieceTreeCompiler.findAndAddMethodPieces("main.MethodPieceTestA", 
				"main.MethodPieceTestB", "main.ObjectPieceTestA");
		System.out.println("The added Method Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getMethodPieceList()){
			System.out.println(oP.getSimpleName());
		}
	}
	
	private static void test3(){
		if(PieceTreeCompiler.loadPieceTree("MethodPieceTestA") == true){
			System.out.println("test3 successful");
		}
		else{
			System.out.println("test3 not successful");
		}
	}
	
	private static void test4(){
		PieceTreeCompiler.findAndAddObjectPieces("testPack1.TestO1", 
				"testPack1.TestO2", "testPack1.TestO3", "testPack1.TestO4");
		System.out.println("The added Object Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getObjectPieceList()){
			System.out.println(oP.getSimpleName());
		}
	}
	
	private static void test5(){
		PieceTreeCompiler.findAndAddMethodPieces("testPack1.TestM1", 
				"testPack1.TestM2", "testPack1.TestM3", "testPack1.TestM4");
		System.out.println("The added Method Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getMethodPieceList()){
			System.out.println(oP.getSimpleName());
		}
	}
	
	private static void test6(){
		if(PieceTreeCompiler.loadPieceTree("TestM1") == true){
			System.out.println("test6 preliminarily successful");
			Application.launch(PieceTreeDisplay.class, (String[]) null); 
		}
		else{
			System.out.println("test6 not successful");
		}
	}
	
	private static void test7(){
		PieceTreeCompiler.findAndAddObjectPieces("chronosPack.Agent", 
				"chronosPack.RoundSignallerContinuous", "fairyChessPack1.Epifyte",
				"fairyChessPack1.EpifyteModifier", "fairyChessPack1.EpifyteArm");
		System.out.println("The added Object Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getObjectPieceList()){
			System.out.println(oP.getSimpleName());
		}
		PieceTreeCompiler.findAndAddMethodPieces("chronosPack.Chronos", "chronosPack.InitializerDefault",
				"fairyChessPack1.BindingValidityEvaluator", "fairyChessPack1.DetachmentValidityEvaluator",
				"fairyChessPack1.EpifyteDisplayEventInterpreter");
		System.out.println("The added Method Pieces are: ");
		for(Class<Piece> oP : PieceTreeCompiler.getMethodPieceList()){
			System.out.println(oP.getSimpleName());
		}
		if(PieceTreeCompiler.loadPieceTree("Chronos") == true){
			System.out.println("test7 preliminarily successful");
			//Application.launch(PieceTreeDisplay.class, (String[]) null); 
		}
		else{
			System.out.println("test7 not successful");
		}
	}

	public static boolean isUserInteraction() {
		return userInteraction;
	}

	public static void setUserInteraction(boolean userInteraction) {
		World.userInteraction = userInteraction;
	}

}
