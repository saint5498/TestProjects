package com.example.currencyconverter.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.currencyconverter.R;
import com.example.currencyconverter.model.Currency;

import java.util.ArrayList;
import java.util.List;


public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    private final List<Currency> currencyList = new ArrayList<>();
    private final ListViewInterface listViewInterface;

    public CurrencyAdapter(ListViewInterface listViewInterface) {
        this.listViewInterface = listViewInterface;
    }

    class CurrencyViewHolder extends RecyclerView.ViewHolder {
        private final TextView charCodeView;
        private final TextView nameView;
        private final TextView valueView;
        private final TextView nominalView;

        public CurrencyViewHolder(View itemView) {
            super(itemView);

            charCodeView = itemView.findViewById(R.id.charCode);
            nameView = itemView.findViewById(R.id.name);
            valueView = itemView.findViewById(R.id.value);
            nominalView = itemView.findViewById(R.id.nominal);

            itemView.setOnClickListener(v -> listViewInterface.onItemClick(getAdapterPosition()));
        }

        public void bind(Currency currency) {
            charCodeView.setText(currency.getCharCode());
            nameView.setText(currency.getName());
            nominalView.setText("Номинал: " + currency.getNominal());
            valueView.setText(String.valueOf(currency.getValue()));
        }
    }

    public void setItems(List<Currency> currencies) {
        currencyList.addAll(currencies);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_item, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        holder.bind(currencyList.get(position));
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }
}
