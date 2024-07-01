package com.example.investool.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investool.Logic.Cryptocurrency;
import com.example.investool.R;

import java.util.List;

public class CryptocurrencyAdapter extends RecyclerView.Adapter<CryptocurrencyAdapter.CryptocurrencyViewHolder> {
    private Context context;
    private List<Cryptocurrency> cryptocurrencyList;

    public CryptocurrencyAdapter(Context context, List<Cryptocurrency> cryptocurrencyList) {
        this.context = context;
        this.cryptocurrencyList = cryptocurrencyList;
    }

    @NonNull
    @Override
    public CryptocurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cryptocurrency, parent, false);
        return new CryptocurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CryptocurrencyViewHolder holder, int position) {
        Cryptocurrency cryptocurrency = cryptocurrencyList.get(position);
        holder.bind(cryptocurrency);
    }

    @Override
    public int getItemCount() {
        return cryptocurrencyList.size();
    }

    public class CryptocurrencyViewHolder extends RecyclerView.ViewHolder {
        boolean decreasedChange = false;
        View pointer_green, pointer_red, intradayArrowUp, intradayArrowDown, intrahourArrowUp,intrahourArrowDown, intraweekArrowUp, intraweekArrowDown;
        TextView nameTextView, priceTextView, intradayChangeTextView, intrahourChangeTextView, intraweekChangeTextView;

        public CryptocurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.item_crypto_name);
            priceTextView = itemView.findViewById(R.id.item_crypto_intraday_price);
            intradayChangeTextView = itemView.findViewById(R.id.item_crypto_intraday);
            intrahourChangeTextView = itemView.findViewById(R.id.item_crypto_intrahour);
            intraweekChangeTextView = itemView.findViewById(R.id.item_crypto_intraweek);
            intradayArrowUp = itemView.findViewById(R.id.item_crypto_day_arrow_up);
            intradayArrowDown = itemView.findViewById(R.id.item_crypto_day_arrow_down);
            intrahourArrowUp = itemView.findViewById(R.id.item_crypto_hour_arrow_up);
            intrahourArrowDown = itemView.findViewById(R.id.item_crypto_hour_arrow_down);
            intraweekArrowUp = itemView.findViewById(R.id.item_crypto_week_arrow_up);
            intraweekArrowDown = itemView.findViewById(R.id.item_crypto_week_arrow_down);
            pointer_green = itemView.findViewById(R.id.item_crypto_pointer_green);
            pointer_red = itemView.findViewById(R.id.item_crypto_pointer_red);
        }

        void bind(Cryptocurrency cryptocurrency) {
            nameTextView.setText(cryptocurrency.getName());
            priceTextView.setText("$" + cryptocurrency.getPrice());
            bindChange(cryptocurrency, cryptocurrency.getIntradayChange(), intradayChangeTextView, intradayArrowUp, intradayArrowDown);
            bindChange(cryptocurrency, cryptocurrency.getIntrahourChange(), intrahourChangeTextView, intrahourArrowUp, intrahourArrowDown);
            bindChange(cryptocurrency, cryptocurrency.getIntraweekChange(), intraweekChangeTextView, intraweekArrowUp, intraweekArrowDown);
        }

        private void bindChange(Cryptocurrency cryptocurrency, double change, TextView changeTextView, View arrowUp, View arrowDown) {
            if (change < 0) {
                changeTextView.setTextColor(ContextCompat.getColor(context, R.color.text_red));
                arrowUp.setVisibility(View.INVISIBLE);
                arrowDown.setVisibility(View.VISIBLE);
            } else {
                changeTextView.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                arrowDown.setVisibility(View.INVISIBLE);
                arrowUp.setVisibility(View.VISIBLE);
            }
            updatePointerVisibility(cryptocurrency);
            changeTextView.setText(String.format("%.2f%%", change));
        }

        private void updatePointerVisibility(Cryptocurrency cryptocurrency) {
            boolean decreasedChange = cryptocurrency.getIntradayChange() < 0 || cryptocurrency.getIntrahourChange() < 0 || cryptocurrency.getIntraweekChange() < 0;
            if (decreasedChange) {
                pointer_green.setVisibility(View.INVISIBLE);
                pointer_red.setVisibility(View.VISIBLE);
            } else {
                pointer_green.setVisibility(View.VISIBLE);
                pointer_red.setVisibility(View.INVISIBLE);
            }
        }
    }
}