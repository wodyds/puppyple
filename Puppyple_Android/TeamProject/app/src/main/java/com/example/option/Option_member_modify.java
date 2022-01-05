package com.example.option;

import static com.example.Common.CommonMethod.loginDTO;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ATask.MemberModify;
import com.example.ATask.NicknameCheck;
import com.example.PUPPYPLE.R;
import com.example.login.Login_Activity;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;


public class Option_member_modify extends AppCompatActivity {

    Button btnMemberModifyCancel, btnMemberModifySave, btnMemberModifyNicknameChk;

    ImageView ivMemberModify_back;

    TextView etMemberModifyId;

    EditText etMemberModifyPw, etMemberModifyPwChk, etMemberModifyEmail, etMemberModifyNickname, etMemberModifyPhone;

    //20211123 유효성 검사 연결
    //비밀번호 정규식
    private static final Pattern MEMBER_PW_PATTERN = Pattern.compile("^[a-zA-Z0-9!@.#$%^&*?_~]{4,16}$");
    //전화번호 정규식
    private static final Pattern MEMBER_PHONE_PATTERN = Pattern.compile("^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", Pattern.CASE_INSENSITIVE);
    //이메일 정규식
    private static final Pattern MEMBER_EMAIL_PATTERN = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    //닉네임 정규식
    private static final Pattern MEMBER_NICKNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9ㄱ-ㅎ가-힣]+$");

    String state;
    int nickChk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.option_member_modify);

        //Button
        btnMemberModifyCancel = findViewById(R.id.btnMemberModifyCancel);
        btnMemberModifySave = findViewById(R.id.btnMemberModifySave);
      btnMemberModifyNicknameChk = findViewById(R.id.btnMemberModifyNicknameChk);

        //ImageView
        ivMemberModify_back = findViewById(R.id.ivMemberModify_back);

        //EditText
       etMemberModifyId = findViewById(R.id.etMemberModifyId);
        etMemberModifyPw = findViewById(R.id.etMemberModifyPw);
        etMemberModifyPwChk = findViewById(R.id.etMemberModifyPwChk);
        etMemberModifyPhone = findViewById(R.id.etMemberModifyPhone);
        etMemberModifyEmail = findViewById(R.id.etMemberModifyEmail);
        etMemberModifyNickname = findViewById(R.id.etMemberModifyNickname);

        //뒤로가기 이미지 버튼 기능
        ivMemberModify_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        //취소버튼 클릭시 기능
        btnMemberModifyCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                overridePendingTransition(0, 0); //애니메이션 없애기
            }
        });

        //ID EditText에 member_id 보여주기. xml창에는 수정을 못하게 기능 구현완료
        etMemberModifyId.setText(loginDTO.getMember_id());

        //나머지 EditText도 정보가 보여지도록 구현
        //etMemberModifyPw.setText(loginDTO.getMember_pw()); //PW의경우 loginDTO에 정보가 없음
        etMemberModifyEmail.setText(loginDTO.getMember_email());
        etMemberModifyNickname.setText(loginDTO.getMember_nickname());
        etMemberModifyPhone.setText(loginDTO.getMember_phone());

        //저장버튼기능
        btnMemberModifySave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String member_id = etMemberModifyId.getText().toString();
                String member_pw = etMemberModifyPw.getText().toString();
                String member_pwchk = etMemberModifyPwChk.getText().toString();
                String member_phone = etMemberModifyPhone.getText().toString();
                String member_email = etMemberModifyEmail.getText().toString();
                String member_nickname = etMemberModifyNickname.getText().toString();

                if (isValidPw(member_pw) && isValidPwChk(member_pw, member_pwchk) && isValidPhone(member_phone)
                        && isValidEmail(member_email) && isValidNickname(member_nickname)&&nickNameBtnValidCheck(nickChk)) {
                    //추가적으로 Nickname 중복검사 버튼 기능 구현 필요 및 구현 후 nickname 버튼클릭시만 실행되도록 기능구현 필요
                    //20211123 현재는 일단 저장기능까지 구현후 하나씩 구현할 예정임

                //이제 위의 정보를 비동기 Task로 넘겨 서버에게 전달한다.
                MemberModify memberModify = new
                        MemberModify(member_id, member_pw, member_phone, member_email, member_nickname);
                try {
                    state = memberModify.execute().get().trim();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (state.equals("1")) {
                    Toast.makeText(Option_member_modify.this,
                            "회원정보가 정상적으로 수정되었습니다", Toast.LENGTH_SHORT).show();

                    //20211129 회원정보 수정후에 loginDTO가 null로 바뀌면서 정보 사라지고, 메인페이지로 넘어간다.
                    loginDTO = null;
                    Intent intent = new Intent(Option_member_modify.this, Login_Activity.class);
                    startActivity(intent);

                    finish();



                } else {
                    Toast.makeText(Option_member_modify.this,
                            "회원정보 수정에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(Option_member_modify.this, "회원가입 수정이 안됐습니다.", Toast.LENGTH_SHORT).show();
            }






            }//onClick()
        });//수정된사항 저장버튼 클릭 기능



        //20211124 닉네임 체크, 20211129 기존 닉네임 사용할수 있도록 처리
        //닉네임 중복검사
        btnMemberModifyNicknameChk.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String member_nickname = etMemberModifyNickname.getText().toString();

                if (loginDTO.getMember_nickname().equals(member_nickname)) {

                    AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Option_member_modify.this)
                            .setTitle("닉네임 중복검사")
                            .setMessage("기존에 사용 중인 닉네임입니다.\n사용하시겠습니까?")
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


                }else if (! loginDTO.getMember_nickname().equals(member_nickname)) {
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

                        AlertDialog.Builder msgIdChk = new AlertDialog.Builder(Option_member_modify.this)
                                .setTitle("닉네임 중복검사")
                                .setMessage("사용 가능한 닉네임입니다.\n사용하시겠습니까?")
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
                        Toast.makeText(Option_member_modify.this,
                                "사용 중인 닉네임입니다. 다른 닉네임을 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            }//onClick()

        });//닉네임중복검사






    }//onCreate()


    ///////////////////////////////////////////////////////////////////////////
    //20211123 유효성검사
    //비밀번호 유효성 검사
    private boolean isValidPw(String member_pw) {
        if (member_pw.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Option_member_modify.this, "비밀번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_PW_PATTERN.matcher(member_pw).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Option_member_modify.this, "비밀번호 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidPw

    // 비밀번호 체크 유효성 검사
    private boolean isValidPwChk(String member_pw, String member_pwchk) {
        if (member_pwchk.isEmpty()) {
            // 비밀번호 칸이 공백이면 false
            Toast.makeText(Option_member_modify.this, "비밀번호를 다시 입력하세요", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!member_pw.equals(member_pwchk)) {
            // 비밀번호와 비밀번호 확인에 입력한 값이 불일치하면 false
            Toast.makeText(Option_member_modify.this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isVaildpwChk

    //전화번호 유효성 검사
    private boolean isValidPhone(String member_phone) {
        if (member_phone.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Option_member_modify.this, "전화번호를 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_PHONE_PATTERN.matcher(member_phone).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Option_member_modify.this, "전화번호 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidEmail

    //이메일 유효성 검사
    private boolean isValidEmail(String member_email) {
        if (member_email.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Option_member_modify.this, "이메일을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_EMAIL_PATTERN.matcher(member_email).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Option_member_modify.this, "이메일 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isValidEmail

    //별명 닉네임 유효성 검사
    private boolean isValidNickname(String member_nickname) {
        if (member_nickname.isEmpty()) {
            //아이디 칸에 공백이면 false
            Toast.makeText(Option_member_modify.this, "닉네임을 입력하세요.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!MEMBER_NICKNAME_PATTERN.matcher(member_nickname).matches()) {
            // 이메일 형식이 불일치하면 false
            Toast.makeText(Option_member_modify.this, "닉네임 형식이 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }//isVaildNickname

    //닉네임중복검사 버튼 클릭유효성검사
    private boolean nickNameBtnValidCheck(int nickChk) {
        if (nickChk == 1) {
            return true;
        } else if (nickChk == 0) {
            //아이디 중복확인 체크 안한것
            Toast.makeText(Option_member_modify.this, "아이디 중복 확인 버튼을 클릭해서 확인해 주세요.", Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return false;
        }
    }//NickNameBtnValidCheck







}//Option_member_modify_Activity()