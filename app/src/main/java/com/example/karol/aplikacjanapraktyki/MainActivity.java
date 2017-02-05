package com.example.karol.aplikacjanapraktyki;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends AppCompatActivity {

Button downloadButton;
ListView listView;
TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        downloadButton = (Button) findViewById(R.id.download_data);
        listView = (ListView) findViewById(R.id.dataListView);
        title = (TextView) findViewById(R.id.title);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            String url = "http://www.mocky.io/v2/58628ab00f0000350e175575";
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
            }
        });

        listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                String nameText = ((TextView) arg1.findViewById(R.id.nameText)).getText().toString();
                String last_nameText = ((TextView) arg1.findViewById(R.id.last_nameText)).getText().toString();
                String countryText = ((TextView) arg1.findViewById(R.id.countryText)).getText().toString();
                sendData(nameText, last_nameText, countryText);
            }
        });
    }

    private void sendData(String nameText, String last_nameText, String countryText) {
        Intent intent = new Intent(getBaseContext(), SecondActivity.class);
        intent.putExtra("name", nameText);
        intent.putExtra("last_name", last_nameText);
        intent.putExtra("country", countryText);
        startActivity(intent);
    }

    private String downloadUrl (String strUrl) throws IOException {
        String data = "";
        InputStream inputStream = null;
        try {
            URL url = new URL(strUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer stringBuffer = new StringBuffer();

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }

            data = stringBuffer.toString();
            bufferedReader.close();
        } catch (Exception e) {
            Log.d("Exception while downloading url", e.toString());
        } finally {
            inputStream.close();
        }
        return data;
    }

    private class DownloadTask extends AsyncTask<String, Integer, String> {
        String data = null;

        @Override
        protected String doInBackground(String... url) {
            try {
                data = downloadUrl(url[0]);
            } catch (IOException e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result){
            ListViewLoader listViewLoader = new ListViewLoader();
            listViewLoader.execute(result);
        }
    }

    private class ListViewLoader extends AsyncTask<String, Void, SimpleAdapter> {

        JSONObject jsonObject;
        String title;

        @Override
        protected SimpleAdapter doInBackground(String... strJSON) {
            List<HashMap<String, Object>> data = null;
            try {
                jsonObject = new JSONObject(strJSON[0]);
                title = jsonObject.getString("title");
                JSONparser jsonParser = new JSONparser();
                data = jsonParser.parse(jsonObject);
            } catch (Exception e) {
                Log.d("Exception ", e.toString());
            }

            String[] from = {"name", "last_name", "country", "details"};
            int[] to = {R.id.nameText, R.id.last_nameText, R.id.countryText};
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), data, R.layout.list_view_row, from, to);

            return adapter;
        }

        @Override
        protected void onPostExecute(SimpleAdapter adapter) {
            listView.setAdapter(adapter);
            TextView titleTextView = (TextView) findViewById(R.id.title);
            titleTextView.setText(title);
            titleTextView.setVisibility(View.VISIBLE);
        }
    }
}
