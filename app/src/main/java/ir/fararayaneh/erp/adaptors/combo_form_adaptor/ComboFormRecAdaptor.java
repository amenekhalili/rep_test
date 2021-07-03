package ir.fararayaneh.erp.adaptors.combo_form_adaptor;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.commons.CommonComboFormType;
import ir.fararayaneh.erp.data.models.middle.ComboFormModel;

public class  ComboFormRecAdaptor extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements IClickRowComboFormRecHolderListener {

    private Context context;
    private IClickComboFormRecAdaptor clickComboFormRecAdaptor;
    private List<ComboFormModel> itemList = new ArrayList<>();

    /**
     * viewRowTypeList :
     * شامل انواع ویو تایپ های موجود در هر ردیف از ریسایکل که مقادیر آن یکی از ثابت های کلاس
     * CommonComboFormType
     * است
     * <p>
     * دقت شود که یک فرم کد یک یا چند ویو تایپ دارد
     * <p>
     * comboFormClickType :
     * شامل انواع کلیک تایپ موجد در فرم است که مقادیر ان در هر فرم یکتا بوده و از کلاس
     * CommonComboClickType
     * خوانده میشود
     * به کمک این مقدار و یکتا بودن ان در هر فرم، میتوانیم تشخیص دهیم که
     * که کدام سطر کلیک شده است و مقدار آنرا به روز نماییم
     * نکته اینکه بر خلاف
     * CommonComboFormType
     * مقدار
     * comboFormClickType
     * تکراری نیست
     */
    private List<Integer> viewRowTypeList = new ArrayList<>();
    private List<Integer> comboFormClickType = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;

    public ComboFormRecAdaptor(Context context) {
        this.context = context;
    }


    public void setViewRowTypeList(GridLayoutManager gridLayoutManager, List<Integer> viewRowTypeList, ArrayList<Integer> comboFormClickType, List<ComboFormModel> itemList) {
        this.gridLayoutManager = gridLayoutManager;
        this.viewRowTypeList.clear();
        this.viewRowTypeList.addAll(viewRowTypeList);
        this.comboFormClickType.clear();
        this.comboFormClickType.addAll(comboFormClickType);
        setDataModelList(itemList);
    }

    private void setDataModelList(List<ComboFormModel> itemList) {
        this.itemList.clear();
        this.itemList.addAll(itemList);
        notifyDataSetChanged();
    }

    public void setClickRecycle(IClickComboFormRecAdaptor clickComboFormRecAdaptor) {
        this.clickComboFormRecAdaptor = clickComboFormRecAdaptor;
    }

    @NonNull
    @Override
    // TODO: 2/3/2019 تکمیل برای بقیه تایپ ها
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_1:
                return new ComboFormRecHolderType1(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_2:
                return new ComboFormRecHolderType2(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_3:
                return new ComboFormRecHolderType3(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_4:
                return new ComboFormRecHolderType4(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_5:
                return new ComboFormRecHolderType5(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_6:
                return new ComboFormRecHolderType6(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_7:
                return new ComboFormRecHolderType7(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_8:
                return new ComboFormRecHolderType8(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_9:
                return new ComboFormRecHolderType9(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_10:
                return new ComboFormRecHolderType10(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_11:
                return new ComboFormRecHolderType11(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_12:
                return new ComboFormRecHolderType12(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_13:
                return new ComboFormRecHolderType13(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_14:
                return new ComboFormRecHolderType14(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_15:
                return new ComboFormRecHolderType15(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_16:
                return new ComboFormRecHolderType16(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_17:
                return new ComboFormRecHolderType17(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_18:
                return new ComboFormRecHolderType18(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_19:
                return new ComboFormRecHolderType19(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_20:
                return new ComboFormRecHolderType20(viewTypeFactory(viewGroup, viewType), this);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_21:
                return new ComboFormRecHolderType21(viewTypeFactory(viewGroup, viewType), this);
            default:
                return null;
        }
    }

    // TODO: 2/3/2019 تکمیل برای بقیه تایپ ها
    private View viewTypeFactory(@NonNull ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_1:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_1, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_2:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_2, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_3:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_3, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_4:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_4, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_5:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_5, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_6:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_6, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_7:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_7, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_8:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_8, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_9:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_9, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_10:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_10, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_11:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_11, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_12:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_12, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_13:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_13, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_14:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_14, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_15:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_15, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_16:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_16, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_17:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_17, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_18:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_18, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_19:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_19, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_20:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_20, viewGroup, false);
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_21:
                return LayoutInflater.from(context).inflate(R.layout.row_combo_form_type_21, viewGroup, false);
            default:
                return null;
        }
    }

    @Override
    // TODO: 2/3/2019 تکمیل برای بقیه تایپ ها
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        switch (viewRowTypeList.get(viewHolder.getAdapterPosition())) {
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_1:
                ComboFormRecHolderType1 comboFormRecHolderType1 = (ComboFormRecHolderType1) viewHolder;
                setupBindingType1(comboFormRecHolderType1);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_2:
                ComboFormRecHolderType2 comboFormRecHolderType2 = (ComboFormRecHolderType2) viewHolder;
                setupBindingType2(comboFormRecHolderType2);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_3:
                ComboFormRecHolderType3 comboFormRecHolderType3 = (ComboFormRecHolderType3) viewHolder;
                setupBindingType3(comboFormRecHolderType3);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_4:
                ComboFormRecHolderType4 comboFormRecHolderType4 = (ComboFormRecHolderType4) viewHolder;
                setupBindingType4(comboFormRecHolderType4);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_5:
                ComboFormRecHolderType5 comboFormRecHolderType5 = (ComboFormRecHolderType5) viewHolder;
                setupBindingType5(comboFormRecHolderType5);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_6:
                ComboFormRecHolderType6 comboFormRecHolderType6 = (ComboFormRecHolderType6) viewHolder;
                setupBindingType6(comboFormRecHolderType6);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_7:
                ComboFormRecHolderType7 comboFormRecHolderType7 = (ComboFormRecHolderType7) viewHolder;
                setupBindingType7(comboFormRecHolderType7);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_8:
                ComboFormRecHolderType8 comboFormRecHolderType8 = (ComboFormRecHolderType8) viewHolder;
                setupBindingType8(comboFormRecHolderType8);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_9:
                ComboFormRecHolderType9 comboFormRecHolderType9 = (ComboFormRecHolderType9) viewHolder;
                setupBindingType9(comboFormRecHolderType9);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_10:
                ComboFormRecHolderType10 comboFormRecHolderType10 = (ComboFormRecHolderType10) viewHolder;
                setupBindingType10(comboFormRecHolderType10);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_11:
                ComboFormRecHolderType11 comboFormRecHolderType11 = (ComboFormRecHolderType11) viewHolder;
                setupBindingType11(comboFormRecHolderType11);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_12:
                ComboFormRecHolderType12 comboFormRecHolderType12 = (ComboFormRecHolderType12) viewHolder;
                setupBindingType12(comboFormRecHolderType12);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_13:
                ComboFormRecHolderType13 comboFormRecHolderType13 = (ComboFormRecHolderType13) viewHolder;
                setupBindingType13(comboFormRecHolderType13);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_14:
                ComboFormRecHolderType14 comboFormRecHolderType14 = (ComboFormRecHolderType14) viewHolder;
                setupBindingType14(comboFormRecHolderType14);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_15:
                ComboFormRecHolderType15 comboFormRecHolderType15 = (ComboFormRecHolderType15) viewHolder;
                setupBindingType15(comboFormRecHolderType15);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_16:
                ComboFormRecHolderType16 comboFormRecHolderType16 = (ComboFormRecHolderType16) viewHolder;
                setupBindingType16(comboFormRecHolderType16);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_17:
                ComboFormRecHolderType17 comboFormRecHolderType17 = (ComboFormRecHolderType17) viewHolder;
                setupBindingType17(comboFormRecHolderType17);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_18:
                ComboFormRecHolderType18 comboFormRecHolderType18 = (ComboFormRecHolderType18) viewHolder;
                setupBindingType18(comboFormRecHolderType18);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_19:
                ComboFormRecHolderType19 comboFormRecHolderType19 = (ComboFormRecHolderType19) viewHolder;
                setupBindingType19(comboFormRecHolderType19);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_20:
                ComboFormRecHolderType20 comboFormRecHolderType20 = (ComboFormRecHolderType20) viewHolder;
                setupBindingType20(comboFormRecHolderType20);
                break;
            case CommonComboFormType.COMMON_COMBO_FORM_TYPE_21:
                ComboFormRecHolderType21 comboFormRecHolderType21 = (ComboFormRecHolderType21) viewHolder;
                setupBindingType21(comboFormRecHolderType21);
                break;
        }
    }




    private void setupBindingType1(ComboFormRecHolderType1 comboFormRecHolderType1) {
        comboFormRecHolderType1.setRowData(itemList.get(comboFormRecHolderType1.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType1.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType1.getAdapterPosition()).getError());
    }

    private void setupBindingType2(ComboFormRecHolderType2 comboFormRecHolderType2) {
        comboFormRecHolderType2.setRowData(itemList.get(comboFormRecHolderType2.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType2.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType2.getAdapterPosition()).getError());
    }

    private void setupBindingType3(ComboFormRecHolderType3 comboFormRecHolderType3) {
        comboFormRecHolderType3.setRowData(itemList.get(comboFormRecHolderType3.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType3.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType3.getAdapterPosition()).getError());
    }

    private void setupBindingType4(ComboFormRecHolderType4 comboFormRecHolderType4) {
        comboFormRecHolderType4.setComboFormModel(itemList.get(comboFormRecHolderType4.getAdapterPosition()));
        comboFormRecHolderType4.setRowData(itemList.get(comboFormRecHolderType4.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType4.getAdapterPosition()).getValue());
    }

    private void setupBindingType5(ComboFormRecHolderType5 comboFormRecHolderType5) {
        comboFormRecHolderType5.setComboFormModel(itemList.get(comboFormRecHolderType5.getAdapterPosition()));
        comboFormRecHolderType5.setRowData(itemList.get(comboFormRecHolderType5.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType5.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType5.getAdapterPosition()).isOn());
    }

    private void setupBindingType6(ComboFormRecHolderType6 comboFormRecHolderType6) {
        comboFormRecHolderType6.setRowData(itemList.get(comboFormRecHolderType6.getAdapterPosition()).getValue());
    }

    private void setupBindingType7(ComboFormRecHolderType7 comboFormRecHolderType7) {
        comboFormRecHolderType7.setComboFormModel(itemList.get(comboFormRecHolderType7.getAdapterPosition()));
        comboFormRecHolderType7.setRowData(itemList.get(comboFormRecHolderType7.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType7.getAdapterPosition()).getValue());
    }

    private void setupBindingType8(ComboFormRecHolderType8 comboFormRecHolderType8) {
        comboFormRecHolderType8.setRowData(itemList.get(comboFormRecHolderType8.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType8.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType8.getAdapterPosition()).getError());
    }

    private void setupBindingType9(ComboFormRecHolderType9 comboFormRecHolderType9) {
        comboFormRecHolderType9.setRowData(itemList.get(comboFormRecHolderType9.getAdapterPosition()).getValue());
    }

    private void setupBindingType10(ComboFormRecHolderType10 comboFormRecHolderType10) {
        comboFormRecHolderType10.setRowData(itemList.get(comboFormRecHolderType10.getAdapterPosition()).isOn());
    }

    private void setupBindingType11(ComboFormRecHolderType11 comboFormRecHolderType11) {
        //no have any update
    }

    private void setupBindingType12(ComboFormRecHolderType12 comboFormRecHolderType12) {
        //no have any update
    }

    private void setupBindingType13(ComboFormRecHolderType13 comboFormRecHolderType13) {
        //no have any update
    }

    private void setupBindingType14(ComboFormRecHolderType14 comboFormRecHolderType14) {
        comboFormRecHolderType14.setComboFormModel(itemList.get(comboFormRecHolderType14.getAdapterPosition()));
        comboFormRecHolderType14.setRowData(itemList.get(comboFormRecHolderType14.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType14.getAdapterPosition()).getValue());
    }

    private void setupBindingType15(ComboFormRecHolderType15 comboFormRecHolderType15) {
        comboFormRecHolderType15.setRowData(itemList.get(comboFormRecHolderType15.getAdapterPosition()).getValue());
    }

    private void setupBindingType16(ComboFormRecHolderType16 comboFormRecHolderType16) {
        comboFormRecHolderType16.setRowData(itemList.get(comboFormRecHolderType16.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType16.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType16.getAdapterPosition()).getError());
    }

    private void setupBindingType17(ComboFormRecHolderType17 comboFormRecHolderType17) {
        //no have any update
    }

    private void setupBindingType18(ComboFormRecHolderType18 comboFormRecHolderType18) {
        comboFormRecHolderType18.setComboFormModel(itemList.get(comboFormRecHolderType18.getAdapterPosition()));
        comboFormRecHolderType18.setRowData(itemList.get(comboFormRecHolderType18.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType18.getAdapterPosition()).getValue());
    }

    private void setupBindingType19(ComboFormRecHolderType19 comboFormRecHolderType19) {
        comboFormRecHolderType19.setComboFormModel(itemList.get(comboFormRecHolderType19.getAdapterPosition()));
        comboFormRecHolderType19.setRowData(itemList.get(comboFormRecHolderType19.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType19.getAdapterPosition()).getValue());
    }

    private void setupBindingType20(ComboFormRecHolderType20 comboFormRecHolderType20) {
        comboFormRecHolderType20.setRowData(itemList.get(comboFormRecHolderType20.getAdapterPosition()).getTitle(),
                itemList.get(comboFormRecHolderType20.getAdapterPosition()).getValue(), itemList.get(comboFormRecHolderType20.getAdapterPosition()).getError());
    }

    private void setupBindingType21(ComboFormRecHolderType21 comboFormRecHolderType21) {
        comboFormRecHolderType21.setComboFormModel(itemList.get(comboFormRecHolderType21.getAdapterPosition()));
        comboFormRecHolderType21.setRowData(itemList.get(comboFormRecHolderType21.getAdapterPosition()).getTitle(), itemList.get(comboFormRecHolderType21.getAdapterPosition()).getValue());
    }


    @Override
    public int getItemCount() {
        return viewRowTypeList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return viewRowTypeList.get(position);
    }

    // TODO: 2/3/2019 ایجاد متد برای بقیه تایپ ها
    public void updateRowValue(int clickType, ComboFormModel formModel, boolean shouldScrool) {
        if (comboFormClickType.contains(clickType)) {
            int position = comboFormClickType.indexOf(clickType);
            if (shouldScrool) {
                scroolLayoutManager(position);
            }
            switch (viewRowTypeList.get(comboFormClickType.indexOf(clickType))) {
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_1:
                    updateRowValueType1(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_2:
                    updateRowValueType2(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_3:
                    updateRowValueType3(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_4:
                    //do nothing, row were updated in holder
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_5:
                    //do nothing, row were updated in holder
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_6:
                    updateRowValueType6(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_7:
                    //do nothing, row were updated in holder
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_8:
                    updateRowValueType8(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_9:
                    updateRowValueType9(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_10:
                    updateRowValueType10(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_11:
                    //no need to update
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_12:
                    //no need to update
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_13:
                    //no need to update
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_14:
                    updateRowValueType14(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_15:
                    updateRowValueType15(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_16:
                    updateRowValueType16(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_17:
                    //no need to update
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_18:
                    //do nothing, row were updated in holder
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_19:
                    updateRowValueType19(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_20:
                    updateRowValueType20(position, formModel);
                    break;
                case CommonComboFormType.COMMON_COMBO_FORM_TYPE_21:
                    updateRowValueType21(position, formModel);
                    break;
            }
        }
    }




    private void updateRowValueType1(int position, ComboFormModel formModel) {
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType2(int position, ComboFormModel formModel) {
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType3(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType6(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType8(int position, ComboFormModel formModel) {
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType9(int position, ComboFormModel formModel) {
        itemList.get(position).setValue(formModel.getValue());
        notifyItemChanged(position);
    }

    private void updateRowValueType10(int position, ComboFormModel formModel) {
        itemList.get(position).setOn(formModel.isOn());
        notifyItemChanged(position);
    }

    private void updateRowValueType14(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        notifyItemChanged(position);
    }

    private void updateRowValueType15(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        notifyItemChanged(position);
    }

    private void updateRowValueType16(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType19(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        notifyItemChanged(position);
    }

    private void updateRowValueType20(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        itemList.get(position).setError(formModel.getError());
        notifyItemChanged(position);
    }

    private void updateRowValueType21(int position, ComboFormModel formModel) {
        itemList.get(position).setTitle(formModel.getTitle());
        itemList.get(position).setValue(formModel.getValue());
        notifyItemChanged(position);
    }

    @Override
    public void clickRow(int position, String someValue) {
        if (clickComboFormRecAdaptor != null) {
            clickComboFormRecAdaptor.click(comboFormClickType.get(position), someValue);
        }
    }

    private void scroolLayoutManager(int position) {
        if (gridLayoutManager != null) {
            gridLayoutManager.scrollToPosition(position);
            Log.i("arash", "scroolLayotuManager: ");
        }
    }
}
