package ysn.codepoilitan_chatlayout.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ysn.codepoilitan_chatlayout.R;
import ysn.codepoilitan_chatlayout.model.DataSms;

/**
 * Created by root on 28/01/17.
 */

public class AdapterSms extends RecyclerView.Adapter<AdapterSms.ViewHolder> {

    public static final int VIEW_TYPE_KANAN = 1;
    public static final int VIEW_TYPE_KIRI = 2;

    List<Integer> listViewType;
    List<DataSms> listDataSms;

    public AdapterSms(List<Integer> listViewType, List<DataSms> listDataSms) {
        this.listViewType = listViewType;
        this.listDataSms = listDataSms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_KANAN) {
             view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sisi_kanan, null);
            return new ItemSisiKananViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sisi_kiri, null);
            return new ItemSisiKiriViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int viewType = listViewType.get(position);
        DataSms dataSms = listDataSms.get(position);
        if (viewType == VIEW_TYPE_KANAN) {
            ItemSisiKananViewHolder itemSisiKananViewHolder = (ItemSisiKananViewHolder) holder;
            itemSisiKananViewHolder.textViewPesanItemSisiKanan.setText(dataSms.getPesan());
            itemSisiKananViewHolder.textViewWaktuPesanItemSisiKanan.setText(dataSms.getWaktu());
        } else {
            ItemSisiKiriViewHolder itemSisiKiriViewHolder = (ItemSisiKiriViewHolder) holder;
            itemSisiKiriViewHolder.textViewPesanItemSisiKiri.setText(dataSms.getPesan());
            itemSisiKiriViewHolder.textViewWaktuPesanItemSisiKiri.setText(dataSms.getWaktu());
        }
    }

    @Override
    public int getItemCount() {
        return listViewType.size();
    }

    @Override
    public int getItemViewType(int position) {
        return listViewType.get(position);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public class ItemSisiKananViewHolder extends ViewHolder {

        private TextView textViewPesanItemSisiKanan;
        private TextView textViewWaktuPesanItemSisiKanan;

        public ItemSisiKananViewHolder(View itemView) {
            super(itemView);
            textViewPesanItemSisiKanan = (TextView) itemView.findViewById(R.id.text_view_pesan_item_sisi_kanan);
            textViewWaktuPesanItemSisiKanan = (TextView) itemView.findViewById(R.id.text_view_waktu_pesan_item_sisi_kanan);
        }
    }

    public class ItemSisiKiriViewHolder extends ViewHolder {

        private TextView textViewPesanItemSisiKiri;
        private TextView textViewWaktuPesanItemSisiKiri;

        public ItemSisiKiriViewHolder(View itemView) {
            super(itemView);
            textViewPesanItemSisiKiri = (TextView) itemView.findViewById(R.id.text_view_pesan_item_sisi_kiri);
            textViewWaktuPesanItemSisiKiri = (TextView) itemView.findViewById(R.id.text_view_waktu_pesan_item_sisi_kiri);
        }
    }
}
