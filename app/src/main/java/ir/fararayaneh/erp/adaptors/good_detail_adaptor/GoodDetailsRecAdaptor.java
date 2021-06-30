package ir.fararayaneh.erp.adaptors.good_detail_adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.models.middle.GoodsDetailModel;


public class GoodDetailsRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IClickRowDetailGoodsRecHolderListener {

    private Context context;
    private List<GoodsDetailModel> itemList = new ArrayList<>();
    private IGoodsDetailRecAdaptorListener iGoodsDetailRecAdaptorListener
;
    public GoodDetailsRecAdaptor( Context context) {
        this.context = context;
    }

    public void setiGoodsDetailRecAdaptorListener(IGoodsDetailRecAdaptorListener iGoodsDetailRecAdaptorListener) {
        this.iGoodsDetailRecAdaptorListener = iGoodsDetailRecAdaptorListener;
    }

    /**
     * because my rows is limited, any change in row make notify data set changed
     */
    public void setDataModelList(List<GoodsDetailModel> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    /**
     * because my rows is limited, any change in row make notify data set changed
     */
    public void removeFromList(int position) {
        itemList.remove(position);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new GoodDetailsRecHolder(LayoutInflater.from(context).inflate(R.layout.row_detail_goods, viewGroup, false),this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        GoodDetailsRecHolder goodDetailsRecHolder = (GoodDetailsRecHolder) viewHolder;
        setupBinding(goodDetailsRecHolder);
    }

    private void setupBinding(GoodDetailsRecHolder goodDetailsRecHolder) {
        goodDetailsRecHolder.setRowData(itemList.get(goodDetailsRecHolder.getAdapterPosition()).getTitle(),itemList.get(goodDetailsRecHolder.getAdapterPosition()).getContent());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    @Override
    public void clickDeleteRow(int position) {
        if(iGoodsDetailRecAdaptorListener!=null){
            iGoodsDetailRecAdaptorListener.clickDeleteRow(position);
        }
    }

    @Override
    public void clickEditRow(int position) {
        if(iGoodsDetailRecAdaptorListener!=null){
            iGoodsDetailRecAdaptorListener.clickEditRow(position);
        }
    }
}

