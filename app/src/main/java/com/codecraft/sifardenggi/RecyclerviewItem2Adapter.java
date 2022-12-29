package com.codecraft.sifardenggi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerviewItem2Adapter extends RecyclerView.Adapter<RecyclerviewItem2Adapter.MyViewHolder> {

    private List<Items2> itemsList;

    RecyclerviewItem2Adapter(List<Items2> mItemList){
        this.itemsList = mItemList;
    }

    @Override
    public RecyclerviewItem2Adapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row2,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerviewItem2Adapter.MyViewHolder holder, final int position) {
        final Items2 item = itemsList.get(position);
        String state = item.getDaerah();
        String lokaliti = item.getLokaliti();
        holder.daerahTxt.setText(state);
        holder.lokalitiTxt.setText(lokaliti);
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView daerahTxt,lokalitiTxt;
        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            daerahTxt = itemView.findViewById(R.id.daerah);
            lokalitiTxt = itemView.findViewById(R.id.lokaliti);
        }
    }
}