import CSV.CSVReader;
import CSV.CSVWriter;
import com.example.TeamWorkAnalyzer.models.TeamWork;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class WorkedTogetherApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Load data from CSV
        String filePath = "employees.csv";
        List<TeamWork> teamWorkList = CSVReader.readCsvFile(filePath);

        // Display menu
        displayMenu(teamWorkList);

        scanner.close();
    }

    private static void displayMenu(List<TeamWork> teamWorkList) {
        Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1 - View Employees");
            System.out.println("2 - Add Employee");
            System.out.println("3 - Edit Employee");
            System.out.println("4 - Delete Employee");
            System.out.println("5 - Employees who have worked together the longest");
            System.out.println("6 - Exit");

            System.out.print("Enter your choice: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number between 1 and 6.");
                scanner.next(); // Consume invalid input
            }

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    viewEmployees(teamWorkList);
                    break;
                case 2:
                    addEmployee(teamWorkList);
                    break;
                case 3:
                    editEmployee(teamWorkList);
                    break;
                case 4:
                    deleteEmployee(teamWorkList);
                    break;
                case 5:
                    WorkedTogetherResult result = findMostTimeWorkedPair(teamWorkList);
                    if (result != null) {
                        System.out.println(result);
                    } else {
                        System.out.println("No employees have worked together.");
                    }
                    break;
                case 6:
                    System.out.println("Shutting down the app. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 6);

        scanner.close();
    }

    private static void viewEmployees(List<TeamWork> teamWorkList) {
        System.out.println("List of Employees:");
        for (TeamWork teamWork : teamWorkList) {
            System.out.println("Employee ID: " + teamWork.getEmpId() +
                    ", Project ID: " + teamWork.getProjectId() +
                    ", Start Date: " + teamWork.getStartDate() +
                    ", End Date: " + teamWork.getEndDate());
        }
    }

    private static void addEmployee(List<TeamWork> teamWorkList) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding Employee:");

        // Validate and get Employee ID
        Long empId = readNonNegativeLongInput(scanner, "Employee ID");

        // Validate and get Project ID
        Long projectId = readNonNegativeLongInput(scanner, "Project ID");

        // Validate and get Start Date
        Date startDate = readDateInput(scanner, "Start Date");

        // Validate and get End Date
        Date endDate = readDateInput(scanner, "End Date");

        // Add the new employee to the list
        TeamWork newTeamWork = new TeamWork(empId, projectId, startDate, endDate);
        teamWorkList.add(newTeamWork);

        // Update the CSV file
        CSVWriter.writeCsvFile("employees.csv", teamWorkList);

        System.out.println("Employee added successfully!");
    }

    private static Long readLongInput(Scanner scanner, String inputType, List<TeamWork> teamWorkList) {
        while (true) {
            System.out.print("Enter " + inputType + ": ");
            if (scanner.hasNextLong()) {
                Long input = scanner.nextLong();

                // Additional validation
                if (input >= 0 && !idExists(teamWorkList, input)) {
                    return input; // Exit the loop if input is valid and the ID doesn't exist
                } else if (input < 0) {
                    System.out.println(inputType + " must be a non-negative number.");
                } else {
                    System.out.println(inputType + " already exists. Please choose a different " + inputType + ".");
                }
            } else {
                System.out.println("Please enter a valid " + inputType + " (a number).");
                scanner.next(); // Consume invalid input
            }
        }
    }

    private static boolean idExists(List<TeamWork> teamWorkList, Long empId) {
        return teamWorkList.stream().anyMatch(teamWork -> teamWork.getEmpId().equals(empId));
    }

    private static Long readNonNegativeLongInput(Scanner scanner, String inputName) {
        Long input;
        do {
            System.out.print("Enter " + inputName + ": ");
            while (!scanner.hasNextLong()) {
                System.out.println("Please enter a valid " + inputName + " (a non-negative number).");
                scanner.next(); // Consume invalid input
            }
            input = scanner.nextLong();
            if (input < 0) {
                System.out.println("Please enter a non-negative " + inputName + ".");
            }
        } while (input < 0);
        return input;
    }


    private static Date readDateInput(Scanner scanner, String inputType) {
        System.out.print("Enter " + inputType + " (yyyy-MM-dd): ");
        String dateStr = scanner.next();
        return CSVReader.parseDate(dateStr);
    }

    private static void editEmployee(List<TeamWork> teamWorkList) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Editing Employee:");
        System.out.print("Enter Employee ID to edit: ");

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a valid ID");
            scanner.next(); // Consume invalid input
        }

        Long empIdToEdit = scanner.nextLong();

        TeamWork employeeToEdit = findEmployeeById(teamWorkList, empIdToEdit);

        if (employeeToEdit != null) {
            // Display details
            System.out.println("Details of Employee with ID " + empIdToEdit + ":");
            System.out.println(employeeToEdit);

            // Get new details
            System.out.print("Enter new Project ID: ");

            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid ProjectID");
                scanner.next(); // Consume invalid input
            }

            Long newProjectId = scanner.nextLong();
            System.out.print("Enter new Start Date (yyyy-MM-dd): ");

            String newStartDateStr = scanner.next();
            Date newStartDate = CSVReader.parseDate(newStartDateStr);
            System.out.print("Enter new End Date (yyyy-MM-dd): ");
            String newEndDateStr = scanner.next();
            Date newEndDate = newEndDateStr.isEmpty() ? new Date() : CSVReader.parseDate(newEndDateStr);

            // Update details
            employeeToEdit.setProjectId(newProjectId);
            employeeToEdit.setStartDate(newStartDate);
            employeeToEdit.setEndDate(newEndDate);

            CSVWriter.writeCsvFile("employees.csv", teamWorkList);

            System.out.println("Employee details updated successfully!");
        } else {
            System.out.println("Employee with ID " + empIdToEdit + " not found.");
        }
    }
    private static void deleteEmployee(List<TeamWork> teamWorkList) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the Employee ID to delete:");

        while (!scanner.hasNextInt()) {
            System.out.println("Please enter an Employee ID.");
            scanner.next(); // Consume invalid input
        }

        long employeeIdToDelete = scanner.nextLong();

        boolean employeeFound = false;

        Iterator<TeamWork> iterator = teamWorkList.iterator();
        while (iterator.hasNext()) {
            TeamWork teamWork = iterator.next();
            if (teamWork.getEmpId() == employeeIdToDelete) {
                iterator.remove();
                System.out.println("Employee with ID " + employeeIdToDelete + " deleted successfully!");
                employeeFound = true;
                break;
            }
        }

        if (!employeeFound) {
            System.out.println("Employee with ID " + employeeIdToDelete + " not found.");
        } else {
            // Update the CSV file after deletion
            CSVWriter.writeCsvFile("employees.csv", teamWorkList);
        }
    }



    private static WorkedTogetherResult findMostTimeWorkedPair(List<TeamWork> teamWorkList) {
        WorkedTogetherResult longestWorkedTogetherResult = null;
        Long maxDaysWorked = 0L;

        for (int i = 0; i < teamWorkList.size(); i++) {
            for (int j = i + 1; j < teamWorkList.size(); j++) {
                TeamWork teamWork1 = teamWorkList.get(i);
                TeamWork teamWork2 = teamWorkList.get(j);

                if (haveWorkedTogether(teamWork1, teamWork2)) {
                    Long daysWorked = computeDaysWorked(teamWork1) + computeDaysWorked(teamWork2);
                    if (daysWorked > maxDaysWorked) {
                        maxDaysWorked = daysWorked;
                        longestWorkedTogetherResult = new WorkedTogetherResult(
                                teamWork1.getProjectId(),
                                teamWork1.getEmpId(),
                                teamWork2.getEmpId(),
                                daysWorked / 2
                        );
                    }
                }
            }
        }

        return longestWorkedTogetherResult;
    }

    private static WorkedTogetherResult findLongestWorkedTogether(List<TeamWork> teamWorkList) {
        WorkedTogetherResult longestWorkedTogetherResult = null;
        Long maxDaysWorked = 0L;

        for (int i = 0; i < teamWorkList.size(); i++) {
            for (int j = i + 1; j < teamWorkList.size(); j++) {
                TeamWork teamWork1 = teamWorkList.get(i);
                TeamWork teamWork2 = teamWorkList.get(j);

                if (haveWorkedTogether(teamWork1, teamWork2)) {
                    Long daysWorked = computeDaysWorked(teamWork1) + computeDaysWorked(teamWork2);
                    if (daysWorked > maxDaysWorked) {
                        maxDaysWorked = daysWorked;
                        longestWorkedTogetherResult = new WorkedTogetherResult(
                                teamWork1.getProjectId(),
                                teamWork1.getEmpId(),
                                teamWork2.getEmpId(),
                                daysWorked / 2
                        );
                    }
                }
            }
        }

        return longestWorkedTogetherResult;
    }

    private static boolean haveWorkedTogether(TeamWork teamWork1, TeamWork teamWork2) {
        // Check if the work periods overlap
        return !teamWork1.getEndDate().before(teamWork2.getStartDate())
                && !teamWork2.getEndDate().before(teamWork1.getStartDate());
    }

    private static long computeDaysWorked(TeamWork teamWork) {
        Instant startInstant = teamWork.getStartDate().toInstant();
        LocalDate startDate = startInstant.atZone(ZoneId.systemDefault()).toLocalDate();

        Instant endInstant = teamWork.getEndDate() != null
                ? teamWork.getEndDate().toInstant()
                : LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant();

        return ChronoUnit.DAYS.between(startDate, endInstant.atZone(ZoneId.systemDefault()).toLocalDate());
    }


    private static TeamWork findEmployeeById(List<TeamWork> teamWorkList, Long empId) {
        for (TeamWork teamWork : teamWorkList) {
            if (teamWork.getEmpId().equals(empId)) {
                return teamWork;
            }
        }
        return null;
    }
}
