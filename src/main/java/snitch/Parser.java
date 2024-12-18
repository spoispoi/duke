package snitch;

import snitch.command.*;
import snitch.SnitchException;

/**
 * Parses user input and returns the appropriate command to be executed.
 */
public class Parser {

    /**
     * Parses the user input and returns the corresponding Command object.
     *
     * @param input The user input as a string.
     * @return The Command object corresponding to the user input.
     * @throws SnitchException If the input is unrecognized or contains an error.
     */
    public static Command parse(String input) throws SnitchException {
        if (input.equalsIgnoreCase("bye")) {
            return new ByeCommand();
        } else if (input.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (input.startsWith("todo") || input.startsWith("deadline") || input.startsWith("event")) {
            return new AddCommand(input);
        } else if (input.startsWith("delete")) {
            String argument = input.substring(6).trim(); // Extracts everything after "delete"
            if (argument.isEmpty()) {
                throw new SnitchException("Invalid command. Try delete xxx (Task number).");
            }
            try {
                return new DeleteCommand(Integer.parseInt(argument));
            } catch (NumberFormatException e) {
                throw new SnitchException("Invalid task number format. Please enter a valid number.");
            }
        } else if (input.startsWith("mark")) {
            try {
                return new MarkCommand(Integer.parseInt(input.substring(5).trim()));
            } catch (NumberFormatException e) {
                throw new SnitchException("Invalid task number format. Try: mark <Task number>");
            }
        } else if (input.startsWith("unmark")) {
            try {
                return new UnmarkCommand(Integer.parseInt(input.substring(7).trim()));
            } catch (NumberFormatException e) {
                throw new SnitchException("Invalid task number format. Try: unmark <Task number>");
            }
        } else if (input.startsWith("find")) {
            String keyword = input.substring(4).trim();
            return new FindCommand(keyword);
        } else {
            throw new SnitchException("MEOW!! Come on man!!! I don't know what that means.");
        }
    }
}