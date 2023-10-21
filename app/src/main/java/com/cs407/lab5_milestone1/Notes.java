package com.cs407.lab5_milestone1;

import static com.cs407.lab5_milestone1.DBHelper.sqLiteDatabase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class Notes extends AppCompatActivity {
    public static ArrayList<NotesClass> notes1 = new ArrayList<>();
    private ArrayList<String> displayNotes = new ArrayList<>();
    TextView welcomeMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        getSupportActionBar().setTitle("Notes");
        //GET WELCOME MESSAGE
        welcomeMessage = findViewById(R.id.WelcomeMessage);
        Intent intent=getIntent();
        String str=intent.getStringExtra("message");
        welcomeMessage.setText("Welcome " + str + " to the Notes App!");
        SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username","");
        //Get sqLite Instance
        Context context = getApplicationContext();
        SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
        DBHelper dbHelper = new DBHelper(sqLiteDatabase);
        notes1 = dbHelper.readNotes(username);
        Log.i("username",notes1.toString());
        for (NotesClass notes : notes1){
            displayNotes.add(String.format("title:%s\nDate:%s\n", notes.getTitle(), notes.getDate()));
        }
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,displayNotes);
        ListView notesListView = findViewById(R.id.listView);
        notesListView.setAdapter(adapter);
        notesListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                Intent intent = new Intent(getApplicationContext(), NoteWriting.class);
                intent.putExtra("noteId",i);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.notes_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.addNote) {
            goToMainAddNote("note");
        } else if (itemId == R.id.logout) {
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username","");
            sharedPreferences.edit().clear().apply();
            goToMainActivity(username);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToMainActivity(String s){
        Intent intent = new Intent(this,MainActivity.class);
        intent.putExtra("message",s);
        startActivity(intent);

    }

    public void goToMainAddNote(String s){
        Intent intent = new Intent(this,NoteWriting.class);
        intent.putExtra("message",s);
        startActivity(intent);

    }


}