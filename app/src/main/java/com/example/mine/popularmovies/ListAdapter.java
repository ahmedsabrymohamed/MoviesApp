package com.example.mine.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.gestures.GestureDetector;

import java.util.List;

/**
 *
 */

public class ListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private String itemType;
    private List<Trailer> trailers;
    private List<Review> reviews;
    private SetOncLickListener mListener;
    public ListAdapter(String itemType) {
        this.itemType=itemType;
    }
    public void setTrailers(List<Trailer> trailers){
        this.trailers=trailers;
        this.notifyDataSetChanged();
    }
    public void setReviews(List<Review> reviews){
        this.reviews=reviews;
        this.notifyDataSetChanged();
    }
    public void setTrailerItemOncLickListener(SetOncLickListener Listener){

        this.mListener=Listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        if (itemType.equals("review")) {
            View root = inflater.inflate(  R.layout.review_item, parent, false);
            return new ReviewsListViewHolder(root);
        }
        else{
            View root = inflater.inflate(R.layout.trailer_item, parent, false);
            return new TrailersListViewHolder(root);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (itemType.equals("review")) {


            ReviewsListViewHolder viewHolder=(ReviewsListViewHolder) holder;
            viewHolder.content.setText(reviews.get(position).getContent());
            viewHolder.authorName.setText(reviews.get(position).getAuthor());
        }
        else{
            TrailersListViewHolder viewHolder=(TrailersListViewHolder) holder;
            viewHolder.trailerName.setText(trailers.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        switch (itemType){
            case "review":
                return (reviews!=null?reviews.size():0);

            case "trailer":
                return (trailers!=null?trailers.size():0);

        }
        return 0;
    };

    class ReviewsListViewHolder extends RecyclerView.ViewHolder{

        TextView authorName;
        TextView content;
        public ReviewsListViewHolder(View itemView) {

            super(itemView);
            authorName=itemView.findViewById(R.id.review_author);
            content=itemView.findViewById(R.id.review_content);


        }
    }
    class TrailersListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView trailerName;
        public TrailersListViewHolder(View itemView) {

            super(itemView);
            trailerName=itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            mListener.SetOnclick(trailers.get(getAdapterPosition()));
        }
    }
    public interface SetOncLickListener{
        void SetOnclick(Trailer trailer);
    }
}
