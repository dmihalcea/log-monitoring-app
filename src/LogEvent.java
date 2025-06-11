/**
 * Represents a single log entry, either a START or END event,
 * for a given PID and job description.
 */
public class LogEvent {

  public final String time; // HH:MM:SS is a timestamp in hours, minutes, and seconds
  public final String description; // The job description
  public final String type; // START or END
  public final int pid; // Unique id

  public LogEvent(String time, String description, String type, int pid) {
    this.time = time;
    this.description = description;
    this.type = type;
    this.pid = pid;
  }
}
