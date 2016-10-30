package ua.gvv.lesson2;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

/**
 * Created by gvv on 30.10.16.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Student> list;

    public RecyclerViewAdapter(List<Student> records) {
        this.list = records;
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

    class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener, View.OnLongClickListener {
        private TextView name;
        private Button button;

        @Override
        public void onClick(View v) {
            if (v.getId() == name.getId()) {
                Student student = list.get(getAdapterPosition());
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(student.getGithubLinkleLink()));
                v.getContext().startActivity(intent);
            } else if (v.getId() == button.getId()) {
                Student student = list.get(getAdapterPosition());
                Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse(student.getGoogleLink()));
                v.getContext().startActivity(intent);
            }
        }

        @Override
        public boolean onLongClick(View v) {
            Log.v("RecyclerViewAdapter", "PRESSLONG:" + String.valueOf(getAdapterPosition()));
            return false;
        }

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.recycler_item_textview);
            button = (Button) itemView.findViewById(R.id.recycler_item_button);
            itemView.setOnClickListener(this);
            name.setOnClickListener(this);
            button.setOnClickListener(this);
        }
    }
}

