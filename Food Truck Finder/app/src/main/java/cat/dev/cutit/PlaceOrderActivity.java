package cat.dev.cutit;

<<<<<<< HEAD
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
=======
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

>>>>>>> presentation

public class PlaceOrderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
<<<<<<< HEAD
    }
=======
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

>>>>>>> presentation
}
