package com.driviz.notepad;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Driviz on 16-07-2017.
 */

public class Custom_Adapter extends ArrayAdapter<Notes_objects> {

    public Custom_Adapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView==null)
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_layout_item,null);

        Notes_objects notes_objects=getItem(position);
        if(notes_objects!=null)
        {
            TextView file_name=convertView.findViewById(R.id.file_name_tv);

            if(file_name!=null)
                file_name.setText(notes_objects.get_file_name());
        }
        return convertView;
    }
}
