package codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String name = getIntent().getStringExtra("name");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        EditText etName = (EditText) findViewById(R.id.editItem);
        etName.setText(name);
        etName.setSelection(etName.getText().length());
        etName.requestFocus();

    }

    public void onSave(View v) {
        EditText etName = (EditText) findViewById(R.id.editItem);
        int pos = getIntent().getIntExtra("pos",0);
        // Prepare data intent
        Intent data = new Intent();
        // Pass relevant data back as a result
        data.putExtra("name", etName.getText().toString());
        data.putExtra("pos",pos);
        // Activity finished ok, return the data
        setResult(RESULT_OK, data); // set result code and bundle data for response
        finish();
    }
}
