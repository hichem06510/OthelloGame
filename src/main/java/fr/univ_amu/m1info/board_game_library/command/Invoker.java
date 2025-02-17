package fr.univ_amu.m1info.board_game_library.command;
import fr.univ_amu.m1info.board_game_library.othello.Jeu;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final Jeu jeu; // Receiver
    private final List<Command> commandHistory;
    private int undoDepth; // Tracks the undo stack
    private int executed; // Flag to mark the initial state


    public Invoker(Jeu jeu) {
        this.jeu = jeu;
        this.commandHistory = new ArrayList<>();
        this.undoDepth = 0;
        this.executed = 0;
    }

    public void executeCommand(Command command) {
        clearUndoneCommands(); // Clear commands beyond the current state
        command.execute(jeu);  // Execute the command
        commandHistory.add(command);
        this.executed++;
        System.out.println("execute command");
    }

    public void undo() {
        if ((undoDepth >= commandHistory.size()) || (this.executed == 4)) return; // No more commands to undo

        int position = commandHistory.size() - 1 - undoDepth;
        Command command = commandHistory.get(position);
        command.unexecute(jeu); // Undo the command
        undoDepth++;
        this.executed--;
        System.out.println("undo");
    }

    public void redo() {
        if (undoDepth == 0) return; // No more commands to redo

        int position = commandHistory.size() - undoDepth;
        Command command = commandHistory.get(position);
        command.execute(jeu); // Redo the command
        undoDepth--;
        this.executed++;
        System.out.println("redo");
    }

    private void clearUndoneCommands() {
        while (undoDepth > 0) {
            commandHistory.remove(commandHistory.size() - 1);
            undoDepth--;
            System.out.println("here");
        }
        System.out.println("clear undone commands");
    }


}
