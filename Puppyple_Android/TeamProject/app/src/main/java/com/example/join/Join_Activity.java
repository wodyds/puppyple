package com.example.join;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.IdCheck;
import com.example.ATask.JoinInsert;
import com.example.ATask.NicknameCheck;
import com.example.PUPPYPLE.R;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

public class Join_Activity extends AppCompatActivity {

    //전체동의
    CheckBox checkBox1;
    //필수 서비스이용약관
    CheckBox checkBox2;
    //필수 개인정보
    CheckBox checkBox3;
    //선택 위치정보
    CheckBox checkBox4;

    //이용약관 버튼1 - 서비스
    Button btnAgr1;
    //이용약관 버튼2 - 개인정보
    Button btnAgr2;
    //이용약관 버튼3 - 위치정보
    Button btnAgr3;

    //가입 취소 버튼
    Button btnMemberJoin, btnMemberCancel;
    //EditText
    EditText etMemberId, etMemberPw, etMemberPhoneNumber, etMemberEmail, etMemberNickname;
    EditText etMemberPwChk;

    // id, nickname 중복체크
    Button btnIdChk, btnMemberNicknameChk;

    ImageView join_back;
    int idChk;
    String state;
    int nickChk;
    public int reqPicCode = 1004;

    //20211112 유효성 검사 연결

    //아이디 정규식
    private static final Pattern MEMBER_ID_PATTERN = Pattern.compile("^[a-zA-Z0-9]*$");
    //비밀번호 정규식
    private static final Pattern MEMBER_PW_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    //전화번호 정규식
    private static final Pattern MEMBER_PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", Pattern.CASE_INSENSITIVE);
    //이메일 정규식
    private static final Pattern MEMBER_EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    //닉네임 정규식
    private static final Pattern MEMBER_NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$");

    // 회원정보에 저장할 값 객체 생성
    private String member_id = "";
    private String member_pw = "";
    private String member_pwchk = "";
    private String member_phone = "";
    private String member_email = "";
    private String member_nickname = "";
    private InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_member);
        //전체동의
        checkBox1 = findViewById(R.id.checkBox1);
        //필수 서비스이용약관
        checkBox2 = findViewById(R.id.checkBox2);
        //필수 개인정보
        checkBox3 = findViewById(R.id.checkBox3);
        //선택 위치정보
        checkBox4 = findViewById(R.id.checkBox4);
        //이용약관 버튼1 - 서비스
        btnAgr1 = findViewById(R.id.btnAgr1);
        //이용약관 버튼2 - 개인정보
        btnAgr2 = findViewById(R.id.btnAgr2);
        //이용약관 버튼3 - 위치정보
        btnAgr3 = findViewById(R.id.btnAgr3);

        //버튼내 전체보기 text에 밑줄긋는 기능 구현
        btnAgr1.setPaintFlags(btnAgr1.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnAgr2.setPaintFlags(btnAgr2.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        btnAgr3.setPaintFlags(btnAgr3.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        etMemberId = findViewById(R.id.etMemberId);
        etMemberPw = findViewById(R.id.etMemberPw);
        etMemberPwChk = findViewById(R.id.etMemberPwChk);
        etMemberPhoneNumber = findViewById(R.id.etMemberPhoneNumber);
        etMemberEmail = findViewById(R.id.etMemberEmail);
        etMemberNickname = findViewById(R.id.etMemberNickname);
        btnIdChk = findViewById(R.id.btnIdChk);
        btnMemberNicknameChk = findViewById(R.id.btnMemberNicknameChk);

        idChk = 0;

        //가입버튼
        btnMemberJoin = findViewById(R.id.btnMemberJoin);
        //취소버튼
        btnMemberCancel = findViewById(R.id.btnMemberCancel);

        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        join_back = findViewById(R.id.join_back);
        join_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기

            }

        });

        //20211127 회원가입 닉네임 중복확인 기능 구현_JP
        btnMemberNicknameChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(etMemberNickname.getWindowToken(), 0);
                String member_nickname = etMemberNickname.getText().toString();
                if(member_nickname.length() < 2 || member_nickname.length() > 10){
                    AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Join_Activity.this);
                    msgIdChk.setTitle("닉네임 중복검사");
                    msgIdChk.setMessage("닉네임은 한/영/숫자 2~9자 입니다.");
                    msgIdChk.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idChk = 0;
                        }
                    });
                    AlertDialog msgDlg = msgIdChk.create();
                    msgDlg.show();

                }else {
                    //이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                    NicknameCheck nicknameCheck = new
                            NicknameCheck(member_nickname);
                    try {
                        state = nicknameCheck.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("0")) {
                        AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Join_Activity.this)
                                .setTitle("닉네임 중복검사")
                                .setMessage("사용가능한 닉네임입니다.\n사용하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        nickChk = 1;
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        nickChk = 0;
                                    }
                                });
                        AlertDialog msgDlg = msgIdChk.create();
                        msgDlg.show();
                    } else {
                        Toast.makeText(Join_Activity.this,
                                "사용 중인 닉네임입니다. 다른 닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });//닉네임체크버튼클릭시 이벤트 기능 구현 _ JP



        //가입
        btnMemberJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_id = etMemberId.getText().toString();
                String member_pw = etMemberPw.getText().toString();
                String member_pwchk = etMemberPwChk.getText().toString();
                String member_phone = etMemberPhoneNumber.getText().toString();
                String member_email = etMemberEmail.getText().toString();
                String member_nickname = etMemberNickname.getText().toString();
                CheckBox checkBox1 = findViewById(R.id.checkBox1);
                CheckBox checkBox2 = findViewById(R.id.checkBox2);
                CheckBox checkBox3 = findViewById(R.id.checkBox3);
                CheckBox checkBox4 = findViewById(R.id.checkBox4);


                //유효성 검사 후 회원가입 실행
                if (isValidId(member_id) && isValidPw(member_pw) && isValidPwChk(member_pw, member_pwchk) && isValidPhone(member_phone)
                        && isValidEmail(member_email) && isValidNickname(member_nickname) && isChecked(checkBox1, checkBox2, checkBox3)
                        && idValidCheck(idChk) && nickNameBtnValidCheck(nickChk)) {


                    //이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                    JoinInsert joinInsert = new
                            JoinInsert(member_id, member_pw, member_phone, member_email, member_nickname);
                    try {
                        state = joinInsert.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (state.equals("1")) {
                        Toast.makeText(Join_Activity.this,
                                "정상적으로 회원가입이 되었습니다.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Join_Activity.this,
                                "회원가입에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Join_Activity.this, "회원가입 진행이 안됐습니다.", Toast.LENGTH_SHORT).show();
                }
            }

        });


        //취소
        btnMemberCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //전체동의 클릭시
        //전체 true / 전체 false 로 변경
        checkBox1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()) {
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                    checkBox4.setChecked(true);
                } else {
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                    checkBox4.setChecked(false);
                }
            }
        });

        //2 클릭시
        checkBox2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //만약 전체 클릭이 true 라면 false로 변경
                if (checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                    //각 체크박스 체크 여부 확인해서  전체동의 체크박스 변경
                } else if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
                    checkBox1.setChecked(true);
                }
            }
        });

        //3 클릭시
        checkBox3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                } else if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
                    checkBox1.setChecked(true);
                }
            }
        });

        //4클릭시
        checkBox4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkBox1.isChecked()) {
                    checkBox1.setChecked(false);
                } else if (checkBox2.isChecked() && checkBox3.isChecked() && checkBox4.isChecked()) {
                    checkBox1.setChecked(true);
                }
            }
        });

        //버튼누르면 전체보기로 각각 새창이 뜨는 기능 구현
        btnAgr1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Join_Activity.this, SubActivityService.class);
                startActivity(intent);

            }
        });

        btnAgr2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Join_Activity.this, SubActivityPersonal.class);
                startActivity(intent);

            }
        });

        btnAgr3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Join_Activity.this, SubActivityLocation.class);
                startActivity(intent);

            }
        });

        //아이디 중복검사
        btnIdChk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imm.hideSoftInputFromWindow(etMemberId.getWindowToken(),0);
                String member_id = etMemberId.getText().toString();
                if(member_id.length() < 4 || member_id.length() > 12){
                    AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Join_Activity.this);
                    msgIdChk.setTitle("아이디 중복검사");
                    msgIdChk.setMessage("아이디는 영문/숫자 4~11자 입니다.");
                    msgIdChk.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            idChk = 0;
                        }
                    });
                    AlertDialog msgDlg = msgIdChk.create();
                    msgDlg.show();
                }else {
                    //이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                    IdCheck idCheck = new
                            IdCheck(member_id);
                    try {
                        state = idCheck.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    if (state.equals("0")) {
                        AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Join_Activity.this)
                                .setTitle("아이디 중복검사")
                                .setMessage("사용가능한 아이디입니다.\n사용하시겠습니까?")
                                .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        idChk = 1;
                                    }
                                })
                                .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        idChk = 0;
                                    }
                                });

                        AlertDialog msgDlg = msgIdChk.create();
                        msgDlg.show();


                    } else {
                        Toast.makeText(Join_Activity.this,
                                "사용 중인 아이디입니다. 다른 아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });//아이디중복검사

    }//onCreate()

    ///////////////////////////////////////////////////////////////////

    //유효성검사


    //아이디 유효성 검사
    private boolean isValidId(String member_id) {
        if (member_id.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Join_Activity.this, "아이디를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_ID_PATTERN.matcher(member_id).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Join_Activity.this, "아이디 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidId

    //비밀번호 유효성 검사
    private boolean isValidPw(String member_pw) {
        if (member_pw.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Join_Activity.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_PW_PATTERN.matcher(member_pw).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Join_Activity.this, "비밀번호 형식이 일치하지 않습니다. \n비밀번호는 4~11자이며 영문, 숫자를 혼합해서 적어주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidPw

    // 비밀번호 체크 유효성 검사
    private boolean isValidPwChk(String member_pw, String member_pwchk) {
        if (member_pwchk.isEmpty()) {
            // 비밀번호 칸이 공백이면 false
            Toast.makeText(Join_Activity.this, "비밀번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!member_pw.equals(member_pwchk)) {
            // 비밀번호와 비밀번호 확인에 입력한 값이 불일치하면 false
            Toast.makeText(Join_Activity.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isVaildpwChk

    //전화번호 유효성 검사
    private boolean isValidPhone(String member_phone) {
        if (member_phone.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Join_Activity.this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_PHONE_PATTERN.matcher(member_phone).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Join_Activity.this, "전화번호 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidEmail

    //이메일 유효성 검사
    private boolean isValidEmail(String member_email) {
        if (member_email.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Join_Activity.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_EMAIL_PATTERN.matcher(member_email).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Join_Activity.this, "이메일 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidEmail

    //별명 닉네임 유효성 검사
    private boolean isValidNickname(String member_nickname) {
        if (member_nickname.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Join_Activity.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_NICKNAME_PATTERN.matcher(member_nickname).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Join_Activity.this, "별명 (닉네임) 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isVaildNickname

    //이용약관 20211129 회원가입관련 이용약관 체크 관련
    // java.lang.StackOverflowError: 오류 수정완료 오전 10:43분 by JP
    private boolean isChecked(CheckBox checkBox1, CheckBox checkBox2, CheckBox checkBox3) {
        if (checkBox1.isChecked() || checkBox2.isChecked() && checkBox3.isChecked()) {
            return true;
        } else if (! checkBox1.isChecked()
                || ! checkBox2.isChecked() && ! checkBox3.isChecked()
                || checkBox2.isChecked() && ! checkBox3.isChecked()
                || ! checkBox2.isChecked() && checkBox3.isChecked()) {
            Toast.makeText(Join_Activity.this, "이용약관에 동의해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            Toast.makeText(Join_Activity.this, "이용약관에 동의 및 체크해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }
    }//isChecked

    //아이디중복검사유효성검사
    private boolean idValidCheck(int idChk) {
        if (idChk == 1) {
            return true;
        } else if (idChk == 0) {
            //아이디 중복확인 체크 안한것
            Toast.makeText(Join_Activity.this, "ID중복확인버튼을 클릭해서 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return false;
        }
    }//idValidCheck

    //닉네임중복검사 버튼 클릭유효성검사
    private boolean nickNameBtnValidCheck(int nickChk) {
        if (nickChk == 1) {
            return true;
        } else if (nickChk == 0) {
            //아이디 중복확인 체크 안한것
            Toast.makeText(Join_Activity.this, "아이디중복확인버튼을 클릭해서 확인해주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return false;
        }
    }//NickNameBtnValidCheck


}//class Join_Activity

