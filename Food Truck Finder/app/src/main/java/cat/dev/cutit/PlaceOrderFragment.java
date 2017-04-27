package cat.dev.cutit;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.ArrayList;


public class PlaceOrderFragment extends Fragment {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private static String TAG = "PlaceOrderFragment";
    private int MAX_ORDER_ITEMS = 20;
    private int totalOrderItems = 0;
    private EditText mOrderItems[]=new EditText[MAX_ORDER_ITEMS];
    private EditText mOrderItemsDescription[] = new EditText[MAX_ORDER_ITEMS];
    private EditText mOrderItemsQuantity[] = new EditText[MAX_ORDER_ITEMS];
    private View mRegisterForm;
    private View mProgressView;
    private String orderKey = ref.push().getKey();


    private FirebaseAuth mAuthentication;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Place Order");
        return inflater.inflate(R.layout.fragment_place_order, container, false);


    }

    public static class Dinosaur {

        public int height;
        public int weight;

        public Dinosaur(int height, int weight) {
            // ...
        }

    }



    public void getMenu(String vendorId){

    }

}
