package translation.state_machine.gild.com;

import javafx.util.Pair;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StateMachine {
    private Map<Pair<Integer, Integer>, String> transitions;
    private Set<Integer> finalState;

    public StateMachine() {
        transitions = new HashMap<>();
        finalState = new HashSet<>();
    }

    public void getStatesFromFile(String filePath) {
        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            String currentString;
            if ((currentString = bf.readLine()) != null) {
                for (int i = 0; i < currentString.split(" ").length; i++) {
                    finalState.add(Integer.parseInt(currentString.split(" ")[i]));
                }
            }
            while ((currentString = bf.readLine()) != null) {
                transitions.put(new Pair<>(Integer.parseInt(currentString.split(" ")[0]),
                                Integer.parseInt(currentString.split(" ")[2])),
                        currentString.split(" ")[1]);
            }
        } catch (IOException e) {
            System.out.println("Can not read file with state machine description");
        }
    }

    public boolean isReadable(String filePath) {
        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            String currentLine;
            int i;
            int currentState = 1;
            if ((currentLine = bf.readLine()) != null) {
                for (i = 0; i != currentLine.toCharArray().length; i++) {
                    for (Map.Entry<Pair<Integer, Integer>, String> entry : transitions.entrySet()) {
                        if (entry.getKey().getKey().equals(currentState)) {
                            if (entry.getValue().equals(Character.toString(currentLine.toCharArray()[i]))) {
                                currentState = entry.getKey().getValue();
                                if(!transitions.containsKey(entry) && (finalState.contains(entry.getKey().getValue()))) {
                                    System.out.println("The state machine.txt recognizes the word \"" + currentLine + "\"");
                                    return true;
                                }
                            }
                        }
                    }
                }
                System.out.println("The state machine doesn't recognizes the word \"" + currentLine + "\"");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Can not read file with string to read");
        }

        return false;
    }
}

