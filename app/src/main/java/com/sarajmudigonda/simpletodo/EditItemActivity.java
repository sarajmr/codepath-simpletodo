package com.sarajmudigonda.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    int position;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

       EditText etItem = (EditText) findViewById(R.id.editItem);
       String edit_item = getIntent().getStringExtra("edit_item");
       etItem.setText(edit_item);
       //to make sure the user's cursor in the text field is at the end of the current text value
       etItem.setSelection(etItem.getText().length());
       position = getIntent().getIntExtra("position", 0);
       //Toast.makeText(this, "Edit list item: \n" + edit_item, Toast.LENGTH_SHORT).show();
    }

     public void onSave(View v) {
        EditText etItem = (EditText) findViewById(R.id.editItem);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("edited_item", etItem.getText().toString());
        data.putExtra("pos", position);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish(); // closes the activity, pass data to parent
    }

}
