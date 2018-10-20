package mad.sis.uncc.listviewexercisetemplate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CharacterAdapter extends ArrayAdapter<SrCharacter>{

    Context context;
    ViewHolder viewHolder = null;
    int resource = 0;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        SrCharacter character = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
           // View.OnClickListener onClickListener = ;
            //convertView.setOnClickListener(onClickListener);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.name_tv);
            viewHolder.descrptionView = (TextView) convertView.findViewById(R.id.description_tv);
            viewHolder.urlView = (TextView) convertView.findViewById(R.id.url_tv);
            viewHolder.imageView = convertView.findViewById(R.id.character_iv);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.nameView.setText(character.getName());
        if(character.getDescription().equals("null"))
            viewHolder.descrptionView.setText("No description Available!");
        else
            viewHolder.descrptionView.setText(character.getDescription());
        viewHolder.urlView.setText(character.getUrl());
        Picasso.get().load(character.getImgUrl()).into(viewHolder.imageView);

        return convertView;
    }

    public CharacterAdapter(Context context, int resource, List<SrCharacter> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }
    private class ViewHolder{
        TextView nameView ;
        TextView descrptionView;
        TextView urlView;
        ImageView imageView;
    }
}
