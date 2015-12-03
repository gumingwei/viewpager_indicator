package com.mingwei.example;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleFragment extends Fragment {

    public static final String TITLE = "title";

    private View mContentView;

    private TextView mTitle;

    public static SimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContentView = LayoutInflater.from(getActivity()).inflate(R.layout.simple_fragment, null);
        mTitle = (TextView) mContentView.findViewById(R.id.simple_fragment_title);
        Bundle bundle = getArguments();
        mTitle.setText(bundle.getString(TITLE));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return mContentView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//		ViewGroup viewGroup = (ViewGroup) mContentView.getParent();
//		if (viewGroup != null) {
//			viewGroup.removeAllViewsInLayout();
//		}

    }
}
