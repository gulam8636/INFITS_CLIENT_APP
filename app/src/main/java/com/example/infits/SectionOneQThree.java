package com.example.infits;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.infits.customDialog.SectionPref;

import java.util.LinkedHashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SectionOneQThree#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SectionOneQThree extends Fragment {
    //SEC
    ImageButton imgBack;
    Button nextbtn;
    TextView backbtn, agetv;
    EditText eTextAge;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SectionOneQThree() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SectionOneQThree.
     */
    // TODO: Rename and change types and number of parameters
    public static SectionOneQThree newInstance(String param1, String param2) {
        SectionOneQThree fragment = new SectionOneQThree();
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
        View view = inflater.inflate(R.layout.fragment_section_one_q_three, container, false);

        imgBack = view.findViewById(R.id.imgback);
        nextbtn = view.findViewById(R.id.nextbtn);
        backbtn = view.findViewById(R.id.backbtn);
        eTextAge = view.findViewById(R.id.eTextAge);

        agetv = view.findViewById(R.id.textView80);

        TextView gotomain = view.findViewById(R.id.gotomainsection);
        gotomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_sectionOneQThree_to_consultationFragment);

            }
        });

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("STEP1Q3", Context.MODE_PRIVATE);
        String storedvalue = sharedPreferences.getString("age", "");
        if(!storedvalue.isEmpty()) {
            eTextAge.setText(storedvalue);
            DataSectionOne.age = storedvalue;
        }

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_age = eTextAge.getText().toString();
                //Toast.makeText(getContext(),user_age, Toast.LENGTH_SHORT).show();

                DataSectionOne.age = user_age;
                DataSectionOne.s1q3 = agetv.getText().toString();
                if(user_age.equals("")|| user_age.equals(" "))
                    Toast.makeText(getContext(),"Add your age",Toast.LENGTH_SHORT).show();
                else{
                    ConsultationFragment.psection1+=1;
                    SharedPreferences sharedPreferences2 = requireContext().getSharedPreferences("SEC1PROG", Context.MODE_PRIVATE);
                    int preval =       sharedPreferences2.getInt("progress",0);
                    SectionPref.saveform("age",eTextAge.getText().toString(),2,preval,3,"STEP1Q3",requireContext());
//                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("STEP1Q3", Context.MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("age", eTextAge.getText().toString());
//                    editor.apply();
//
//                    String sharedage = sharedPreferences.getString("age", "");
////                    Toast.makeText(requireContext(), String.valueOf(ConsultationFragment.psection1)+" "+sharedage, Toast.LENGTH_SHORT).show();
//                    if (!(sharedage.isEmpty()) && preval==2){
//                        SharedPreferences sharedPreferences1 = requireContext().getSharedPreferences("SEC1PROG", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
//                        editor1.putInt("progress", 3);
//                        editor1.apply();
//                    }
                Navigation.findNavController(v).navigate(R.id.action_sectionOneQThree_to_sectionOneQFour);
            }
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ConsultationFragment.psection1>0)
                    ConsultationFragment.psection1-=1;
                requireActivity().onBackPressed();
            }
        });

        imgBack.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }
}