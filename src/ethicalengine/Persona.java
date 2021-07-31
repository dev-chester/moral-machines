package ethicalengine;
/**
 * An abstract class in which Human and Animal will depend on its base properties
 * @author Camille Sayoc 
 */
public abstract class Persona {
	
	public enum Gender {
		FEMALE, MALE, UNKNOWN
	};
	public enum BodyType {
		 AVERAGE, ATHLETIC, OVERWEIGHT, UNSPECIFIED
	};
	
	private int age;
	private Gender gender;
	private BodyType bodyType;
	
	/**
	 * Default constructor will set all properties to default 
	 */
	public Persona() {
		this.age = 0;
		this.gender = Gender.UNKNOWN;
		this.bodyType = BodyType.UNSPECIFIED;
	}
	
	/**
	 * Constructor with params
	 * @param int age
	 * @param Gender gender
	 * @param BodyType bodyType
	 **/
	public Persona(int age, Gender gender, BodyType bodyType) {
		this.age = age;
		this.gender = gender;
		this.bodyType = bodyType;
	}
	
	/**
	 * Copy constructor, deconstruct passed argument and set its properties 
	 * @param Persona otherPersona
	 */
	public Persona(Persona otherPersona) {
		this.age = otherPersona.age;
		this.bodyType = otherPersona.bodyType;
		this.gender = otherPersona.gender;
	}
	
	/**
     * This method is used to get age. 
     * @return int age
     */
	public int getAge() {
		return age;
	}
	
	/**
     * This method is used to set age with default of 0 if param is negative int value. 
     * @param int age
     */
	public void setAge(int age) {
		if (age >= 0) {
			this.age = age;
			return;
		}
	}
	
	/**
     * This method is used to get Gender enum. 
     * @return Gender
     */
	public Gender getGender() {
		return gender;
		 
	}
	
	/**
     * This method is used to set Gender property based on Gender enum. 
     * @param Gender
     */
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	/**
     * This method is used to get BodyType enum. 
     * @return BodyType
     */
	public BodyType getBodyType() {
		return bodyType;
	}

	/**
     * This method is used to set BodyType property based on BodyType enum. 
     * @param BodyType
     */
	public void setBodyType(BodyType bodyType) {
		this.bodyType = bodyType;
	}
	
}
