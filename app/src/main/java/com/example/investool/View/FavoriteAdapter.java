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
import com.example.investool.Logic.Stock;
import com.example.investool.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static Context context;
    private List<Object> favoriteItemList;
    private OnItemClickListener itemClickListener;

    public FavoriteAdapter(Context context, OnItemClickListener listener) {
        this.context = context;
        favoriteItemList = new ArrayList<>();
        this.itemClickListener = listener;
    }

    public interface OnItemClickListener {
        void onAddToFavoritesClicked(Object item);

        void onRemoveItemClicked(Object item);
    }
    public interface OnItemRemovedListener {
        void onItemRemoved(Object item);
    }
    private OnItemRemovedListener itemRemovedListener;

    public void setItemRemovedListener(OnItemRemovedListener listener) {
        this.itemRemovedListener = listener;
    }

    public void addItem(Object item) {
        favoriteItemList.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(Object item) {
        int position = favoriteItemList.indexOf(item);
        if (position != -1) {
            favoriteItemList.remove(item);
            notifyItemRemoved(position);
            if (itemRemovedListener != null) {
                itemRemovedListener.onItemRemoved(item);
            }
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == 0) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_currency, parent, false);
            return new CurrencyViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_stock, parent, false);
            return new StockViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Object item = favoriteItemList.get(position);
        if (item instanceof Currency) {
            ((CurrencyViewHolder) holder).bind((Currency) item);
        } else if (item instanceof Stock) {
            ((StockViewHolder) holder).bind((Stock) item);
        }
    }

    @Override
    public int getItemCount() {
        return favoriteItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        Object item = favoriteItemList.get(position);
        if (item instanceof Currency) {
            return 0;
        } else if (item instanceof Stock) {
            return 1;
        }
        return -1;
    }

    public class CurrencyViewHolder extends RecyclerView.ViewHolder {
        TextView item_currency_exchange, item_currency_intraday_bid, item_currency_intraday_ask,
                item_currency_intraday_change, item_currency_intraday_percentChange;
        View item_currency_pointer_green, item_currency_pointer_red, item_currency_intraday_percent_arrow_up,
                item_currency_intraday_percent_arrow_down;
        AppCompatImageButton item_currency_BTN_removeFavorite;

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
            item_currency_BTN_removeFavorite = itemView.findViewById(R.id.item_currency_BTN_removeFavorite);

            item_currency_BTN_removeFavorite.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onRemoveItemClicked(favoriteItemList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Currency currency) {
            item_currency_exchange.setText(currency.getName());
            item_currency_intraday_bid.setText(currency.getFormattedIntradayBid());
            item_currency_intraday_ask.setText(currency.getFormattedIntradayAsk());
            item_currency_intraday_change.setText(currency.getFormattedIntradayChange());
            item_currency_intraday_percentChange.setText(currency.getFormattedIntradayPercentChange());
            bindPercentChange(currency.getIntradayPercentChange(), item_currency_intraday_percentChange,
                    item_currency_intraday_percent_arrow_up, item_currency_intraday_percent_arrow_down);
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

    public class StockViewHolder extends RecyclerView.ViewHolder {
        TextView item_stock_name, item_stock_intraday_open, item_stock_intraday_close,
                item_stock_intraday_change, item_stock_intraday_percent_change;
        View item_pointer_red, item_pointer_green, item_stock_intraday_percent_arrow_up,
                item_stock_intraday_percent_arrow_down;
        AppCompatImageButton item_stock_BTN_removeFavorite;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            item_pointer_red = itemView.findViewById(R.id.item_pointer_red);
            item_pointer_green = itemView.findViewById(R.id.item_pointer_green);
            item_stock_name = itemView.findViewById(R.id.item_stock_name);
            item_stock_intraday_open = itemView.findViewById(R.id.item_stock_intraday_open);
            item_stock_intraday_close = itemView.findViewById(R.id.item_stock_intraday_close);
            item_stock_intraday_change = itemView.findViewById(R.id.item_stock_intraday_change);
            item_stock_intraday_percent_change = itemView.findViewById(R.id.item_stock_intraday_percent_change);
            item_stock_intraday_percent_arrow_up = itemView.findViewById(R.id.item_stock_intraday_percent_arrow_up);
            item_stock_intraday_percent_arrow_down = itemView.findViewById(R.id.item_stock_intraday_percent_arrow_down);
            item_stock_BTN_removeFavorite = itemView.findViewById(R.id.item_stock_BTN_removeFavorite);

            item_stock_BTN_removeFavorite.setOnClickListener(view -> {
                if (itemClickListener != null) {
                    itemClickListener.onRemoveItemClicked(favoriteItemList.get(getAdapterPosition()));
                }
            });
        }

        public void bind(Stock stock) {
            if (stock.getChange() < 0) {
                item_pointer_green.setVisibility(View.INVISIBLE);
                item_pointer_red.setVisibility(View.VISIBLE);
                item_stock_intraday_change.setTextColor(ContextCompat.getColor(context, R.color.text_red));
                item_stock_intraday_percent_change.setTextColor(ContextCompat.getColor(context, R.color.text_red));
                item_stock_intraday_percent_arrow_up.setVisibility(View.INVISIBLE);
                item_stock_intraday_percent_arrow_down.setVisibility(View.VISIBLE);
            } else {
                item_pointer_red.setVisibility(View.INVISIBLE);
                item_pointer_green.setVisibility(View.VISIBLE);
                item_stock_intraday_change.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                item_stock_intraday_percent_change.setTextColor(ContextCompat.getColor(context, R.color.text_green));
                item_stock_intraday_percent_arrow_down.setVisibility(View.INVISIBLE);
                item_stock_intraday_percent_arrow_up.setVisibility(View.VISIBLE);
            }
            item_stock_name.setText(stock.getName());
            item_stock_intraday_open.setText("$" + stock.getFormattedOpen());
            item_stock_intraday_close.setText("$" + stock.getFormattedClose());
            item_stock_intraday_change.setText(String.valueOf(stock.getFormattedChange()));
            item_stock_intraday_percent_change.setText(stock.getFormattedChangePercent() + "%");
        }
    }
}