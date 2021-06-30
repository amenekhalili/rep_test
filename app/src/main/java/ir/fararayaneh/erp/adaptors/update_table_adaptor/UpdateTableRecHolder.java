package ir.fararayaneh.erp.adaptors.update_table_adaptor;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.View;
import com.daimajia.numberprogressbar.NumberProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class UpdateTableRecHolder extends BaseRecHolder {

    @BindView(R.id.number_prog_row_updatable_progress)
    NumberProgressBar number_prog_row_updatable_progress;
    @BindView(R.id.txt_row_updatable_progress)
    AppCompatTextView txt_row_updatable_progress;
    @BindView(R.id.img_row_updatable_progress)
    AppCompatImageView img_row_updatable_progress;
    @BindView(R.id.img_row_updatable_progress_green)
    AppCompatImageView img_row_updatable_progress_green;
    @BindView(R.id.img_row_updatable_progress_gray)
    AppCompatImageView img_row_updatable_progress_gray;


    UpdateTableRecHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String name , int percent,byte downloadState) {
        number_prog_row_updatable_progress.setProgress(percent);
        txt_row_updatable_progress.setText(name);
        handleDownloadImage(downloadState);

    }

    private void handleDownloadImage(byte downloadState) {
        switch (downloadState){
            case CommonUpdateTableState.BEFORE_DOWNLOAD_STATE:
                img_row_updatable_progress_gray.setVisibility(View.VISIBLE);
                img_row_updatable_progress_green.setVisibility(View.GONE);
                img_row_updatable_progress.setVisibility(View.GONE);
                break;
            case CommonUpdateTableState.ON_DOWNLOAD_STATE:
                img_row_updatable_progress_gray.setVisibility(View.GONE);
                img_row_updatable_progress_green.setVisibility(View.VISIBLE);
                img_row_updatable_progress.setVisibility(View.GONE);
                break;
            case CommonUpdateTableState.AFTER_DOWNLOAD_STATE:
                img_row_updatable_progress_gray.setVisibility(View.GONE);
                img_row_updatable_progress_green.setVisibility(View.GONE);
                img_row_updatable_progress.setVisibility(View.VISIBLE);
                break;
        }
    }
}
