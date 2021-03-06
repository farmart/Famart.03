package com.example.fujimiya.farmartrevisi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class PenawaranFragment extends Fragment {

    RecyclerView rv;
    MyRecyclerAdapterListPenawaran adapter;
    CardView cv;

    public static String kunci;


    public PenawaranFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_penawaran, container, false);

        kunci = getArguments().getString("kunci");
        //Toast.makeText(view.getContext(),kunci, Toast.LENGTH_LONG).show();


        try {
            cv = (CardView) view.findViewById(R.id.cardlist_item);
            rv = (RecyclerView) view.findViewById(R.id.home_recyclerview);

            rv.setHasFixedSize(true);
            rv.setLayoutManager(new LinearLayoutManager(this.getActivity()));
            rv.setItemAnimator(new DefaultItemAnimator());

            //Adapter
            if (adapter == null) {
                adapter = new MyRecyclerAdapterListPenawaran(this.getActivity());
                rv.setAdapter(adapter);
            }

        }catch (Exception e){
            Toast.makeText(view.getContext(), "Data Telah Rusak", Toast.LENGTH_LONG).show();
        }


        return view;
    }

}
