package pl.sdajp.java26.spring.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class TaskCountByUser {

//    @AllArgsConstructor // lombok konstruktor
   private String name;
    private long tasksCount;

    public String getName() {
        return name;
    }

    public TaskCountByUser(String name, long tasksCount) {
        this.name = name;
        this.tasksCount = tasksCount;
    }
}
