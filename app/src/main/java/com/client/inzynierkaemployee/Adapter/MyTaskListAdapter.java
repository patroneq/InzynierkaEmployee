package com.client.inzynierkaemployee.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.R;
import com.client.inzynierkaemployee.Utils.Utils;

import java.util.List;
import java.util.Map;

public class MyTaskListAdapter extends RecyclerView.Adapter<MyTaskListAdapter.ViewHolder>  {
    protected List<TaskModel> mDataset;
    protected String mUserModelSerialized;
    protected Context mContext;
    protected Class mActivity;
    protected Map<String, Boolean> mActivityFlags;

    public MyTaskListAdapter(List<TaskModel> myDataset) {
        this(myDataset, null, null, null);
    }

    public MyTaskListAdapter(List<TaskModel> myDataset, String userModelSerialized, Context currentActivityContext, Class activityClass) {
        mDataset = myDataset;
        mUserModelSerialized = userModelSerialized;
        mContext = currentActivityContext;
        mActivity = activityClass;
    }

    public void setDataset( List<TaskModel> myDataset) {
        this.mDataset = myDataset;
    }

    public void setActivityForListener(Class activityClass) {
        this.mActivity = activityClass;
    }

    public void setUser(String userSerialized) {
        this.mUserModelSerialized = userSerialized;
    }

    public void setContext(Context context)
    {
        this.mContext = context;
    }

    public void setActivityFlags(Map<String, Boolean> activityFlags) {
        this.mActivityFlags = activityFlags;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTaskName, mTaskCreationDate;

        ViewHolder(View view) {
            super(view);
            mTaskName = (TextView) view.findViewById(R.id.my_task_item_name);
            mTaskCreationDate = (TextView) view.findViewById(R.id.my_task_item_creation_date_text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition(); // gets item position
            if (position == RecyclerView.NO_POSITION) { return; }

            if (mActivity != null && mContext != null) {
                Intent intent = new Intent(mContext, mActivity);
                intent.putExtra("user_profile", mUserModelSerialized);
                intent.putExtra("MYTASK", Utils.getGsonInstance().toJson(mDataset.get(position)));
                if ( mActivityFlags != null && !mActivityFlags.isEmpty()) {
                    for (String key : mActivityFlags.keySet()) {
                        intent.putExtra(key, mActivityFlags.get(key));
                    }
                }
                mContext.startActivity(intent);
            }
        }
    }

    @Override
    public MyTaskListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_task_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Spannable word = new SpannableString("***");

        word.setSpan(new ForegroundColorSpan(Color.RED), 0, word.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.mTaskName.setText(mDataset.get(position).title);
        if ((mDataset.get(position).problem).equals("YES")) {
            holder.mTaskName.append(word);
        }
        holder.mTaskCreationDate.setText(mDataset.get(position).getFormattedDate());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}