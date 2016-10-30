package ua.gvv.lesson2;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentListView extends Fragment {
    private ArrayAdapter<String> adapter;

    public FragmentListView() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayList<String> fakeData = new ArrayList<String>(Arrays.asList(new String[]{
                "Today - light cloud, no precipitation - 0/+8",
                "Tomorrow - cloudy, clear at times, no precipitation - +3/+9",
                "Sunday - overcast, no precipitation - +5/+8",
                "Monday - cloudy, clear at times, no precipitation - +3/+6",
                "Tuesday - light cloud, no precipitation - -1/+5",
                "Wednesday - clear, no precipitation - 1/+5",
                "Thursday - cloud, no precipitation - -1/+6"
        }));
        adapter = new ArrayAdapter<String>(getActivity(), R.layout.list_item, R.id.list_item_textview, fakeData);


        View rootView = inflater.inflate(R.layout.fragment_list_view, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.listview_lesson2);
        listView.setAdapter(adapter);

        return rootView;
    }


}

