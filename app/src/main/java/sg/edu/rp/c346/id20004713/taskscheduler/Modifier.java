package sg.edu.rp.c346.id20004713.taskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Modifier extends AppCompatActivity {
    EditText etDesc, etTitle;
    TextView tvModDate, tvModTime;
    Calendar cal = Calendar.getInstance();
    Button btnAddUpdate, btnDelete, btnCancel;
    DBHelper dbh = new DBHelper(Modifier.this);
    boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);

        etTitle = findViewById(R.id.etTitle);
        etDesc = findViewById(R.id.etDesc);
        tvModDate = findViewById(R.id.tvModDate);
        tvModTime = findViewById(R.id.tvModTime);
        btnAddUpdate = findViewById(R.id.btnAddUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        update = false;

        Intent intent = getIntent();
        if (intent.hasExtra("item")) {
            item item = (item) intent.getSerializableExtra("item");

            etTitle.setText(item.getTitle());
            etDesc.setText(item.getDesc());
            tvModTime.setText(item.getTime());
            tvModDate.setText(item.getDate());
            update = true;
        } else {
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int hourOfDay = cal.get(Calendar.HOUR_OF_DAY);  // Current Hour
            int minute = cal.get(Calendar.MINUTE);  // Current Minute

            tvModDate.setText((year + "-" + (month+1) + "-" + day));
            tvModTime.setText((hourOfDay + ":" + minute));
            btnDelete.setEnabled(false);
        }

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = etTitle.getText().toString();
                String desc = etDesc.getText().toString();
                String date = tvModDate.getText().toString();
                String time = tvModTime.getText().toString();

                item updateItem = new item(title, desc, date, time);
                if (update){
                    item item = (item) intent.getSerializableExtra("item");
                    int id = item.getId();
                    updateItem.setId(id);
                    
                    dbh.update(updateItem);
                } else {
                    dbh.insert(updateItem);
                }
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item item = (item) intent.getSerializableExtra("item");
                int id = item.getId();
                dbh.delete(id);
                finish();
            }
        });

        tvModDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Listener to set date
                DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        tvModDate.setText((year + "-" + (month + 1) + "-" + dayOfMonth));
                    }
                };

                //Create the date picker dialog
                String date = tvModDate.getText().toString();
                int year = Integer.parseInt(date.split("-")[0]);
                int month = Integer.parseInt(date.split("-")[1]);
                int day = Integer.parseInt(date.split("-")[2]);

                DatePickerDialog myDateDialog = new DatePickerDialog(Modifier.this,
                        dateListener, year, (month - 1), day);

                myDateDialog.show();
            }
        });

        tvModTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Listener to set date
                TimePickerDialog.OnTimeSetListener timeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        tvModTime.setText((hourOfDay + ":" + minute));
                    }
                };

                //Create the date picker dialog
                String time = tvModTime.getText().toString();
                int hour = Integer.parseInt(time.split(":")[0]);
                int minute = Integer.parseInt(time.split(":")[1]);

                TimePickerDialog myTimeDialog = new TimePickerDialog(Modifier.this,
                        timeListener, hour, minute, true);
                myTimeDialog.show();
            }
        });
    }
}