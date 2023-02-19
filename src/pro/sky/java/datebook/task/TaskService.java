package pro.sky.java.datebook.task;

import java.time.LocalDate;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashMap;
import java.util.HashSet;

public class TaskService {

    private final Map<Integer, Task> tasksMap = new HashMap<>();
    private final Set<Task> removedTasks = new HashSet<>();

    public void add(Task task) {
        tasksMap.put(task.getId(), task);
    }

    public List<Task> getAllByDate(LocalDate localDate) throws TaskNotFoundException {
        List<Task> tasksList = new ArrayList<>();
        for (Map.Entry<Integer, Task> entry : tasksMap.entrySet()) {
            if (entry.getValue().appearsIn(localDate)) {
                tasksList.add(entry.getValue());
            }
        }
        if (tasksList.isEmpty()) {
            throw new TaskNotFoundException("There are no tasks for this date.");
        }
        return tasksList;
    }

    public void remove(int id) throws TaskNotFoundException {
        for (Map.Entry<Integer, Task> entry : tasksMap.entrySet()) {
            if (entry.getKey() == id) {
                tasksMap.remove(entry.getKey());
            } else {
                throw new TaskNotFoundException("There is no task with this id.");
            }
        }
    }
}
