package com.fyusuf.quizapplication;
import android.app.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class TrainingActivity extends Activity {
    private HashMap<String,String> dict;
    private ArrayList<String> arrayList;
    private TextView word;
    private TextView meanOfWord;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);
        dict = new HashMap<>();
        word = (TextView) findViewById(R.id.word);
        meanOfWord = (TextView) findViewById(R.id.mean);
        getDataFromFile();
    }

   public void getDataFromFile(){
       try {
           FileInputStream fileIn=openFileInput("dictionary.txt");
           InputStreamReader InputRead= new InputStreamReader(fileIn,"UTF8");
           BufferedReader br = new BufferedReader(InputRead);
           String line;
           while ((line = br.readLine()) != null) {
               String[] words = line.split("\t");
               //Log.i("line",line);
               dict.put(words[0],words[1]);
           }
           InputRead.close();


       } catch (Exception e) {
           e.printStackTrace();
       }
       arrayList = new ArrayList<>(dict.keySet());
       Collections.shuffle(arrayList);
       word.setText(arrayList.get(0));
       meanOfWord.setText(dict.get(arrayList.get(0)));


   }

    public void getAnotherWord(View view){
        counter++;
        if(counter < arrayList.size()){
            word.setText(arrayList.get(counter));
            meanOfWord.setText(dict.get(arrayList.get(counter)));
        }
        else{
            counter = 0;
            Collections.shuffle(arrayList);
            word.setText(arrayList.get(counter));
            meanOfWord.setText(dict.get(arrayList.get(counter)));
        }

    }
}
