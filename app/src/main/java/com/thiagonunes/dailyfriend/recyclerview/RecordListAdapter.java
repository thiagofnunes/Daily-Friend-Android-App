package com.thiagonunes.dailyfriend.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thiagonunes.dailyfriend.R;
import com.thiagonunes.dailyfriend.model.Record;

import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Record> mRecordList; // Cached copy of words

    public RecordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new RecordViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecordViewHolder holder, int position) {
        if (mRecordList != null) {
            Record current = mRecordList.get(position);
            holder.recordItemTitle.setText(current.date);
            holder.id = current.id;
        } else {
            holder.recordItemTitle.setText("No record");
        }
    }

    public void setRecordList(List<Record> recordList) {
        mRecordList = recordList;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mRecordList != null)
            return mRecordList.size();
        else return 0;
    }

    public Record getRecordAtPosition(int position) {
        return mRecordList.get(position);
    }

    class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView recordItemTitle;
        private int id;

        private RecordViewHolder(View itemView) {
            super(itemView);
            recordItemTitle = itemView.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View v) {
            if (mRecordList != null && id>=0) {
                //TODO: SHOW DATA
            }
        }
    }
}