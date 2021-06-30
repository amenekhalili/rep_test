package ir.fararayaneh.erp.fragments.main;


import android.graphics.Canvas;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import ir.fararayaneh.erp.R;
import ir.fararayaneh.erp.adaptors.facilities_adaptor.FacilitiesRecAdaptor;
import ir.fararayaneh.erp.commons.Commons;
import ir.fararayaneh.erp.custom_views.custom_recycle_view.CustomRecycleView;
import ir.fararayaneh.erp.data.models.middle.FacilitiesModel;
import ir.fararayaneh.erp.utils.intent_handler.IntentReceiverHandler;


/**
 * this fragment should have bundle
 * * no need to Architecture
 * * were checked by eng.Rezaee that this fragment only  will handle clicks
 * * 97.10.04
 */
public class MainFragment extends Fragment {

    @BindView(R.id.custom_recycle_main_frag)
    CustomRecycleView custom_recycle_main_frag;
    private FacilitiesRecAdaptor facilitiesRecAdaptor;
    private View view;
    private Unbinder unbinder;

    public MainFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container, false);
        unbinder=ButterKnife.bind(this, view);
        setupCleanRecycle();
        getProperData();
        return view;
    }

    private void setupCleanRecycle() {
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(Commons.RECYCLER_SPAN_COUNT_2,StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        custom_recycle_main_frag.setLayoutManager(staggeredGridLayoutManager);
        custom_recycle_main_frag.setHasFixedSize(true);
        setRecycleAdaptor();
    }

    private void setRecycleAdaptor() {
        facilitiesRecAdaptor = new FacilitiesRecAdaptor(getActivity());
        custom_recycle_main_frag.setAdapter(facilitiesRecAdaptor);
    }

    private void getProperData() {
        List<FacilitiesModel> facilitiesModelList = IntentReceiverHandler.getIntentData(Objects.requireNonNull(getArguments())).getFacilitiesModelList();
        facilitiesRecAdaptor.setDataModelList(facilitiesModelList);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroyView() {
        //preventLeakMemory();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        preventLeakMemory();
        unbinder.unbind();
        super.onDestroy();
    }

    /**
     * for prevent leak memory
     */
    private void preventLeakMemory() {
        //((MainActivity) Objects.requireNonNull(getActivity())).getView().clearFragmentFromList(this);
        facilitiesRecAdaptor=null;
        custom_recycle_main_frag=null;
        view=null;
    }
}
