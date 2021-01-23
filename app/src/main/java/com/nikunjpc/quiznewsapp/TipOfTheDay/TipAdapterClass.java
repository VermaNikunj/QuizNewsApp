package com.nikunjpc.quiznewsapp.TipOfTheDay;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nikunjpc.quiznewsapp.R;

import java.util.List;

public class TipAdapterClass extends RecyclerView.Adapter<TipAdapterClass.ViewHolder> {

    List<TipModelClass> Tipitem;
    Context context;
    TipDatabaseHelperClass TipdatabaseHelperClass;


    public TipAdapterClass(List<TipModelClass> Tipitem, Context context) {
        this.Tipitem = Tipitem;
        this.context = context;
        TipdatabaseHelperClass = new TipDatabaseHelperClass(context);
    }

    @NonNull
    @Override
    public TipAdapterClass.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate( R. layout.tip_item, parent,false);
        TipAdapterClass.ViewHolder viewHolder=new TipAdapterClass.ViewHolder( view );
        return viewHolder;
    }

    public void onBindViewHolder(@NonNull final TipAdapterClass.ViewHolder holder, final int position) {

        final TipModelClass modelClass= Tipitem.get( position );

        holder.tipDate.setText(modelClass.getTipDate());
        holder.tipLine.setText( modelClass.getTipLine() );
    }



    @Override
    public int getItemCount() {
        return Tipitem.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tipLine, tipDate;

        public ViewHolder(View itemView){
            super(itemView);

            tipLine= itemView.findViewById( R.id.tipLine );
            tipDate= itemView.findViewById( R.id.tipDate);

        }
    }
}
