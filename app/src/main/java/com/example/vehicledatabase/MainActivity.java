package com.example.vehicledatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton add,sub;
    Button uploadButton,viewDataButton;
    int count=0;
    String value;
    EditText ac,temper,fanSpeed,ID;
    DatabaseHelper myDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb=new DatabaseHelper(this);
        add=(ImageButton)findViewById(R.id.Add);
        sub=(ImageButton)findViewById(R.id.sub);
        fanSpeed=(EditText)findViewById(R.id.fanspeed);
        ac=(EditText)findViewById(R.id.ac);
        temper=(EditText)findViewById(R.id.temper);
        uploadButton = (Button) findViewById(R.id.upload);
        viewDataButton = (Button) findViewById(R.id.viewdata);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                value=Integer.toString(count);
                fanSpeed.setText( value);

            }
        });
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count--;
                value=Integer.toString(count);
                fanSpeed.setText( value);

            }
        });


        AddData();
        viewAll();

        Cursor cursor= myDb.getData();
        if (cursor.getCount()==0){
            Toast.makeText(getApplicationContext(),"No data",Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()){

                fanSpeed.setText(cursor.getString(1));
                ac.setText(cursor.getString(2));
                temper.setText(cursor.getString(3));

            }
        }
    }

    public  void AddData() {
        uploadButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(fanSpeed.getText().toString(),
                                ac.getText().toString(),
                                temper.getText().toString() );
                        if(isInserted == true)
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this,"Data not Inserted",Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void viewAll(){

        viewDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Cursor res = myDb.getAllData();
               if(res.getCount()==0){

                   showMessage("Error","No Data");
                   return;
               }
               StringBuffer buffer =new StringBuffer();
               while (res.moveToNext()){
                   buffer.append("Id:"+res.getString(0)+"\n");
                   buffer.append("Fan speed:"+res.getString(1)+"\n");
                   buffer.append("A/C State:"+res.getString(2)+"\n");
                   buffer.append("Temperature:"+res.getString(3)+"\n\n");

               }


               showMessage("Data",buffer.toString());


            }
        });
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}