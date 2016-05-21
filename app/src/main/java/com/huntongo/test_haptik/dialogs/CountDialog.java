package com.huntongo.test_haptik.dialogs;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.huntongo.test_haptik.R;
import com.huntongo.test_haptik.adapter.ChatCountAdapter;
import com.huntongo.test_haptik.model.ChatCount;
import com.huntongo.test_haptik.utils.AppUtils;

import java.util.List;

/**
 * Created by ashish on 21/5/16.
 */
public class CountDialog extends DialogFragment {
    int height = 0, width = 0;
    private View view;
    private RecyclerView mRecyclerView;
    private TextView mTextView;
    private LinearLayoutManager linearLayoutManager;
    private List<ChatCount> mChatCounts;
    private ChatCountAdapter mChatCountAdapter;

    public static CountDialog newInstance() {
        CountDialog countDialog = new CountDialog();
        return countDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        view = inflater.inflate(R.layout.dialog_chat_count, container, false);
        initLayout();
        getDataFromDB();

        return view;
    }

    public void initLayoutDimesion() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        height = displaymetrics.heightPixels;
        width = displaymetrics.widthPixels;
        getDialog().getWindow().setLayout((int) (width), (int) (height * 0.70));
    }

    public void initLayout() {

        mTextView = (TextView) view.findViewById(R.id.closeIM);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.countRecyclerView);
        linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

    }

    public void getDataFromDB() {


        new Thread(new Runnable() {
            public void run() {
                mChatCounts = AppUtils.getAllData();
                initRecyclerView();
            }
        }).start();


    }

    public void initRecyclerView(){
        mChatCountAdapter = new ChatCountAdapter(getActivity(),mChatCounts);
        mRecyclerView.setAdapter(mChatCountAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        initLayoutDimesion();
    }
}
