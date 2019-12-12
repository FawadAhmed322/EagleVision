package com.example.eaglevision;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.w3c.dom.Comment;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;


public class MainActivity extends AppCompatActivity implements login_screen.OnFragmentInteractionListener, Confirm_Screen.OnFragmentInteractionListener,
        main_menu.OnFragmentInteractionListener, upload_media_screen.OnFragmentInteractionListener, DirectoryFragment.OnFragmentInteractionListener, DetailsForm.OnFragmentInteractionListener {
    public static final int VIDEO_CODE = 2345;

    public static final int Image_Request_Code = 7;
    public static final int GALLERY_REQUEST_CODE = 1;
    public static final int CAMERA_REQUEST_CODE = 111;
    public static final String ip_ = "http://35.222.122.93";
    public static final String port_ = "5000";
    ProgressDialog progressDialog;

    main_menu mainMenu = null;

    upload_acqusation acquiringMediaScreen = null;
    upload_media_screen uploadMediaScreen = null;


    public DatabaseReference mDatabase;
    public DirectoryAdapter directoryAdapter;
    public StorageReference mStorageRef;

//Inter Directory Person  Object

    public static Person P;

    //location manager
    LocationManager lm;
    Location location;

    double longitude;
    double latitude;


    Uri FilePathUri;

    // Folder path for Firebase Storage.
    String OrignImage_Path = "gs://eaglevision-e3584.appspot.com/Orignal_Images";//ID image
    String AccqImage_Path = "gs://eaglevision-e3584.appspot.com/Acquisition_Images";
    String ExtractedImage_Path = "gs://eaglevision-e3584.appspot.com/Extracted_Images";

    // Root Database Name for Firebase Database.
    public static final String Database_Path = "gs://eaglevision-e3584.appspot.com/";


    private ListView listView;

    @Override
    public void onAttachFragment(Fragment context) {
        if (context instanceof DetailsForm) {
            DetailsForm form = (DetailsForm) context;
            form.setOnDetailSelectedListener(this);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        P = new Person();
        setContentView(R.layout.activity_main);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReferenceFromUrl(Database_Path);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        @SuppressLint("MissingPermission") Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        ChildEventListener childEventListener = new ChildEventListener() {
            // Adding click listener to Choose image button.

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                // ...
            }


            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {

                Comment newComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();
                // ...
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

                Comment movedComment = dataSnapshot.getValue(Comment.class);
                String commentKey = dataSnapshot.getKey();

                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


            //---------------------------/


        };


    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }


    public void userSignedIn(boolean signedIn) {

    }


    @Override
    public void onFragmentInteraction(Person person) {

        Confirm_Screen personinfoFrag = (Confirm_Screen)
                getSupportFragmentManager().findFragmentById(R.id.confirm_Screen);

        //    Log.d("name", "Taimoor");

        if (personinfoFrag != null) {
            // If confirm frag is available, we're in two-pane layout...

            // Call a method in the confirm to update its content
            personinfoFrag.SetPerson(person);

            Log.d("name", "Taimoor");


        } else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.nav_host_fragment, personinfoFrag);
            transaction.addToBackStack(null);

            Log.d("name", "I am in else you dummies");
            personinfoFrag.SetPerson(person);
            transaction.commit();
        }
    }


    public DatabaseReference getDatabaseReference() {
        return this.mDatabase;
    }


    public StorageReference getStorageReference() {
        return mStorageRef;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Log.d("videoUrihere", requestCode + " " + resultCode);


        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());



        uploadMediaScreen = (upload_media_screen) getSupportFragmentManager().findFragmentById(R.id.upload_media_screen);
        acquiringMediaScreen = (upload_acqusation) getSupportFragmentManager().findFragmentById(R.id.upload_acqusation);

       if (requestCode == VIDEO_CODE && resultCode == RESULT_OK) {

            Log.d("videoUrihere2", requestCode + " " + resultCode);
            Uri videoUri = intent.getData();

            try {

                //Intent to handle for accquireing screen
                if (acquiringMediaScreen != null) {
                    String path = acquiringMediaScreen.getPath(this, videoUri);
                    Log.d("videoUri-Path", path);
                    if (path == null) {
                        return;
                    }

                    acquiringMediaScreen.retrieveVideoFramesFromVideo(this,videoUri);

                } else {
                    acquiringMediaScreen = upload_acqusation.newInstance("frame", "no");

                    String path = acquiringMediaScreen.getPath(this, videoUri);
                    Log.d("videoUri-Path", path);
                    if (path == null) {
                        return;
                    }
                   // acquiringMediaScreen.retrieveVideoFramesFromVideo(path);
                    acquiringMediaScreen.retrieveVideoFramesFromVideo(this,videoUri);

                }
            } catch (Exception e) {

            }
        }


           if (requestCode == Image_Request_Code && intent != null && intent.getData() != null) {

            //Image from the gallery
            FilePathUri = intent.getData();


            //data.getData return the content URI for the selected Image
            Uri selectedImage = intent.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            // Get the cursor
            Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();
            //Get the column index of MediaStore.Images.Media.DATA
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //Gets the String value in the column
            String imgDecodableString = cursor.getString(columnIndex);
            cursor.close();
            LocationListener locationListener = new MyLocationListener();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    Activity#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for Activity#requestPermissions for more details.
                return;
            }
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                locationListener.onLocationChanged(location);
                acquiringMediaScreen.saveImage(BitmapFactory.decodeFile(imgDecodableString),currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
                acquiringMediaScreen.uploadImage(BitmapFactory.decodeFile(imgDecodableString),currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
                // After selecting image change choose button above text.
                //ChooseButton.setText("Image Selected");



        }

        if (requestCode == GALLERY_REQUEST_CODE &&  intent != null && intent.getData() != null) {

            //Image from the gallery
            FilePathUri = intent.getData();


                // Getting selected image into Bitmap.
               // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                //data.getData return the content URI for the selected Image
                Uri selectedImage = intent.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                cursor.moveToFirst();
                //Get the column index of MediaStore.Images.Media.DATA
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                //Gets the String value in the column
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();
                LocationListener locationListener = new MyLocationListener();
                lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
                locationListener.onLocationChanged(location);
                uploadMediaScreen.saveImage(BitmapFactory.decodeFile(imgDecodableString),currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
                uploadMediaScreen.uploadImage(BitmapFactory.decodeFile(imgDecodableString),currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
                // After selecting image change choose button above text.
                //ChooseButton.setText("Image Selected");


        }

        if (requestCode == CAMERA_REQUEST_CODE && intent != null && intent.getData() != null) {

            // Getting selected image into Bitmap.
            // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
            Bundle extras = intent.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            //data.getData return the content URI for the selected Image

            LocationListener locationListener = new MyLocationListener();
            lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
            locationListener.onLocationChanged(location);
            uploadMediaScreen.saveImage(imageBitmap,currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
            uploadMediaScreen.uploadImage(imageBitmap,currentDateTimeString+"_"+Double.toString(location.getLongitude())+"_"+Double.toString(location.getLatitude()));
            // After selecting image change choose button above text.
            //ChooseButton.setText("Image Selected");
        }



    }





    public void writeNewPerson (DatabaseReference mDatabase, Person newPerson)
    {
        mDatabase.child("Suspects").child(newPerson.getID()).setValue(newPerson);
    }

}


class MyLocationListener implements LocationListener {

    @Override
    public void onLocationChanged(Location loc) {
        Double longitude = loc.getLongitude();
        Double latitude =loc.getLatitude();

    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
}


class DirectoryAdapter extends RecyclerView.Adapter<com.example.eaglevision.DirectoryAdapter.PersonViewHolder>
{
    public List<Person> directory;
    public Context context;

    public static class PersonViewHolder extends RecyclerView.ViewHolder
    {
        TextView targetPersonName;
        TextView personSuspectedCrime;
        ImageView IDImage;
        Button indicator;

        public PersonViewHolder(View ItemView)
        {
            super(ItemView);
            targetPersonName= (TextView) itemView.findViewById(R.id.targetPersonName);
            personSuspectedCrime=(TextView) itemView.findViewById(R.id.SpersonSuspectedCrime);
            IDImage= (ImageView) itemView.findViewById(R.id.IDImage);;
            indicator= (Button) itemView.findViewById(R.id.indicator);;


        }


        public void setValue(TextView name , TextView crime)
        {
            targetPersonName=name;
            personSuspectedCrime=crime;
        }
    }


    public DirectoryAdapter(Context context, List<Person> TempList)
    {
        this.directory = TempList;
        this.context = context;

    }


    @Override
    public com.example.eaglevision.DirectoryAdapter.PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.directoryview_items, parent, false);
        com.example.eaglevision.DirectoryAdapter.PersonViewHolder viewHolder = new com.example.eaglevision.DirectoryAdapter.PersonViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(com.example.eaglevision.DirectoryAdapter.PersonViewHolder holder, int position)
    {
        Person p= directory.get(position);
        holder.targetPersonName.setText(p.getName());
        holder.personSuspectedCrime.setText(p.getSuspectedCrime());
   //     holder.Image.setImageURI(Uri.parse("file://" + ((MainActivity).getActivity()).getStorageReference().getPath() + "/" + directory[position].ID));
        Glide.with(context).load(p.getIdimageref()).into(holder.IDImage);
        holder.indicator.setBackgroundColor(p.getViewholderColor());
    }


    @Override
    public int getItemCount()
    {
        return directory.size();
    }



}





