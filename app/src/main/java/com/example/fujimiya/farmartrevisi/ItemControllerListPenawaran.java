package com.example.fujimiya.farmartrevisi;

/**
 * Created by fujimiya on 12/25/16.
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;


public class ItemControllerListPenawaran extends RecyclerView.ViewHolder implements View.OnClickListener {

    //Variable
    CardView cardItemLayout;
    ImageView icon; // Picture
    TextView title;
    TextView subTitle;
    DialogInterface.OnClickListener listener;
    Firebase FUref,NOref;
    Intent i;
    AlertDialog alert;

    TextView txttanggal,txtcostomer,txtkomoditi,txtharga,txtjumlah,txttotal;
    Button btnterima,btnbatalkan;

    public ItemControllerListPenawaran(View itemView) {
        super(itemView);

        //Set id
        cardItemLayout = (CardView) itemView.findViewById(R.id.cardlist_item);

        //Tambahan untuk id Picture

        kunci = "";
        //id Text
        title = (TextView) itemView.findViewById(R.id.listitem_name);
        subTitle = (TextView) itemView.findViewById(R.id.listitem_subname);

        //onClick
        itemView.setOnClickListener(this);

    }


    public String kunci;
    public View view;
    public AlertDialog alg;
    public int urut;

    public String Snama, Sstatus, Semail, Salamat, Spassword;
    Double lat, lon;

    @Override
    public void onClick(View v) {
        view = v;
        //tampilkan toas ketika click
        urut = Integer.parseInt(String.format("%d", getAdapterPosition()));
        //i = new Intent(v.getContext(), DetailListPenjualanActivity.class);
        Firebase.setAndroidContext(v.getContext());
        NOref = new Firebase("https://farmartcorp.firebaseio.com/notifikasi-customer");

        FUref = new Firebase("https://farmartcorp.firebaseio.com/penjualan");
        FUref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                try {


                    kunci = "";
                    //Toast.makeText(view.getContext(), MyRecyclerAdapterListPenjualan.FNama.get(urut), Toast.LENGTH_LONG).show();
                    for (DataSnapshot child : dataSnapshot.child(PenawaranFragment.kunci).getChildren()) {


                        if (child.getKey().equals(MyRecyclerAdapterListPenawaran.Fkunci.get(urut))) {

                            String key = (String) child.getKey();
                            String costomer = (String) child.child("costomer").getValue();
                            String gambar = (String) child.child("gambar").getValue();
                            String harga = (String) child.child("harga").getValue();
                            String jumlah = (String) child.child("jumlah").getValue();
                            String komoditi = (String) child.child("komoditi").getValue();
                            String modebayar = (String) child.child("modebayar").getValue();
                            String timestamp = (String) child.child("timestamp").getValue();
                            String total = (String) child.child("total").getValue();
                            String kuncicustomer = (String) child.child("key").getValue();
                            kunci = key;
                            Toast.makeText(view.getContext(), FUref.child(key).getKey().toString(), Toast.LENGTH_LONG).show();
                            urut = 0;

                            if (modebayar.equals("Transfer")){

                            }

                            if (modebayar.equals("COD")){
                                Toast.makeText(view.getContext(), "COD", Toast.LENGTH_LONG).show();
                                COD(costomer,gambar,harga,jumlah,komoditi,modebayar,timestamp,total,kuncicustomer);
                            }
                        }

                    }
                } catch (Exception e) {
                    Toast.makeText(view.getContext(), "Data Telah Rusak\n"+e, Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }

    public void COD(String costomer, final String gambar, final String harga, final String jumlah, final String komoditi, final String modebayar, final String timestamp, final String total, final String kuncicustomer){

        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.pemesanan, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(view.getContext());
        alertDialogBuilder.setView(v);

        txttanggal = (TextView) v.findViewById(R.id.tanggal);
        txtcostomer = (TextView) v.findViewById(R.id.customer);
        txtkomoditi = (TextView) v.findViewById(R.id.komoditi);
        txtharga = (TextView) v.findViewById(R.id.harga);
        txtjumlah = (TextView) v.findViewById(R.id.jumlah);
        txttotal = (TextView) v.findViewById(R.id.total);

        txttanggal.setText(timestamp);
        txtcostomer.setText(costomer);
        txtkomoditi.setText(komoditi);
        txtharga.setText(harga);
        txtjumlah.setText(jumlah);
        txttotal.setText(total);

        btnterima = (Button) v.findViewById(R.id.btnterima);
        btnterima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODModel codModel = new CODModel(timestamp,komoditi,harga,jumlah,total,modebayar,gambar,UserActivity.nama,"Tersedia");
                NOref.child(kuncicustomer).push().setValue(codModel);
                FUref.child(PenawaranFragment.kunci).child(kunci).setValue(null);
                alert.cancel();

            }
        });

        btnbatalkan = (Button) v.findViewById(R.id.btntolak);
        btnbatalkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CODModel codModel = new CODModel(timestamp,komoditi,harga,jumlah,total,modebayar,gambar,UserActivity.nama,"Habis");
                NOref.child(kuncicustomer).push().setValue(codModel);
                FUref.child(PenawaranFragment.kunci).child(kunci).setValue(null);
                alert.cancel();

            }
        });


        alert = alertDialogBuilder.create();
        alert.show();

    }
}