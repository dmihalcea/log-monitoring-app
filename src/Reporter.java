import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

/**
 * Reporter is responsible for generating a text report of job execution durations, categorized by
 * severity level (INFO, WARNING or ERROR).
 */
public class Reporter {

  public void generateReport(List<JobInfo> jobs) {
    try (FileWriter writer = new FileWriter("output.txt")) {
      // Iterate over each job and calculate its duration
      for (JobInfo job : jobs) {
        Duration duration = job.getDuration();
        long minutes = duration.toMinutes();

        // Classify job duration and write appropriate log level
        if (minutes > 10) {
          writer.write(String.format("[ERROR] Job %d (%s) took %d minutes\n",
              job.pid, job.description, minutes));
        } else if (minutes > 5) {
          writer.write(String.format("[WARNING] Job %d (%s) took %d minutes\n",
              job.pid, job.description, minutes));
        } else {
          if (minutes > 1) {
            writer.write(String.format("[INFO] Job %d (%s) took %d minutes\n",
                job.pid, job.description, minutes));
          } else if (minutes == 1) {
            writer.write(String.format("[INFO] Job %d (%s) took %d minute\n",
                job.pid, job.description, minutes));
          } else {
            writer.write(String.format("[INFO] Job %d (%s) took less than 1 minute\n",
                job.pid, job.description, minutes));
          }
        }
      }
    } catch (IOException e) {
      System.err.println("Error writing report: " + e.getMessage());
    }
  }
}
