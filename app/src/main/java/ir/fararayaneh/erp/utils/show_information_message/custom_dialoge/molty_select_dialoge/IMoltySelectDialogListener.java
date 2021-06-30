package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.molty_select_dialoge;

import java.util.ArrayList;

public interface IMoltySelectDialogListener {

     void cancel();

     void confirm(ArrayList<Integer> selectedIds, ArrayList<String> selectedNames);
}
