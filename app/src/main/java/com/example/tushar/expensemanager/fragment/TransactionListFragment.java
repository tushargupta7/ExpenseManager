package com.example.tushar.expensemanager.fragment;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.adapter.TransactionsListAdapter;
import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.model.Transaction;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionListFragment extends Fragment implements TransactionsListAdapter.AdapterInterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private ImageButton nextButton,prevButton;
    private TextView dateBox;
    private String formattedDate;
    private RecyclerView mRecyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;


    private OnFragmentInteractionListener mListener;

    public TransactionListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment TransactionListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionListFragment newInstance(OnFragmentInteractionListener param1) {
        TransactionListFragment fragment = new TransactionListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mListener = (OnFragmentInteractionListener) getArguments().getSerializable(ARG_PARAM1);
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
        TransactionsListAdapter tr=new TransactionsListAdapter(transactionList,getActivity(),this);
        mRecyclerView.setAdapter(tr);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Transaction item) {
        if (mListener != null) {
            mListener.showTransactionDetailFragment(item);
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

    @Override
    public void itemCLicked(Transaction item) {
        onButtonPressed(item);
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
    public interface OnFragmentInteractionListener extends Serializable {
        // TODO: Update argument type and name
        void showTransactionDetailFragment(Transaction item);
    }
}
