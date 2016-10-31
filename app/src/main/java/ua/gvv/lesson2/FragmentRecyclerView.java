package ua.gvv.lesson2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentRecyclerView extends Fragment {
    private ArrayList<Student> students;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getArguments();
        if (extras != null) {
            students = extras.getParcelableArrayList("StudentsList");
            if (students != null) {
                Student student = students.get(0);
                //Log.v("FragmentRecyclerView", "STUDENT:" + student.getName());
            }
        }
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview_lesson2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //RecyclerViewAdapter adapter = new RecyclerViewAdapter(students);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), students);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

}
