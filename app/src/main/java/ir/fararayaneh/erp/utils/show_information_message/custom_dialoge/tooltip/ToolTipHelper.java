package ir.fararayaneh.erp.utils.show_information_message.custom_dialoge.tooltip;

import android.content.Context;
import android.view.View;


import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.custom_views.CustomToolTip;
import ir.fararayaneh.erp.utils.GetATTResources;

public class ToolTipHelper {

    private static final long TOOL_TIP_DURATION = 5000;
    private static final int TOOL_TIP_CORNER = 30;

    public static void Show(Context context, View view, String message, CustomToolTip.Position position) {
        CustomToolTip
                .on(view)
                .autoHide(true, TOOL_TIP_DURATION)
                .color(context.getResources().getColor(GetATTResources.resId(context, new int[]{R.attr.colorPrimaryDarkAttr})))
                .corner(TOOL_TIP_CORNER)
                .clickToHide(true)
                .position(position)
                .text(message)
                .show();

    }
}
