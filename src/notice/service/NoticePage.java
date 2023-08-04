package notice.service;

import java.util.List;

import notice.model.Notice;


public class NoticePage {

	private int total;				//총게시글수
	private int currentPage;		//보고싶은 페이지=>현재 페이지
	private List<Notice> content;    //게시판 목록
	private List<Notice> searchResults;    //검색 목록

	private int  totalPages;		//총페이지수   15
	private int  startPage;			//시작페이지   12345  678910   11~15
	private int  endPage;			//끝페이지
	
	//생성자 
	//ListNoticeService에서 총 게시글 수+board목록,페이징 처리 정보 
	/*int total               	//총게시글수
	  int currentPage		    //보고싶은 페이지=>현재 페이지
	  int size    			    //1페이지당 출력할 게시글 수
	  List<Article> content  	//article목록 */
	NoticePage(List<Notice> content, int currentPage, int size, int total) {
		this.total = total; // 총게시글수
		this.currentPage = currentPage; // 현재 페이지(보고싶은페이지)
 		this.content = content; //게시판 목록
		
		if(total==0) { //게시글이 없는 경우
			totalPages=0;
			startPage=0;
			endPage=0;
		}else { //게시글 있으면 

			totalPages=total/size;	//총페이지수-p649 25라인  10/3
			if(total%size>0) { 
				totalPages++; 
		}
			int modVal = currentPage%5;
					
			startPage=currentPage/5*5+1;
			if(modVal==0)  startPage=startPage-5;
			
			endPage=startPage+4;	//끝페이지-p649 33라인
			if(endPage>totalPages) endPage=totalPages;}
	}
	
	
	
	
	



	public NoticePage(int total, int currentPage, List<Notice> content, List<Notice> searchResults, int totalPages,
			int startPage, int endPage) {
		this.total = total;
		this.currentPage = currentPage;
		this.content = content;
		this.searchResults = searchResults;
		this.totalPages = totalPages;
		this.startPage = startPage;
		this.endPage = endPage;
	}






	public void setSearchResults(List<Notice> searchResults) {
		this.searchResults = searchResults;
	}

	public List<Notice> getSearchResults() {
		return searchResults;
	}






	//총 게시글수 
	public int getTotal() {
		return total;
	}

	//총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false
	public boolean hasNoNotice() {
		return total==0;
	}
	
	//보고싶은 페이지=>현재 페이지	
	public int getCurrentPage() {
		return currentPage;
	}

	 //notice 목록
	public List<Notice> getContent() {
		return content;
	}

	//총페이지수
	public int getTotalPages() {
		return totalPages;
	}

	//시작페이지
	public int getStartPage() {
		return startPage;
	}

	//끝페이지
	public int getEndPage() {
		return endPage;
	}


	@Override
	public String toString() {
		return "NoticeBoardPage [total=" + total + ", currentPage=" + currentPage + ", content=" + content
				+ ", totalPages=" + totalPages + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	
	
}
