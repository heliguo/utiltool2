package com.example.utiltool2.nfc;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Bundle;
import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.utiltool2.BaseActivity;
import com.example.utiltool2.R;

/**
 * author:lgh on 2020-01-09 08:54
 */
public class NfcActivity extends BaseActivity {

    private NfcAdapter nfc;
    private PendingIntent nfcintent;
    private IntentFilter[] mFilters;
    private String[][] mTechLists;
    private String value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nfc_activity);
        initNFC();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nfc != null)
            nfc.enableForegroundDispatch(this, nfcintent, mFilters, mTechLists);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (nfc != null)
            nfc.disableForegroundDispatch(this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        final byte[] id = tag.getId();
        value = Byte2Hex(id);
        Log.e("12354689788", "onNewIntentNFC: " + value);
    }

    private void initNFC() {
        nfc = NfcAdapter.getDefaultAdapter(this);
        nfcintent = PendingIntent.getActivity(this, 0,
                new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        IntentFilter ndef = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        mFilters = new IntentFilter[]{
                ndef
        };
        mTechLists = new String[][]{
                new String[]{NfcF.class.getName()},
                new String[]{NfcA.class.getName()},
                new String[]{NfcB.class.getName()},
                new String[]{NfcV.class.getName()}
        };

    }

    private String Byte2Hex(byte[] input) {
        return Byte2Hex(input, " ");
    }

    private String Byte2Hex(byte[] input, String space) {
        StringBuilder result = new StringBuilder();
        for (Byte inputbyte : input) {
            result.append(String.format("%02X" + space, inputbyte));
        }
        return result.toString();
    }

}
