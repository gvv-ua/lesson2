package ua.gvv.lesson2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by gvv on 30.10.16.
 */

public class ListViewAdapter extends BaseAdapter implements ListAdapter {
    private ArrayList<Student> list = new ArrayList<Student>();
    private Context context;

    public ListViewAdapter(Context context, ArrayList<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView listItemText = (TextView)row.findViewById(R.id.list_item_textview);
        listItemText.setText(list.get(position).getName());

        //Handle buttons and add onClickListeners
        Button button = (Button)row.findViewById(R.id.list_item_button);
        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Student student = list.get(position);
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(student.getGithubLinkleLink()));
                v.getContext().startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Student student = list.get(position);
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(student.getGoogleLink()));
                v.getContext().startActivity(intent);
            }
        });

        row.setOnLongClickListener(new LongClcik(position));
        listItemText.setOnLongClickListener(new LongClcik(position));
        button.setOnLongClickListener(new LongClcik(position));
        return row;
    }

    private void deleteItemByIndex(int position) {
        Student student = list.get(position);
        list.remove(position);
        notifyDataSetChanged();
        Toast toast = Toast.makeText(context, student.getName() + " has been deleted from list!", Toast.LENGTH_SHORT);
        toast.show();
    }

    class LongClcik implements View.OnLongClickListener {
        private int position;

        public LongClcik(int position) {
            this.position = position;
        }

        public boolean onLongClick(View v) {
            String studentName = list.get(position).getName();
            Snackbar snackbar = Snackbar.make(v, studentName, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.action_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItemByIndex(position);
                }
            });
            snackbar.show();
            return true;
        }
    }
}
