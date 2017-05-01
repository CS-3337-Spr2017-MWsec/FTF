package cat.dev.cutit;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static String TAG = "RegisterActivity";

    private EditText mConfirmPasswordView;
    private EditText mEmailView;
    private EditText mFirstNameView;
    private EditText mLastNameView;
    private EditText mPasswordView;
    private View mRegisterForm;
    private View mProgressView;

    private FirebaseAuth mAuthentication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mEmailView = (EditText) findViewById(R.id.email);
        mFirstNameView = (EditText) findViewById(R.id.first_name);
        mLastNameView = (EditText) findViewById(R.id.last_name);
        mPasswordView = (EditText) findViewById(R.id.password);

        mProgressView = findViewById(R.id.sign_up_progress);
        mRegisterForm = findViewById(R.id.register_form);

        Button signUpButton = (Button) findViewById(R.id.sign_up_button);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccount();
            }
        });

        mAuthentication = FirebaseAuth.getInstance();
    }

    private void createAccount() {
        final String firstName = mFirstNameView.getText().toString();
        final String lastName = mLastNameView.getText().toString();
        String password = mPasswordView.getText().toString();

        if (firstName.equals("")) {
            mFirstNameView.setError("First name is required!");
            mFirstNameView.requestFocus();
        }

        if (lastName.equals("")) {
            mLastNameView.setError("Last name is required!");
            mLastNameView.requestFocus();
        }

        if (password.equals(mConfirmPasswordView.getText().toString())) {
            if (password.length() <= 4) {
                mPasswordView.setError("Password is too short!");
                mPasswordView.requestFocus();
                return;
            }

            final String email = mEmailView.getText().toString();

            if (email.equals("")) {
                mEmailView.setError("Email is required!");
                mEmailView.requestFocus();
                return;
            }

            showProgress(true);

            mAuthentication.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d(TAG, "createdUserWithEmail:onComplete:" + task.isSuccessful());

                            if (!task.isSuccessful()) {
                                Toast.makeText(RegisterActivity.this, "Registration failed!",
                                        Toast.LENGTH_SHORT).show();

                                try {
                                    throw task.getException();
                                } catch (Exception exception) {
                                    // TODO Handle errors such as WEAK_PASSWORD and email is already in use
                                    Log.e(TAG, exception.getMessage());
                                }
                            } else {
                                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                DatabaseReference databaseReference = FirebaseDatabase.getInstance()
                                        .getReference();

                                if (firebaseUser != null) {
                                    User user = new User();
                                    user.setFirstName(firstName);
                                    user.setLastName(lastName);
                                    user.setEmail(email);

                                    databaseReference.child("/users/" + firebaseUser.getUid())
                                            .setValue(user);
                                }

                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                            }

                            showProgress(false);
                        }
                    });
        } else {
            mConfirmPasswordView.setError("Password does not match!");
            mConfirmPasswordView.requestFocus();
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        if (Build.VERSION.SDK_INT >= 15) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterForm.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterForm.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
