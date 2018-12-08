package com.example.android.news_insider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.Locale;

public class TopicSelectActivity extends AppCompatActivity {
    EditText Topicname,fromDate,toDate;
    Spinner sortBy;
    String sortType;
    Button SeeResult;
    String TOPIC="";
    String FROMdate,TOdate,SortBy;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_select);
        NumberPicker np = findViewById(R.id.np);
        SeeResult = findViewById(R.id.result);
        Topicname = findViewById(R.id.charactername);
        fromDate = findViewById(R.id.searchFromDate);
        toDate = findViewById(R.id.searchToDate);
        sortType= "popularity";
        //sortBy = findViewById(R.id.sort);
        final String[] values= {"relevancy","popularity", "publishedAt"};

        //Populate NumberPicker values from String array values
        //Set the minimum value of NumberPicker
        np.setMinValue(0); //from array first value
        //Specify the maximum value/number of NumberPicker
        np.setMaxValue(values.length-1); //to array last value

        //Specify the NumberPicker data source as array elements
        np.setDisplayedValues(values);

        //Gets whether the selector wheel wraps when reaching the min/max value.
        np.setWrapSelectorWheel(true);

        //Set a value change listener for NumberPicker
        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                //Display the newly selected value from picker
                sortType = values[newVal];
            }
        });

        myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };


        fromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TopicSelectActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        toDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(TopicSelectActivity.this, date1, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        SeeResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isValid())
                {
                    Intent in = new Intent(TopicSelectActivity.this,ShowNews.class);
                    in.putExtra("topic",TOPIC);
                    in.putExtra("from",FROMdate);
                    in.putExtra("to",TOdate);
                    in.putExtra("sort",sortType);
                    startActivity(in);
                    finish();
                }
            }
        });
    }

    private void updateLabel1() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        toDate.setText(sdf.format(myCalendar.getTime()));
    }

    private boolean isValid() {
        if(TextUtils.isEmpty(Topicname.getText().toString()))
            return false;
        if(TextUtils.isEmpty(fromDate.getText().toString()))
            return false;
        if(TextUtils.isEmpty(toDate.getText().toString()))
            return  false;
        TOPIC = Topicname.getText().toString();
        FROMdate = fromDate.getText().toString();
        TOdate = toDate.getText().toString();
//        SortBy = sortBy.getSelectedItem().toString();
        return true;
    }
    private void updateLabel() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        fromDate.setText(sdf.format(myCalendar.getTime()));
    }
}
