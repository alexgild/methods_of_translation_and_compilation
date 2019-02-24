import translation.state_machine.gild.com.StateMachine;

import java.io.File;

public class Main {
    //Usage: args: file with descriptions of states, file with the word.txt to recognize
    public static void main(String[] args) {
        StateMachine stateMachine = new StateMachine();
        stateMachine.getStatesFromFile(args[0]);
        stateMachine.isReadable(args[1]);
    }
}
