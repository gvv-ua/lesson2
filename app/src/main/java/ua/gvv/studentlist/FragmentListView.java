package ua.gvv.studentlist;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ua.gvv.studentlist.data.Student;

import static android.Manifest.permission.READ_CONTACTS;

public class FragmentListView extends Fragment {
    private ArrayList<Student> students;
    private final int PERMISSIONS_REQUEST_READ_CONTACTS = 20;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Bundle extras = getArguments();
        if (extras != null) {
            students = extras.getParcelableArrayList("StudentsList");
            if (students != null) {
                Student student = students.get(0);
            }
        }
        ListViewAdapter adapter = new ListViewAdapter(getActivity(), students);
        ListView listView = (ListView) view.findViewById(R.id.listview_lesson2);
        listView.setAdapter(adapter);
    }

    private void showExplanationDialog() {
        new AlertDialog.Builder(getActivity())
                .setMessage(R.string.read_contacts_err)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{READ_CONTACTS}, PERMISSIONS_REQUEST_READ_CONTACTS);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_READ_CONTACTS: {
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    showContactList();
                } else {
                    Toast toast = Toast.makeText(getActivity(), "You can't see contact list", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        }
    }

    private void showContactList() {
        Intent intent = new Intent(getActivity(), ActivityDetail.class)
                .putExtra(ActivityDetail.DETAIL_TYPE, ActivityDetail.DETAIL_CONTACT_LIST);
        getActivity().startActivity(intent);
    }



}

