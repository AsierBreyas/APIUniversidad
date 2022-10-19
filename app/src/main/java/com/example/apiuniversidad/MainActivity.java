package com.example.apiuniversidad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class MainActivity extends AppCompatActivity {
    Button llamarApi;
    TextInputLayout inputPais;
    TextInputLayout inputNombre;
    String enlaceApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llamarApi = findViewById(R.id.buttonLlamarApi);
        inputPais = findViewById(R.id.textInputPais);
        inputNombre = findViewById(R.id.textInputNombre);
/*
        datos = findViewById(R.id.textUniversidades);
*/

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
                    ArrayList<HashMap<String, String>> data=new ArrayList<HashMap<String,String>>();
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject universidad = jsonArray.getJSONObject(i);
                        String nombre = universidad.getString("name");
                        String web = universidad.getJSONArray("web_pages").toString();
                        System.out.println(nombre + " " + web);
                        HashMap<String, String> map=new HashMap<String, String>();
                        map.put("Nombre", nombre);
                        map.put("Web", web);
                        data.add(map);
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
    }
    private UniversidadFragment newInstance(String nombre, String web){
        UniversidadFragment f = new UniversidadFragment();
        Bundle args = new Bundle();
        args.putString("nombre", nombre);
        args.putString("web", web);
        f.setArguments(args);
        return f;
    }
}