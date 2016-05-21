package com.huntongo.test_haptik.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by ashish on 21/5/16.
 */
@Table(name = "chat_count")
public class ChatCount extends Model{
    @Column(name = "username")
    public String username;

    @Column(name = "name")
    public String name;

    @Column(name = "image_url")
    public String image_url;

    @Column(name = "count")
    public int count;

    @Column(name = "latest_time")
    public String latest_time;

    public ChatCount(){
        super();
    }
    public ChatCount(String username,String name,String image_url,int count,String latest_time){
        super();
        this.username = username;
        this.name = name;
        this.image_url = image_url;
        this.count = count;
        this.latest_time = latest_time;
    }

}
