import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for reading and parsing log entries from a CSV file
 * into structured LogEvent objects.
 */
public class LogParser {
  public List<LogEvent> parse(String filePath) {
    List<LogEvent> events = new ArrayList<>();

    try {
      // Reads every line from the file
      List<String> lines = Files.readAllLines(Path.of(filePath));
      for (String line : lines) {
        // Splits the lines by ','
        String[] parts = line.split(",");
        String time = parts[0].trim();
        String description = parts[1].trim();
        String type = parts[2].trim(); //START or END
        int pid = Integer.parseInt(parts[3].trim());

        events.add(new LogEvent(time, description, type, pid));
      }
    } catch (IOException e) {
      System.err.println("Failed to read file: " + e.getMessage());
    }

    return events;
  }
}
