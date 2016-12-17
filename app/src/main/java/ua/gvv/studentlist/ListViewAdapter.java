package ua.gvv.studentlist;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.gvv.studentlist.data.Student;

/**
 * Created by gvv on 30.10.16.
 */

public class ListViewAdapter extends BaseAdapter implements ListAdapter {
    private final String TAG = "ListViewAdapter";
    private List<Student> list = new ArrayList<Student>();
    private Context context;

    public ListViewAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return list.get(position).getId();
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
        listItemText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Student student = list.get(position);
                showDetailInfo(ActivityDetail.DETAIL_GIT_HUB, student.getGitHubName());
            }
        });

        //Handle buttons and add onClickListeners
        ImageView ivGooglePlus = (ImageView) row.findViewById(R.id.iv_google_plus);
        ivGooglePlus.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Student student = list.get(position);
                showDetailInfo(ActivityDetail.DETAIL_GOOGLE_PLUS, student.getGoogleName());
            }
        });

        row.setOnLongClickListener(new LongClcik(position));
        listItemText.setOnLongClickListener(new LongClcik(position));
        ivGooglePlus.setOnLongClickListener(new LongClcik(position));
        return row;
    }

    private void deleteItemByIndex(final int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ((RealmResults<Student>)list).deleteFromRealm(position);
            }
        }) ;
        notifyDataSetChanged();
    }

    private void showDetailInfo(int apiType, String link) {
        Intent intent = new Intent((ActivityMain)context, ActivityDetail.class)
                .putExtra(ActivityDetail.DETAIL_TYPE, apiType)
                .putExtra(ActivityDetail.USER, link);
        context.startActivity(intent);
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
