package pl.sdajp.java26.spring.jpa;

import org.hibernate.annotations.Persister;
import org.springframework.scheduling.config.Task;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDao {
    private final static String DB_Connection = "jdbc:mysql://localhost:3306/java26jpa?serverTimezone=UTC";
    Connection connection;
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("mySql");

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(DB_Connection, "darek2", "tajnehaslo");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    private void closeConnection() {
        try {
            connection.close();
        } catch (SQLException throwables) {
            System.out.println("Not opened");
        }
    }


    public void importInitialData() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final TodoTask todoTask = new TodoTask();
        todoTask.setTaskName("Moje pierwsze zadanie");

        entityManager.getTransaction().begin();
        entityManager.persist(todoTask);
        entityManager.getTransaction().commit();

        entityManager.close();
    }


    public void importTaskUsers() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final User user1 = new User("John");
        final User user2 = new User("Jerry");

        final TodoTask todoTask1 = new TodoTask();
        todoTask1.setTaskName("Zadanie1");
        todoTask1.setUser(user1);

        final TodoTask todoTask2 = new TodoTask();
        todoTask2.setTaskName("Zadanie2");
        todoTask2.setUser(user2);

        final TodoTask todoTask3 = new TodoTask();
        todoTask3.setTaskName("Zadanie3");
        todoTask3.setUser(user2);

        entityManager.getTransaction().begin();
        entityManager.persist(todoTask1);
        entityManager.persist(todoTask2);
        entityManager.persist(todoTask3);


        entityManager.getTransaction().commit();
        entityManager.close();

    }
    public TodoTask saveTasksForGivenUser(String taskName, String userName){
        EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final TodoTask todoTask = new TodoTask();
        todoTask.setTaskName(taskName);
        final List<User> foundUsers = entityManager.createQuery("select u from User u where u.username = :uname", User.class)
                .setParameter("uname", userName)
                .getResultList();

        entityManager.getTransaction().begin();

        if (foundUsers.isEmpty()){
            User user = new User(userName);
            todoTask.setUser(user);
        }else {
            final User user = foundUsers.get(0);
            todoTask.setUser(user);
        }
       entityManager.persist(todoTask);
        entityManager.getTransaction().commit();
        entityManager.close();

        return todoTask;
    }

    public List<TodoTask> getAllTasks() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();
//        String jpqlQuerry = "SELECT t FROM TodoTask t";
//        entityManager.createQuery(jpqlQuerry);

        final List<TodoTask> tasks = entityManager
                .createQuery("SELECT t from TodoTask t", TodoTask.class)
                .getResultList();
        return tasks;
    }

    public List<TaskCountByUser> getTaskCountByUser() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        List<TaskCountByUser> taskCountByUsers = entityManager
                .createQuery("SELECT " +
                                "new pl.sdajp.java26.spring.jpa.TaskCountByUser(t.user.username, count(t))" +
                                "FROM  TodoTask t " +
                                "GROUP BY t.user",
                        TaskCountByUser.class)
                .getResultList();
        return taskCountByUsers;

//        final List resultList = entityManager
//                .createQuery("SELECT new pl.sdajp.java26.spring.jpa.TodoTask(t.taskName, t.id)" +
//                        "FROM  TodoTask t ",TodoTask.class)
//                .getResultList();
//        return resultList;
    }

    public List<TaskCountByUser> getTaskCountByUserJV() {
        final EntityManager entityManager = ENTITY_MANAGER_FACTORY.createEntityManager();

        final List<TodoTask> tasks = entityManager
                .createQuery("SELECT t from TodoTask t", TodoTask.class)
                .getResultList();

        final List<User> users = entityManager
                .createQuery("SELECT u FROM User u", User.class)
                .getResultList();

        Map<User, Integer> map = new HashMap<>();

        for (User u : users) {
            List<TodoTask> currentUserTasks = new ArrayList<>();

            for (TodoTask task : tasks) {
                if (task.getUser() != null && task.getUser().getId().equals(u.getId()))
                {
                    currentUserTasks.add(task);
                }
            }
            map.put(u, currentUserTasks.size());

        }
        List<TaskCountByUser> tasksCountByUser = new ArrayList<>();
        for (User user : map.keySet()) {
            int values = map.get(user);
            tasksCountByUser.add(new TaskCountByUser(user.getUsername(), values));
        }
        return tasksCountByUser;

    }

    public List<TaskCountByUser> getTaskCountByUserJDBC() {

        String querry = "SELECT u.username, count(t.task_name)  FROM users u JOIN  tasks t ON t.user_id = u.id GROUP BY u.id";
        List<TaskCountByUser> taskCountByUsersList = new ArrayList<>();
        createConnection();
        try (Statement statement = connection.createStatement()) {
            ResultSet rs = statement.executeQuery(querry);
            while (rs.next()) {
TodoTask todoTask = new TodoTask();
User user = new User();
                fillUser(user, rs);
//taskCountByUsersList.add();

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
        return taskCountByUsersList;
    }
    private void fillUser(User u, ResultSet rs){
        u.getUsername();

    }
}
