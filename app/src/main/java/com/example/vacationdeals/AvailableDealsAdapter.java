package com.example.vacationdeals;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AvailableDealsAdapter extends RecyclerView.Adapter<AvailableDealsAdapter.viewHolder> {
    ArrayList<RichTravelDeal> deals;
    private FirebaseDatabase mFirebaseDB;
    private DatabaseReference mDdReference;
    private ChildEventListener mChildListener;
    private onDealListener onDealListener;

    public AvailableDealsAdapter(onDealListener onDealListener) {
        this.onDealListener=onDealListener;
        //FirebaseUtil.openFbReference("richTravels");
        mFirebaseDB = FirebaseUtil.mFirebase;
        mDdReference = FirebaseUtil.mReference;
        deals =  FirebaseUtil.deals;
        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                RichTravelDeal rDeals = dataSnapshot.getValue(RichTravelDeal.class);
                rDeals.setId(dataSnapshot.getKey());
                deals.add(rDeals);
                notifyItemInserted(deals.size()-1);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDdReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.single_deal_item,parent,false);
        return new viewHolder(view,onDealListener);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        RichTravelDeal deal = deals.get(position);
        holder.dealsTitle.setText(deal.getTitle());
        holder.dealDescription.setText(deal.getDescription());
        holder.dealPrice.setText(deal.getPrice());

    }

    @Override
    public int getItemCount() {
        return deals.size();
    }


    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView dealsTitle, dealDescription, dealPrice;
        onDealListener onDealListener;
        public viewHolder(@NonNull View itemView,onDealListener onDealListener) {
            super(itemView);
            dealsTitle = itemView.findViewById(R.id.itemTvDeals);
            dealDescription = itemView.findViewById(R.id.dealItemDescription);
            dealPrice =  itemView.findViewById(R.id.itemTvPrice);
            this.onDealListener = onDealListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDealListener.onDealclick(getAdapterPosition(),deals);

        }
    }

    public interface  onDealListener{
        void onDealclick(int position,ArrayList<RichTravelDeal> clickedDeal);

    }
}
