package id.keyta.testad;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.keyta.testad.adapter.ItemAdapter;
import id.keyta.testad.model.ItemRequest;
import id.keyta.testad.model.ItemResponse;
import id.keyta.testad.model.MessageResponse;
import id.keyta.testad.rest.ApiClient;
import id.keyta.testad.rest.ApiInterface;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.listItem) RecyclerView listItem;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.root_item1) LinearLayout root_item1;
    @BindView(R.id.root_item2) LinearLayout root_item2;
    @BindView(R.id.txtSelectedItem) TextView txtSelectedItem;
    @BindView(R.id.btnSimpan)
    Button btnSimpan;

    ItemAdapter adapter;
    List<ItemResponse> itemResponses = new ArrayList<>();
    ItemResponse itemResponse = new ItemResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setupToolbar();
        setupAdapter();
        setupData();

    }

    void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Item");
    }

    void setupAdapter(){
        adapter = new ItemAdapter(itemResponses);

        listItem.setHasFixedSize(true);
        listItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        listItem.setAdapter(adapter);
    }

    void setupData(){
        ApiClient.getClient().getListData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<ItemResponse>>() {
                @Override
                public void onSubscribe(@NonNull Disposable d) {

                }

                @Override
                public void onNext(@NonNull List<ItemResponse> responses) {
                    if (!responses.isEmpty()){
                        itemResponses = responses;
                        adapter.addAll(itemResponses);
                    }

                }

                @Override
                public void onError(@NonNull Throwable e) {
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onComplete() {

                }
            });
    }

    @OnClick(R.id.btnSimpan)
    void saveData(){
        if (itemResponse.getId()==null){
            Type type = new TypeToken<List<ItemResponse>>() {}.getType();
            String json = new Gson().toJson(itemResponses, type);
            startActivityForResult(DetailItemActivity.newInstance(this, json), 1);
        }else{
            ApiClient.getClient().kirimData(new ItemRequest(String.valueOf(itemResponse.getId()), itemResponse.getName()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MessageResponse>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull MessageResponse messageResponse) {
                        Toast.makeText(MainActivity.this, messageResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
            if (resultCode == RESULT_OK){
                if (data.getExtras()!=null) {
                    root_item2.setVisibility(View.VISIBLE);
                    root_item1.setVisibility(View.GONE);
                    Bundle bundle = new Bundle(data.getExtras());
                    Type type = new TypeToken<ItemResponse>() {
                    }.getType();
                    itemResponse = new Gson().fromJson(bundle.getString("ITEM"), type);
                    txtSelectedItem.setText(itemResponse.getName());
                    btnSimpan.setText("Kirim");
                }
            }
        }
    }
}