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

import com.client.inzynierkaemployee.Adapter.WaitingTaskListAdapter;
import com.client.inzynierkaemployee.Model.EmployeeModel;
import com.client.inzynierkaemployee.Model.TaskModel;
import com.client.inzynierkaemployee.R;
import com.client.inzynierkaemployee.Utils.Communication;
import com.client.inzynierkaemployee.Utils.DividerItemDecoration;
import com.client.inzynierkaemployee.Utils.Utils;
import com.client.inzynierkaemployee.WaitingTaskActivity;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class FinishedTasksFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    private EmployeeModel mEmployeeModel;

    private RecyclerView mRecycler;
    private WaitingTaskListAdapter mAdapter;

    private List<TaskModel> myTaskModelList = new ArrayList<TaskModel>();


    public FinishedTasksFragment() {
        // Required empty public constructor
    }

    public static FinishedTasksFragment newInstance(String param1, String param2) {
        FinishedTasksFragment fragment = new FinishedTasksFragment();
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
        return inflater.inflate(R.layout.fragment_finished_tasks, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecycler = (RecyclerView) view.findViewById(R.id.task_recycler);
        createAdapters();
        new GetAllPendingTasksForEmployeeTask().execute((Void)null);
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
            mAdapter = new WaitingTaskListAdapter(tasks);
        }

        DividerItemDecoration recyclerDecoration = new DividerItemDecoration(mRecycler.getContext(),R.drawable.list_decorator);
        mRecycler.addItemDecoration(recyclerDecoration);

        mRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));

        mAdapter.setContext(this.getContext());
        mAdapter.setActivityForListener(WaitingTaskActivity.class);
        mRecycler.setAdapter(mAdapter);
    }

    public class GetAllPendingTasksForEmployeeTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... voids) {
            if (getActivity() != null) {
                EmployeeModel employeeModel = Utils.getGsonInstance().fromJson(getActivity().getIntent().getStringExtra("user_profile"), EmployeeModel.class);
                String response = new Communication().Receive("/employee/showmyfinishedtasks/" + employeeModel.id, "", "GET");
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