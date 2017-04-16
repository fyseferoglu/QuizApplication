package com.fyusuf.quizapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

public class QuizActivity extends Activity implements AdapterView.OnItemClickListener {

    private ArrayList<String> wordList;
    private ListView list;
    private ArrayAdapter<String> adapter;
    private HashMap<String, String> dict;
    private double point = 0.0;
    private ArrayList<String> definitions;
    private int counter = 0;
    private TextView pointView;
    private TextView the_word;
    private Button btnFinish;
    private String currentWord ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        dict = new HashMap<>();
        readAll();
        list = (ListView) findViewById(R.id.listView);
        list.setAdapter(adapter);
        the_word = (TextView) findViewById(R.id.currentWord);
        btnFinish = (Button) findViewById(R.id.btnFinish);
        pointView = (TextView) findViewById(R.id.pointView);
        pointView.setText(String.valueOf(point));
        list.setOnItemClickListener(this);
        generateRandom();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int index, long id) {

        if(counter <= wordList.size()) {
            if(counter == wordList.size()){
                list.setVisibility(View.INVISIBLE);
                btnFinish.setVisibility(View.INVISIBLE);
                the_word.setText("You solved all questions.");
            }
            if (dict.get(currentWord).equals(list.getItemAtPosition(index).toString())) {
                point++;
                pointView.setText("Your point: " + String.valueOf(point));
                if(point > 0){
                    TextView msg =  (TextView) findViewById(R.id.recommend);
                    msg.setVisibility(View.INVISIBLE);
                    Button btn = (Button) findViewById(R.id.btnTraining);
                    btn.setVisibility(View.INVISIBLE);
                }
                generateRandom();
            } else {
                point = point - 0.5;
                pointView.setText("Your point: " + String.valueOf(point));
                if(point < 0){
                    TextView msg =  (TextView) findViewById(R.id.recommend);
                    msg.setVisibility(View.VISIBLE);
                    Button btn = (Button) findViewById(R.id.btnTraining);
                    btn.setVisibility(View.VISIBLE);
                }
                generateRandom();

            }
        }
        else{
            Toast.makeText(this, "Solved all questions!!", Toast.LENGTH_SHORT).show();
        }

    }

    private void readAll() {
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
        definitions = new ArrayList<>();
        wordList = new ArrayList<>(dict.keySet());
        Collections.shuffle(wordList);
    }

    private void generateRandom() {
        if(wordList.size() > 3) {
            if (counter < wordList.size()) {
                Random rand = new Random();
                String word = wordList.get(counter);
                the_word.setText("What is the meaning of '" + word + "'?");
                currentWord = word;
                Set<Integer> intSet = new HashSet<>();
                while (intSet.size() < 3) {
                    int num = rand.nextInt(wordList.size());
                    if (num == counter)
                        continue;
                    intSet.add(num);
                }
                definitions.clear();
                definitions.add(dict.get(wordList.get(counter)));
                Iterator<Integer> iter = intSet.iterator();
                while (iter.hasNext()) {
                    definitions.add(dict.get(wordList.get(iter.next())));
                }
                Collections.shuffle(definitions);
                adapter = new ArrayAdapter<>(this, R.layout.list_layout, definitions);
                list.setAdapter(adapter);
            }
            counter++;
        }
        else{
            Toast.makeText(this, "Must be at least 4 words in the dictionary!", Toast.LENGTH_LONG).show();
        }
    }

    public void goTraining(View view){
        Intent intent = new Intent(this,TrainingActivity.class);
        startActivity(intent);
    }

    public void finishQuiz(View view){
        list.setVisibility(View.INVISIBLE);
        btnFinish.setVisibility(View.INVISIBLE);
        int numOfQuestion = counter-1;
        the_word.setText("You finished the quiz.\n" + "You solved " + numOfQuestion + " question(s).\n" + "Your total score: " + String.valueOf(point));
    }

}
