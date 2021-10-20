package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ThirdActivity extends AppCompatActivity {
    Button button;
    public SQLiteDatabase sql;
    int noteid = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        EditText text = findViewById(R.id.NoteText);

        Intent intent = getIntent();
        noteid = intent.getIntExtra("noteid", -1);

        if(noteid != -1){

            Note note = NoteAppMain.notes.get(noteid);
            String noteContent = note.getContent();
            text.setText(noteContent);
        }

        button = findViewById(R.id.SaveButton);
        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText text = findViewById(R.id.NoteText);
                String content = text.getText().toString();
                Context context = getApplicationContext();
                SQLiteDatabase sql = context.openOrCreateDatabase("notes", MODE_PRIVATE, null);
                DBHelper dbHelper = new DBHelper(sql);

                SharedPreferences sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
                String name = sharedPreferences.getString(MainActivity.usernameKey, "");
                String title;
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                String date = dateFormat.format(new Date());

                if(noteid ==-1) {
                    title = "NOTE_" + (NoteAppMain.notes.size()+1);
                    dbHelper.saveNotes(name, title, content, date);
                }
                else{
                    title = "NOTE_" + (noteid+1);
                    dbHelper.updateNote(title, date, content, name);
                }
                leave();


            }
        });

    }
    public void leave() {
        Intent next = new Intent(this, NoteAppMain.class);
        startActivity(next);

    }
}