package pl.sdajp.java26.spring.jpa;

import java.util.ArrayList;
import java.util.List;

public class TaskCount {
    private String name;
    private Long count;

    public TaskCount(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count;
    }
    List<TaskCount> list = new ArrayList<>();

}
