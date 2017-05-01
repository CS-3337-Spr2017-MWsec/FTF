package cat.dev.cutit;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;


public class PlaceOrderFragment extends Fragment {

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    public DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private static String TAG = "PlaceOrderFragment";
    private int MAX_ORDER_ITEMS = 20;
    private int totalOrderItems = 0;
    private View mRegisterForm;
    private View mProgressView;
    private String orderKey = ref.push().getKey();


    private FirebaseAuth mAuthentication;

    @Override


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Place Order");

        View view = inflater.inflate(R.layout.fragment_place_order, container, false);

        Button login = (Button)view.findViewById(R.id.place_order_button);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new OrderPaymentFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        return view;

    }
    public void getOrder(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Order order = dataSnapshot.getValue(Order.class);
                System.out.println(order);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }


}
