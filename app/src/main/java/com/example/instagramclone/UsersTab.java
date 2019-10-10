package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class UsersTab extends Fragment {

    private ListView mListView;
    private List mList;
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
        mList = new ArrayList();
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

}
