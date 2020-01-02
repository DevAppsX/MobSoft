package a.todolist.database;

import java.util.Date;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "tasks")
public class TaskEntity {
    // Аннотация @PrimaryKey указывает ключ таблицы
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String description;
    private int priority;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;
    // @Ignore используется конструктор без параметров
    @Ignore
    public TaskEntity(String description, int priority, Date updatedAt) {
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }
    public TaskEntity(int id, String description, int priority, Date updatedAt) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.updatedAt = updatedAt;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public Date getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
}
