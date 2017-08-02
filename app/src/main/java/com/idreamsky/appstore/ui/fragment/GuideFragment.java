package com.idreamsky.appstore.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.idreamsky.appstore.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class GuideFragment extends Fragment {

    @BindView(R.id.backgroundLayout)
    FrameLayout layout;
    Unbinder unbinder;

    public static GuideFragment newInstance(int color) {
        GuideFragment fragment = new GuideFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("color", color);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guide, null);
        unbinder = ButterKnife.bind(this, view);
        layout.setBackgroundColor(getArguments().getInt("color"));
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
