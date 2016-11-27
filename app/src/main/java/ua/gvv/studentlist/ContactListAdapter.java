package ua.gvv.studentlist;

import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by gvv on 27.11.16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private Cursor cursor;

    public ContactListAdapter(Cursor cursor) {
        this.cursor = cursor;
    }

    @Override
    public int getItemCount() {
        return cursor.getCount();
    }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactListAdapter.ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        cursor.moveToPosition(position);
        String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
        holder.name.setText(name);
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView name;

        public ContactViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.contact_item_textview);
        }
    }
}
