package com.huanxindemo20170323.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huanxindemo20170323.R;
import com.huanxindemo20170323.adapter.MessageListViewAdapter;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMConversation;
import com.hyphenate.chat.EMMessage;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private String friend;
    private TextView tvFriend;
    private EditText et_message;
    private List<EMMessage> messageList;
    private EMConversation conversation;//会话对象
    private MessageListViewAdapter mAdapter;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        friend = getIntent().getStringExtra("friend");
        initView();
    }

    private void initView() {
        tvFriend = (TextView) findViewById(R.id.tv_friend_name);
        et_message = (EditText) findViewById(R.id.et_message);
        mListView = (ListView) findViewById(R.id.lv_message);
        messageList = new ArrayList<>();//所有聊天记录
        if (!TextUtils.isEmpty(friend)) {
            tvFriend.setText(friend);
        }
        conversation = EMClient.getInstance().chatManager().getConversation(friend, EMConversation.EMConversationType.Chat, true);
        //通过conversation对象获取会话记录
        messageList = conversation.getAllMessages();
        //注册接收聊天消息的监听器
        EMClient.getInstance().chatManager().addMessageListener(new ChatListener());
        mAdapter = new MessageListViewAdapter(this, messageList);
        mListView.setAdapter(mAdapter);
        //把聊天记录定位到最后一条
        mListView.setSelection(mAdapter.getCount() - 1);
    }

    public void sendClick(View view) {
        String messageInfo = et_message.getText().toString().trim();
        if (TextUtils.isEmpty(messageInfo)) {
            Toast.makeText(ChatActivity.this, "输入为空", Toast.LENGTH_SHORT).show();
        } else {
            //创建一条文本消息，s为消息文本内容，name为对方用户或者群聊的id
            final EMMessage message = EMMessage.createTxtSendMessage(messageInfo, friend);
            //设置消息的类型，单聊的类型是（EMMessage.ChatType.Chat）
            message.setChatType(EMMessage.ChatType.Chat);
            //发送消息
            EMClient.getInstance().chatManager().sendMessage(message);
            message.setMessageStatusCallback(new EMCallBack() {
                @Override
                public void onSuccess() {
                    Log.e("duanlian", "onSuccess:发送成功 ");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            et_message.setText("");
                            //通过conversation对象获取会话记录
                            messageList = conversation.getAllMessages();
                            //创建adapter
                            mAdapter.getMessage(messageList);
                            //把聊天记录定位到最后一条
                            mListView.setSelection(mAdapter.getCount() - 1);

                        }
                    });
                }

                @Override
                public void onError(int code, String error) {
                    Log.e("duanlian", "onError:发送失败code== " + code);
                    Log.e("duanlian", "onError:发送失败error== " + error);
                }

                @Override
                public void onProgress(int progress, String status) {

                }
            });
        }
    }

    /**
     * 聊天消息的监听
     */
    private class ChatListener implements EMMessageListener {


        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            String fromname = null;
            for (EMMessage message : messages) {
                //如果当前消息类型是单聊
                if (EMMessage.ChatType.Chat == message.getChatType()) {
                    fromname = message.getFrom();
                }
                //如果当前的消息发送者是来自于聊天好友的
                if (fromname.equals(friend)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //将所有的两天记录设置为已读状态
                            conversation.markAllMessagesAsRead();
                            messageList = conversation.getAllMessages();
                            mAdapter.getMessage(messageList);
                            //把聊天记录定位到最后一条
                            mListView.setSelection(mAdapter.getCount() - 1);

                        }
                    });

                }
            }
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {

        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {

        }

        @Override
        public void onMessageDelivered(List<EMMessage> messages) {

        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {

        }
    }
}
