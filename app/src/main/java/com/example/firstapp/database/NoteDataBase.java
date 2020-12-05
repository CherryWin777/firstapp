package com.example.firstapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.firstapp.DAO.dao;
import com.example.firstapp.Entity.entity.note;

@Database(entities = note.class,version=1,exportSchema = false)
public abstract class NoteDataBase extends RoomDatabase {
    private static NoteDataBase notedatabase;
    public static NoteDataBase getNoteDatabase(Context context){
        if(notedatabase==null){
            notedatabase= Room.databaseBuilder(
                context,
                NoteDataBase.class,
                   "notes").build();}
         return notedatabase;


            }
            public abstract dao note_dao();
        }



