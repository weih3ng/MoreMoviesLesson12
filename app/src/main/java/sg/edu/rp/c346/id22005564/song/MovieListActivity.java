package sg.edu.rp.c346.id22005564.song;



import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    ListView listView;
    Spinner yearSpinner;
    Button pg13Button;
    CustomAdapter adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list);
        yearSpinner = findViewById(R.id.yearSpinner);
        pg13Button = findViewById(R.id.pg13Button);

        ArrayList<Movies> movieList = dbHelper.getAllMovies();
        adapter = new CustomAdapter(this, R.layout.row, movieList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movies clickedMovie = movieList.get(position);
                int selectedRating = clickedMovie.getRating(); // Get the selected rating from the clicked movie
                Intent intent = new Intent(MovieListActivity.this, ThirdActivity.class);
                intent.putExtra("movie", clickedMovie);
                intent.putExtra("rating", selectedRating); // Pass the selected rating as an extra
                startActivity(intent);
            }
        });


        pg13Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Movies> pg13List = dbHelper.getAllMoviesWithRating(DBHelper.RATING_PG13);
                adapter.clear();
                adapter.addAll(pg13List);
                adapter.notifyDataSetChanged();
            }
        });

        // Populate the Spinner with unique years from the movie list
        ArrayList<Integer> uniqueYears = dbHelper.getUniqueYears();
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uniqueYears);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedYear = (int) parent.getItemAtPosition(position);
                ArrayList<Movies> filteredList = dbHelper.getMoviesByYear(selectedYear);
                adapter.clear();
                adapter.addAll(filteredList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Reload the updated movie list from the database
        ArrayList<Movies> movieList = dbHelper.getAllMovies();
        adapter.clear();
        adapter.addAll(movieList);
        adapter.notifyDataSetChanged();
    }
}
