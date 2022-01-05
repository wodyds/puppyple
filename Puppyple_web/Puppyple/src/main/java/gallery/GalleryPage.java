package gallery;

import java.util.List;

import org.springframework.stereotype.Component;

import common.PageVO;

@Component
public class GalleryPage extends PageVO {

	private List<GalleryVO> list;

	public List<GalleryVO> getList() {
		return list;
	}

	public void setList(List<GalleryVO> list) {
		this.list = list;
	}	
}
