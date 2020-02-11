package com.anuj.devsocsignup.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anuj.devsocsignup.R;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>
{
    private List<TeamMember> mData;
    private Context context;
    private LayoutInflater layoutInflater;
    private int layoutResource;

    public MyRecyclerViewAdapter(List<TeamMember> data){
        this.mData = data;
    }

    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row,parent,false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TeamMember item = mData.get(position);
        holder.Position.setText(item.PositionName);
        holder.Name.setText(item.Name);
        holder.Photo.setImageResource(item.HomeImageId);
        holder.Photo.setClipToOutline(true);
        holder.topLayer.setClipToOutline(true);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView Position;
        TextView Name;
        ImageView Photo;
        ImageView topLayer;
        ViewHolder (View view)
        {
            super(view);
            this.Position = view.findViewById(R.id.position_team);
            this.Name = view.findViewById(R.id.name_team);
            this.Photo = view.findViewById(R.id.team_image);
            this.topLayer = view.findViewById(R.id.imageView4);
        }
    }

    TeamMember getItem(int id) {
        return mData.get(id);
    }
}
