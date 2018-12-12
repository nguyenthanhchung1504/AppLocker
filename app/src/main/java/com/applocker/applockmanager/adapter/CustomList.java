package com.applocker.applockmanager.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.applocker.R;
import com.applocker.applockmanager.activities.AppList;
import com.applocker.applockmanager.databases.Database;
import com.applocker.applockmanager.models.Been;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 15-Oct-17.
 */

public class CustomList extends BaseAdapter {
    String[] result1;
    Drawable[] result2;
    final ArrayList<Integer> result3 = new ArrayList<Integer>();
    ArrayList<String> result4;
    List<Been> list = new ArrayList<Been>();
    Been been = new Been();
    Context context;
    Database database;
    //Switch [] result3;
    private static LayoutInflater inflater=null;
    public CustomList(AppList appList, String[] Appname, Drawable[] Appicon, ArrayList<String> AppPackagename) {
        // TODO Auto-generated constructor stub
        result1=Appname;
        result2=Appicon;
        result4=AppPackagename;
        context=appList;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result1.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView appname;
        ImageView appicon;
        Switch appswitch;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Log.d("update","update");
        context=parent.getContext();
        database = new Database(context);
        final Holder holder=new Holder();
        //View rowView;
        convertView = inflater.inflate(R.layout.customlist, null);
        holder.appname=(TextView) convertView.findViewById(R.id.appname);
        holder.appicon=(ImageView) convertView.findViewById(R.id.appimage);
        holder.appswitch=(Switch) convertView.findViewById(R.id.appswitch);




        holder.appname.setText(result1[position]);
//        holder.appicon.setImageDrawable(result2[position]);
        Glide.with(context).load(result2[position]).into(holder.appicon);

//        holder.appswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                holder.appswitch.setChecked(b);
//            }
//        });



        holder.appswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                  if(holder.appswitch.isChecked())
                  {
                      //result3.add(position);
                      been.setAppName(result4.get(position));

                      if (database.AppInsert(been))
                      {
                          //Toast.makeText(context,"Data Insertion Successful",Toast.LENGTH_SHORT).show();
                          Log.d("Update","Data Insertion Successful");
                      }
                      else {
                          //Toast.makeText(context,"Data Insertion Failed",Toast.LENGTH_SHORT).show();
                          Log.d("Update","Data Insertion Failed");
                      }

                  }
                  else
                  {
                      //result3.remove(result3.indexOf(position));
                      been.setAppName(result4.get(position));

                      if (database.AppRemove(been))
                      {
                          //Toast.makeText(context,"Data Removal Successful",Toast.LENGTH_SHORT).show();
                          Log.d("Update","Data Removal Successful");
                      }
                      else {
                          //Toast.makeText(context,"Data Removal Failed",Toast.LENGTH_SHORT).show();
                          Log.d("Update","Data Removal Failed");
                      }
                  }
                //Log.d("ArrayList",result3.toString());
                //Log.d("package",result4.get(position));


            }
        });
        showData(database.AppCheck());


        list = database.AppCheck();

        List<String> list1 = new ArrayList<String>();

        for(Been been:list)
        {
            list1.add(been.getAppName());
        }
//        for(int i=0;i<list1.size();i++) {
//            if (result4.get(position)==list1.get(i)) {
//                holder.appswitch.setChecked(true);
//            }
//            break;
//        }

        if (list1.contains(result4.get(position))) {
            holder.appswitch.setChecked(true);
        }

//        try {
//            if (result3.get(position) == position) {
//                holder.appswitch.setChecked(true);
//            }
//        }
//        catch (Exception e){}


       // holder.appswitch.setChecked(true);

        return convertView;
    }

    public void showData(List<Been> list)
    {
        for(Been been:list)
        {
            Log.d("All Data ",been.getAppName());
        }

    }

}

