package com.sarajmudigonda.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView lvItems;
    Boolean remove_flag = false;
    // REQUEST_CODE can be any value we like, used to determine the result type later
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        items = new ArrayList<>();
        readItems();

        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);

        lvItems.setAdapter(itemsAdapter);
        //items.add("First Item");
        //items.add("Second Item");

        setupListViewListener();
        editListViewListener();

    }

    private void editListViewListener(){
        lvItems.setOnItemClickListener(
                new AdapterView.OnItemClickListener(){

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View item, int pos, long id) {
                        // first parameter is the context, second is the class of the activity to launch
                        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                        // put "extras" into the bundle for access in the second activity
                        i.putExtra("edit_item", items.get(pos));
                        i.putExtra("position", pos);
                        startActivityForResult(i, REQUEST_CODE); // brings up the edit item activity
                    }
                });
    }

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            // Extract value from result extras
            String edited_item = data.getExtras().getString("edited_item");
            int pos = data.getExtras().getInt("pos", 0);
            items.set(pos, edited_item);
            itemsAdapter.notifyDataSetChanged();
            writeItems();
            // Toast the name to display temporarily on screen
            Toast.makeText(this, "List item edited ", Toast.LENGTH_SHORT).show();
        }
    }

    public void addItem(View v){
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        Toast.makeText(this, "Added list item", Toast.LENGTH_SHORT).show();
        writeItems();
        etNewItem.setText("");
    }

    private void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        }catch (IOException e){
            items = new ArrayList<String>();
        }
    }

    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
            if (remove_flag == true) {
                Toast.makeText(this, "List item removed", Toast.LENGTH_SHORT).show();
                remove_flag = false;
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
