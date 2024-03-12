# Cron Parser
_Cron Parser is a simple Java application that parses a cron expression and prints the corresponding schedule. It supports the standard 6-field cron format (minute, hour, day of month, month, day of week, command)._

**Usage**

To use the Cron Parser, run the application with a cron expression as the command-line argument. For example:

    java -jar CronParser.jar "*/15 0 1,15 * 1-5 /usr/bin/find"
The above command will parse the cron expression _"*/15 0 1,15 * 1-5 /usr/bin/find"_ and print the schedule.

**Cron Expression Format**

The cron expression consists of 6 space-separated fields:

* Minute (0 - 59)
* Hour (0 - 23)
* Day of month (1 - 31)
* Month (1 - 12)
* Day of week (0 - 7, where 0 and 7 represent Sunday)
* Command (the command to be executed)
* Fields can contain single values, lists (comma-separated), ranges (using '-'), and step values (using '/'). Use "*" to indicate all possible values.

**Examples**

* [* * * * * /usr/bin/find]: Run every minute.
* [0 0 * * * /usr/bin/find]: Run every day at midnight.
* [*/15 0 1,15 * 1-5 /usr/bin/find]: Run every 15 minutes on the 1st and 15th of each month, Monday to Friday.

**Dependencies**

  Java 8 or higher
  Spring Boot (included in the project)
