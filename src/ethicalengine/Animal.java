package ethicalengine;

public class Animal extends Persona{
	
	private String species = "";
	private boolean isPet;
	
	
	public Animal() {}
	/**
	 * Constructor that accepts one param
	 * @param String species 
	 */
	public Animal(String species) {
		this.setSpecies(species);
	}
	/**
	 * Copy constructor to have properties copied into this instance 
	 * @param Animal otherAnimal
	 */
	public Animal(Animal otherAnimal) {
		super.setAge(otherAnimal.getAge());
		super.setGender(otherAnimal.getGender());
		super.setBodyType(otherAnimal.getBodyType());
		this.setSpecies(otherAnimal.getSpecies());
		this.setPet(otherAnimal.isPet());
	}
	/**
	 * Getter for species property
	 * @return String 
	 */
	public String getSpecies() {
		return species;
	}
	
	/**
	 * Setter for species
	 * @param String
	 */
	public void setSpecies(String species) {
		this.species = species;
	}
	/**
	 * Setter for isPet
	 * @param boolean
	 */
	public void setPet(boolean isPet) {
		this.isPet = isPet;
	}
	
	/**
	 * Getter for isPet property
	 * @return boolean
	 */
	public boolean isPet() {
		return this.isPet;
	}
	
	/**
	 * toString method for printing
	 * @return String
	 */
	public String toString() {
		String returnVal = "";	
		returnVal += this.getSpecies().toLowerCase() + " ";
		
		if (this.isPet()) {
			returnVal += "is pet ";
		}
//		returnVal += returnVal.substring(0, returnVal.length() - 1);  
		return returnVal.trim();
	}
}
