package com.example.sasha.finalsoftware;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.cs4531.finalsoftware.R;

import java.util.ArrayList;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;

    //NEW
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends  RecyclerView.ViewHolder{
        public TextView tvName;
        public TextView tvSex;
        public TextView tvYear;
        public TextView tvPercent;

        public ExampleViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            tvName = itemView.findViewById(R.id.textViewName);
            tvSex = itemView.findViewById(R.id.textViewSex);
            tvYear = itemView.findViewById(R.id.textViewYear);
            tvPercent = itemView.findViewById(R.id.textViewPercent);

            //NEW
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });

        }
    }

    public ExampleAdapter(ArrayList<ExampleItem> exampleList) {
        mExampleList = exampleList;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_example_item, parent, false);
        //CHANGED BELOW
        ExampleViewHolder evh = new ExampleViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {
        ExampleItem currentItem = mExampleList.get(position);

        holder.tvName.setText(currentItem.getmName());
        holder.tvSex.setText(currentItem.getmSex());
        holder.tvYear.setText(currentItem.getmYear());
        holder.tvPercent.setText(currentItem.getmPercent());

    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
