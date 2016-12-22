package ua.gvv.studentlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ua.gvv.studentlist.data.Student;
import ua.gvv.studentlist.helper.RecyclerViewAdapterItemTouchHelper;

/**
 * Created by gvv on 30.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> implements RecyclerViewAdapterItemTouchHelper {

    private List<Student> list;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
        setHasStableIds(true);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.bind(list.get(i));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public long getItemId(int position) { return list.get(position).getId(); }

    private void deleteItemByIndex(final int position) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                ((RealmResults<Student>)list).deleteFromRealm(position);
            }
        });
        notifyDataSetChanged();
    }

    private void showDetailInfo(int apiType, String link) {
        Intent intent = new Intent(context, ActivityDetail.class)
                .putExtra(ActivityDetail.DETAIL_TYPE, apiType)
                .putExtra(ActivityDetail.USER, link);
        context.startActivity(intent);
    }

    @Override
    public void onItemDismiss(int position) {
        deleteItemByIndex(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView name;
        private ImageView button;

        @Override
        public void onClick(View v) {
            if (v.getId() == name.getId()) {
                Student student = list.get(getAdapterPosition());
                showDetailInfo(ActivityDetail.DETAIL_GOOGLE_PLUS, student.getGoogleName());
            } else if (v.getId() == button.getId()) {
                Student student = list.get(getAdapterPosition());
                showDetailInfo(ActivityDetail.DETAIL_GIT_HUB, student.getGitHubName());
            }
        }

//        @Override
//        public boolean onLongClick(View v) {
//            String studentName = list.get(getAdapterPosition()).getName();
//            Snackbar snackbar = Snackbar.make(v, studentName, Snackbar.LENGTH_LONG);
//            snackbar.setAction(R.string.action_delete, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    deleteItemByIndex(getAdapterPosition());
//                }
//            });
//            snackbar.show();
//            return true;
//        }

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recycler_item_textview);
            button = (ImageView) itemView.findViewById(R.id.iv_git_hub);
            itemView.setOnClickListener(this);
            name.setOnClickListener(this);
            button.setOnClickListener(this);
//
//            itemView.setOnLongClickListener(this);
//            name.setOnLongClickListener(this);
//            button.setOnLongClickListener(this);
        }

        public void bind(Student student) {
            name.setText(student.getName());
        }
    }

}

