package com.example.dell.afinal.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dell.afinal.Activity.HistoryNotificationActivity;
import com.example.dell.afinal.Activity.MyNotificationActivity;
import com.example.dell.afinal.R;
import com.example.dell.afinal.Utils.ToastUtil;
import com.example.dell.afinal.View.CircleImageView;
import com.example.dell.afinal.bean.MessageEvent;
import com.example.dell.afinal.bean.SystemNotification;
import com.example.dell.afinal.bean.User;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class SystemNotifAdapter extends RecyclerView.Adapter<SystemNotifAdapter.ViewHolder> {
    private List<SystemNotification> notifications;
    private Context mContext;
    private View mView;

    public SystemNotifAdapter(List<SystemNotification> list) {
        notifications = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();
        }
        if (mContext instanceof MyNotificationActivity) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.notification_item, parent,
                    false);
        } else if (mContext instanceof HistoryNotificationActivity) {
            mView = LayoutInflater.from(mContext).inflate(R.layout.history_item, parent,
                    false);
        }
        final ViewHolder viewHolder = new ViewHolder(mView);
        viewHolder.readTag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onReadTagClicked(viewHolder);
            }
        });
        return viewHolder;
    }

    // 点击标记已读
    private void onReadTagClicked(final ViewHolder holder) {
        if (holder.readTag.getText().toString().equals("已读"))
            return;
        User user = BmobUser.getCurrentUser(User.class);
        BmobRelation relation = new BmobRelation();
        SystemNotification notification = new SystemNotification();
        notification.setObjectId(holder.id);
        relation.add(notification);
        user.setSysNotifications(relation);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    onCommitSuccess(holder);
                } else {
                    Log.e("提交标记失败:", e.toString());
                }
            }
        });
    }

    // 提交成功, 提醒更新消息列表, 不再显示已读消息
    private void onCommitSuccess(ViewHolder holder) {
        holder.readTag.setText("已读");
        holder.readTag.setTextColor(mContext.getResources().getColor(R.color.white));
        holder.readTag.setBackgroundColor(mContext.getResources().getColor(R.color.colorPrimary));
        ToastUtil.toast(mContext, "标为已读");
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        SystemNotification notification = notifications.get(position);
        holder.title.setText(notification.getTitle());
        holder.content.setText(notification.getContent());
        holder.date.setText(notification.getCreatedAt());
        holder.id = notification.getObjectId();
    }

    @Override
    public int getItemCount() {
        return notifications.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        View view;
        CircleImageView userImage;    // 通知来源的头像
        TextView userName;            // 通知来源的用户名(教师或管理员)
        TextView title;               // 通知的标题
        TextView content;             // 通知的内容
        TextView date;                // 通知的日期
        TextView readTag;             // 标为已读
        String id;                    // 通知的唯一标识

        ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            userImage = itemView.findViewById(R.id.cms_team);
            userName = itemView.findViewById(R.id.username);
            title = itemView.findViewById(R.id.notification_title);
            content = itemView.findViewById(R.id.notification_content);
            date = itemView.findViewById(R.id.notification_date);
            readTag = itemView.findViewById(R.id.read_tag);
        }
    }
}
