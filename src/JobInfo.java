import java.time.Duration;
import java.time.LocalTime;

/**
 * Represents a job's execution metadata, including its PID,
 * description, start time and end time.
 */
public class JobInfo {

  public final int pid; // Unique id
  public final String description; // The job description
  public final LocalTime startTime; // The timestamp for the Start Job
  public final LocalTime endTime; // The timestamp for the End Job

  public JobInfo(int pid, String description, LocalTime startTime, LocalTime endTime) {
    this.pid = pid;
    this.description = description;
    this.startTime = startTime;
    this.endTime = endTime;
  }

  // Calculates and returns the Job duration
  public Duration getDuration() {
    return Duration.between(startTime, endTime);
  }
}
