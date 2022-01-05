var join = {
   tag_status : function (tag) {
      var data = tag.val();   // tag 내 입력된 값을 data 변수에 할당
      tag = tag.attr('name');   // tag 의 name 속성의 값을 확인 ex) id, pw, email...
      
      if ( tag == 'member_id')   		return this.member_id_status( data );
      else if ( tag == 'member_pw')    return this.member_pw_status ( data ); 
      else if ( tag == 'pw_ck')	return this.pw_ck_status ( data );  
      else if ( tag == 'member_nickname')	return this.member_nickname_status ( data );  
      else if ( tag == 'member_email') return this.member_email_status ( data );
   }
   , member_email_status : function ( member_email ) {
//		var reg = /([\w-\.]+)@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.)|(([\w-]+\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\]?)$/;
		var reg = /^([0-9a-zA-Z_\.-]+)@([0-9a-zA-Z_-]+)(\.[0-9a-zA-Z_-]+){1,2}$/;
		if (member_email == '')				return this.common.empty;
		else if (member_email.match(space)) 	return this.common.space;
		else if (reg.test(member_email))		return this.member_email.valid;
		else 							return this.member_email.invalid;
      				
	}
   , member_email : {
		valid : { code : 'valid' , desc : "유효한 이메일입니다."}
		, invalid : { code : "invalid", desc : "유효하지 않은 이메일입니다."}
	}
	
   , pw_ck_status : function ( pw_ck ) {
		if (pw_ck == '')		return this.common.empty;
		else if (pw_ck == $('[name=member_pw]').val())		return this.member_pw.equal;
		else					return this.member_pw.notEqual;	
	}
   , member_pw_status : function ( member_pw ) {
		var reg = /[^A-Za-z0-9]/g;
		var upper = /[A-Z]/g, lower = /[a-z]/g, digit = /[0-9]/g;
		if (member_pw == '')				return this.common.empty;
		else if (member_pw.match(space)) 	return this.common.space;
		/* 영문 대/소문자. 숫자 입력 여부 확인 */
		else if (reg.test(member_pw))		return this.member_pw.invalid;
      	/* 10자 이상 입력 여부 확인 */
      	else if (member_pw.length > 10) 	return this.common.max;
      	/* 영문 대/소문자. 숫자를 모두 포함 (즉, 하나라도 없다면) */
      	else if (!upper.test(member_pw) || !lower.test(member_pw) || !digit.test(member_pw) ) return this.member_pw.lack;
      	else						return this.member_pw.valid; 
	}
   , member_pw : { // pw 의 기준 설정
      invalid : { code : 'invalid' , desc : '비밀번호를 영문 대/소문자. 숫자를 모두 포함'}
      , valid : { code : 'valid' , desc : '사용 가능한 비밀번호입니다.'}
      , lack : { code : 'invalid' , desc : '비밀번호 영문 대/소문자. 숫자를 모두 포함'}
      , equal : {code : 'valid', desc : '비밀번호가 일치합니다.'}
      , notEqual : { code : 'invalid', desc : '비밀번호가 일치하지 않습니다.'}
   }
   , member_id_status : function ( member_id ) {   // id 의 상태 확인
      var reg = /^[a-z0-9]{4,12}$/; //소문자, 숫자 외 입력시
      
      /* 입력값이 없을 경우 */
      if (member_id == '') return this.common.empty;
      /* 입력값에 공백이 있을 경우 */
      else if (member_id.match(space))   return this.common.space;
      /* 소문자, 숫자 외 문자 입력 여부 확인 */
      else if (!reg.test(member_id)) return this.member_id.invalid;
      /* 5자 이하 입력 여부 확인 */
     /* else if (id.length < 5) return this.common.min;*/
      /* 10자 이상 입력 여부 확인 */
      else if (member_id.length > 12) return this.common.max;
      else   return this.member_id.valid;
   }
   
   , member_id : { // id 의 기준 설정
      invalid : { code:'invalid', desc : '아이디는 4~12자 영문 소문자, 숫자만 입력 가능' }
      ,valid : { code : 'valid', desc : '사용 가능한 아이디 입니다1.'}  
      ,usable : { code : 'valid', desc : '사용 가능한 아이디 입니다2.'}
      ,unUsable : { code : 'invalid', desc : '이미 사용 중인 아이디입니다' }
   }
   , member_nickname_status : function ( member_nickname ) {   // 닉네임의  상태 확인
      var reg = /^[\w\W가-힣]{2,20}$/;
      
      /* 입력값이 없을 경우 */
      if ( member_nickname == '') return this.common.empty;
      /* 입력값에 공백이 있을 경우 */
      else if (member_nickname.match(space))   return this.common.space;
      /* 소문자, 숫자 외 문자 입력 여부 확인 */
      else if (!reg.test(member_nickname)) return this.member_nickname.invalid;
      else   return this.member_nickname.valid;
   }
   
   ,member_nickname : { // nickname 의 기준 설정
      invalid : { code:'invalid', desc : '닉네임은 2~20자 한글,영문,숫자만 입력 가능' }
      ,valid : { code : 'valid', desc : '사용 가능한 닉네임 입니다.'}  
      ,usable : { code : 'valid', desc : '사용 가능한 닉네임 입니다.'}
      ,unUsable : { code : 'invalid', desc : '이미 사용 중인 닉네임입니다' }
   }
   ,common : {
      empty : { code : 'invalid', desc : '입력하세요'}
      , space : { code : 'invalid', desc : '공백없이 입력하세요'}
      , max : { code : 'invalid', desc : '최대 12자 이하로 입력하세요'}
   }
   ,member_id_usable : function ( usable ) {
	 if ( usable)	return this.member_id.usable;
	 else 			return this.member_id.unUsable;
	}
   ,member_nickname_usable : function ( usable ) {
	 if ( usable)	return this.member_nickname.usable;
	 else 			return this.member_nickname.unUsable;
	}
   
}
var space = /\s/g;   // 전체 문자에 공백이 있는지 확인... space 할당