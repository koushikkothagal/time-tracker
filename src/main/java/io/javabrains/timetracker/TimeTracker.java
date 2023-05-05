package io.javabrains.timetracker;

import io.javabrains.timetracker.data.Category;
import io.javabrains.timetracker.data.CurrentTasks;
import io.javabrains.timetracker.data.Task;
import io.javabrains.timetracker.util.ArgUtil;
import io.javabrains.timetracker.util.Args;
import io.javabrains.timetracker.util.Commands;
import io.javabrains.timetracker.util.FileUtil;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Map;

public class TimeTracker {
    public static void main(String[] args) throws URISyntaxException, IOException {

        ArgUtil argUtil = new ArgUtil();
        Args arguments = argUtil.parseArgs(args);

        FileUtil fileUtil = new FileUtil();
        CurrentTasks currentTasks = fileUtil.getSavedTasks();
        switch (arguments.getCommand()) {
            case TASK_START -> {
                Task task = new Task(arguments.getTaskName(), new Category(arguments.getCategoryName()));
                currentTasks.startTask(task);
            }
            case TASK_STOP -> currentTasks.completeTask(arguments.getTaskName());
            case REPORT_TASKS -> {
                Map<String, Duration> taskReport = currentTasks.getTaskReport();
                for (Map.Entry<String, Duration> entry : taskReport.entrySet()) {
                    System.out.println("Task: " + entry.getKey());
                    System.out.println("Duration in minutes: " + entry.getValue().toMinutes());
                }
            }
            case REPORT_CATEGORIES -> {
                Map<String, Duration> categoryReport = currentTasks.getCategoryReport();
                for (Map.Entry<String, Duration> entry : categoryReport.entrySet()) {
                    System.out.println("Category: " + entry.getKey());
                    System.out.println("Duration in minutes: " + entry.getValue().toMinutes());
                }
            }
        };

        fileUtil.saveTasksToFile(currentTasks);








    }

}
