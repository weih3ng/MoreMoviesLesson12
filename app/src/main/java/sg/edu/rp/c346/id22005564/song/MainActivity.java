package sg.edu.rp.c346.id22005564.song;




import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTitle, editGenre, editYear;
    Spinner spinner;
    Button insertButton, showListButton;
    MovieDBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitle = findViewById(R.id.editMovie);
        editGenre = findViewById(R.id.editGenre);
        editYear = findViewById(R.id.editDate);
        spinner = findViewById(R.id.spinner);

        insertButton = findViewById(R.id.button);
        showListButton = findViewById(R.id.button2);

        dbHelper = new MovieDBHelper(this);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                this, R.array.movie_ratings, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String genre = editGenre.getText().toString().trim();
                String yearString = editYear.getText().toString().trim();

                if (title.isEmpty() || genre.isEmpty() || yearString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearString);
                int rating = getSelectedRating();

                dbHelper.insertMovie(title, genre, year, rating);
                Toast.makeText(MainActivity.this, "Movie inserted", Toast.LENGTH_SHORT).show();

                clearFields();
            }
        });

        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MovieListActivity.class);
                startActivity(intent);
            }
        });
    }

    private int getSelectedRating() {
        String selectedRatingString = spinner.getSelectedItem().toString();
        // Convert the selected rating string to the corresponding image resource ID
        switch (selectedRatingString) {
            case "G":
                return R.drawable.rating_g;
            case "PG":
                return R.drawable.rating_pg;
            case "PG13":
                return R.drawable.rating_pg13;
            case "NC16":
                return R.drawable.rating_nc16;
            case "M18":
                return R.drawable.rating_m18;
            case "R21":
                return R.drawable.rating_r21;
            default:
                return 0; // Default image resource if no match is found
        }
    }

    private void clearFields() {
        editTitle.setText("");
        editGenre.setText("");
        editYear.setText("");
    }
}




