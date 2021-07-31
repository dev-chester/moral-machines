package ethicalengine;


public class Human extends Persona {
	
	public enum Profession {
		DOCTOR, CEO, CRIMINAL, HOMELESS, UNEMPLOYED, NONE
	};
	
	public enum AgeCategory {
		BABY, CHILD, ADULT, SENIOR
	}

	private Profession profession;
	private boolean isPregnant;
	private boolean isYou;
	
	public Human() {}
	
	/**
	 * Constructor for Human with five params
	 * @param int
	 * @param Profession
	 * @param Gender
	 * @param BodyType
	 * @param boolean 
	 */
	public Human(int age, Profession profession,Gender gender, BodyType bodyType, 
			  boolean isPregnant) {
		super.setAge(age);
		super.setBodyType(bodyType);
		super.setGender(gender);
		this.profession = profession;
		this.isPregnant = isPregnant;
	}
		
	/**
	 * Constructor for Human with three params
	 * @param int
	 * @param Gender
	 * @param BodyType
	 */
	public Human(int age, Gender gender, BodyType bodyType) {
		super.setAge(age);
		super.setBodyType(bodyType);
		super.setGender(gender);
	}
	
	/**
	 * Copy constructor for Human with five properties params
	 * @param Human
	 */
	public Human(Human otherHuman) {
		super.setAge(otherHuman.getAge());
		super.setGender(otherHuman.getGender());
		super.setBodyType(otherHuman.getBodyType());
		this.setProfession(otherHuman.getProfession());
		this.setPregnant(otherHuman.isPregnant());
	}
	

	/**
	 * Getter for AgeCategory
	 * @return AgeCategory
	 */
	public AgeCategory getAgeCategory() {
		if (super.getAge() >= 0 && super.getAge() <= 4) {
			return AgeCategory.BABY;
		}
		if (super.getAge() >= 5 && super.getAge() <= 16) {
			return AgeCategory.CHILD;
		}
		if (super.getAge() >= 17 && super.getAge() <= 68) {
			return AgeCategory.ADULT;
		}
		return AgeCategory.SENIOR;
	}
	
	/**
	 * Getter for Profession
	 * @return Profession
	 */
	public Profession getProfession() {
		if (this.getAgeCategory() == AgeCategory.ADULT) {
			return profession;
		}
		return Profession.NONE;
	}
	

	/**
	 * Setter for Profession
	 * @return profession
	 */
	public void setProfession(Profession profession) {
		this.profession = profession;
	}

	/**
	 * Getter for isPregnant
	 * @return boolean
	 */
	public boolean isPregnant() {
		return isPregnant;
	}

	/**
	 * Setter for isPregnant
	 * @param boolean
	 */
	public void setPregnant(boolean isPregnant) {
		if (super.getGender() == Gender.FEMALE) {
			this.isPregnant = isPregnant;
			return;
		}
		this.isPregnant = false;
		
	}



	/**
	 * Setter for isYou
	 * @param boolean
	 */
	public void setAsYou(boolean isYou) {
		this.isYou = isYou;
	}
	/**
	 * Setter for gender
	 * @param Gender
	 */
	public void setGender(Gender gender) {
		super.setGender(gender);
	}
	
	/**
	 * toString for printing
	 * @return String
	 */
	public String toString() {
		String returnVal = "";
		if (isYou) {
			returnVal += "you ";
		}
		returnVal += super.getBodyType().toString().toLowerCase() + " ";
		returnVal += this.getAgeCategory().toString().toLowerCase() + " ";
		if (this.getAgeCategory() == AgeCategory.ADULT) {
			returnVal += this.getProfession().toString().toLowerCase() + " ";
		}
		returnVal += super.getGender().toString().toLowerCase() + " ";
		if (super.getGender() == Gender.FEMALE) {
			returnVal += (this.isPregnant()) ? "pregnant " : "";
		}
		returnVal = returnVal.substring(0, returnVal.length() - 1);  
		return returnVal;
	}

	
	
}
