package com.client.inzynierkaemployee.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.client.inzynierkaemployee.R;
import com.client.inzynierkaemployee.Adapter.TaskListAdapter;
import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.TaskActivity;
import com.client.inzynierkaemployee.Utils.Communication;
import com.client.inzynierkaemployee.Utils.DividerItemDecoration;
import com.client.inzynierkaemployee.Utils.Utils;
import com.google.gson.reflect.TypeToken;

public class MainFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EmployeeModel mEmployeeModel;

    private RecyclerView mRecycler;
    private TaskListAdapter mAdapter;

    private List<TaskModel> myTaskModelList = new ArrayList<TaskModel>();


    public MainFragment() {
        // Required empty public constructor
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEmployeeModel = Utils.getGsonInstance().fromJson(getActivity().getIntent().getStringExtra("user_profile"), EmployeeModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = (RecyclerView) view.findViewById(R.id.task_recycler);
        createAdapters();
        new GetAllTasksForEmployeeTask().execute((Void)null);
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void createAdapters() {
        if ( mAdapter == null) {
            List<TaskModel> tasks = new ArrayList<>();
            mAdapter = new TaskListAdapter(tasks);
            Map<String,Boolean> flags = new HashMap<>();
            flags.put(TaskActivity.IS_TASK_BTN_VISIBLE, true);
            mAdapter.setActivityFlags(flags);
        }

        DividerItemDecoration recyclerDecoration = new DividerItemDecoration(mRecycler.getContext(),R.drawable.list_decorator);
        mRecycler.addItemDecoration(recyclerDecoration);

        mRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mAdapter.setContext(this.getContext());
        mAdapter.setActivityForListener(TaskActivity.class);
        mRecycler.setAdapter(mAdapter);
    }

    public class GetAllTasksForEmployeeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (getActivity() != null) {
                EmployeeModel employeeModel = Utils.getGsonInstance().fromJson(getActivity().getIntent().getStringExtra("user_profile"), EmployeeModel.class);
                String response = new Communication().Receive("/employee/showcreatedtasks/" + employeeModel.id, "", "GET");
                Log.v("======GSON", response);
                myTaskModelList = Utils.getGsonInstance().fromJson(response, new TypeToken<ArrayList<TaskModel>>() {
                }.getType());
                return true;
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            mAdapter.setDataset(myTaskModelList);
            mAdapter.notifyDataSetChanged();
            if (getActivity() != null)
                mAdapter.setUser(getActivity().getIntent().getStringExtra("user_profile"));
        }
    }
}