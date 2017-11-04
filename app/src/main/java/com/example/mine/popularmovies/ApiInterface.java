package com.example.mine.popularmovies;



import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

interface ApiInterface {
    @GET("{SHOW_TYPE}/{Category}")
    Call<MoviesResponse>getMoviesData(@Path("SHOW_TYPE")String showType, @Path("Category")String category
            , @Query("api_key") String apiKey,@Query("page") String pageNumber);

    @GET("{SHOW_TYPE}/{Movie_id}/images")
    Call<ImagesResponse>getMovieDetails(@Path("SHOW_TYPE")String showType,
                                        @Path("Movie_id")String id,
                                        @Query("api_key") String apiKey,
                                        @Query("language") String language,
                                        @Query("include_image_language") String imageLanguage);
    @GET("{SHOW_TYPE}/{Movie_id}/videos")
    Call<TrailerResponse>getMovieVideos(@Path("SHOW_TYPE")String showType,
                                       @Path("Movie_id")String id,
                                       @Query("api_key") String apiKey);
    @GET("{SHOW_TYPE}/{Movie_id}/reviews")
    Call<ReviewsResponse>getMovieReviews(@Path("SHOW_TYPE")String showType,
                                      @Path("Movie_id")String id,
                                      @Query("api_key") String apiKey);

}