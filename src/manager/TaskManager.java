package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface TaskManager {

    Integer generateId(); // Генерирует ID для каждого из видов задач

    Integer addNewTask(Task task); // Добавление и сохранение новой ЗАДАЧИ

    Integer addNewEpic(Epic epic); // Добавление и сохранение нового ЭПИКА

    Integer addNewSubtask(Subtask subtask); // Добавление и сохранение новой ПОДЗАДАЧИ

    List<Task> getAllTasks(); // Вывод всех ЗАДАЧ

    List<Epic> getAllEpics(); // Вывод всех ЭПИКОВ

    List<Subtask> getAllSubTasks(); // Вывод всех САБТАСКОВ

    void removeAllTasks(); // Удаление всех ТАСКОВ

    void removeAllEpics(); // Удаление всех ЭПИКОВ и соответственно всех САБТАСКОВ

    void removeAllSubtasks(); // Удаление всех САБТАСКОВ

    Optional<Task> getTaskById(int id); // Поиск ТАСКИ по ID

    Optional<Epic> getEpicById(int id); // Поиск ЭПИКА по ID

    Optional<Subtask> getSubtaskById(int id); // Поиск САБТАСКИ по ID

    void removeTaskById(int id); // Удаление ТАСКИ по ID

    void removeEpicById(int id); // Удаление ЭПИКА по ID и всех его подзадач

    void removeSubtaskById(int id); // Удаление САБТАСКИ по ID

    HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId); // Выводим все САБТАСКИ определенного ЭПИКА

    void updateTask(int id, Task newTask); // Обновление уже имеющейся ТАСКИ

    void updateEpic(int id, Epic newEpic); // Обновление уже имеющегося ЭПИКА

    void updateSubtask(int id, Subtask newSubtask); // Обновление уже имеющейся ПОДЗАДАЧИ

    void updateEpicStatus(Epic epic); // Обновление статуса ЭПИКА

    List<Task> getHistory();
}
