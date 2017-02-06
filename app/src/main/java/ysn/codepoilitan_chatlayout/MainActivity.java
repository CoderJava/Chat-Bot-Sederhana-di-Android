package ysn.codepoilitan_chatlayout;

import android.content.res.AssetManager;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import org.alicebot.ab.AIMLProcessor;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;
import org.alicebot.ab.MagicStrings;
import org.alicebot.ab.PCAIMLProcessorExtension;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ysn.codepoilitan_chatlayout.adapter.AdapterSms;
import ysn.codepoilitan_chatlayout.model.DataSms;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivityTAG";
    private RecyclerView recyclerViewMain;
    private EditText editTextIsiPesan;
    private FloatingActionButton floatingActionButtonKirimPesan;

    List<Integer> listViewType;
    List<DataSms> listDataSms;
    AdapterSms adapterSms;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");

    private Bot bot;
    private Chat chat;

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
        //  checking SD Card availability
        boolean sdCardAvailable = isSdCardAvailable();

        //  receiving the assets from the app directory
        AssetManager assetManager = getResources().getAssets();
        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/chatbot/bots/Popoyo");
        boolean makeDir = dir.mkdirs();
        if (dir.exists()) {
            //  reading the file assets
            try {
                for (String fileAsset : assetManager.list("chatbot")) {
                    File subdir = new File(dir.getPath() + "/" + fileAsset);
                    boolean subdirCheck = subdir.mkdirs();
                    for (String file : assetManager.list("chatbot/" + fileAsset)) {
                        File f = new File(dir.getPath() + "/" + fileAsset + "/" + file);
                        if (f.exists()) {
                            continue;
                        }
                        InputStream in = null;
                        OutputStream out = null;
                        in = assetManager.open("chatbot/" + fileAsset + "/" + file);
                        out = new FileOutputStream(dir.getPath() + "/" + fileAsset + "/" + file);

                        //  copy file from assets to the mobile SD card or any secondary memory
                        copyFile(in, out);
                        in.close();
                        in = null;
                        out.flush();
                        out.close();
                        out = null;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //  get the working directory
        MagicStrings.root_path = Environment.getExternalStorageDirectory().toString() + "/chatbot";
        System.out.println("working directory: " + MagicStrings.root_path);
        AIMLProcessor.extension = new PCAIMLProcessorExtension();

        //  assign the AIML files to bot for processing
        bot = new Bot("Popoyo", MagicStrings.root_path, "chat");
        chat = new Chat(bot);


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

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    private boolean isSdCardAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ? true : false;
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

                //  bot response
                String botResponse = chat.multisentenceRespond(pesan);
                listViewType.add(AdapterSms.VIEW_TYPE_KIRI);
                DataSms dataSmsBot = new DataSms();
                dataSmsBot.setPesan(botResponse);
                dataSmsBot.setWaktu(simpleDateFormat.format(new Date()));
                listDataSms.add(dataSmsBot);
                adapterSms.notifyDataSetChanged();
            }
        }
    }
}
