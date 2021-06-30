package ir.fararayaneh.erp.adaptors.attach_album_adaptor;

import android.view.View;

import com.github.abdularis.buttonprogress.DownloadButtonProgress;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.data.models.middle.DownloadState;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

/**
 * if we have file on the sd cart (haveFileOnSDCart = true),
 * show that by picasso (if is image, show, other wise show default icon)
 * other wise, we show progress until download would be completed
 */
public class AttachAlbumRecHolder extends BaseRecHolder {

    private IClickRowAttachAlbumRecHolderListener holderListener;

    @BindView(R.id.img_attach_album_row)
    CustomCircleImageView img_attach_album_row;
    @BindView(R.id.img_delete_album_row)
    AppCompatImageView img_delete_album_row;
    @BindView(R.id.txt_attach_album_row)
    AppCompatTextView txt_attach_album_row;
    @BindView(R.id.btn_download_attach_album_row)
    DownloadButtonProgress btn_download_attach_album_row;


    /**
     * only click on img_attach_album_row, can make intent to open file
     */
    @OnClick({R.id.img_attach_album_row,R.id.img_delete_album_row})
    public void clickAttachAlbumRecHolder(View view) {
        switch (view.getId()){
            case R.id.img_attach_album_row:
                if (holderListener != null) {
                    holderListener.clickRowToShowByIntent(getAdapterPosition());
                }
                break;
            case R.id.img_delete_album_row:
                if (holderListener != null) {
                    holderListener.clickRowToDeleteAttach(getAdapterPosition());
                }
                break;
        }
    }

    AttachAlbumRecHolder(View itemView, IClickRowAttachAlbumRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
        setupClickForDownload();
    }

    //todo remove leak memory
    private void setupClickForDownload() {
        btn_download_attach_album_row.addOnClickListener(new DownloadButtonProgress.OnClickListener() {
            @Override
            public void onIdleButtonClick(View view) {
                if (holderListener != null) {
                    holderListener.clickRowToStartDownload(getAdapterPosition());
                    btn_download_attach_album_row.setIndeterminate();
                }
            }

            @Override
            public void onCancelButtonClick(View view) {
                if (holderListener != null) {
                    holderListener.clickRowToCancelDownload(getAdapterPosition());
                    btn_download_attach_album_row.setIdle();
                }
            }

            @Override
            public void onFinishButtonClick(View view) {
                //user not see this step ,
            }
        });
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    void setRowData(String fileName, DownloadState downloadState,boolean canDeleteRow) {

        txt_attach_album_row.setText(makeProperFileName(fileName));
        showProperDeleteImage(canDeleteRow);
        showProperAttachImage(downloadState);
        if (downloadState.equals(DownloadState.DOWNLOAD_COMPLETE)) {
            PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_attach_album_row, false, Commons.SPACE, false, true);
        }
    }

    private String makeProperFileName(String fileName) {
        if(fileName.length()>4){
            return fileName.substring(0,3)+MimeTypeHandler.getFileSuffixFromName(fileName);
        } else {
            return fileName;
        }
    }

    private void showProperDeleteImage(boolean canDeleteRow) {
        img_delete_album_row.setVisibility(canDeleteRow?View.VISIBLE:View.GONE);
    }


    private void showProperAttachImage(DownloadState downloadState) {
        btn_download_attach_album_row.setVisibility(downloadState.equals(DownloadState.DOWNLOAD_COMPLETE) ? View.GONE : View.VISIBLE);
        img_attach_album_row.setVisibility(btn_download_attach_album_row.getVisibility()==View.VISIBLE ? View.GONE : View.VISIBLE);
        if(!downloadState.equals(DownloadState.DOWNLOAD_COMPLETE)){
            setupDownloadState(downloadState);
        }

    }

    /**
     * @param downloadState = DOWNLOAD_COMPLETE were be handled in showProperAttachImage()
     */
    private void setupDownloadState(DownloadState downloadState) {
        switch (downloadState){
            case ON_DOWNLOAD:
                btn_download_attach_album_row.setIndeterminate();
                break;
            case BEFORE_DOWNLOAD:
                btn_download_attach_album_row.setIdle();
                break;
        }
    }


}
