package com.hazelwood.androidparse;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

/**
 * Created by Hazelwood on 2/5/15.
 */
public class Record_Form extends Activity {
    EditText title = null;
    EditText description = null;
    CheckBox complete = null;
    Button submitBTN;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_form);

        title = (EditText)findViewById(R.id.edit_title);
        description = (EditText)findViewById(R.id.edit_description);
        complete = (CheckBox)findViewById(R.id.edit_complete);

        Record currentRecord = (Record) getIntent().getSerializableExtra("RECORD");
        String currentTitle = currentRecord.getRecordTitle();
        String currentDesc = currentRecord.getRecordDescription();

        title.setText(currentTitle);
        description.setText(currentDesc);



        int validation = getIntent().getIntExtra("TYPE", -1);
        submitBTN = (Button) findViewById(R.id.edit_button);

        if (validation == 0){
            submitBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected()){
                        ParseObject post = new ParseObject("Record");
                        post.put("user", ParseUser.getCurrentUser());
                        post.put("title", title.getText().toString());
                        post.put("description", description.getText().toString());

                        if (complete.isChecked()){
                            post.put("finish", true);
                        }else{
                            post.put("finish", false);
                        }

                        post.saveInBackground();
                        finish();
                    }
                }
            });
        }

        if (validation == 1){
            submitBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isConnected()){
                        redo();
                    }
                    if (!isConnected()){
                        new AlertDialog.Builder(Record_Form.this)
                                .setTitle("Network Problem")
                                .setMessage("There was a problem with network.")
                                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // do nothing
                                    }
                                })
                                .setIcon(android.R.drawable.ic_delete)
                                .show();
                    }
                }
            });
        }
    }

    public void redo(){
        String idString = getIntent().getStringExtra("ID");
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Record");

        query.getInBackground(idString, new GetCallback<ParseObject>() {
            @Override
            public void done(final ParseObject parseObject, ParseException e) {
                parseObject.put("title", title.getText().toString());
                parseObject.put("description", description.getText().toString());

                if (complete.isChecked()){
                    parseObject.put("finish", true);
                }else{
                    parseObject.put("finish", false);
                }

                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        finish();
                    }
                });
            }
        });

    }

    public boolean isConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return networkInfo.isConnected();
    }

}
