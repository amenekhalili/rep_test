package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.molty_select_dialoge;

import androidx.appcompat.app.AppCompatActivity;

import com.abdeveloper.library.MultiSelectDialog;
import com.abdeveloper.library.MultiSelectModel;

import java.util.ArrayList;

import ir.fararayaneh.erp.R;

public class MoltySelectDialogHelper {

    public static void show(AppCompatActivity activity, ArrayList<MultiSelectModel> multiSelectModelArrayList, IMoltySelectDialogListener iMoltySelectDialogListener){


        MultiSelectDialog multiSelectDialog = new MultiSelectDialog()
                .title(activity.getString(R.string.please_select))
                .titleSize(14)
                .positiveText(activity.getString(R.string.ok))
                .negativeText(activity.getString(R.string.cancel))
                .setMinSelectionLimit(1) //you can set minimum checkbox selection limit (Optional)
                .multiSelectList(multiSelectModelArrayList) // the multi select model list with ids and name
                .onSubmit(new MultiSelectDialog.SubmitCallbackListener() {
                    @Override
                    public void onSelected(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames, String dataString) {
                        if(iMoltySelectDialogListener !=null){
                            iMoltySelectDialogListener.confirm(selectedIds,selectedNames);
                        }
                    }

                    @Override
                    public void onCancel() {
                        if(iMoltySelectDialogListener !=null){
                            iMoltySelectDialogListener.cancel();
                        }
                    }
                });

        multiSelectDialog.show(activity.getSupportFragmentManager(), "multiSelectDialog");
    }
}
