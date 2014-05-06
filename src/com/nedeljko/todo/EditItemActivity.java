package com.nedeljko.todo;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;
import android.os.Bundle;

public class EditItemActivity extends ActionBarActivity {
    private EditText editTextItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        editTextItem = (EditText)findViewById(R.id.editTextItem);
        editTextItem.setText(getIntent().getStringExtra("item"));
    }

    public void onSaveItem(View v) {
        String editTextString = editTextItem.getText().toString().trim();

        int itemPosition = getIntent().getIntExtra("itemPosition", 0);
        String item = editTextString.length() > 0 ? editTextString : getIntent().getStringExtra("item");

        Intent data = new Intent();
        data.putExtra("itemPosition", itemPosition);
        data.putExtra("item", item);

        setResult(RESULT_OK, data);
        this.finish();
    }
}
