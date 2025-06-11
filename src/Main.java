public class Main {
  public static void main(String[] args) {
    String logFilePath = "logs.log";

    LogParser parser = new LogParser();
    // Parse the logs into event logs
    var events = parser.parse(logFilePath);

    JobMonitor monitor = new JobMonitor();
    // Identifies Start & End for each Job
    var jobDurations = monitor.analyze(events);

    Reporter reporter = new Reporter();
    // Generates the output file (INFO, WARNING, ERROR)
    reporter.generateReport(jobDurations);
  }
}
