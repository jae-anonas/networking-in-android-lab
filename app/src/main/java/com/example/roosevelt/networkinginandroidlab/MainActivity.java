package com.example.roosevelt.networkinginandroidlab;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    Button btnCereal, btnChocolate, btnTea;
    ListView listView;

    ArrayAdapter<String> itemsAdapter;
    List<String> itemsList;

    private String getURL(String query) {
        return  "http://api.walmartlabs.com/v1/search?query=" + query +
                "&format=json&apiKey=sevttjvxmn2hgp4hdxvbxvqp";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bindViews();
        itemsList = new LinkedList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, itemsList);
        listView.setAdapter(itemsAdapter);

    }

    @Override
    public void onClick(View view) {

        ConnectivityManager conMng = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conMng.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()){
            //do internet stuff
            switch(view.getId()){
                case R.id.btnCereal:
                    new DownloadTask().execute(getURL("cereal"));
                    Log.i("sssssssss", "count cereal: " + itemsList.size());
                    itemsAdapter.notifyDataSetChanged();
                    break;

                case R.id.btnChocolate:
                    new DownloadTask().execute(getURL("chocolate"));
                    Log.i("sssssssss", "count chocolate: " + itemsList.size());
                    itemsAdapter.notifyDataSetChanged();
                    break;

                case R.id.btnTea:
                    new DownloadTask().execute(getURL("tea"));
                    Log.i("sssssssss", "count tea: " + itemsList.size());
                    itemsAdapter.notifyDataSetChanged();
                    break;
            }

        } else {
            //no network connection available
            Toast.makeText(this, "No Internet connection available", Toast.LENGTH_SHORT).show();
        }
    }

    private void bindViews(){
        btnCereal = (Button) findViewById(R.id.btnCereal);
        btnChocolate = (Button) findViewById(R.id.btnChocolate);
        btnTea = (Button) findViewById(R.id.btnTea);
        listView = (ListView) findViewById(R.id.listView);

        btnCereal.setOnClickListener(this);
        btnChocolate.setOnClickListener(this);
        btnTea.setOnClickListener(this);

    }


    public List<String> downloadUrl(String myUrl) throws IOException, JSONException {
        InputStream is = null;

        URL url;
        try {
            url = new URL(myUrl);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            is = conn.getInputStream();

            String contentAsString = readIt(is);

            return parseJson(contentAsString);
        } finally {
            if (is != null)
                is.close();

        }
    }

    private class DownloadTask extends AsyncTask<String, Void, List<String>> {


        @Override
        protected List<String> doInBackground(String... strings) {

            try {
                return downloadUrl(strings[0]);
//                return performPost(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("ASYNC", "Unable to retrieve webpage. URL may be invalid.");
            } catch (JSONException e){
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(List<String> list) {
            super.onPostExecute(list);
            itemsList.clear();
            for (String s : list) {
                itemsList.add(s);
            }
            Log.i("aaaaaaaaa", "count: " + itemsList.size());

            itemsAdapter.notifyDataSetChanged();
//            listView.setAdapter(itemsAdapter);

        }
    }

    private String readIt(InputStream is) throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String read;

        while ((read = br.readLine()) != null){
            sb.append(read);
        }

        return sb.toString();
    }


    private List<String> parseJson(String contentAsString) throws JSONException{
        List<String> itemList = new LinkedList<>();

        JSONObject root = new JSONObject(contentAsString);
        JSONArray itemsArray = root.getJSONArray("items");

        for (int i = 0 ; i < itemsArray.length() ; i++){
            JSONObject item = itemsArray.getJSONObject(i);
            itemList.add(item.getString("name"));
            Log.i("iiiiiiii", item.getString("name"));

        }

        Log.i("iiiiiiiii", "i got here count: " + itemList.size());
        return itemList;

    }


}
