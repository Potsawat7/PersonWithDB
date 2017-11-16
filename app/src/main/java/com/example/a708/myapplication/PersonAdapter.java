package com.example.a708.myapplication;

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
 * Created by 708 on 10/27/2017.
 */

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List objects) {
        super(context,R.layout.item, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item,null);

        }
        Person person = getItem(position);
        TextView nickNameField = (TextView) convertView.findViewById(R.id.nickNameField);
        nickNameField.setText("Nick Name: " + person.getNickName());

        TextView firstField = (TextView) convertView.findViewById(R.id.firstField);
        firstField.setText("First Name: " + person.getFirstName());

        TextView lastField = (TextView) convertView.findViewById(R.id.lastField);
        lastField.setText("Last Name: " + person.getSurName());
        return convertView;
    }
}
