package event.service;

import java.util.List;

import event.model.Event;

public class EventPage {
	private int totalCount; //총 게시글 수 
	private int currentPage; //보고싶은 페이지 = 현재 페이지
	private List<Event> content; //Event목록
	private int totalPages; //총 페이지 수 	15
	private int startPage; //시작 페이지 수 	'1'2345 '6'78910  '11'~15
	private int endPage; //마지막 페이지 수 	5  10  15
	
	EventPage(int totalCount, int currentPage, int size, List<Event> content){
		this.totalCount=totalCount;
		this.currentPage=currentPage;
		this.content=content;
		if(totalCount==0) { //게시글이 존재하지 않는 경우
			totalPages = 0;
			startPage = 0;
			endPage =0;
		} else { //게시글이 존재하는 경우.
			
			totalPages=totalCount/size;
		
			if(totalCount%size>0) {  //전체게시글 수를 한번에 조회할 게시글의 개수로 나눴을 때 남는 경우에는
				totalPages++; //전체페이지 수를 1 증가시켜라.
			}
		
			//startPage는 currentPage가 중요
			int modVal=currentPage%5; //나머지. 현재페이지를 5로 나눈 나머지. 5의 배수.
			startPage=currentPage/5*5+1;
			if(modVal==0) startPage = startPage-5;
			endPage=startPage+4;
			if(endPage>totalPages) endPage = totalPages; //전체페이지가 11인데 끝나는 페이지가 15면, 끝나는 페이지를 전체페이지로 덮어쓰기.
	
		}
	}
	public boolean hasNoEvents() {
		return totalCount==0;
	}
	public int getTotalCount() {
		return totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<Event> getContent() {
		return content;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getStartPage() {
		return startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	@Override
	public String toString() {
		return "EventPage [totalCount=" + totalCount + ", currentPage=" + currentPage + ", content=" + content
				+ ", totalPages=" + totalPages + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	
}
