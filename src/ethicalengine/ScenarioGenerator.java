package ethicalengine;

import java.util.Random;

public class ScenarioGenerator {

	private long seed;
	private int passengerCountMinimum = 1;
	private int pedestrianCountMinimum = 1;
	private int passengerCountMaximum = 5;
	private int pedestrianCountMaximum = 5;
	private Random randomno = new Random();

	/**
	 * Default constructor 
	 */
	public ScenarioGenerator() {
		randomno.setSeed(new Random().nextLong());
		this.seed = randomno.nextInt();
	}
	
	/**
	 * Constructor with one param
	 * @param long
	 */
	public ScenarioGenerator(long seed) {
		this.seed = seed;
		this.randomno.setSeed(seed);
	}
	
	/**
	 * Constructor with five params
	 * @param long
	 * @param int
	 * @param int
	 * @param int
	 * @param int
	 */
	public ScenarioGenerator(long seed, int passengerCountMinimum, int passengerCountMaximum, int pedestrianCountMinimum, int pedestrianCountMaximum) {
		this.setSeed(seed);
		this.randomno.setSeed(seed);
		this.setPassengerCountMin(passengerCountMinimum);
		this.setPedestrianCountMin(pedestrianCountMinimum);
		this.setPassengerCountMax(passengerCountMaximum);
		this.setPedestrianCountMax(pedestrianCountMaximum);
	}
	
	/**
	 * Getter for seed
	 * @return long 
	 */
	public long getSeed() {
		return seed;
	}

	/**
	 * Setter method for seed
	 * @param long 
	 */
	public void setSeed(long seed) {
		this.seed = seed;
	}

	/**
	 * Getter method of getting random Human instance
	 * @return Human 
	 */
	public Human getRandomHuman() {
		int randomAge;
		Human.Profession randomProfession;
		Persona.BodyType randomBodyType;
		Persona.Gender randomGender;
		boolean randomPregnant;
		
		randomAge = this.randomno.nextInt((150 - 0) + 1 );
		if (randomAge >= 17 && randomAge <= 68) {
			randomProfession = Human.Profession.values()[this.randomno.nextInt(Human.Profession.values().length)];
		}else {
			randomProfession = Human.Profession.NONE;
		}
		randomBodyType = Persona.BodyType.values()[this.randomno.nextInt(Persona.BodyType.values().length)];
		randomGender = Persona.Gender.values()[this.randomno.nextInt(Persona.Gender.values().length)];
		if(randomGender.toString() == "MALE") {
			randomPregnant = false;
		} else {
			randomPregnant =  this.randomno.nextBoolean();
		} 
		
		return new Human(randomAge, randomProfession, randomGender, randomBodyType, randomPregnant);
		
	}
	
	/**
	 * generate method use to generate Scenario randomly
	 * @return Scenario
	 */
	public Scenario generate() {

        int passengerCount = this.randomno.nextInt(passengerCountMaximum - passengerCountMinimum + 1)
                + passengerCountMinimum;

        int pedestrianCount = this.randomno.nextInt(pedestrianCountMaximum - pedestrianCountMinimum + 1)
                + pedestrianCountMinimum;

        Persona[] passengers = new Persona[passengerCount];
        Persona[] pedestrians = new Persona[pedestrianCount];

        // 75% generate random human as passenger | 25% generate random animal as passenger
        for (int i = 0;i < passengerCount; i++) {
            if (this.randomno.nextInt(100) <= 75) {
                passengers[i] = this.getRandomHuman();
            }
            else passengers[i] = this.getRandomAnimal();
        }

        // 75% generate random human as pedestrian | 25% generate random animal as pedestrian
        for (int i = 0;i < pedestrianCount; i++) {
            if (this.randomno.nextInt(100) <= 75) {
                pedestrians[i] = this.getRandomHuman();
            }
            else pedestrians[i] = this.getRandomAnimal();
        }

        boolean legal = this.randomno.nextBoolean();

        boolean hasYou = this.randomno.nextBoolean();
        // 50% chance of hasYou in the scenario
        if (hasYou) { 
            int u = this.randomno.nextInt(2);
            if (u == 0) { // isYou in passengers
                for (Persona persona : passengers) {
                    if (persona instanceof Human) {
                        ((Human) persona).setAsYou(true);
                        break;
                    }

                }
            }
            else if (u == 1) { // isYou in pedestrians
                for (Persona persona : pedestrians) {
                    if (persona instanceof Human) {
                        ((Human) persona).setAsYou(true);
                        break;
                    }
                }
            }
        }

        return new Scenario(passengers, pedestrians, legal);
    
	}
	
	/**
	 * getRandomAnimal use to generate random animal 
	 * @return Animal
	 */
	public Animal getRandomAnimal() {
		String[] species = {"dog", "cat", "bird"};
		
		int randomAge; 
		Persona.BodyType randomBodyType;
		Persona.Gender randomGender;
		String randomSpecies;
		boolean randomPet;
		
		randomAge = this.randomno.nextInt((150 - 0) + 1 ); 
		randomBodyType = Persona.BodyType.values()[this.randomno.nextInt(Persona.BodyType.values().length)];
		randomGender = Persona.Gender.values()[this.randomno.nextInt(Persona.Gender.values().length)]; 
		randomSpecies = species[this.randomno.nextInt(species.length)];
		randomPet = this.randomno.nextBoolean();
		
		Animal newRandomAnimal = new Animal();
		newRandomAnimal.setAge(randomAge);
		newRandomAnimal.setBodyType(randomBodyType);
		newRandomAnimal.setGender(randomGender);
		newRandomAnimal.setSpecies(randomSpecies);
		newRandomAnimal.setPet(randomPet);
		
		return newRandomAnimal;
	}
	
	/**
	 * Getter for passenger count minimum property
	 * @return int 
	 */
	public int getPassengerCountMinimum() {
		return passengerCountMinimum;
	}

	/**
	 * Setter for passenger count minimum property
	 * @param int 
	 */
	public void setPassengerCountMin(int passengerCountMinimum) {
		this.passengerCountMinimum = passengerCountMinimum;
	}

	/**
	 * Getter for pedestrian count minimum property
	 * @return int 
	 */
	public int getPedestrianCountMinimum() {
		return pedestrianCountMinimum;
	}

	/**
	 * Setter for pedestrian count minimum property
	 * @param int 
	 */
	public void setPedestrianCountMin(int pedestrianCountMinimum) {
		this.pedestrianCountMinimum = pedestrianCountMinimum;
	}

	/**
	 * Getter for passenger count maximum property
	 * @return int 
	 */
	public int getPassengerCountMaximum() {
		return passengerCountMaximum;
	}

	/**
	 * Setter for passenger count maximum property
	 * @param int 
	 */
	public void setPassengerCountMax(int passengerCountMaximum) {
		this.passengerCountMaximum = passengerCountMaximum;
	}

	/**
	 * Getter for pedestrian count maximum property
	 * @return int 
	 */
	public int getPedestrianCountMaximum() {
		return pedestrianCountMaximum;
	}

	/**
	 * Setter for pedestrian count maximum property
	 * @param int 
	 */
	public void setPedestrianCountMax(int pedestrianCountMaximum) {
		this.pedestrianCountMaximum = pedestrianCountMaximum;
	}

}
