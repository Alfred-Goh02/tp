package seedu.budgetbuddy.validators.budget;

import seedu.budgetbuddy.commands.budget.AddBudgetCommand;
import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.exceptions.BudgetBuddyException;

import java.time.YearMonth;
import java.util.logging.Logger;

import static seedu.budgetbuddy.validators.AmountValidator.validateAmount;
import static seedu.budgetbuddy.validators.DateValidator.validateYearMonth;

/**
 * Validates commands for adding budgets.
 */
public class AddBudgetValidator {
    private static Logger logger = Logger.getLogger(AddBudgetValidator.class.getName());

    public static Command processCommand(String command) throws BudgetBuddyException {
        assert command != null : "Command cannot be null";

        if (command.equals("add budget")) {
            logger.warning("Attempted to add budget without description.");
            throw new BudgetBuddyException("No description provided.");
        }

        String trimmedCommand = command.substring("add budget ".length());
        String[] parts = trimmedCommand.split(" ");

        // Initialize default values
        double amount = 0; // invalid amount initially
        YearMonth date = null; // invalid date initially

        // Process parts to extract details
        for (String part : parts) {
            if (part.startsWith("a/")) {
                amount = validateAmount(part);
                if (amount == -1) {
                    throw new BudgetBuddyException("Invalid amount format. " +
                            "Amount should be a positive number.");
                }
            } else if (part.startsWith("m/")) {
                date = validateYearMonth(part);
            }
        }

        // Validate amount
        if (amount <= 0) {
            throw new BudgetBuddyException("Invalid amount: " + amount + ". Amount must be a positive value.");
        }

        // Validate date
        if (date == null) {
            throw new BudgetBuddyException("Invalid date format. Use m/MM/yyyy.");
        }

        // All validations passed, return the command
        return new AddBudgetCommand(amount, date);
    }

}
