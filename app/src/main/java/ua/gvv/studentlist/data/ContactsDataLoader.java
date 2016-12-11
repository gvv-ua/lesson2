package ua.gvv.studentlist.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gvv on 11.12.16.
 */

public class ContactsDataLoader extends AsyncTaskLoader {

    public ContactsDataLoader(Context context) {
        super(context);
    }

    @Override
    public List<Contact> loadInBackground() {
        List<Contact> contacts = new ArrayList<>();
        Context context = getContext();

        String[] projection = new String[]{
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.Contacts.LOOKUP_KEY,
                ContactsContract.Contacts.PHOTO_THUMBNAIL_URI,
                ContactsContract.Contacts.HAS_PHONE_NUMBER
        };

        String sortOrder = ContactsContract.Contacts.Entity.DISPLAY_NAME_PRIMARY + " ASC";
        String selection = ContactsContract.Contacts.Entity.HAS_PHONE_NUMBER + " = ?";
        String[] selectionArgs = {"1"};

        ContentResolver contentResolver = context.getContentResolver();
        Cursor data = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        if ((data != null) && (data.moveToFirst())) {
            while (data.moveToNext()) {
                Contact contact = new Contact();
                contact.setName(data.getString(data.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)));
                //contact.setPhone("123456");
                String photo = data.getString(data.getColumnIndex(ContactsContract.Contacts.PHOTO_THUMBNAIL_URI));
                if (photo != null) {
                    contact.setPhoto(Uri.parse(photo));
                }

                projection = new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER};
                selection = ContactsContract.Data.CONTACT_ID + " = ? " +
                        " and " + ContactsContract.Data.MIMETYPE + " = ? ";
                selectionArgs = new String[]{data.getString(data.getColumnIndex(ContactsContract.Contacts._ID)), ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
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
        return contacts;
    }
}
