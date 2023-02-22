package pro.sky.java.datebook;


import pro.sky.java.datebook.task.Task;
import pro.sky.java.datebook.task.Type;
import pro.sky.java.datebook.task.TaskService;
import pro.sky.java.datebook.task.OneTimeTask;
import pro.sky.java.datebook.task.DailyTask;
import pro.sky.java.datebook.task.WeeklyTask;
import pro.sky.java.datebook.task.MonthlyTask;
import pro.sky.java.datebook.task.YearlyTask;
import pro.sky.java.datebook.task.IncorrectArgumentException;
import pro.sky.java.datebook.task.TaskNotFoundException;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Program {

    private static final TaskService taskService = new TaskService();

    public static void main(String[] args) throws IncorrectArgumentException {

        System.out.println("DATEBOOK | Current date: " + LocalDate.now());

        startMenu();
    }

    private static void startMenu() throws IncorrectArgumentException {
        boolean check = true;
        while (check) {
            check = false;
            System.out.print("\nCommands: \n\"e\", \"exit\" — to exit; " +
                    "\n\"v\", \"view\" — to view some tasks by date; " +
                    "\n\"c\", \"create\" — to create some task; " +
                    "\n\"r\", \"remove\" — to remove some task; " +
                    "\n\"s\", \"show\" — to show all removed tasks. \n\nWrite a command: ");
            String command = new Scanner(System.in).nextLine();
            switch (command) {
                case "e":
                case "exit":
                    System.out.print("Bye bye!");
                    System.exit(0);
                    break;
                case "v":
                case "view":
                    printTasksByDate();
                    check = true;
                    break;
                case "c":
                case "create":
                    createATask();
                    check = true;
                    break;
                case "r":
                case "remove":
                    removeATask();
                    check = true;
                    break;
                case "s":
                case "show":
                    printAllRemovedTasks();
                    check = true;
                    break;
                default:
                    System.out.println("There is no such command. Please try again.");
                    check = true;
                    break;
            }
        }
    }

    private static void printTasksByDate() {
        try {
            int[] date = getUserDate();
            List<Task> tasksList = taskService.getAllByDate(LocalDate.of(date[0], date[1], date[2]));
            for (Task task : tasksList) {
                System.out.println(task);
            }
        } catch (DateTimeException | TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void printAllRemovedTasks() {
        try {
            Set<Task> set = taskService.getAllRemovedTasks();
            for (Task task : set) {
                System.out.println(task);
            }
        } catch (TaskNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void createATask() throws IncorrectArgumentException {
        boolean check = true;
        while (check) {
            check = false;
            System.out.print("\nSelect repeatability of the task: \n1 — one time task; \n2 — daily task; " +
                    "\n3 — weekly task; \n4 — monthly task; \n5 — yearly task; \n0 — cancel. \n\nWrite a command: ");
            String command = new Scanner(System.in).nextLine();
            if (command.isBlank()) {
                System.out.println("Please enter the command.");
                check = true;
            } else {
                if (isNumber(command)) {
                    int amountOfCommands = 5;
                    int numericCommand = Integer.parseInt(command);
                    if (numericCommand < 0 || numericCommand > amountOfCommands) {
                        System.out.println("There is no such numeric variant of the command. Please try again.");
                        check = true;
                    } else if (numericCommand == 0) {
                        startMenu();
                    } else {
                        createATaskByNumericCommand(numericCommand);
                    }
                } else {
                    System.out.println("Please enter a numeric variant of the command.");
                    check = true;
                }
            }
        }
    }

    private static void createATaskByNumericCommand(int numericCommand) {
        try {
            int[] date = getUserDate();
            System.out.println("Possible types of tasks: " + Arrays.toString(Type.values()));
            String[] parameters = getUserTaskParameters();
            switch (numericCommand) {
                case 1:
                    OneTimeTask oneTimeTask = new OneTimeTask(LocalDate.of(date[0], date[1], date[2]),
                            parameters[0], parameters[1], parameters[2]);
                    taskService.add(oneTimeTask);
                    System.out.println("The task (ID:" + oneTimeTask.getId() + ") has been created.");
                    break;
                case 2:
                    DailyTask dailyTask = new DailyTask(LocalDate.of(date[0], date[1], date[2]),
                            parameters[0], parameters[1], parameters[2]);
                    taskService.add(dailyTask);
                    System.out.println("The task (ID:" + dailyTask.getId() + ") has been created.");
                    break;
                case 3:
                    WeeklyTask weeklyTask = new WeeklyTask(LocalDate.of(date[0], date[1], date[2]),
                            parameters[0], parameters[1], parameters[2]);
                    taskService.add(weeklyTask);
                    System.out.println("The task (ID:" + weeklyTask.getId() + ") has been created.");
                    break;
                case 4:
                    MonthlyTask monthlyTask = new MonthlyTask(LocalDate.of(date[0], date[1], date[2]),
                            parameters[0], parameters[1], parameters[2]);
                    taskService.add(monthlyTask);
                    System.out.println("The task (ID:" + monthlyTask.getId() + ") has been created.");
                    break;
                case 5:
                    YearlyTask yearlyTask = new YearlyTask(LocalDate.of(date[0], date[1], date[2]),
                            parameters[0], parameters[1], parameters[2]);
                    taskService.add(yearlyTask);
                    System.out.println("The task (ID:" + yearlyTask.getId() + ") has been created.");
                    break;
            }
        } catch (DateTimeException e) {
            System.out.println(e.getMessage());
        } catch (IncorrectArgumentException e) {
            System.out.println(e.toString());
        }
    }

    private static void removeATask() {
        boolean check = true;
        while (check) {
            check = false;
            System.out.print("Task ID: ");
            String command = new Scanner(System.in).nextLine();
            if (command.isBlank()) {
                System.out.println("Please enter the task ID.");
                check = true;
            } else {
                if (isNumber(command)) {
                    try {
                        taskService.remove(Integer.parseInt(command));
                        System.out.println("The task has been removed.");
                    } catch (TaskNotFoundException e) {
                        System.out.println(e.getMessage());
                    }
                } else {
                    System.out.println("ID must be a number.");
                    check = true;
                }
            }
        }
    }

    private static int[] getUserDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the date, please.");
        System.out.print("Year: ");
        int year = scanner.nextInt();
        System.out.print("Month: ");
        int month = scanner.nextInt();
        System.out.print("Day: ");
        int day = scanner.nextInt();
        return new int[]{year, month, day};
    }

    private static String[] getUserTaskParameters() {
        String[] parameters = new String[3];
        boolean check = true;
        while (check) {
            check = false;
            System.out.print("Task type: ");
            String type = new Scanner(System.in).nextLine();
            if (type.isBlank()) {
                System.out.println("Please enter the task type.");
                check = true;
            } else {
                parameters[0] = type;
            }
        }
        check = true;
        while (check) {
            check = false;
            System.out.print("Title: ");
            String title = new Scanner(System.in).nextLine();
            if (title.isBlank()) {
                System.out.println("Please enter the task title.");
                check = true;
            } else {
                parameters[1] = title;
            }
        }
        check = true;
        while (check) {
            check = false;
            System.out.print("Description: ");
            String description = new Scanner(System.in).nextLine();
            if (description.isBlank()) {
                System.out.println("Please enter the task description.");
                check = true;
            } else {
                parameters[2] = description;
            }
        }
        return parameters;
    }

    private static boolean isNumber(String command) {
        return command.chars().allMatch(Character::isDigit);
    }
}
