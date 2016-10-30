package ua.gvv.lesson2;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentRecyclerView extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle extras = getArguments();
        if (extras != null) {
            ArrayList<Student> students  = extras.getParcelableArrayList("StudentsList");
            if (students != null) {
                Log.v("FragmentRecyclerView", "STUDENT222");
                Student student = students.get(0);
                Log.v("FragmentRecyclerView", "STUDENT:" + student.getName());
            }
        }



        ArrayList<String> fakeData = new ArrayList<String>(Arrays.asList(new String[]{
                "Today - light cloud, no precipitation - 0/+8",
                "Tomorrow - cloudy, clear at times, no precipitation - +3/+9",
                "Sunday - overcast, no precipitation - +5/+8",
        }));



        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        RecyclerView recyclerView = (RecyclerView)rootView.findViewById(R.id.recyclerview_lesson2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(
                new ArrayList<String>(Arrays.asList(new String[]{
                        "Today - light cloud, no precipitation - 0/+8",
                        "Tomorrow - cloudy, clear at times, no precipitation - +3/+9",
                        "Sunday - overcast, no precipitation - +5/+8",
                }))
        );
        recyclerView.setAdapter(adapter);

        //listView.setAdapter(adapter);

        return rootView;
    }

}
