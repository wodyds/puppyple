package com.example.petInfo;

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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.ATask.PetInfoAdd;
import com.example.PUPPYPLE.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class PetInfoAdd_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    File imgFile = null;
    String imgFilePath = "", state = "";
    String pet_gender = "";
    String pet_nuetering = "";

    //이미지 처리가 정상적으로 되었을때 onActivityResult에서 데이터를 받기위한 코드
    public int reqPicCode = 1004;

    EditText etMyPagePetName, etMyPagePetAge, etMyPagePetBreed, etMyPagePetWeight;

    RadioGroup raPetPageBoxGender;
    RadioButton raPetPageBoxFemale, raPetPageBoxMale;

    CheckBox chBoxMyPageNuetering;

    Button btnMyPagePetInfoAdd, btnMyPagePetCancel;

    ImageView ivMyPagePet, petInfo_back;

    // 갤러리에서 파일 선택 시 첫 화면이 아닌 갤러리탭 안에서 파일선택만 가능
    // 첫화면에서 파일 선택 시 충돌 == 나중에 디버깅
    static final int CAPTURE_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    Button reg_btn;
    Button btn_back;
    Spinner board_spinner;
    String[] galleryOrCamera = {"갤러리","카메라"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_pet_info_add);

        checkDangerousPermissions();

        /*ImageView*/
        ivMyPagePet = findViewById(R.id.ivMyPagePet);
        petInfo_back = findViewById(R.id.petInfo_back);

        /*사진넣기전에 기본으로 사진이 넣게한다.*/
        ivMyPagePet.setImageResource(R.drawable.user_basic);

        /*EditText*/
        etMyPagePetName = findViewById(R.id.etMyPagePetName);
        etMyPagePetAge = findViewById(R.id.etMyPagePetAge);
        etMyPagePetBreed = findViewById(R.id.etMyPagePetBreed);
        etMyPagePetWeight = findViewById(R.id.etMyPagePetWeight);

        /*CheckBox & RadioButton & Button*/
        raPetPageBoxGender = findViewById(R.id.raPetPageBoxGender);
        //raPetPageBoxFemale = findViewById(R.id.raPetPageBoxFemale);
        //raPetPageBoxMale = findViewById(R.id.raPetPageBoxMale);
        chBoxMyPageNuetering = findViewById(R.id.chBoxMyPageNuetering);
        btnMyPagePetInfoAdd =  findViewById(R.id.btnMyPagePetInfoAdd);
        btnMyPagePetCancel = findViewById(R.id.btnMyPagePetCancel);

        //뒤로가기
        petInfo_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                onBackPressed();
                finish();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        //저장 및 추가 버튼을 클릭시
        btnMyPagePetInfoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //20211119
                //0.회원의 id도 같이 물려주려는것
                String member_id = loginDTO.getMember_id().toString();


                //1. 펫추가에 필요한 정보를 가져온다.
                String pet_name = etMyPagePetName.getText().toString();
                String pet_age = etMyPagePetAge.getText().toString();
                String pet_breed = etMyPagePetBreed.getText().toString();
                String pet_weight = etMyPagePetWeight.getText().toString();
                String pet_filepath = imgFilePath;


                //1-1성별라디오버튼 처리 기능 구현
                String pet_gender = "";
                int radioButtonId = raPetPageBoxGender.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(radioButtonId);
                if (radioButton.getText().toString().equals("암컷")) {
                    pet_gender = "F";
                }else if (radioButton.getText().toString().equals("수컷")) {
                    pet_gender = "M";
                }

                //1-2중성화여부 체크박스 처리 기능 구현
                if(chBoxMyPageNuetering.isChecked()) {
                    pet_nuetering = "Y";
                }else {
                    pet_nuetering = "N";
                }

                //2. 이정보를 비동기 Task로 넘겨 서버에게 전달한다.
                PetInfoAdd petInfoAdd = new
                        PetInfoAdd(member_id, pet_name, pet_age, pet_breed, pet_weight, pet_gender, pet_nuetering, pet_filepath);

                try {
                    state = petInfoAdd.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")) {
                    Toast.makeText(PetInfoAdd_Activity.this,
                            "정상적으로 반려견 정보가 추가되었습니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(PetInfoAdd_Activity.this,
                            "반려견 정보 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }


            }
        });// btnMyPagePetInfoAdd()


        //이미지뷰를 클릭하면 사진을 찍어서 데이터를 저장한다.
        ivMyPagePet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("사진 선택");
                builder.setItems(galleryOrCamera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which == 0){ // 갤러리
                            Intent gallIntent = new Intent();
                            gallIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            gallIntent.setType("image/*");
                            gallIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(gallIntent,GALLERY_REQUEST_CODE);

                        }else if(which == 1){ // 카메라
                            // 암묵적인텐트 : 사진찍기(카메라를 불러옴)
                            Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 일단 이 인텐트가 사용가능한지 체크
                            if(picIntent.resolveActivity(getPackageManager()) != null){
                                imgFile = null;
                                // createFile 메소드를 이용하여
                                imgFile = createFile();

                                if(imgFile != null){
                                    // API24 이상부터는 FileProvider 를 제공해야함
                                    Uri imgUri = FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".fileprovider",imgFile);
                                    // 만약에 API24 이상이면
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
        });//ivMyPagePet 클릭시 사진찍어서 데이터 저장

        //취소버튼 클릭
        btnMyPagePetCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });//btnMyPagePetCancel



    }//onCreate()


    ////////////////////////////////////////////////////////////////////////////

    // 위험권한
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
            /*Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();*/
        } else {
            /*Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();*/

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                /*Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();*/
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
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    //파일생성
    private File createFile() {
        // 파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My"+timestamp;
        // 사진파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            // 임시파일을 생성함, 2번째 suffix 확장자 : 파일확장자 , 3번째 경로
            curFile = File.createTempFile(imageFileName,".jpg",storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // String 타입으로 임시 파일이 있는 곳의 절대경로를 저장함
        imgFilePath = curFile.getAbsolutePath();

        return curFile;
    }

    // 사진 찍은 후 데이터를 받는 곳
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAPTURE_REQUEST_CODE && resultCode == RESULT_OK){
            // 저장 처리를 함
            //Toast.makeText(this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();

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
                ivMyPagePet.setImageBitmap(bmRotated);
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



    // 사진을 저장처리 하는곳
    private void setPic() {
        // 이미지뷰의 크기 알아오기
        int targetW = ivMyPagePet.getWidth();
        int targetH = ivMyPagePet.getHeight();

        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // 이미지 크기를 맞출 비율을 결정

        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // 이미지뷰의 크기에 맞게 이미지크기를 조절
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        // 비트맵 이미지 생성
        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath);

        //90도 회전
        Matrix matrix = new Matrix();
        matrix.setRotate(90); //90도 회전
        Bitmap bitmap90 = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        bitmap.recycle(); //bitmap은 더이상 필요 없음으로 메모리에서 free시킨다.

        // 이미지를 갤러리에 저장하기
        galleryAddPic(bitmap90);
        ivMyPagePet.setImageBitmap(bitmap90);

    }

    private void galleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ // API29
            ContentResolver resolver = getContentResolver();
            // 맵 구조를 가진 ContentValues : 파일정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME,"Image_"+"jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE,"image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH,Environment.DIRECTORY_PICTURES+File.separator+"TestFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues
            );
            try {
                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));
                //Toast.makeText(this, "fos 작업됨", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,fos);
                Objects.requireNonNull(fos);
            } catch (Exception e) {
                e.printStackTrace();
            }


        }else{
            // 이미지파일을 스캔해서 갤러리에 저장
            Intent msIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            // 처음에 CreateFile 에서 생성해둔 이미지경로 (imgFilePath)
            File f = new File(imgFilePath);
            Uri contentUri = Uri.fromFile(f);
            msIntent.setData(contentUri);
            // 저장
            this.sendBroadcast(msIntent);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}//class PetInfo_Activity
