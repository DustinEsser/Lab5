package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class NoteAppMain extends AppCompatActivity {
    TextView welcome;
    SharedPreferences sharedPreferences;
    Intent intent;
    public static ArrayList<Note> notes = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_app_main);


        sharedPreferences = getSharedPreferences("com.example.lab5", Context.MODE_PRIVATE);
        String name = sharedPreferences.getString(MainActivity.usernameKey, "");

        welcome = (TextView) findViewById(R.id.WelcomeMessage);
        welcome.setText("Welcome " + name + "!");

        Context context = getApplicationContext();
        SQLiteDatabase sql = context.openOrCreateDatabase("notes", Context.MODE_PRIVATE, null);
        DBHelper dbHelper = new DBHelper(sql);
        notes = dbHelper.readNotes(name);

        ArrayList<String> displayNotes = new ArrayList<>();
        for (Note note:notes){
            displayNotes.add(String.format("Title:%s\nDate:%s", note.getTitle(), note.getDate()));

        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, displayNotes);
        ListView listView = (ListView) findViewById(R.id.notesListView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);

                intent.putExtra("noteid", position);
                startActivity(intent);
            }
        });

    }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()){
                case R.id.Logout:
                    logout();
                    break;
                case R.id.AddNote:
                    AddNote();
                    break;
            }

            return true;

        }

    public void logout(){
        sharedPreferences.edit().remove(MainActivity.usernameKey).apply();
        startActivity(new Intent(this, MainActivity.class));

    }
    public void AddNote(){
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}