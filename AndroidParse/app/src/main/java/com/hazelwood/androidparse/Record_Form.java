package com.hazelwood.androidparse;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by Hazelwood on 2/5/15.
 */
public class Record_Form extends Activity {
    EditText title = null;
    EditText description = null;
    CheckBox complete = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_form);

        title = (EditText)findViewById(R.id.edit_title);
        description = (EditText)findViewById(R.id.edit_description);
        complete = (CheckBox)findViewById(R.id.edit_complete);
        Log.d("USER", ParseUser.getCurrentUser().getEmail());

        Button submitBTN = (Button) findViewById(R.id.edit_button);
        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });



    }
}
