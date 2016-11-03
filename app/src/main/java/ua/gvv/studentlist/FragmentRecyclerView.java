package ua.gvv.studentlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
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

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview_lesson2);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), students);
        recyclerView.setAdapter(adapter);
    }
}
