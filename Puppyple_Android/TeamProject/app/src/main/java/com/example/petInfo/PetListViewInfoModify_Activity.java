package com.example.petInfo;

import static com.example.Common.CommonMethod.dtos;
import static com.example.Common.CommonMethod.loginDTO;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.example.ATask.PetInfoModify;
import com.example.ATask.PetInfoModify_nofile;
import com.example.ATask.PetListSelect;
import com.example.DTO.PetDTO;
import com.example.PUPPYPLE.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PetListViewInfoModify_Activity extends AppCompatActivity {

    //Context context;
    //PetDTO dto;

    PetListViewAdapter adapter;

    ImageView ivPetInfoModify_back, ivPetInfoModify;

    TextView tvPetInfoModify, tvPetInfoModifyName, tvPetInfoModifyAge,tvPetInfoModifyBreed
            , tvPetInfoModifyWeight, tvPetInfoModifyGender, tvPetInfoModifyNeutering;

    EditText etPetInfoModifyName, etPetInfoModifyAge, etPetInfoModifyBreed, etPetInfoModifyWeight;

    RadioGroup raPetInfoModifyGender;
    RadioButton raPetInfoModifyFemale, raPetInfoModifyMale;

    CheckBox chBoxPetInfoModifyNuetering;

    Button btnPetInfoModifySave, btnPetInfoModifyCancel;

    //20211125 PetInfoAdd_Activity?????? ?????? ????????? ?????? ??????///////////////////////
    File imgFile = null;
    String imgFilePath = "", state = "", old_filename;
    String pet_gender = "";
    String pet_nuetering = "";

    // ??????????????? ?????? ?????? ??? ??? ????????? ?????? ???????????? ????????? ??????????????? ??????
    // ??????????????? ?????? ?????? ??? ?????? == ????????? ?????????
    static final int CAPTURE_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    Button reg_btn;
    Button btn_back;
    Spinner board_spinner;
    String[] galleryOrCamera = {"?????????","?????????"};
    //////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pet_listview_info_modify);

        //20211125 getextra??? ???????????? dto ????????? ????????????, ?????? ?????????
        //?????? ????????? ?????? ????????? ????????? ?????????.
        PetDTO dto = (PetDTO) getIntent().getSerializableExtra("dto");
        Intent intent = getIntent();
        old_filename = dto.getPet_filename();
        checkDangerousPermissions();

        /*ImageView*/
        ivPetInfoModify = findViewById(R.id.ivPetInfoModify);
        ivPetInfoModify_back = findViewById(R.id.ivPetInfoModify_back);

        //?????? ????????? ???????????? ???????????? Glide??? ??????????????????
        //xml??? ????????? ????????????
        Glide.with(PetListViewInfoModify_Activity.this)
                .load(dto.getPet_filename())
                .circleCrop()
                .placeholder(R.drawable.user_basic)
                .into(ivPetInfoModify);

        /*EditText*/
        etPetInfoModifyName = findViewById(R.id.etPetInfoModifyName);
        etPetInfoModifyAge = findViewById(R.id.etPetInfoModifyAge);
        etPetInfoModifyBreed = findViewById(R.id.etPetInfoModifyBreed);
        etPetInfoModifyWeight = findViewById(R.id.etPetInfoModifyWeight);

        /*CheckBox & RadioButton & Button*/
        raPetInfoModifyGender = findViewById(R.id.raPetInfoModifyGender);
        raPetInfoModifyFemale = findViewById(R.id.raPetInfoModifyFemale);
        raPetInfoModifyMale = findViewById(R.id.raPetInfoModifyMale);
        chBoxPetInfoModifyNuetering = findViewById(R.id.chBoxPetInfoModifyNuetering);
        btnPetInfoModifySave = findViewById(R.id.btnPetInfoModifySave);
        btnPetInfoModifyCancel = findViewById(R.id.btnPetInfoModifyCancel);

        etPetInfoModifyName.setText(dto.getPet_name());
        etPetInfoModifyAge.setText(dto.getPet_age());
        etPetInfoModifyBreed.setText(dto.getPet_breed());
        etPetInfoModifyWeight.setText(dto.getPet_weight());

        if (dto.getPet_gender().equals("F")) {
            raPetInfoModifyGender.check(R.id.raPetInfoModifyFemale);
        } else if (dto.getPet_gender().equals("M")) {
            raPetInfoModifyGender.check(R.id.raPetInfoModifyMale);
        }

        if (dto.getPet_nuetering().equals("Y")) {
            chBoxPetInfoModifyNuetering.setChecked(true);
        } else if (dto.getPet_nuetering().equals("N")) {
            chBoxPetInfoModifyNuetering.setChecked(false);
        }


        //????????????

        ivPetInfoModify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //??????????????? ?????????
            }
        });//inPetInfoModify_back

        //???????????? ??????
        btnPetInfoModifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//btnPetInfoModifyCancel

        //?????? ??? ?????? ?????? ?????????
        btnPetInfoModifySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //20211125
                //0.pet???????????? ?????? ????????????. ????????? id??? ?????? ????????????.
                String pet_id = String.valueOf(dto.getPet_id());
                String member_id = loginDTO.getMember_id();

                //1.???????????? ????????? ????????? ????????????.
                String pet_name = etPetInfoModifyName.getText().toString();
                String pet_age = etPetInfoModifyAge.getText().toString();
                String pet_breed = etPetInfoModifyBreed.getText().toString();
                String pet_weight = etPetInfoModifyWeight.getText().toString();
                String pet_filepath = imgFilePath;
                if(pet_filepath.equals(old_filename)||pet_filepath.equals("")){
                    pet_filepath = null;
                }
                //1-1????????????????????? ?????? ?????? ??????
                String pet_gender = "";
                int radioButtonId = raPetInfoModifyGender.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                if (radioButton.getText().toString().equals("??????")) {
                    pet_gender = "F";
                }else if (radioButton.getText().toString().equals("??????")) {
                    pet_gender = "M";
                }

                //1-2??????????????? ???????????? ?????? ?????? ??????
                if(chBoxPetInfoModifyNuetering.isChecked()) {
                    pet_nuetering = "Y";
                }else {
                    pet_nuetering = "N";
                }

                //2. ???????????? ????????? Task??? ?????? ???????????? ????????????.
                if(pet_filepath != null){
                    PetInfoModify petInfoModify = new
                            PetInfoModify(pet_id, member_id, pet_name, pet_age, pet_breed, pet_weight, pet_gender, pet_nuetering, pet_filepath);

                    try {
                        state = petInfoModify.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")) {
                        Toast.makeText(PetListViewInfoModify_Activity.this,
                                "????????? ????????? ??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();

                        ArrayList<PetDTO> dtos1 = new ArrayList<>();
                        PetListSelect petListSelect = new PetListSelect(member_id,dtos1);
                        try {
                            petListSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        dtos.get(0).setPet_id(Integer.parseInt(pet_id));
                        dtos.get(0).setMember_id(member_id);
                        dtos.get(0).setPet_name(pet_name);
                        dtos.get(0).setPet_age(pet_age);
                        dtos.get(0).setPet_breed(pet_breed);
                        dtos.get(0).setPet_weight(pet_weight);
                        dtos.get(0).setPet_gender(pet_gender);
                        dtos.get(0).setPet_nuetering(pet_nuetering);
                        dtos.get(0).setPet_filename(dtos1.get(0).getPet_filename());

                        finish();

                        //20211125 ???????????? activity??? ????????? ????????? ?????? context???, flag??? ?????????,
                        //??? Listview??? ????????? ????????? ?????????.
                        Intent intent = new Intent(PetListViewInfoModify_Activity.this, PetListView_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);


                    }else {
                        Toast.makeText(PetListViewInfoModify_Activity.this,
                                "????????? ?????? ????????? ??????????????????. ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    PetInfoModify_nofile petInfoModify = new
                            PetInfoModify_nofile(pet_id, member_id, pet_name, pet_age, pet_breed, pet_weight, pet_gender, pet_nuetering);

                    try {
                        state = petInfoModify.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if(state.equals("1")) {
                        Toast.makeText(PetListViewInfoModify_Activity.this,
                                "????????? ????????? ??????????????? ?????????????????????.", Toast.LENGTH_SHORT).show();
                        ArrayList<PetDTO> dtos1 = new ArrayList<>();
                        PetListSelect petListSelect = new PetListSelect(member_id,dtos1);
                        try {
                            petListSelect.execute().get();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        dtos.get(0).setPet_id(Integer.parseInt(pet_id));
                        dtos.get(0).setMember_id(member_id);
                        dtos.get(0).setPet_name(pet_name);
                        dtos.get(0).setPet_age(pet_age);
                        dtos.get(0).setPet_breed(pet_breed);
                        dtos.get(0).setPet_weight(pet_weight);
                        dtos.get(0).setPet_gender(pet_gender);
                        dtos.get(0).setPet_nuetering(pet_nuetering);
                        dtos.get(0).setPet_filename(dtos1.get(0).getPet_filename());


                        finish();

                        //20211125 ???????????? activity??? ????????? ????????? ?????? context???, flag??? ?????????,
                        //??? Listview??? ????????? ????????? ?????????.
                        Intent intent = new Intent(PetListViewInfoModify_Activity.this, PetListView_Activity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                                Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);


                    }else {
                        Toast.makeText(PetListViewInfoModify_Activity.this,
                                "????????? ?????? ????????? ??????????????????. ?????? ??????????????????.", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });

        ivPetInfoModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("?????? ??????");
                builder.setItems(galleryOrCamera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){ // ?????????
                            Intent gallIntent = new Intent();
                            gallIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            gallIntent.setType("image/*");
                            gallIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(gallIntent,GALLERY_REQUEST_CODE);

                        }else if(which == 1){ // ?????????
                            // ?????????????????? : ????????????(???????????? ?????????)
                            Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // ?????? ??? ???????????? ?????????????????? ??????
                            if(picIntent.resolveActivity(getPackageManager()) != null){
                                imgFile = null;
                                // createFile ???????????? ????????????
                                imgFile = createFile();

                                if(imgFile != null){
                                    // API24 ??????????????? FileProvider ??? ???????????????
                                    Uri imgUri = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".fileprovider",imgFile);
                                    // ????????? API24 ????????????
                                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT,imgUri);
                                    }else{
                                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT,Uri.fromFile(imgFile));
                                    }

                                    startActivityForResult(picIntent,CAPTURE_REQUEST_CODE);
                                }
                            }
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });//ivMyPagePet ????????? ??????????????? ????????? ??????




    }//onCreate()

    ////////////////////////////////////////////////////////////////////////////

    // ????????????
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_MEDIA_LOCATION,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "?????? ??????", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "?????? ?????? ?????????.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " ????????? ?????????.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " ????????? ???????????? ??????.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    //????????????
    private File createFile() {
        // ?????? ????????? ????????? ?????? ???????????? ?????????
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My"+timestamp;
        // ??????????????? ???????????? ?????? ??????
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            // ??????????????? ?????????, 2?????? suffix ????????? : ??????????????? , 3?????? ??????
            curFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // String ???????????? ?????? ????????? ?????? ?????? ??????????????? ?????????
        imgFilePath = curFile.getAbsolutePath();

        return curFile;
    }

    // ?????? ?????? ??? ???????????? ?????? ???
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_REQUEST_CODE && resultCode == RESULT_OK){
            // ?????? ????????? ???
            //Toast.makeText(this, "????????? ??? ??????", Toast.LENGTH_SHORT).show();

            setPic();
        }
        if(requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK){

            if(data != null && resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                //Uri mPhotoUri = Uri.parse(getRealPathFromUri(selectedImage));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage,filePathColumn,null,null,null);

                if(cursor == null || cursor.getCount() < 1){
                    return;
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                if(columnIndex < 0){
                    return;
                }

                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                ExifInterface exif = null;
                try {
                    exif = new ExifInterface(picturePath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

                Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
                Bitmap bmRotated = rotateBitmap(bitmap, orientation);


                imgFilePath = picturePath;
                ivPetInfoModify.setImageBitmap(bmRotated);
            }

        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation){
            case ExifInterface.ORIENTATION_NORMAL:
                return bitmap;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(-90);
                break;
            default:
                return bitmap;
        }
        try {
            Bitmap bmRotated = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.recycle();
            return bmRotated;
        }
        catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }



    // ????????? ???????????? ?????????
    private void setPic() {
        // ??????????????? ?????? ????????????
        int targetW = ivPetInfoModify.getWidth();
        int targetH = ivPetInfoModify.getHeight();

        // ????????? ?????? ????????????
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // ????????? ????????? ?????? ????????? ??????

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // ??????????????? ????????? ?????? ?????????????????? ??????
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // ????????? ????????? ??????
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);

        //90??? ??????
        Matrix matrix = new Matrix();
        matrix.setRotate(90); //90??? ??????
        Bitmap bitmap90 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle(); //bitmap??? ????????? ?????? ???????????? ??????????????? free?????????.

        // ???????????? ???????????? ????????????
        galleryAddPic(bitmap90);
        ivPetInfoModify.setImageBitmap(bitmap90);

    }

    private void galleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ // API29
            ContentResolver resolver = getContentResolver();
            // ??? ????????? ?????? ContentValues : ??????????????? ?????????
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_"+"jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator+"TestFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues
            );
            try {
                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));
                //Toast.makeText(this, "fos ?????????", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                Objects.requireNonNull(fos);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{
            // ?????????????????? ???????????? ???????????? ??????
            Intent msIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // ????????? CreateFile ?????? ???????????? ??????????????? (imgFilePath)
            File f = new File(imgFilePath);
            Uri contentUri = Uri.fromFile(f);
            msIntent.setData(contentUri);
            // ??????
            this.sendBroadcast(msIntent);
        }
    }




    //20211125 PetInfoAdd_Activity????????? ?????? ????????? override??????
    //???????????????, ????????? ??????????????? ????????? ????????? ?????? ??????????????? 10:52 am
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }

}//class PetListViewInfoModify_Activity