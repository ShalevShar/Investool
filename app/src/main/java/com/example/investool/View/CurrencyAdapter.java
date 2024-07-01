package com.example.investool.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investool.Logic.Currency;
import com.example.investool.R;

import java.util.List;
import java.util.Set;

public class CurrencyAdapter extends RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder> {
    private static Context context;
    private List<Currency> currencyList;
    private static Set<Currency> favoriteItemListCurrency;

    public CurrencyAdapter(Context context, List<Currency> currencyList, Set<Currency> favoriteItemList) {
        this.context = context;
        this.currencyList = currencyList;
        this.favoriteItemListCurrency = favoriteItemList;
    }
    public interface OnItemClickListener {
        void onAddToFavoritesClicked(Currency currency);

        void onRemoveItemClicked(Currency currency);
    }

    private OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public int getCurrencyPosition(Currency currency) {
        return currencyList.indexOf(currency);
    }

    @NonNull
    @Override
    public CurrencyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
        return new CurrencyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CurrencyViewHolder holder, int position) {
        Currency currency = currencyList.get(position);
        holder.bind(currency);

        holder.item_currency_BTN_addFavorite.setOnClickListener(view -> {
            if (listener != null) {
                listener.onAddToFavoritesClicked(currency);
                holder.item_currency_BTN_addFavorite.setVisibility(View.INVISIBLE);
                holder.item_currency_BTN_removeFavorite.setVisibility(View.VISIBLE);
            }
        });

        if (favoriteItemListCurrency.contains(currency)) {
            holder.item_currency_BTN_addFavorite.setVisibility(View.INVISIBLE);
            holder.item_currency_BTN_removeFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.item_currency_BTN_addFavorite.setVisibility(View.VISIBLE);
            holder.item_currency_BTN_removeFavorite.setVisibility(View.INVISIBLE);
        }
    }
    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public static class CurrencyViewHolder extends RecyclerView.ViewHolder {
        View item_currency_pointer_green, item_currency_pointer_red, item_currency_intraday_percent_arrow_up, item_currency_intraday_percent_arrow_down;
        TextView item_currency_exchange, item_currency_intraday_bid, item_currency_intraday_ask, item_currency_intraday_change, item_currency_intraday_percentChange;
        public AppCompatImageButton item_currency_BTN_addFavorite, item_currency_BTN_removeFavorite;

        public CurrencyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_currency_exchange = itemView.findViewById(R.id.item_currency_exchange);
            item_currency_intraday_bid = itemView.findViewById(R.id.item_currency_intraday_bid);
            item_currency_intraday_ask = itemView.findViewById(R.id.item_currency_intraday_ask);
            item_currency_intraday_change = itemView.findViewById(R.id.item_currency_intraday_change);
            item_currency_intraday_percentChange = itemView.findViewById(R.id.item_currency_intraday_percentChange);
            item_currency_pointer_green = itemView.findViewById(R.id.item_currency_pointer_green);
            item_currency_pointer_red = itemView.findViewById(R.id.item_currency_pointer_red);
            item_currency_intraday_percent_arrow_up = itemView.findViewById(R.id.item_currency_intraday_percent_arrow_up);
            item_currency_intraday_percent_arrow_down = itemView.findViewById(R.id.item_currency_intraday_percent_arrow_down);
            item_currency_BTN_addFavorite = itemView.findViewById(R.id.item_currency_BTN_addFavorite);
            item_currency_BTN_removeFavorite = itemView.findViewById(R.id.item_currency_BTN_removeFavorite);
        }

        public void bind(Currency currency) {
            item_currency_exchange.setText(currency.getName());
            item_currency_intraday_bid.setText(currency.getFormattedIntradayBid());
            item_currency_intraday_ask.setText(currency.getFormattedIntradayAsk());
            item_currency_intraday_change.setText(currency.getFormattedIntradayChange());
            item_currency_intraday_percentChange.setText(currency.getFormattedIntradayPercentChange());
            bindPercentChange(currency.getIntradayPercentChange(), item_currency_intraday_percentChange, item_currency_intraday_percent_arrow_up, item_currency_intraday_percent_arrow_down);

            boolean isFavorite = favoriteItemListCurrency.contains(currency);

            if (isFavorite) {
                item_currency_BTN_addFavorite.setVisibility(View.INVISIBLE);
                item_currency_BTN_removeFavorite.setVisibility(View.VISIBLE);
            } else {
                item_currency_BTN_addFavorite.setVisibility(View.VISIBLE);
                item_currency_BTN_removeFavorite.setVisibility(View.INVISIBLE);
            }
        }

        private void bindPercentChange(double percentChange, TextView changeTextView, View arrowUp, View arrowDown) {
            if (percentChange < 0) {
                changeTextView.setTextColor(ContextCompat.getColor(context, R.color.text_red));
                arrowUp.setVisibility(View.INVISIBLE);
                arrowDown.setVisibility(View.VISIBLE);
            } else {
                changeTextView.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                arrowDown.setVisibility(View.INVISIBLE);
                arrowUp.setVisibility(View.VISIBLE);
            }
            changeTextView.setText(String.format("%.2f%%", percentChange));
            updatePointerVisibility(percentChange);
        }

        private void updatePointerVisibility(double percentChange) {
            if (percentChange < 0) {
                item_currency_pointer_green.setVisibility(View.INVISIBLE);
                item_currency_pointer_red.setVisibility(View.VISIBLE);
            } else {
                item_currency_pointer_green.setVisibility(View.VISIBLE);
                item_currency_pointer_red.setVisibility(View.INVISIBLE);
            }
        }
    }
}
