package com.example.hoopscoach;

import static com.example.hoopscoach.MainActivity.team;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ShowVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);

        Bundle extras = getIntent().getExtras();

        String exerciseTitle = extras.getString("exerciseTitle");

        storageReference = FirebaseStorage.getInstance().getReference();

        videoView = findViewById(R.id.videoView);

        storageReference.child(team.teamName).child(exerciseTitle + "_video").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri uri = task.getResult();
                videoView.setVideoURI(uri);
                MediaController mediaController = new MediaController(ShowVideoActivity.this);
                videoView.setMediaController(mediaController);
                mediaController.setMediaPlayer(videoView);
                videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        finish();
                    }
                });
            }
        });

    }
}