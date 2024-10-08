package com.example.a2;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


public class InputTaskFrag extends Fragment {

    EditText nameView, descriptionView;
    OnTaskAdded onTaskAddedListener;



    public interface OnTaskAdded {
        public void addTask(Task task);
    }
    public InputTaskFrag() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnTaskAdded) {
            onTaskAddedListener = (OnTaskAdded) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnTaskAddedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        System.out.println("Now we shall perform: onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_input_task, container, false);
        // Inflate the layout for this fragment

        System.out.println("Now we shall perform: onCreateView");
        nameView = view.findViewById(R.id.taskNameEditText);
        descriptionView = view.findViewById(R.id.taskDescriptionEditText);

        view.findViewById(R.id.saveTaskButton).setOnClickListener(v->{
            addTask();
        });

        return view;
    }

    public void addTask(){
        String taskName = nameView.getText().toString().trim();
        String taskDescription = descriptionView.getText().toString().trim();
        Task newTask = new Task(taskName, taskDescription);

        if (!newTask.getName().isEmpty() && !newTask.getDescription().isEmpty()) {
            onTaskAddedListener.addTask(newTask);
            requireActivity().getSupportFragmentManager().popBackStack();
        }

    }



    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Now we shall perform: onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("Now we shall perform: onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("Now we shall perform: onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        System.out.println("Now we shall perform: onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        System.out.println("Now we shall perform: onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        System.out.println("Now we shall perform: onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        System.out.println("Now we shall perform: onDetach");
    }


}