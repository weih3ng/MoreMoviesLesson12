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

public class SongListActivity extends AppCompatActivity {

    ListView listView;
    Spinner yearSpinner;
    Button fiveStarsButton;
    ArrayAdapter<Song> adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_list);

        dbHelper = new DBHelper(this);

        listView = findViewById(R.id.list);
        yearSpinner = findViewById(R.id.yearSpinner);
        fiveStarsButton = findViewById(R.id.fiveStarsButton);

        ArrayList<Song> songList = dbHelper.getAllSongs();
//        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, songList);
        adapter = new CustomAdapter(this, R.layout.row, songList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Song clickedSong = (Song) parent.getItemAtPosition(position);
                Intent intent = new Intent(SongListActivity.this, ThirdActivity.class);
                intent.putExtra("song", clickedSong);
                startActivity(intent);
            }
        });

        fiveStarsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Song> fiveStarsList = dbHelper.getAllSongsWithFiveStars();
                adapter.clear();
                adapter.addAll(fiveStarsList);
                adapter.notifyDataSetChanged();
            }
        });

        // Populate the Spinner with unique years from the song list
        ArrayList<Integer> uniqueYears = dbHelper.getUniqueYears();
        ArrayAdapter<Integer> yearAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, uniqueYears);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        yearSpinner.setAdapter(yearAdapter);

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int selectedYear = (int) parent.getItemAtPosition(position);
                ArrayList<Song> filteredList = dbHelper.getSongsByYear(selectedYear);
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
    protected void onResume() {
        super.onResume();
        // Reload the updated song list from the database
        ArrayList<Song> songList = dbHelper.getAllSongs();
        adapter.clear();
        adapter.addAll(songList);
        adapter.notifyDataSetChanged();
    }
}
