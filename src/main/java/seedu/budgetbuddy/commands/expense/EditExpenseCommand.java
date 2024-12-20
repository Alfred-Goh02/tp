package seedu.budgetbuddy.commands.expense;

import seedu.budgetbuddy.Ui;
import seedu.budgetbuddy.commands.Command;
import seedu.budgetbuddy.exceptions.BudgetBuddyException;
import seedu.budgetbuddy.transaction.Category;
import seedu.budgetbuddy.transaction.expense.Expense;
import seedu.budgetbuddy.transaction.expense.ExpenseManager;
import seedu.budgetbuddy.util.LoggerSetup;
import seedu.budgetbuddy.validators.expense.EditExpenseValidator;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditExpenseCommand extends Command {

    private static final Logger LOGGER = LoggerSetup.getLogger();
    private static final double EMPTY_AMOUNT = -1.0;
    private static final Category EMPTY_CATEGORY = null;
    private static final LocalDate EMPTY_DATE = null;
    private static Category category;
    private static LocalDate date;
    private static double amount;
    private Expense expense;


    public EditExpenseCommand(String command) throws BudgetBuddyException {
        getExpense(command);
    }

    /**
     * Used for Unit Testing
     *
     * @param expense Created expense object to be tested
     */
    public EditExpenseCommand(Expense expense) {
        this.expense = expense;
    }

    /**
     * Checks if the provided command matches the command to list expenses.
     *
     * @param command The command to be checked.
     * @return True if the command matches "list expenses", false otherwise.
     */
    public static boolean isCommand(String command) {
        return command.startsWith("edit expenses");
    }
    
    /**
     * Set date that will be used to update expenses
     *
     * @param newDate new Expense Date
     */
    public static void setDate(LocalDate newDate) {
        date = newDate;
    }

    /**
     * Set amount that will be used to update expenses
     *
     * @param newAmount new Expense Amount
     */
    public static void setAmount(double newAmount) {
        amount = newAmount;
    }

    /**
     * Set category that will be used to update expenses
     *
     * @param newCategory new Expense Category
     */
    public static void setCategory(Category newCategory) {
        category = newCategory;
    }

    /**
     * Execute Command to get fields to edit an expense object
     * Ends Command if user input nothing
     * Shows Error Text if user input an invalid field
     * Else Edits the expense based on input fields
     */
    public void execute() {
        String editFields = getEditFields();
        LOGGER.log(Level.INFO, "Successfully retrieve edit fields");
        Boolean validInput;
        if(editFields.isEmpty()) {
            return;
        }
        validInput = EditExpenseValidator.processCommand(editFields);
        if(validInput) {
            processEdit();
        }
        LOGGER.log(Level.INFO, "Successfully edit expense");
    }

    /**
     * Displays Custom Message that informs users to input edit fields
     * Then reads in user input.
     *
     * @return User input EditFields that will be used for editing Expenses {@code String}
     */
    public String getEditFields(){
        Ui.showMessage("Edit the following fields as follows: Amount: a/, Category: c/, Date: d/\n" +
                "Currently Editing Entry:\n" +
                expense.toString());
        String editFields = Ui.getUserEditFields();
        return editFields;
    }

    /**
     * Finds the desired expense from the list of expenses that will be updated
     * Saves Expense for future reference on successful find
     *
     * @param command User Input
     * @throws BudgetBuddyException
     */
    private void getExpense(String command) throws BudgetBuddyException {
        if (command.equals("edit expenses")) {
            throw new BudgetBuddyException("No index detected, try again with an index.");
        }
        try {
            String trimmedCommand = command.substring("edit expenses ".length());
            String[] parts = trimmedCommand.split(" ");
            int editIndex = Integer.parseInt(parts[0]) + 1;
            if (editIndex <= 0) {
                throw new BudgetBuddyException("Edit index must be greater than 0.");
            }
            this.expense = ExpenseManager.getExpenseByIndex(editIndex);
        } catch (NumberFormatException e) {
            throw new BudgetBuddyException("Index must be a valid number larger than 0.");
        }
    }

    /**
     * Process which fields to edit based on values stored
     * For any field that is not left empty by user, it will update the Expense object.
     */
    public void processEdit(){
        if(category != EMPTY_CATEGORY) {
            expense.editCategory(category);
        }
        if(date != EMPTY_DATE) {
            expense.editDate(date);
        }
        if(amount != EMPTY_AMOUNT) {
            expense.editAmount(amount);
        }
        Ui.displayToUser("Edited Expense:\n" + expense.toString());
    }

}
