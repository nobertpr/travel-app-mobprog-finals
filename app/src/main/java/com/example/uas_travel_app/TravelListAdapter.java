package com.example.uas_travel_app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Vector;


public class TravelListAdapter extends RecyclerView.Adapter<TravelListAdapter.ViewHolder> {

    Context context;
    Vector<TravelList> tlList;

    DestinationHelper destinationHelper;

    public TravelListAdapter(Context context) {
        this.context = context;
    }

    public TravelListAdapter(Context context, Vector<TravelList> tlList) {
        this.context = context;
        this.tlList = tlList;
    }

    public void setTravelList(Vector<TravelList> tlList) {
        this.tlList = tlList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.travel_list_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.UserNameTxt.setText("Destination " + Integer.toString(position+1));
//        holder.UserNameTxt.setText(tlList.get(position).getUserName());
        holder.DestNameTxt.setText(tlList.get(position).getDestinationName());

        int tempTlID = tlList.get(position).getTlID();

        destinationHelper = new DestinationHelper(context);

        destinationHelper.open();
        String imageName = destinationHelper.getDestinationImageAlt_from_TravelListID(tempTlID);
        destinationHelper.close();


        Picasso.get().load(imageName).into(holder.DestImageTxt);

        holder.TlNoteTxt.setText(tlList.get(position).getNote());
        holder.tlPos = position;


    }

    @Override
    public int getItemCount() {
        return tlList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView UserNameTxt, DestNameTxt, TlNoteTxt;
        ImageView DestImageTxt;
        // delete button
        Button delBtn, updateBtn;

        int tlPos;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            UserNameTxt = itemView.findViewById(R.id.tlUserName);
            DestNameTxt = itemView.findViewById(R.id.tlDestinationName);
            DestImageTxt = itemView.findViewById(R.id.tlDestinationImage);
            TlNoteTxt = itemView.findViewById(R.id.tlNote);



            updateBtn = itemView.findViewById(R.id.updateBtn);

            updateBtn.setOnClickListener(view -> {



                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View popUp = inflater.inflate(R.layout.travel_list_update , null);

                EditText updateET = (EditText) new EditText(context);
                EditText noteET = popUp.findViewById(R.id.tlUpdateET);

                new AlertDialog.Builder(context)
                        .setTitle("Update Note")

                        .setView(popUp)
                        .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String note = noteET.getText().toString();
                                int posAdapt = getAdapterPosition();
                                TravelListHelper tlHelper = new TravelListHelper(context);

                                tlHelper.open();
                                tlHelper.updateTravelList(tlList.get(posAdapt).getTlID(),note);
                                tlHelper.close();

                                TravelList tempTL = new TravelList(tlList.get(posAdapt).getUserName(), tlList.get(posAdapt).getDestinationName(), tlList.get(posAdapt).getDestinationImage(), tlList.get(posAdapt).getDestinationImageAlt(), note, tlList.get(posAdapt).getTlID());

                                tlList.set(posAdapt,tempTL);

                                notifyItemChanged(posAdapt);

                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
            });

            delBtn = itemView.findViewById(R.id.deleteBtn);

            delBtn.setOnClickListener(view -> {
                new AlertDialog.Builder(context).setTitle("Delete")
                        .setMessage("Are you sure you want to delete this item from the list?")
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                            Log.d("AlertDelete","Delete");
                                TravelListHelper tlHelper = new TravelListHelper(context);

                                tlHelper.open();
                                tlHelper.deleteTravelList(tlList.get(tlPos).getTlID());
                                tlHelper.close();

                                // delete entry from list
                                tlList.remove(tlPos);

                                // notify viewholder to update
                                notifyItemRemoved(tlPos);
                                notifyItemRangeChanged(tlPos, tlList.size());
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
//                            Log.d("AlertCancel","Cancel Delete");
                            }
                        })
                        .show();

            });


        }
    }
}
