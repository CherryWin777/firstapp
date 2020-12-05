package com.example.firstapp.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.example.firstapp.R;
import com.example.firstapp.database.NoteDataBase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class note2<set> extends AppCompatActivity {
    TextView title, date;
    EditText enter_title, note;
     com.example.firstapp.Entity.entity.note intent_note;
     Button delete_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        title = findViewById(R.id.text1);
        date = findViewById(R.id.text2);
        enter_title = findViewById(R.id.edt_text2);
        note = findViewById(R.id.edt_text3);
        delete_btn=findViewById(R.id.delete);


        date.setText(new SimpleDateFormat("h:mm a", Locale.getDefault()).format(new Date()));

        if(getIntent().getBooleanExtra("isViewOrUpdate",false)){
            delete_btn.setVisibility(View.VISIBLE);
            intent_note= (com.example.firstapp.Entity.entity.note) getIntent().getSerializableExtra("object");
            Final();


        }


        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
            }
        });


    }

    private void Final(){
        enter_title.setText(intent_note.getEnter_title());
        date.setText(intent_note.getDate());
        note.setText(intent_note.getNote());
    }

    public void ClickSaveButton(View view) {
        save_note();
    }



    protected void save_note() {
        if (enter_title.getText().toString().isEmpty()) {
            Toast.makeText(this, "empty title", Toast.LENGTH_SHORT).show();
        } else if (note.getText().toString().isEmpty()) {
            Toast.makeText(this, "empty note", Toast.LENGTH_SHORT).show();
        }

        final com.example.firstapp.Entity.entity.note note1 = new com.example.firstapp.Entity.entity.note();

        note1.setEnter_title(enter_title.getText().toString());
        note1.setNote(note.getText().toString());
        note1.setDate(date.getText().toString());

        if(intent_note!=null){
            note1.setId(intent_note.getId());
        }


        class save_task extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                 NoteDataBase.getNoteDatabase(getApplicationContext()).note_dao().insert_note(note1);
                 return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }

        }
        new save_task().execute();
        }





    private void delete(){
        class delete_all extends AsyncTask<Void,Void,Void>{


            @Override
            protected Void doInBackground(Void... voids) {
                NoteDataBase.getNoteDatabase(getApplicationContext()).note_dao().delete_notes(intent_note);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                Intent intent=new Intent();
                intent.putExtra("delete",true);
                super.onPostExecute(aVoid);
                setResult(RESULT_OK,intent);
                finish();
            }
        }
        new delete_all().execute();
        }

    }



