package com.example.eaglevision;

//Firebase

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.eaglevision.MainActivity.CAMERA_REQUEST_CODE;
import static com.example.eaglevision.MainActivity.ip_;
import static com.example.eaglevision.MainActivity.port_;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link upload_media_screen.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link upload_media_screen#newInstance} factory method to
 * create an instance of this fragment.
 */
public class upload_media_screen extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private StorageReference tempStorageRef;
    File currentimage;
    //Authentication
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    private OnFragmentInteractionListener mListener;

    private ImageButton upload;
    private ImageButton uploadByGallery;

    int REQUEST_CODE=2345;
    int GALLERY_REQUEST_CODE=1;
    protected int timeInMicroSeconds=10000000;


    public upload_media_screen() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment upload_media_screen.
     */
    // TODO: Rename and change types and number of parameters
    public static upload_media_screen newInstance(String param1, String param2) {
        upload_media_screen fragment = new upload_media_screen();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_upload_media_screen, container, false);

        upload= (ImageButton) view.findViewById(R.id.upload_image);
       // vv=  view.findViewById(R.id.vv);
        uploadByGallery=(ImageButton)view.findViewById(R.id.libupload1);

        uploadByGallery.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view)
                                               {
                                                   Intent intent=new Intent(Intent.ACTION_PICK);
                                                   // Sets the type as image/*. This ensures only components of type image are selected
                                                   intent.setType("image/*");
                                                   //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
                                                   String[] mimeTypes = {"image/jpeg", "image/png"};
                                                   intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
                                                   // Launching the Intent
                                                   getActivity().startActivityForResult(intent,GALLERY_REQUEST_CODE);
                                                   Navigation.findNavController(view).navigate(R.id.action_upload_media_screen_to_detailsForm);
                                               }

                                           }

        );
        upload.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view)
                                               {

                                                   Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                   if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                                                       getActivity().startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
                                                   }
                                                   Navigation.findNavController(view).navigate(R.id.action_upload_media_screen_to_detailsForm);
                                               }

                                           }

        );


        return view;
    }



    // TODO: Rename method, update argument and hook method into UI event
    /*public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }*/

    private static File getOutputMediaFile(){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "CameraCommand");

        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        return new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    //    if (context instanceof OnFragmentInteractionListener) {
     //       mListener = (OnFragmentInteractionListener) context;
     //   } else {
     //       throw new RuntimeException(context.toString()
      //              + " must implement OnFragmentInteractionListener");
      //  }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }






    public void saveImage(Bitmap finalBitmap, String image_name)
    {
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root);
        myDir.mkdirs();
        String fileName = "Image-" + image_name+ ".jpg";
        File file = new File(myDir, fileName);
        if (file.exists()) file.delete();
        Log.i("LOAD", root + fileName);
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);

            out.flush();
            out.close();
            currentimage=file;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void uploadImage(Bitmap finalBitmap, String image_name)
    {
        tempStorageRef= ((MainActivity)getActivity()).getStorageReference();
        String root = Environment.getExternalStorageDirectory().toString();
        String fileName=currentimage.getName();
        final String postBodyText=fileName;
        Log.i("LOAD", root + fileName);
        try {

            //Uploading
            Uri urifile = Uri.fromFile(currentimage);
            final StorageReference imageref = tempStorageRef.child("Orignal_Images/"+fileName);
            UploadTask uploadTask = imageref.putFile(urifile);

            // Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i("upload_up", exception.toString());
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    MediaType mediaType = MediaType.parse("text/plain; charset=utf-8");
                    RequestBody postBody = RequestBody.create(mediaType, postBodyText);
                    postRequest(ip_+port_,  postBody  );

                }
                //=============================

            });

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    void postRequest(String postUrl, RequestBody postBody) {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(postUrl)
                .post(postBody)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Cancel the post on failure.
                call.cancel();

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                Log.i("TALKING", response.body().string());
            }
        });
    }



    public  String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = { "_data" };
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        }
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }


/**
public void retrieveVideoFramesFromVideo(String p_videoPath)

    {
        Bitmap frame = null;
        MediaMetadataRetriever mediaMetadataRetriever = null;
        try
        {
            mediaMetadataRetriever = new MediaMetadataRetriever();
            mediaMetadataRetriever.setDataSource(p_videoPath);

            long durationInSeconds= Long.parseLong(mediaMetadataRetriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            Log.d("loopcount_durationInSec",durationInSeconds+"");

            int noOfFrames= (int) (durationInSeconds / 10000);

            Log.d("loopcount_noOfFrames",noOfFrames+"");
            for (int count=1; count <= noOfFrames; ++count)
            {
                Log.d("loopcount",count+"");
                frame=mediaMetadataRetriever.getFrameAtTime( count * 10000000);

                 saveImage(frame, "frame-no" + count);
                uploadImage(frame, "frame-no" + count);
            }


        }
        catch (Exception m_e)
        {
            Log.d("exception","-"+m_e);
        }
        finally
        {
            if (mediaMetadataRetriever != null)
            {
                mediaMetadataRetriever.release();
            }
        }



    }
**/
    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Person person);
    }
}


