package ua.gvv.studentlist;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by gvv on 26.11.16.
 */

public class FragmentContactList extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static int CONTACT_LOADER_ID = 100;
    private Uri contactUri;
    private String[] projection;
    private RecyclerView contactListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        projection = new String[]
                {
                        ContactsContract.Contacts._ID,
                        ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                        ContactsContract.Contacts.LOOKUP_KEY,
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
                };

        contactListView = (RecyclerView)view.findViewById(R.id.contact_list_recycler);
        contactListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        //Show FragmentContactList
        ImageView image = (ImageView) view.findViewById(R.id.contact_item_add);
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
//                                         Toast toast = Toast.makeText(getActivity(), "Add Contact", Toast.LENGTH_SHORT);
//                                         toast.show();
                                         Intent intent = new Intent(ContactsContract.Intents.Insert.ACTION);
                                         intent.setType(ContactsContract.RawContacts.CONTENT_TYPE);
                                         startActivity(intent);
                                     }
                                 }
        );

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        if (id == CONTACT_LOADER_ID) {
            String sortOrder = ContactsContract.Contacts.Entity.DISPLAY_NAME_PRIMARY + " ASC";

            cursorLoader = new CursorLoader(getActivity(),
                    ContactsContract.Contacts.CONTENT_URI,
                    projection,
                    null,
                    null,
                    sortOrder);
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if ((data != null) && (data.moveToFirst())) {
//            String name = data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
//            TextView textViewview = (TextView) getActivity().findViewById(R.id.contact_list_title);
//            textViewview.setText(name);
            ContactListAdapter adapter = new ContactListAdapter(data);
            contactListView.setAdapter(adapter);

        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
