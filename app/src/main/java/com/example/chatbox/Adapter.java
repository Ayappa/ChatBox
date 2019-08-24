package com.example.chatbox;



import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    Context context;
    ArrayList<Message> messages;
    public Adapter(ArrayList<Message> messages) {
        this.messages=messages;
    }

    public Adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chat,viewGroup,false);
        ViewHolder viewHolder=new ViewHolder(view);
        context= viewGroup.getContext();
        return viewHolder;
    }

    @Override

    public void onBindViewHolder(@NonNull Adapter.ViewHolder viewHolder, int i) {
        Message chat=messages.get(i);
        viewHolder.name.setText( chat.name);
        viewHolder.messgae.setText( chat.message);
        viewHolder.time.setText( chat.time);
        // Context context = null;

      //  viewHolder.details1=details;
      //  viewHolder.list_detailsArrayList=detailsObjects;
        viewHolder.position=i;

    }

    @Override

    public int getItemCount() {

        return messages.size();

    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView messgae;

        TextView name;
        TextView time;
        Button like;



       // ArrayList<Message> messages=new ArrayList<Message>();
        Message details1;
        private int position;

        public ViewHolder(@NonNull final View itemView) {

            super(itemView);
            messgae=(TextView)itemView.findViewById(R.id.message);
            name=(TextView)itemView.findViewById(R.id.name);
            time=(TextView)itemView.findViewById(R.id.time);
            like=itemView.findViewById(R.id.like);




        }





    }

}






