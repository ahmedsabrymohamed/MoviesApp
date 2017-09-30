package com.example.mine.popularmovies;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.ArrayList;

/**
 * this class is the customAdapter and viewHolder used to handle the clicks and view recycling
 * of the recycler view
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.GridViewHolder> {

    private final ArrayList<Movie> movies;
    private final  SetOncLickListener mListener;
    private final static String BaseURL="https://image.tmdb.org/t/p/w500";
    private Integer lastPage;

    GridAdapter(SetOncLickListener Listener){

        this.mListener=Listener;
        movies=new ArrayList<>();
        lastPage=10;

    }


    @Override
    public GridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View root=inflater.inflate(R.layout.grid_item,parent,false);
        return new GridViewHolder(root);
    }

    @Override
    public void onBindViewHolder(GridViewHolder holder, int position) {


        Uri uri=Uri.parse(BaseURL+movies.get(position).getPosterPath());

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(500, 500))
                .build();
        holder.movieImage.setController(
                Fresco.newDraweeControllerBuilder()
                        .setOldController(holder.movieImage.getController())
                        .setImageRequest(request)
                        .build());
        holder.title.setText(movies.get(position).getTitle());
        holder.date.setText(movies.get(position).getReleaseDate().substring(0,4));

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private void insertItem(Movie movie){

        movies.add(movie);
        notifyItemInserted(movies.size()-1);
    }
    public void refresh(){

        movies.clear();
        notifyDataSetChanged();


    }

    public class GridViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        final SimpleDraweeView movieImage;
        final TextView title ;
        final TextView date;
        GridViewHolder(View itemView) {
            super(itemView);

            movieImage=itemView.findViewById(R.id.movieDraweeView);
            title=itemView.findViewById(R.id.gridTitleTextView);
            date=itemView.findViewById(R.id.gridDateTextView);




            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            mListener.SetOnclick(movies.get(getAdapterPosition()));

        }
    }

    public void setMovies(ArrayList<Movie> movies) {
        if(movies!=null)
            for(Movie x:movies)
                insertItem(x);
    }

    public Integer getLastPage() {
        return lastPage;
    }

    public void setLastPage(Integer lastPage) {
        this.lastPage = lastPage;
    }

    public interface SetOncLickListener{
        void SetOnclick(Movie movie);
    }
}
