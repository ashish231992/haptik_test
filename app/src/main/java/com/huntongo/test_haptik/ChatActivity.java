package com.huntongo.test_haptik;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.huntongo.test_haptik.adapter.ChatMessageAdapter;
import com.huntongo.test_haptik.core.ChatMessages;
import com.huntongo.test_haptik.dialogs.CountDialog;
import com.huntongo.test_haptik.utils.AppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

public class ChatActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    private JsonObjectRequest mJsonObjectRequest;
    private RecyclerView mRecyclerView;
    private ChatMessageAdapter messageAdapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<ChatMessages> mChatMessages;
    private JSONArray result;
    private ChatMessages chatMessages;
    private JSONObject itemObject;
    private ImageView chatDetails;
    private FrameLayout progressBarHolder;
    private AlphaAnimation inAnimation;
    private AlphaAnimation outAnimation;
    private FloatingActionButton sendButton;
    private EditText sendEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initializeToolbar();
        initializeRequestQueue();
        initializeRequest();

    }

    public void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("HAPTIK");
        setSupportActionBar(toolbar);
        chatDetails = (ImageView) toolbar.findViewById(R.id.showChatData);
        chatDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getSupportFragmentManager();
                CountDialog mCountDialog = CountDialog.newInstance();
                mCountDialog.show(fm, "chat_dialog");
            }
        });
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        sendButton = (FloatingActionButton) findViewById(R.id.fab);
        sendEditText = (EditText) findViewById(R.id.et1);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sendEditText.getText().toString().equalsIgnoreCase("")) {
                    Toast.makeText(ChatActivity.this, "Please Enter Some Message", Toast.LENGTH_LONG).show();
                } else {
                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    String strDate = dateFormat.format(c.getTime());
                    chatMessages = new ChatMessages();
                    chatMessages.setBody(sendEditText.getText().toString());
                    chatMessages.setUsername("ashish231992");
                    chatMessages.setName("Ashish");
                    chatMessages.setMessageTime(strDate);
                    chatMessages.setImageUrl("");
                    mChatMessages.add(chatMessages);
                    sendEditText.setText("");
                    enterIntoDB();
                    initViews();
                }
            }
        });
        mChatMessages = new ArrayList<ChatMessages>();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    public void initializeRequestQueue() {
        Cache cache = new DiskBasedCache(getCacheDir(), 1024 * 1024);
        Network network = new BasicNetwork(new HurlStack());
        mRequestQueue = new RequestQueue(cache, network);
        mRequestQueue.start();
    }

    public void initializeRequest() {
        enableProgress();
        mJsonObjectRequest = new JsonObjectRequest(Request.Method.GET, AppUtils.JSON_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                initializeArrayList(response);
                enterIntoDB();
                disableProgress();
                initViews();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                disableProgress();
            }
        });
        mJsonObjectRequest.setTag(AppUtils.TAG);
        mRequestQueue.add(mJsonObjectRequest);
    }

    public void enableProgress() {
        inAnimation = new AlphaAnimation(0f, 1f);
        inAnimation.setDuration(200);
        progressBarHolder.setAnimation(inAnimation);
        progressBarHolder.setVisibility(View.VISIBLE);
    }

    public void disableProgress() {
        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }

    public void enterIntoDB() {
        try {
            for (int i = 0; i < mChatMessages.size(); i++) {
                Log.d("Time", mChatMessages.get(i).getUsername() + "::::" + mChatMessages.get(i).getMessageTime());
                AppUtils.getAll(mChatMessages.get(i));
            }
        } catch (Exception e) {

        }

    }

    public void initializeArrayList(JSONObject response) {
        try {
            result = response.getJSONArray("messages");
            for (int i = 0; i < result.length(); i++) {
                chatMessages = new ChatMessages();
                itemObject = result.getJSONObject(i);
                chatMessages.setBody(itemObject.getString("body"));
                chatMessages.setImageUrl(itemObject.getString("image-url"));
                chatMessages.setName(itemObject.getString("Name"));
                chatMessages.setUsername(itemObject.getString("username"));
                chatMessages.setMessageTime(itemObject.getString("message-time"));
                mChatMessages.add(chatMessages);
            }
            Collections.sort(mChatMessages);
        } catch (Exception e) {

        }
    }

    public void initViews() {
        messageAdapter = new ChatMessageAdapter(this, mChatMessages);
        mRecyclerView.setAdapter(messageAdapter);

    }

}
