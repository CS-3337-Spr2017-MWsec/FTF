package cat.dev.cutit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;


public class PlaceOrderFragment extends Fragment {

    private static String TAG = "PlaceOrderFragment";
    private int MAX_ORDER_ITEMS = 20;
    private int totalOrderItems = 0;
    private EditText mOrderItems[]=new EditText[MAX_ORDER_ITEMS];
    private EditText mOrderItemsDescription[] = new EditText[MAX_ORDER_ITEMS];
    private EditText mOrderItemsQuantity[] = new EditText[MAX_ORDER_ITEMS];
    private View mRegisterForm;
    private View mProgressView;



    private FirebaseAuth mAuthentication;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Place Order");
        return inflater.inflate(R.layout.fragment_place_order, container, false);
    }

}
