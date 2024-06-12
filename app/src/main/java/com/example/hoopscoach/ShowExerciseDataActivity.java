package com.example.hoopscoach;

import static com.example.hoopscoach.MainActivity.exercisesRef;
import static com.example.hoopscoach.MainActivity.team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShowExerciseDataActivity extends AppCompatActivity {

    private TextView title, description;
    private Button back, watchVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_exercise_data);
        Bundle extras = getIntent().getExtras();
        String exerciseTitle = extras.getString("title");

        title = findViewById(R.id.tv_exerciseTitle);
        description = findViewById(R.id.tv_exerciseDescription);
        back = findViewById(R.id.btn_backToTraining);
        watchVideo = findViewById(R.id.btn_watchVideo);


        title.setText(exerciseTitle);

        exercisesRef.document(exerciseTitle).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot ds = task.getResult();
                String exerciseDescription = ds.getString("description");
                description.setText(exerciseDescription);
                if(ds.getString("video_resource") != null){
                    watchVideo.setVisibility(View.VISIBLE);
                }else {
                    watchVideo.setVisibility(View.GONE);
                }
            }
        });

        watchVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ShowExerciseDataActivity.this, ShowVideoActivity.class);
                i.putExtra("exerciseTitle", exerciseTitle);
                startActivity(i);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}