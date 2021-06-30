package ir.fararayaneh.erp.adaptors.configs_adaptor;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.models.middle.ShowAppConfigsModel;

public class ConfigsRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IClickRowConfigsRecHolderListener {

    private Context context;
    private IClickConfigsRecAdaptor clickConfigsRecAdaptor;
    private List<ShowAppConfigsModel> itemList = new ArrayList<>();

    public ConfigsRecAdaptor(Context context) {
        this.context = context;
    }

    public void setDataModelList(List<ShowAppConfigsModel> itemList) {
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void setClickRecycle(IClickConfigsRecAdaptor clickConfigsRecAdaptor) {
        this.clickConfigsRecAdaptor=clickConfigsRecAdaptor;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_configs_list, viewGroup, false);
        return new ConfigsRecHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ConfigsRecHolder configsRecHolder = (ConfigsRecHolder) viewHolder;
        setupBinding(configsRecHolder);
    }

    private void setupBinding(ConfigsRecHolder configsRecHolder) {
        configsRecHolder.setRowData(itemList.get(configsRecHolder.getAdapterPosition()).getTitle(),
                itemList.get(configsRecHolder.getAdapterPosition()).getDescriptions(),
                itemList.get(configsRecHolder.getAdapterPosition()).getSrcImageDrawable());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void clickRow(int position) {
        if(clickConfigsRecAdaptor!=null){
            clickConfigsRecAdaptor.clikcConfigs(position);
        }
    }
}
