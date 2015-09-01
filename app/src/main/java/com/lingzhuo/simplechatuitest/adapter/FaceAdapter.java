package com.lingzhuo.simplechatuitest.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.lingzhuo.simplechatuitest.R;
import com.lingzhuo.simplechatuitest.moudel.Face;

import java.util.List;

/**
 * Created by heinika on 2015/8/30.
 */
public class FaceAdapter extends BaseAdapter {
    private List<Face> faces;
    private LayoutInflater inflater;

    public FaceAdapter(List<Face> faces, LayoutInflater inflater) {
        this.faces = faces;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return faces.size();
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
        Face face =faces.get(position);
        ViewHolder vh = null;
        if(convertView==null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_face,null);
            vh.mImageViewFace = (ImageView) convertView.findViewById(R.id.imageview_face);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder) convertView.getTag();
        }
        vh.mImageViewFace.setImageResource(face.getImg());
        return convertView;
    }
    class ViewHolder{
        ImageView mImageViewFace;
    }
}
