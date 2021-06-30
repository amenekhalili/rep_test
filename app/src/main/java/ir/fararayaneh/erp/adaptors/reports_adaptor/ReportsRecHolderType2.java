package ir.fararayaneh.erp.adaptors.reports_adaptor;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsDownloadFile;
import ir.fararayaneh.erp.utils.GetATTResources;
import ir.fararayaneh.erp.utils.PicassoHandler;
import ir.fararayaneh.erp.utils.file_helper.MimeTypeHandler;

public class ReportsRecHolderType2 extends BaseRecHolder {

    private IClickRowReportsRecHolderListener holderListener;
    private boolean canUserSeeAttach;

    @BindView(R.id.linlay_state_report_2)
    LinearLayout linlay_state_report_2;
    @BindView(R.id.txt_main_titile_report_2)
    AppCompatTextView txt_main_titile_report_2;
    @BindView(R.id.txt_sub_titile_report_2)
    AppCompatTextView txt_sub_titile_report_2;
    @BindView(R.id.txt_date_report_2)
    AppCompatTextView txt_date_report_2;
    @BindView(R.id.img_attach_report_2)
    AppCompatImageView img_attach_report_2;
    @BindView(R.id.txt_state_report_2)
    AppCompatTextView txt_state_report_2;
    @BindView(R.id.txt_main_content_report_2)
    AppCompatTextView txt_main_content_report_2;



    private int myViewType = CommonViewTypeReport.NORMAL_VIEW_TYPE;


    @OnClick({R.id.img_attach_report_2,R.id.txt_state_report_2, R.id.txt_main_content_report_2})
    public void clickReportsRecHolderType2(View view) {
        switch (view.getId()) {
            case R.id.img_attach_report_2:
                clickImageAttach();
                break;
            case R.id.txt_main_content_report_2:
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


    ReportsRecHolderType2(View itemView, IClickRowReportsRecHolderListener holderListener) {
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
    void setRowData(String description,String mainTitle,String subTitle,String date, String fileName, boolean canUserSeeAttach, int viewType) {
        this.canUserSeeAttach = canUserSeeAttach;
        txt_main_titile_report_2.setText(mainTitle);
        txt_sub_titile_report_2.setText(subTitle);
        txt_date_report_2.setText(date);
        txt_main_content_report_2.setText(description);
        setupState(viewType);
        showHideAttachImage(fileName);
    }



    private void showHideAttachImage(String fileName) {
        img_attach_report_2.setVisibility(canUserSeeAttach ? View.VISIBLE : View.GONE);
        if (canUserSeeAttach) {
                PicassoHandler.setByPicasso(picasso, MimeTypeHandler.getFilePathFromName(CommonsDownloadFile.DOWNLOAD_FILE_FOLDER_NAME, fileName), MimeTypeHandler.getURLFromName(fileName), img_attach_report_2, false, Commons.SPACE, false, false);
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
        txt_state_report_2.setTextColor(color);
        txt_state_report_2.setText(state);
        linlay_state_report_2.setBackgroundColor(color);
    }

}
