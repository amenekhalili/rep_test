package ir.fararayaneh.erp.adaptors.reports_adaptor;

import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;

public class ReportsRecHolderType5 extends BaseRecHolder {

    private IClickRowReportsRecHolderListener holderListener;


    @BindView(R.id.txt_main_title_report_5)
    AppCompatTextView txt_main_title_report_5;
    @BindView(R.id.txt_sub_titile_report_5)
    AppCompatTextView txt_sub_titile_report_5;
    @BindView(R.id.txt_sub_titile_2_report_5)
    AppCompatTextView txt_sub_titile_2_report_5;
    @BindView(R.id.txt_sub_titile_3_report_5)
    AppCompatTextView txt_sub_titile_3_report_5;
    @BindView(R.id.txt_sub_titile_4_report_5)
    AppCompatTextView txt_sub_titile_4_report_5;
    @BindView(R.id.txt_sub_titile_5_report_5)
    AppCompatTextView txt_sub_titile_5_report_5;
    @BindView(R.id.card_row_report_5)
    CardView card_row_report_5;
    @BindView(R.id.linlayclick)
    LinearLayout linlayclick;
    @BindView(R.id.done_work_image_report_5)
    AppCompatImageView done_work_image_report_5;

    @OnClick({R.id.linlayclick})
    public void clickReportsRecHolderType5(View view) {
        clickEdit();
    }


    /**
     * all user can edit, in presenter we prevent non allowed user
     */
    private void clickEdit() {
        if (holderListener != null) {
            holderListener.clickRow(getAdapterPosition(), CommonClickRowReport.EDIT_CLICKED, true);
        }
    }

    ReportsRecHolderType5(View itemView, IClickRowReportsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }


    void setRowData(String placeName,String date,String distributeType,String amount,String deviceName,String description,boolean isAccessdenied) {
        txt_main_title_report_5.setText(placeName);
        txt_sub_titile_report_5.setText(date);
        txt_sub_titile_2_report_5.setText(distributeType);
        txt_sub_titile_3_report_5.setText(amount);
        txt_sub_titile_4_report_5.setText(deviceName);
        txt_sub_titile_5_report_5.setText(description);
        showHideDoneImage(isAccessdenied);
    }

    private void showHideDoneImage(boolean showHide){
        done_work_image_report_5.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

}


