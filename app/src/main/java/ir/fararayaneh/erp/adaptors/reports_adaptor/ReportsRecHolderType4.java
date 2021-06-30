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


public class ReportsRecHolderType4 extends BaseRecHolder {

    private IClickRowReportsRecHolderListener holderListener;


    @BindView(R.id.txt_main_title_report_4)
    AppCompatTextView txt_main_title_report_4;
    @BindView(R.id.txt_sub_titile_report_4)
    AppCompatTextView txt_sub_titile_report_4;
    @BindView(R.id.txt_sub_2_report_4)
    AppCompatTextView txt_sub_2_report_4;
    @BindView(R.id.txt_form_number_report_4)
    AppCompatTextView txt_form_number_report_4;
    @BindView(R.id.txt_form_date_report_4)
    AppCompatTextView txt_form_date_report_4;
    @BindView(R.id.txt_driver_name_report_4)
    AppCompatTextView txt_driver_name_report_4;
    @BindView(R.id.txt_fuel_name_report_4)
    AppCompatTextView txt_fuel_name_report_4;
    @BindView(R.id.txt_main_content_report_4)
    AppCompatTextView txt_main_content_report_4;
    @BindView(R.id.txt_sub_3_report_4)
    AppCompatTextView txt_sub_3_report_4;
    @BindView(R.id.linlayclick)
    LinearLayout linlayclick;
    @BindView(R.id.done_work_image_report_4)
    AppCompatImageView done_work_image_report_4;



    /*@OnClick({R.id.txt_main_title_report_4,
            R.id.txt_sub_titile_report_4,
            R.id.txt_sub_2_report_4,
            R.id.txt_form_number_report_4,
            R.id.txt_form_date_report_4,
            R.id.txt_driver_name_report_4,
            R.id.txt_fuel_name_report_4,
            R.id.txt_main_content_report_4})
    public void clickReportsRecHolderType4(View view) {
                clickEdit();
    } */

    @OnClick({R.id.linlayclick})
    public void clickReportsRecHolderType4(View view) {
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



    ReportsRecHolderType4(View itemView, IClickRowReportsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }


    void setRowData(String mainTitle,String subTitle,String subTitle2,String formNumber,String formDate,String driverName,String fuelName,String mainContent,String unitName,boolean showDoneImage) {

        txt_main_title_report_4.setText(mainTitle);
        txt_sub_titile_report_4.setText(subTitle);
        txt_sub_2_report_4.setText(subTitle2);
        txt_form_number_report_4.setText(formNumber);
        txt_form_date_report_4.setText(formDate);
        txt_driver_name_report_4.setText(driverName);
        txt_fuel_name_report_4.setText(fuelName);
        txt_main_content_report_4.setText(mainContent);
        txt_sub_3_report_4.setText(unitName);
        showHideDoneImage(showDoneImage);
    }

    private void showHideDoneImage(boolean showHide){
        done_work_image_report_4.setVisibility(showHide?View.VISIBLE:View.GONE);
    }

}


