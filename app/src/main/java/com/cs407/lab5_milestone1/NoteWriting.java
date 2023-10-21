package com.cs407.lab5_milestone1;

import static com.cs407.lab5_milestone1.DBHelper.sqLiteDatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class NoteWriting extends AppCompatActivity {
    private int noteId = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_writing);
        EditText editText = findViewById(R.id.note);
        Intent intent = getIntent();
        noteId=intent.getIntExtra("noteId",-1);
        if(noteId != -1){
            NotesClass note = Notes.notes1.get(noteId);
            String noteContent = note.getContent();
            editText.setText(noteContent);
        }
        Button save = findViewById(R.id.Save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            Context context = getApplicationContext();
            SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
            DBHelper dbHelper = new DBHelper(sqLiteDatabase);
            SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
            String username = sharedPreferences.getString("username", "");
            String title;
            DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US);
            String date = dateFormat.format(new Date());
        Log.i("Info","Printing noteid before using in condition"+noteId);
        if(noteId==-1)
            {
                EditText editText = findViewById(R.id.note);
                title = "NOTES_"+(Notes.notes1.size()+1);
                dbHelper.saveNotes(username, title, date, editText.getText().toString());
            }
        else{
            Log.i("Info","Printing notesid from update update condition " +noteId);
            title = "NOTES_"+(noteId+1);
            dbHelper.updateNotes(String.valueOf(editText),date,title,username);
        }
        goToActivity(username);
        }
    });
        Button delete = findViewById(R.id.Delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                SQLiteDatabase sqLiteDatabase = context.openOrCreateDatabase("notes",Context.MODE_PRIVATE,null);
                DBHelper dbHelper = new DBHelper(sqLiteDatabase);
                SharedPreferences sharedPreferences = getSharedPreferences("com.cs407.lab5_milestone1", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "");
                String title = "NOTES_" + (noteId + 1);
                dbHelper.deleteNotes("", title);
                goToActivity(username);
                if (noteId != -1) {
                    NotesClass note = Notes.notes1.get(noteId);
                    String name = note.getTitle();
                    String content = note.getContent();
                    dbHelper.deleteNotes(content, name);
                }

            }
        });
    }


    public void goToActivity(String s){
        Intent intent = new Intent(this,Notes.class);
        intent.putExtra("message",s);
        startActivity(intent);

    }
}