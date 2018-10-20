package mad.sis.uncc.listviewexercisetemplate;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class GetCharactersAPI extends AsyncTask <String,Integer,ArrayList> {
    private Context ctx;
   // private ListView listViewNews;
    DataInterface iData;
    public GetCharactersAPI(Context ctx, DataInterface iData) {
        this.ctx = ctx;
        this.iData = iData;
    }

    @Override
    protected ArrayList<SrCharacter> doInBackground(String... strings) {
        //limit the response to 25 series no more
        long time = System.currentTimeMillis();
        String hash = generateHash(Long.toString(time),strings[1],strings[2]);
        Log.d("Test","Hash is "+hash);

        StringBuilder stringBuilder = new StringBuilder();
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String response = null;
        ArrayList<SrCharacter> crList = new ArrayList<SrCharacter>();
        try {
            String urlString = "https://gateway.marvel.com:443/v1/public/series/"+strings[0]+"/characters?ts="+time+"&apikey="+URLEncoder.encode(strings[1].toLowerCase(),"UTF-8")+"&hash="+URLEncoder.encode(hash.toLowerCase(),"UTF-8");
            Log.d("test", "the http Url is:"+urlString);
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            int code = connection.getResponseCode();
            if (code == HttpURLConnection.HTTP_OK) {
                Log.d("test","Code is "+ HttpURLConnection.HTTP_OK);
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line = "";
                int progress = 0;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                response = stringBuilder.toString();
                crList = SrCharacterJsonParser.parseJsonString(response,iData);

            }
            else
            {
                Log.d("Test", "The code is "+code);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        Log.d("test","the response is "+response);
        return crList;

    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        iData.sendSrCharacters(arrayList);
    }

    //the code is taken from https://github.com/Karumi/MarvelApiClientAndroid/tree/master/MarvelApiClient/src
    private String generateHash(String timestamp, String publicKey, String privateKey)
    {
        try {
            String value = timestamp + privateKey + publicKey;
            MessageDigest md5Encoder = MessageDigest.getInstance("MD5");
            byte[] md5Bytes = md5Encoder.digest(value.getBytes());

            StringBuilder md5 = new StringBuilder();
            for (int i = 0; i < md5Bytes.length; ++i) {
                md5.append(Integer.toHexString((md5Bytes[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return md5.toString();
        }catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static interface DataInterface{
        public void updateProgress(Integer... progress);
        public void sendSrCharacters(ArrayList<SrCharacter> srCharactersArrayList);
    }
}
