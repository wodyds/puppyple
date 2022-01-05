package pet;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class PetDAO implements PetService {

	@Autowired
	@Qualifier("bteam")
	private SqlSession sql;

	@Override
	public int pet_and_petAdd(PetVO vo) {
		return sql.insert("pet.mapper.and_petAdd", vo);
	}

	@Override
	public List<PetVO> pet_and_petList(String member_id) {

		return sql.selectList("pet.mapper.and_petList", member_id);
	}

	@Override
	public int pet_and_petDelete(int pet_id) {
		return sql.delete("pet.mapper.and_petDelete", pet_id);
	}

	@Override
	public int pet_and_petModify(PetVO vo) {
		return sql.update("pet.mapper.and_petModify", vo);
	}

	public int pet_and_petModify_Nofile(PetVO vo) {
		return sql.update("pet.mapper.and_petModify_Nofile", vo);
	}

	@Override
	public List<PetVO> pet_petList(String member_id) {
		return sql.selectList("pet.mapper.pet_petList", member_id);
	}

	@Override
	public void pet_web_petAdd(PetVO vo) {
		sql.insert("pet.mapper.web_petAdd", vo);
	}

	@Override
	public void pet_web_petDelete(int pet_id) {
		sql.delete("pet.mapper.web_petDelete", pet_id);

	}

	@Override
	public PetVO pet_web_petSelect(int pet_id) {

		return sql.selectOne("pet.mapper.web_petSelect", pet_id);
	}

	@Override
	public void pet_web_petUpdate(PetVO vo) {
		sql.update("pet.mapper.web_petUpdate", vo);

	}

}
