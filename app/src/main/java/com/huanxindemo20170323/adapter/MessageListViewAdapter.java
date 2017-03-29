package com.huanxindemo20170323.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huanxindemo20170323.R;
import com.huanxindemo20170323.utils.ImageUtils;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import java.util.List;

/**
 * Created by android on 2017/3/23.
 */
public class MessageListViewAdapter extends BaseAdapter {
    private Context context;
    private List<EMMessage> list;
    private View view;

    public MessageListViewAdapter(Context context, List<EMMessage> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list!=null?list.size():0;
    }

    @Override
    public Object getItem(int position) {
         return getItem(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //获取消息的来源（发送者）
        String from = list.get(position).getFrom();
        //获取消息的内容
        String messageText = list.get(position).getBody().toString();
        messageText = messageText.substring(5, messageText.length() - 1);
        //获取当前登录的用户
        String currentUser = EMClient.getInstance().getCurrentUser();
        //如果消息来源来自用户
        if (from.equals(currentUser)) {
            //引用右边的布局
            view = LayoutInflater.from(context).inflate(R.layout.item_chat2, null);
            TextView textRight = (TextView) view.findViewById(R.id.item_chat_from);
            textRight.setText(ImageUtils.formatString(context,messageText));
        } else {
            view = LayoutInflater.from(context).inflate(R.layout.item_chat1, null);
            TextView textLeft = (TextView) view.findViewById(R.id.item_chat_to);
            textLeft.setText(ImageUtils.formatString(context,messageText));
        }
        return view;
    }

    public void getMessage( List<EMMessage> list) {
        this.list = list;
        notifyDataSetChanged();

    }
}
