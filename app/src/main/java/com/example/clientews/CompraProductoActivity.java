package com.example.clientews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.clientews.ipserver.Servidor;
import com.example.clientews.modeloVO.Medicamento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class CompraProductoActivity extends AppCompatActivity implements  Response.Listener<JSONObject>,Response.ErrorListener {

    private ListView lista;
    private ArrayList <String> listaDatos;
    private ArrayList<Medicamento> listamedicamento;
    private EditText buscar;

    ArrayAdapter<String> adapter;
    private RequestQueue requestQueue;
    private JsonObjectRequest jsonObjectRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_producto);

        lista = findViewById(R.id.listaMedicamentos);
        requestQueue= Volley.newRequestQueue(this);
        consulta();

        buscar= findViewById(R.id.buscador);
        //Metodo para mostrar informacion al pulsar letras en el buscador
        /*buscar.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode== event.KEYCODE_DEL){
                    consulta();
                }else {
                   buscquedaevento();
                }
                return false;
            }
        });*/

    }

    public void consulta (){
        String url;
        url= Servidor.ip_server+"/php_sw/mostrar_sw.php";
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,url,null,this,this);
        requestQueue.add(jsonObjectRequest);
    }

   /* private void buscquedaevento(){
        String url;
        try {
            url = Servidor.ip_server + "/php_sw/Buscador.php?nombre_medicamento=" + buscar.getText().toString();
            jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
            requestQueue.add(jsonObjectRequest);
        }catch (Exception e){
            Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
        }
    }*/

    public void busqueda (){
        String url;
        if (!buscar.getText().toString().isEmpty()){
            try {
                url = Servidor.ip_server + "/php_sw/Buscador.php?nombre_medicamento=" + buscar.getText().toString();
                jsonObjectRequest= new JsonObjectRequest(Request.Method.GET,url,null,this,this);
                requestQueue.add(jsonObjectRequest);
            }catch (Exception e){
                Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(this, "Datos no ingresados", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onResponse(JSONObject response) {

        Medicamento medicamento = null;

        //OBTENCION DE LA RESPUESTA DE LOS REGISTROS OBTENIDOS POR LA CONSULTA EN EL PHP
        JSONArray jsonArray = response.optJSONArray("tbl_medicamento");
        listamedicamento= new ArrayList<>();

        try {
            for (int i = 0; i <jsonArray.length(); i ++){
                medicamento = new Medicamento();

                //Se define para poder cargar la informacion ya en el arreglo de respuesta
                JSONObject jsonObject= null;

                //Se le asigna cada informacion por recorrido del array de respuesta
                jsonObject = jsonArray.getJSONObject(i);

                //se agrega cada registro relacionado con los campos
                medicamento.setId(jsonObject.optInt("id"));
                medicamento.setNombre_medicamento(jsonObject.optString("nombre_medicamento"));
                medicamento.setCantidad(jsonObject.optInt("cantidad"));
                medicamento.setPrecio(jsonObject.optDouble("precio"));
                medicamento.setFecha_vencimiento(jsonObject.optString("fecha_vencimiento"));

                //llenamos nuestra lista
                listamedicamento.add(medicamento);
            }
            listaDatos = new ArrayList<>();
            for (int i=0; i<listamedicamento.size(); i++){
                listaDatos.add(listamedicamento.get(i).getNombre_medicamento());
            }

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,listaDatos);
            lista.setAdapter(adapter);

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent CompraFinal = new Intent(getApplicationContext(),CompraFinalActivity.class);
                    CompraFinal.putExtra("id", listamedicamento.get(position).getId());
                    CompraFinal.putExtra("nombre",listamedicamento.get(position).getNombre_medicamento());
                    CompraFinal.putExtra("cantidad",listamedicamento.get(position).getCantidad());
                    CompraFinal.putExtra("precio",listamedicamento.get(position).getPrecio());
                    CompraFinal.putExtra("fecha", listamedicamento.get(position).getFecha_vencimiento());
                    startActivity(CompraFinal);
                }
            });
        }catch (Exception e){
            Toast.makeText(this, "Error Desconocido", Toast.LENGTH_SHORT).show();
        }

    }




    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(this, "Error" +error, Toast.LENGTH_SHORT).show();
        System.out.println("El error es:  "+error +"***************************************************");
    }

    public void click(View view) {
        busqueda();
    }
}
