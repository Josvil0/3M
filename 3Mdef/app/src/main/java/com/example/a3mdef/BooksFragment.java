package com.example.a3mdef;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.appcompat.widget.SearchView;  // Correcto, debe ser de androidx.appcompat.widget
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3mdef.modelos.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Fragmento que muestra una lista de libros y permite buscar libros a través de un campo de búsqueda.
 * Al seleccionar un libro, se muestra su detalle en un fragmento separado.
 */
public class BooksFragment extends Fragment {

    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private BookViewModel bookViewModel;

    /**
     * Se ejecuta cuando se crea el fragmento.
     * Asegura que el fragmento no se destruya al cambiar de fragmento.
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);  // Asegura que el fragmento no se destruya al cambiar de fragmento
    }

    /**
     * Se ejecuta cuando se crea la vista del fragmento.
     * Infla el layout y prepara las vistas para mostrar la lista de libros y el campo de búsqueda.
     * @param inflater El objeto LayoutInflater para inflar la vista.
     * @param container El contenedor en el que se colocará la vista.
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     * @return La vista inflada que representa el contenido del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    /**
     * Se ejecuta después de que la vista ha sido creada.
     * Inicializa las vistas, el adaptador de la lista de libros y configura la búsqueda.
     * También observa los cambios en la lista de libros para actualizar la vista.
     * @param view La vista creada en onCreateView().
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicializar el ViewModel que contiene la lógica de los libros
        bookViewModel = new ViewModelProvider(requireActivity()).get(BookViewModel.class);

        // Inicializar RecyclerView y su adaptador
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializar el adaptador vacío solo una vez
        if (bookAdapter == null) {
            bookAdapter = new BookAdapter();
            recyclerView.setAdapter(bookAdapter);
        }

        // Observar los cambios en la lista de libros desde el ViewModel
        bookViewModel.getBooks().observe(getViewLifecycleOwner(), books -> {
            if (books != null && !books.isEmpty()) {
                Log.d("BooksFragment", "Libros obtenidos: " + books.size());
                bookAdapter.setBooks(books);  // Actualiza la lista de libros
            } else {
                Log.d("BooksFragment", "No se encontraron libros.");
                bookAdapter.setBooks(new ArrayList<>());  // Si no hay libros, pasar una lista vacía
                Toast.makeText(getContext(), "No se encontraron libros", Toast.LENGTH_SHORT).show();
            }
        });

        // Configuración del click sobre un libro
        bookAdapter.setOnItemClickListener(book -> {
            // Llamar al método para mostrar el detalle del libro
            showBookDetailFragment(book);
        });

        // Configuración del SearchView para buscar libros
        SearchView searchView = view.findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Llamar al método de búsqueda en el ViewModel con el texto ingresado
                Log.d("BooksFragment", "Buscando libros con: " + query);
                bookViewModel.searchBooks(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    /**
     * Muestra un fragmento con los detalles del libro seleccionado.
     * @param book El libro que se seleccionó para mostrar su detalle.
     */
    private void showBookDetailFragment(Book book) {
        // Crear un nuevo fragmento para mostrar los detalles del libro
        BookDetailFragment bookDetailFragment = new BookDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("book", book);  // Pasar el libro como argumento al fragmento
        bookDetailFragment.setArguments(bundle);

        // Realizar la transacción para mostrar el fragmento
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fragmentContainer, bookDetailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /**
     * Se ejecuta cuando el fragmento es visible.
     * Aquí se actualiza la lista de libros si ha habido cambios en el ViewModel.
     */
    @Override
    public void onResume() {
        super.onResume();
        // Verificar si la lista de libros ha cambiado y actualizar el adaptador
        List<Book> currentBooks = bookViewModel.getBooks().getValue();
        if (currentBooks != null) {
            bookAdapter.setBooks(currentBooks);  // Asegúrate de notificar el cambio
            bookAdapter.notifyDataSetChanged();  // Forzar actualización visual
        }
    }
}
