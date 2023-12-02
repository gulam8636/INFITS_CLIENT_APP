package com.example.infits;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infits.customDialog.SectionPref;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Section5Q5#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Section5Q5 extends Fragment {

    ImageButton imgBack;
    Button nextbtn;
    TextView backbtn, textView77;
    RadioButton yes,no,dk;
    String allergies="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Section5Q5() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Section5Q5.
     */
    // TODO: Rename and change types and number of parameters
    public static Section5Q5 newInstance(String param1, String param2) {
        Section5Q5 fragment = new Section5Q5();
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
        View view = inflater.inflate(R.layout.fragment_section5_q5, container, false);

        imgBack = view.findViewById(R.id.imgback);
        nextbtn = view.findViewById(R.id.nextbtn);
        backbtn = view.findViewById(R.id.backbtn);
        yes = view.findViewById(R.id.yes);
        no = view.findViewById(R.id.no);
        dk = view.findViewById(R.id.dk);
        textView77 = view.findViewById(R.id.textView77);

        TextView gotomain = view.findViewById(R.id.gotomainsection);
        gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_section5Q5_to_consultationFragment);

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("STEP5Q5", Context.MODE_PRIVATE);
        String storedvalue = sharedPreferences.getString("allergies", "");
        if(!storedvalue.isEmpty()) {
            switch (storedvalue) {
                case "Yes":
                   Yes();
                    break;
                case "No":
                    No();
                    break;
                case "Don't know":
                   DK();
                    break;
                default:
            }
        }

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Yes();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                No();
            }
        });

        dk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DK();
            }
        });


        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(),uGender, Toast.LENGTH_SHORT).show();

                DataSectionFive.allergies = allergies;
                DataSectionFive.s5q5 = textView77.getText().toString();
                if (allergies.equals(""))
                    Toast.makeText(getContext(), "Select atleast one of the given options", Toast.LENGTH_SHORT).show();
                else {
                    ConsultationFragment.psection5 += 1;
                    SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("SEC5PROG", Context.MODE_PRIVATE);
                    int preval =       sharedPreferences2.getInt("progress5",0);
                    SectionPref.saveformsection5("allergies",allergies,4,preval,5,"STEP5Q5",requireContext());
                    Navigation.findNavController(v).navigate(R.id.action_section5Q5_to_section5Q6);
                }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ConsultationFragment.psection5>0)
                    ConsultationFragment.psection5-=1;
                requireActivity().onBackPressed();
            }
        });

        imgBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void Yes() {
        yes.setBackgroundResource(R.drawable.radiobtn_on);
        no.setBackgroundResource(R.drawable.radiobtn_off);
        dk.setBackgroundResource(R.drawable.radiobtn_off);

        yes.setTextColor(Color.WHITE);
        no.setTextColor(Color.BLACK);
        dk.setTextColor(Color.BLACK);

        allergies="Yes";
    }

    private void No() {
        no.setBackgroundResource(R.drawable.radiobtn_on);
        yes.setBackgroundResource(R.drawable.radiobtn_off);
        dk.setBackgroundResource(R.drawable.radiobtn_off);

        no.setTextColor(Color.WHITE);
        yes.setTextColor(Color.BLACK);
        dk.setTextColor(Color.BLACK);

        allergies="No";
    }

    private void DK() {
        dk.setBackgroundResource(R.drawable.radiobtn_on);
        yes.setBackgroundResource(R.drawable.radiobtn_off);
        no.setBackgroundResource(R.drawable.radiobtn_off);

        dk.setTextColor(Color.WHITE);
        yes.setTextColor(Color.BLACK);
        no.setTextColor(Color.BLACK);

        allergies="Don't know";
    }
}