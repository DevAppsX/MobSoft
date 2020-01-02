/*
* Copyright (C) 2016 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package a.todolist;

import java.util.Date;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import a.todolist.database.AppDatabase;
import a.todolist.database.TaskEntity;

public class AddTaskActivity extends AppCompatActivity {

    // Extra for the task ID to be received in the intent
    public static final String EXTRA_TASK_ID = "extraTaskId";
    // Extra for the task ID to be received after rotation
    public static final String INSTANCE_TASK_ID = "instanceTaskId";
    // Constants for priority
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_MEDIUM = 2;
    public static final int PRIORITY_LOW = 3;
    // Constant for default task id to be used when not in update mode
    private static final int DEFAULT_TASK_ID = -1;
    // Constant for logging
    private static final String TAG = AddTaskActivity.class.getSimpleName();
    // Fields for views
    EditText editText;
    RadioGroup radioGroup;
    Button button;

    private int taskId = DEFAULT_TASK_ID;
    // Member variable for the Database
    private AppDatabase db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        editText = findViewById(R.id.editTextTaskDescription);
        radioGroup = findViewById(R.id.radioGroup);
        button = findViewById(R.id.saveButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveButtonClicked();
            }
        });

        db = AppDatabase.getInstance(getApplicationContext());

/*        if (savedInstanceState != null && savedInstanceState.containsKey(INSTANCE_TASK_ID)) {
            taskId = savedInstanceState.getInt(INSTANCE_TASK_ID, DEFAULT_TASK_ID);
        }
*/
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_TASK_ID)) {
            button.setText(R.string.update_button);
            // TODO БД U: запрос задачи по id
            if (taskId == DEFAULT_TASK_ID) {
                taskId = intent.getIntExtra(EXTRA_TASK_ID, DEFAULT_TASK_ID);
                // Получение задачи по идентификатору:
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        final TaskEntity task = db.taskDao().loadTaskById(taskId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                populateUI(task);
                            }
                        });
                    }
                });
            }
        }
    }
/*  @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(INSTANCE_TASK_ID, taskId);
        super.onSaveInstanceState(outState);
    }
*/
    /**
     * populateUI would be called to populate the UI when in update mode
     *
     * @param task the taskEntity to populate the UI
     */
    private void populateUI(TaskEntity task) {
        // COMPLETED (7) return if the task is null
        if (task == null) {return;}
        editText.setText(task.getDescription());
        setPriorityInViews(task.getPriority());
    }
    /**
     * getPriority is called whenever the selected priority needs to be retrieved
     */
    public int getPriorityFromViews() {
        int priority = 1;
        int checkedId = ((RadioGroup) findViewById(R.id.radioGroup)).getCheckedRadioButtonId();
        switch (checkedId) {
            case R.id.radButton1:
                priority = PRIORITY_HIGH;
                break;
            case R.id.radButton2:
                priority = PRIORITY_MEDIUM;
                break;
            case R.id.radButton3:
                priority = PRIORITY_LOW;
        }
        return priority;
    }
    /**
     * setPriority is called when we receive a task from MainActivity
     *
     * @param priority the priority value
     */
    public void setPriorityInViews(int priority) {
        switch (priority) {
            case PRIORITY_HIGH:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton1);
                break;
            case PRIORITY_MEDIUM:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton2);
                break;
            case PRIORITY_LOW:
                ((RadioGroup) findViewById(R.id.radioGroup)).check(R.id.radButton3);
        }
    }
    /**
     * onSaveButtonClicked is called when the "save" button is clicked.
     * It retrieves user input and inserts that new task data into the underlying database.
     */
    public void onSaveButtonClicked() {
        String description = editText.getText().toString();
        int priority = getPriorityFromViews();
        Date date = new Date();
        final TaskEntity task = new TaskEntity(description, priority, date);
        // TODO БД S: добавление задачи с новым id или сохранение изменений с существующим id в БД
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (taskId == DEFAULT_TASK_ID) {
                    // insert new task
                    db.taskDao().insertTask(task);
                } else {
                    //update task
                    task.setId(taskId);
                    db.taskDao().updateTask(task);
                }
                finish();
            }
        });
    }
}
