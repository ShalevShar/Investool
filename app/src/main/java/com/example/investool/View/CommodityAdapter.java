package com.example.investool.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.investool.Logic.Commodity;
import com.example.investool.R;

import java.util.List;

public class CommodityAdapter extends RecyclerView.Adapter<CommodityAdapter.CommodityViewHolder> {
    private Context context;
    private List<Commodity> commodityList;

    public CommodityAdapter(Context context, List<Commodity> commodityList) {
        this.context = context;
        this.commodityList = commodityList;
    }

    @NonNull
    @Override
    public CommodityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_commodity, parent, false);
        return new CommodityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommodityViewHolder holder, int position) {
        Commodity commodity = commodityList.get(position);
        holder.bind(commodity);
    }

    @Override
    public int getItemCount() {
        return commodityList.size();
    }

    public class CommodityViewHolder extends RecyclerView.ViewHolder {
        private View pointer_red, pointer_green, arrow_up, arrow_down;
        private TextView nameTextView, unitTextView, priceTextView, changeTextView, percentChangeTextView;

        public CommodityViewHolder(@NonNull View itemView) {
            super(itemView);
            pointer_red = itemView.findViewById(R.id.item_commodity_pointer_red);
            pointer_green = itemView.findViewById(R.id.item_commodity_pointer_green);
            nameTextView = itemView.findViewById(R.id.item_commodity_name);
            unitTextView = itemView.findViewById(R.id.item_commodity_unit);
            priceTextView = itemView.findViewById(R.id.item_commodity_intraday_price);
            changeTextView = itemView.findViewById(R.id.item_commodity_intraday_change);
            percentChangeTextView = itemView.findViewById(R.id.item_commodity_intraday_percent_change);
            arrow_up = itemView.findViewById(R.id.item_commodity_arrow_up);
            arrow_down = itemView.findViewById(R.id.item_commodity_arrow_down);
        }

        void bind(Commodity commodity) {
            if(commodity.getPercentChange()<0) {
                pointer_green.setVisibility(View.INVISIBLE);
                pointer_red.setVisibility(View.VISIBLE);
                changeTextView.setTextColor(ContextCompat.getColor(context,R.color.text_red));
                percentChangeTextView.setTextColor(ContextCompat.getColor(context,R.color.text_red));
                arrow_up.setVisibility(View.INVISIBLE);
                arrow_down.setVisibility(View.VISIBLE);
            }
            else {
                pointer_red.setVisibility(View.INVISIBLE);
                pointer_green.setVisibility(View.VISIBLE);
                changeTextView.setTextColor(ContextCompat.getColor(context,R.color.text_green));
                percentChangeTextView.setTextColor(ContextCompat.getColor(context,R.color.text_green));
                arrow_down.setVisibility(View.INVISIBLE);
                arrow_up.setVisibility(View.VISIBLE);
            }
            nameTextView.setText(commodity.getName());
            unitTextView.setText(commodity.getUnit());
            priceTextView.setText("$"+commodity.getPrice());
            changeTextView.setText(String.valueOf(commodity.getChange()));
            percentChangeTextView.setText(String.valueOf(commodity.getPercentChange()));
        }
    }
}
