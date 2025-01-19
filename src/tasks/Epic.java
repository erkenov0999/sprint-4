package tasks;

import manager.Status;

import java.util.HashMap;

public class Epic extends Task {
    private HashMap<Integer, Subtask> EpicSubtasks;


    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        this.EpicSubtasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getEpicSubtasks() {
        return EpicSubtasks;
    }

    public void setEpicSubtasks(HashMap<Integer, Subtask> epicSubtasks) {
        EpicSubtasks = epicSubtasks;
    }

    public void addEpicSubtasks(int subtaskId, Subtask subtask) {
        EpicSubtasks.put(subtaskId, subtask);
    }
}
