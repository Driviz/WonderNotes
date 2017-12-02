package com.driviz.notepad;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Driviz on 17-07-2017.
 */

public class Notes extends AppCompatActivity {

    EditText notepad;
    Notes_objects notes_objects;
    ImageView image_share,image_theme;
    TextView button1,button2,button3,button4;
    int id;
    DBHandler dbHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.notes_layout);

        dbHandler=new DBHandler(Notes.this,null,null,1);
        notepad=(EditText)findViewById(R.id.notepad);
        notes_objects=getIntent().getExtras().getParcelable("file");
        id=notes_objects.get_id();

        image_share=(ImageView)findViewById(R.id.share);
        image_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                share();
            }
        });

        image_theme=(ImageView)findViewById(R.id.theme);
        image_theme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theme();
            }
        });

        showdata();
    }

    @Override
    public void onBackPressed() {
        String data=notepad.getText().toString();
        dbHandler.update_data(id,data);
        super.onBackPressed();
    }

    public void showdata()
    {
        String data=dbHandler.databasetostring(id);
        notepad.setText(data);
    }

    public void share()
    {
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT,notepad.getText().toString());
        intent.setType("text/plain");
        startActivity(Intent.createChooser(intent,"Share with"));
    }

    public void theme()
    {
        final AlertDialog.Builder builder=new AlertDialog.Builder(Notes.this);
        final View view = getLayoutInflater().inflate(R.layout.dialogbox,null,false);
        builder.setCancelable(true);

        builder.setTitle("Pick your note color");
        button1=(TextView) view.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityBackgroundColor(Color.parseColor("#dadee1"));
            }
        });

        button2=(TextView) view.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityBackgroundColor(Color.parseColor("#e4d99e"));
            }
        });

        button3=(TextView) view.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityBackgroundColor(Color.parseColor("#b8ffc1"));
            }
        });

        button4=(TextView) view.findViewById(R.id.button4);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setActivityBackgroundColor(Color.parseColor("#f1cdf1"));
            }
        });

        builder.setView(view);
        builder.create().show();
    }

    public void setActivityBackgroundColor(int color){
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(color);
    }
}
