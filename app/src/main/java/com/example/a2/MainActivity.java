package com.example.a2;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.a2.InputTaskFrag;
import com.example.a2.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements InputTaskFrag.OnTaskAdded {

    private static final String TASKS_PREF_KEY = "tasks_pref_key";
    private static final String PREFS_NAME = "task_prefs";

    LinearLayout tasksListView;
    FloatingActionButton addTaskButton;
    List<Task> tasks = new ArrayList<>();
    FragmentManager manager;
    InputTaskFrag inputTaskFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        tasksListView = findViewById(R.id.tasks_container);
        addTaskButton = findViewById(R.id.add_tasks_btn);
        manager = getSupportFragmentManager();

        // Load tasks from SharedPreferences
        loadTasksFromPreferences();
        updateTaskList();

        addTaskButton.setOnClickListener(view -> {
            FragmentTransaction transaction = manager.beginTransaction();

            if (inputTaskFrag == null) {
                inputTaskFrag = new InputTaskFrag();
                transaction.replace(R.id.fragmentContainer, inputTaskFrag);
                transaction.addToBackStack(null);
            } else {
                transaction.show(inputTaskFrag);
            }

            transaction.commit();
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        saveTasksToPreferences();
    }

    private void updateTaskList() {
        tasksListView.removeAllViews();
        for (Task newTask : tasks) {
            TextView taskView = new TextView(this);
            taskView.setText(newTask.getName());
            taskView.setPadding(16, 16, 16, 16);
            tasksListView.addView(taskView);
        }
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
        updateTaskList();
    }

    private void saveTasksToPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        Set<String> taskSet = new HashSet<>();
        for (Task task : tasks) {
            taskSet.add(task.getName());
        }

        editor.putStringSet(TASKS_PREF_KEY, taskSet);
        editor.apply();
    }

    private void loadTasksFromPreferences() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        Set<String> taskSet = prefs.getStringSet(TASKS_PREF_KEY, new HashSet<>());

        tasks.clear();

        for (String taskString : taskSet) {
            Task task = new Task();
            task.setName(taskString);
            tasks.add(task);
        }
    }
}
