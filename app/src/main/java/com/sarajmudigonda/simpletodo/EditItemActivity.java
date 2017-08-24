package com.sarajmudigonda.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.Arrays;

public class EditItemActivity extends AppCompatActivity {
    public static final String[] MONTHS = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    int position;
    Boolean add_flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.edit_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        EditText etItem = (EditText) findViewById(R.id.etTaskName);
        String task_name = getIntent().getStringExtra("task_name");
        etItem.setText(task_name);

        add_flag = getIntent().getBooleanExtra("add_flag", false);

        if (add_flag.equals(true)){
            getSupportActionBar().setTitle("\n" + "Añadir Lista");
        }else{
            getSupportActionBar().setTitle("Editar Lista");
        }

        DatePicker dpDate = (DatePicker) findViewById(R.id.dpDateTask);
        String task_date = getIntent().getStringExtra("task_date");

        int month, year, day;
        //Toast.makeText(this, "length " + task_date.length(), Toast.LENGTH_SHORT).show();

        if (task_date.length() != 0) {
            if (task_date.length() == 11) {
                month = Arrays.asList(MONTHS).indexOf(task_date.substring(0, 3));
                year = Integer.valueOf(task_date.substring(7));
                day = Integer.valueOf(task_date.substring(4, 6));
            } else {
                month = Arrays.asList(MONTHS).indexOf(task_date.substring(0, 3));
                year = Integer.valueOf(task_date.substring(6));
                day = Integer.valueOf(task_date.substring(4, 5));
            }
            dpDate.updateDate(year, month, day);
        }


        EditText etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
        String task_notes = getIntent().getStringExtra("task_notes");
        etTaskNotes.setText(task_notes);

        final Spinner spPriority = (Spinner) findViewById(R.id.spPriority);
        String task_priority = getIntent().getStringExtra("task_priority");

        String pTaskArray[];
        pTaskArray = getResources().getStringArray(R.array.priority_arrays);
        String task_high = pTaskArray[0];
        String task_medium = pTaskArray[1];
        String task_low = pTaskArray[2];

        int pos_priority = 0;

        if (task_priority.equalsIgnoreCase(task_high)) {
            pos_priority = 0;
        } else if (task_priority.equalsIgnoreCase(task_medium)) {
            pos_priority = 1;
        } else if (task_priority.equalsIgnoreCase(task_low)) {
            pos_priority = 2;
        }

        spPriority.setSelection(pos_priority, true);

        Spinner spStatus = (Spinner) findViewById(R.id.spStatus);
        String task_status = getIntent().getStringExtra("task_status");

        String sTaskArray[];
        sTaskArray = getResources().getStringArray(R.array.status_arrays);
        String task_todo = sTaskArray[0];
        String task_done = sTaskArray[1];

        int pos_status = 0;

        if (task_status.equalsIgnoreCase(task_todo)) {
            pos_status = 0;
        } else if (task_status.equalsIgnoreCase(task_done)) {
            pos_status = 1;
        }


        spStatus.setSelection(pos_status, true);


        //to make sure the user's cursor in the text field is at the end of the current text value
        etItem.setSelection(etItem.getText().length());
        etTaskNotes.setSelection(etTaskNotes.getText().length());

        position = getIntent().getIntExtra("position", 0);

        //Toast.makeText(this, "Edit list item: \n" + position , Toast.LENGTH_SHORT).show();
    }

     /*
     public void onSave(View v) {
        EditText etItem = (EditText) findViewById(R.id.etTaskName);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("edited_item", etItem.getText().toString());
        data.putExtra("pos", position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }
    */

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                EditText etItem = (EditText) findViewById(R.id.etTaskName);
                DatePicker dpTaskDate = (DatePicker) findViewById(R.id.dpDateTask);
                EditText etTaskNotes = (EditText) findViewById(R.id.etTaskNotes);
                Spinner spPriority = (Spinner) findViewById(R.id.spPriority);
                Spinner spStatus = (Spinner) findViewById(R.id.spStatus);

                String taskDate = MONTHS[dpTaskDate.getMonth()];
                taskDate += " " + dpTaskDate.getDayOfMonth();
                taskDate += " " + dpTaskDate.getYear();

                // Prepare data intent
                Intent data = new Intent();
                // Pass relevant data back as a result
                data.putExtra("task_name", etItem.getText().toString());
                data.putExtra("task_date", taskDate);
                data.putExtra("task_notes", etTaskNotes.getText().toString());
                data.putExtra("task_priority", spPriority.getSelectedItem().toString());
                data.putExtra("task_status", spStatus.getSelectedItem().toString());
                data.putExtra("pos", position);

                data.putExtra("add_flag", add_flag);

                // Activity finished ok, return the data
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish(); // closes the activity, pass data to parent

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


}
