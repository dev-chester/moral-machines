import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import ethicalengine.Animal;
import ethicalengine.Human;
import ethicalengine.Human.Profession;
import ethicalengine.Persona;
import ethicalengine.Persona.BodyType;
import ethicalengine.Persona.Gender;
import ethicalengine.Scenario;

public class EthicalEngine {

	public enum Decision {
		PASSENGERS, PEDESTRIANS
	};

	/**
	 * Decides whether to save the passengers or the pedestrians
	 * 
	 * @param Scenario scenario: the ethical dilemma
	 * @return Decision: which group to save
	 */
	public static Decision decide(Scenario scenario) {
		// a rather random decision engine
		// TOOD: take into account at least 5 scenario characteristics

		// 1. If the difference of count between passengers and pedestrian is 4, save
		// the majority, regardless of other factors
		if (Math.abs(scenario.getPassengerCount() - scenario.getPedestrianCount()) >= 4) {
			return (scenario.getPassengerCount() > scenario.getPedestrianCount() ? Decision.PASSENGERS
					: Decision.PEDESTRIANS);
		}

		// 2. If passengers and pedestrians are in equal human count, count if there is
		// criminal on both; save the group that have less criminal/s
		int countHumanPassenger = 0;
		int countHumanPedestrian = 0;
		int countCriminalPassenger = 0;
		int countCriminalPedestrian = 0;

		for (Persona p : scenario.getPassengers()) {
			if (p.getClass() == Human.class) {
				countHumanPassenger++;
				if (((Human) p).getProfession() == Profession.CRIMINAL) {
					countCriminalPassenger++;
					
				}
			}
		}

		for (Persona d : scenario.getPedestrians()) {
			if (d.getClass() == Human.class) {
				countHumanPedestrian++;
				if (((Human) d).getProfession() == Profession.CRIMINAL) {
					countCriminalPedestrian++;
				}
			}
		}
		if (countCriminalPassenger > countCriminalPedestrian) {
			return Decision.PEDESTRIANS;
		} else if (countCriminalPassenger < countCriminalPedestrian) {
			return Decision.PASSENGERS;
		} else {
			// Do nothing
		}

		// 3. Consider Human Count as basis
		if (countHumanPedestrian > countHumanPassenger) {
			return Decision.PEDESTRIANS;
		}
		if (countHumanPedestrian < countHumanPassenger) {
			return Decision.PASSENGERS;
		}

		// 4. Consider Average Age in the group. save younger on average age group
		int averageAgePassenger = 0;
		int averageAgePedestrian = 0;
		for (Persona p : scenario.getPassengers()) {
			if (p.getClass() == Human.class) {
				averageAgePassenger = averageAgePassenger + p.getAge();
			}
		}

		for (Persona d : scenario.getPedestrians()) {
			if (d.getClass() == Human.class) {
				averageAgePedestrian = averageAgePedestrian + d.getAge();
			}
		}
		
		if (countHumanPassenger == 0 ) {
			return Decision.PEDESTRIANS;
		}
		if (countHumanPedestrian == 0 ) {
			return Decision.PASSENGERS;
		}
		averageAgePassenger = averageAgePassenger / countHumanPassenger;
		averageAgePedestrian = averageAgePedestrian / countHumanPedestrian;

		if (Math.abs(averageAgePassenger - averageAgePedestrian) > 30) {
			if (averageAgePassenger > averageAgePedestrian) {
				return Decision.PEDESTRIANS;
			} else {
				return Decision.PASSENGERS;
			}
		}

		// 5. Number of Pregnant
		int countPassengerPregnant = 0;
		int countPedestrianPregnant = 0;
		for (Persona p : scenario.getPassengers()) {
			if (p.getClass() == Human.class) {
				if (((Human) p).isPregnant()) {
					countPassengerPregnant++;
				}
			}
		}

		for (Persona d : scenario.getPedestrians()) {
			if (d.getClass() == Human.class) {
				if (((Human) d).isPregnant()) {
					countPedestrianPregnant++;
				}
			}
		}

		if (countPassengerPregnant > countPedestrianPregnant)
			return Decision.PASSENGERS;
		if (countPassengerPregnant < countPedestrianPregnant)
			return Decision.PEDESTRIANS;

		// 6. Flip a coin who to save
		if (Math.random() > 0.5) {
			return Decision.PASSENGERS;
		} else {
			return Decision.PEDESTRIANS;
		}
	}

	
	/**
	 * Entry point for EthicalEngine, implementation for all flag options
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		displayMessage();
		if (args.length > 0) {
			int validFlag = isValidFlag(args[0]);
//			boolean isValidConfigFile = isValidConfigFile(args[1]);

			if (validFlag == -1) {
				System.out.print("Invalid command");
			}
			if (validFlag == 1) {
				ArrayList<Scenario> scenarios = readFile(args[1]);
				scenarios.forEach(sc -> {
					System.out.println(sc.toString());
					System.out.print("Who will be saved? ");
					if (decide(sc) == Decision.PASSENGERS) {
						System.out.println("Passengers");
					} else {
						System.out.println("Pedestrians");
					}
				});
			}
			
			if (validFlag == 4 ) {
				System.out.println("\nDo you consent to have your decisions saved to a file? (yes/no) ");
				Scanner kbd = new Scanner(System.in);
				String userConsentSave = kbd.nextLine();
				if (userConsentSave.toLowerCase().equals("yes")|| userConsentSave.toLowerCase().equals("y")) {
					showRandomScenarios(true);
				}
			}
		} else {
			System.out.print("Running default audit");
		}
	}

	/**
	 * Checking of passed argument in command line if valid returns negative value if not.
	 * @param String flag
	 * @return int
	 */
	public static int isValidFlag(String flag) {
		if (flag.equals("--config") || flag.equals("-c"))
			return 1;
		if (flag.equals("--help") || flag.equals("-h"))
			return 2;
		if (flag.equals("--results") || flag.equals("-r"))
			return 3;
		if (flag.equals("--interactive") || flag.equals("-i"))
			return 4;
		return -1;
	}

	/**
	 * Method checking if path provided is valid and if the file is existing, return true if valid
	 * @throws FileNotFoundException
	 * @param String path 
	 */
	public static boolean isValidConfigFile(String path) throws FileNotFoundException {
		String line = "";
		String splitBy = ",";

		File f = new File(path);
		if (f.exists() && !f.isDirectory()) {
			return true;
		}
		throw new FileNotFoundException();

	}

	/**
	 * Read a valid and existing file with creation of scenario based on csv.
	 * @return ArrayList<Scenario>
	 * @param String path
	 * @throws IOException
	 */
	public static ArrayList<Scenario> readFile(String path) throws IOException {

		String line = "";
		String splitBy = ",";
		boolean isFirstLine = true;
		ArrayList<Scenario> scenarios = new ArrayList<Scenario>();

		// Allocate array of Persona with allowance size of 10
		Persona[] pass = new Persona[10];
		Persona[] ped = new Persona[10];

		//Read the file
		BufferedReader br = new BufferedReader(new FileReader(path));
		
		Scenario sc = null;
		
		boolean isLegal = false;
		int psIndex = 0;
		int pdIndex = 0;
		
		// Reading of csv is line by line
		while ((line = br.readLine()) != null) {
			// Do not process firstline / Column names
			if (isFirstLine) {
				isFirstLine = false;
				continue;
			}
			// Convert string line into string array words, splitting by comma
			String[] elements = line.split(splitBy); 
			
			// Determine if only one word is in the line ( is Legal or not )
			if (elements.length == 1) {
				
				// Evaluate Scenario if there is pending Scenario and current line is color legal, then save the current scenario
				if (sc != null) {

					List<Persona> items = new ArrayList<Persona>(pass.length);
					for (Persona input : pass) {
						if (input != null) {
							items.add(input);
						}
					}
					Persona[] finalPassengers = items.toArray(new Persona[items.size()]);

					List<Persona> items2 = new ArrayList<Persona>(ped.length);
					for (Persona input : ped) {
						if (input != null) {
							items2.add(input);
						}
					}
					Persona[] finalPedestrians = items2.toArray(new Persona[items2.size()]);

					scenarios.add(new Scenario(finalPassengers,finalPedestrians,isLegal));
					
					sc = null;
					psIndex = 0;
					pdIndex = 0;
					pass = new Persona[10];
					ped = new Persona[10];

				}
				if (sc == null) {
					sc = new Scenario();
				}
				if (elements[0].length() == 12) {
					isLegal = false;
				} else {
					isLegal = true;
				}
				
			} else { // Else if current line is in passenger / pedestrian, continue reading the splitted string words
				Human h = new Human();
				Animal a = new Animal();

				if (elements[0].equals("human")) {

					String gender = elements[1];
					int age = Integer.parseInt(elements[2]);
					String bodyType = elements[3];
					String profession = elements[4];
					boolean isPregnant = Boolean.parseBoolean(elements[5]);
					boolean isYou = Boolean.parseBoolean(elements[6]);
					h.setAge(age);
					h.setGender(Gender.valueOf(gender.toUpperCase()));
					h.setBodyType(BodyType.valueOf(bodyType.toUpperCase()));
					h.setProfession(Profession.valueOf(profession.toUpperCase()));
					h.setPregnant(isPregnant);
					h.setAsYou(isYou);

				} else { // Animals

					String gender = elements[1];
					int age = Integer.parseInt(elements[2]);
					String bodyType = elements[3];
					String species = elements[7];
					boolean isPet = Boolean.parseBoolean(elements[8]);
					a.setAge(age);
					a.setGender(Gender.valueOf(gender.toUpperCase()));
					a.setBodyType(BodyType.valueOf(bodyType.toUpperCase()));
					a.setSpecies(species);
					a.setPet(isPet);

				}

				// Add to the Array Persona
				if (elements[9].equals("passenger") && elements[0].equals("human")) {
					pass[psIndex] = h;
					psIndex++;
				}
				if (elements[9].equals("passenger") && elements[0].equals("animal")) {
					pass[psIndex] = a;
					psIndex++;
				}
				if (elements[9].equals("pedestrian") && elements[0].equals("human")) {
					ped[pdIndex] = h;
					pdIndex++;
				}
				if (elements[9].equals("pedestrian") && elements[0].equals("animal")) {
					ped[pdIndex] = a;
					pdIndex++;
				}

			}

		}

		// Transfer array persona to ArrayList, transfer only the non-null values
		List<Persona> items = new ArrayList<Persona>(pass.length);
		for (Persona input : pass) {
			if (input != null) {
				items.add(input);
			}
		}
		// Converting ArrayList to Array
		Persona[] finalPassengers = items.toArray(new Persona[items.size()]);

		List<Persona> items2 = new ArrayList<Persona>(ped.length);
		for (Persona input : ped) {
			if (input != null) {
				items2.add(input);
			}
		}
		// Converting ArrayList to Array
		Persona[] finalPedestrians = items2.toArray(new Persona[items2.size()]);

		// Create new scenario and push it to ArrayList of scenarios
		scenarios.add(new Scenario(finalPassengers,finalPedestrians,isLegal));

		return scenarios;
	}

	
	/**
	 * Displaying ascii characters to be called in main method
	 * @return void
	 */
	public static void displayMessage() {
		
		String path = new File("welcome.ascii").getAbsolutePath();
		File file = new File(path); 
		
		try (FileInputStream fin = new FileInputStream(file)) {
		      int i;
			do {
		        i = fin.read();
		        if (i != -1)
		          System.out.print((char) i);
		      } while (i != -1);
		      fin.close();
		    } catch (IOException e) {
		      System.out.println("An I/O Error Occurred");
		    }
		
	}

	public static void showRandomScenarios(boolean isRequiredSaving) {
		Audit a = new Audit();
		a.setAuditType("User");
		a.run(3);
		a.printStatistic();
		
	}
}