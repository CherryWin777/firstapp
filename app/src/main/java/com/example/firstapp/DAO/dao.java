package com.example.firstapp.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.firstapp.Entity.entity.note;

import java.util.List;

@Dao
public interface dao {

    @Query("SELECT * FROM note ORDER BY id DESC")
    List<note> get_all_notes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert_note(note note1);

    @Delete
    void delete_notes(note note1);
}
