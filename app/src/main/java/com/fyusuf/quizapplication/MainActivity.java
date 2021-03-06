package com.fyusuf.quizapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void training(View view){
        Intent intent = new Intent(this,TrainingActivity.class);
        startActivity(intent);
    }

    public void add(View view){
        Intent intent = new Intent(this,AddWordActivity.class);
        startActivity(intent);
    }

    public void quiz(View view){
        Intent intent = new Intent(this,QuizActivity.class);
        startActivity(intent);
    }
}
