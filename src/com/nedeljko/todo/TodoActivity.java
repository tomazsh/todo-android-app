package com.nedeljko.todo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class TodoActivity extends ActionBarActivity {
    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView listViewItems;
    private EditText editTextNewItem;

    private final int EDIT_REQUEST_CODE = 1337;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo);

        readItems();
        todoAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);

        editTextNewItem = (EditText)findViewById(R.id.editTextNewItem);
        listViewItems = (ListView)findViewById(R.id.listViewItems);
        listViewItems.setAdapter(todoAdapter);
        setupListViewItemsListeners();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == EDIT_REQUEST_CODE) {
            int itemPosition = data.getExtras().getInt("itemPosition", 0);
            String item = data.getExtras().getString("item");
            replaceItem(itemPosition, item);
        }
    }

    private void replaceItem(int position, String newItem) {
        todoItems.remove(position);
        todoItems.add(position, newItem);
        todoAdapter.notifyDataSetChanged();
        writeItems();
    }

    private void readItems() {
        File directory = getFilesDir();
        File todoFile = new File(directory, "todo.txt");

        try {
            List<String> lines = FileUtils.readLines(todoFile);
            todoItems = new ArrayList<String>(lines);
        } catch (IOException exception) {
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File directory = getFilesDir();
        File todoFile = new File(directory, "todo.txt");

        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private void setupListViewItemsListeners() {
        listViewItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                    int position, long rowId) {
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                writeItems();
                return false;
            }
        });

        listViewItems.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                    long rowId) {
                String item = todoItems.get(position);
                launchEditView(position, item);
            }
        });
    }

    private void launchEditView(int itemPosition, String item) {
        Intent intent = new Intent(TodoActivity.this, EditItemActivity.class);
        intent.putExtra("itemPosition", itemPosition);
        intent.putExtra("item", item);
        startActivityForResult(intent, EDIT_REQUEST_CODE);
    }

    public void onAddedItem(View v) {
        String itemText = editTextNewItem.getText().toString().trim();
        todoAdapter.add(itemText);
        editTextNewItem.setText("");
        writeItems();
    }
}
