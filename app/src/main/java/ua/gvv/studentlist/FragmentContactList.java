package ua.gvv.studentlist;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.gvv.studentlist.data.Contact;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by gvv on 26.11.16.
 */

public class FragmentContactList extends Fragment  implements LoaderManager.LoaderCallbacks<Cursor> {
    private final static int CONTACT_LOADER_ID = 100;
    private final static int CONTACT_ADD_INFO_ID = 101;

    private Uri contactUri;
    private String[] projection;
    private RecyclerView contactListView;
    private List<Contact> contacts;

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
                        ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                };

        contactListView = (RecyclerView)view.findViewById(R.id.contact_list_recycler);
        contactListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_contact_item_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(getActivity(), ActivityContact.class);
                                         startActivityForResult(intent, CONTACT_ADD_INFO_ID);
                                     }
                                 }
        );

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == CONTACT_ADD_INFO_ID) && (resultCode == RESULT_OK) && (data != null)) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            Toast toast = Toast.makeText(getContext(), name + "-" + phone, Toast.LENGTH_SHORT);
            toast.show();

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
//            ContentProviderOperation.Builder op = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                    .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, name)
//                    .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE);
//
//            op.withYieldAllowed(true);
//            ops.add(op.build());
            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            //------------------------------------------------------ Names
            if (name != null) {
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(
                                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                                name).build());
            }

            //------------------------------------------------------ Mobile Number
            if (phone != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE,
                                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                                ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                        .build());
            }
            try {
                getActivity().getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            } catch (Exception e) {
                // Log exception
                Log.e(TAG, "Exception encountered while inserting contact: " + e);
            }

        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader cursorLoader = null;
        if (id == CONTACT_LOADER_ID) {
            String sortOrder = ContactsContract.Contacts.Entity.DISPLAY_NAME_PRIMARY + " ASC";
            String selection = ContactsContract.Contacts.Entity.HAS_PHONE_NUMBER + " = ?";
            String[] selectionArgs = {"1"};

            cursorLoader = new CursorLoader(getActivity(),
                    ContactsContract.Contacts.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder);
        }
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        contacts = new ArrayList<>();

        ContentResolver contentResolver = getActivity().getContentResolver();
        if ((data != null) && (data.moveToFirst())) {
            while (data.moveToNext()) {
                Contact contact = new Contact();
                contact.setName(data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));
                //contact.setPhone("123456");
                String photo = data.getString(data.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                if (photo != null) {
                    contact.setPhoto(Uri.parse(photo));
                }

                String[] projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                String selection = ContactsContract.Data.CONTACT_ID + " = ? " +
                        " and " + ContactsContract.Data.MIMETYPE + " = ? ";
                String[] selectionArgs = new String[]{data.getString(data.getColumnIndex(ContactsContract.Contacts._ID)), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
                Cursor cursor = contentResolver.query(ContactsContract.Data.CONTENT_URI,
                        projection,
                        selection,
                        selectionArgs,
                        null);
                if ((cursor != null) && (cursor.moveToFirst())) {
                    contact.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                }
                cursor.close();

                contacts.add(contact);
            }
            data.close();
        }
        ContactListAdapter adapter = new ContactListAdapter(contacts);
        contactListView.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
