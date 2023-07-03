package com.example.uas_travel_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Vector;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.ViewHolder> {

    Context context;
    Vector<Destination> destList;

    SharedPreferences sp;



    public DestinationAdapter(Context context){
        this.context = context;
    }

    public void setDestList(Vector<Destination> destList) {
        this.destList = destList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.destination_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.DestNameTxt.setText(destList.get(position).getName());
        holder.DestPriceTxt.setText(destList.get(position).getPrice());

        String imageName = destList.get(position).getImage();

        Picasso.get().load(imageName).into(holder.DestImageTxt);

        holder.destDetailPos = position;
        holder.DestinationID = destList.get(position).getDestinationID();


    }

    @Override
    public int getItemCount() {
        return destList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CardView destCV;
        TextView DestNameTxt, DestPriceTxt;
        ImageView DestImageTxt;
        int DestinationID;

        int destDetailPos;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            DestNameTxt = itemView.findViewById(R.id.destinationName);
            DestPriceTxt = itemView.findViewById(R.id.destinationPrice);
            DestImageTxt = itemView.findViewById(R.id.destinationImage);
            destCV = itemView.findViewById(R.id.destination_cv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View detailDestView = inflater.inflate(R.layout.destination_detail , null);

            TextView name = detailDestView.findViewById(R.id.destinationDetailName);
            ImageView image = detailDestView.findViewById(R.id.destinationDetailImage);
            TextView rating = detailDestView.findViewById(R.id.destinationDetailRating);
            TextView price = detailDestView.findViewById(R.id.destinationDetailPrice);
            TextView description = detailDestView.findViewById(R.id.destinationDetailDescription);

            EditText note = detailDestView.findViewById(R.id.destinationDetailNote);


            DestinationHelper destinationHelper = new DestinationHelper(context);

            destinationHelper.open();
            Destination tempDest = destinationHelper.getSingleDestination(DestinationID);
            destinationHelper.close();

            name.setText(tempDest.getName());

            String imageNameAlt = tempDest.getImageAlt();
//            String imageNameAlt = destList.get(destDetailPos).getImageAlt();

//            Resources resources = view.getContext().getResources();
//            int resourceID = resources.getIdentifier(imageName, "drawable", itemView.getContext().getPackageName());
//            image.setImageResource(resourceID);
            Picasso.get().load(imageNameAlt).into(image);

            description.setText(tempDest.getDescription());
            rating.setText("Rating : " + String.valueOf(tempDest.getRating()));
            price.setText("Price : Rp. " + String.valueOf(tempDest.getPrice()));

            AlertDialog alertDialog = new AlertDialog.Builder(context).
                    setTitle("Destination Detail")
                    .setView(detailDestView)
                    .setPositiveButton("Insert note", null)
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setCancelable(false)
                    .create();

            // overide on show listener to prevent dialog closing when empty
            alertDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button button = ((AlertDialog) alertDialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {



                            sp = context.getSharedPreferences("LoggedInSession",Context.MODE_PRIVATE);
                            String username = sp.getString("username","");

                            ProfileHelper profileHelper = new ProfileHelper(context);
                            profileHelper.open();
                            Profile profile = profileHelper.viewProfile(username);
                            profileHelper.close();

                            TravelListHelper tlHelper = new TravelListHelper(context);
                            tlHelper.open();
                            boolean isDestinationExist = tlHelper.isDestinationExistInList(profile.getUserID(),DestinationID);
                            tlHelper.close();


                            if (isDestinationExist) {
                                Toast.makeText(context, "Destination is already in list!", Toast.LENGTH_SHORT).show();
                            } else {
                                tlHelper.open();
                                tlHelper.addTravelList(note.getText().toString(), profile.getUserID(),DestinationID);
                                tlHelper.close();

                                notifyDataSetChanged();

                                Toast.makeText(context, "Destination added!", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            }


                        }
                    });
                }
            });
            alertDialog.show();


        }
    }
}
