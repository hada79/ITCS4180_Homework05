package mad.sis.uncc.listviewexercisetemplate;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;

public class CharctersActivity extends AppCompatActivity implements GetCharactersAPI.DataInterface{

    ArrayList<SrCharacter> characterArrayList = null;
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
            GetCharactersAPI getCharactersAPI = new GetCharactersAPI(CharctersActivity.this,CharctersActivity.this);
            getCharactersAPI.execute(Integer.toString(series.getSerID()),APIKey,privateKey);
        }

        TextView serNameView = (TextView) findViewById(R.id.srNametextView);
        serNameView.setText(series.getName());
        setTitle("Series ID: "+series.getSerID());

    }

    @Override
    public void updateProgress(Integer... progress) {

    }

    @Override
    public void sendSrCharacters(ArrayList<SrCharacter> srCharactersArrayList) {
        this.characterArrayList = srCharactersArrayList;
        ListView characterListView = (ListView) findViewById(R.id.charListView);
        characterListView.setVisibility(View.VISIBLE);
        //Setting up the List View..............
        CharacterAdapter adapter = new CharacterAdapter(CharctersActivity.this, R.layout.character_list_item, characterArrayList);
        characterListView.setAdapter(adapter);
        //AdapterView.OnItemClickListener onItemClickListener = ;
        characterListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(CharctersActivity.this, WebviewActivity.class);
                i.putExtra("URL", (characterArrayList.get(position)).getUrl());

                startActivity(i);

            }
        });
    }
}
