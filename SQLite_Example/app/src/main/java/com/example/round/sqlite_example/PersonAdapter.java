package com.example.round.sqlite_example;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Round on 2017-08-31.
 */

public class PersonAdapter extends BaseAdapter {
    Activity activity;
    List<Person> lstPersons;
    LayoutInflater inflater;
    EditText editId, editName, editEmail;

    public PersonAdapter(Activity activity, List<Person> lstPersons, EditText editId, EditText editName, EditText editEmail) {
        this.activity = activity;
        this.lstPersons = lstPersons;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.editId = editId;
        this.editName = editName;
        this.editEmail = editEmail;
    }

    @Override
    public int getCount() {
        return lstPersons.size();
    }

    @Override
    public Object getItem(int i) {
        return lstPersons.get(i);
    }

    @Override
    public long getItemId(int i) {
        return lstPersons.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View rowView;
        rowView = inflater.inflate(R.layout.datatbase_list_item,null);
        final TextView txtRowId, txtRowName, txtRowEmail;

        txtRowId = (TextView)rowView.findViewById(R.id.txtRowId);
        txtRowName = (TextView)rowView.findViewById(R.id.txtRowName);
        txtRowEmail = (TextView)rowView.findViewById(R.id.txtRowEmail);

        txtRowId.setText(" "+lstPersons.get(i).getId());
        txtRowName.setText(" "+lstPersons.get(i).getName());
        txtRowEmail.setText(" "+lstPersons.get(i).getEmail());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editId.setText(" "+txtRowId.getText());
                editName.setText(" "+txtRowName.getText());
                editEmail.setText(" "+txtRowEmail.getText());
            }
        });

        return rowView;
    }
}
