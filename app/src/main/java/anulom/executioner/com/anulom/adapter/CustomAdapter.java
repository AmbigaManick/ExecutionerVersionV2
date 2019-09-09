package anulom.executioner.com.anulom.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import anulom.executioner.com.anulom.CommentInfo;
import anulom.executioner.com.anulom.R;

public class CustomAdapter implements ListAdapter {
    ArrayList<String> cusernew;
    ArrayList<String> cownernew;
    ArrayList<String> cdatenew;
    ArrayList<String> ccommentsnew;
    Context context;
    private static LayoutInflater inflater = null;

    public CustomAdapter(CommentInfo commentInfo, ArrayList<String> cuser, ArrayList<String> cdate,
                         ArrayList<String> ccomments, ArrayList<String> cowner) {

        // TODO Auto-generated constructor stub
        context = commentInfo;

        cusernew = cuser;

        cdatenew = cdate;
        ccommentsnew = ccomments;
        cownernew = cowner;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        // TODO Auto-generated method stub

    }

    @Override
    public int getCount() {
        return cusernew.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public boolean hasStableIds() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public int getViewTypeCount() {
        // TODO Auto-generated method stub
        return cusernew.size();
    }

    @Override
    public boolean isEmpty() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isEnabled(int position) {
        // TODO Auto-generated method stub
        return false;
    }

    public class Holder {
        TextView tv1, tv2, tv3, tv4;

    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        // TODO Auto-generated method stub

        Holder holder = new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.commentdetail, parent,false);
        holder.tv1 = rowView.findViewById(R.id.textView1);
        holder.tv2 = rowView.findViewById(R.id.textView2);
        holder.tv3 = rowView.findViewById(R.id.textView3);
        holder.tv4 = rowView.findViewById(R.id.textView4);

        holder.tv1.setText("By: " + cusernew.get(position));
        holder.tv2.setText(" / " + cdatenew.get(position));
        holder.tv3.setText("Owner: " + cownernew.get(position));
        holder.tv4.setText(ccommentsnew.get(position));

        holder.tv1.setTextIsSelectable(true);
        holder.tv2.setTextIsSelectable(true);
        holder.tv3.setTextIsSelectable(true);
        holder.tv4.setTextIsSelectable(true);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(context, "You Clicked " + cusernew.get(position), Toast.LENGTH_LONG).show();
            }
        });
        return rowView;

    }

}
