package ua.gvv.lesson2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentListView extends Fragment {
    private ArrayList<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getArguments();
        if (extras != null) {
            students = extras.getParcelableArrayList("StudentsList");
            if (students != null) {
                Student student = students.get(0);
                //Log.v("FragmentListView", "STUDENT:" + student.getName());
            }
        }

        ListViewAdapter adapter = new ListViewAdapter(getActivity(), students);
        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.listview_lesson2);
        listView.setAdapter(adapter);

        return rootView;
    }


}

