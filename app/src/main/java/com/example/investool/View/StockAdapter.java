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

import com.example.investool.R;
import com.example.investool.Logic.Stock;

import java.util.List;
import java.util.Set;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private Context context;
    private List<Stock> stockList;
    private static Set<Stock> favoriteItemListStock;

    public StockAdapter(Context context, List<Stock> stockList, Set<Stock> favoriteItemList) {
        this.context = context;
        this.stockList = stockList;
        this.favoriteItemListStock = favoriteItemList;
    }

    public interface OnItemClickListener {
        void onAddToFavoritesClicked(Stock stock);

        void onRemoveItemClicked(Stock stock);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(StockAdapter.OnItemClickListener listener) {this.listener = listener;}

    public int getStockPosition(Stock stock) {
        return stockList.indexOf(stock);
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_stock, parent, false);
        return new StockViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder holder, int position) {
        Stock stock = stockList.get(position);
        holder.bind(stock);

        holder.item_stock_BTN_addFavorite.setOnClickListener(view -> {
            if (listener != null) {
                listener.onAddToFavoritesClicked(stock);
                holder.item_stock_BTN_addFavorite.setVisibility(View.INVISIBLE);
                holder.item_stock_BTN_removeFavorite.setVisibility(View.VISIBLE);
            }
        });

        if (favoriteItemListStock.contains(stock)) {
            holder.item_stock_BTN_addFavorite.setVisibility(View.INVISIBLE);
            holder.item_stock_BTN_removeFavorite.setVisibility(View.VISIBLE);
        } else {
            holder.item_stock_BTN_addFavorite.setVisibility(View.VISIBLE);
            holder.item_stock_BTN_removeFavorite.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return stockList.size();
    }

    public class StockViewHolder extends RecyclerView.ViewHolder {
        private View item_pointer_red, item_pointer_green, item_stock_intraday_percent_arrow_up, item_stock_intraday_percent_arrow_down;
        private TextView item_stock_name, item_stock_intraday_open, item_stock_intraday_close, item_stock_intraday_change, item_stock_intraday_percent_change;
        public AppCompatImageButton item_stock_BTN_addFavorite, item_stock_BTN_removeFavorite;
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
            item_stock_BTN_addFavorite = itemView.findViewById(R.id.item_stock_BTN_addFavorite);
            item_stock_BTN_removeFavorite = itemView.findViewById(R.id.item_stock_BTN_removeFavorite);
        }

        public void bind(Stock stock) {
            if(stock.getChange()<0) {
                item_pointer_green.setVisibility(View.INVISIBLE);
                item_pointer_red.setVisibility(View.VISIBLE);
                item_stock_intraday_change.setTextColor(ContextCompat.getColor(context,R.color.text_red));
                item_stock_intraday_percent_change.setTextColor(ContextCompat.getColor(context,R.color.text_red));
                item_stock_intraday_percent_arrow_up.setVisibility(View.INVISIBLE);
                item_stock_intraday_percent_arrow_down.setVisibility(View.VISIBLE);
            }
            else {
                item_pointer_red.setVisibility(View.INVISIBLE);
                item_pointer_green.setVisibility(View.VISIBLE);
                item_stock_intraday_change.setTextColor(ContextCompat.getColor(context,R.color.text_green));
                item_stock_intraday_percent_change.setTextColor(ContextCompat.getColor(context,R.color.text_green));
                item_stock_intraday_percent_arrow_down.setVisibility(View.INVISIBLE);
                item_stock_intraday_percent_arrow_up.setVisibility(View.VISIBLE);
            }
            item_stock_name.setText(stock.getName());
            item_stock_intraday_open.setText("$"+stock.getFormattedOpen());
            item_stock_intraday_close.setText("$"+stock.getFormattedClose());
            item_stock_intraday_change.setText(String.valueOf(stock.getFormattedChange()));
            item_stock_intraday_percent_change.setText(stock.getFormattedChangePercent() + "%");

            boolean isFavorite = favoriteItemListStock.contains(stock);

            if (isFavorite) {
                item_stock_BTN_addFavorite.setVisibility(View.INVISIBLE);
                item_stock_BTN_removeFavorite.setVisibility(View.VISIBLE);
            } else {
                item_stock_BTN_addFavorite.setVisibility(View.VISIBLE);
                item_stock_BTN_removeFavorite.setVisibility(View.INVISIBLE);
            }
        }
    }
}
