import ethicalengine.ScenarioGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ethicalengine.Human;
import ethicalengine.Persona;
import ethicalengine.Scenario;

public class Audit {

	public static final String NEWLINE = "\n";
	private static final Pattern p = Pattern.compile("^\\D+(\\d+).*");

	private String auditType = "Unspecified";
	private String statistics = "";
	private String simulationMsg = "no audit available";
	private String filePath = "";
	private int currentRuns = 0;
	private int totalAge = 0;
	private int totalCount = 0;
	private String previousLogString = "";
	private int previousTotalAge = 0;
	private int previousTotalCount = 0;
	private int previousRunCount = 0;
	private HashMap<String, Double> previousMap;
	private HashMap<String, Double> currentMap;
	private HashMap<String, Double> newMap;

	public Audit() {
	}

	/**
	 * A public method that runs a decision from EthicalEngine, the number of
	 * randomly generated Scenarios are based on params int runs. Also, compose the
	 * simulationMsg for Audit Report
	 * 
	 * @param int runs
	 */
	public void run(int runs) {
		Scanner kbd = new Scanner(System.in);
		this.currentRuns = runs;
		ScenarioGenerator sg = new ScenarioGenerator();
		ArrayList<Scenario> randomScenarios = new ArrayList<Scenario>();
		//""
		
		
		for (int i = 0; i < runs; i++) {
			randomScenarios.add(sg.generate());
		}
		//"scenario 1", "scenario2"
		
		Map<Scenario, EthicalEngine.Decision> resultStatistics = new HashMap<>();
		
		//"", 
		//"scenario 1 - Passenger", "scenario 2  - ",
		
		randomScenarios.forEach(sc -> {
			System.out.println(sc.toString());
			System.out.print("Who will be saved? (passenger(s) [1] or pedestrian(s) [2]) : ");
			if (this.auditType == "User") {
				String saveChoice = kbd.nextLine();
				if (saveChoice.toLowerCase() == "passenger" || saveChoice.toLowerCase() == "passengers" || saveChoice == "1") {
					resultStatistics.put(sc, EthicalEngine.Decision.PASSENGERS);
				} else {
					resultStatistics.put(sc, EthicalEngine.Decision.PASSENGERS); // should be pedestrian
				}
			}else {
				if (EthicalEngine.decide(sc) == EthicalEngine.Decision.PASSENGERS) {
					resultStatistics.put(sc, EthicalEngine.Decision.PASSENGERS);
					System.out.println("Passengers");
				} else {
					resultStatistics.put(sc, EthicalEngine.Decision.PEDESTRIANS);
					System.out.println("Pedestrians");
				}
			}
			
		});

		HashMap<String, Integer> wordCountMap = new HashMap<String, Integer>();
		HashMap<String, Integer> saveCountMap = new HashMap<String, Integer>();
		
		Set<Map.Entry<Scenario, EthicalEngine.Decision>> entrySet = resultStatistics.entrySet();
		
		//average adult criminal female
		
		for (Map.Entry<Scenario, EthicalEngine.Decision> entry : entrySet) {
			this.totalCount += entry.getKey().getPassengerCount();
			this.totalCount += entry.getKey().getPedestrianCount();
			for (Persona p : entry.getKey().getPassengers()) {
				this.totalAge += p.getAge();
			}
			for (Persona p : entry.getKey().getPedestrians()) {
				this.totalAge += p.getAge();
			}
		}

		resultStatistics.forEach((k, v) -> {

			String[] lines = k.toString().split("\n");
			// get occurences for saved group
			if (v == EthicalEngine.Decision.PASSENGERS) {
				for (Persona p : k.getPassengers()) {
					if (p.getClass() == Human.class) {
						for (String word : p.toString().split(" ")) {
							if (saveCountMap.containsKey(word)) {
								saveCountMap.put(word, saveCountMap.get(word) + 1);
							} else {
								saveCountMap.put(word, 1);
							}
						}
					}
				}
			} else {
				for (Persona p : k.getPedestrians()) {
					if (p.getClass() == Human.class) {
						for (String word : p.toString().split(" ")) {
							if (saveCountMap.containsKey(word)) {
								saveCountMap.put(word, saveCountMap.get(word) + 1);
							} else {
								saveCountMap.put(word, 1);
							}
						}
					}
				}
			}

			// get total occurences in scenario
			for (String l : lines) {
				if (l.startsWith("-")) {
					for (String word : l.substring(2).split(" ")) {
						if (wordCountMap.containsKey(word)) {
							wordCountMap.put(word, wordCountMap.get(word) + 1);
						} else {
							wordCountMap.put(word, 1);
						}
					}
				}
			}
		});

		simulationMsg = "======================================" + NEWLINE;
		simulationMsg += "# Algorithm Audit" + NEWLINE;
		simulationMsg += "======================================" + NEWLINE;
		simulationMsg += "- % SAVED AFTER " + runs + " RUNS" + NEWLINE;
		this.currentMap = new HashMap<String, Double>();
		// COMPUTATION
		for (Map.Entry e : wordCountMap.entrySet()) {
			if (saveCountMap.get(e.getKey().toString()) == null) {
				continue;
			}
			double res = saveCountMap.get(e.getKey().toString()) / Double.parseDouble(e.getValue().toString());
			DecimalFormat numberFormat = new DecimalFormat("0.00");
			this.currentMap.put(e.getKey().toString(), Double.parseDouble(numberFormat.format(res)));
			simulationMsg += e.getKey() + ": " + numberFormat.format(res) + NEWLINE;

		}
		simulationMsg += "--" + NEWLINE;
		simulationMsg += "average age: " + totalAge / totalCount;

	}

	/**
	 * Setter for auditType
	 * @param String type
	 */
	public void setAuditType(String type) {
		this.auditType = type;
	}
	/**
	 * Getter for auditType
	 * @return String
	 */
	public String getAuditType() {
		return this.auditType;
	}

	/**
	 * toString method that uses simulationMsg
	 * @return String 
	 */
	public String toString() {
		return simulationMsg;
	}

	/**
	 * printStatistic method used to print out the return string coming from toString()
	 * 
	 */
	public void printStatistic() {
		System.out.print(this.toString());
	}

	/**
	 * printToFile used to create and write a file based on params
	 * @param filePath 
	 */
	public void printToFile(String filePath) {
		String previousAuditResults = "";
		if (filePath == "") {
			String path = new File("results.log").getAbsolutePath();
			System.out.println(path);
			try {
				File f = new File(path);
				if (f.exists() && !f.isDirectory()) { // append results in default directory

					File file = new File(path);
					Scanner scanner = new Scanner(file);
					while (scanner.hasNextLine()) {
						previousAuditResults += scanner.nextLine() + NEWLINE;
					}

					this.previousLogString = previousAuditResults.trim();
					this.processAppendStat();
					FileWriter myWriter = new FileWriter(path);
					myWriter.write(this.toString() + NEWLINE + "sum age: " + (this.previousTotalAge + this.totalAge) + NEWLINE
							+ "total count: " + (this.previousTotalCount + this.totalCount) + NEWLINE);
					myWriter.close();
				} else { // Write the results.log in default directory
					if (f.createNewFile()) {
						FileWriter myWriter = new FileWriter(path);
						myWriter.write(this.toString() + NEWLINE + "sum age: " + (this.previousTotalAge + this.totalAge) + NEWLINE
								+ "total count: " + (this.previousTotalCount + this.totalCount) + NEWLINE);
						myWriter.close();
					}
				}

			} catch (Exception e) {
				System.out.println("Error: " + e.toString());
			}
		}
	}

	
	public String getIntValueFromIndexStringLine(List<String> list, int index) {
		String n = list.get(index);
		Matcher m = p.matcher(n);
		if (m.find()) {
			return m.group(1);
		}
		return "0";
	}

	/**
	 * processAppendStat method is used to add and compute previous Audit result to the current simulation, also, manipulated simulationMsg
	 *  
	 */
	public void processAppendStat() {
		// Breakdown this.previousLogString
		String[] linePreviousResults = this.previousLogString.split(NEWLINE);

		List<String> listPrevResults = new ArrayList<String>();
		for (String text : linePreviousResults) {
			listPrevResults.add(text);
		}

		listPrevResults.remove(0);
		listPrevResults.remove(0);
		listPrevResults.remove(0);
		this.previousTotalCount = Integer
				.parseInt(getIntValueFromIndexStringLine(listPrevResults, listPrevResults.size() - 1));

		listPrevResults.remove(listPrevResults.get(listPrevResults.size() - 1));
		this.previousTotalAge = Integer
				.parseInt(getIntValueFromIndexStringLine(listPrevResults, listPrevResults.size() - 1));
		this.previousRunCount = Integer.parseInt(getIntValueFromIndexStringLine(listPrevResults, 0));

		listPrevResults.remove(0);
		listPrevResults.remove(listPrevResults.size() - 1);
		listPrevResults.remove(listPrevResults.size() - 1);
		listPrevResults.remove(listPrevResults.size() - 1);

		this.previousMap = new HashMap<String, Double>();
		this.newMap = new HashMap<String, Double>();

		for (int i = 0; i < listPrevResults.size(); i++) {
			String[] tmpMap = listPrevResults.get(i).split(": ");
			previousMap.put(tmpMap[0], Double.parseDouble(tmpMap[1]));
		}

		this.newMap.putAll(this.currentMap);
		this.newMap.putAll(this.previousMap);
		
		if (this.newMap.size() > this.previousMap.size()) {
			for (int i = 0; i < this.previousMap.size(); i++) {
				if (this.newMap.containsKey(this.previousMap.keySet().toArray()[i])) {
					this.newMap.put((String) this.previousMap.keySet().toArray()[i], this.previousMap.get(this.previousMap.keySet().toArray()[i]) + this.newMap.get(this.previousMap.keySet().toArray()[i]) );
				} else {
					this.newMap.put((String) this.previousMap.keySet().toArray()[i], this.previousMap.get(this.previousMap.keySet().toArray()[i]));
				}
			}
		}
		
		simulationMsg = "======================================" + NEWLINE;
		simulationMsg += "# Algorithm Audit" + NEWLINE;
		simulationMsg += "======================================" + NEWLINE;
		simulationMsg += "- % SAVED AFTER " + (previousRunCount + currentRuns) + " RUNS" + NEWLINE;
		// COMPUTATION
		for (Map.Entry e : this.newMap.entrySet()) {
			//double res = this.newMap.get(e.getKey().toString()) / Double.parseDouble(e.getValue().toString());
			DecimalFormat numberFormat = new DecimalFormat("0.00");
			simulationMsg += e.getKey() + ": " + numberFormat.format(e.getValue()) + NEWLINE;

		}
		simulationMsg += "--" + NEWLINE;
		simulationMsg += "average age: " + (this.previousTotalAge + this.totalAge) / (this.totalCount + this.previousTotalCount);
//		Test Print
//		Set<Map.Entry<String, Double>> entrySet = previousMap.entrySet();
//		Set<Map.Entry<String, Double>> entrySet2 = currentMap.entrySet();
//		Set<Map.Entry<String, Double>> entrySet3 = newMap.entrySet();
//
//		System.out.println("============old");
//		for (Map.Entry<String, Double> entry : entrySet) {
//			System.out.println(entry.getKey() + " ==== " + entry.getValue());
//
//		}
//		System.out.println("============current");
//		for (Map.Entry<String, Double> entry : entrySet2) {
//			System.out.println(entry.getKey() + " ==== " + entry.getValue());
//
//		}
//		System.out.println("============new");
//		for (Map.Entry<String, Double> entry : entrySet3) {
//			System.out.println(entry.getKey() + " ==== " + entry.getValue());
//
//		}

	}

}
