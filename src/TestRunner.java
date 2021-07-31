import java.lang.AssertionError;
import java.lang.IllegalArgumentException;

import ethicalengine.*;
import ethicalengine.Human.*;
import ethicalengine.Persona.*;


public class TestRunner {

    public static final String NEWLINE = "\n";

    public void run() {
        System.out.println("++++++++++++++++++++++ Initializing Tests ++++++++++++++++++++++");
        int errorCount = 0;

        try {
            testAgeCategories();
        } catch (IllegalArgumentException ae) {
            System.err.println("- failed");
            errorCount+=1;
        }

        try {
            testBodyType();
        } catch (IllegalArgumentException ae) {
            System.err.println("- failed");
            errorCount+=1;
        }

        try {
            testGender();
        } catch (IllegalArgumentException ae) {
            System.err.println("- failed");
            errorCount+=1;
        }

        try {
            testProfession();
        } catch (IllegalArgumentException ae) {
            System.err.println("- failed");
            errorCount+=1;
        }

        try {
            testDecision();
        } catch (IllegalArgumentException ae) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if (!testEthicalEngine()) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if (!testPersona()) {
            System.err.println("- failed");
            errorCount+=1;
        }
        
        if (!testAnimal()) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if (!testHuman()) {
            System.err.println("- failed");
            errorCount+=1;
        }
        
        if (!testScenario()) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if (!testScenarioGenerator()) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if (!testAudit()) {
            System.err.println("- failed");
            errorCount+=1;
        }

        if(errorCount>0) {
            System.out.println("-- " + errorCount + " error" + (errorCount==1 ? "" : "s") + " found :(");   
        } else {
            System.out.println("++ No errors found so far. Keep on testing! :) +++++++++++++++++"); 
        }
    }

    private void testAgeCategories() {
        System.out.println("+ Test: enumeration <AgeCategory>");
        AgeCategory.valueOf("BABY");
        AgeCategory.valueOf("CHILD");
        AgeCategory.valueOf("ADULT");
        AgeCategory.valueOf("SENIOR");
    }

    private void testBodyType() {
        System.out.println("+ Test: enumeration <BodyType>");
        BodyType.valueOf("AVERAGE");
        BodyType.valueOf("ATHLETIC");
        BodyType.valueOf("OVERWEIGHT");
        BodyType.valueOf("UNSPECIFIED");
    }

    private void testDecision() {
        System.out.println("+ Test: enumeration <Decision>");
        EthicalEngine.Decision.valueOf("PASSENGERS");
        EthicalEngine.Decision.valueOf("PEDESTRIANS");
    }

    private boolean testEthicalEngine() {
        System.out.println("+ Test: class <EthicalEngine>");
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        EthicalEngine.Decision decision = EthicalEngine.decide(scenarioGenerator.generate());
        return (decision==EthicalEngine.Decision.PASSENGERS || decision==EthicalEngine.Decision.PEDESTRIANS);
    }

    private void testGender() {
        System.out.println("+ Test: enumeration <Gender>");
        Gender.valueOf("MALE");
        Gender.valueOf("FEMALE");
        Gender.valueOf("UNKNOWN");
    }

    private void testProfession() {
        System.out.println("+ Test: enumeration <Profession>");
        Profession.valueOf("DOCTOR");
        Profession.valueOf("CEO");
        Profession.valueOf("CRIMINAL");
        Profession.valueOf("HOMELESS");
        Profession.valueOf("UNEMPLOYED");
        Profession.valueOf("NONE");
    }

    private boolean testPersona() {
        System.out.println("+ Test: abstract class <Persona>");
        Human p1 = new Human();
        Human p2 = new Human(30, Gender.MALE, BodyType.AVERAGE);

        // setters
        Human p3 = new Human();
        p3.setAge(50);
        p3.setGender(Gender.MALE);
        p3.setBodyType(BodyType.ATHLETIC);

        return p3.getAge()==50;
    }

    private boolean testAnimal() {
        System.out.println("+ Test: class <Animal>");
        Animal a1 = new Animal();
        Animal a2 = new Animal("dog");
        Animal a3 = new Animal("cat");
        a1.setPet(true);

        // copy constructor
        Animal copy = new Animal(a2);

        // getter
        String species = a1.getSpecies(); 

        // setter
        a1.setSpecies("dog");
        a2.setSpecies("cat");
        a3.setSpecies("bird");
        a3.setPet(true);

        return (a1.isPet() && a2.toString().equals("cat") && a3.toString().equals("bird is pet"));
    }

    private boolean testHuman() {
        System.out.println("+ Test: class <Human>");
        Human p1 = new Human(34, Profession.HOMELESS, Gender.MALE, BodyType.ATHLETIC, false);
        Human p2 = new Human(28, Profession.CEO, Gender.FEMALE, BodyType.OVERWEIGHT, true);
        Human baby = new Human(1, Profession.CEO, Gender.FEMALE, BodyType.OVERWEIGHT, false);

        Human p3 = new Human(p2);

        // getter
        Profession profession = p1.getProfession();
        int age = p1.getAge();
        Gender gender = p1.getGender();
        BodyType bodytype = p1.getBodyType();
        boolean pregnant = p1.isPregnant();

        // setter
        p2.setPregnant(true);
        baby.setAsYou(true);

        // toString
        return (p1.toString().equals("athletic adult homeless male")
            && p2.toString().equals("overweight adult ceo female pregnant")
            && baby.toString().equals("you overweight baby female")
        );
    }

    private boolean testScenario() {
        System.out.println("+ Test: class <Scenario>");

        Human passenger1 = new Human(34, Human.Profession.HOMELESS, ethicalengine.Persona.Gender.MALE, ethicalengine.Persona.BodyType.ATHLETIC, false);
        Human passenger2 = new Human(28, Human.Profession.CEO, ethicalengine.Persona.Gender.FEMALE, ethicalengine.Persona.BodyType.OVERWEIGHT, true);
        Human passenger3 = new Human(87, Human.Profession.NONE, ethicalengine.Persona.Gender.FEMALE, ethicalengine.Persona.BodyType.ATHLETIC, false);
        Human baby = new Human(1, Human.Profession.CEO, Gender.FEMALE, BodyType.OVERWEIGHT, false);

        Human pedestrian1 = new Human(66, Human.Profession.UNEMPLOYED, ethicalengine.Persona.Gender.MALE, ethicalengine.Persona.BodyType.OVERWEIGHT, false);
        Human pedestrian2 = new Human(14, Human.Profession.NONE, ethicalengine.Persona.Gender.FEMALE, ethicalengine.Persona.BodyType.ATHLETIC, false);
        pedestrian2.setAsYou(true);

        Animal a1 = new Animal("bird");
        Animal a2 = new Animal("dog");
        a2.setPet(true);

        Persona[] passengers = new ethicalengine.Persona[4];
        passengers[0] = passenger1;
        passengers[1] = passenger2;
        passengers[2] = passenger3;
        passengers[3] = baby;

        Persona[] pedestrians = new ethicalengine.Persona[4];
        pedestrians[0] = pedestrian1;
        pedestrians[1] = pedestrian2;
        pedestrians[2] = a1;
        pedestrians[3] = a2;

        Scenario s1 = new Scenario(passengers, pedestrians, false);
        Scenario s2 = new Scenario(pedestrians, passengers, false);

        // getter
        s1.hasYouInCar();
        s1.hasYouInLane();
        s1.getPassengerCount();
        s1.getPedestrianCount();

        // setter
        s1.setLegalCrossing(true);

        // toString()
        String res = "======================================" + NEWLINE;
            res += "# Scenario" + NEWLINE;
            res += "======================================" + NEWLINE;
            res += "Legal Crossing: yes" + NEWLINE;
            res += "Passengers (4)" + NEWLINE;
            res += "- athletic adult homeless male" + NEWLINE;
            res += "- overweight adult ceo female pregnant" + NEWLINE;
            res += "- athletic senior female" + NEWLINE;
            res += "- overweight baby female" + NEWLINE;
            res += "Pedestrians (4)" + NEWLINE;
            res += "- overweight adult unemployed male" + NEWLINE;
            res += "- you athletic child female" + NEWLINE;
            res += "- bird" + NEWLINE;
            res += "- dog is pet";

        String res2 = "======================================" + NEWLINE;
            res2 += "# Scenario" + NEWLINE;
            res2 += "======================================" + NEWLINE;
            res2 += "Legal Crossing: no" + NEWLINE;
            res2 += "Passengers (4)" + NEWLINE;
            res2 += "- overweight adult unemployed male" + NEWLINE;
            res2 += "- you athletic child female" + NEWLINE;
            res2 += "- bird" + NEWLINE;
            res2 += "- dog is pet" + NEWLINE;
            res2 += "Pedestrians (4)" + NEWLINE;
            res2 += "- athletic adult homeless male" + NEWLINE;
            res2 += "- overweight adult ceo female pregnant" + NEWLINE;
            res2 += "- athletic senior female" + NEWLINE;
            res2 += "- overweight baby female";

            
        return (s1.isLegalCrossing()
            && s1.toString().equals(res)
            && s2.toString().equals(res2)
        );
    }

    private boolean testScenarioGenerator() {
        System.out.println("+ Test: class <ScenarioGenerator>");

        long seed1 = 1234;
        long seed2 = 1337;
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator();
        ScenarioGenerator scenarioGenerator2 = new ScenarioGenerator(seed1);
        ScenarioGenerator scenarioGenerator3 = new ScenarioGenerator(seed2, 1, 7, 1, 7);

        // getter
        Human person = scenarioGenerator.getRandomHuman();
        Animal animal = scenarioGenerator3.getRandomAnimal();

        // setter
        scenarioGenerator.setPassengerCountMin(1);
        scenarioGenerator.setPassengerCountMax(7);
        scenarioGenerator.setPedestrianCountMin(1);
        scenarioGenerator.setPedestrianCountMax(7);

        // same seed same scenario features
        ScenarioGenerator tmp = new ScenarioGenerator(seed1);
        Scenario scenario1 = scenarioGenerator2.generate();
        Scenario scenario2 = tmp.generate();
        
        System.out.print(scenario1.toString());
        System.out.println();
        System.out.print(scenario2.toString());
        System.out.println();

        return (scenario1.toString().equals(scenario2.toString()));
    }

    private boolean testAudit() {
        System.out.println("+ Test: class <Audit>");

        long seed1 = 1234;
        long seed2 = 1337;
        ScenarioGenerator scenarioGenerator = new ScenarioGenerator(seed1);
        ScenarioGenerator scenarioGenerator2 = new ScenarioGenerator(seed2, 1, 7, 1, 7);

        Audit audit = new Audit();

        audit.setAuditType("Algorithm");
        String auditType = audit.getAuditType();

        int runCount = 100;
        audit.run(runCount);

        String[] lines = audit.toString().split(NEWLINE);

        return (lines[0].equals("======================================") 
            && lines[1].equals("# Algorithm Audit")
            && lines[2].equals("======================================")
            && lines[3].equals("- % SAVED AFTER " + runCount + " RUNS")
            && lines.length > 5
            && lines[lines.length-2].startsWith("--")
            && lines[lines.length-1].startsWith("average age: ")

        );
    }

    public static void main(String[] args) {
        TestRunner tests = new TestRunner();
        tests.run();
    }
}