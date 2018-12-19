package com.applocker.applockmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.adapter.onclickinterface.ItemClickListener;
import com.applocker.applockmanager.adapter.viewholder.ThemeViewHolder;
import com.applocker.applockmanager.models.Theme;
import com.applocker.applockmanager.utils.Constant;
import com.applocker.applockmanager.utils.SharedPreferenceUtils;
import com.bumptech.glide.Glide;

import java.util.List;

public class ThemeAdapter extends RecyclerView.Adapter<ThemeViewHolder> {
    private Context context;
    private List<Theme> themeList;
    int selectedPosition=-1;

    public ThemeAdapter(Context context, List<Theme> themeList) {
        this.context = context;
        this.themeList = themeList;
    }

    @NonNull
    @Override
    public ThemeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theme,viewGroup,false);
        return new ThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ThemeViewHolder themeViewHolder, final int i) {
        final Theme theme = themeList.get(i);
        Glide.with(context).load(theme.getImage()).into(themeViewHolder.img_item_theme);
        if (selectedPosition == i){
            themeViewHolder.layout_item_theme.setBackgroundResource(R.drawable.focus_item_theme);
        }
        else {
            themeViewHolder.layout_item_theme.setBackgroundResource(R.color.tran);
        }
        themeViewHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int posittion, boolean isLongClick) {
                selectedPosition = posittion;
                SharedPreferenceUtils utils = new SharedPreferenceUtils(context);
                utils.setValue(Constant.CHANGE_THEME,selectedPosition);
                Toast.makeText(context,context.getString(R.string.please_reset_app), Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return themeList.size();
    }

}
