package ua.gvv.studentlist;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by gvv on 30.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Student> list;
    private Context context;

    public RecyclerViewAdapter(List<Student> records) {
        this.list = records;
    }

    public RecyclerViewAdapter(Context context, List<Student> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        Student record = list.get(i);
        viewHolder.name.setText((CharSequence) record.getName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private void deleteItemByIndex(int position) {
        Student student = list.get(position);
        list.remove(position);
        notifyDataSetChanged();
        Toast toast = Toast.makeText(context, student.getName() + " has been deleted from list!", Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showDetailInfo(int apiType, String link) {
        Intent intent = new Intent((ActivityMain)context, ActivityDetail.class)
                .putExtra(ActivityDetail.DETAIL_TYPE, apiType)
                .putExtra(ActivityDetail.USER, link);
        context.startActivity(intent);
    }

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        private TextView name;
        private Button button;

        @Override
        public void onClick(View v) {
            if (v.getId() == name.getId()) {
                Student student = list.get(getAdapterPosition());
                showDetailInfo(ActivityDetail.DETAIL_GIT_HUB, student.getGitHubName());
            } else if (v.getId() == button.getId()) {
                Student student = list.get(getAdapterPosition());
                showDetailInfo(ActivityDetail.DETAIL_GOOGLE_PLUS, student.getGoogleName());
            }
        }

        @Override
        public boolean onLongClick(View v) {
            String studentName = list.get(getAdapterPosition()).getName();
            Snackbar snackbar = Snackbar.make(v, studentName, Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.action_delete, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    deleteItemByIndex(getAdapterPosition());
                }
            });
            snackbar.show();
            return true;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recycler_item_textview);
            button = (Button) itemView.findViewById(R.id.recycler_item_button);
            itemView.setOnClickListener(this);
            name.setOnClickListener(this);
            button.setOnClickListener(this);

            itemView.setOnLongClickListener(this);
            name.setOnLongClickListener(this);
            button.setOnLongClickListener(this);
        }
    }

}

