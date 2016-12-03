package ua.gvv.studentlist;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

import ua.gvv.studentlist.data.Student;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentListView extends Fragment {
    private ArrayList<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        return rootView;
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

        //Show FragmentImageSelector
        ImageView image = (ImageView) view.findViewById(R.id.list_view_image);
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(getActivity(), ActivityDetail.class)
                                                 .putExtra(ActivityDetail.DETAIL_TYPE, ActivityDetail.DETAIL_IMAGE_SELECTOR);
                                         getActivity().startActivity(intent);
                                     }
                                 }
        );

        //Show FragmentContactList
        image = (ImageView) view.findViewById(R.id.list_view_contacts);
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Intent intent = new Intent(getActivity(), ActivityDetail.class)
                                                 .putExtra(ActivityDetail.DETAIL_TYPE, ActivityDetail.DETAIL_CONTACT_LIST);
                                         getActivity().startActivity(intent);
                                     }
                                 }
        );

    }
}

