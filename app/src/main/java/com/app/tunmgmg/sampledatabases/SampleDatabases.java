package com.app.tunmgmg.sampledatabases;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

public class SampleDatabases extends AppCompatActivity implements View.OnClickListener {
    Button bt_insert,bt_update,bt_delete,bt_search,bt_precious,bt_next;
    EditText ed_name,ed_roll,ed_class,ed_major;
    Spinner spinner;
    ArrayList<GetData> arrayList;
    Database database;
    int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_databases);

        ed_name=(EditText)findViewById(R.id.ed_name);
        ed_roll=(EditText)findViewById(R.id.ed_roll);
        ed_class=(EditText)findViewById(R.id.ed_class);
        ed_major=(EditText)findViewById(R.id.ed_major);

        bt_insert=(Button)findViewById(R.id.bt_insert);
        bt_update=(Button)findViewById(R.id.bt_update);
        bt_delete=(Button)findViewById(R.id.bt_delete);
        bt_search=(Button)findViewById(R.id.bt_search);
        bt_precious=(Button)findViewById(R.id.bt_precious);
        bt_next=(Button)findViewById(R.id.bt_next);

        bt_insert.setOnClickListener(this);
        bt_update.setOnClickListener(this);
        bt_delete.setOnClickListener(this);
        bt_search.setOnClickListener(this);
        bt_precious.setOnClickListener(this);
        bt_next.setOnClickListener(this);
        spinner=(Spinner)findViewById(R.id.spinner);


        arrayList=new ArrayList<>();
        database=new Database(this);
    }

    private void showNext() {
        ++count;
        if(count>arrayList.size()-1){
            count=0;
        }
        showAllData(count);
    }

    private void showAllData(int count) {
        ed_name.setText(arrayList.get(count).getName());
        ed_roll.setText(arrayList.get(count).getRoll());
        ed_class.setText(arrayList.get(count).getStudentClass());
        ed_major.setText(arrayList.get(count).getMajor());
    }

    private void showPrevious() {
        --count;
        if(count<0){
            count=arrayList.size()-1;
        }
        showAllData(count);
    }

     public void searchData(){
         Cursor cursor = database.searchData(ed_name.getText().toString());
         if (cursor.moveToFirst()){
             do {
                 ed_name.setText(cursor.getString(1));
                 ed_roll.setText(cursor.getString(2));
                 ed_class.setText(cursor.getString(3));
                 //showAllData(count);
             }while (cursor.moveToNext());
         }

     }

    private void SpinnerData() {
        arrayList=database.getAllData();
        if(arrayList.size()>0){
            Log.d("Data","data");
            ArrayList<String> name=new ArrayList<>();
            for(int i=0; i<arrayList.size(); i++){
                name.add(arrayList.get(i).getName());
            }
            ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,name);
            spinner.setAdapter(arrayadapter);
        }
    }

    @Override
    public void onClick(View v) {

        if (ed_name.getText().toString().length()>0 || ed_roll.getText().toString().length() >0 || ed_class.getText().toString().length() >0 || ed_major.getText().length() >0) {
            switch (v.getId()) {
                case R.id.bt_insert:
                    database.insertData(ed_name.getText().toString(), ed_roll.getText().toString(), ed_class.getText().toString(), ed_major.getText().toString());
                    SpinnerData();
                    break;

                case R.id.bt_update:
                    database.updateData(arrayList.get(count).getId(),ed_name.getText().toString(),ed_roll.getText().toString(),ed_class.getText().toString(),ed_major.getText().toString());
                    SpinnerData();
                    break;

                case R.id.bt_delete:
                    database.deleteData(arrayList.get(count).getId(),ed_name.getText().toString(),ed_roll.getText().toString(),ed_class.getText().toString(),ed_major.getText().toString());
                    break;

                case R.id.bt_search:
                    searchData();
                    SpinnerData();
                    break;

                case R.id.bt_precious:
                    showPrevious();
                    SpinnerData();
                    break;

                case R.id.bt_next:
                    showNext();
                    SpinnerData();
                    Log.d("next",ed_name.getText().toString()+ed_roll.getText().toString());
                    break;
            }

        }
    }
}
