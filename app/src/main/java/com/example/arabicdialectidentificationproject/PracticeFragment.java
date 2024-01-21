package com.example.arabicdialectidentificationproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PracticeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PracticeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String dialect;

    public PracticeFragment() {
        // Required empty public constructor
    }

    public PracticeFragment(String dialect) {
        this.dialect = dialect;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PracticeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PracticeFragment newInstance(String param1, String param2) {
        PracticeFragment fragment = new PracticeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_practice, container, false);

        CardView lesson_1 = view.findViewById(R.id.lesson_1);
        CardView lesson_2 = view.findViewById(R.id.lesson_2);
        CardView lesson_3 = view.findViewById(R.id.lesson_3);

        lesson_1.setOnClickListener(view1 -> {
            Intent intent = new Intent(view.getContext(), PracticesPage.class);
            intent.putExtra("dialect", dialect);
            intent.putExtra("lesson", 1);
            startActivity(intent);
        });

        lesson_2.setOnClickListener(view1 -> {
            Intent intent = new Intent(view.getContext(), PracticesPage.class);
            intent.putExtra("dialect", dialect);
            intent.putExtra("lesson", 2);
            startActivity(intent);
        });

        lesson_3.setOnClickListener(view1 -> {
            Intent intent = new Intent(view.getContext(), PracticesPage.class);
            intent.putExtra("dialect", dialect);
            intent.putExtra("lesson", 3);
            startActivity(intent);
        });

        return view;
    }
}