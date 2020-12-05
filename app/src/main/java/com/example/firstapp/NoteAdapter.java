package com.example.firstapp;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.firstapp.Entity.entity.note;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
 private List<note>  list_note;
 private final noteListener listener;
 private List<note>   search_note;
 Timer timer;



    public NoteAdapter(List<note> list_note, noteListener listener) {
        this.list_note = list_note;
        this.listener = listener;
        search_note=list_note;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.note_review,parent,false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {

        holder.setNote_note(list_note.get(position));
        holder.card.setOnClickListener(v -> {
            listener.onItemClick(list_note.get(position), position);
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list_note.size();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView title,date, note_note;
        CardView card;


        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.one);
            date=itemView.findViewById(R.id.two);
            note_note =itemView.findViewById(R.id.three);
            card=itemView.findViewById(R.id.four);
        }

        void setNote_note(com.example.firstapp.Entity.entity.note note){
            title.setText(note.getEnter_title());
            date.setText(note.getDate());
            note_note.setText(note.getNote());

        }

    }

    public void searchNote(final String searchString){
         timer = new Timer();
         timer.schedule(new TimerTask() {
             @Override
             public void run() {
                 if(searchString.trim().isEmpty()){
                     list_note=search_note;
                 }
                 else{
                     ArrayList<note> temp=new ArrayList<>();
                     for(note note:search_note){
                         if(note.getEnter_title().toLowerCase().contains(searchString.toLowerCase()) || note.getNote().toLowerCase().contains(searchString.toString().toLowerCase())){
                             temp.add(note);
                         }
                     }
                     list_note=temp;
                 }
             }
         } ,500);
         new Handler(Looper.getMainLooper()).post(new Runnable(){
             @Override
             public void run() {
                 notifyDataSetChanged();

             }
         });
    }
    public void cancelTimer(){
        if(timer!=null){
            timer.cancel();

        }
    }
}
