package com.example.instagramclone;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileTab extends Fragment {

    private TextInputEditText mProfileNameEdtTxt, mBioEdtTxt, mProfessionEdtTxt, mHobbiesEdtTxt, mFavoriteSportEdtTxt;
    private Button mUpdateBtn;

    public ProfileTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);

        mProfileNameEdtTxt = view.findViewById(R.id.profileNameMaterialEdtTxt);
        mBioEdtTxt = view.findViewById(R.id.bioMaterialEdtTxt);
        mProfessionEdtTxt = view.findViewById(R.id.professionMaterialEdtTxt);
        mHobbiesEdtTxt = view.findViewById(R.id.hobbiesMaterialEdtTxt);
        mFavoriteSportEdtTxt = view.findViewById(R.id.favoriteSportMaterialEdtTxt);
        mUpdateBtn = view.findViewById(R.id.updateMateriaBtn);

        final ParseUser parseUser = ParseUser.getCurrentUser();
        fillInputs(parseUser);

        mUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parseUser.put("profileName", mProfileNameEdtTxt.getText().toString());
                parseUser.put("profileBio", mBioEdtTxt.getText().toString());
                parseUser.put("profileProfession", mProfessionEdtTxt.getText().toString());
                parseUser.put("profileHobbies", mHobbiesEdtTxt.getText().toString());
                parseUser.put("profileFavoriteSport", mFavoriteSportEdtTxt.getText().toString());

                parseUser.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if(e == null) {
                            Toast.makeText(getContext(), "Info updated!", Toast.LENGTH_SHORT).show();
                        } else {
                            e.printStackTrace();
                            Toast.makeText(getContext(), e.getMessage() + "", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        return view;
    }

    private void fillInputs(ParseUser parseUser) {

        if(parseUser.get("profileName") != null) {
            mProfileNameEdtTxt.setText(parseUser.get("profileName").toString());
        }
        if(parseUser.get("profileBio") != null) {
            mBioEdtTxt.setText(parseUser.get("profileBio").toString());
        }
        if(parseUser.get("profileProfession") != null) {
            mProfessionEdtTxt.setText(parseUser.get("profileProfession").toString());
        }
        if(parseUser.get("profileHobbies") != null) {
            mHobbiesEdtTxt.setText(parseUser.get("profileHobbies").toString());
        }
        if(parseUser.get("profileFavoriteSport") != null) {
            mFavoriteSportEdtTxt.setText(parseUser.get("profileFavoriteSport").toString());
        }

    }

}
