package com.example.jsonimage;

import androidx.appcompat.app.AppCompatActivity;

import android.net.UrlQuerySanitizer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView l1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
   		.cacheInMemory(true)
                .cacheOnDisk(true)
           .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
           .defaultDisplayImageOptions(defaultOptions)
           .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        l1 = findViewById(R.id.listID);
        JsonTask jsonTask = new JsonTask();
        jsonTask.execute();

    }

    private class JsonTask extends AsyncTask<String,String,List<CarModel>> {

        HttpURLConnection httpURLConnection = null;
        BufferedReader bufferedReader = null;

        @Override
        protected List<CarModel> doInBackground(String... strings) {
            try {
                URL url = new URL("https://jsonkeeper.com/b/FN11");
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.connect();
                InputStream inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line="";
                StringBuffer stringBuffer=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null){
                    stringBuffer.append(line);
                }
                String myFile = stringBuffer.toString();
                JSONObject filebject = new JSONObject(myFile);
                JSONArray cars = filebject.getJSONArray("cars");
                List<CarModel> carModelList = new ArrayList<CarModel>();

                for (int i =0; i<cars.length(); i++){
                    JSONObject innerObject = cars.getJSONObject(i);
                    CarModel carModel = new CarModel();
                    carModel.setName(innerObject.getString("name"));
                    carModel.setImg(innerObject.getString("image"));
                    carModelList.add(carModel);

                }
                return carModelList;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }finally {
                httpURLConnection.disconnect();
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(List<CarModel> carModels) {
            super.onPostExecute(carModels);

            CustomAdapter adapter = new CustomAdapter(MainActivity.this,R.layout.sample,carModels);
            l1.setAdapter(adapter);
        }
    }

}