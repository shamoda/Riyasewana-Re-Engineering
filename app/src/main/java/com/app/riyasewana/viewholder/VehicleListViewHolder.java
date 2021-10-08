package com.app.riyasewana.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.riyasewana.R;
import com.app.riyasewana.interfaces.ItemClickListner;


public class VehicleListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView specialization;
    public TextView name;
    public TextView date;
    public ImageView image;
    public ItemClickListner listner;


    public VehicleListViewHolder(@NonNull View itemView) {
        super(itemView);
        image = itemView.findViewById(R.id.sm_doctor_details_row_img);
        specialization = itemView.findViewById(R.id.sm_doctor_details_row_specialization_value);
        date = itemView.findViewById(R.id.sm_doctor_details_row_date_value);
        name = itemView.findViewById(R.id.sm_doctor_details_row_name_value);

    }

    @Override
    public void onClick(View view) {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
