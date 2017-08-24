package com.sarajmudigonda.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements EditNameDialogFragment.EditNameDialogListener {
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;
    //public ArrayList<String> items;
    //public ArrayAdapter<String> itemsAdapter;
    public CustomUsersAdapter usersAdapter;
    ArrayList<User> user;
    ListView lvItems;
    Boolean remove_flag = false;
    //Boolean add_flag = false;
    int posItem = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find the toolbar view inside the activity layout
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar);

        // Display icon in the toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        lvItems = (ListView) findViewById(R.id.lvItems);
        //items = new ArrayList<>();
        user = new ArrayList<User>();

        readItems();

        // Create the adapter to convert the array to views
        usersAdapter = new CustomUsersAdapter(this, user);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(usersAdapter);

        //itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        //lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");

        //Remove on long click
        //setupListViewListener();
        editListViewListener();
        //populateUsersList();
    }

    public void populateUsersList() {
        // Construct the data source
        ArrayList<User> arrayOfUsers = User.getUsers();
        // Create the adapter to convert the array to views
        CustomUsersAdapter adapter = new CustomUsersAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvItems);
        listView.setAdapter(adapter);
    }


    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
        editNameDialogFragment.show(fm, "activity_edit_name_dialog_fragment_edit_name");
        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putInt("num", 5);
        editNameDialogFragment.setArguments(args);

    }

    // Menu icons are inflated just as they were with actionbar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_main_favorite:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...

                /*
                EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
                String itemText = etNewItem.getText().toString();
                itemsAdapter.add(itemText);
                Toast.makeText(this, "Added list item", Toast.LENGTH_SHORT).show();
                writeItems();
                etNewItem.setText("");
                */

                Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                //add_flag = true;
                i.putExtra("task_name", "");
                i.putExtra("task_date", "");
                i.putExtra("task_notes", "");
                i.putExtra("task_priority", "HIGH");
                i.putExtra("task_status", "TO-DO");
                i.putExtra("add_flag", true);

                //i.putExtra("position", items.size());
                i.putExtra("position", user.size());
                //i.putExtra("positionItem", user.size() * 2);
                //Toast.makeText(this, "Main to Edit for Add " + user.size() + user.size() * 2, Toast.LENGTH_SHORT).show();
                startActivityForResult(i, REQUEST_CODE); // brings up the edit item activity

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    private void editListViewListener() {
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
                        // first parameter is the context, second is the class of the activity to launch
                        /*
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        // put "extras" into the bundle for access in the second activity
                        i.putExtra("edit_item", items.get(pos));
                        i.putExtra("position", pos);
                        startActivityForResult(i, REQUEST_CODE); // brings up the edit item activity
                        */

                        FragmentManager fm = getSupportFragmentManager();
                        EditNameDialogFragment editNameDialogFragment = EditNameDialogFragment.newInstance("Some Title");
                        editNameDialogFragment.show(fm, "activity_edit_name_dialog_fragment_edit_name");
                        // Supply num input as an argument.
                        Bundle args = new Bundle();
                        //args.putString("edit_item", items.get(pos));
                        args.putString("task_name", usersAdapter.getItem(pos).getName());
                        args.putString("task_date", usersAdapter.getItem(pos).getDate());
                        args.putString("task_notes", usersAdapter.getItem(pos).getNotes());
                        args.putString("task_priority", usersAdapter.getItem(pos).getPriority());
                        args.putString("task_status", usersAdapter.getItem(pos).getStatus());
                        args.putBoolean("add_flag", false);

                        args.putInt("position", pos);
                        editNameDialogFragment.setArguments(args);

                        //showEditDialog();
                    }
                });
    }

    /*
    private void setupListViewListener(){
        lvItems.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View item, int pos, long id) {
                        items.remove(pos);
                        remove_flag = true;
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
        });

    }
    */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract value from result extras
            String task_name = data.getExtras().getString("task_name");
            String task_date = data.getExtras().getString("task_date");
            String task_notes = data.getExtras().getString("task_notes");
            String task_priority = data.getExtras().getString("task_priority");
            String task_status = data.getExtras().getString("task_status");
            Boolean add_flag = data.getExtras().getBoolean("add_flag");

            int pos = data.getExtras().getInt("pos", 0);

            if (add_flag == true) {
                //itemsAdapter.add(edited_item);
                user.add(new User(task_name, task_date, task_notes, task_priority.toString(), task_status.toString()));
                //Temporary - change when SQL Lite is added
                //items.add(pos, usersAdapter.getItem(posUser).getName());
                //items.add(pos + 1, usersAdapter.getItem(posUser).getHometown());
                add_flag = false;
                Toast.makeText(this, "New task added", Toast.LENGTH_SHORT).show();
            } else {
                //items.set(pos, edited_item);
                usersAdapter.getItem(pos).name = task_name;
                usersAdapter.getItem(pos).date = task_date;
                usersAdapter.getItem(pos).notes = task_notes;
                usersAdapter.getItem(pos).priority = task_priority;
                usersAdapter.getItem(pos).status = task_status;
                //Temporary - change when SQL Lite is added
                //items.set(pos, usersAdapter.getItem(posUser).getName());
                //items.set(pos + 1, usersAdapter.getItem(posUser).getHometown());
                Toast.makeText(this, "Edited task details", Toast.LENGTH_SHORT).show();
            }

            //itemsAdapter.notifyDataSetChanged();
            usersAdapter.notifyDataSetChanged();
            writeItems();

        }
    }

    /*
    public void addItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        Toast.makeText(this, "Added list item", Toast.LENGTH_SHORT).show();
        writeItems();
        etNewItem.setText("");
    }
    */

    private void readItems() {
        ArrayList<String> items;
        items = new ArrayList<>();
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
            //Toast.makeText(this, "readItems " + items.size() , Toast.LENGTH_SHORT).show();

            for (int i = 0; i < items.size(); i += 5) {
                user.add(new User(items.get(i), items.get(i + 1), items.get(i + 2), items.get(i + 3), items.get(i + 4)));
                //Toast.makeText(this, "Read i=" + i + " " + items.get(i), Toast.LENGTH_SHORT).show();
            }


            //todoFile.delete();
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }

    private void writeItems() {
        ArrayList<String> items;
        items = new ArrayList<>();
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");

        try {
            if (remove_flag == true) {
                Toast.makeText(this, "Task removed", Toast.LENGTH_SHORT).show();
                remove_flag = false;
            }
            for (int i = 0; i < user.size(); i += 1) {
                items.add(i * 5, usersAdapter.getItem(i).getName());
                items.add(i * 5 + 1, usersAdapter.getItem(i).getDate());
                items.add(i * 5 + 2, usersAdapter.getItem(i).getNotes());
                items.add(i * 5 + 3, usersAdapter.getItem(i).getPriority());
                items.add(i * 5 + 4, usersAdapter.getItem(i).getStatus());
                //Toast.makeText(this, "writeItems" + user.size() + " " + usersAdapter.getItem(i).getName() + i + " " + usersAdapter.getItem(i).getHometown(), Toast.LENGTH_SHORT).show();
            }
            FileUtils.writeLines(todoFile, items);

            //todoFile.delete();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    // This method is invoked in the activity when the Dialog Fragment listener is triggered
    // Access the data result passed to the activity here
    @Override
    public void onFinishEditDialog(String inputText, int position) {

        /*
        // Extract value from result extras
        items.set(position, inputText);
        itemsAdapter.notifyDataSetChanged();
        writeItems();
        // Toast the name to display temporarily on screen
        Toast.makeText(this, "Hi, " + inputText + " " + position, Toast.LENGTH_SHORT).show();
        */
        if (inputText == "REMOVE") {
            //items.remove(position);
            user.remove(position);
            usersAdapter.notifyDataSetChanged();

            // Toast.makeText(this, "Hi, " + items.get(position) + " " + items.get(position+1), Toast.LENGTH_SHORT).show();
            /*
            if (items.size() == 2){
                items.set(position, null);
                items.set(position + 1, null);
            }else {
                items.remove(position);
                items.remove(position + 1);
            }
            */
            remove_flag = true;
            //itemsAdapter.notifyDataSetChanged();
            writeItems();
        } else {
            Intent i = new Intent(MainActivity.this, EditItemActivity.class);
            // put "extras" into the bundle for access in the second activity
            //i.putExtra("edit_item", items.get(position));
            i.putExtra("task_name", usersAdapter.getItem(position).getName());
            i.putExtra("task_date", usersAdapter.getItem(position).getDate());
            i.putExtra("task_notes", usersAdapter.getItem(position).getNotes());
            i.putExtra("task_priority", usersAdapter.getItem(position).getPriority());
            i.putExtra("task_status", usersAdapter.getItem(position).getStatus());
            i.putExtra("add_flag", false);

            i.putExtra("position", position);
            startActivityForResult(i, REQUEST_CODE); // brings up the edit item activity
        }
    }

    public static class User {

        //declare private data instead of public to ensure the privacy of data field of each class
        private String name;
        private String date;
        private String notes;
        private String priority;
        private String status;

        public User(String name, String date, String notes, String priority, String status) {
            this.name = name;
            this.date = date;
            this.notes = notes;
            this.priority = priority;
            this.status = status;
        }

        public static ArrayList<User> getUsers() {
            ArrayList<User> user1 = new ArrayList<User>();
            return user1;
        }

        //retrieve task name
        public String getName() {
            return name;
        }

        //retrieve task hometown
        public String getDate() {
            return date;
        }

        //retrieve task hometown
        public String getNotes() {
            return notes;
        }

        //retrieve task hometown
        public String getPriority() {
            return priority;
        }

        //retrieve task hometown
        public String getStatus() {
            return status;
        }
    }

}
