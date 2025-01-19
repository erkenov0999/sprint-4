import manager.InMemoryTaskManager;
import manager.Status;
import manager.TaskManager;
import tasks.Task;

public class Main {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        Task task1 = new Task("Task1", "Taskaaa111", Status.NEW);
        Task task2 = new Task("Task2", "Taskaaa222", Status.NEW);
        Task task3 = new Task("Task3", "Taskaaa333", Status.NEW);
        Task task4 = new Task("Task4", "Taskaaa444", Status.NEW);
        manager.addNewTask(task1);
        manager.addNewTask(task2);
        manager.addNewTask(task3);
        manager.addNewTask(task4);

        System.out.println(manager.getAllTasks());
    }
}