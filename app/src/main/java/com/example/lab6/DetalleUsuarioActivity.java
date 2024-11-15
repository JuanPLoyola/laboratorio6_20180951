package com.example.lab6;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DetalleUsuarioActivity extends AppCompatActivity {
    private ImageView imgFotoUsuario;
    private TextView txtNombreApellido, txtCorreo, txtCodigo, txtDni, txtEstado;
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_usuario);

        // Referenciar vistas
        imgFotoUsuario = findViewById(R.id.imgFotoUsuario);
        txtNombreApellido = findViewById(R.id.txtNombreApellido);
        txtCorreo = findViewById(R.id.txtCorreo);
        txtCodigo = findViewById(R.id.txtCodigo);
        txtDni = findViewById(R.id.txtDni);
        txtEstado = findViewById(R.id.txtEstado);
        mapView = findViewById(R.id.mapView);

        // Obtener el usuario del Intent
        Usuario usuario = (Usuario) getIntent().getSerializableExtra("usuario");

        if (usuario != null) {
            // Configurar datos en las vistas
            txtNombreApellido.setText(usuario.getApellido() + ", " + usuario.getNombre());
            txtCorreo.setText("Correo: " + usuario.getCorreo());
            txtCodigo.setText("Código: " + usuario.getCodigoPucp());
            txtDni.setText("DNI: " + usuario.getDni());
            txtEstado.setText("Estado: " + (usuario.isHabilitado() ? "Habilitado" : "No habilitado"));

        }

        // Configurar el mapa
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(googleMap -> {
            LatLng lima = new LatLng(-12.0464, -77.0428); // Coordenadas de Lima
            googleMap.addMarker(new MarkerOptions().position(lima).title("Lima"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lima, 10));
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
