package com.example.hoopscoach;

import static com.example.hoopscoach.MainActivity.exercisesRef;
import static com.example.hoopscoach.MainActivity.team;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CreateExercisesActivity extends AppCompatActivity {

    private EditText title, description, shotsNumber;
    private Button addExercise, addVideo, rechooseVideo;
    private Spinner specializationsSpinner;
    private VideoView videoView;
    private ImageView imageView;
    private TextView textView;
    private Uri video;
    StorageReference storageReference;
    Map<String, Object> exerciseMap;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult o) {
            if(o.getResultCode() == RESULT_OK){
                if(o.getData() != null){
                    video = o.getData().getData();
                    videoView.setVideoURI(video);
                    MediaController mediaController = new MediaController(CreateExercisesActivity.this);
                    videoView.setMediaController(mediaController);
                    mediaController.setMediaPlayer(videoView);
                    exerciseMap.put("video_resource", team.teamName + "/" + title.getText().toString() + "_video");
                    addVideo.setVisibility(View.GONE);
                    videoView.setVisibility(View.VISIBLE);
                    rechooseVideo.setVisibility(View.VISIBLE);
                }
            }
        }
    });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_exercises);
        Bundle extras = getIntent().getExtras();

        String specialization = extras.getString("specialization");

        storageReference = FirebaseStorage.getInstance().getReference();


        title = findViewById(R.id.et_exerciseTitle);
        description = findViewById(R.id.et_exerciseDescription);
        shotsNumber = findViewById(R.id.et_shotsNumber);
        addVideo = findViewById(R.id.btn_addVideo);
        specializationsSpinner = findViewById(R.id.specializationsSpinner);
        videoView = findViewById(R.id.videoView2);
        addExercise = findViewById(R.id.btn_createExercise);
        imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textView7);
        rechooseVideo = findViewById(R.id.btn_rechooseVideo);

        videoView.setVisibility(View.GONE);
        rechooseVideo.setVisibility(View.GONE);


        exerciseMap = new HashMap<>();

        if(specialization.equals("None")){
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
            specializationsSpinner.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);
            specializationsSpinner.setVisibility(View.GONE);
            if(specialization.equals("Shooting")){
                shotsNumber.setVisibility(View.VISIBLE);
            }
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.specializations_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        specializationsSpinner.setAdapter(adapter);
        specializationsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).equals("Бросковая")){
                    shotsNumber.setVisibility(View.VISIBLE);
                    exerciseMap.put("training_type", "Shooting");
                } else if (parent.getItemAtPosition(position).equals("Дриблинг")) {
                    shotsNumber.setVisibility(View.GONE);
                    exerciseMap.put("training_type", "Dribbling");
                } else if (parent.getItemAtPosition(position).equals("Физ. подготовка")) {
                    shotsNumber.setVisibility(View.GONE);
                    exerciseMap.put("training_type", "Physical");
                } else if (parent.getItemAtPosition(position).equals("Игровая")) {
                    shotsNumber.setVisibility(View.GONE);
                    exerciseMap.put("training_type", "Play");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("video/*");
                activityResultLauncher.launch(i);
            }
        });

        rechooseVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("video/*");
                activityResultLauncher.launch(i);
            }
        });

        addExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!TextUtils.isEmpty(title.getText().toString()) && !TextUtils.isEmpty(description.getText().toString())){

                    exerciseMap.put("description", description.getText().toString());
                    if(!specialization.equals("None")){
                        exerciseMap.put("training_type", specialization);
                        Intent i = new Intent();
                        i.putExtra(AddTrainingActivity.NEW_EXERCISE_TITLE, title.getText().toString());
                        setResult(RESULT_OK, i);
                    }
                    if(exerciseMap.get("training_type").equals("Shooting")){
                        exerciseMap.put("shots_number", Integer.parseInt(shotsNumber.getText().toString()));
                    }

                    if(exerciseMap.get("video_resource") != null){
                        uploadVideo(video);
                    }

                    exercisesRef.document(title.getText().toString()).set(exerciseMap);


                    finish();

                }

            }
        });


    }

    private void uploadVideo(Uri uri){
        StorageReference reference = storageReference.child(team.teamName + "/" + title.getText().toString() + "_video");
        reference.putFile(uri);
    }
}