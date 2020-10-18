package pl.sdajp.java26.spring.jpa;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "tasks")
public class TodoTask {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Column(name = "task_name", length = 100, nullable = false)
    private String taskName;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
//    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    public String getTaskName() {
//        return taskName;
//    }
//
//    public void setTaskName(String taskName) {
//        this.taskName = taskName;
//    }

}
