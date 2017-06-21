package com.example.nitin.desichain.Adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.nitin.desichain.Contents.AddressList;
import com.example.nitin.desichain.R;

import java.util.List;

/**
 * Created by ashis on 6/17/2017.
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {

    private Context mContext;
    private List<AddressList> mAddress;

    public interface SaveAddress{
        public void OnEditButtonClicked(int positon);
    }

    public AddressAdapter(Context mContext, List<AddressList> mAddress) {
        this.mContext = mContext;
        this.mAddress = mAddress;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_card_address,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        AddressList addressList = mAddress.get(position);
        holder.mNameText.setText(addressList.getmName());
        holder.mMobile.setText(addressList.getmContactNumber());
        holder.mAddressText.setText(addressList.getmAddress());

        holder.mPopupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupMenu(holder.mPopupbtn,position);
            }
        });

    }

    private void showPopupMenu(ImageButton mPopupbtn, final int position) {

        PopupMenu popup = new PopupMenu(mContext,mPopupbtn);
        MenuInflater inflator = popup.getMenuInflater();
        inflator.inflate(R.menu.address_popup,popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
               if(item.getItemId()==R.id.editAddress)
               {
                   SaveAddress saveAddress= (SaveAddress) mContext;
                   saveAddress.OnEditButtonClicked(position);
               }
                else if(item.getItemId()==R.id.removeAddress)
               {
                   mAddress.remove(position);
                   notifyDataSetChanged();
               }
                return true;
            }
        });
        popup.show();
    }



    @Override
    public int getItemCount() {
        return mAddress.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView mNameText,mAddressText,mMobile;
        public ImageButton mPopupbtn;

        public MyViewHolder(View itemView) {
            super(itemView);

            mPopupbtn=(ImageButton)itemView.findViewById(R.id.popupImage);
            mNameText=(TextView) itemView.findViewById(R.id.nameText);
            mAddressText=(TextView)itemView.findViewById(R.id.addressText);
            mMobile=(TextView)itemView.findViewById(R.id.mobileText);
        }
    }
}