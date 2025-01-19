package manager;

import tasks.Epic;
import tasks.Subtask;
import tasks.Task;

import java.util.*;

public class InMemoryTaskManager implements TaskManager { // Данный класс отвечает за управление всеми задачами

    private HashMap<Integer, Task> tasks = new HashMap<>(); // Храним задачи
    private HashMap<Integer, Epic> epics = new HashMap<>(); // Храним эпики
    private HashMap<Integer, Subtask> subtasks = new HashMap<>(); // Храним подзадачи эпиков
    private HistoryManager historyManager = Managers.getDefaultHistory();
    private static int generateId = 0;


    @Override
    public Integer generateId() { //Генерирует новый id
        return ++generateId;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public Integer addNewTask(Task task) { // Добавление и сохранение новой ЗАДАЧИ
        task.setId(generateId());
        tasks.put(task.getId(), task);

        return task.getId();
    }

    @Override
    public Integer addNewEpic(Epic epic) { // Добавление и сохранение нового ЭПИКА
        epic.setId(generateId());
        epics.put(epic.getId(), epic);

        return epic.getId();
    }

    @Override
    public Integer addNewSubtask(Subtask subtask) { // Добавление и сохранение новой ПОДЗАДАЧИ
        Epic epic = epics.get(subtask.getEpicId());
        if(epic == null) {
            throw new RuntimeException("Нельзя добавить подзадачу к несуществующему Эпику");
        } else {
            subtask.setId(generateId());
            epic.addEpicSubtasks(subtask.getId(), subtask);
            subtasks.put(subtask.getId(), subtask);
            return subtask.getId();
        }
    }

    /**
    На данном этапе мы выполняем манипуляции
    со всеми видами задач
     */

    @Override
    public List<Task> getAllTasks() { // Вывод всех ТАСКОВ
        List<Task> allTasks = new ArrayList<>(tasks.values());
        return allTasks;
    }

    @Override
    public List<Epic> getAllEpics() { // Вывод всех ЭПИКОВ
        List<Epic> allEpics = new ArrayList<>(epics.values());
        return allEpics;
    }

    @Override
    public List<Subtask> getAllSubTasks() { // Вывод всех САБТАСКОВ
        List<Subtask> allSubtasks = new ArrayList<>(subtasks.values());
        return allSubtasks;
    }



    @Override
    public void removeAllTasks() { // Удаление всех ТАСКОВ
        tasks.clear();
        System.out.println("Список задач очищен");
    }

    @Override
    public void removeAllEpics() { // Удаление всех ЭПИКОВ и соответственно всех САБТАСКОВ
        epics.clear();
        subtasks.clear();
        System.out.println("Список эпиков очищен");
    }

    @Override
    public void removeAllSubtasks() { // Удаление всех САБТАСКОВ
        subtasks.clear();
        System.out.println("Список подзадач очищен");
    }



    @Override
    public Optional<Task> getTaskById(int id) { // Поиск ТАСКИ по ID
        Optional<Task> task = Optional.empty();
        if (tasks.containsKey(id)) {
            task = Optional.of(tasks.get(id));
        } else {
            System.out.println("Задачи с таким ID не существует!");
        }
        return task;
    }

    @Override
    public Optional<Epic> getEpicById(int id) { // Поиск ЭПИКА по ID
        Optional<Epic> epic = Optional.empty();
        if (epics.containsKey(id)) {
            epic = Optional.of(epics.get(id));
        } else {
            System.out.println("Задачи с таким ID не существует!");
        }
        return epic;
    }

    @Override
    public Optional<Subtask> getSubtaskById(int id) { // Поиск САБТАСКИ по ID
        Optional<Subtask> subtask= Optional.empty();
        if (subtasks.containsKey(id)) {
            subtask = Optional.of(subtasks.get(id));
        } else {
            System.out.println("Задачи с таким ID не существует!");
        }
        return subtask;
    }



    @Override
    public void removeTaskById(int id) {  // Удаление ТАСКИ по ID
        tasks.remove(id);
        System.out.println("Задача с id " + id + " удалена!");
    }

    @Override
    public void removeEpicById(int id) { // Удаление ЭПИКА по ID и всех его подзадач
        epics.get(id).getEpicSubtasks().clear();
        epics.remove(id);
        System.out.println("Эпик с id " + id + " и его подзадачи удалены!");
    }

    @Override
    public void removeSubtaskById(int id) { // Удаление САБТАСКИ по ID
        subtasks.remove(id);
        System.out.println("Подзадача с id " + id + " удалена!");
    }



    @Override
    public HashMap<Integer, Subtask> getSubtasksByEpicId(int epicId) { // Выводим все САБТАСКИ определенного ЭПИКА
        if (epics.containsKey(epicId)) {
            return epics.get(epicId).getEpicSubtasks();
        } else {
            System.out.println("Эпика с id " + epicId + " не существует!");
            return new HashMap<>();
        }
    }



    @Override
    public void updateTask(int id, Task newTask) { // Обновление уже имеющейся ТАСКИ
        if(tasks.containsKey(id)) {
            newTask.setId(id);
            tasks.replace(id, newTask);
            System.out.println("Обновлена задача с id " + id);
        } else {
            System.out.println("Задача с id " + id + " не существует, проверьте корректность данных!");
        }
    }

    @Override
    public void updateEpic(int id, Epic newEpic) { // Обновление уже имеющегося ЭПИКА
        if(epics.containsKey(id)) {
            newEpic.setEpicSubtasks(epics.get(id).getEpicSubtasks());
            newEpic.setId(id);
            epics.replace(id, newEpic);

            System.out.println("Обновлен Эпик с id " + id);
        } else {
            System.out.println("Эпика с id " + id + " не существует, проверьте корректность данных!");
        }
    }

    @Override
    public void updateSubtask(int id, Subtask newSubtask) { // Обновление уже имеющейся ПОДЗАДАЧИ
        if (subtasks.containsKey(id)) {
            newSubtask.setId(id);
            subtasks.replace(id, newSubtask);
            epics.get(id).addEpicSubtasks(id, newSubtask);
            updateEpicStatus(epics.get(id));
            System.out.println("Подзадача с id " + newSubtask.getId() + " обновлена!");
        } else {
                    System.out.println("Подзадачи с id " + newSubtask.getId() + " не существует, " +
                            "проверьте корректность данных!");
                }
    }


    @Override
    public void updateEpicStatus(Epic epic) { //Обновление статуса ЭПИКА
        int statusNew = 0;
        int statusInProgress = 0;
        int statusDone = 0;

        // Перебор всех подзадач по ключу
        for (Integer key : epic.getEpicSubtasks().keySet()) {
            Subtask subtask = epic.getEpicSubtasks().get(key); // Получаем подзадачу по ключу
            if (subtask.getStatus().equals(Status.NEW)) {
                statusNew++;
            }
            if (subtask.getStatus().equals(Status.IN_PROGRESS)) {
                statusInProgress++;
            }
            if (subtask.getStatus().equals(Status.DONE)) {
                statusDone++;
            }
        }
        // Логика для обновления статуса эпика
        if (statusNew == 0 && statusInProgress == 0) {
            epic.setStatus(Status.DONE);
        } else if (statusInProgress == 0 && statusDone == 0 && !epic.getEpicSubtasks().isEmpty()) {
            epic.setStatus(Status.NEW);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }



    public HashMap<Integer, Task> getTasks() {
        return tasks;
    }

    public HashMap<Integer, Epic> getEpics() {
        return epics;
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }
}