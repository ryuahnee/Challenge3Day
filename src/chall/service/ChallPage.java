package chall.service;

import java.util.List;

import chall.model.Chall;

public class ChallPage {
	//필드
	private int total;				//총게시글수
	private int currentPage;		//보고싶은 페이지=>현재 페이지
	private List<Chall> content;  //chall목록
	private int  totalPages;		//총페이지수
	private int  startPage;			//시작페이지
	private int  endPage;			//끝페이지
	
	ChallPage(int total, int currentPage, int size, List<Chall> content){
		this.total=total;		//총게시글수
		this.currentPage=currentPage; //보고싶은 페이지=>현재 페이지
		this.content=content;	//chall목록
		
		if(total==0) { //게시글이 존재하지 않는 경우
			totalPages=0;
			startPage=0;
			endPage=0;
		}else { //게시글이 존재하는 경우
			totalPages=total/size;
			if(total%size>0) { 
				totalPages++; 
			}
			int modVal = currentPage%5; //현재페이지를 5로 나눈 나머지
			startPage=currentPage/5*5+1;
			if(modVal==0)  startPage=startPage-5;
			endPage=startPage+4;	//끝페이지
			if(endPage>totalPages) endPage=totalPages;
		}
	}

	public int getTotal() {
		return total; //총게시글수
	}
	
	//총게시글(수가 0이면)없으면  true리턴, 그렇지 않으면 false
	public boolean hasNoChalls() {
		return total==0;
	}

	public List<Chall> getContent() {
		return content; //chall목록
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

	@Override
	public String toString() {
		return "ChallPage [total=" + total + ", currentPage=" + currentPage + ", content=" + content + ", totalPages="
				+ totalPages + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
}