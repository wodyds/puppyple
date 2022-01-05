package com.example.community;

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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.example.ATask.BoardAdd;
import com.example.ATask.BoardAdd_Nofile;
import com.example.PUPPYPLE.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class Community_register extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private static final String TAG = "main:Community_register";
    // 갤러리에서 파일 선택 시 첫 화면이 아닌 갤러리탭 안에서 파일선택만 가능
    // 첫화면에서 파일 선택 시 충돌 == 나중에 디버깅
    static final int CAPTURE_REQUEST_CODE = 1;
    static final int GALLERY_REQUEST_CODE = 2;
    Button reg_btn;
    //Button btn_back;
    Spinner board_spinner;
    String[] galleryOrCamera = {"갤러리", "카메라"};
    ImageView image_file, toolbar_title, post_back;
    File imgFile = null;
    String imgFilePath = null, state = "", selected = "";

    // xml에서 사용하는 것
    EditText et_title, et_content;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_register);
        checkDangerousPermissions();
        // 인텐트
/*

        toolbar_title = findViewById(R.id.toolbar_title);
        // 로고 클릭 시 메인화면
        toolbar_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Community_register.this, MainActivity.class);
                startActivityForResult(intent,10);
            }
        });
*/

        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);
        // 이미지 등록 부분

        image_file = findViewById(R.id.image_file);
        image_file.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle("사진 선택");
                builder.setItems(galleryOrCamera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) { // 갤러리
                            Intent gallIntent = new Intent();
                            gallIntent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            gallIntent.setType("image/*");
                            gallIntent.setAction(Intent.ACTION_GET_CONTENT);

                            startActivityForResult(gallIntent, GALLERY_REQUEST_CODE);

                        } else if (which == 1) { // 카메라
                            // 암묵적인텐트 : 사진찍기(카메라를 불러옴)
                            Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // 일단 이 인텐트가 사용가능한지 체크
                            if (picIntent.resolveActivity(getPackageManager()) != null) {
                                imgFile = null;
                                // createFile 메소드를 이용하여
                                imgFile = createFile();

                                if (imgFile != null) {
                                    // API24 이상부터는 FileProvider 를 제공해야함
                                    Uri imgUri = FileProvider.getUriForFile(getApplicationContext(), getApplicationContext().getPackageName() + ".fileprovider", imgFile);
                                    // 만약에 API24 이상이면
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                                    } else {
                                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                                    }

                                    startActivityForResult(picIntent, CAPTURE_REQUEST_CODE);
                                }
                            }
                        }
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });


        // 뒤로가기 버튼 클릭 이벤트
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });


        // 카테고리 설정
        board_spinner = findViewById(R.id.board_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.community_array, R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        board_spinner.setAdapter(adapter);
        board_spinner.setOnItemSelectedListener(this);
        
        //20211221 글작성시 해당 커뮤니티 프레그먼트의 게시판 Flag에맞게 자동선택하도록함 by JP
        if (getIntent().getStringExtra("flag").equals("B")) {
            board_spinner.setSelection(0);
        }else if (getIntent().getStringExtra("flag").equals("I")) {
            board_spinner.setSelection(1);
        }else if (getIntent().getStringExtra("flag").equals("G")) {
            board_spinner.setSelection(2);
        }else if (getIntent().getStringExtra("flag").equals("T")) {
            board_spinner.setSelection(3);
        }else if (getIntent().getStringExtra("flag").equals("A")) {
            board_spinner.setSelection(0);
        }


        // 등록하기 버튼 클릭 이벤트
        reg_btn = findViewById(R.id.btn_regist);
        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //20211119
                //0.회원의 id도 같이 물려주려는것
                String member_id = loginDTO.getMember_id();


                //1. 게시글 등록에 필요한 정보를 가져온다.
                String board_title = String.valueOf(et_title.getText());
                String board_content = et_content.getText().toString();
                String board_filepath = imgFilePath;
                Log.d(TAG, "imgfilepath: " + board_filepath);

                String flag = board_spinner.getSelectedItem().toString();
                if (flag.equals("자유게시판")) {
                    flag = "B";
                } else if (flag.equals("정보공유")) {
                    flag = "I";
                } else if (flag.equals("갤러리")) {
                    flag = "G";
                } else if (flag.equals("중고거래")) {
                    flag = "T";
                }


                //2. 이정보를 비동기 Task로 넘겨 서버에게 전달한다.
                if (board_filepath != null) {
                    BoardAdd boardAdd = new
                            BoardAdd(flag, member_id, board_title, board_content, board_filepath);

                    try {
                        state = boardAdd.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("1")) {
                        Toast.makeText(Community_register.this,
                                "게시글 등록 완료", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(Community_register.this,
                                "게시글 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    BoardAdd_Nofile boardAdd = new
                            BoardAdd_Nofile(flag, member_id, board_title, board_content);

                    try {
                        state = boardAdd.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("1")) {
                        Toast.makeText(Community_register.this,
                                "게시글 등록 완료", Toast.LENGTH_SHORT).show();

                        finish();
                    } else {
                        Toast.makeText(Community_register.this,
                                "게시글 등록에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        // 취소하기 버튼 클릭 이벤트 (뒤로가기)
        post_back = findViewById(R.id.post_back);
        post_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

    }


    // 카테고리 선택 시 기능구현
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }


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
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
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

    private File createFile() {
        // 파일 이름을 만들기 위해 시간값을 생성함
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "My" + timestamp;
        // 사진파일을 저장하기 위한 경로
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            // 임시파일을 생성함, 2번째 suffix 확장자 : 파일확장자 , 3번째 경로
            curFile = File.createTempFile(imageFileName, ".jpg", storageDir);
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

        if (requestCode == CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            // 저장 처리를 함
            //Toast.makeText(this, "사진이 잘 찍힘", Toast.LENGTH_SHORT).show();

            setPic();
        }
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {

            if (data != null && resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                //Uri mPhotoUri = Uri.parse(getRealPathFromUri(selectedImage));
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);

                if (cursor == null || cursor.getCount() < 1) {
                    return;
                }
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);

                if (columnIndex < 0) {
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
                Log.d(TAG, "gallpath: " + picturePath);


                imgFilePath = picturePath;
                image_file.setImageBitmap(bmRotated);
            }

        }
    }

    public static Bitmap rotateBitmap(Bitmap bitmap, int orientation) {
        Matrix matrix = new Matrix();
        switch (orientation) {
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
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }


    // 사진을 저장처리 하는곳
    private void setPic() {
        // 이미지뷰의 크기 알아오기
        int targetW = image_file.getWidth();
        int targetH = image_file.getHeight();

        // 사진의 크기 가져오기
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // 이미지 크기를 맞출 비율을 결정

        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

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
        image_file.setImageBitmap(bitmap90);

    }

    private void galleryAddPic(Bitmap bitmap) {
        FileOutputStream fos;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) { // API29
            ContentResolver resolver = getContentResolver();
            // 맵 구조를 가진 ContentValues : 파일정보를 저장함
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "Image_" + "jpg");
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "TestFolder");

            Uri imageUri = resolver.insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues
            );
            try {
                fos = (FileOutputStream) resolver.openOutputStream(Objects.requireNonNull(imageUri));
                //Toast.makeText(this, "fos 작업됨", Toast.LENGTH_SHORT).show();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                Objects.requireNonNull(fos);
            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
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
}

