package com.example.firstapp.Entity.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
 @Entity(tableName ="note")
public class note implements Serializable{
     @PrimaryKey(autoGenerate = true)
     private int id;

     @ColumnInfo(name="title")
     private String enter_title;

     @ColumnInfo(name="date")
     private String date;

     @ColumnInfo(name="note")
     private String note;

     public int getId() {
         return id;
     }

     public void setId(int id) {
         this.id = id;
     }

     public String getEnter_title() {
         return enter_title;
     }

     public void setEnter_title(String enter_title) {
         this.enter_title = enter_title;
     }

     public String getDate() {
         return date;
     }

     public void setDate(String date) {
         this.date = date;
     }

     public String getNote() {
         return note;
     }

     public void setNote(String note) {
         this.note = note;
     }

     @NonNull
     @Override
     public String toString() {
         return "note{" +
                 "id=" + id +
                 ", enter_title='" + enter_title + '\'' +
                 ", date='" + date + '\'' +
                 ", note='" + note + '\'' +
                 '}';
     }
 }

