package com.example.Spacex;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RETROFIT TRANSACTION";
    List<Root> list;
    MyRecyclerViewAdapter adapter;
    String url;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    WebView webView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.delete,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.delete) {
            class SaveTask extends AsyncTask<Void, Void, String> {
                @Override
                protected String doInBackground(Void... ent) {

                    if (DatabaseClient.getInstance(getApplicationContext()).getDatabase().countryDao().getAll().size() != 0) {
                        try {
                            DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                                    .countryDao()
                                    .deleteAll();

                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.i(TAG, "doInBackground: " + e);
                        }
                        return "All data deleted from the database if there is connection the data will automatically be loaded in database";
                    } else
                        return "Data already deleted";
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    Toast.makeText(MainActivity.this, " " + s, Toast.LENGTH_LONG).show();
                }
            }
            SaveTask saveTask = new SaveTask();
            saveTask.execute();
            return true;
        } return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        url = "https://api.spacexdata.com/v4/";
        ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
        if(connected) {
            get_Data();
            findViewById(R.id.no_connection).setVisibility(View.INVISIBLE);
        }
        else {
            findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
            caching();
        }

        refreshLayout = findViewById(R.id.refreash);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ConnectivityManager cm = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo nInfo = cm.getActiveNetworkInfo();
                boolean connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
                if(connected) {
                    get_Data();
                    findViewById(R.id.no_connection).setVisibility(View.INVISIBLE);
                }
                else {
                    findViewById(R.id.no_connection).setVisibility(View.VISIBLE);
                    caching();
                }
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void get_Data() {
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "get_Data: there is internet , starting retrofit");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SpacexService service = retrofit.create(SpacexService.class);
        Call<List<Root>> call = service.getinfo();
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.code() == 200) {
                    list = response.body();
                    final List<entity> entityList= rootToEntity(response.body());


                    adapter = new MyRecyclerViewAdapter(MainActivity.this, list,false,webView);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);


                   class SaveTask extends AsyncTask<List<entity>, Void, Void> {


                       @Override
                        protected Void doInBackground(List<entity>... ent) {

                           try {
                               if(DatabaseClient.getInstance(getApplicationContext()).getDatabase().countryDao().getAll().size()==0){
                                   for(int i=0;i<entityList.size();i++) {
                                       try {
                                           DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                                                   .countryDao()
                                                   .insert(entityList.get(i));
                                       } catch (Exception e) {
                                           e.printStackTrace();
                                           Log.i(TAG, "doInBackground: "+e);
                                       }

                                   }
                               }
                           } catch (Exception e) {
                               e.printStackTrace();
                               Log.i(TAG, "doInBackground: "+e);
                           }


                           for(int i=0;i<entityList.size();i++) {
                               try {
                                   DatabaseClient.getInstance(getApplicationContext()).getDatabase()
                                           .countryDao()
                                           .insert(entityList.get(i));
                               } catch (Exception e) {
                                   e.printStackTrace();
                                   Log.i(TAG, "doInBackground: "+e);
                               }

                           }
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                            Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
                        }


                   }
                    SaveTask saveTask=new SaveTask();
                       saveTask.execute(entityList);


                } else {
                    caching();
                    Toast.makeText(MainActivity.this,"Unable to load Data ",Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                    Log.i(TAG, " " + response.code());
                    Log.i(TAG, " " + "loading data from database");
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                caching();
                progressBar.setVisibility(View.INVISIBLE);
                Toast.makeText(MainActivity.this,"Unable to load Data",Toast.LENGTH_SHORT).show();
                Log.i(TAG, " " + t);
                Log.i(TAG, " " + "loading data from database");

            }
        });
    }

    private List<entity> rootToEntity(List<Root> response) {
        List<entity> entityList=new ArrayList<>();
        for (Root root:response){
            entity entity=new entity();
            entity.setName(root.name);
            entity.setID(root.id);
            entity.setAgency(root.agency);
            entity.setLaunches(root.launches);
            entity.setImage(root.image);
            entity.setWikipedia(root.wikipedia);
            entity.setStatus(root.status);
            entityList.add(entity);
        }
        return entityList;

    }

    private void caching() {
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "getTasks: no internet connection");
        Toast.makeText(MainActivity.this,"No Connection , Images will not be loaded properly",Toast.LENGTH_SHORT).show();
        class GetTasks extends AsyncTask<Void, Void, List<Root>> {

            @Override
            protected List<Root> doInBackground(Void... voids) {

                    DatabaseClient databaseClient = DatabaseClient.getInstance(getApplicationContext());
                    SpacexDatabase database=databaseClient.getDatabase();
                try {
                    if (database.countryDao().getAll().size()!=0) {
                        return EntityToRoot(database.countryDao().getAll());
                    }else return EntityToRoot(new ArrayList<entity>());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, " " + e);
                    return EntityToRoot(new ArrayList<entity>());

                }


            }

            @Override
            protected void onPostExecute(List<Root> list) {
                super.onPostExecute(list);
                if(list.size()!=0) {
                    adapter = new MyRecyclerViewAdapter(MainActivity.this, list,true,webView);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new MyRecyclerViewAdapter(MainActivity.this, list,true,webView);
                    recyclerView.setAdapter(adapter);
                    Toast.makeText(MainActivity.this, "no data to fetch from database", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
    private List<Root> EntityToRoot(List<entity> entities) {
        List<Root> entityList = new ArrayList<>();
        if(entities!=null) {
            for (entity entity1 : entities) {
                Root root = new Root();
                root.agency=entity1.getAgency();
                root.id=entity1.getID();
                root.image=entity1.getImage();
                root.launches=entity1.getLaunches();
                root.name=entity1.getName();
                root.status=entity1.getStatus();
                root.wikipedia=entity1.getWikipedia();
                entityList.add(root);
            }

        }
        return entityList;

    }
}