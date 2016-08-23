package com.example.tushar.expensemanager.fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.model.Transaction;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import Utils.Utils;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TransactionDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TransactionDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private Transaction mTransaction;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public TransactionDetailsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TransactionDetailsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TransactionDetailsFragment newInstance(Transaction param1, OnFragmentInteractionListener param2) {
        TransactionDetailsFragment fragment = new TransactionDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        args.putSerializable(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTransaction = (Transaction) getArguments().getSerializable(ARG_PARAM1);
            mListener = (OnFragmentInteractionListener) getArguments().getSerializable(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.transact_edit_menu,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_item: launchAddFragment(mTransaction);break;
            case R.id.delete_item : deleteTransactionItem(mTransaction);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void launchAddFragment(Transaction mTransaction) {
        if(mListener!=null){
            mListener.onEditing(mTransaction);
        }
    }

    private void deleteTransactionItem(Transaction mTransaction) {
        DatabaseManager.getInstance().deleteTransaction(mTransaction.getId());
        onTransactionDeleted();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_transaction_details,container,false);
        initializeView(view);
        return view;
    }

    private void setTransactionInformation() {
    //   initializeView();

    }

    private void initializeView(View view) {
       // View view=getView();
        ImageButton button=(ImageButton)view.findViewById(R.id.date_setter_button);
        ImageButton timeSetterButton=(ImageButton)view.findViewById(R.id.time_setter_button);
        RadioButton expenseButton=(RadioButton)view.findViewById(R.id.radio_expense);
        RadioButton savingsButton=(RadioButton)view.findViewById(R.id.radio_saving);
        Button submitButton=(Button)view.findViewById(R.id.submit_button);
        EditText amount=(EditText)view.findViewById(R.id.amount_textbox);
        EditText title=(EditText)view.findViewById(R.id.transaction_title);
        EditText date=(EditText)view.findViewById(R.id.setdate_textview);
        EditText time=(EditText)view.findViewById(R.id.settime_textview);
        Spinner categorySpinner=(Spinner)view.findViewById(R.id.category_selector);
        Spinner tagSpinner=(Spinner)view.findViewById(R.id.tags_selector);
        EditText notesText=(EditText)view.findViewById(R.id.note_textbox);
        Spinner repeatNode=(Spinner)view.findViewById(R.id.repeat_mode_spinner);
        if(mTransaction.getT_type().equalsIgnoreCase("expense")){
            expenseButton.setChecked(true);
        }
        else savingsButton.setChecked(true);
        TextView selectedCategory=(TextView)view.findViewById(R.id.selected_category_text);
        TextView selectedTag=(TextView)view.findViewById(R.id.selected_tag_text);
        TextView selectedRepeat=(TextView)view.findViewById(R.id.selected_repeat_text);
        selectedCategory.setText(mTransaction.getCategory());
        selectedTag.setText(mTransaction.getTag());
        selectedRepeat.setText(getResources().getStringArray(R.array.repeat_modes)[mTransaction.getRepeat_mode()]);
        amount.setText(mTransaction.getAmount());
        title.setText(mTransaction.getTitle());
        date.setText(Utils.getFormattedDate(mTransaction.getTimestamp(),"date"));
        time.setText(Utils.getFormattedDate(mTransaction.getTimestamp(),"time"));
        notesText.setText(mTransaction.getNotes());
    }

  /*  private String getFormattedDate(long timestamp,String option) {
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date netDate = (new Date(timestamp));
        String dateTime= df.format(netDate);
        if(option.equalsIgnoreCase("date")){
            return dateTime.split(" ")[0];
        }
        else return dateTime.split(" ")[1];
    }*/

    // TODO: Rename method, update argument and hook method into UI event
    public void onTransactionDeleted() {
        if (mListener != null) {
            mListener.onDeleted();
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
    public interface OnFragmentInteractionListener extends Serializable {
        // TODO: Update argument type and name
        void onDeleted();
        void onEditing(Transaction item);
    }
}
