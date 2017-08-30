package codepath.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<String>items;
    ArrayAdapter<String>itemsAdapter;
    ListView lvItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems=(ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        lvItems.setAdapter(itemsAdapter);
        EditText etName = (EditText) findViewById(R.id.etNewItem);
        etName.setSelection(0);
        etName.requestFocus();
        setupListViewListner();
    }
    private void setupListViewListner(){
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapter,
                                                   View item, int pos, long id){
                        items.remove(pos);
                        itemsAdapter.notifyDataSetChanged();
                        writeItems();
                        return true;
                    }
                });
        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapter,
                                       View item, int pos, long id){
                        launchComposeView(pos);

                    }
                });

    }
    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);
        String itemText = etNewItem.getText().toString();
        itemsAdapter.add(itemText);
        etNewItem.setText("");
        writeItems();

    }
    private void readItems(){
        File filesdir=getFilesDir();
        File todoFile= new File(filesdir,"todo.txt");
        try{
            items= new ArrayList<String>(FileUtils.readLines(todoFile));
        }
        catch(IOException e) {
            items= new ArrayList<String>();
        }
    }
    private void writeItems(){
        File filesdir=getFilesDir();
        File todoFile= new File(filesdir,"todo.txt");
        try{
           FileUtils.writeLines(todoFile, items);
        }
        catch(IOException e) {
           e.printStackTrace();
        }
    }
    public void launchComposeView(int pos) {
        Intent i = new Intent(MainActivity.this, EditItemActivity.class);
        i.putExtra("name",items.get(pos));
        i.putExtra("pos",pos);
        startActivityForResult(i,20); // brings up the second activity
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
       if (resultCode == RESULT_OK && requestCode == 20) {
            String name = data.getExtras().getString("name");
            int pos = data.getExtras().getInt("pos", 0);
            items.set(pos,name);
            itemsAdapter.notifyDataSetChanged();
            EditText etName = (EditText) findViewById(R.id.etNewItem);
            etName.setSelection(0);
            etName.requestFocus();
            writeItems();
        }
    }
}
