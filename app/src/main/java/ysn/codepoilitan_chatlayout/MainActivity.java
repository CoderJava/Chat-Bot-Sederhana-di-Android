package ysn.codepoilitan_chatlayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ysn.codepoilitan_chatlayout.adapter.AdapterSms;
import ysn.codepoilitan_chatlayout.model.DataSms;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerViewMain;
    private EditText editTextIsiPesan;
    private FloatingActionButton floatingActionButtonKirimPesan;

    List<Integer> listViewType;
    List<DataSms> listDataSms;
    AdapterSms adapterSms;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewMain = (RecyclerView) findViewById(R.id.recycler_view_main);
        editTextIsiPesan = (EditText) findViewById(R.id.edit_text_isi_pesan);
        floatingActionButtonKirimPesan = (FloatingActionButton) findViewById(R.id.floating_action_button_kirim_pesan);

        loadData();

        floatingActionButtonKirimPesan.setOnClickListener(this);
    }

    private void loadData() {
        listViewType = new ArrayList<>();
        listViewType.add(AdapterSms.VIEW_TYPE_KIRI);

        listDataSms = new ArrayList<>();
        DataSms dataSms = new DataSms();
        dataSms.setPesan("Hello, apa kabar?");
        dataSms.setWaktu(simpleDateFormat.format(new Date()));
        listDataSms.add(dataSms);

        adapterSms = new AdapterSms(listViewType, listDataSms);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewMain.setLayoutManager(layoutManager);
        recyclerViewMain.setAdapter(adapterSms);
    }


    @Override
    public void onClick(View view) {
        if (view == floatingActionButtonKirimPesan) {
            String pesan = editTextIsiPesan.getText().toString();
            if (TextUtils.isEmpty(pesan)) {
                Snackbar.make(findViewById(android.R.id.content), "Pesan belum di isi!!!", Snackbar.LENGTH_LONG)
                        .show();
                editTextIsiPesan.setError("Pesan kosong");
            } else {
                editTextIsiPesan.setText("");
                listViewType.add(AdapterSms.VIEW_TYPE_KANAN);
                DataSms dataSms = new DataSms();
                dataSms.setPesan(pesan);
                dataSms.setWaktu(simpleDateFormat.format(new Date()));
                listDataSms.add(dataSms);
                adapterSms.notifyDataSetChanged();
            }
        }
    }
}
