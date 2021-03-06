package ua.gvv.studentlist;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ua.gvv.studentlist.data.Contact;

/**
 * Created by gvv on 27.11.16.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> implements Filterable {
    private List<Contact> list;
    private List<Contact> original;
    private String filter = "";

    public ContactListAdapter(List<Contact> list) {
        this.list = list;
        this.original = new ArrayList<Contact>(list);
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

    public void update(List<Contact> contacts) {
        list.clear();
        if (contacts != null) {
            list.addAll(contacts);
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        Filter filter = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                list.clear();
                final FilterResults results = new FilterResults();
                for (final Contact contact : original) {
                    if (contact.getName().toLowerCase().trim().contains(charSequence.toString().toLowerCase())) {
                        list.add(contact);
                    }
                }
                results.values = list;
                results.count = list.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                notifyDataSetChanged();
            }
        };

        return filter;
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
            Uri photoUri = contact.getPhoto();
            if (photoUri != null) {
                photo.setImageURI(photoUri);
            } else {
                photo.setImageResource(R.drawable.img_account);
            }

        }
    }
}
