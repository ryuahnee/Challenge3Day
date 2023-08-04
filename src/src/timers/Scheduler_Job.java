package src.timers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import jdbc.connection.ConnectionProvider;
import myPage.dao.MypageDAO;
import myPage.model.MypageDTO;

// 일정 시간이 되면 DAO의 메소드를 실행시키고 그 시간과 결과를 알기 위한 클래스
public class Scheduler_Job implements Job{

	private Scheduler_DAO scheduler_DAO = new Scheduler_DAO();
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		int list = scheduler_DAO.challstatusUpdate();
		System.out.println("실행 시간 :" + new Date()+ "\t실행결과:" + list);
		
	}
			
}	//class

