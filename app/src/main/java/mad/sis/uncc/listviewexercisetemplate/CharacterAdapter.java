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

public class CharacterAdapter extends ArrayAdapter<Series>{
//TODO: this was just copied and pasted of SeriesAdapter
    Context context;
    ViewHolder viewHolder = null;
    int resource = 0;
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Series series = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(this.resource, parent, false);
           // View.OnClickListener onClickListener = ;
            //convertView.setOnClickListener(onClickListener);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
            viewHolder.nameView = (TextView) convertView.findViewById(R.id.nameView);
            viewHolder.descrptionView = (TextView) convertView.findViewById(R.id.descriptionView);
            viewHolder.urlView = (TextView) convertView.findViewById(R.id.urlView);
            viewHolder.imageView = convertView.findViewById(R.id.imageView);
        }
        else
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.nameView.setText(series.getName());
        if(series.getDescription().equals("null"))
            viewHolder.descrptionView.setText("No description Available!");
        else
            viewHolder.descrptionView.setText(series.getDescription());
        viewHolder.urlView.setText(series.getUrl());
        Picasso.get().load(series.getImgUrl()).into(viewHolder.imageView);

        return convertView;
    }

    public CharacterAdapter(Context context, int resource, List<Series> objects) {
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
