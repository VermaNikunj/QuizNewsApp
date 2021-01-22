package com.nikunjpc.quiznewsapp.History;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikunjpc.quiznewsapp.R;

import java.util.List;

public class HistoryAdapterClass extends RecyclerView.Adapter<HistoryAdapterClass.ViewHolder>{

        List<HistoryModelClass> item;
    Context context;
    HistoryDatabaseHelperClass databaseHelperClass;


    public HistoryAdapterClass(List<HistoryModelClass> item, Context context) {
        this.item = item;
        this.context = context;
        databaseHelperClass = new HistoryDatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R. layout.history_item, parent,false);
        ViewHolder viewHolder=new ViewHolder( view );
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {


        final HistoryModelClass modelClass= item.get( position );

        holder.id.setText(Integer.toString(position+1));
//        String name=modelClass.getFile_name() + " -- "+ modelClass.getClass_type();
        final String type_cat=modelClass.getType()+" -- "+ modelClass.getCategory();
        holder.type_category.setText( type_cat);
        holder.correct_number.setText( "Correct Answered: "+ modelClass.getCorrect_answer() );


        holder.historyDelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelperClass.deleteItem( (modelClass.getId()) );
                Toast.makeText( context, type_cat+" : Deleted Successfully", Toast.LENGTH_SHORT ).show();
                item.remove(position);
                notifyDataSetChanged();
            }
        } );
    }



    @Override
    public int getItemCount() {
        return item.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView id, type_category, correct_number;
        ImageButton historyDelete;

        public ViewHolder(View itemView){
            super(itemView);

            id= itemView.findViewById( R.id.id );
            type_category= itemView.findViewById( R.id.type_category);
            correct_number= itemView.findViewById( R.id.correct_number );
            historyDelete= itemView.findViewById( R.id.HistoryDelete );

        }
    }
}