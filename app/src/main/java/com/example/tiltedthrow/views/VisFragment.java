package com.example.tiltedthrow.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiltedthrow.MainViewModel;
import com.example.tiltedthrow.R;
import com.example.tiltedthrow.utility.Point;
import com.example.tiltedthrow.utility.PointAdapter;

import java.util.List;

public class VisFragment extends Fragment {
    public static final String TAG = "VisFragment";

    private PointAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vis, container, false);

        adapter = new PointAdapter();

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_vis);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        mainViewModel.getPoints().observe(getViewLifecycleOwner(), pointsObserver);
    }

    private Observer<List<Point>> pointsObserver = new Observer<List<Point>>() {
        @Override
        public void onChanged(List<Point> points) {
            if (points != null)
                adapter.setPoints(points);
        }
    };
}
