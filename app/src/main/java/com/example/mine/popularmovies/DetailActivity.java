package com.example.mine.popularmovies;

import android.content.Intent;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class DetailActivity extends AppCompatActivity {

    Movie movie=null;
    Toast mToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=intent.getExtras();
            if(bundle!=null){

                movie=bundle.getParcelable("Movie");
            }
        }

    }
}
