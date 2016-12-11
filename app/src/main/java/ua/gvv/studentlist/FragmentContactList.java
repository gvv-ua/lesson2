package ua.gvv.studentlist;

import android.Manifest;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ua.gvv.studentlist.data.Contact;
import ua.gvv.studentlist.data.ContactsDataLoader;

import static android.Manifest.permission.WRITE_CONTACTS;
import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

/**
 * Created by gvv on 26.11.16.
 */

public class FragmentContactList extends Fragment  implements LoaderManager.LoaderCallbacks<List<Contact>> {
    private final static int CONTACT_LOADER_ID = 100;
    private final static int CONTACT_ADD_INFO_ID = 101;

    private final int PERMISSIONS_REQUEST_WRITE_CONTACTS = 21;


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
        getLoaderManager().initLoader(CONTACT_LOADER_ID, null, this).forceLoad();;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Contact List");

        contactListView = (RecyclerView)view.findViewById(R.id.contact_list_recycler);
        contactListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        FloatingActionButton fabAdd = (FloatingActionButton) view.findViewById(R.id.fab_contact_item_add);
        fabAdd.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_CONTACTS) != PackageManager.PERMISSION_GRANTED) {

                                             if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.WRITE_CONTACTS)) {
                                                 showExplanationDialog();
                                             } else {
                                                 ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_CONTACTS},  PERMISSIONS_REQUEST_WRITE_CONTACTS);
                                             }
                                         } else {
                                             addContact();
                                         }
                                     }
                                 }
    );
    }

    private void showExplanationDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.read_contacts_err)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_CONTACTS}, PERMISSIONS_REQUEST_WRITE_CONTACTS);
                    }
                })
                .setNegativeButton(R.string.go_to_settings, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ((ActivityDetail)getActivity()).openPermissionSettings();
                    }
                })
                .create()
                .show();
    }

    private void addContact() {
        Intent intent = new Intent(getActivity(), ActivityDetail.class)
                .putExtra(ActivityDetail.DETAIL_TYPE, ActivityDetail.DETAIL_CONTACT_ADD);
        startActivityForResult(intent, CONTACT_ADD_INFO_ID);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_WRITE_CONTACTS: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    addContact();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "You can't see contact list", Toast.LENGTH_SHORT);
                    toast.show();
                }
                return;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if ((requestCode == CONTACT_ADD_INFO_ID) && (resultCode == RESULT_OK) && (data != null)) {
            String name = data.getStringExtra("name");
            String phone = data.getStringExtra("phone");
            Toast toast = Toast.makeText(getContext(), name + "-" + phone, Toast.LENGTH_SHORT);
            toast.show();

            ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

            ops.add(ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .build());

            if (name != null) {
                ops.add(ContentProviderOperation.newInsert(
                        ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                        .build());
            }

            if (phone != null) {
                ops.add(ContentProviderOperation.
                        newInsert(ContactsContract.Data.CONTENT_URI)
                        .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                        .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                        .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, phone)
                        .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
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
    public Loader<List<Contact>> onCreateLoader(int id, Bundle args) {
        return new ContactsDataLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<Contact>> loader, List<Contact> data) {
        updateUI(data);
    }

    @Override
    public void onLoaderReset(Loader<List<Contact>> loader) {
        updateUI(null);
    }

    public void updateUI(List<Contact> users) {
        ProgressBar progressBar = (ProgressBar)getActivity().findViewById(R.id.pb_contacts);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        if (contactListView.getAdapter() == null) {
            contactListView.setAdapter(new ContactListAdapter(users));
        } else {
            ContactListAdapter adapter = (ContactListAdapter) contactListView.getAdapter();
            adapter.update(users);
        }
    }
}
