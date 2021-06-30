package ir.fararayaneh.erp.adaptors.reports_adaptor;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.fararayaneh.erp.IBase.Ibase_recycle_view_holder.BaseRecHolder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonViewTypeReport;
import ir.fararayaneh.erp.utils.GetATTResources;


public class ReportsRecHolderType3 extends BaseRecHolder {

    private IClickRowReportsRecHolderListener holderListener;


    @BindView(R.id.frameState)
    FrameLayout frameState;
    @BindView(R.id.txt_form_date_row_report)
    AppCompatTextView txt_form_date_row_report;
    @BindView(R.id.txt_form_number_row_report)
    AppCompatTextView txt_form_number_row_report;
    @BindView(R.id.txt_val1_row_report)
    AppCompatTextView txt_val1_row_report;
    @BindView(R.id.txt_val2_row_report)
    AppCompatTextView txt_val2_row_report;
    @BindView(R.id.txt_val3_row_report)
    AppCompatTextView txt_val3_row_report;
    @BindView(R.id.txt_val4_row_report)
    AppCompatTextView txt_val4_row_report;
    @BindView(R.id.txt_val5_row_report)
    AppCompatTextView txt_val5_row_report;
    @BindView(R.id.txt_val6_row_report)
    AppCompatTextView txt_val6_row_report;
    @BindView(R.id.txt_name1_row_report)
    AppCompatTextView txt_name1_row_report;
    @BindView(R.id.txt_name2_row_report)
    AppCompatTextView txt_name2_row_report;
    @BindView(R.id.txt_name3_row_report)
    AppCompatTextView txt_name3_row_report;
    @BindView(R.id.txt_name4_row_report)
    AppCompatTextView txt_name4_row_report;
    @BindView(R.id.txt_name5_row_report)
    AppCompatTextView txt_name5_row_report;
    @BindView(R.id.txt_name6_row_report)
    AppCompatTextView txt_name6_row_report;


    private int myViewType = CommonViewTypeReport.NORMAL_VIEW_TYPE;


    /*
     * here we have not attach
     * every click go to edit
     * */
    @OnClick({R.id.txt_form_date_row_report, R.id.txt_form_number_row_report,
            R.id.txt_val1_row_report,
            R.id.txt_val2_row_report,
            R.id.txt_val3_row_report,
            R.id.txt_val4_row_report,
            R.id.txt_val5_row_report,
            R.id.txt_val6_row_report,
            R.id.txt_name1_row_report,
            R.id.txt_name2_row_report,
            R.id.txt_name3_row_report,
            R.id.txt_name4_row_report,
            R.id.txt_name5_row_report,
            R.id.txt_name6_row_report
    })
    public void clickReportsRecHolderT3(View view) {
        switch (view.getId()) {
            case R.id.txt_form_date_row_report:
            case R.id.txt_form_number_row_report:
            case R.id.txt_val1_row_report:
            case R.id.txt_val2_row_report:
            case R.id.txt_val3_row_report:
            case R.id.txt_val4_row_report:
            case R.id.txt_val5_row_report:
            case R.id.txt_val6_row_report:
            case R.id.txt_name1_row_report:
            case R.id.txt_name2_row_report:
            case R.id.txt_name3_row_report:
            case R.id.txt_name4_row_report:
            case R.id.txt_name5_row_report:
            case R.id.txt_name6_row_report:
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


    ReportsRecHolderType3(View itemView, IClickRowReportsRecHolderListener holderListener) {
        super(itemView);
        this.holderListener = holderListener;
    }

    @Override
    protected void inflateButterKnife(View itemView) {
        ButterKnife.bind(this, itemView);
    }

    /**
     * @param viewType  : from CommonViewTypeReport
     */
    void setRowData(

                    String name1,String val1,
                    String name2,String val2,
                    String name3,String val3,
                    String name4,String val4,
                    String name5,String val5,
                    String name6,String val6,
                    String formDate,String formNumber, int viewType) {


                    txt_name1_row_report.setText(name1);
                    txt_name2_row_report.setText(name2);
                    txt_name3_row_report.setText(name3);
                    txt_name4_row_report.setText(name4);
                    txt_name5_row_report.setText(name5);
                    txt_name6_row_report.setText(name6);

                    txt_val1_row_report.setText(val1);
                    txt_val2_row_report.setText(val2);
                    txt_val3_row_report.setText(val3);
                    txt_val4_row_report.setText(val4);
                    txt_val5_row_report.setText(val5);
                    txt_val6_row_report.setText(val6);

                    txt_form_date_row_report.setText(formDate);
                    txt_form_number_row_report.setText(formNumber);
                    setupState(viewType);

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

