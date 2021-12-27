package com.example.datastructuresproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class options extends AppCompatActivity {

    /*     public void onClickOutput (View v) {
        String copy = "", message = txtOutputDataBrute.getText().toString();
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(copy,message);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(getApplicationContext(),"Copied to Clipboard.",Toast.LENGTH_SHORT).show();
    }

    */

    /*     //BACK BUTTON:
    public void onClickBack (View v) {
        Intent i = new Intent(getApplicationContext(), mainMenu.class);
        startActivity(i);
        finish();
    }

    public void onBackPressed() {
        //super.onBackPressed();
        Intent i = new Intent(getApplicationContext(), mainMenu.class);
        startActivity(i);
        finish();
    }
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
    }
}