package com.example.eaglevision;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;

import java.util.Map;

import static com.example.eaglevision.MainActivity.P;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Confirm_Screen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Confirm_Screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Confirm_Screen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Button ConfirmBtn;
    public DatabaseReference tempReference;
    public DirectoryAdapter directoryrefer;
    public Person newPerson;
    public String ID;

    public TextView pname;
    public TextView cnic;
    public TextView pDescription;
    public TextView pskinc;
    public TextView shairc;
    public TextView pcrime;
    public TextView Location;


    public String personID;
    //Person Data

    //---------------------------------------

    public Map<String,Object> tempdata;
    private OnFragmentInteractionListener mListener;

    public Confirm_Screen() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Confirm_Screen.
     */
    // TODO: Rename and change types and number of parameters
    public static Confirm_Screen newInstance(String param1, String param2) {
        Confirm_Screen fragment = new Confirm_Screen();
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

        tempReference= ((MainActivity)this.getActivity()).getDatabaseReference();
        directoryrefer=(((MainActivity) this.getActivity()).directoryAdapter);


       // P=directoryrefer.directory.get(directoryrefer.getItemCount()-1);
       /* for(int i=0;i<directoryrefer.getItemCount();i++) {
            tempdata = directoryrefer.directory.get(i).toMap();
            UserReference= tempReference.child("Suspects");
            user=UserReference.child(directoryrefer.directory.get(i).getID());
            UserReference.push().setValue(tempdata);

             tempReference.child("Suspects").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().child("leftSpace").setValue(newValue);
                dialog.dismiss();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("User", databaseError.getMessage());
            }
        });
        }*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view=inflater.inflate(R.layout.fragment_confirm__screen, container, false);

        ConfirmBtn=(Button)view.findViewById(R.id.confirmPerson);
        //Setting Values


        pname=(TextView)view.findViewById(R.id.pname);
        pname.setText(P.getName());
        shairc=(TextView)view.findViewById(R.id.shairc);
        shairc.setText(P.getHairColor());
        cnic=(TextView)view.findViewById(R.id.CNIC);
        cnic.setText(P.getCNIC());
        pskinc=(TextView)view.findViewById(R.id.pskinc);
        pskinc.setText(P.getSkinColor());
        pDescription=(TextView)view.findViewById(R.id.pDescription);
        pDescription.setText(P.getDescription());
        pcrime=(TextView)view.findViewById(R.id.pcrime);
        pcrime.setText(P.getSuspectedCrime());
        Location=(TextView)view.findViewById(R.id.Location);
        Location.setText(P.getLastKnownLocation());


        personID="";

        ConfirmBtn.setOnClickListener(
                new View.OnClickListener() {


                    @Override
                    public void onClick(View view)
                    {
                           personID=Double.toString(Math.random());
                           tempReference.child("suspects").child(personID).setValue(P);
                    }
                });
        return view;
    }


    public void SetPerson(Person person)
    {
        P=person;
    }

    // TODO: Rename method, update argument and hook method into UI event
  /*  public void onButtonPressed(Uri uri) {
        if (mListener != null) {

            mListener.onFragmentInteraction(uri);


        }
    }*/

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
     //   if (context instanceof OnFragmentInteractionListener) {
     //       mListener = (OnFragmentInteractionListener) context;
     //   } else {
     //       throw new RuntimeException(context.toString()
      //             + " must implement OnFragmentInteractionListener");
      //  }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Person person);
    }
}
