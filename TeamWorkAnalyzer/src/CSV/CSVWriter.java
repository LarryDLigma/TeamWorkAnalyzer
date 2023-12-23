package CSV;

import com.example.TeamWorkAnalyzer.models.TeamWork;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CSVWriter {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void writeCsvFile(String filePath, List<TeamWork> teamWorkList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (TeamWork teamWork : teamWorkList) {
                String line = teamWork.getEmpId() + ","
                        + teamWork.getProjectId() + ","
                        + dateFormat.format(teamWork.getStartDate()) + ","
                        + dateFormat.format(teamWork.getEndDate()) + "\n";
                writer.write(line);
            }
            System.out.println("CSV file written successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
