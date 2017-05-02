package cat.dev.cutit;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompletedOrderFragment extends Fragment {


    public CompletedOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Thank you for your order!");
        View view = inflater.inflate(R.layout.fragment_completed_order, container, false);

        Button login = (Button)view.findViewById(R.id.finish_order_button);

        login.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Fragment fragment = new ViewOrdersFragment();

                FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();
            }
        });
        return view;
    }

}
