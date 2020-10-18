package pl.sdajp.java26.spring.jpa;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.net.ConnectException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDaoJdbc {

    public static final String URL = "jdbc:mysql://localhost:3306/java26jpa?serverTimezone=UTC";
    private final DataSource dataSource;

    public TaskDaoJdbc() {
        dataSource = createDatasource();
    }

    public List<TaskCountByUser> getTasksCountByUser() throws SQLException {
        List<TaskCountByUser> tasks = new ArrayList();
        String querry = "SELECT min(u.username) username, count(*) cnt FROM users u JOIN  tasks t ON t.user_id = u.id GROUP BY u.id";

        try (//final Connection connection = getConnectionByDriverManager();
             final Connection connection = getConnectionByDriverManager();
             final Statement statement = connection.createStatement();
             final ResultSet resultSet = statement.executeQuery(querry);) {


            while (resultSet.next()) {
                final String username = resultSet.getString("username");
                int cnt = resultSet.getInt("cnt");
//            final TaskCountByUser taskCountByUser = new TaskCountByUser(username, cnt);
//            tasks.add(taskCountByUser);
                tasks.add(new TaskCountByUser(username, cnt));
            }

        }
        return tasks;
    }

    private Connection getConnectionByDriverManager() throws SQLException {
        return DriverManager.getConnection(URL, "darek2", "tajnehaslo");

    }
    private DataSource createDatasource(){
        final HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setJdbcUrl(URL);
        hikariDataSource.setUsername("darek2");
        hikariDataSource.setPassword("tajnehaslo");
        hikariDataSource.setMinimumIdle(2);//minimalna ilosc polaczen

        return hikariDataSource;
    }
}

