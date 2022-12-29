package com.codecraft.sifardenggi;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerviewItemAdapter extends RecyclerView.Adapter<RecyclerviewItemAdapter.MyViewHolder> {

    private List<Items> itemsList;

    RecyclerviewItemAdapter(List<Items> mItemList){
        this.itemsList = mItemList;
    }

    @Override
    public RecyclerviewItemAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerviewItemAdapter.MyViewHolder holder, final int position) {
        final Items item = itemsList.get(position);
        String state = item.getState();
//        if (state.equals("NEGERISEMBILAN")) state = "N.SEMBILAN";
//        else if (state.equals("WPKUALALUMPUR")) state = "KUALA LUMPUR";
//        else if (state.equals("WPLABUAN")) state = "LABUAN";
//        else if (state.equals("WPPUTRAJAYA")) state = "PUTRA JAYA";
//        else if (state.equals("PULAUPINANG")) state = "PULAU PINANG";
        holder.stateTxt.setText(state);
        holder.dailyTxt.setText(item.getDaily());
        holder.cumulativeTxt.setText(item.getCumulative());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView stateTxt,dailyTxt,cumulativeTxt;
        private LinearLayout itemLayout;

        public MyViewHolder(View itemView) {
            super(itemView);
            stateTxt = itemView.findViewById(R.id.negeri_list);
            dailyTxt = itemView.findViewById(R.id.harian_list);
            cumulativeTxt = itemView.findViewById(R.id.kumulatif_list);
        }
    }
}