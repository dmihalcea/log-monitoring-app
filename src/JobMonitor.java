import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * JobMonitor is responsible for analyzing log events,
 * matching START and END entries for each job based on PID
 * and generating JobInfo objects with execution time.
 */
public class JobMonitor {
  public List<JobInfo> analyze(List<LogEvent> events) {
    // Map to store START events temporarily by PID
    Map<Integer, LogEvent> startEvents = new HashMap<>();
    // Final list of matched jobs
    List<JobInfo> jobs = new ArrayList<>();

    for (LogEvent event : events) {
      if (event.type.equals("START")) {
        // Store START event by PID
        startEvents.put(event.pid, event);
      } else if (event.type.equals("END")) {
        // Match END with corresponding START
        LogEvent start = startEvents.remove(event.pid);
        if (start != null) {
          // Parse time values and create JobInfo
          LocalTime startTime = LocalTime.parse(start.time);
          LocalTime endTime = LocalTime.parse(event.time);
          JobInfo job = new JobInfo(event.pid, event.description, startTime, endTime);
          jobs.add(job);
        }
      }
    }
    return jobs;
  }
}
