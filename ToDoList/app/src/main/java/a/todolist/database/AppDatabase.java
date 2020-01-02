package a.todolist.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

@Database(entities = {TaskEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "todolist";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                // Создание нового экземпляра БД
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
// TODO: метод allowMainThreadQueries позволяет обрабатывать данных БД в основном потоке (удалить)
//                        .allowMainThreadQueries()
                        .build();
            }
        }
        // Возвращает существующий экземпляр БД
        return sInstance;
    }
    public abstract TaskDao taskDao();
}