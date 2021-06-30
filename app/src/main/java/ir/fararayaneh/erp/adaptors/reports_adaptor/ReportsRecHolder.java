package ir.fararayaneh.erp.adaptors.reports_adaptor;


import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.custom_views.CustomCircleImageView;
import ir.fararayaneh.erp.utils.GetATTResources;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

/**
 * all reports layout like (row_report_type1.xml or row_report_type2.xml)
 * should be same id for editText and ....else, we should create an other holder
 */
public class ReportsRecHolder extends BaseRecHolder {

    private IClickRowReportsRecHolderListener holderListener;
    private boolean canUserSeeAttach;

    @BindView(R.id.img_attach_row_report)
    CustomCircleImageView img_attach_row_report;
    @BindView(R.id.txt_description_row_report)
    AppCompatTextView txt_description_row_report;
    @BindView(R.id.frameState)
    FrameLayout frameState;

    private String description = Commons.SPACE;

    private int myViewType = CommonViewTypeReport.NORMAL_VIEW_TYPE;


    @OnClick({R.id.img_attach_row_report, R.id.txt_description_row_report})
    public void clickReportsRecHolder(View view) {
        switch (view.getId()) {
            case R.id.img_attach_row_report:
                clickImageAttach();
                break;
            case R.id.txt_description_row_report:
                clickEdit();
                break;
        }
    }


    /**
     * all user can edit, in presenter we prevent non allowed user
     */
    private void clickEdit() {
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), CommonClickRowReport.EDIT_CLICKED, myViewType == CommonViewTypeReport.NORMAL_VIEW_TYPE);
        }
    }

    /**
     * if user see attach, can click on it
     */
    private void clickImageAttach() {
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), CommonClickRowReport.IMAGE_ATTACH_CLICKED, myViewType == CommonViewTypeReport.NORMAL_VIEW_TYPE);
        }
    }


    ReportsRecHolder(View itemView, IClickRowReportsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    /**
     * @param fileName: GUID.Suffix
     * @param viewType  : from CommonViewTypeReport
     */
    void setRowData(String description, String fileName, boolean canUserSeeAttach, int viewType) {
        this.description = description;
        this.canUserSeeAttach = canUserSeeAttach;
        setupDescription();
        setupState(viewType);
        showHideAttachImage(fileName);
    }

    private void setupDescription() {
        txt_description_row_report.setText(description);
    }

    private void showHideAttachImage(String fileName) {
        img_attach_row_report.setVisibility(canUserSeeAttach ? View.VISIBLE : View.GONE);
        if (canUserSeeAttach) {
            PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_attach_row_report, false, Commons.SPACE, true, false);
        }
    }

    //todo add other view type
    private void setupState(int viewType) {
        myViewType = viewType;
        switch (viewType) {
            case CommonViewTypeReport.NORMAL_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.seen), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorGrayAttr})));
                break;
            case CommonViewTypeReport.INSERTED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.New), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorGreenAttr})));
                break;
            case CommonViewTypeReport.DELETED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.deleted), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorBlueAttr})));
                break;
            case CommonViewTypeReport.ACCESS_DENIED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.access_denied), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorRedAttr})));
                break;
            case CommonViewTypeReport.EDITED_VIEW_TYPE:
                setupStateValues(itemView.getResources().getString(R.string.edited), itemView.getResources().getColor(GetATTResources.resId(itemView.getContext(), new int[]{R.attr.colorOrangeAttr})));
                break;
        }
    }

    private void setupStateValues(String state, int color) {
        //txt_state_row_report.setTextColor(color);
        //txt_state_row_report.setText(state);
        frameState.setBackgroundColor(color);
    }

}
