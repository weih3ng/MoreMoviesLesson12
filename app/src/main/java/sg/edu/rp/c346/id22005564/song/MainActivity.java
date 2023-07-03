package sg.edu.rp.c346.id22005564.song;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editSong, editSinger, editDate;
    RadioButton radioButton, radioButton2, radioButton3, radioButton4, radioButton5;
    Button insertButton, showListButton;
    ListView listView;
    ArrayAdapter<Song> adapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editSong = findViewById(R.id.editSong);
        editSinger = findViewById(R.id.editSinger);
        editDate = findViewById(R.id.editDate);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        insertButton = findViewById(R.id.button);
        showListButton = findViewById(R.id.button2);
        listView = findViewById(R.id.lv);

        dbHelper = new DBHelper(this);

        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editSong.getText().toString().trim();
                String singers = editSinger.getText().toString().trim();
                String yearString = editDate.getText().toString().trim();

                if (title.isEmpty() || singers.isEmpty() || yearString.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int year = Integer.parseInt(yearString);
                int stars = getSelectedStars();

                dbHelper.insertSong(title, singers, year, stars);
                Toast.makeText(MainActivity.this, "Song inserted successfully", Toast.LENGTH_SHORT).show();

                clearFields();
            }
        });

        showListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Song> songList = dbHelper.getAllSongs();
                adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, songList);
                listView.setAdapter(adapter);
            }
        });
    }

    private int getSelectedStars() {
        if (radioButton.isChecked()) {
            return 1;
        } else if (radioButton2.isChecked()) {
            return 2;
        } else if (radioButton3.isChecked()) {
            return 3;
        } else if (radioButton4.isChecked()) {
            return 4;
        } else if (radioButton5.isChecked()) {
            return 5;
        } else {
            return 0; // No star selected
        }
    }

    private void clearFields() {
        editSong.setText("");
        editSinger.setText("");
        editDate.setText("");
        radioButton.setChecked(false);
        radioButton2.setChecked(false);
        radioButton3.setChecked(false);
        radioButton4.setChecked(false);
        radioButton5.setChecked(false);
    }
}

