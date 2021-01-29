package com.nikunjpc.quiznewsapp.News.NewsHistory;

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

public class NewsHistoryAdapterClass extends RecyclerView.Adapter<NewsHistoryAdapterClass.ViewHolder>{

    List<NewsHistoryModelClass> item;
    Context context;
    NewsHistoryDatabaseHelperClass databaseHelperClass;


    public NewsHistoryAdapterClass(List<NewsHistoryModelClass> item, Context context) {
        this.item = item;
        this.context = context;
        databaseHelperClass = new NewsHistoryDatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public NewsHistoryAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R. layout.news_history_item, parent,false);
        NewsHistoryAdapterClass.ViewHolder viewHolder=new NewsHistoryAdapterClass.ViewHolder( view );
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull final NewsHistoryAdapterClass.ViewHolder holder, final int position) {


        final NewsHistoryModelClass modelClass= item.get( position );
        holder.searchHistory.setText( modelClass.getSearched());


        holder.btdelete.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseHelperClass.deleteItem( (modelClass.getS_id()) );
                Toast.makeText( context, " 1 Deleted Successfully", Toast.LENGTH_SHORT ).show();
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

        TextView searchHistory;
        ImageButton btdelete;

        public ViewHolder(View itemView){
            super(itemView);

            searchHistory= itemView.findViewById( R.id.searchHistory );
            btdelete= itemView.findViewById( R.id.btdelete);

        }
    }
}
