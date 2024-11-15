package com.example.lab6;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class UsuariosFragment extends Fragment {
    private RecyclerView recyclerView;
    private UsuarioAdapter usuarioAdapter;
    private List<Usuario> usuarios;
    private FirebaseFirestore db;
    private boolean mostrarHabilitados; // Variable para definir si muestra habilitados o baneados

    public UsuariosFragment(boolean mostrarHabilitados) {
        this.mostrarHabilitados = mostrarHabilitados;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_usuarios, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewUsuarios);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        usuarios = new ArrayList<>();
        usuarioAdapter = new UsuarioAdapter(usuarios, getContext());
        recyclerView.setAdapter(usuarioAdapter);

        db = FirebaseFirestore.getInstance();
        cargarUsuarios();
        cargarUsuariosNoHabilitados();
        return view;
    }

    private void cargarUsuarios() {
        db.collection("Usuarios")
                .whereEqualTo("habilitado", mostrarHabilitados)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        usuarios.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Usuario usuario = document.toObject(Usuario.class);
                            usuarios.add(usuario);
                        }
                        usuarioAdapter.notifyDataSetChanged();
                        Log.d("Firestore", "Usuarios cargados: " + usuarios.size());
                    } else {
                        // no me salen los usuarios pipipi
                        Toast.makeText(getContext(), "Error al cargar usuarios", Toast.LENGTH_SHORT).show();
                        Log.e("Firestore", "Error al cargar usuarios", task.getException());
                    }
                });
    }

    private void cargarUsuariosNoHabilitados() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Usuarios")
                .whereEqualTo("habilitado", false) // Filtra para usuarios no habilitados
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        usuarios.clear();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Usuario usuario = document.toObject(Usuario.class);
                            usuarios.add(usuario);
                        }
                        usuarioAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "Error al cargar usuarios no habilitados", Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
