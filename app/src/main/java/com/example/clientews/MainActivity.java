package com.example.clientews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.clientews.Recycler.AdaptadorRecycler;
import com.example.clientews.Recycler.DatosVo;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ArrayList <DatosVo> listamenu = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ingresodatos();
        AdaptadorRecycler adaptadorRecycler = new AdaptadorRecycler(listamenu);
        adaptadorRecycler.setOnClickListener(new AdaptadorRecycler.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                switch (position){
                    case 0:
                        Intent compra = new Intent(getApplicationContext(),CompraProductoActivity.class);
                        startActivity(compra);
                        break;
                    case 1:
                        Intent localizacion = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(localizacion);
                        break;
                    case 2:
                        dialogo();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, "No seleccion", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });
        recyclerView.setAdapter(adaptadorRecycler);
    }



    public void ingresodatos(){
        listamenu.add(new DatosVo("Compra de Producto", R.drawable.comprarm));
        listamenu.add(new DatosVo("Encuentra una Farmacia", R.drawable.localizacionh));
        listamenu.add(new DatosVo("Nosotros",R.drawable.nosotros));
    }

    public void dialogo (){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Nosotros");
        builder.setMessage("Nuestra vision es contar con los mejores medicamentos y precios competitivos para todos nuestros clientes. \n \n fundada en Guatemala año 2020");
        builder.setPositiveButton("Ok",null);
        AlertDialog dialog =builder.create();
        dialog.show();

    }
}
