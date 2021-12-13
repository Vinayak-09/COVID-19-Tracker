package com.darkness.covid_19tracker;

import androidx.annotation.MainThread;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;


public class MainActivity extends AppCompatActivity {




    RequestQueue queue;

    JSONObject jsonData;

    ArrayList<String> allStates,allDistricts;
    Spinner spinnerStates;

    ArrayAdapter<String> arrayAdapterForSpinnerStates,arrayAdapterForSpinnerDistricts;

    RelativeLayout mainScreen, progressScreen;

    ArrayList<DistrictModel> districtModelArrayList;


    RecyclerView recyclerView;

    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressScreen = findViewById(R.id.progressBar);
        mainScreen = findViewById(R.id.mainRelative);

        queue = Volley.newRequestQueue(this);

        jsonData = new JSONObject();

        allDistricts = new ArrayList<>();
        allStates = new ArrayList<>();
        districtModelArrayList = new ArrayList<>();

        adapter = new MyAdapter(districtModelArrayList,this);

        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        arrayAdapterForSpinnerStates = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,allStates);
        arrayAdapterForSpinnerDistricts = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,allDistricts);
//        spinnerDistricts = findViewById(R.id.districtsSpinner);
        spinnerStates = findViewById(R.id.statesSpinner);

        spinnerStates.setAdapter(arrayAdapterForSpinnerStates);
//        spinnerDistricts.setAdapter(arrayAdapterForSpinnerDistricts);


        fetchJsonData();


        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtModelArrayList.clear();
                allDistricts.clear();
                JSONObject districts = null,mainData;
                try {
                    districts = jsonData.getJSONObject(allStates.get(position)).getJSONObject("districtData");
                    for (Iterator<String> it = districts.keys(); it.hasNext(); ) {
                        String key = it.next();
                        allDistricts.add(key);
                        mainData = districts.getJSONObject(key);
                        districtModelArrayList.add(new DistrictModel(key,mainData.getInt("active"),mainData.getInt("confirmed"),mainData.getInt("deceased"),mainData.getInt("recovered")));
                    }
                    arrayAdapterForSpinnerDistricts.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    void fetchJsonData(){

        mainScreen.setVisibility(View.GONE);
        progressScreen.setVisibility(View.VISIBLE);

        JsonObjectRequest objectRequest = new JsonObjectRequest("https://data.covid19india.org/state_district_wise.json", response -> {
            allDistricts.clear();
            allStates.clear();
            jsonData = response;
            for (Iterator<String> it = response.keys(); it.hasNext(); ) {
                String key = it.next();
                allStates.add(key);
            }

            try {
                updateSpinners();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            progressScreen.setVisibility(View.GONE);
            mainScreen.setVisibility(View.VISIBLE);
        }, error -> {
            Snackbar snackbar = Snackbar.make(MainActivity.this, findViewById(android.R.id.content),"Network Error!\nPlease try again.",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Retry", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            fetchJsonData();
                        }
                    });
            snackbar.show();

        });
        queue.add(objectRequest);
    }


    void updateSpinners() throws JSONException {

        arrayAdapterForSpinnerStates.notifyDataSetChanged();
        spinnerStates.setSelection(1);



        JSONObject districts = jsonData.getJSONObject(allStates.get(spinnerStates.getSelectedItemPosition())).getJSONObject("districtData");
        for (Iterator<String> it = districts.keys(); it.hasNext(); ) {
            String key = it.next();
            allDistricts.add(key);
        }
        arrayAdapterForSpinnerDistricts.notifyDataSetChanged();

    }
}