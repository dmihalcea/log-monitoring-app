# Log Monitoring App

A Java application that analyzes job log events from a CSV file, calculates the duration of each job and generates a report file **('output.txt')** with severity levels based on job duration:
- **ERROR** – if job takes over 10 minutes
- **WARNING** – if job takes over 5 minutes
- **INFO** – if job takes 5 minutes or less

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