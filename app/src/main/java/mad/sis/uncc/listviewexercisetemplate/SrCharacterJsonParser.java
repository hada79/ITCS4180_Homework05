package mad.sis.uncc.listviewexercisetemplate;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SrCharacterJsonParser {


    static public ArrayList<SrCharacter> parseJsonString(String jstring,GetCharactersAPI.DataInterface iData)
    {
        ArrayList <SrCharacter> srList = new ArrayList<SrCharacter>();
        try
        {
            JSONObject root = new JSONObject(jstring);
            Log.d("json","Inside the parser");
            //get the SrCharacter list
            JSONObject dataObj = root.getJSONObject("data");
            JSONArray jSrCharacter = dataObj.getJSONArray("results");
            Log.d("json","After array results");
            if(jSrCharacter.length() != 0)
                for(int i= 0;i< jSrCharacter.length();i++) {
                    SrCharacter charItem = new SrCharacter();
                    Log.d("json", "SrCharacter" + i);
                    JSONObject serObj = jSrCharacter.getJSONObject(i);
                    //id
                    charItem.setSerID(serObj.getInt("id"));
                    Log.d("json", "SrCharacter" + i + " id: " + charItem.getSerID());
                    //title
                    charItem.setName(serObj.getString("title"));
                    Log.d("json", "SrCharacter" + i + " name: " + charItem.getName());
                    //description
                    charItem.setDescription(serObj.getString("description"));
                    Log.d("json", "SrCharacter" + i + " description: " + charItem.getDescription());
                    //URL
                    JSONArray urls = serObj.getJSONArray("urls");
                    //get the fisrt url if any
                    if (urls.length() != 0) {
                        charItem.setUrl(urls.getJSONObject(0).getString("url"));
                        Log.d("json","SrCharacter"+i+" url: "+charItem.getUrl());
                    }
                    // get the image URL
                    StringBuilder imgURLb = new StringBuilder();
                    JSONObject thmObj = serObj.getJSONObject("thumbnail");
                    imgURLb.append(thmObj.getString("path"));
                    imgURLb.append("/standard_large.");
                    imgURLb.append(thmObj.getString("extension"));
                    charItem.setImgUrl(imgURLb.toString());
                    Log.d("json","SrCharacter"+i+" imgUrl: "+charItem.getImgUrl());
                    //SrCharacter CharcterList
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
                    charItem.setCharacters(charList);
                    srList.add(charItem);
                    for(int k =0;k<1000000;k++);
                    iData.updateProgress((100/jSrCharacter.length())* (i+1));
                }

            if(jSrCharacter.length() !=0)iData.updateProgress(100 - (100/jSrCharacter.length())+ (100/jSrCharacter.length())* (jSrCharacter.length()));

        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        return srList;
    }
}
