package com.huntongo.test_haptik.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.huntongo.test_haptik.R;
import com.huntongo.test_haptik.core.ChatMessages;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ashish on 20/5/16.
 */
public class ChatMessageAdapter extends RecyclerView.Adapter<ChatMessageAdapter.ChatViewHolder> {

    private Context mContext;
    private ArrayList<ChatMessages> messages;
    private ChatMessages mChatMessages;

    public ChatMessageAdapter(Context mContext, ArrayList<ChatMessages> messages) {
        this.mContext = mContext;
        this.messages = messages;
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_layout, null);
        ChatViewHolder rcv = new ChatViewHolder(layoutView);
        return rcv;
    }


    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        mChatMessages = messages.get(position);

        if (mChatMessages.getImageUrl().equals("") ) {
            Picasso.with(mContext)
                    .load("http://img5.ropose.com/userImages/16557067377322653404501466127117042162245336_circle_100x100.png")
                    .into(holder.chatView);
        }else{
            Picasso.with(mContext)
                    .load(Uri.parse(mChatMessages.getImageUrl()))
                    .into(holder.chatView);
        }
        holder.message.setText(mChatMessages.getBody());

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date date1 = dateFormat.parse(mChatMessages.getMessageTime());
            holder.time.setText(date1.getHours()+":"+date1.getMinutes()+":"+date1.getSeconds());
        }catch (Exception e){

        }

    }


    @Override
    public int getItemCount() {
        return messages.size();
    }


    class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView message;
        ImageView chatView;
        TextView time;

        public ChatViewHolder(View itemView) {
            super(itemView);
            message = (TextView) itemView.findViewById(R.id.message);
            chatView = (ImageView) itemView.findViewById(R.id.profileImage);
            time = (TextView) itemView.findViewById(R.id.time);
        }
    }
}
