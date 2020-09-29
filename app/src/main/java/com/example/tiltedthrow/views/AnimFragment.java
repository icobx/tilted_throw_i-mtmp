package com.example.tiltedthrow.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.tiltedthrow.MainViewModel;
import com.example.tiltedthrow.R;
import com.example.tiltedthrow.utility.Point;

import java.util.List;



public class AnimFragment extends Fragment {
    public static final String TAG = "AnimFragment";

    private View view;
    private FragmentActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_anim, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        activity = requireActivity();

        final MainViewModel mainViewModel = new ViewModelProvider(activity).get(MainViewModel.class);

        final ImageView imageView = view.findViewById(R.id.img);
        final EditText speedInput = view.findViewById(R.id.speed);
        final EditText angleInput = view.findViewById(R.id.angle);
        Button submit = view.findViewById(R.id.button_anim);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float speed = speedInput.getText().length() > 0 ? Float.parseFloat(speedInput.getText().toString()) : Float.MIN_VALUE;
                float angleDeg = angleInput.getText().length() > 0 ? Float.parseFloat(angleInput.getText().toString()) : Float.MIN_VALUE;

                if (!wrongOrNoEntries(speed, angleDeg)) {
                    mainViewModel.getPoints(speed, angleDeg).observe(getViewLifecycleOwner(), getDataObserver(imageView));;
                }
            }
        });
    }

    private Observer<List<Point>> getDataObserver(final ImageView imageView) {
        return new Observer<List<Point>>() {
            @Override
            public void onChanged(List<Point> points) {
                animSetup(points, imageView);
            }
        };
    }

    private Animator.AnimatorListener onEndListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {}
        
        @Override
        public void onAnimationEnd(Animator animation) {
            Toast.makeText(activity, "koniec animacie", Toast.LENGTH_LONG).show();
        }
        
        @Override
        public void onAnimationCancel(Animator animation) {}
        @Override
        public void onAnimationRepeat(Animator animation) {}
    };

    private void animSetup(List<Point> points, ImageView imageView) {
        ObjectAnimator[][] anims = new ObjectAnimator[2][points.size()];
        for (int i = 0; i < anims[0].length; i++) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(imageView, "translationX", points.get(i).getX());
            animation.setInterpolator(new LinearInterpolator());
            animation.setDuration(50);

            ObjectAnimator animation2 = ObjectAnimator.ofFloat(imageView, "translationY", -points.get(i).getY());
            animation2.setInterpolator(new LinearInterpolator());
            animation2.setDuration(50);

            anims[0][i] = animation;
            anims[1][i] = animation2;
        }

        AnimatorSet set = new AnimatorSet();
        AnimatorSet set2 = new AnimatorSet();
        for (int i = 1; i < anims[0].length; i++) {
            set.play(anims[0][i]).after(anims[0][i-1]);
            set2.play(anims[1][i]).after(anims[1][i-1]);
        }

        AnimatorSet mainSet = new AnimatorSet();
        mainSet.play(set2).with(set);
        mainSet.addListener(onEndListener);
        mainSet.start();
    }

    private boolean wrongOrNoEntries(float speed, float angle) {
        String message = null;
        
        if (speed == Float.MIN_VALUE || angle == Float.MIN_VALUE)
            message = view.getResources().getText(R.string.empty_input).toString();

        else if (speed < 0 || speed > 10000)
            message = view.getResources().getText(R.string.speed_input_off).toString();

        else if (angle < 0 || angle > 90)
            message = view.getResources().getText(R.string.angle_input_off).toString();

        if (message != null) {
            Spannable centeredMessage = new SpannableString(message);
            centeredMessage.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                    0, message.length() - 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            Toast.makeText(activity, centeredMessage, Toast.LENGTH_LONG).show();

            return true;
        }
        return false;
    }
}
