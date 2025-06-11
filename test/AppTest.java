import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalTime;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AppTest {

  @Test
  void testLogParserParsesCorrectly() throws IOException {

    String content = """
            10:00:00, Job A, START, 101
            10:10:00, Job A, END, 101
            10:15:00, Job B, START, 102
            10:35:00, Job B, END, 102
        """;

    Path tempFile = Files.createTempFile("logtest", ".log");
    Files.writeString(tempFile, content);

    LogParser parser = new LogParser();
    List<LogEvent> events = parser.parse(tempFile.toString());

    assertEquals(4, events.size());
    assertEquals("Job A", events.get(0).description);
    assertEquals("START", events.get(2).type);
    assertEquals(101, events.get(0).pid);
  }

  @Test
  void testJobMonitorMatchesStartAndEnd() {

    List<LogEvent> events = List.of(
        new LogEvent("10:00:00", "Job A", "START", 1),
        new LogEvent("10:05:00", "Job A", "END", 1),
        new LogEvent("10:10:00", "Job B", "START", 2),
        new LogEvent("10:30:00", "Job B", "END", 2)
    );

    JobMonitor monitor = new JobMonitor();
    List<JobInfo> jobs = monitor.analyze(events);

    assertEquals(2, jobs.size());
    assertEquals(Duration.ofMinutes(5), jobs.get(0).getDuration());
    assertEquals(Duration.ofMinutes(20), jobs.get(1).getDuration());
  }

  @Test
  void testJobMonitorIgnoresUnpairedEnds() {

    List<LogEvent> events = List.of(
        new LogEvent("10:00:00", "Job A", "END", 1) // no START
    );

    JobMonitor monitor = new JobMonitor();
    List<JobInfo> jobs = monitor.analyze(events);

    assertEquals(0, jobs.size());
  }

  @Test
  void testJobInfoDurationIsCorrect() {
    JobInfo job = new JobInfo(42, "scheduled task test",
        LocalTime.parse("10:00:00"), LocalTime.parse("10:15:00")
    );

    assertEquals(Duration.ofMinutes(15), job.getDuration());
  }
}