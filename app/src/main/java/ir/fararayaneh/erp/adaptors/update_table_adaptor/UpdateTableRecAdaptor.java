package ir.fararayaneh.erp.adaptors.update_table_adaptor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.models.middle.ProgressPercentModel;

public class UpdateTableRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<ProgressPercentModel> itemList = new ArrayList<>();

    public UpdateTableRecAdaptor(Context context) {
        this.context = context;
    }

    public void setDataModelList(List<ProgressPercentModel> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_updatable_progress, viewGroup, false);
        return new UpdateTableRecHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        UpdateTableRecHolder updateTableRecHolder = (UpdateTableRecHolder) viewHolder;
        setupBinding(updateTableRecHolder);
    }

    /**
     * here by default getDownloadState = 0
     */
    private void setupBinding(UpdateTableRecHolder updateTableRecHolder) {
        updateTableRecHolder.setRowData(itemList.get(updateTableRecHolder.getAdapterPosition()).getName(),
                itemList.get(updateTableRecHolder.getAdapterPosition()).getCurrentPercentage(), itemList.get(updateTableRecHolder.getAdapterPosition()).getDownloadState());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void updatePercentage(int position, int newPercent) {
        itemList.get(position).setCurrentPercentage(newPercent);
        updateDownloadState(position, newPercent);
        notifyDataSetChanged();

    }

    private void updateDownloadState(int position, int newPercent) {
        itemList.get(position)
                .setDownloadState(newPercent == 0 ? CommonUpdateTableState.BEFORE_DOWNLOAD_STATE : newPercent == 100 ? CommonUpdateTableState.AFTER_DOWNLOAD_STATE : CommonUpdateTableState.ON_DOWNLOAD_STATE);
    }

    public void updateName(int position, String name) {
        itemList.get(position).setName(name);
        notifyDataSetChanged();
    }


}
