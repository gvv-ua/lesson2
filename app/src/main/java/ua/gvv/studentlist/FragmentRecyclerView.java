package ua.gvv.studentlist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;
import ua.gvv.studentlist.data.Student;

/**
 * Created by gvv on 30.10.16.
 */

public class FragmentRecyclerView extends Fragment {
    RecyclerView rvStudents;
    private String filter = "";
    RealmResults<Student> students;
    private final String TAG = "FragmentRecyclerView";

    private final RealmChangeListener<RealmResults<Student>> changeListener = new RealmChangeListener<RealmResults<Student>>() {
        @Override
        public void onChange(RealmResults<Student> elements) {
            updateUI(elements);
        }
    };

    private void updateUI(RealmResults<Student> elements) {
        rvStudents.setAdapter(new RecyclerViewAdapter(getActivity(), elements));
//        if (rvStudents.getAdapter() == null) {
//            rvStudents.setAdapter(new RecyclerViewAdapter(getActivity(), elements));
//        } else {
//            RecyclerViewAdapter adapter = (RecyclerViewAdapter) rvStudents.getAdapter();
//            adapter.notifyDataSetChanged();
//        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Recycler View");

        rvStudents = (RecyclerView)view.findViewById(R.id.recyclerview_lesson2);
        rvStudents.setLayoutManager(new LinearLayoutManager(getActivity()));

        Realm realm = Realm.getDefaultInstance();
        students = realm.where(Student.class).findAllSortedAsync("searchName", Sort.ASCENDING);
        students.addChangeListener(changeListener);


        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(ItemTouchHelper.DOWN,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                RecyclerViewAdapter adapter = (RecyclerViewAdapter)rvStudents.getAdapter();
                if (adapter != null) {
                    adapter.onItemDismiss(viewHolder.getAdapterPosition());
                }
            }
        });
        itemTouchHelper.attachToRecyclerView(rvStudents);

        rvStudents.setItemAnimator(new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, @NonNull List<Object> payloads) {
                return true;
            }
        });

        setHasOptionsMenu(true);
    }

    @Override
    public void onDestroyView() {
        students.removeChangeListeners();
        super.onDestroyView();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_tool_bar, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView =
                (SearchView) MenuItemCompat.getActionView(searchItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (!newText.equals(filter)) {
                    Realm realm = Realm.getDefaultInstance();
                    students.removeChangeListeners();
                    students = realm.where(Student.class).contains("searchName", newText.toLowerCase()).findAllSortedAsync("searchName", Sort.ASCENDING);
                    students.addChangeListener(changeListener);
                    filter = newText;
                    return true;
                }
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
