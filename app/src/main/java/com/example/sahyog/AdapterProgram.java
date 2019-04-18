package com.example.sahyog;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.StringBufferInputStream;

public class AdapterProgram extends RecyclerView.Adapter<AdapterProgram.ProgramViewHolder>{

    private  String[] userNameData;
    private String[] userServiceData;
    private String[] userAddressData;
    private String[] userStatusText;
    private int[] userImageStatusText;


    public AdapterProgram(String[] data , String[] ServiceData , String[] addressData , String[] StatusText, int[] ImageStatusText){
        this.userNameData = data;
        this.userServiceData = ServiceData;
        this.userAddressData = addressData;
        this.userStatusText = StatusText;
        this.userImageStatusText = ImageStatusText;
    }


    @NonNull
    @Override
    public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.recycler_view_item,viewGroup,false);

        return new ProgramViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProgramViewHolder programViewHolder, int i) {
        String name = userNameData[i];
        String service = userServiceData[i];
        String address = userAddressData[i];
        String statusT = userStatusText[i];
        int imageStatusT = userImageStatusText[i];


        programViewHolder.textView.setText(name);
        programViewHolder.textDataView.setText(service);
        programViewHolder.textAddressView.setText(address);
        programViewHolder.textStatusView.setText(statusT);

        if(imageStatusT == 0){programViewHolder.imgview.setImageResource(R.drawable.bulebutton); }
        else if(imageStatusT == 1){programViewHolder.imgview.setImageResource(R.drawable.yellowbutton); }
        else if(imageStatusT == 2){programViewHolder.imgview.setImageResource(R.drawable.greenbutton); }
         }

    @Override
    public int getItemCount() {
        return  userNameData.length;
    }

    public class ProgramViewHolder extends RecyclerView.ViewHolder {
        ImageView imgview;
        TextView textView;
        TextView textDataView;
        TextView textAddressView;
        TextView textStatusView;

        public ProgramViewHolder(@NonNull View itemView) {
            super(itemView);

            imgview = (ImageView) itemView.findViewById(R.id.statusImageView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            textDataView = (TextView) itemView.findViewById(R.id.textView2);
            textAddressView = (TextView) itemView.findViewById(R.id.AddressTextView);
            textStatusView = (TextView) itemView.findViewById(R.id.statusView);

        }
    }
}
