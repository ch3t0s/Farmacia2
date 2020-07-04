package com.example.clientews;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clientews.ipserver.Modificar;

public class CompraFinalActivity extends AppCompatActivity {

    Modificar modificar= new Modificar();

    EditText nomcliente, nitCliente;
    TextView nombre,cantidad,precio,fecha, id2;
    String nom,fec, nomc,nitc;
    String cant2;

    int cant, cant3, id;
    Double prec;
    Spinner spinerCant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compra_final);

        nombre=findViewById(R.id.Nombretxt);
        cantidad=findViewById(R.id.Cantidadtxt);
        precio=findViewById(R.id.Preciotxt);
        fecha=findViewById(R.id.fechatxt);
         nomcliente=findViewById(R.id.txtNombre);
         nitCliente=findViewById(R.id.txtNit);
        obtencion();

        //MOSTRAR SPINNER, LOS ITEMS DEL SPINNER SE CREARON EN LA RUTA RES,VALUE,OPCANTIDAD.XML
        spinerCant= findViewById(R.id.Spinner);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.cantidad, android.R.layout.simple_spinner_item);
        spinerCant.setAdapter(adapter);
        spinerCant.setSelection(0);

        //se almacena en una variable el valor del item del spinner



    }



    public void obtencion (){

        Bundle bundle = getIntent().getExtras();

        nom=bundle.getString("nombre");
        cant= bundle.getInt("cantidad");
        prec=bundle.getDouble("precio");
        fec=bundle.getString("fecha");

        nombre.setText(nom);
        cantidad.setText("Existencias: "+cant);
        precio.setText("Precio: "+prec);
        fecha.setText("Vencimiento: "+fec);

    }


    public void onclick(View view) {
        switch (view.getId()){
            case R.id.btnComprar:
                if (spinerCant.getSelectedItemPosition()!=0) {
                    if (!nomcliente.getText().toString().isEmpty() | !nitCliente.getText().toString().isEmpty()) {
                        cant2 = spinerCant.getSelectedItem().toString();
                        cant3 = Integer.parseInt(cant2);
                        Bundle bundle2 = getIntent().getExtras();
                        cant = bundle2.getInt("cantidad");
                        id = bundle2.getInt("id");
                        nom = bundle2.getString("nombre");
                        prec = bundle2.getDouble("precio");
                        fec = bundle2.getString("fecha");


                        if (cant >= cant3) {
                            Bundle bundle = getIntent().getExtras();
                            prec = bundle.getDouble("precio");
                            nomc = nomcliente.getText().toString();
                            nitc = nitCliente.getText().toString();

                            AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
                            dialogo.setTitle("Confirmacion de Compra");
                            dialogo.setMessage("Nombre: " + nomc + "\n Nit: " + nitc + "\n TOTAL: " + prec);
                            dialogo.setCancelable(false);
                            dialogo.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    int resta = cant - cant3;
                                    modificar.Modificarclase(getApplicationContext(), id, nom, resta, prec, fec);
                                    Intent inicio = new Intent(getApplicationContext(), MainActivity.class);
                                    inicio.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(inicio);
                                    Toast.makeText(CompraFinalActivity.this, "Compra Exitosa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialogo.setNegativeButton("CANCELAR", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent cancelado = new Intent(getApplicationContext(), CompraProductoActivity.class);
                                    cancelado.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(cancelado);
                                    Toast.makeText(CompraFinalActivity.this, "Compra Cancelada", Toast.LENGTH_SHORT).show();
                                }
                            });
                            dialogo.show();
                        } else {
                            Toast.makeText(this, "No hay Producto suficiente", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(this, "No ha seleccionado Cantidad", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.btnCancelar:
                Intent cancelado = new Intent(getApplicationContext(), CompraProductoActivity.class);
                cancelado.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(cancelado);
                break;
        }
    }
}
