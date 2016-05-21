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
import com.huntongo.test_haptik.model.ChatCount;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by ashish on 21/5/16.
 */
public class ChatCountAdapter extends RecyclerView.Adapter<ChatCountAdapter.ChatCountViewHolder> {

    private Context mContext;
    private List<ChatCount> chatCounts;
    private ChatCount chat_Count;

    public ChatCountAdapter(Context mContext, List<ChatCount> chatCounts) {
        this.mContext = mContext;
        this.chatCounts = chatCounts;

    }

    @Override
    public ChatCountAdapter.ChatCountViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_count_layout, null);
        ChatCountViewHolder rcv = new ChatCountViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ChatCountAdapter.ChatCountViewHolder holder, int position) {
        chat_Count = chatCounts.get(position);
        if (chat_Count.image_url.equals("") ) {

        }else{
            Picasso.with(mContext)
                    .load(Uri.parse(chat_Count.image_url))
                    .into(holder.chatView);
        }
        holder.name.setText(chat_Count.name+"");
        holder.username.setText("@ "+chat_Count.username);
        holder.chatcount.setText(chat_Count.count+"");
    }

    @Override
    public int getItemCount() {
        return chatCounts.size();
    }

    class ChatCountViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView chatView;
        TextView username;
        TextView chatcount;

        public ChatCountViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name);
            chatView = (ImageView) itemView.findViewById(R.id.profile1);
            username = (TextView) itemView.findViewById(R.id.username);
            chatcount = (TextView) itemView.findViewById(R.id.chat_count);
        }
    }
}
