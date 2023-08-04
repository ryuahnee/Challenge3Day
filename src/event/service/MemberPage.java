package event.service;

import java.util.List;

import event.model.EventMemList;

public class MemberPage {
	private int totalCount;
	private int currentPage;
	private List<EventMemList> content;
	private int totalPages;
	private int startPage;
	private int endPage;
	
	MemberPage(int totalCount, int currentPage, int size, List<EventMemList> content){
		this.totalCount=totalCount;
		this.currentPage=currentPage;
		this.content=content;
		if(totalCount==0) { //신청자 없는 경우
			totalPages = 0;
			startPage = 0;
			endPage = 0;
		} else { //신청자 있는 경우
			totalPages = totalCount/size;
			if(totalCount%size>0) {
				totalPages++;
			}
		}
		int modVal = currentPage%5;
		startPage=currentPage/5*5+1;
		if(modVal==0) startPage = startPage-5;
		endPage = startPage+4;
		if(endPage>totalPages) endPage = totalPages;
	}
	
	public boolean hasNoApplicants() {
		return totalCount==0;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public List<EventMemList> getContent() {
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
		return "MemberPage [totalCount=" + totalCount + ", currentPage=" + currentPage + ", content=" + content
				+ ", totalPages=" + totalPages + ", startPage=" + startPage + ", endPage=" + endPage + "]";
	}
	
	
}

