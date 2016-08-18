package com.example.tushar.expensemanager.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.model.Transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddTransactionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddTransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddTransactionFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    //private enum type {expense,saving};
    private boolean isExpense=true;
    private OnFragmentInteractionListener mListener;

    public AddTransactionFragment(OnFragmentInteractionListener listener) {
        mListener=listener;
        // Required empty public constructor
    }

    public AddTransactionFragment(){

    }
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AddTransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTransactionFragment newInstance(String param1,OnFragmentInteractionListener listener) {
        AddTransactionFragment fragment = new AddTransactionFragment(listener);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_add_transaction, container, false);
        ImageButton button=(ImageButton)view.findViewById(R.id.date_setter_button);
        ImageButton timeSetterButton=(ImageButton)view.findViewById(R.id.time_setter_button);
        RadioButton expenseButton=(RadioButton)view.findViewById(R.id.radio_expense);
        RadioButton savingsButton=(RadioButton)view.findViewById(R.id.radio_saving);
        Button submitButton=(Button)view.findViewById(R.id.submit_button);
        submitButton.setOnClickListener(this);
        savingsButton.setOnClickListener(this);
        expenseButton.setOnClickListener(this);
        timeSetterButton.setOnClickListener(this);
        button.setOnClickListener(this);
        return view; }

    // TODO: Rename method, update argument and hook method into UI event
    public void onTransactionSubmit() {
        if (mListener != null) {
            mListener.loadTransactionListFragment();
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.date_setter_button: showDatePicker();break;
            case R.id.time_setter_button: showTimePicker();break;
            case R.id.radio_expense: onRadioButtonClicked(v);break;
            case R.id.radio_saving: onRadioButtonClicked(v);break;
            case R.id.submit_button: submitTransaction();break;
        }
    }

    private void submitTransaction() {
        Transaction item = DatabaseManager.getInstance().newTransaction();
        item.setAmount(String.valueOf(((EditText)getView().findViewById(R.id.amount_textbox)).getText()));
        item.setNotes(((EditText)getView().findViewById(R.id.note_textbox)).getText().toString());
        item.setT_type(isExpense?"expense":"savings");
        item.setRepeat_mode(((Spinner)getView().findViewById(R.id.repeat_mode_spinner)).getSelectedItem().toString());
        item.setCategory(((Spinner)getView().findViewById(R.id.category_selector)).getSelectedItem().toString());
        item.setTag(((Spinner)getView().findViewById(R.id.tags_selector)).getSelectedItem().toString());
        item.setTimestamp(getTimeStamp(((EditText)getView().findViewById(R.id.setdate_textview)).getText().toString(),((EditText)getView().findViewById(R.id.settime_textview)).getText().toString()));
        item.setTitle(((EditText)getView().findViewById(R.id.transaction_title)).getText().toString());
        DatabaseManager.getInstance().updateTransaction(item);
        onTransactionSubmit();
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
        void loadTransactionListFragment();
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_expense:
                if (checked)
                    isExpense=true;
                    // Pirates are the best
                    break;
            case R.id.radio_saving:
                if (checked)
                    isExpense=false;
                    // Ninjas rule
                    break;
        }
    }

    public long getTimeStamp(String date,String time)
    {
        String dateTime=date+" "+time;
        DateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date dated=(Date)df.parse(dateTime);
            return dated.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 10000;
    }

    public void showDatePicker(){
        final EditText dateEditText=(EditText)getView().findViewById(R.id.setdate_textview);
        Calendar date1=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate=df.format(date1.getTime());
        String[] arr=formattedDate.split("/");
        int day=Integer.parseInt(arr[0]);
        int year=Integer.parseInt(arr[2]);
        int month=Integer.parseInt(arr[1]);
        DatePickerDialog datePickerDialog=new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
              dateEditText.setText(String.valueOf(dayOfMonth)+"/"+String.valueOf(monthOfYear+1)+"/"+String.valueOf(year));
            }
        },year,month,day);
        datePickerDialog.show();
    }

    public void showTimePicker(){
        final EditText timeEditText=(EditText)getView().findViewById(R.id.settime_textview);
        Calendar date1=Calendar.getInstance();
        SimpleDateFormat df=new SimpleDateFormat("HH:mm");
        String formattedDate=df.format(date1.getTime());
        String[] arr=formattedDate.split(":");
        int hour=Integer.parseInt(arr[0]);
        int min=Integer.parseInt(arr[1]);
        TimePickerDialog timePickerDialog=new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeEditText.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute) );
            }
        },hour,min,false);
        timePickerDialog.show();
    }
}
