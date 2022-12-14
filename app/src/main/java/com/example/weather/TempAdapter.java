package com.example.weather;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


public class TempAdapter extends RecyclerView.Adapter<TempAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private List<Temperature> tempList;

    public TempAdapter(Context context, List<Temperature> tempList) {
        this.tempList = tempList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Temperature temperature = tempList.get(position);
        holder.dateView.setText(temperature.getDate());
        holder.tempView.setText("Температура: " + Double.toString(temperature.getTemp()) + " C°");
        holder.picView.setImageBitmap(Bitmap.createScaledBitmap(
                temperature.getImg(), 180, 180, false
        ));
    }

    @Override
    public int getItemCount() {
        return tempList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final ImageView picView;
        final TextView dateView, tempView;
        ViewHolder(View view){
            super(view);
            picView = view.findViewById(R.id.pic);
            dateView = view.findViewById(R.id.data);
            tempView = view.findViewById(R.id.temp);
        }
    }
}
