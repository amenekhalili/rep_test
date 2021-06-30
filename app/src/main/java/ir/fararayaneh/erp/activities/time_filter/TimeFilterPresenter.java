package ir.fararayaneh.erp.activities.time_filter;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.Date;

import ir.fararayaneh.erp.IBase.common_base.BasePresenter;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.commons.CommonsFormat;
import ir.fararayaneh.erp.data.models.IModels;
import ir.fararayaneh.erp.data.models.UserMessageModel;
import ir.fararayaneh.erp.data.models.middle.IntentModel;
import ir.fararayaneh.erp.data.models.online.request.socket.ISocketModel;
import ir.fararayaneh.erp.utils.intent_handler.ActivityIntents;
import ir.fararayaneh.erp.utils.time_helper.CustomDatePicker;
import ir.fararayaneh.erp.utils.time_helper.CustomTimeHelper;
import ir.fararayaneh.erp.utils.time_helper.ISelectDateListener;

/**
 * here we get formCode(reportCode) and start and stop date
 * after that, filter values between those date
 * and return arrays of ids or guid
 */
public class TimeFilterPresenter extends BasePresenter<TimeFilterView> {

    private ITimeFilterClickListener iTimeFilterClickListener;
    private ISelectDateListener iSelectDateListener;
    private String myFormCode = Commons.NULL_INTEGER;
    private Date startDate, endDate;

    public TimeFilterPresenter(WeakReference<TimeFilterView> weekView, Context context, boolean shouldHaveIntent) {
        super(weekView, context, shouldHaveIntent);
    }

    @Override
    protected UserMessageModel makeProperShowDialogueData(int dialogueType, int commonComboClickType, String someValue) {
        return null;
    }

    @Override
    protected void workForListenToMSGOrDialoge(int whichDialog, int whichClick, IModels iModels) {

    }

    @Override
    protected void updateUIWhenReceivedAttachment() {

    }

    @Override
    protected void workForSocketListener(ISocketModel iSocketModel) {

    }

    @Override
    protected void ClearAllPresenterListener() {
        iTimeFilterClickListener = null;
        iSelectDateListener = null;
    }

    @Override
    public void click() {

        iTimeFilterClickListener = new ITimeFilterClickListener() {
            @Override
            public void clickStartDate() {
                setupStartDate();

            }

            @Override
            public void clickEndDate() {
                setupEndDate();
            }

            @Override
            public void clickConfirm() {
                setupFilterProcess();
            }
        };

        if (checkNull()) {
            getView().setTimeFilterClickListener(iTimeFilterClickListener);
        }
    }


    @Override
    public void onCreate(IntentModel intentModel) {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onBackPress() {
        cancelResultIntent();
    }

    //----------------------------------------------------------------------------------------------
    private void setupFilterProcess() {
        if (startDate == null || endDate == null) {
            if (checkNull()) {
                getView().showMessageSetTwoTimeError();
            }
        } else if (CustomTimeHelper.getDiffDate(startDate, endDate) < 0) {
            if (checkNull()) {
                getView().showMessageStartDateAfterEndDate();
            }
        } else {
            if (checkNull()) {
                ActivityIntents.resultOkFromTimeFilterAct(getView().getActivity(), startDate, endDate);
            }
        }
    }

    private void setupEndDate() {
        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {
                endDate = date;
                if (checkNull()) {
                    getView().setEndDate(String.format(CommonsFormat.FORMAT_3,context.getResources().getString(R.string.end_date),CustomTimeHelper.getPresentableNumberDate(date,context)));
                }
            }

            @Override
            public void onDateNotSelection() {

            }
        };
        if (checkNull()) {
            CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
        }
    }

    private void setupStartDate() {

        iSelectDateListener = new ISelectDateListener() {
            @Override
            public void onDateSelection(Date date) {
                startDate = date;
                if (checkNull()) {
                    getView().setStartDate(String.format(CommonsFormat.FORMAT_3, context.getResources().getString(R.string.start_date), CustomTimeHelper.getPresentableNumberDate(date, context)));
                }
            }

            @Override
            public void onDateNotSelection() {

            }
        };
        if (checkNull()) {
            CustomDatePicker.showProperDateTimePicker(getView().getActivity(), iSelectDateListener);
        }
    }
}
