package team6.cmpt276.greenfoodchallenge.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import team6.cmpt276.greenfoodchallenge.R;
import team6.cmpt276.greenfoodchallenge.classes.Meal;

public class AddMeal extends AppCompatActivity implements PlaceSelectionListener  {
    private static final String TAG = "Tag" ;
    private ImageView imageView;

    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;

    //Firebase
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private DatabaseReference database;
    private FirebaseAuth mAuth;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    private String mealName;
    private String mealDescription;
    private String restaurantAddress;
    private String restaurantCity;
    private String restaurantName;
    private String protein;
    private Float mealRating;

    private EditText mealNameEdit;
    private EditText mealDescriptionEdit;
    private PlaceAutocompleteFragment autocompleteFragment;
    private TextView address;
    private Spinner proteinSpinner;
    private RatingBar ratingBar;

    private Geocoder mGeocoder = new Geocoder(this, Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Meal");

        database = FirebaseDatabase.getInstance().getReference();

        //Initialize UI elements
        mealNameEdit = (EditText) findViewById(R.id.mealName);
        mealDescriptionEdit = (EditText) findViewById(R.id.mealDescription);
        proteinSpinner = (Spinner) findViewById(R.id.protein);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);


        //Set up Google Autocomplete Fragment
        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        address = (TextView) findViewById(R.id.address);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setCountry("CA")
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(this);


        //Set up Rating Bar Listener
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                mealRating = rating;
                Log.i("rating", mealRating.toString());
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        imageView = findViewById(R.id.testImage);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public void uploadImage(String fileName, String userId) {
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        String fileDirectory = "meals/" + userId;

        if(filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();


            StorageReference ref = storageReference.child(fileDirectory + fileName);
            ref.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(AddMeal.this, "Uploaded", Toast.LENGTH_SHORT).show();
                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddMeal.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
    }


    // Upload the meal onto the database
    public void submitMeal(View view){
        mealName = mealNameEdit.getText().toString();
        mealDescription = mealDescriptionEdit.getText().toString();
        protein = proteinSpinner.getSelectedItem().toString();
        mealRating = ratingBar.getRating();

        //Check required fields
        if (mealName == null || restaurantName == null) {
            if(mealName == null){
                mealNameEdit.setError("This Field cannot be blank");
            }
            if(restaurantName == null){
                address.setText("This Field cannot be blank");
                address.setTextColor(getResources().getColor(R.color.red));
            }
            return;
        }


        String fileName = UUID.randomUUID().toString();
        String userId = user.getUid();
        uploadImage(fileName, userId);

        Meal meal = new Meal(userId,mealName,protein,restaurantName,restaurantAddress,restaurantCity, fileName);

        if (mealDescription != null){
            meal.setMealDescription(mealDescription);
        }
        if (mealRating != null) {
            meal.setRating(mealRating);
        }
        String key = database.child("meals").push().getKey();
        database.child("meals").child(key).setValue(meal);

        //Start a new intent
        /*Intent intent = new Intent(AddMeal.this, UserProfile.class);
        startActivity(intent);*/
    }
    public void clickAddImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //Callback when a place has been selected
    @Override
    public void onPlaceSelected(Place place) {
        Log.i("Place", "Place Selected: " + place.getName());
        address.setText(place.getName() + "\n" + place.getAddress());
        address.setTextColor(getResources().getColor(R.color.black));

        /*Extracting Restaurant Information*/
        //Extracting Address
        restaurantAddress = place.getAddress().toString();

        //Extracting City
        try {
            getCityInfo(place.getLatLng().latitude, place.getLatLng().longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //Extracting Name
        restaurantName = place.getName().toString();
    }

    // Callback when placeAutoComplete returns an error
    @Override
    public void onError(Status status) {
        Log.e("Place", "onError: Status = " + status.toString());
        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();

    }

    //Get lattitude and longtitude of a place, return the city it is associated with
    private void getCityInfo(double lat, double lon) throws IOException {
        List<Address> addresses = mGeocoder.getFromLocation(lat, lon, 1);

        if (addresses.get(0).getLocality() != null) {
            restaurantCity = addresses.get(0).getLocality();
            Log.d("CITY",restaurantCity);
        }
    }
}
