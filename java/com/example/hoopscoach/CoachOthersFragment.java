package com.example.hoopscoach;

import static com.example.hoopscoach.MainActivity.team;

import android.content.Intent;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CoachOthersFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CoachOthersFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ConstraintLayout addOptions, notExist;
    private Button toExerciseCreate, toGroupCreate;

    public CoachOthersFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CoachOthersFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CoachOthersFragment newInstance(String param1, String param2) {
        CoachOthersFragment fragment = new CoachOthersFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        addOptions = (ConstraintLayout) getActivity().findViewById(R.id.addOptionsLayout);
        notExist = (ConstraintLayout) getActivity().findViewById(R.id.notExistLayout);
        toExerciseCreate = (Button) getActivity().findViewById(R.id.btn_toExerciseCreate);
        toGroupCreate = (Button) getActivity().findViewById(R.id.btn_toCreateGroup);

        if(team == null || team.players.size() == 0){
            notExist.setVisibility(View.VISIBLE);
            addOptions.setVisibility(View.GONE);
        }else{
            notExist.setVisibility(View.GONE);
            addOptions.setVisibility(View.VISIBLE);
        }
        toExerciseCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), CreateExercisesActivity.class);
                i.putExtra("specialization", "None");
                startActivity(i);
            }
        });
        toGroupCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CreateGroupActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coach_others, container, false);
    }
}