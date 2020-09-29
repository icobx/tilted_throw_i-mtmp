package com.example.tiltedthrow;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.tiltedthrow.utility.Point;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private LiveData<List<Point>> pointsLive = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Point>> getPoints(float v0, float angleDeg) {
        List<Point> points = new ArrayList<>();

        float x, y = Float.MAX_VALUE, t = .5f, g = 9.81f;
        double angle = Math.toRadians(angleDeg);

        while (y > 0) {
            x = (float) (0 + v0 * t * Math.cos(angle));
            y = (float) (0 + v0 * t * Math.sin(angle) - (g * t * t / 2));

            points.add(new Point(x, y, t));

            t += .5;
        }
        this.pointsLive = new MutableLiveData<>(points);
        return pointsLive;
    }

    public LiveData<List<Point>> getPoints() {
        return this.pointsLive;
    }

    public LiveData<List<Entry>> getEntries() {
        return Transformations.map(pointsLive, points -> {
            List<Entry> entries = new ArrayList<>();

            if (points != null) {
                entries.add(new Entry(0f, 0f));

                for (Point p : points) entries.add(new Entry(p.getT(), p.getY()));
                return entries;
            }
            return entries;
        });
    }
}
