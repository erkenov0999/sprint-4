package manager;

import tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private List<Task> historyTasks = new ArrayList<>(10);

    @Override
    public void add(Task task) {
        historyTasks.add(task);
    }

    @Override
    public List<Task> getHistory() {
        return historyTasks;
    }
}
