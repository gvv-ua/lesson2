package ua.gvv.studentlist;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by gvv on 12.11.16.
 */

public class FragmentImageSelector extends Fragment {
    static final int REQUEST_IMAGE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image_selector, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView image = (ImageView) view.findViewById(R.id.image_selector);
        image.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         getImage();
                                     }
                                 }
        );
    }

    private void getImage() {
        List<Intent> imageIntentsList = new ArrayList<Intent>();

        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        List<ResolveInfo> listGall = getActivity().getPackageManager().queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGall) {
            Intent targetIntent = new Intent(galleryIntent);
            targetIntent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            imageIntentsList.add(targetIntent);
        }

        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        Intent chooserIntent = Intent.createChooser(cameraIntent, getString(R.string.title_request_image));
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, imageIntentsList.toArray(new Parcelable[imageIntentsList.size()]));
        startActivityForResult(chooserIntent, REQUEST_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            Uri selectedImage = data.getData();
            if (selectedImage == null) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                ImageView image = (ImageView)getActivity().findViewById(R.id.image_selector);
                image.setImageBitmap(imageBitmap);
            } else {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                ImageView image = (ImageView)getActivity().findViewById(R.id.image_selector);
                image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
    }

}
