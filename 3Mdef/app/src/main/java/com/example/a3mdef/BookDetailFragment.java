package com.example.a3mdef;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.a3mdef.modelos.Book;
import com.example.a3mdef.modelos.BookInfo;

/**
 * Fragmento que muestra los detalles de un libro seleccionado, como su título, autor, descripción,
 * categoría, fecha de publicación, calificación y una imagen de portada.
 */
public class BookDetailFragment extends Fragment {

    private ImageView bookCoverImageView;
    private TextView bookTitleTextView;
    private TextView bookAuthorsTextView;
    private TextView bookCategoryTextView;
    private TextView bookDescriptionTextView;
    private TextView bookPublishedDateTextView;
    private TextView bookRatingTextView;
    private TextView bookInfoLinkTextView;

    /**
     * Se ejecuta cuando se crea la vista del fragmento.
     * Aquí se infla el layout del fragmento y se configuran los argumentos pasados al fragmento.
     * @param inflater El objeto LayoutInflater para inflar la vista.
     * @param container El contenedor en el que se colocará la vista.
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     * @return La vista inflada que representa el contenido del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout del fragmento
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        // Verificar si se han pasado argumentos al fragmento
        if (getArguments() != null) {
            // Obtener el objeto Book pasado como argumento
            Book book = (Book) getArguments().getSerializable("book");
            if (book != null) {
                // Cargar los detalles del libro en el fragmento
            }
        }
        return view;
    }

    /**
     * Se ejecuta después de que la vista del fragmento ha sido creada.
     * Aquí se inicializan las vistas y se llenan con los detalles del libro.
     * @param view La vista creada en onCreateView().
     * @param savedInstanceState El estado guardado de la instancia, si está disponible.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Inicialización de las vistas
        bookCoverImageView = view.findViewById(R.id.bookCoverImageView);
        bookTitleTextView = view.findViewById(R.id.bookTitleTextView);
        bookAuthorsTextView = view.findViewById(R.id.bookAuthorsTextView);
        bookCategoryTextView = view.findViewById(R.id.bookCategoryTextView);
        bookDescriptionTextView = view.findViewById(R.id.bookDescriptionTextView);
        bookPublishedDateTextView = view.findViewById(R.id.bookPublishedDateTextView);
        bookInfoLinkTextView = view.findViewById(R.id.bookInfoLinkTextView);

        // Verificar si se pasaron argumentos al fragmento
        if (getArguments() != null) {
            // Recuperar el libro que fue pasado como argumento
            Book book = (Book) getArguments().getSerializable("book");

            // Si el libro no es nulo, cargar sus detalles
            if (book != null) {
                BookInfo bookInfo = book.getVolumeInfo();

                // Asegurarse de que el objeto bookInfo no sea nulo antes de intentar acceder a sus datos
                if (bookTitleTextView != null) {
                    // Establecer el título del libro en el TextView correspondiente
                    bookTitleTextView.setText(bookInfo.getTitle());
                }

                if (bookAuthorsTextView != null && bookInfo.getAuthors() != null) {
                    // Establecer los autores del libro en el TextView correspondiente
                    bookAuthorsTextView.setText(String.join(", ", bookInfo.getAuthors()));
                }

                if (bookCategoryTextView != null && bookInfo.getCategories() != null) {
                    // Establecer las categorías del libro en el TextView correspondiente
                    bookCategoryTextView.setText(String.join(", ", bookInfo.getCategories()));
                }

                if (bookDescriptionTextView != null && bookInfo.getDescription() != null) {
                    // Establecer la descripción del libro en el TextView correspondiente
                    bookDescriptionTextView.setText(bookInfo.getDescription());
                }

                if (bookPublishedDateTextView != null && bookInfo.getPublishedDate() != null) {
                    // Establecer la fecha de publicación del libro en el TextView correspondiente
                    bookPublishedDateTextView.setText(bookInfo.getPublishedDate());
                }

                // Enlace para obtener más información sobre el libro
                if (bookInfoLinkTextView != null && bookInfo.getInfoLink() != null) {
                    bookInfoLinkTextView.setText("Más información");
                    bookInfoLinkTextView.setOnClickListener(v -> {
                        // Abrir el enlace del libro en un navegador
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(bookInfo.getInfoLink()));
                        startActivity(intent);
                    });
                }

                // Cargar la imagen del libro usando Glide
                if (bookCoverImageView != null) {
                    BookInfo.ImageLinks imageLinks = bookInfo.getImageLinks();
                    if (imageLinks != null) {
                        // Construir la URL de la imagen usando el ID del libro
                        String bookImgId = book.getId();
                        String imageUrl = "https://books.google.com/books/publisher/content/images/frontcover/" + bookImgId + "?fife=w400-h600&source=gbs_api";

                        // Cargar la imagen en el ImageView usando Glide
                        Glide.with(requireContext())
                                .load(imageUrl)
                                .into(bookCoverImageView);
                    } else {
                        Log.d("BookDetail", "No image links available");
                    }
                }
            } else {
                // Si no se pasó un libro válido al fragmento, mostrar un error en los logs
                Log.e("BookDetailFragment", "El libro no se pasó correctamente al fragmento.");
            }
        } else {
            // Si no se encontraron argumentos, mostrar un error en los logs
            Log.e("BookDetailFragment", "No se encontraron argumentos.");
        }
    }
}
