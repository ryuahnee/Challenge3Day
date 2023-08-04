package member.service;

import java.util.List;

import member.model.Member;

public class MemberPage {
	//필드-p649 9라인
	private int total;				//총게시글수
	private int currentPage;		//보고싶은 페이지=>현재 페이지
	private List<Member> content;  //article목록
	private int totalPages;		//총페이지수   15
	private int startPage;			//시작페이지   12345  678910   11~15
	private int endPage;			//끝페이지
	
	MemberPage(int total, int currentPage, int size, List<Member> content) {
		this.total=total;
		this.currentPage=currentPage;
		this.content=content;
		
		
		if(total==0) { //게시글이 존재하지 않는 경우
			totalPages=0;
			startPage=0;
			endPage=0;
		}else { //게시글이 존재하는 경우
			totalPages=total/size;//총페이지수
			if(total%size>0) { 
				totalPages++; 
			}
	        
			int modVal = currentPage%5;	//현재페이지를 5로 나눈 나머지=> 5의 배수 5,10,15~
			startPage=currentPage/5*5+1;
			if(modVal==0)  startPage=startPage-5;
			
			endPage=startPage+4;	//끝페이지-p649 33라인
			if(endPage>totalPages) endPage=totalPages;
		}
	}

	//p650 38라인
	public int getTotal() {
		return total; //총게시글수
	}
	
	//p650 42라인 - 총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false
	public boolean hasNoArticles() {
		return total==0;
	}

	//p650 58라인
	public List<Member> getContent() {
		return content; //article목록
	}

	//보고싶은 페이지=>현재 페이지	
	public int getCurrentPage() {
		return currentPage; 
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

}



