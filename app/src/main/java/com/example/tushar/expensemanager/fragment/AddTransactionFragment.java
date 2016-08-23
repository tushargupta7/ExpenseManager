package com.example.tushar.expensemanager.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.db.DatabaseManager;
import com.example.tushar.expensemanager.model.Transaction;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Utils.Utils;


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
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";

    // TODO: Rename and change types of parameters
    //private enum type {expense,saving};
    private boolean isExpense = true;
    private OnFragmentInteractionListener mListener;
    private Transaction mTransactionItem;
    private Button categoryButton;
    private Button tagButton;
    private static ArrayList<String> mCategoryValues,mTagValues;
    private String mTypes;
    ImageButton timeSetterButton;
    ImageButton button;
    RadioButton expenseButton;
    RadioButton savingsButton;
    Button submitButton;
    EditText amountBox;
    EditText notesBox;
    Spinner repeatSpinner;
    Spinner categorySpinner;
    Spinner tagSpinner;
    EditText dateTextBox;
    EditText timeTextBox;
    EditText titleBox;
    TextView categoryText, tagText;

    public AddTransactionFragment() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AddTransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddTransactionFragment newInstance(OnFragmentInteractionListener listener, Transaction item) {
        AddTransactionFragment fragment = new AddTransactionFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM2, listener);
        args.putSerializable(ARG_PARAM3, item);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setRetainInstance(true);
    //  setRetainInstance(true);
        if (getArguments() != null) {
            mListener = (OnFragmentInteractionListener) getArguments().getSerializable(ARG_PARAM2);
            mTransactionItem = (Transaction) getArguments().getSerializable(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_transaction, container, false);
        ImageButton button = (ImageButton) view.findViewById(R.id.date_setter_button);
        ImageButton timeSetterButton = (ImageButton) view.findViewById(R.id.time_setter_button);
        expenseButton = (RadioButton) view.findViewById(R.id.radio_expense);
        savingsButton = (RadioButton) view.findViewById(R.id.radio_saving);
        Button submitButton = (Button) view.findViewById(R.id.submit_button);
        amountBox = (EditText) view.findViewById(R.id.amount_textbox);
        notesBox = (EditText) view.findViewById(R.id.note_textbox);
        repeatSpinner = (Spinner) view.findViewById(R.id.repeat_mode_spinner);
        categorySpinner = (Spinner) view.findViewById(R.id.category_selector);
        tagSpinner = (Spinner) view.findViewById(R.id.tags_selector);
        categoryButton = (Button) view.findViewById(R.id.category_textview);
        tagButton = (Button) view.findViewById(R.id.tag_textview);
        dateTextBox = (EditText) view.findViewById(R.id.setdate_textview);
        timeTextBox = (EditText) view.findViewById(R.id.settime_textview);
        titleBox = (EditText) view.findViewById(R.id.transaction_title);
        categoryText = (TextView) view.findViewById(R.id.catgory_selected);
        tagText = (TextView) view.findViewById(R.id.tag_selected);
        //addSpinerData();
        submitButton.setOnClickListener(this);
        categoryButton.setOnClickListener(this);
        tagButton.setOnClickListener(this);
        savingsButton.setOnClickListener(this);
        expenseButton.setOnClickListener(this);
        timeSetterButton.setOnClickListener(this);
        button.setOnClickListener(this);
        if (mTransactionItem != null) {
            fillTransactionForm();
        }


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void addSpinerData() {
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, DatabaseManager.getInstance().getAllCategories());
        categorySpinner.setAdapter(categoryAdapter);
    }

    private void fillTransactionForm() {
        if (mTransactionItem.getT_type().equalsIgnoreCase("expense")) {
            expenseButton.setChecked(true);
        } else savingsButton.setChecked(true);
        titleBox.setText(mTransactionItem.getTitle());
        amountBox.setText(mTransactionItem.getAmount());
        notesBox.setText(mTransactionItem.getNotes());
        categoryText.setText(mTransactionItem.getCategory());
        tagText.setText(mTransactionItem.getTag());
        dateTextBox.setText(Utils.getFormattedDate(mTransactionItem.getTimestamp(), "date"));
        timeTextBox.setText(Utils.getFormattedDate(mTransactionItem.getTimestamp(), "time"));
        repeatSpinner.setSelection(mTransactionItem.getRepeat_mode());

    }

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

    private String getStringFromSelectedArray(String type) {
        String selected = null;
        if (mCategoryValues == null && mTagValues== null) {
            return null;
        } else if (type.equalsIgnoreCase("category")) {
                for (String category : mCategoryValues) {
                    selected = category + " ";
                }
            }
        else {
            for (String tag:mTagValues
                 ) {
                selected=tag+" ";
            }
        }
        return selected;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.date_setter_button:
                showDatePicker();
                break;
            case R.id.time_setter_button:
                showTimePicker();
                break;
            case R.id.radio_expense:
                onRadioButtonClicked(v);
                break;
            case R.id.radio_saving:
                onRadioButtonClicked(v);
                break;
            case R.id.submit_button: {
                if (mTransactionItem == null) {
                    submitTransaction(false);
                } else {
                    submitTransaction(true);
                }
                ;
                break;
            }
            case R.id.tag_textview:
                //    submitTransaction(false);
                loadCategoryFragment("tag");
                break;
            case R.id.category_textview:
                // submitTransaction(false);
                loadCategoryFragment("category");
                break;
        }
    }

    private void loadCategoryFragment(String type) {
        mListener.loadCategoryTagList(type);
    }

    private void submitTransaction(boolean isUpdate) {
        Transaction item;
        if (!isUpdate) {
            item = DatabaseManager.getInstance().newTransaction();
        } else item = DatabaseManager.getInstance().getTransactionById(mTransactionItem.getId());
        item.setAmount(String.valueOf((amountBox).getText()));
        item.setNotes((notesBox).getText().toString());
        item.setT_type(isExpense ? "expense" : "savings");
        item.setRepeat_mode((repeatSpinner).getSelectedItemPosition());
        item.setCategory((categoryText).getText().toString());
        item.setTag((tagText).getText().toString());
        item.setTimestamp(getTimeStamp((dateTextBox).getText().toString(), (timeTextBox).getText().toString()));
        item.setTitle((titleBox).getText().toString());
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
    public interface OnFragmentInteractionListener extends Serializable {
        // TODO: Update argument type and name
        void loadTransactionListFragment();

        void loadCategoryTagList(String type);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.radio_expense:
                if (checked)
                    isExpense = true;
                // Pirates are the best
                break;
            case R.id.radio_saving:
                if (checked)
                    isExpense = false;
                // Ninjas rule
                break;
        }
    }

    public long getTimeStamp(String date, String time) {
        String dateTime = date + " " + time;
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        try {
            Date dated = (Date) df.parse(dateTime);
            return dated.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 10000;
    }

    public void showDatePicker() {
        final EditText dateEditText = (EditText) getView().findViewById(R.id.setdate_textview);
        Calendar date1 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = df.format(date1.getTime());
        String[] arr = formattedDate.split("/");
        int day = Integer.parseInt(arr[0]);
        int year = Integer.parseInt(arr[2]);
        int month = Integer.parseInt(arr[1]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                dateEditText.setText(String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear + 1) + "/" + String.valueOf(year));
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void showTimePicker() {
        final EditText timeEditText = (EditText) getView().findViewById(R.id.settime_textview);
        Calendar date1 = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String formattedDate = df.format(date1.getTime());
        String[] arr = formattedDate.split(":");
        int hour = Integer.parseInt(arr[0]);
        int min = Integer.parseInt(arr[1]);
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                timeEditText.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
            }
        }, hour, min, false);
        timePickerDialog.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // outState.putStringArrayList("saved",mTransactionItem);
    }

    public void setTypeAndSelected(String type, ArrayList<String> selected) {
        mTypes=type;
            switch (mTypes) {
                case "category":
                    mCategoryValues=selected;
                    categoryText.setText(getStringFromSelectedArray(mTypes));
                    break;

                case "tag":
                    mTagValues=selected;
                    tagText.setText(getStringFromSelectedArray(mTypes));
                    break;
            }
        }

    @Override
    public void setInitialSavedState(SavedState state) {
        super.setInitialSavedState(state);
    }
}
