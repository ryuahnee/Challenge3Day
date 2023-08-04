package event.service;
//보여줄 상세 이벤트가 없으면 도착
public class EventNotFoundException extends RuntimeException {
	public static void main(String [] args) {
		System.out.println("이벤트 페이지가 존재하지 않습니다.");
	}
}