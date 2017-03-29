package com.example.user.searchwifiservice.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.searchwifiservice.MapsActivity;
import com.example.user.searchwifiservice.R;
import com.example.user.searchwifiservice.models.Row;

import java.util.ArrayList;

/**
 * Created by USER on 2017-03-27.
 */

public class ListviewAdapter extends BaseAdapter {

    private ArrayList<Row> mData;

    public ListviewAdapter(ArrayList<Row> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Row getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        ViewHolder viewHolder;
        // 레이아웃
        if (convertView == null) {
            viewHolder = new ViewHolder();

            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_layout, parent, false);

            viewHolder.placeNameTextview = (TextView) convertView.findViewById(R.id.place_name_textview);
            viewHolder.companyTextview = (TextView) convertView.findViewById(R.id.company_textview);
            viewHolder.viewLocationImageview = (ImageView) convertView.findViewById(R.id.view_location_imageview);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        // getItem(position).getPLACE_NAME()

        viewHolder.placeNameTextview.setText(mData.get(position).getPLACE_NAME());
        viewHolder.companyTextview.setText(mData.get(position).getINSTL_DIV());
        viewHolder.viewLocationImageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(parent.getContext(), "지도 띄울것이다", Toast.LENGTH_SHORT).show();


                // TODO 지도 띄우면서 해당 아이템의 정보 실어 보내기
                /**
                 * 지도 띄우기
                 * {@link com.example.user.searchwifiservice.MapsActivity}
                 */
                Intent intent = new Intent(parent.getContext(), MapsActivity.class);
                parent.getContext().startActivity(intent);

            }
        });


        return convertView;
    }


    private static class ViewHolder {
        TextView placeNameTextview;
        TextView companyTextview;
        ImageView viewLocationImageview;
    }
}

