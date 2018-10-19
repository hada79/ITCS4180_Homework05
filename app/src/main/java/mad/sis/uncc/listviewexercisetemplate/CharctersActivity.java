package mad.sis.uncc.listviewexercisetemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CharctersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcters);
        Intent intent = getIntent();
        Series series = (Series) intent.getSerializableExtra("Series");
        if(series == null || series.getCharacters().size() == 0)
            Log.d("2","Null list");
        else {
            ListView chListView = (ListView) findViewById(R.id.charListView);
            ArrayAdapter<String> adapter = new ArrayAdapter(CharctersActivity.this,android.R.layout.simple_list_item_1,series.getCharacters());// new CharacterAdapter(CharctersActivity.this, R.layout.list_item, series.getCharacters());
            chListView.setAdapter(adapter);
        }

        TextView serNameView = (TextView) findViewById(R.id.srNametextView);
        serNameView.setText(series.getName());
        setTitle("Series ID: "+series.getSerID());

    }
}
