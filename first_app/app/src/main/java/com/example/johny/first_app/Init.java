package com.example.johny.first_app;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Init extends AppCompatActivity {

    private static String url = "http://192.168.1.8:3000/";

    ListView gamesLayout;
    LinearLayout.LayoutParams param;
    TableLayout gamesTable;
    JSONArray jsonAll = null;
    final ArrayList<String> gamesList = new ArrayList<String>();

    private static final String TAG_RESULT = "result";
    private static final String TAG_EVENT = "event";
    private static final String TAG_NAME = "name";
    private static final String TAG_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        String user = getIntent().getStringExtra("user");
        TextView t = (TextView) findViewById(R.id.textView);
        t.setText("Hello " + user);

        //createTable();
       // teste();

        new JSONParse().execute();

    }

    private void teste(){
        TableLayout t = (TableLayout) findViewById(R.id.gamesTable);

    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void createTable(){
        gamesTable = (TableLayout) findViewById(R.id.gamesTable);

        TableRow tr_head = new TableRow(this);
        //tr_head.setId(10);
        tr_head.setBackgroundColor(Color.argb(80,00,00,00));
        /*tr_head.setLayoutParams(new TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT));*/

        TextView label_date = new TextView(this);
        //label_date.setId(20);
        label_date.setText("Game");
        label_date.setTextColor(Color.WHITE);
        label_date.setPadding(5, 5, 5, 5);
        label_date.setTextSize(20);
        label_date.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tr_head.addView(label_date);// add the column to the table row here


        TextView label_perc = new TextView(this);
        //label_perc.setId(21);// define id that must be unique
        label_perc.setText("Date"); // set the text for the header
        label_perc.setTextColor(Color.WHITE); // set the color
        label_perc.setPadding(5, 5, 5, 5); // set the padding (if required)
        label_perc.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        tr_head.addView(label_perc); // add the column to the table row here


//        param = new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.WRAP_CONTENT,
//                TableLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//        param.weight = 1.0f;
//        label_date.setLayoutParams(param);
//        label_perc.setLayoutParams(param);

        gamesTable.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
       // gamesTable.setWeightSum(1.0f);
//        gamesTable.addView(tr_head, new TableLayout.LayoutParams(
//                TableLayout.LayoutParams.MATCH_PARENT,
//                TableLayout.LayoutParams.MATCH_PARENT,1.0f));
        gamesTable.addView(tr_head);
    }

    private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            gamesTable = (TableLayout) findViewById(R.id.gamesTable);

            pDialog = new ProgressDialog(Init.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();

            try {
                jsonAll = json.getJSONArray(TAG_RESULT);
                Log.d("array", json.toString());
                JSONObject game = null;
                Integer count=0;

                for (int i = 2; i< jsonAll.length();i++) {

                    game = jsonAll.getJSONObject(i);
                    JSONObject event = game.getJSONObject(TAG_EVENT);
                    String name = event.getString(TAG_NAME);
                    String id = event.getString(TAG_ID);


                    TableRow tr = new TableRow(Init.this);
                    LinearLayout  l = new LinearLayout(Init.this);

                    if(count%2!=0) tr.setBackgroundColor(Color.argb(80,00,00,00));
                    //if(count%2==0) tr.setBackgroundColor(Color.BLACK);
                    //tr.setId(100+count);
                    //tr.setMinimumWidth(100);
                    tr.setLayoutParams(new TableLayout.LayoutParams(
                            TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.MATCH_PARENT));


                    //Create two columns to add as table data
                    // Create a TextView to add date
                    TextView labelDATE = new TextView(Init.this);
                    //labelDATE.setId(200+count);
                    labelDATE.setText(name);
                    labelDATE.setTextSize(18);
                    //labelDATE.setPadding(2, 0, 5, 0);
                    labelDATE.setTextColor(Color.WHITE);
                    tr.addView(labelDATE);

                    TextView labelWEIGHT = new TextView(Init.this);
                    //labelWEIGHT.setId(200+count);
                    labelWEIGHT.setText(id);
                    labelWEIGHT.setTextColor(Color.WHITE);
                    tr.addView(labelWEIGHT);


                    param = new TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
                    param.weight = 1.0f;
                    labelDATE.setLayoutParams(param);

                    param = new TableRow.LayoutParams(
                            TableRow.LayoutParams.MATCH_PARENT,
                            TableRow.LayoutParams.MATCH_PARENT, 2.0f);
                    param.weight = 2.0f;
                    labelWEIGHT.setLayoutParams(param);
                    // finally add this to the table row
//                    gamesTable.addView(tr, new TableLayout.LayoutParams(
//                            TableLayout.LayoutParams.MATCH_PARENT,
//                            TableLayout.LayoutParams.MATCH_PARENT));
                    gamesTable.addView(tr);

                    count++;

                    //gamesList.add(name);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

    }













    /*private class JSONParse extends AsyncTask<String, String, JSONObject> {
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            gamesLayout = (ListView) findViewById(R.id.gamesList);

            pDialog = new ProgressDialog(Init.this);
            pDialog.setMessage("Getting Data ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONParser jParser = new JSONParser();

            // Getting JSON from URL
            JSONObject json = jParser.getJSONFromUrl(url);
            return json;
        }
        @Override
        protected void onPostExecute(JSONObject json) {
            pDialog.dismiss();

            try {
                // Getting JSON Array
                jsonAll = json.getJSONArray(TAG_RESULT);

                JSONObject game = null;
                for (int i = 2; i< jsonAll.length();i++) {

                    //TextView gameRow = new TextView(Init.this);

                    game = jsonAll.getJSONObject(i);
                    JSONObject event = game.getJSONObject(TAG_EVENT);
                    String name = event.getString(TAG_NAME);
                    String id = event.getString(TAG_ID);

                    gamesList.add(name);

                    //gameRow.setText(name);
                    //gamesLayout.addView(gameRow);

                    Log.d("game", name);
                }

//               final StableArrayAdapter adapter = new StableArrayAdapter(Init.this,
//                                                        android.R.layout.simple_list_item_1, gamesList);

                StableArrayAdapter adapter = new StableArrayAdapter(Init.this,
                                                       R.layout.games_row, gamesList);
                gamesLayout.setAdapter(adapter);


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private class StableArrayAdapter extends ArrayAdapter<String> {

        private List <String>items ;
        private Context mContext;
        private int id;


        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {

            super(context, textViewResourceId, objects);
            this.items = objects;
            this.mContext = context;
            this.id = textViewResourceId;
        }


        @Override
        public boolean hasStableIds() {
            return true;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.games_row, parent, false);
            TextView textView = (TextView) rowView.findViewById(R.id.label);

            //ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

            textView.setText(items.get(position));
            //rowView.setBackgroundResource(R.drawable.rounded_corners);
//            textView.setTextColor(Color.WHITE);
//            textView.setTypeface(null, Typeface.BOLD);
//            textView.setGravity(Gravity.CENTER_VERTICAL);
//            textView.setTextSize(20);

            return rowView;
        }

    }*/

}
