package com.example.tushar.expensemanager.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.adapter.TransactionsListAdapter;
import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.model.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionListFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private ImageButton nextButton,prevButton;
    private TextView dateBox;
    private String formattedDate;
    private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionListFragment newInstance(String param1, String param2) {
        TransactionListFragment fragment = new TransactionListFragment();
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
        View view=inflater.inflate(R.layout.fragment_transaction_list_fragments,container,false);
        mRecyclerView=(RecyclerView)view.findViewById(R.id.transaction_list_recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        nextButton=(ImageButton)view.findViewById(R.id.next_button);
        prevButton=(ImageButton)view.findViewById(R.id.prev_button);
        dateBox=(TextView)view.findViewById(R.id.date_box);
        handleDateChange();
        return view;
    }

    private void handleDateChange() {

        final Calendar c = Calendar.getInstance();

       // System.out.println("Current time => " + c.getTime());

        final DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        formattedDate = df.format(c.getTime());
           // Date date=df.parse(formattedDate);
        loadTransations(c);

        dateBox.setText(formattedDate);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, 1);
                formattedDate = df.format(c.getTime());
                loadTransations(c);
                Log.v("NEXT DATE : ", formattedDate);
                dateBox.setText(formattedDate);
            }
        });

        prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c.add(Calendar.DATE, -1);
                formattedDate = df.format(c.getTime());
                loadTransations(c);
                Log.v("PREVIOUS DATE : ", formattedDate);
                dateBox.setText(formattedDate);
            }
        });
    }

    private void loadTransations(Calendar c) {
        Calendar prevDay= (Calendar) c.clone();
        prevDay.add(Calendar.DATE,-1);
        List<Transaction> transactionList= DatabaseManager.getInstance().getDatedTransaction(prevDay.getTimeInMillis(),c.getTimeInMillis());
        TransactionsListAdapter tr=new TransactionsListAdapter(transactionList,getActivity());
        mRecyclerView.setAdapter(tr);
    }

    // TODO: Rename method, update argument and hook method into UI event
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
        void onFragmentInteraction(Uri uri);
    }
}
