package ir.fararayaneh.erp.adaptors.facilities_adaptor;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.data.data_providers.offline.SetFormCodeToIntentModel;
import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntentFactoryHandler;

public class FacilitiesRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IClickRowFacilitiesRecHolderListener {

    private Activity activity;
    private List<FacilitiesModel> itemList = new ArrayList<>();

    public FacilitiesRecAdaptor(Activity activity) {
        this.activity = activity;
    }

    public void setDataModelList(List<FacilitiesModel> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        if(viewType==0){
            view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities, viewGroup, false);

        } else {
            Log.i("arash", "onCreateViewHolder: "+viewType%(6));

            switch (viewType%(6)){
                case 0:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities, viewGroup, false);
                    break;
                case 1:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities5, viewGroup, false);
                    break;
                case 2:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities2, viewGroup, false);
                    break;
                case 3:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities4, viewGroup, false);
                    break;
                case 4:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities3, viewGroup, false);
                    break;
                case 5:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities, viewGroup, false);
                    break;
                default:
                    view = LayoutInflater.from(activity).inflate(R.layout.row_main_facilities5, viewGroup, false);

            }
        }


        return new FacilitiesRecHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        FacilitiesRecHolder facilitiesRecHolder = (FacilitiesRecHolder) viewHolder;
        setupBinding(facilitiesRecHolder);
    }

    private void setupBinding(FacilitiesRecHolder facilitiesRecHolder) {
        facilitiesRecHolder.setRowData(itemList.get(facilitiesRecHolder.getAdapterPosition()).getTitle()
                , itemList.get(facilitiesRecHolder.getAdapterPosition()).getImageUrl());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    /**
     *idForShowAsPrimitiveData is 0, because we can not go to edit from main activity
     */
    @Override
    public void clickRow(int position) {
        if(!itemList.get(0).isFake()){
            ActivityIntentFactoryHandler.goToProperActivity(activity,itemList.get(position).getFormCode(), SetFormCodeToIntentModel.setFormCode(itemList.get(position).getFormCode(),Commons.NULL),false,false);
            //todo remove this codes :
            Toast.makeText(activity, "formCode: "+itemList.get(position).getFormCode(), Toast.LENGTH_SHORT).show();
            Log.i("arash", "clickRow: "+itemList.get(position).getFormCode());
        } else {
            //todo remove this code :
            Toast.makeText(activity, "fake soon", Toast.LENGTH_SHORT).show();
        }
    }
}

