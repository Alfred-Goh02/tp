package seedu.budgetbuddy.validators;

import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.commands.IncorrectCommand;
import seedu.budgetbuddy.commands.ListBudgetCommand;

import java.time.YearMonth;

import static seedu.budgetbuddy.validators.DateValidator.validateYearMonth;

/**
 * Validates user commands for listing budgets.
 */
public class ListBudgetValidator {

    /**
     * Processes the command string to determine if it is valid for listing budgets.
     * If valid, it returns a ListBudgetCommand with the parsed date.
     *
     * @param command The command string entered by the user.
     * @return A ListBudgetCommand if valid; otherwise, an IncorrectCommand.
     */
    public static Command processCommand(String command) {
        if (command.equals("list budget")) {
            return new ListBudgetCommand(null); // No date provided, list all budgets
        }

        // Check for date in the command
        String trimmedCommand = command.substring("list budget ".length()).trim();
        YearMonth date = null;

        if (!trimmedCommand.isEmpty()) {
            date = validateYearMonth(trimmedCommand);
            if (date == null) {
                return new IncorrectCommand("Invalid format. Use 'list budget [m/MM/yyyy]'.");
            }
        }

        return new ListBudgetCommand(date); // Return command with date
    }
}