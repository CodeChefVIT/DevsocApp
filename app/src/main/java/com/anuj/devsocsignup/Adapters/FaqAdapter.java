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

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder>
{
    private List<FAQ> mData;
    private Context context;
    private LayoutInflater layoutInflater;
    private int layoutResource;

    public FaqAdapter(List<FAQ> data){
        this.mData = data;
    }

    @NonNull
    @Override
    public FaqAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.faq_element,parent,false);
        return new ViewHolder(itemLayoutView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FAQ item = mData.get(position);
        holder.Ques.setText(item.getQ());
        holder.Ans.setText(item.getA());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView Ques;
        TextView Ans;
        ViewHolder (View view)
        {
            super(view);
            this.Ques = view.findViewById(R.id.ques);
            this.Ans = view.findViewById(R.id.ans);
        }
        public void showAns(){
            if(Ans.getVisibility()==View.GONE)
                Ans.setVisibility(View.VISIBLE);
        }
    }

}
