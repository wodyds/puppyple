package pet;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetDAO dao;

	@Override
	public int pet_and_petAdd(PetVO vo) {
		return dao.pet_and_petAdd(vo);
	}

	@Override
	public List<PetVO> pet_and_petList(String member_id) {
		return dao.pet_and_petList(member_id);
	}

	@Override
	public int pet_and_petDelete(int pet_id) {
		return dao.pet_and_petDelete(pet_id);
	}

	@Override
	public int pet_and_petModify(PetVO vo) {
		return dao.pet_and_petModify(vo);
	}

	public int pet_and_petModify_Nofile(PetVO vo) {
		return dao.pet_and_petModify_Nofile(vo);
	}

	@Override
	public List<PetVO> pet_petList(String member_id) {
		return dao.pet_petList(member_id);
	}

	@Override
	public void pet_web_petAdd(PetVO vo) {
		dao.pet_web_petAdd(vo);

	}

	@Override
	public void pet_web_petDelete(int pet_id) {
		dao.pet_web_petDelete(pet_id);

	}

	@Override
	public PetVO pet_web_petSelect(int pet_id) {

		return dao.pet_web_petSelect(pet_id);
	}

	@Override
	public void pet_web_petUpdate(PetVO vo) {
		dao.pet_web_petUpdate(vo);

	}

}
