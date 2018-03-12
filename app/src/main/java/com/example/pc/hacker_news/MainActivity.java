package com.example.pc.hacker_news;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.oc.hnapp.android.HNQueryTask;

public class MainActivity extends AppCompatActivity {
    private HNQueryTask _task=null;
    private int _page = 0;

    private HNArticlesAdapter _adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        _adapter = new HNArticlesAdapter(this);
        recyclerView.setAdapter(_adapter);

        loadNext();

        final ProgressBar progress = (ProgressBar) findViewById(R.id.progress);
        _adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                progress.setVisibility(View.GONE);
            }
        });

    }

    public void loadNext() {
        if (_task != null && _task.getStatus() != AsyncTask.Status.FINISHED)
            return ;

        _task = new HNQueryTask(_adapter, 80, ++_page);
        _task.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _task.cancel(true);
    }
}
