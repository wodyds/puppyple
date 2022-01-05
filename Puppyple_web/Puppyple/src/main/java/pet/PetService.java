package pet;

import java.util.List;

import gallery.GalleryVO;

public interface PetService {

	///////////////////// 안드로이드//////////////////////////////////////////

	// 펫정보등록시 정보 저장 (C)
	int pet_and_petAdd(PetVO vo);

	// 펫리스트불러오기
	List<PetVO> pet_and_petList(String member_id);

	// 펫리스트뷰에서 삭제처리 기능 구현
	int pet_and_petDelete(int pet_id);

	// 펫정보수정
	int pet_and_petModify(PetVO vo);

	
	/////////////////////////// 웹//////////////////////////////////////////////

	// 펫 리스트 불러오기
	List<PetVO> pet_petList(String member_id);

	// 펫정보등록
	void pet_web_petAdd(PetVO vo);

	// 펫 삭제
	void pet_web_petDelete(int pet_id);

	// 펫 셀렉트
	PetVO pet_web_petSelect(int pet_id);

	// 펫정보 수정
	void pet_web_petUpdate(PetVO vo);

}
