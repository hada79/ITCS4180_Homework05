package mad.sis.uncc.listviewexercisetemplate;

import android.util.JsonReader;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.util.ArrayList;

public class SeriesJsonParser {


    static public ArrayList<Series> parseJsonString(String jstring,GetSeriesAPI.DataInterface iData)
    {
        ArrayList <Series> srList = new ArrayList<Series>();
        try
        {
            JSONObject root = new JSONObject(jstring);
            Log.d("json","Inside the parser");
            //get the Series list
            JSONObject dataObj = root.getJSONObject("data");
            JSONArray jseries = dataObj.getJSONArray("results");
            Log.d("json","After array results");
            if(jseries.length() != 0)
                for(int i= 0;i< jseries.length();i++) {
                    Series serItem = new Series();
                    Log.d("json", "Series" + i);
                    JSONObject serObj = jseries.getJSONObject(i);
                    //id
                    serItem.setSerID(serObj.getInt("id"));
                    Log.d("json", "Series" + i + " id: " + serItem.getSerID());
                    //title
                    serItem.setName(serObj.getString("title"));
                    Log.d("json", "Series" + i + " name: " + serItem.getName());
                    //description
                    serItem.setDescription(serObj.getString("description"));
                    Log.d("json", "Series" + i + " description: " + serItem.getDescription());
                    //URL
                    JSONArray urls = serObj.getJSONArray("urls");
                    //get the fisrt url if any
                    if (urls.length() != 0) {
                        serItem.setUrl(urls.getJSONObject(0).getString("url"));
                        Log.d("json","Series"+i+" url: "+serItem.getUrl());
                    }
                    // get the image URL
                    StringBuilder imgURLb = new StringBuilder();
                    JSONObject thmObj = serObj.getJSONObject("thumbnail");
                    imgURLb.append(thmObj.getString("path"));
                    imgURLb.append("/standard_large.");
                    imgURLb.append(thmObj.getString("extension"));
                    serItem.setImgUrl(imgURLb.toString());
                    Log.d("json","Series"+i+" imgUrl: "+serItem.getImgUrl());
                    //Series CharcterList
                    ArrayList <String> charList = new ArrayList<>();
                    //Get characters object
                    JSONObject charsObj = serObj.getJSONObject("characters");
                    JSONArray charsItems = charsObj.getJSONArray("items");
                    if(charsItems.length() != 0)
                    {
                        for(int j =0;j<charsItems.length();j++)
                        {
                            //SrCharacter srCharacter = new SrCharacter();
                            JSONObject charObj = charsItems.getJSONObject(j);
                            charList.add(charObj.getString("name"));
                        }
                    }
                    serItem.setCharacters(charList);
                    srList.add(serItem);
                    for(int k =0;k<1000000;k++);
                    iData.updateProgress((100/jseries.length())* (i+1));
                }

                if(jseries.length() !=0)iData.updateProgress(100 - (100/jseries.length())+ (100/jseries.length())* (jseries.length()));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return srList;
    }
}
