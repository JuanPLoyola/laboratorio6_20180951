package com.example.lab6;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab6.Usuario;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.List;

public class UsuarioAdapter extends RecyclerView.Adapter<UsuarioAdapter.UsuarioViewHolder> {
    private List<Usuario> usuarios;
    private Context context;

    public UsuarioAdapter(List<Usuario> usuarios, Context context) {
        this.usuarios = usuarios;
        this.context = context;
    }

    @NonNull
    @Override
    public UsuarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_usuario, parent, false);
        return new UsuarioViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsuarioViewHolder holder, int position) {
        Usuario usuario = usuarios.get(position);

        holder.nombreTextView.setText(usuario.getApellido() + ", " + usuario.getNombre());
        holder.correoTextView.setText(usuario.getCorreo());
        holder.dniTextView.setText("DNI: " + usuario.getDni());
        holder.codigoTextView.setText("Código: " + usuario.getCodigoPucp());

        // Clic en la imagen para abrir DetalleUsuarioActivity
        holder.fotoImageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleUsuarioActivity.class);
            intent.putExtra("usuario", usuario); // Asegúrate de que Usuario implemente Serializable o Parcelable
            context.startActivity(intent);
        });

        // Implementar el click listener para editar
        holder.editarButton.setOnClickListener(v -> {
            // Aquí puedes añadir la funcionalidad para editar el usuario
        });

        // Implementar el click listener para eliminar
        holder.eliminarButton.setOnClickListener(v -> {
            eliminarUsuario(usuario);
        });
    }

    @Override
    public int getItemCount() {
        return usuarios.size();
    }

    public static class UsuarioViewHolder extends RecyclerView.ViewHolder {
        ImageView fotoImageView;
        TextView nombreTextView, correoTextView, dniTextView, codigoTextView;
        ImageButton editarButton, eliminarButton;

        public UsuarioViewHolder(@NonNull View itemView) {
            super(itemView);
            fotoImageView = itemView.findViewById(R.id.fotoImageView);
            nombreTextView = itemView.findViewById(R.id.nombreTextView);
            correoTextView = itemView.findViewById(R.id.correoTextView);
            dniTextView = itemView.findViewById(R.id.dniTextView);
            codigoTextView = itemView.findViewById(R.id.codigoTextView);
            editarButton = itemView.findViewById(R.id.editarButton);
            eliminarButton = itemView.findViewById(R.id.eliminarButton);
        }
    }

    private void eliminarUsuario(Usuario usuario) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("usuarios").document(usuario.getDni())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    usuarios.remove(usuario);
                    notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {

                });
    }


}
