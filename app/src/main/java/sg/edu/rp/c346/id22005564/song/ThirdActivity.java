package sg.edu.rp.c346.id22005564.song;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText editTitle, editGenre, editYear, editStars;
    Button updateButton, deleteButton;
    DBHelper dbHelper;
    Movies selectedMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        dbHelper = new DBHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editGenre = findViewById(R.id.editGenre);
        editYear = findViewById(R.id.editYear);
        Spinner spinnerRating = findViewById(R.id.spinnerRating);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        selectedMovie = (Movies) intent.getSerializableExtra("movie");

        editTitle.setText(selectedMovie.getTitle());
        editGenre.setText(selectedMovie.getGenre());
        editYear.setText(String.valueOf(selectedMovie.getYear()));

        int selectedRating = intent.getIntExtra("rating", 0); // Get the selected rating from the intent extras
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.movie_ratings, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRating.setAdapter(spinnerAdapter);


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String genre = editGenre.getText().toString().trim();
                int year = Integer.parseInt(editYear.getText().toString().trim());
                int rating = spinnerRating.getSelectedItemPosition() + 1; // +1 because the array index starts from 0
                selectedMovie.setRating(rating);

                selectedMovie.setTitle(title);
                selectedMovie.setGenre(genre);
                selectedMovie.setYear(year);
                selectedMovie.setRating(rating);

                dbHelper.updateMovie(selectedMovie);

                Toast.makeText(ThirdActivity.this, "Movie updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        });



        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThirdActivity.this);
                builder.setTitle("Danger");
                builder.setMessage("Are you sure you want to delete the movie " + selectedMovie.getTitle() + "?");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbHelper.deleteMovie(selectedMovie.getId());
                        Toast.makeText(ThirdActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AlertDialog.Builder dcBuilder = new AlertDialog.Builder(ThirdActivity.this);
                        dcBuilder.setTitle("Danger");
                        dcBuilder.setMessage("Are you sure you want discard the changes?");
                        dcBuilder.setPositiveButton("Discard", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        dcBuilder.setNegativeButton("Do not discard", null);
                        AlertDialog discardChangesDialog = dcBuilder.create();
                        discardChangesDialog.show();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }}

