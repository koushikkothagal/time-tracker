package io.javabrains.timetracker.data;

import io.javabrains.timetracker.Logger;
import io.javabrains.timetracker.data.Task;
import io.javabrains.timetracker.data.TaskStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurrentTasks {

    private Map<String, Task> currentTasks = new HashMap<>();

    public CurrentTasks(Map<String, Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    public void startTask(Task task) {
        if (currentTasks.putIfAbsent(task.getTaskName(), task) != null) {
            Logger.log("Task already exists, skipping");
        }
    }

    public void completeTask(String taskName) {
        Task existingTask = currentTasks.get(taskName);
        if (existingTask == null) {
            Logger.log("No tasks found");
        } else {
            existingTask.setEndTime(LocalDateTime.now());
            existingTask.setStatus(TaskStatus.COMPLETE);
        }
    }

    public Map<String, Duration> getTaskReport() {
        return currentTasks
                .values()
                .stream()
                .filter(task -> task.getEndTime() != null)
                .collect(Collectors.toMap(Task::getTaskName, Task::getTaskDuration));
    }

    public Map<String, Duration> getCategoryReport() {

        Map<String, Duration> categoryReport = new HashMap<>();
        currentTasks
                .values()
                .stream()
                .filter(task -> task.getEndTime() != null)
                .forEach(task -> {
                    String category = task.getCategory().getName();
                    Duration categoryDuration = categoryReport.getOrDefault(category, Duration.ZERO);
                    categoryReport.put(category, categoryDuration.plus(task.getTaskDuration()));
                });
        return categoryReport;


    }

    public Map<String, Task> getCurrentTasks() {
        return currentTasks;
    }

    public void setCurrentTasks(Map<String, Task> currentTasks) {
        this.currentTasks = currentTasks;
    }

    @Override
    public String toString() {
        return "CurrentTasks{" +
                "currentTasks=" + currentTasks +
                '}';
    }
}
