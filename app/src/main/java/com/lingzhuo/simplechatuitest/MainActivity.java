package com.lingzhuo.simplechatuitest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.lingzhuo.simplechatuitest.adapter.FaceAdapter;
import com.lingzhuo.simplechatuitest.adapter.MessageAdapter;
import com.lingzhuo.simplechatuitest.moudel.Face;
import com.lingzhuo.simplechatuitest.moudel.Message;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener {
    private Button mBtnReceive;
    private Button mBtnSend;
    private ImageButton mImageButtonFace;
    private EditText mEditTextMsg;
    private ListView mListViewChat;
    private GridView mGridViewFace;
    private List<Message> messages;
    private List<Face> faces;
    private LayoutInflater inflater;
    private MessageAdapter messageAdapter;
    private FaceAdapter faceAdapter;
    private String time;
    private Html.ImageGetter mImageGetter;
    private ImageButton mImageButtonCalander;
    private PopupWindow popupWindow;
    private RelativeLayout mRelativeLayoutHeader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnReceive = (Button) findViewById(R.id.button_receive);
        mBtnSend = (Button) findViewById(R.id.button_send);
        mEditTextMsg = (EditText) findViewById(R.id.edittext_msg);
        mListViewChat = (ListView) findViewById(R.id.listview_chat);
        mGridViewFace = (GridView) findViewById(R.id.gridview_face);
        mImageButtonFace = (ImageButton) findViewById(R.id.imagebutton_face);
        mImageButtonCalander = (ImageButton) findViewById(R.id.imagebutton_calender);
        mRelativeLayoutHeader = (RelativeLayout) findViewById(R.id.relativelayout_header);

        inflater = getLayoutInflater();
        /**
         * 设置底部发送栏
         */
        messages = new ArrayList<Message>();
        mBtnSend.setOnClickListener(this);
        mBtnReceive.setOnClickListener(this);
        mImageButtonFace.setOnClickListener(this);
        mEditTextMsg.setOnClickListener(this);
        mImageButtonCalander.setOnClickListener(this);


//        mImageGetter=new Html.ImageGetter() {
//            @Override
//            public Drawable getDrawable(String source) {
//                Drawable drawable = getResources().getDrawable(Integer.parseInt(source));
//                drawable.setBounds(0,0,50,50);
//                return drawable;
//            }
//        };
        /**
         * 用于解析富文本中的表情图片
         */
        mImageGetter = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Class clazz = R.mipmap.class;
                try {
                    Field field = clazz.getDeclaredField(source);
                    int sourceId = field.getInt(field);
                    Drawable drawable = getResources().getDrawable(sourceId);
                    drawable.setBounds(0, 0, 50, 50);
                    return drawable;
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        initFaces();
        faceAdapter = new FaceAdapter(faces, inflater);
        mGridViewFace.setAdapter(faceAdapter);
        /**
         *pop
         */
        popupWindow = new PopupWindow(this);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        View view = inflater.inflate(R.layout.popupwindow, null);
        popupWindow.setContentView(view);
        /**
         * 设置聊天的listview
         */
        messageAdapter = new MessageAdapter(messages, inflater, mImageGetter);
        mListViewChat.setAdapter(messageAdapter);

        /**
         * 点击表情时，将富文本添加到edittext中
         */
        mGridViewFace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Spanned spanned = Html.fromHtml("<img src='" + faces.get(position).getName() + "'/>", mImageGetter, null);
                mEditTextMsg.getText().insert(mEditTextMsg.getSelectionStart(), spanned);
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_send:
                if (mEditTextMsg.getText().toString().equals("")) {
                } else {
                    Message messageSend = new Message();
                    messageSend.setType(MessageAdapter.TYPE_SEND_TEXT);
                    time = grtFormatTime();
                    messageSend.setTime(time);
                    messageSend.setMassageContent(filterHtml(Html.toHtml(mEditTextMsg.getText())));
                    messages.add(messageSend);
                    messageAdapter.notifyDataSetChanged();
                    mEditTextMsg.setText("");
                }
                break;
            case R.id.button_receive:
                if (mEditTextMsg.getText().toString().equals("")) {
                }else {
                    Message messageReceive = new Message();
                    messageReceive.setType(MessageAdapter.TYPE_RECEIVE_TEXT);
                    time = grtFormatTime();
                    messageReceive.setTime(time);
                    messageReceive.setMassageContent(filterHtml(Html.toHtml(mEditTextMsg.getText())));
                    messages.add(messageReceive);
                    messageAdapter.notifyDataSetChanged();
                    mEditTextMsg.setText("");
                }
                break;
            case R.id.imagebutton_face:
                hideSoftInputView();//隐藏软键盘
                if (mGridViewFace.getVisibility() == View.GONE) {
                    mGridViewFace.setVisibility(View.VISIBLE);
                } else {
                    mGridViewFace.setVisibility(View.GONE);
                }
                break;
            case R.id.edittext_msg:
                mGridViewFace.setVisibility(View.GONE);
                break;
            case R.id.imagebutton_calender:
                if(popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }else {
                    popupWindow.showAsDropDown(mRelativeLayoutHeader);
                }
                break;
            default:
                break;
        }
    }

    private String grtFormatTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM—dd HH:mm");
        String time = simpleDateFormat.format(calendar.getTime());
        return time;
    }

    public void hideSoftInputView() {
        InputMethodManager manager = ((InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE));
        if (getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (getCurrentFocus() != null)
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
    /*
    用正则表达式过滤掉无关的文本
     */
    public String filterHtml(String str) {
        str = str.replaceAll("<(?!br|img)[^>]+>", "").trim();
        return str;
    }

    private void initFaces() {
        faces = new ArrayList<Face>();
        faces.add(new Face(R.mipmap.face1, "face1"));
        faces.add(new Face(R.mipmap.face2, "face2"));
        faces.add(new Face(R.mipmap.face3, "face3"));
        faces.add(new Face(R.mipmap.face4, "face4"));
        faces.add(new Face(R.mipmap.face5, "face5"));
        faces.add(new Face(R.mipmap.face6, "face6"));
        faces.add(new Face(R.mipmap.face7, "face7"));
        faces.add(new Face(R.mipmap.face8, "face8"));
        faces.add(new Face(R.mipmap.face9, "face9"));
        faces.add(new Face(R.mipmap.face10, "face10"));
        faces.add(new Face(R.mipmap.face11, "face11"));
        faces.add(new Face(R.mipmap.face12, "face12"));
        faces.add(new Face(R.mipmap.face13, "face13"));
        faces.add(new Face(R.mipmap.face14, "face14"));
        faces.add(new Face(R.mipmap.face15, "face15"));
        faces.add(new Face(R.mipmap.face16, "face16"));
        faces.add(new Face(R.mipmap.face17, "face17"));
        faces.add(new Face(R.mipmap.face6, "face6"));
    }
}
