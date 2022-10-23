package com.example.apiuniversidad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button llamarApi;
    TextInputLayout inputPais;
    TextInputLayout inputNombre;
    String enlaceApi;
    List<ListUniversidad> universidades;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llamarApi = findViewById(R.id.buttonLlamarApi);
        inputPais = findViewById(R.id.textInputPais);
        inputNombre = findViewById(R.id.textInputNombre);
        webView = findViewById(R.id.webView);
        final WebSettings ajustesVisorWeb = webView.getSettings();
        ajustesVisorWeb.setJavaScriptEnabled(true);

        llamarApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leerApi();
            }
        });
    }
    public void leerApi(){
        String nombreString = inputNombre.getEditText().getText().toString();
        String paisString = inputPais.getEditText().getText().toString();
        if (nombreString.matches("") && paisString.matches("")) {
            enlaceApi = "http://universities.hipolabs.com/search?country=Spain";
        }else if(nombreString.matches("") && paisString != ""){
            enlaceApi = "http://universities.hipolabs.com/search?country=" + paisString;
        }else if(nombreString != "" && paisString.matches("")){
            enlaceApi = "http://universities.hipolabs.com/search?name=" + nombreString;
        }else{
            enlaceApi = "http://universities.hipolabs.com/search?country=" + paisString + "&name=" + nombreString;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, enlaceApi, new Response.Listener<String>() {
            @Override
            public void onResponse(String response){
                try{
                    JSONArray jsonArray = new JSONArray(response);
                    universidades = new ArrayList<>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject universidad = jsonArray.getJSONObject(i);
                        String nombre = universidad.getString("name");
                        String web = universidad.getJSONArray("web_pages").toString();
                        System.out.println(nombre + " " + web);
                        universidades.add(new ListUniversidad(nombre, web));
                    }

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("Error", error.getMessage());
            }
        });
        Volley.newRequestQueue(this).add(postRequest);
        ListAdapter listAdapter = new ListAdapter(universidades, this, webView);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }
}