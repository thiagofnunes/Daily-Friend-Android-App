package com.thiagonunes.dailyfriend.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.thiagonunes.dailyfriend.R;
import com.thiagonunes.dailyfriend.utils.RecyclerViewInterface;

import java.lang.ref.WeakReference;
import java.util.List;

public class FeelingListAdapter extends RecyclerView.Adapter<FeelingListAdapter.FeelingViewHolder> {

    private final LayoutInflater mInflater;
    private List<String> mFeelings;
    private WeakReference<Context> mContext;
    private int mType;
    private RecyclerViewInterface mInterface;

    public FeelingListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
        mContext = new WeakReference<>(context);
    }

    @Override
    public FeelingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_feelings_item, parent, false);
        return new FeelingViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(FeelingViewHolder holder, int position) {
        if (mFeelings != null) {
            String current = mFeelings.get(position);
            holder.itemTitle.setText(current);
        }
    }

    public void setFeelingsList(List<String> feelingsList) {
        mFeelings = feelingsList;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        mType = type;
    }

    public void setInterface(RecyclerViewInterface anInterface) {
        mInterface = anInterface;
    }

    @Override
    public int getItemCount() {
        if (mFeelings != null)
            return mFeelings.size();
        else return 0;
    }

    public String getFeelingAtPosition(int position) {
        return mFeelings.get(position);
    }

    class FeelingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private final TextView itemTitle;
        private final CardView parent;

        private FeelingViewHolder(View itemView) {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.feeling);
            parent = itemView.findViewById(R.id.cardview);
            if (mType == 1) {
                parent.setCardBackgroundColor(mContext.get().getResources().getColor(R.color.clicked_green));
            }
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mType == 0) {
                mInterface.addItemToSelectedFeelings(mFeelings.get(getAdapterPosition()), getAdapterPosition());

            } else {
                mInterface.removeItemFromSelectedFeelings(mFeelings.get(getAdapterPosition()));
            }
        }

        @Override
        public boolean onLongClick(View v) {

            if (mType == 0) {
                mInterface.addItemToSelectedFeelings(mFeelings.get(getAdapterPosition()), getAdapterPosition());

            } else {
                mInterface.removeItemFromSelectedFeelings(mFeelings.get(getAdapterPosition()));
            }

            return true;
        }
    }
}