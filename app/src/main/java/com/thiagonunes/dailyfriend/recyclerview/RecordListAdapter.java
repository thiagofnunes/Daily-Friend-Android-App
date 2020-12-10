package com.thiagonunes.dailyfriend.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.thiagonunes.dailyfriend.R;
import com.thiagonunes.dailyfriend.RecordActivity;
import com.thiagonunes.dailyfriend.model.Record;
import com.thiagonunes.dailyfriend.utils.Constants;

import java.lang.ref.WeakReference;
import java.util.List;

public class RecordListAdapter extends RecyclerView.Adapter<RecordListAdapter.RecordViewHolder> {

    private final LayoutInflater mInflater;
    private List<Record> mRecordList;
    private final WeakReference<Context> mContextWeakReference;

    public RecordListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContextWeakReference = new WeakReference<>(context);
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
            holder.itemTitle.setText(current.title);
            holder.itemDate.setText(current.date);
            holder.itemHour.setText(current.hour);
        } else {
            holder.itemTitle.setText("No record");
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

    class RecordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView itemTitle;
        private final TextView itemDate;
        private final TextView itemHour;

        private RecordViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.textView);
            itemDate = itemView.findViewById(R.id.dateTextView);
            itemHour = itemView.findViewById(R.id.hourTextView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Record element = mRecordList.get(position);
            Intent intent = new Intent(mContextWeakReference.get(), RecordActivity.class);
            intent.putExtra(Constants.KEY_RECORD_ID, element.id);
            mContextWeakReference.get().startActivity(intent);
        }

        @Override
        public boolean onLongClick(View v) {
            return false;
        }
    }
}