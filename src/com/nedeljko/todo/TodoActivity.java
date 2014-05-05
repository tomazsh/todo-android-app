package com.nedeljko.todo;

import java.util.ArrayList;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends ActionBarActivity {
	private ArrayList<String> todoItems;
	private ArrayAdapter<String> todoAdapter;
	private ListView listViewItems;
	private EditText editTextNewItem;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);
        
        populateTodoItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
        
    	editTextNewItem = (EditText)findViewById(R.id.editTextNewItem);
        listViewItems = (ListView)findViewById(R.id.listViewItems);
        listViewItems.setAdapter(todoAdapter);
        setupListViewItemsLongClickListener();
    }

	private void populateTodoItems() {
		todoItems = new ArrayList<String>();
		todoItems.add("London");
		todoItems.add("Berlin");
		todoItems.add("San Francisco");
	}
	
	private void setupListViewItemsLongClickListener() {
		listViewItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long rowId) {
				todoItems.remove(position);
				todoAdapter.notifyDataSetChanged();
				return false;
			}
			
		});
	}
    
    public void onAddedItem(View v) {
		String itemText = editTextNewItem.getText().toString();
		todoAdapter.add(itemText);
		editTextNewItem.setText("");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.todo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
