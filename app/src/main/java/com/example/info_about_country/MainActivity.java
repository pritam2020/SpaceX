package com.example.info_about_country;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        url = "https://restcountries.eu/rest/v2/";
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
        countriesService service = retrofit.create(countriesService.class);
        Call<List<Root>> call = service.getinfo();
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.code() == 200) {
                    list = response.body();
                    final List<entity> entityList= rootToEntity(response.body());


                    adapter = new MyRecyclerViewAdapter(MainActivity.this, list,false);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);


                   class SaveTask extends AsyncTask<List<entity>, Void, Void> {


                       @Override
                        protected Void doInBackground(List<entity>... ent) {


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
            entity.setAlpha2Code(root.alpha2Code);
            entity.setAlpha3Code(root.alpha3Code);
            entity.setAltSpellings(root.altSpellings);
            entity.setArea(root.area);
            entity.setBorders(root.borders);
            entity.setCallingCodes(root.callingCodes);
            entity.setCapital(root.capital);
            entity.setCioc(root.cioc);
            entity.setCurrencies(root.currencies);
            entity.setDemonym(root.demonym);
            entity.setGini(root.gini);
            entity.setLanguages(root.languages);
            entity.setLatlng(root.latlng);
            entity.setName(root.name);
            entity.setNativeName(root.nativeName);
            entity.setNumericCode(root.numericCode);
            entity.setRegion(root.region);
            entity.setSubregion(root.subregion);
            entity.setPopulation(root.population);
            entity.setTimezones(root.timezones);
            entity.setTopLevelDomain(root.topLevelDomain);
            entity.setTranslations(root.translations);
            entity.setRegionalBlocs(root.regionalBlocs);
            entity.setFlag(root.flag);
            entityList.add(entity);
        }
        return entityList;

    }

    private void caching() {
        progressBar.setVisibility(View.VISIBLE);
        Log.i(TAG, "getTasks: no internet connection");
        Toast.makeText(MainActivity.this,"No Connection , Images will not be loaded",Toast.LENGTH_SHORT).show();
        class GetTasks extends AsyncTask<Void, Void, List<Root>> {

            @Override
            protected List<Root> doInBackground(Void... voids) {

                    DatabaseClient databaseClient = DatabaseClient.getInstance(getApplicationContext());
                    CountryDatabase database=databaseClient.getDatabase();
                try {
                    return EntityToRoot(database.countryDao().getAll());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i(TAG, " " + e);
                    return EntityToRoot(null);

                }


            }

            @Override
            protected void onPostExecute(List<Root> list) {
                super.onPostExecute(list);
                if(list!=null) {
                    adapter = new MyRecyclerViewAdapter(MainActivity.this, list,true);
                    recyclerView.setAdapter(adapter);
                    progressBar.setVisibility(View.INVISIBLE);
                }else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "unable to fetch data from database", Toast.LENGTH_SHORT).show();
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
                root.alpha2Code = entity1.getAlpha2Code();
                root.alpha3Code = entity1.getAlpha3Code();
                root.altSpellings = entity1.getAltSpellings();
                root.area = entity1.getArea();
                root.borders = entity1.getBorders();
                root.callingCodes = entity1.getCallingCodes();
                root.capital = entity1.getCapital();
                root.cioc = entity1.getCioc();
                root.currencies = entity1.getCurrencies();
                root.demonym = entity1.getDemonym();
                root.gini = entity1.getGini();
                root.languages = entity1.getLanguages();
                root.latlng = entity1.getLatlng();
                root.name = entity1.getName();
                root.nativeName = entity1.getNativeName();
                root.numericCode = entity1.getNumericCode();
                root.region = entity1.getRegion();
                root.subregion = entity1.getSubregion();
                root.population = entity1.getPopulation();
                root.timezones = entity1.getTimezones();
                root.topLevelDomain = entity1.getTopLevelDomain();
                root.translations = entity1.getTranslations();
                root.regionalBlocs = entity1.getRegionalBlocs();
                entityList.add(root);
            }

        }
        return entityList;

    }
}