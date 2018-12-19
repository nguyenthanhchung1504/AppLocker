package com.applocker.applockmanager.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.applocker.applockmanager.R;
import com.applocker.applockmanager.activities.ListApplicationActivity;
import com.applocker.applockmanager.models.App;

import java.util.List;

public class ListAppAdapter extends RecyclerView.Adapter<ListAppAdapter.ListAppViewHolder> {
    Context mContext;
    List<App> mListApp;

    public ListAppAdapter(Context context, List<App> listApp) {
        this.mContext = context;
        this.mListApp = listApp;
    }

    @NonNull
    @Override
    public ListAppViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.item_application, viewGroup, false);
        return new ListAppViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAppViewHolder listAppViewHolder, int i) {
        listAppViewHolder.imgImageApp.setImageDrawable(mListApp.get(i).getIcon());
        listAppViewHolder.txtAppName.setText(mListApp.get(i).getName());
        listAppViewHolder.swOnOf.setChecked(mListApp.get(i).getState() == 1 ? true: false);
        if (mListApp.get(i).getState() == 0){
            listAppViewHolder.swOnOf.setChecked(false);
        }
        else {
            listAppViewHolder.swOnOf.setChecked(true);
        }
    }

    @Override
    public int getItemCount() {
        return mListApp.size();
    }

    public class ListAppViewHolder extends RecyclerView.ViewHolder{
        ImageView imgImageApp;
        TextView txtAppName;
        Switch swOnOf;
        public ListAppViewHolder(@NonNull View itemView) {
            super(itemView);
            imgImageApp = itemView.findViewById(R.id.img_app);
            txtAppName = itemView.findViewById(R.id.txt_name_app);
            swOnOf = itemView.findViewById(R.id.sw_on_off);

            swOnOf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        swOnOf.setChecked(true);
                        mListApp.get(getAdapterPosition()).setState(1);
                    }
                    else {
                        swOnOf.setChecked(false);
                        mListApp.get(getAdapterPosition()).setState(0);
                    }

                    ListApplicationActivity.mDBManager.update(mListApp.get(getAdapterPosition()));
                }

            });
        }
    }
}
