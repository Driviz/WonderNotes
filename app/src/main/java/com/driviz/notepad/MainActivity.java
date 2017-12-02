package com.driviz.notepad;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Notes_objects> files;
    Custom_Adapter adapter;
    ListView listView;
    Intent intent;
    AlertDialog.Builder builder;
    int id=0;
    DBHandler dbHandler=new DBHandler(this,null,null,1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView=(ListView)findViewById(R.id.List_view);
        files=new ArrayList<>();
        intent=new Intent(MainActivity.this,Notes.class);

        checklastdata();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Notes_objects current=new Notes_objects(id,"New Note");
                dbHandler.add_data(current);
                files.add(current);
                intent.putExtra("file",current);
                startActivity(intent);
                adapter=new Custom_Adapter(MainActivity.this,R.layout.list_layout_item,files);
                listView.setAdapter(adapter);
                id++;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                intent.putExtra("file",files.get(i));
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Delete?");
                final int position=i;

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dbHandler.delete_data(files.get(position).get_id());
                        files.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });

                builder.setNegativeButton("Cancel" , null);

                final AlertDialog alert=builder.create();
                alert.show();
                return true;
            }
        });
    }

    public void checklastdata()
    {
        files=dbHandler.getFileName();
        if(files.size()>0) {
            id = files.get(files.size() - 1).get_id()+1;
            adapter = new Custom_Adapter(MainActivity.this, R.layout.list_layout_item, files);
            listView.setAdapter(adapter);
        }
    }

}
