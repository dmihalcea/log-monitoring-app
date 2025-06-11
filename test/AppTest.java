public class LogMonitoringAppTest {

  @Test
  void testLogParser_parsesCorrectly() throws IOException {
    // Arrange
    String content = """
            10:00:00, Job A, START, 101
            10:10:00, Job A, END, 101
            10:15:00, Job B, START, 102
            10:35:00, Job B, END, 102
        """;

    Path tempFile = Files.createTempFile("logtest", ".log");
    Files.writeString(tempFile, content);

    LogParser parser = new LogParser();

    // Act
    List<LogEvent> events = parser.parse(tempFile.toString());

    // Assert
    assertEquals(4, events.size());
    assertEquals("Job A", events.get(0).description);
    assertEquals("START", events.get(0).action);
    assertEquals(101, events.get(0).pid);
  }

  @Test
  void testJobMonitor_matchesStartAndEnd() {
    // Arrange
    List<LogEvent> events = List.of(
        new LogEvent("10:00:00", "Job A", "START", 1),
        new LogEvent("10:05:00", "Job A", "END", 1),
        new LogEvent("10:10:00", "Job B", "START", 2),
        new LogEvent("10:30:00", "Job B", "END", 2)
    );

    JobMonitor monitor = new JobMonitor();

    // Act
    List<JobInfo> jobs = monitor.analyze(events);

    // Assert
    assertEquals(2, jobs.size());
    assertEquals(Duration.ofMinutes(5), jobs.get(0).getDuration());
    assertEquals(Duration.ofMinutes(20), jobs.get(1).getDuration());
  }

  @Test
  void testJobMonitor_ignoresUnpairedEnds() {
    // Arrange
    List<LogEvent> events = List.of(
        new LogEvent("10:00:00", "Job A", "END", 1) // no START
    );

    JobMonitor monitor = new JobMonitor();

    // Act
    List<JobInfo> jobs = monitor.analyze(events);

    // Assert
    assertEquals(0, jobs.size());
  }

  @Test
  void testJobInfo_durationIsCorrect() {
    JobInfo job = new JobInfo(
        42,
        "Sample",
        LocalTime.parse("10:00:00"),
        LocalTime.parse("10:15:00")
    );

    assertEquals(Duration.ofMinutes(15), job.getDuration());
  }
}