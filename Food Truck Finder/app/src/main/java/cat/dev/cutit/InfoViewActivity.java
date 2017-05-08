package cat.dev.cutit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

public class InfoViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_view);

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.menu);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.VERTICAL));

        DatabaseReference reference = FirebaseDatabase.getInstance()
                .getReference("/vendors/" + getIntent().getStringExtra(
                        GoogleMapsFragment.EXTRA_VENDOR_ID));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Vendor vendor = dataSnapshot.getValue(Vendor.class);

                ((TextView) findViewById(R.id.business_name)).setText(vendor.getBusinessName());
                ((TextView) findViewById(R.id.business_description)).setText(vendor.getDescription());
                // ((TextView) findViewById(R.id.business_hours)).setText("");

                recyclerView.setAdapter(new MenuAdapter(vendor.getMenu()));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("InfoViewActivity", databaseError.toException());
            }
        });
    }

    private static class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {

        private ArrayList<MenuItem> menu;

        MenuAdapter(ArrayList<MenuItem> menu) {
            this.menu = menu;
        }

        @Override
        public MenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View menuItem = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.menu_item, parent, false);

            return new ViewHolder(menuItem);
        }

        @Override
        public void onBindViewHolder(MenuAdapter.ViewHolder holder, int position) {
            MenuItem item = menu.get(position);
            holder.nameView.setText(item.getName());
            holder.descriptionView.setText(item.getDescription());
            holder.priceView.setText(String.format(Locale.US, "$%.2f", item.getPrice()));
        }

        @Override
        public int getItemCount() {
            return menu.size();
        }

        static class ViewHolder extends RecyclerView.ViewHolder {

            ImageView imageView;
            TextView descriptionView;
            TextView nameView;
            TextView priceView;

            ViewHolder(View view) {
                super(view);

                imageView = (ImageView) view.findViewById(R.id.menu_item_image);
                descriptionView = (TextView) view.findViewById(R.id.menu_item_description);
                nameView = (TextView) view.findViewById(R.id.menu_item_name);
                priceView = (TextView) view.findViewById(R.id.menu_item_price);
            }
        }
    }
}
