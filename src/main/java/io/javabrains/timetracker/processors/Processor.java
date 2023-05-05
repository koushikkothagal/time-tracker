package io.javabrains.timetracker.processors;

import io.javabrains.timetracker.data.Task;

public interface Processor {

    public void process(Task task);
}
