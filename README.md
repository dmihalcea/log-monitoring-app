# Log Monitoring App

A Java application that analyzes job log events from a CSV file, calculates the duration of each job and generates a report file **('output.txt')** with severity levels based on job duration.

## How It Works

1. **Parses logs** from a CSV file containing `time`, `description`, `type` and `pid`.
2. **Matches START and END events** for each PID.
3. **Calculates job durations** using timestamps.
4. **Categorizes each job** based on how long it took:
    - Over **10 minutes** → `ERROR`
    - Over **5 minutes** → `WARNING`
    - Otherwise → `INFO`
5. **Writes results** to `output.txt`.

## Usage
1. Add your `logs.csv` file in the `resources/` directory.
2. Run `Main.java`.
3. Check `output.txt` for the generated report.

## Example Log Entry
- 11:35:23,scheduled task 032, START,37980
- 11:35:56,scheduled task 032, END,37980

## Output Examples
- [ERROR] Job 39547 (scheduled task 051) took 11 minutes
- [WARNING] Job 87228 (scheduled task 268) took 9 minutes
- [INFO] Job 37980 (scheduled task 032) took less than 1 minute

## Author
Dragos Mihalcea