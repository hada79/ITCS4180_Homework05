package mad.sis.uncc.listviewexercisetemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class CharctersActivity extends AppCompatActivity implements GetCharactersAPI.DataInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charcters);
        Intent intent = getIntent();
        Series series = (Series) intent.getSerializableExtra("Series");
        String APIKey = (String) intent.getSerializableExtra("APIKey");
        String privateKey = (String) intent.getSerializableExtra("PrivateKey");

        if(series == null || series.getCharacters().size() == 0)
            Log.d("2","Null list");
        else {
            ListView chListView = (ListView) findViewById(R.id.charListView);
            ArrayAdapter<String> adapter = new CharacterAdapter(CharctersActivity.this, R.layout.character_list_item, series.getCharacters());
            chListView.setAdapter(adapter);
        }

        TextView serNameView = (TextView) findViewById(R.id.srNametextView);
        serNameView.setText(series.getName());
        setTitle("Series ID: "+series.getSerID());

        GetCharactersAPI getCharactersAPI = new GetCharactersAPI(CharctersActivity.this,CharctersActivity.this);
        getCharactersAPI.execute(Integer.toString(series.getSerID()),APIKey,privateKey);

    }

    @Override
    public void updateProgress(Integer... progress) {

    }

    @Override
    public void sendSeries(ArrayList<Series> seriesArrayList) {

    }
}
