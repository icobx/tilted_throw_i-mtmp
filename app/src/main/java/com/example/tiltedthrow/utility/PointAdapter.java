package com.example.tiltedthrow.utility;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tiltedthrow.R;

import java.util.ArrayList;
import java.util.List;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.PointHolder> {
    private List<Point> points = new ArrayList<>();

    @NonNull
    @Override
    public PointHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PointHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_point, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PointHolder holder, int position) {
        Point point = points.get(position);

        holder.time.setText(point.getTasString());
        holder.x.setText(point.getXasString());
        holder.y.setText(point.getYasString());
    }

    public void setPoints(List<Point> points) {
        this.points = points;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return points.size();
    }

    static class PointHolder extends RecyclerView.ViewHolder {
        private TextView time, x, y;

        public PointHolder(@NonNull View itemView) {
            super(itemView);

            time = itemView.findViewById(R.id.textview_time);
            x = itemView.findViewById(R.id.textview_x);
            y = itemView.findViewById(R.id.textview_y);
        }
    }
}
