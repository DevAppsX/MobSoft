package a.todolist;

import java.util.List;
import android.os.Bundle;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import a.todolist.database.AppDatabase;
import a.todolist.database.TaskEntity;

public class MainActivity extends AppCompatActivity implements TaskAdapter.ItemClickListener {
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Связать RecyclerView с соответствующим представлением
        recyclerView = findViewById(R.id.recyclerViewTasks);
        // Определить Менеджер макетов для RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // Инициализировать адаптер и связать его с RecyclerView
        adapter = new TaskAdapter(this, this);
        recyclerView.setAdapter(adapter);
        // плавающая кнопка
        FloatingActionButton fabButton = findViewById(R.id.fab);
        // обработка нажатия на плавающую кнопку
        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Создание нового интента для запуска AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });
        db = AppDatabase.getInstance(getApplicationContext());
    }
    @Override
    protected void onResume() {
        super.onResume();
        retrieveTasks();
    }
/*    private void retrieveTasks() {
        // TODO БД: Загрузка всего списка дел из БД
        final List<TaskEntity> tasks = db.taskDao().loadAllTasks();
        adapter.setTasks(tasks);
    }*/
// повторный запрос данных при любых изменениях в БД
private void retrieveTasks() {
    AppExecutors.getInstance().diskIO().execute(new Runnable() {
        @Override
        public void run() {
            final List<TaskEntity> tasks = db.taskDao().loadAllTasks();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    adapter.setTasks(tasks);
                }
            });
        }
    });
}
    @Override
    public void onItemClickListener(int itemId) {
        // Запуск AddTaskActivity с параметром itemId при выборе элемента из списка:
        Intent intent = new Intent(MainActivity.this, AddTaskActivity.class);
        intent.putExtra(AddTaskActivity.EXTRA_TASK_ID, itemId);
        startActivity(intent);
    }
}
