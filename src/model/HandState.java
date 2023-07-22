package model;


// State Design Pattern
public interface HandState {

	String split();
	String surrender();
	String stand();
	String hit();
	String doubleDown();
}
