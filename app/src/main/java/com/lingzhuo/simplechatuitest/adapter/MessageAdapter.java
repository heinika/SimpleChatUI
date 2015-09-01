package com.lingzhuo.simplechatuitest.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingzhuo.simplechatuitest.R;
import com.lingzhuo.simplechatuitest.moudel.Message;

import java.util.List;

/**
 * Created by heinika on 2015/8/27.
 */
public class MessageAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Message> messagelist;
    private Html.ImageGetter mImageGetter;
    public static final int TYPE_SEND_TEXT=0;
    public static final int TYPE_RECEIVE_TEXT=1;
    public static final int TYPE_SUM = 2;


    public MessageAdapter( List<Message> messagelist,LayoutInflater inflater,Html.ImageGetter mImageGetter) {
        this.inflater = inflater;
        this.messagelist = messagelist;
        this.mImageGetter = mImageGetter;
    }

    /**
     * @param position listview中的位置
     * @return 该位置message的type
     */
    @Override
    public int getItemViewType(int position) {
        return messagelist.get(position).getType();
    }

    /**
     * 获取类型的总数
     * @return  类型的种类总数
     */
    @Override
    public int getViewTypeCount() {
        return TYPE_SUM;
    }

    @Override
    public int getCount() {
        return messagelist.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = messagelist.get(position);
        ViewHolderLeft vhLeft = null;
        ViewHolderRight vhRight = null;
        switch (getItemViewType(position)){
            case TYPE_SEND_TEXT:
                if(convertView==null){
                    vhRight = new ViewHolderRight();
                    convertView = inflater.inflate(R.layout.item_massage_right,null);
                    vhRight.mTextViewTime = (TextView) convertView.findViewById(R.id.textview_chat_time);
                    vhRight.mLinearLayoutSend = (LinearLayout) convertView.findViewById(R.id.linearlayout_send);
                    vhRight.mTextViewSend = (TextView) convertView.findViewById(R.id.textview_send);
                    convertView.setTag(vhRight);
                }else {
                    vhRight = (ViewHolderRight) convertView.getTag();
                }
                    vhRight.mTextViewSend.setText(Html.fromHtml(message.getMassageContent(), mImageGetter, null));
                if (position>0&&message.getTime().equals(messagelist.get(position-1).getTime())){
                    vhRight.mTextViewTime.setVisibility(View.GONE);
                }else{
                    vhRight.mTextViewTime.setText(message.getTime());
                    vhRight.mTextViewTime.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_RECEIVE_TEXT:
                if(convertView==null) {
                    vhLeft = new ViewHolderLeft();
                    convertView = inflater.inflate(R.layout.item_massage_left,null);
                    vhLeft.mTextViewTime = (TextView) convertView.findViewById(R.id.textview_chat_time);
                    vhLeft.mLinearLayoutReceive = (LinearLayout) convertView.findViewById(R.id.linearlayout_receive);
                    vhLeft.mTextViewReceive = (TextView) convertView.findViewById(R.id.textview_receive);
                    convertView.setTag(vhLeft);
                }else {
                    vhLeft = (ViewHolderLeft) convertView.getTag();
                }
                vhLeft.mTextViewReceive.setText(Html.fromHtml(message.getMassageContent(), mImageGetter, null));
                if (position>0&&message.getTime().equals(messagelist.get(position-1).getTime())){
                    vhLeft.mTextViewTime.setVisibility(View.GONE);
                }else{
                    vhLeft.mTextViewTime.setText(message.getTime());
                    vhLeft.mTextViewTime.setVisibility(View.VISIBLE);
                }
                break;
            default:
                break;
        }
        return convertView;
    }



    class ViewHolderLeft{
        TextView mTextViewReceive;
        LinearLayout mLinearLayoutReceive;
        TextView mTextViewTime;
    }

    class ViewHolderRight{
        TextView mTextViewSend;
        LinearLayout mLinearLayoutSend;
        TextView mTextViewTime;
    }
}
