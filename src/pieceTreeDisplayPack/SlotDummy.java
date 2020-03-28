package pieceTreeDisplayPack;

import java.util.ArrayList;

import main.PieceProjection;
import java.lang.Math.*;

public class SlotDummy {
	
	private PieceTreeDisplay pieceTreeDisplay;
	private PieceProjection member;
	private SlotDummy parentDummy;
	private Integer a;//Number of sibling nodes including itself
	private Integer b;//The index among its sibling nodes
	private double angle;//radians
	private double angularHalfwidth;//radians
	private double x;
	private double y;
	private Integer distanceFromRootPiece;
	private SlotType slotType;
	private String slotDescription;
	private String memberDescription = "-";
	private static double squareBoxHalfWidth = 20;
	private static double squareFilledHalfWidth = 14;
	private static double circleBoxRadius = 20;
	private static double circleFilledRadius = 14;
	private static String fontName = "Century Gothic";
	private static double fontSize = 12.0;
	private static double textHeight = 10.0;
	private static double textToSlotSpacing = 4.0;
	
	public SlotDummy(PieceTreeDisplay pieceTreeDisplay, SlotDummy parentDummy, PieceProjection member,
			Integer a, Integer b, String slotDiscription, SlotType slotType){
		this.pieceTreeDisplay = pieceTreeDisplay;
		this.parentDummy = parentDummy;
		if(member == null){
			this.member = null;
			this.memberDescription = "(empty)";
		}
		else{
			this.member = member;
			this.memberDescription = member.getOrigin().getSimpleName();
		}
		this.a = a;
		this.b = b;
		if(parentDummy == null){
			this.distanceFromRootPiece = 0;
		}
		else{
			this.distanceFromRootPiece = parentDummy.getDistanceFromRootPiece() + 1;
		}
		if(parentDummy == null){
			this.angle = 0.0;
			this.angularHalfwidth = Math.PI;
		}
		else{
			if(a == 1){
				this.angle = parentDummy.getAngle();
				this.angularHalfwidth = parentDummy.getAngularHalfwidth();
			}
			else{
				if(distanceFromRootPiece == 1){
				this.angle = parentDummy.getAngle() - parentDummy.getAngularHalfwidth() + 
						((double) (b)/ (double) (a))*2.0*parentDummy.getAngularHalfwidth();
				}
				else{
					this.angle = parentDummy.getAngle() - parentDummy.getAngularHalfwidth() + 
						((double) (b-1)/ (double) (a-1))*2.0*parentDummy.getAngularHalfwidth();
				}
				this.angularHalfwidth = (2.0*parentDummy.getAngularHalfwidth())/(3.0*((double) a));
			}
		}
		
		this.slotDescription = slotDiscription;
		this.slotType = slotType;
		
		x = Math.cos(angle)*pieceTreeDisplay.getRadius()*this.distanceFromRootPiece;
		y = Math.sin(angle)*pieceTreeDisplay.getRadius()*this.distanceFromRootPiece;
		if(this.getParentDummy() != null){
		System.out.println("Slot dummy " + slotDescription + " with parent dummy "
		+ this.getParentDummy().getSlotDescription() + " and coordinates x = " + Double.toString(x) 
		+ ", y = " + Double.toString(y) + " created.");
		}
		else{
			System.out.println("Slot dummy " + slotDescription + " with coordinates x = " + Double.toString(x) 
				+ ", y = " + Double.toString(y) + " created.");
		}
	}
	
	public PieceTreeDisplay getPieceTreeDisplay() {
		return pieceTreeDisplay;
	}
	public void setPieceTreeDisplay(PieceTreeDisplay pieceTreeDisplay) {
		this.pieceTreeDisplay = pieceTreeDisplay;
	}
	public SlotDummy getParentDummy() {
		return parentDummy;
	}
	public void setParentDummy(SlotDummy parentDummy) {
		this.parentDummy = parentDummy;
	}
	public double getAngle() {
		return angle;
	}
	public void setAngle(double angle) {
		this.angle = angle;
	}
	public double getAngularHalfwidth() {
		return angularHalfwidth;
	}
	public void setAngularHalfwidth(double angularHalfwidth) {
		this.angularHalfwidth = angularHalfwidth;
	}
	public double getX() {
		return x;
	}
	public void setX(double x) {
		this.x = x;
	}
	public double getY() {
		return y;
	}
	public void setY(double y) {
		this.y = y;
	}
	public SlotType getSlotType() {
		return slotType;
	}
	public void setSlotType(SlotType slotType) {
		this.slotType = slotType;
	}
	public String getSlotDescription() {
		return slotDescription;
	}
	public void setSlotDescription(String slotDescription) {
		this.slotDescription = slotDescription;
	}
	public String getMemberDescription() {
		return memberDescription;
	}
	public void setMemberDescription(String memberDescription) {
		this.memberDescription = memberDescription;
	}

	public PieceProjection getMember() {
		return member;
	}

	public void setMember(PieceProjection member) {
		this.member = member;
	}

	public Integer getA() {
		return a;
	}

	public void setA(Integer a) {
		this.a = a;
	}

	public Integer getB() {
		return b;
	}

	public void setB(Integer b) {
		this.b = b;
	}

	public static double getSquareBoxHalfWidth() {
		return squareBoxHalfWidth;
	}

	public static void setSquareBoxHalfWidth(double squareBoxHalfWidth) {
		SlotDummy.squareBoxHalfWidth = squareBoxHalfWidth;
	}

	public static double getSquareFilledHalfWidth() {
		return squareFilledHalfWidth;
	}

	public static void setSquareFilledHalfWidth(double squareFilledHalfWidth) {
		SlotDummy.squareFilledHalfWidth = squareFilledHalfWidth;
	}

	public static double getCircleBoxRadius() {
		return circleBoxRadius;
	}

	public static void setCircleBoxRadius(double circleBoxRadius) {
		SlotDummy.circleBoxRadius = circleBoxRadius;
	}

	public static double getCircleFilledRadius() {
		return circleFilledRadius;
	}

	public static void setCircleFilledRadius(double circleFilledRadius) {
		SlotDummy.circleFilledRadius = circleFilledRadius;
	}

	public Integer getDistanceFromRootPiece() {
		return distanceFromRootPiece;
	}

	public void setDistanceFromRootPiece(Integer distanceFromRootPiece) {
		this.distanceFromRootPiece = distanceFromRootPiece;
	}

	public static String getFontName() {
		return fontName;
	}

	public static void setFontName(String fontName) {
		SlotDummy.fontName = fontName;
	}

	public static double getFontSize() {
		return fontSize;
	}

	public static void setFontSize(double fontSize) {
		SlotDummy.fontSize = fontSize;
	}

	public static double getTextHeight() {
		return textHeight;
	}

	public static void setTextHeight(double textHeight) {
		SlotDummy.textHeight = textHeight;
	}

	public static double getTextToSlotSpacing() {
		return textToSlotSpacing;
	}

	public static void setTextToSlotSpacing(double textToSlotSpacing) {
		SlotDummy.textToSlotSpacing = textToSlotSpacing;
	}
	
}
