package com.huntongo.test_haptik.core;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ashish on 20/5/16.
 */
public class ChatMessages implements Comparable<ChatMessages> {

    private String body;
    private String username;
    private String Name;
    private String imageUrl;
    private String messageTime;

    /**
     *
     * @return
     * The body
     */
    public String getBody() {
        return body;
    }

    /**
     *
     * @param body
     * The body
     */
    public void setBody(String body) {
        this.body = body;
    }

    /**
     *
     * @return
     * The username
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     * The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     * The Name
     */
    public String getName() {
        return Name;
    }

    /**
     *
     * @param Name
     * The Name
     */
    public void setName(String Name) {
        this.Name = Name;
    }

    /**
     *
     * @return
     * The imageUrl
     */
    public String getImageUrl() {
        return imageUrl;
    }

    /**
     *
     * @param imageUrl
     * The image-url
     */
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    /**
     *
     * @return
     * The messageTime
     */
    public String getMessageTime() {
        return messageTime;
    }

    /**
     *
     * @param messageTime
     * The message-time
     */
    public void setMessageTime(String messageTime) {
        this.messageTime = messageTime;
    }
    @Override
    public int compareTo(ChatMessages o) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date date1  = new Date(),date2 = new Date();
        try {
            date1 = dateFormat.parse(getMessageTime());
            date2 = dateFormat.parse(o.getMessageTime());
        }catch (Exception e){

        }
        return date1.compareTo(date2);
    }

}
