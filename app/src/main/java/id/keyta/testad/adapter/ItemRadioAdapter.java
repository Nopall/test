package id.keyta.testad.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.keyta.testad.R;
import id.keyta.testad.model.ItemResponse;

public class ItemRadioAdapter extends RecyclerView.Adapter<ItemRadioAdapter.ViewHolder> {

    List<ItemResponse> list;
    Listener listener;
    private int lastCheckedPosition = -1;

    public ItemRadioAdapter(List<ItemResponse> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_radio_item_layout,
                parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ItemResponse itemResponse = list.get(position);

        holder.text.setText(itemResponse.getName());
        holder.text.setChecked(position == lastCheckedPosition);

        holder.text.setOnClickListener(v -> {
            lastCheckedPosition = position;
            listener.onClick(itemResponse);
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textRadio)
        AppCompatRadioButton text;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public void addAll(List<ItemResponse> list){
        if(this.list.size() > 0)
            this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public interface Listener {
        void onClick(ItemResponse menu);
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }
}


