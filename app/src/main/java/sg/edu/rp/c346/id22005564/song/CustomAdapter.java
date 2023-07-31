package sg.edu.rp.c346.id22005564.song;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter<Movies> {

    private Context context;
    private int resource;
    private ArrayList<Movies> movieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movies> movieList) {
        super(context, resource, movieList);
        this.context = context;
        this.resource = resource;
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(resource, null);
        }

        Movies movie = movieList.get(position);

        ImageView movieImage = view.findViewById(R.id.imageView);
        TextView movieTitle = view.findViewById(R.id.movieTitle);
        TextView movieYear = view.findViewById(R.id.movieYear);

        TextView movieGenre = view.findViewById(R.id.movieGenre);

        movieImage.setImageResource(getRatingImageResource(movie.getRating()));
        movieTitle.setText(movie.getTitle());
        movieYear.setText("Release Year: " + movie.getYear());
        movieGenre.setText("Genre: " + movie.getGenre());

        return view;
    }

    private int getRatingImageResource(int rating) {
        if (rating == DBHelper.RATING_G) {
            return R.drawable.rating_g;
        } else if (rating == DBHelper.RATING_PG) {
            return R.drawable.rating_pg;
        } else if (rating == DBHelper.RATING_PG13) {
            return R.drawable.rating_pg13;
        } else if (rating == DBHelper.RATING_NC16) {
            return R.drawable.rating_nc16;
        } else if (rating == DBHelper.RATING_M18) {
            return R.drawable.rating_m18;
        } else if (rating == DBHelper.RATING_R21) {
            return R.drawable.rating_r21;
        } else {
            return R.drawable.download; // Replace this with your default rating image
        }
    }
}
