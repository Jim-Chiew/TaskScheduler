package sg.edu.rp.c346.id20004713.taskscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    Button btnAdd;
    ArrayList<item> list;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lv = findViewById(R.id.lv);
        btnAdd = findViewById(R.id.btnAdd);

        list = new ArrayList<>();
        DBHelper dbh = new DBHelper(MainActivity.this);
        list.addAll(dbh.getAllItem());
        customAdapter = new CustomAdapter(this, R.layout.lv, list);
        lv.setAdapter(customAdapter);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Modifier.class);
                startActivity(intent);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, Modifier.class);
                item item = list.get(position);
                intent.putExtra("item", item);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        DBHelper dbh = new DBHelper(MainActivity.this);
        list.clear();
        list.addAll(dbh.getAllItem());
        customAdapter.notifyDataSetChanged();
        super.onResume();
    }
}