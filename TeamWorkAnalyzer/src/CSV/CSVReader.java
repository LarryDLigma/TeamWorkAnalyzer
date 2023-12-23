package CSV;

import com.example.TeamWorkAnalyzer.models.TeamWork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CSVReader {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static List<TeamWork> readCsvFile(String filePath) {
        List<TeamWork> teamWorkList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Long empId = Long.parseLong(parts[0]);
                Long projectId = Long.parseLong(parts[1]);
                Date startDate = parseDate(parts[2]);
                Date endDate = parseDate(parts[3]);

                TeamWork teamWork = new TeamWork(empId, projectId, startDate, endDate);
                teamWorkList.add(teamWork);
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return teamWorkList;
    }

    public static Date parseDate(String dateStr) {
        if ("NULL".equalsIgnoreCase(dateStr.trim())) {
            return new Date(); // Return current date for "NULL"
        }

        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Return null if parsing fails
        }
    }
}
