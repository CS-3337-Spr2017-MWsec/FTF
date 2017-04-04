package cat.dev.cutit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText mConfirmPasswordView;
    private EditText mEmailView;
    // private EditText mFirstNameView;
    // private EditText mLastNameView;
    private EditText mPasswordView;

    private void createAccount() {
        String password = mPasswordView.getText().toString();

        if (password.equals(mConfirmPasswordView.getText().toString())) {
            if (password.length() <= 4) {
                mPasswordView.setError("Password is too short!");
                mPasswordView.requestFocus();
                return;
            }

            String email = mEmailView.getText().toString();

            if (email.equals("")) {
                mEmailView.setError("Email is required!");
                mEmailView.requestFocus();
                return;
            }

            // https://firebase.google.com/docs/auth/android/password-auth#create_a_password-based_account
            // mFirebaseAuth = FirebaseAuth.getInstance();
            // mFirebaseAuth.createUserWithEmailAndPassword(email, password)
            //         .addOnCompleteListener(...);

            startActivity(new Intent(this, MainActivity.class));
        } else {
            mConfirmPasswordView.setError("Password does not match!");
            mConfirmPasswordView.requestFocus();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mEmailView = (EditText) findViewById(R.id.email);
        // mFirstNameView = (EditText) findViewById(R.id.first_name);
        // mLastNameView = (EditText) findViewById(R.id.last_name);
        mPasswordView = (EditText) findViewById(R.id.password);

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });
    }
}
