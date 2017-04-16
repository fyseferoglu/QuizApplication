package com.fyusuf.quizapplication;


import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;


public class AddWordActivity extends Activity {
    private ArrayList<String> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);
        arrayList = new ArrayList();
        try {
            FileInputStream fileIn=openFileInput("dictionary.txt");
            InputStreamReader InputRead= new InputStreamReader(fileIn,"UTF8");
            BufferedReader br = new BufferedReader(InputRead);
            String line;
            while ((line = br.readLine()) != null) {
                String[] words = line.split("\t");
                //Log.i("line",line);
                arrayList.add(words[0]);
            }
            InputRead.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addNewWord(View view){
        EditText newWord = (EditText)findViewById(R.id.newWord);
        EditText meanOfWord = (EditText) findViewById(R.id.meanOfWord);
        String word = newWord.getText().toString();
        String mean = meanOfWord.getText().toString();
        if(word.equals("") || mean.equals(""))
            Toast.makeText(getBaseContext(), "Please,fill in the blanks!",Toast.LENGTH_SHORT).show();
        else {
            if (!arrayList.contains(word)) {
                String str = word + "\t" + mean;
                try {
                    FileOutputStream fileout = openFileOutput("dictionary.txt", MODE_APPEND);
                    OutputStreamWriter outputWriter = new OutputStreamWriter(fileout, "UTF8");
                    outputWriter.write(str);
                    outputWriter.write("\n");
                    outputWriter.flush();
                    outputWriter.close();
                    newWord.setText("");
                    meanOfWord.setText("");
                    //display file saved message
                    Toast.makeText(getBaseContext(), "Added successfully!",
                            Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getBaseContext(), "The word already exists in the dictionary!",Toast.LENGTH_SHORT).show();
            }
        }
    }

}
