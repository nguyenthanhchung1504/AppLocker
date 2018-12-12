package com.applocker.applockmanager.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.applocker.R;
import com.applocker.applockmanager.adapter.onclickinterface.ItemClickListener;

public class ThemeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public ImageView img_item_theme;
    public ImageView layout_item_theme;
    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public ThemeViewHolder(@NonNull View itemView) {
        super(itemView);
        img_item_theme = itemView.findViewById(R.id.img_item_theme);
        layout_item_theme = itemView.findViewById(R.id.layout_item_theme);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
