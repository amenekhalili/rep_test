package ir.fararayaneh.erp.adaptors.attach_album_adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.data.models.middle.AttachAlbumRecycleModel;
import ir.fararayaneh.erp.data.models.middle.DownloadState;
public class AttachAlbumRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IClickRowAttachAlbumRecHolderListener {

    private Context context;
    private List<AttachAlbumRecycleModel> itemList = new ArrayList<>();
    private IClickAttachAlbumRecAdaptor iClickAttachAlbumRecAdaptor;

    public void setiClickAttachAlbumRecAdaptor(IClickAttachAlbumRecAdaptor iClickAttachAlbumRecAdaptor) {
        this.iClickAttachAlbumRecAdaptor = iClickAttachAlbumRecAdaptor;
    }

    public AttachAlbumRecAdaptor(Context context) {
        this.context = context;
    }

    /**
     * @param itemList : because my list is short, no need to performance solicitude
     */
    public void setDataModelList(List<AttachAlbumRecycleModel> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }


    public void changeDownloadStateList(int  position, DownloadState downloadState) {
        AttachAlbumRecycleModel attachAlbumRecycleModel = itemList.get(position);
        attachAlbumRecycleModel.setDownloadState(downloadState);
        notifyItemChanged(position);
    }


    public void clearRowFromList(int position) {
        itemList.remove(position);
        notifyItemRemoved(position);
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_attach_album_rec, viewGroup, false);
        return new AttachAlbumRecHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        AttachAlbumRecHolder attachAlbumRecHolder = (AttachAlbumRecHolder) viewHolder;
        setupBinding(attachAlbumRecHolder);
    }

    private void setupBinding(AttachAlbumRecHolder attachAlbumRecHolder) {
        attachAlbumRecHolder.setRowData(itemList.get(attachAlbumRecHolder.getAdapterPosition()).getFileNme(), itemList.get(attachAlbumRecHolder.getAdapterPosition()).getDownloadState(), itemList.get(attachAlbumRecHolder.getAdapterPosition()).isCanDeleteRow());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    //-------------------------------------------------------------------
    @Override
    public void clickRowToShowByIntent(int position) {
        if (iClickAttachAlbumRecAdaptor != null) {
            iClickAttachAlbumRecAdaptor.clickRowToShowByIntent(position);
        }
    }

    @Override
    public void clickRowToStartDownload(int position) {
        if (iClickAttachAlbumRecAdaptor != null) {
            iClickAttachAlbumRecAdaptor.clickRowToStartDownload(position);
            changeDownloadStateList(position,DownloadState.ON_DOWNLOAD);
        }
    }

    @Override
    public void clickRowToCancelDownload(int position) {
        if (iClickAttachAlbumRecAdaptor != null) {
            iClickAttachAlbumRecAdaptor.clickRowToCancelDownload(position);
            changeDownloadStateList(position,DownloadState.BEFORE_DOWNLOAD);
        }
    }

    @Override
    public void clickRowToDeleteAttach(int position) {
        if (iClickAttachAlbumRecAdaptor != null) {
            iClickAttachAlbumRecAdaptor.clickRowToDeleteAttach(position);
            clearRowFromList(position);
        }
    }
}

