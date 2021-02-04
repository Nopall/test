package id.keyta.testad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import id.keyta.testad.adapter.ItemRadioAdapter;
import id.keyta.testad.model.ItemResponse;

public class DetailItemActivity extends AppCompatActivity implements ItemRadioAdapter.Listener {
    @BindView(R.id.listItem)
    RecyclerView listItem;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ItemRadioAdapter adapter;
    List<ItemResponse> itemResponses = new ArrayList<>();
    ItemResponse itemResponseData = new ItemResponse();

    public static Intent newInstance(Context context, String itemResponses){
        Intent i = new Intent(context, DetailItemActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("KEY", itemResponses);
        i.putExtras(bundle);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_item);
        ButterKnife.bind(this);

        if (getIntent().getExtras()!=null){
            Bundle bundle = new Bundle(getIntent().getExtras());
            Type type = new TypeToken<List<ItemResponse>>() {
            }.getType();
            itemResponses = new Gson().fromJson(bundle.getString("KEY"), type);
        }
        setupToolbar();
        setupAdapter();
    }

    void setupToolbar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Daftar Item tersimpan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(view -> {
            finish();
        });
    }

    void setupAdapter(){
        adapter = new ItemRadioAdapter(itemResponses);

        listItem.setHasFixedSize(true);
        adapter.addListener(this);
        listItem.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listItem.setAdapter(adapter);
    }

    @Override
    public void onClick(ItemResponse menu) {
        itemResponseData = menu;
    }

    @OnClick(R.id.btnSimpan)
    void saveData(){
        if (itemResponseData == null){
            Toast.makeText(this, "Harap Pilih Item!", Toast.LENGTH_SHORT).show();
        }else{
            Type type = new TypeToken<ItemResponse>() {}.getType();
            String json = new Gson().toJson(itemResponseData, type);
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            bundle.putString("ITEM", json);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}