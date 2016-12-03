package ua.gvv.studentlist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ua.gvv.studentlist.data.Contact;

/**
 * Created by gvv on 27.11.16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
    private List<Contact> list;

    public ContactListAdapter(List<Contact> list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ContactListAdapter.ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactListAdapter.ContactViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ContactListAdapter.ContactViewHolder holder, int position) {
        holder.bind(list.get(position));
    }

    class ContactViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private ImageView photo;


        public ContactViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.tv_contact_name);
            phone = (TextView) itemView.findViewById(R.id.tv_contact_phone);
            photo = (ImageView) itemView.findViewById(R.id.iv_contact_photo);
        }

        public void bind(Contact contact) {
            name.setText(contact.getName());
            phone.setText(contact.getPhone());
            photo.setImageURI(contact.getPhoto());
        }
    }
}
