package sg.edu.rp.c346.id22005564.song;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ThirdActivity extends AppCompatActivity {

    EditText editTitle, editSingers, editYear, editStars;
    Button updateButton, deleteButton;
    DBHelper dbHelper;
    Song selectedSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        dbHelper = new DBHelper(this);

        editTitle = findViewById(R.id.editTitle);
        editSingers = findViewById(R.id.editSingers);
        editYear = findViewById(R.id.editYear);
        editStars = findViewById(R.id.editStars);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        Intent intent = getIntent();
        selectedSong = (Song) intent.getSerializableExtra("song");

        editTitle.setText(selectedSong.getTitle());
        editSingers.setText(selectedSong.getSingers());
        editYear.setText(String.valueOf(selectedSong.getYear()));
        editStars.setText(String.valueOf(selectedSong.getStars()));

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String singers = editSingers.getText().toString().trim();
                int year = Integer.parseInt(editYear.getText().toString().trim());
                int stars = Integer.parseInt(editStars.getText().toString().trim());

                selectedSong.setTitle(title);
                selectedSong.setSingers(singers);
                selectedSong.setYear(year);
                selectedSong.setStars(stars);

                dbHelper.updateSong(selectedSong);

                Toast.makeText(ThirdActivity.this, "Song updated", Toast.LENGTH_SHORT).show();
                finish();
            }

        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbHelper.deleteSong(selectedSong.getId());
                Toast.makeText(ThirdActivity.this, "Song deleted", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
