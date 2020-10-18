package pl.sdajp.java26.spring.jpa;

import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.SQLException;
import java.util.List;

@RestController
public class TestController {
//    private final TaskDao taskDao = new TaskDao();
    private final TaskDao taskDao;
    private final TaskDaoJdbc taskDaoJdbc;



    public TestController() {
        taskDao = new TaskDao();
        this.taskDaoJdbc = new TaskDaoJdbc();
    }

    @GetMapping("/test")
    public String test(){
        return "Hello";
    }

    @PostMapping("/task")
    public TodoTask createNewTask(@RequestParam String taskName,
                                  @RequestParam String userName) {
        System.out.println("taskName: " + taskName);
        System.out.println("userName: " + userName);
        return taskDao.saveTasksForGivenUser(taskName, userName);
        //todo: save new Task for given User - if User doesn't exist, create new one.

    }
    @PostMapping("/import")
    public  String initialImport(){
//        final TaskDao taskDao = new TaskDao();
        taskDao.importInitialData();
        return "OK";
    }
    @GetMapping("/tasks")
    public List<TodoTask> getTasks(){
//final TaskDao taskDao = new TaskDao();
final List<TodoTask> allTasks = taskDao.getAllTasks();
return allTasks;

    }
    @PostMapping("/importWithUsers")
    public String initialImportWithUsers() {
//        final TaskDao taskDao = new TaskDao();
        taskDao.importTaskUsers();
        return "OK";
    }

    @GetMapping("/tasks/countByUser")
    public List<TaskCountByUser> getTasksCountByUser(){
//        final TaskDao taskDao = new TaskDao();
        return taskDao.getTaskCountByUser();
    }
    @GetMapping("/tasks/countByUserJava")
    public List<TaskCountByUser> getTasksCountByUserJava(){
//        final TaskDao taskDao = new TaskDao();
        return taskDao.getTaskCountByUserJV();
    }
    @GetMapping("/tasks/countByUserjdbc")
    public  List<TaskCountByUser> getCountByUserJDBC() throws SQLException {
        return taskDaoJdbc.getTasksCountByUser();
    }
}
