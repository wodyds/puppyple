package common;

public class PageVO {
	
	int totallist;										// 총 글의 건수 (DB에서 조회해 온 전체 글수) 
	int totalPage;									// 총 페이지수 				
	int totalBlock;									// 총 블록 수
	int pageList = 10;							// 페이지당 보여질 목록(글)의 수 
	int blockPage = 5;							// 블록당 보여질 페이지의 수
	int curPage;										// 현재 페이지
	int beginList, endList;					// 각 페이지에 보여질 시작 목록(글) 번호, 끝 목록(글)번호
	int curBlock;									// 현재 블록
	int beginPage, endPage;				// 각 블록에 보여질 시작, 끝 페이지 번호
	String search, keyword;				// 검색조건, 검색어
	private String viewType="list";	// 게시판 형태(기본 : 목록형태(list))
	private String flag;						// flag로 나눈 목록 조회
	
	public String getViewType() {
		return viewType;
	}
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public int getTotallist() {
		return totallist;
	}
	public void setTotallist(int totallist) {
		this.totallist = totallist;   // DB에서 받아온 총 글의 건수
		totalPage = totallist / pageList; 	// 총 페이수 = 총 건수 / 페이지당 보여질 글 수
		if ( totallist % pageList > 0 ) ++totalPage; // 총 페이지수
		totalBlock = totalPage / blockPage;	// 총 블록수 = 총 페이지수 / 블록당 보여질 페이지 수
		if ( totalPage % blockPage > 0 ) ++totalBlock;	// 총 블록 수
		
		// 현재 페이지에 보여질 글의 시작 / 끝 목록(글)
		endList = totallist - (curPage -1) * pageList;
		beginList = endList - (pageList -1);
		
		// 블록번호
		curBlock = curPage / blockPage;
		if ( curPage % blockPage > 0 ) ++curBlock;
		
		// 각 블록의 끝 페이지 번호 : 블록번호 * 블록당 보여질 페이지
		endPage = curBlock * blockPage;
		// 각 블록의 시작 페이지 번호 : 끝 페이지번호 - (블럭당 보여질 페이지수-1)
		beginPage = endPage - (blockPage -1 );
		
		// 블록에 보여질 마지막 페이지수
		if (endPage > totalPage) endPage = totalPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getTotalBlock() {
		return totalBlock;
	}
	public void setTotalBlock(int totalBlock) {
		this.totalBlock = totalBlock;
	}
	public int getPageList() {
		return pageList;
	}
	public void setPageList(int pageList) {
		this.pageList = pageList;
	}
	public int getBlockPage() {
		return blockPage;
	}
	public void setBlockPage(int blockPage) {
		this.blockPage = blockPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	public int getBeginList() {
		return beginList;
	}
	public void setBeginList(int beginList) {
		this.beginList = beginList;
	}
	public int getEndList() {
		return endList;
	}
	public void setEndList(int endList) {
		this.endList = endList;
	}
	public int getCurBlock() {
		return curBlock;
	}
	public void setCurBlock(int curBlock) {
		this.curBlock = curBlock;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public void setBeginPage(int beginPage) {
		this.beginPage = beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	
}
