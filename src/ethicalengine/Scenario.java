package ethicalengine;

import java.util.ArrayList;

public class Scenario {

	public static final String NEWLINE = "\n";
	
	private boolean youInCar;
	private boolean youInLane;
	private boolean isLegalCrossing;
	private Persona[] passengers;
	private Persona[] pedestrians;
	
	
    public Scenario() {}
    
    /**
     * Constructor that accepts three params
     * @param Persona[]
     * @param Persona[]
     * @param boolean
     */
    public Scenario(Persona[] passengers, Persona[] pedestrians, boolean isLegalCrossing) {
    	this.passengers = passengers.clone();
    	this.pedestrians = pedestrians.clone();
    	this.setLegalCrossing(isLegalCrossing);
    }
    
    /**
     * Getter for youInCar property
     * @return boolean
     */
    public boolean hasYouInCar() {
    	return this.youInCar;
    }
    
    /**
     * Getter for hasYouInLane property
     * @return boolean
     */
    public boolean hasYouInLane() {
    	return this.youInLane;
    }
    
    /**
     * Setter for isLegalCrossing property
     * @param isLegalCrossing
     */
    public void setLegalCrossing(boolean isLegalCrossing) {
    	this.isLegalCrossing = isLegalCrossing;
    }
    
    /**
     * Getter for isLegalCrossing property
     * @return boolean
     */
    public boolean isLegalCrossing() {
    	return this.isLegalCrossing;
    }
    
    /**
     * Getter for passengers
     * @return Persona[]
     */
    public Persona[] getPassengers() {
		return this.passengers;	
    }
    /**
     * Getter for pedestrians
     * @return Persona[]
     */
    public Persona[] getPedestrians() {
    	return this.pedestrians;
    }
    
    /**
     * Getter for passengers count
     * @return int
     */
    public int getPassengerCount() {
    	return passengers.length;
    }
    
    /**
     * Getter for pedestrian count
     * @return int
     */
    public int getPedestrianCount() {
    	return pedestrians.length;
    }
    
    /**
     * toString for printing
     * @return String
     */
    public String toString(){
    	String returnVal = "======================================" + NEWLINE;
    		returnVal += "# Scenario" + NEWLINE;
    		returnVal += "======================================" + NEWLINE;
    		returnVal += "Legal Crossing: " + (this.isLegalCrossing() ? "yes" : "no" ) + NEWLINE;
    		returnVal += "Passengers (" + this.getPassengerCount() + ")" + NEWLINE;
    		for (Persona n : this.passengers) {
        		returnVal += "- " + n.toString() + NEWLINE;
        	}
    		returnVal += "Pedestrians (" + this.getPedestrianCount() + ")" + NEWLINE;
    		for (Persona p : this.pedestrians) {
        		returnVal +=  "- " + p.toString() + NEWLINE;
        	}
    		

    	
    	return returnVal.trim();
    }
    
}