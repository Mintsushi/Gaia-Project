package com.example.round.sqlite_example;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editId, editName, editEmail;
    Button btnAdd, btnUpdate, btnDelete;
    ListView lstPersons;

    List<Person> data = new ArrayList<>();
    DataBaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DataBaseHelper(this);

        btnAdd = (Button)findViewById(R.id.btnAdd);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        lstPersons = (ListView)findViewById(R.id.list);

        editId = (EditText)findViewById(R.id.editId);
        editName = (EditText)findViewById(R.id.editName);
        editEmail = (EditText)findViewById(R.id.editEmail);

        //Load Data
        refreshData();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(Integer.parseInt(editId.getText().toString()),
                        editName.getText().toString(),editEmail.getText().toString());
                db.addPerson(person);
                refreshData();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(Integer.parseInt(editId.getText().toString()),
                        editName.getText().toString(),editEmail.getText().toString());
                db.updatePerson(person);
                refreshData();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(Integer.parseInt(editId.getText().toString()),
                        editName.getText().toString(),editEmail.getText().toString());
                db.deletePerson(person);
                refreshData();
            }
        });
    }

    private void refreshData(){
        data = db.getAllPerson();
        PersonAdapter adapter = new PersonAdapter(MainActivity.this,data,editId,editName,editEmail);
        lstPersons.setAdapter(adapter);
    }
}
