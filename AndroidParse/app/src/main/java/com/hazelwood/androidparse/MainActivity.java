package com.hazelwood.androidparse;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ui.ParseLoginBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    ParseUser user;
    Record_Adapter record_adapter;
    ArrayList<Record> records;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Parse.initialize(this, "xGLMWF5Ns6eK9AOLUEV5clUzM5SaR3KenwvL9k7R", "D4fGkNS7KQEImh0fAWlB6yVbbKMcIeqUmSG5P8uo");

        ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
        startActivityForResult(builder.build(), 0);


    }



    @Override
    protected void onResume() {
        super.onResume();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Record");
        final ListView listView = (ListView) findViewById(R.id.list);
        records = new ArrayList<Record>();
        record_adapter = new Record_Adapter(this, records);
        final ArrayList recordIDs = new ArrayList<>();

        query.whereEqualTo("user", ParseUser.getCurrentUser());
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException e) {

                for (int i = 0; i < parseObjects.size(); i++) {
                    final String name = parseObjects.get(i).getString("title");
                    final String description = parseObjects.get(i).getString("description");
                    Boolean complete = parseObjects.get(i).getBoolean("finish");
                    recordIDs.add(parseObjects.get(i).getObjectId().toString());

                    records.add(new Record(name,description,complete));

                    record_adapter.notifyDataSetChanged();
                    listView.setAdapter(record_adapter);
                }

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                                records.remove(position);
                                record_adapter.notifyDataSetChanged();

                                ParseQuery<ParseObject> post = ParseQuery.getQuery("Record");
                                post.whereEqualTo("objectId", recordIDs.get(position));
                                post.getFirstInBackground(new GetCallback<ParseObject>() {
                                    @Override
                                    public void done(ParseObject parseObject, ParseException e) {
                                        parseObject.deleteEventually();
                                    }
                                });


                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_delete)
                        .show();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        records.clear();
        record_adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()){
            case R.id.action_sign_out:
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser();
                records.clear();
                record_adapter = new Record_Adapter(this, records);
                record_adapter.notifyDataSetChanged();
                ParseLoginBuilder builder = new ParseLoginBuilder(MainActivity.this);
                startActivityForResult(builder.build(), 0);

                return true;
            case R.id.action_post:
                Intent form = new Intent(this, Record_Form.class);
                startActivity(form);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
}
