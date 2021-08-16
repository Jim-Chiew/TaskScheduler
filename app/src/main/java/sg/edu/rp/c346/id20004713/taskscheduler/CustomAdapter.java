package sg.edu.rp.c346.id20004713.taskscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<item> versionList;

    public CustomAdapter(Context context, int resource, ArrayList<item> objects) {
        super(context, resource, objects);

        parent_context = context;
        layout_id = resource;
        versionList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater) parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvTitle = rowView.findViewById(R.id.tvTitle);
        TextView tvDesc = rowView.findViewById(R.id.tvDesc);
        TextView tvDate = rowView.findViewById(R.id.tvDate);
        TextView tvTime = rowView.findViewById(R.id.tvTime);
        ImageView imageView = rowView.findViewById(R.id.imageView);

        // Obtain the Android Version information based on the position
        item currentVersion = versionList.get(position);

        // Set values to the TextView to display the corresponding information
        tvTitle.setText(currentVersion.getTitle());
        tvDesc.setText(currentVersion.getDesc());
        tvDate.setText(currentVersion.getDate());
        tvTime.setText(currentVersion.getTime());

        Calendar now = Calendar.getInstance();
        int curYear = now.get(Calendar.YEAR);
        int curMonth = now.get(Calendar.MONTH);
        int curDay = now.get(Calendar.DAY_OF_MONTH);

        String[] date = currentVersion.getDate().split("-");
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);


        if(year > curYear || month > curMonth + 1 || day > curDay){
            imageView.setVisibility(View.INVISIBLE);
        }
        
        return rowView;
    }

}
