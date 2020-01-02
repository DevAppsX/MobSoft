package a.todolist.database;

import java.util.List;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM tasks ORDER BY priority")
    List<TaskEntity> loadAllTasks();
    @Insert
    void insertTask(TaskEntity taskEntity);
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTask(TaskEntity taskEntity);
    @Delete
    void deleteTask(TaskEntity taskEntity);
    // Query метод loadTaskById по id выбирает TaskEntity Object
    @Query("SELECT * FROM tasks WHERE id = :id")
    TaskEntity loadTaskById(int id);
}
