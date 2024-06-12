package com.example.hoopscoach;

import static com.example.hoopscoach.MainActivity.exercisesRef;
import static com.example.hoopscoach.MainActivity.trainingsRef;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;

public class TrainingReportActivity extends AppCompatActivity {

    private EditText trainingReport;
    private RecyclerView recyclerView;
    private Button send;
    private ArrayList<String> exercises;
    private ArrayList<ExerciseData> data = new ArrayList<>();
    private ExerciseDataAdapter adapter;

    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training_report);

        trainingReport = findViewById(R.id.et_trainingReport);
        recyclerView = findViewById(R.id.recyclerViewExercises);
        send = findViewById(R.id.btn_sendReport);
        adapter = new ExerciseDataAdapter(this, data);

        Bundle extras = getIntent().getExtras();

        title = extras.getString("trainingTitle");
        exercises = extras.getStringArrayList("exercises");

        trainingsRef.document("title").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                if(ds.getString("specialization").equals("Shooting")){
                    for (String exercise: exercises) {
                        exercisesRef.document(exercise).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot doc = task.getResult();
                                data.add(new ExerciseData(doc.getId(), doc.getDouble("shots_number")));
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                    send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            for (int i = 0; i < adapter.getItemCount(); i++) {


                            }
                        }
                    });
                }
            }
        });

    }
}