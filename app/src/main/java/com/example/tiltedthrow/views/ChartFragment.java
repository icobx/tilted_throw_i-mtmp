package com.example.tiltedthrow.views;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tiltedthrow.MainViewModel;
import com.example.tiltedthrow.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.List;

public class ChartFragment extends Fragment {
    public static final String TAG = "ChartFragment";
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_chart, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        final MainViewModel mainViewModel = new ViewModelProvider(requireActivity()).get(MainViewModel.class);

        LineChart chart = view.findViewById(R.id.chart);

        mainViewModel.getEntries().observe(getViewLifecycleOwner(), getEntriesObserver(chart));
    }

    private Observer<List<Entry>> getEntriesObserver(LineChart chart) {
        return new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) { setUpChart(chart, entries); }
        };
    }

    private void setUpChart(LineChart chart, List<Entry> entries) {
        LineDataSet dataSet = new LineDataSet(entries, view.getResources().getString(R.string.dataset_label));

        dataSet.setColor(Color.RED);
        dataSet.setValueTextColor(Color.RED);



        chart.setData(new LineData(dataSet));
        chart.setNoDataText(view.getResources().getString(R.string.no_data_text));
        chart.getDescription().setText(view.getResources().getString(R.string.chart_desc));
        chart.getDescription().setTextColor(Color.WHITE);
        chart.setDrawGridBackground(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setAxisLineColor(Color.WHITE);
        chart.getAxisLeft().setTextColor(Color.WHITE);
        chart.getAxisLeft().setAxisMinimum(0f);

        chart.getAxisRight().setEnabled(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setAxisLineColor(Color.WHITE);
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);


        chart.invalidate();
    }
}
