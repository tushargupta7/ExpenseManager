package com.example.tushar.expensemanager.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.tushar.expensemanager.R;
import com.example.tushar.expensemanager.adapter.CategoryTagsSelectionAdapter;
import com.example.tushar.expensemanager.db.DatabaseManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class SelectCategoryFragment extends Fragment implements CategoryTagsSelectionAdapter.AdapterInterface{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String ARG_PARAM1 = "type";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private String mType;
    private OnListFragmentInteractionListener mListener;
    private List<String> selectedList;
    private RecyclerView recyclerView;
    private List<String> listCategoryTag;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SelectCategoryFragment(OnListFragmentInteractionListener listener) {
        mListener=listener;
    }

    public String getmType() {
        return mType;
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SelectCategoryFragment newInstance(int columnCount,String type,OnListFragmentInteractionListener listener) {
        SelectCategoryFragment fragment = new SelectCategoryFragment(listener);
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        args.putString(ARG_PARAM1,type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedList=new ArrayList<>();
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
            mType=getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
         listCategoryTag=new ArrayList<>();
        // Set the adapter
            Context context = view.getContext();
            recyclerView = (RecyclerView) view.findViewById(R.id.cat_tag_list);
            updateList();
        ((Button)view.findViewById(R.id.ok_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onCategoryTagsSelected(selectedList,mType);

            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void itemCLicked(String str,String action) {
        if(action.equalsIgnoreCase("add")){
            selectedList.add(str);
        }
        else selectedList.remove(str);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onCategoryTagsSelected(List<String> selected,String type);
    }

    public void updateList(){
        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), mColumnCount));
        }
        if(mType.equalsIgnoreCase("category")){
            listCategoryTag= DatabaseManager.getInstance().getAllCategories();
        }
        else {
            listCategoryTag=DatabaseManager.getInstance().getAllTags();
        }
        recyclerView.setAdapter(new CategoryTagsSelectionAdapter(listCategoryTag, this));

    }
}
