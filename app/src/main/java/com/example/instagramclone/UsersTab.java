package com.example.instagramclone;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private ListView mListView;
    private List<String> mList;
    private ArrayAdapter mArrayAdapter;
    private TextView mProgressTxt;
    private ProgressBar mProgressBar;

    public UsersTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_users_tab, container, false);

        mListView = view.findViewById(R.id.listView);
        mListView.setOnItemClickListener(UsersTab.this);
        mListView.setOnItemLongClickListener(UsersTab.this);
        mList = new ArrayList<String>();
        mArrayAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, mList);
        mProgressBar = view.findViewById(R.id.progressBar);
        mProgressTxt = view.findViewById(R.id.progressTextView);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        parseQuery.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e == null && objects.size() > 0) {
                    for(ParseUser parseUser : objects) {
                        mList.add(parseUser.getUsername());
                    }
                    mListView.setAdapter(mArrayAdapter);
                    mListView.setVisibility(View.VISIBLE);
                    mProgressBar.setVisibility(View.GONE);
                    mProgressTxt.setVisibility(View.GONE);
                }
            }
        });

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        Intent intent = new Intent(getContext(), UsersPosts.class);
        intent.putExtra("username", mList.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {
        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("username", mList.get(position));

        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser object, ParseException e) {
                if(object != null && e == null) {

                }
            }
        });

        return true;
    }
}
