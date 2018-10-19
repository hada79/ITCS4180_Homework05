package mad.sis.uncc.listviewexercisetemplate;


import java.io.Serializable;
import java.util.ArrayList;

public class Series implements Serializable {
    int serID;
    String name;
    String description;
    String imgUrl;
    String url;
    ArrayList<String> characters;

    public void setSerID(int serID) {
        this.serID = serID;
    }

    public int getSerID() {

        return serID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public ArrayList<String> getCharacters() {
        return characters;
    }

    public void setCharacters(ArrayList<String> characters) {
        this.characters = characters;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
