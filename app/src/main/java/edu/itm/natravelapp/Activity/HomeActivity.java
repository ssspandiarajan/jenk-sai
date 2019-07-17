package edu.itm.natravelapp.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


import com.codetroopers.betterpickers.datepicker.DatePickerDialogFragment;
import com.github.florent37.singledateandtimepicker.SingleDateAndTimePicker;
import com.github.florent37.singledateandtimepicker.dialog.SingleDateAndTimePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.itm.natravelapp.R;


public class HomeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,DatePickerDialogFragment.DatePickerDialogHandler {


    EditText from,to,adult;
    Button selectDate;
    FloatingActionButton fab;
    private Spinner spinner1;

    private String dateForFLight;
    String[] prefredTime = {"00:00 - 06:00","06:01 - 12:00","12:01 - 18:00","18:01 - 23:59"};
    SingleDateAndTimePickerDialog.Builder singleBuilder;
    CardView singleCard, roundCard, multiCard;

    SimpleDateFormat simpleDateOnlyFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//
        from = (EditText) findViewById(R.id.from);
        to = (EditText) findViewById(R.id.to);
        spinner1 = (Spinner) findViewById(R.id.spinner);
        selectDate = (Button) findViewById(R.id.selectDateButton);
        fab = (FloatingActionButton) findViewById(R.id.fab1);

        singleCard = (CardView) findViewById(R.id.card);
        roundCard = (CardView) findViewById(R.id.cardRoundTrip);
        multiCard = (CardView) findViewById(R.id.cardMultiCity);

        this.simpleDateOnlyFormat = new SimpleDateFormat("YYYY-MM-d", Locale.getDefault());


        spinner1.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,prefredTime);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(aa);


        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                selectData();
//                DatePickerBuilder dpb = new DatePickerBuilder()
//
//                        .setFragmentManager(getSupportFragmentManager())
//                        .setStyleResId(R.style.BetterPickersDialogFragment);
//                dpb.show();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!from.getText().toString().equals("") && !to.getText().toString().equals("") && !dateForFLight.equals("")){

                    Intent intent = new Intent(HomeActivity.this, SelecteFlight.class);
                    intent.putExtra("from", from.getText().toString());
                    intent.putExtra("to", to.getText().toString());
                    intent.putExtra("date", dateForFLight);
                    startActivity(intent);

                }else{
                    Toast.makeText(getApplicationContext(),"Please fill all data",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @SuppressLint("StringFormatMatches")
    @Override
    public void onDialogDateSet(int reference, int year, int monthOfYear, int dayOfMonth) {

        selectDate.setText(getString(R.string.date_picker_result_value, year, monthOfYear+1, dayOfMonth));

        dateForFLight = "" + year + "-"+ (monthOfYear+1) + "-"+ dayOfMonth;

    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//        Toast.makeText(getApplicationContext(), prefredTime[i], Toast.LENGTH_LONG).show();

    }


    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void showtrips(View view){
        Intent intent = new Intent(HomeActivity.this, YourTripsActivity.class);
        startActivity(intent);
    }

    public void showteamstrips(View view){
        Intent intent = new Intent(HomeActivity.this, ApprovingActivity.class);
        startActivity(intent);
    }


    public void showCardSingle(View view){
        singleCard.setVisibility(View.VISIBLE);
        roundCard.setVisibility(View.GONE);
        multiCard.setVisibility(View.GONE);
    }

    public void showRoundCard(View view){
        singleCard.setVisibility(View.GONE);
        roundCard.setVisibility(View.VISIBLE);
        multiCard.setVisibility(View.GONE);
    }
    public void showMultiCard(View view){
        singleCard.setVisibility(View.GONE);
        roundCard.setVisibility(View.GONE);
        multiCard.setVisibility(View.VISIBLE);
    }



    public void selectData(){
        final Calendar calendar = Calendar.getInstance();
        final Date defaultDate = calendar.getTime();

        singleBuilder = new SingleDateAndTimePickerDialog.Builder(this)

//                .bottomSheet()
//                .curved()

                .title("Select Date")
//                .titleTextColor(getResources().getColor(R.color.colorAccent))
//                .backgroundColor(getResources().getColor(R.color.white))
                .mainColor(getResources().getColor(R.color.colorPrimary))
                .titleTextColor(Color.WHITE)
                .displayHours(false)
                .displayMinutes(false)
                .displayDays(false)
                .displayMonth(true)
                .displayYears(true)
                .displayDaysOfMonth(true)
                .mustBeOnFuture()

                .displayListener(new SingleDateAndTimePickerDialog.DisplayListener() {
                    @Override
                    public void onDisplayed(SingleDateAndTimePicker picker) {

                    }
                })
                .listener(new SingleDateAndTimePickerDialog.Listener() {
                    @Override
                    public void onDateSelected(Date date) {
                        Toast.makeText(getApplicationContext(),simpleDateOnlyFormat.format(date),Toast.LENGTH_SHORT).show();
                        dateForFLight = simpleDateOnlyFormat.format(date);
                        selectDate.setText("Date - "+ dateForFLight);
                    }
                });
        singleBuilder.display();
    }
}

