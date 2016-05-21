package com.huntongo.test_haptik.utils;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;
import com.huntongo.test_haptik.core.ChatMessages;
import com.huntongo.test_haptik.model.ChatCount;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by ashish on 20/5/16.
 */
public class AppUtils {

    public static String JSON_URL = "http://haptik.co/android/test_data/";
    public static final String TAG = "chat_message";
    private static String updateSet = " count = ? ," +
            " latest_time = ?";

    public static void getAll(ChatMessages posts) {
        ChatCount chatCount;
        List<ChatCount> mChatCount = new Select()
                .from(ChatCount.class)
                .where("username = ?", posts.getUsername())
                .execute();
        if (mChatCount.size() <= 0) {
            chatCount = new ChatCount(posts.getUsername(), posts.getName(), posts.getImageUrl(), 1, posts.getMessageTime());
            chatCount.save();
        } else {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                chatCount = mChatCount.get(0);
                Date date1 = dateFormat.parse(posts.getMessageTime());
                Date date2 = dateFormat.parse(chatCount.latest_time);
                if (date1.getTime() > date2.getTime()) {
                    int cnt = chatCount.count;
                    cnt = cnt + 1;
                    new Update(ChatCount.class)
                            .set(updateSet, new Object[]{cnt, posts.getMessageTime()})
                            .where("username = ?", posts.getUsername())
                            .execute();
                }
            } catch (Exception e) {

            }
        }
    }


    public static List<ChatCount> getAllData() {
        return new Select()
                .from(ChatCount.class)
                .execute();
    }


}
