package mad.sis.uncc.listviewexercisetemplate;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements GetSeriesAPI.DataInterface {
    //you should fill those two keys as you receive them from the API website
    String APIKey = "d2944bb4564f42c85e88e951e43a6dda";
    String privateKey = "b0aa49ca256e3affc0396dfc0c5b7622edc72328";
    ArrayList<Series> seriesArrayList = null;
    //parameters
    String seriesNameQuery = null;
    String year = null;
    EditText seriesText = null;
    EditText yearText = null;
    public ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel_api);

        setTitle("Marvel API App");
        loading = (ProgressBar) findViewById(R.id.progressBar);
        //get button
        Button button =  (Button) findViewById(R.id.seriesbutton);

        //EeditTexts
        seriesText = (EditText) findViewById(R.id.seriesNameText);
        yearText = (EditText) findViewById(R.id.yearText);

        //Check Internet Connectivity

        if(!isConnected())
        {
            Toast.makeText(this,"No Internet detected, please check your Internet connectivity!", Toast.LENGTH_LONG);
            button.setEnabled(false);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //validate input
                if(seriesText.getText().toString().trim().length() == 0 || yearText.getText().toString().trim().length() == 0) {
                    Toast toast = Toast.makeText(MainActivity.this, "Empty series name query or empty start year field!", Toast.LENGTH_LONG);
                    toast.show();

                }
                else {
                    seriesNameQuery = seriesText.getText().toString();
                    year = yearText.getText().toString();
                    loading.setMax(100);
                    loading.setVisibility(View.VISIBLE);
                    loading.setProgress(0);

                    //series
                    //clear previous
                    if(seriesArrayList != null && seriesArrayList.size() == 0)
                        seriesArrayList.clear();
                    ListView listView = (ListView)findViewById(R.id.seriesListView);
                    listView.setVisibility(View.INVISIBLE);
                    GetSeriesAPI getSeriesAPI = new GetSeriesAPI(MainActivity.this,MainActivity.this);
                    getSeriesAPI.execute(seriesNameQuery,year,APIKey,privateKey);

                }

            }
        });




    }
    private boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;

    }

    @Override
    public void updateProgress(Integer... progress) {
        loading.setProgress(progress[0]);
    }

    @Override
    public void sendSeries(final ArrayList<Series> seriesArrayList) {
        loading.setVisibility(View.INVISIBLE);
        loading.setProgress(0);
        this.seriesArrayList = seriesArrayList;
        ListView seriesListView = (ListView) findViewById(R.id.seriesListView);
        seriesListView.setVisibility(View.VISIBLE);
        //Setting up the List View..............
        SeriesAdapter adapter = new SeriesAdapter(MainActivity.this, R.layout.list_item, seriesArrayList);
        seriesListView.setAdapter(adapter);
        //AdapterView.OnItemClickListener onItemClickListener = ;
        seriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, CharctersActivity.class);
                i.putExtra("Series", (Serializable) (seriesArrayList.get(position)));
                // Starts TargetActivity
                startActivity(i);

            }
        });
    }
}
