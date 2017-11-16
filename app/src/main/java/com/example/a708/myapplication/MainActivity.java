package com.example.a708.myapplication;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private GestureDetector gestureDetector;
    private DBAccess dbAccess;
    private List<Person> list;
    private  ListView listView;
    private ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listView);
        adapter = new PersonAdapter(this, 0, list);

        this.dbAccess = DBAccess.getInstance(this);

        gestureDetector = new GestureDetector(this, new MyGestureDetector());
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                return false;
            }
        });
        Button addHandle = (Button) findViewById(R.id.addHandle);

        addHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbAccess.open();
                final View alertView = getLayoutInflater().inflate(R.layout.alert_layout, null);
                new AlertDialog.Builder(MainActivity.this)
                        .setView(alertView)
                        .setTitle("Insert Your Info")
                        .setCancelable(false)
                        .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                EditText addNickName = (EditText) alertView.findViewById(R.id.addNickName);
                                EditText addFirstName = (EditText) alertView.findViewById(R.id.addFirstName);
                                EditText addLastName = (EditText) alertView.findViewById(R.id.addLastName);
                                dbAccess.insertPerson(new Person(addNickName.getText().toString(), addFirstName.getText().toString(), addLastName.getText().toString()));
//                                list.add(new Person(addNickName.getText().toString(), addFirstName.getText().toString(), addLastName.getText().toString()));
                                list = dbAccess.getAllData();
                                Log.i("ddddd", "onClick: list" + list.isEmpty());
                                dbAccess.close();
                            }
                        })

                        .create()
                        .show();
            }
        });
        listView.setAdapter(adapter);

    }

        class MyGestureDetector extends GestureDetector.SimpleOnGestureListener {
            @Override
            public boolean onDoubleTap(MotionEvent e) {

                Toast.makeText(getApplicationContext(), "You double tapped", Toast.LENGTH_SHORT).show();

                return true;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
                                   float velocityY) {
                final int y = (int) e1.getY();
                final int index = listView.pointToPosition(0, y);
                Person person = list.get(index);
                final String nn = person.getNickName();
                final String fn = person.getFirstName();
                final String sn = person.getSurName();

                new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Are you sure to delete?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dbAccess.open();
                                list.remove(index);
                                dbAccess.delete(nn,fn,sn);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.i("lp", "onLongPress: in");
                super.onLongPress(e);
                final int y = (int) e.getY();
                final int index = listView.pointToPosition(0, y);
                Person person = (Person) list.get(index);

                final View personLayout =  getLayoutInflater().inflate(R.layout.alert_layout, null);
                ((EditText) personLayout.findViewById(R.id.addNickName)).setText(person.getNickName());
                ((EditText) personLayout.findViewById(R.id.addFirstName)).setText(person.getFirstName());
                ((EditText) personLayout.findViewById(R.id.addLastName)).setText(person.getSurName());
                new AlertDialog.Builder(MainActivity.this)
                        .setView(personLayout)
                        .setTitle("Edit Info")
                        .setPositiveButton("submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String newNickName = ((EditText) personLayout.findViewById(R.id.addNickName)).getText().toString();
                                String newFirstName = ((EditText) personLayout.findViewById(R.id.addFirstName)).getText().toString();
                                String newLastName = ((EditText) personLayout.findViewById(R.id.addLastName)).getText().toString();
                                Person person = new Person(newNickName,newFirstName,newLastName);
                                list.remove(index);
//                                list.add(index, person);

                                adapter.notifyDataSetChanged();
                            }
                        })
                        .create()
                        .show();

            }

        }


}