package com.example.firstapp.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.Entity.entity.note;
import com.example.firstapp.NoteAdapter;
import com.example.firstapp.R;
import com.example.firstapp.database.NoteDataBase;
import com.example.firstapp.noteListener;


import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements noteListener {
    public static final int REQUEST_CODE_ADD_NOTE = 1;
    public static final int REQUEST_CODE_NOTE_UPDATE = 0;
    public static final int REQUEST_CODE_SHOW = -1;

    RecyclerView reView;
    private List<note> list_note;
    private NoteAdapter adapter;
    private int ClickPosition = 2;
    EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        search=findViewById(R.id.edt_text1);
        reView = findViewById(R.id.r1);
        list_note = new ArrayList<>();
        adapter = new NoteAdapter(list_note, this);
        reView.setAdapter(adapter);
        reView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 1));
        getNote(REQUEST_CODE_SHOW, false);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(list_note.size()!=0){
                    adapter.searchNote(s.toString());
                }

            }
        });
    }


    private void getNote(final int requestCode, final boolean delete) {
        class getNotes extends AsyncTask<Void, Void, List<note>> {

            @Override
            protected List<note> doInBackground(Void... voids) {

                return NoteDataBase.getNoteDatabase(getApplicationContext()).note_dao().get_all_notes();
            }

            @Override
            protected void onPostExecute(List<note> notes) {
                super.onPostExecute(notes);
                Log.d("MainActivity", notes.toString());
                /*if (list_note.size() == 0) {
                    list_note.addAll(notes);
                    adapter.notifyDataSetChanged();
                } else {
                    list_note.add(0, list_note.get(0));
                    adapter.notifyItemInserted(0);
                }*/
                if (requestCode == REQUEST_CODE_SHOW) {
                    list_note.addAll(notes);
                    adapter.notifyDataSetChanged();

                } else if (requestCode == REQUEST_CODE_ADD_NOTE) {
                    list_note.add(0, list_note.get(0));
                    adapter.notifyItemInserted(0);


                } else if (requestCode == REQUEST_CODE_NOTE_UPDATE) {
                    if (delete) {
                        adapter.notifyItemRemoved(ClickPosition);
                    } else {
                        notes.remove(ClickPosition);
                        list_note.add(ClickPosition, notes.get(ClickPosition));
                        adapter.notifyItemChanged(ClickPosition);
                    }

                }
            }


        }
        new getNotes().execute();
    }

    @Override
    public void onItemClick(com.example.firstapp.Entity.entity.note note, int position) {
        ClickPosition = position;
        Intent intent = new Intent(getApplicationContext(), note2.class);
        intent.putExtra("object", note);
        intent.putExtra("isViewOrUpdate", true);
        startActivityForResult(intent, REQUEST_CODE_NOTE_UPDATE);


    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_CODE_ADD_NOTE && resultCode == RESULT_OK) {
            getNote(REQUEST_CODE_ADD_NOTE, false);
        } else if (requestCode == REQUEST_CODE_NOTE_UPDATE && resultCode == RESULT_OK) {
            if (data != null) {
                getNote(REQUEST_CODE_NOTE_UPDATE, data.getBooleanExtra("delete", false));
            }

        }
    }

    public void floatingButtonClick(View view) {
        Intent intent = new Intent(getApplicationContext(), note2.class);
        startActivityForResult(intent, REQUEST_CODE_ADD_NOTE);
    }
}