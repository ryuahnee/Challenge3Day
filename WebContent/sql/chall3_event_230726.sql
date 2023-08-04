SELECT * FROM challenge3.event_board;
-- 이벤트 테이블의 글 등록일의 데이터타입 datetime, 기본값을 current_timestamp로 수정.
ALTER TABLE challenge3.event_board 
MODIFY event_regdate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '이벤트 등록 일';


-- 이벤트 신청목록 테이블의 회원에 대한 이벤트 선정여부 컬럼의 기본값을 'N'으로 수정. 
ALTER TABLE challenge3.eventmem_list 
MODIFY isSelector char(1) DEFAULT 'N' COMMENT '이벤트 선정 여부';

-- 특정 이벤트 글 상세 조회
select event_no, event_title, event_con, event_regdate
from event_board
where event_no=1;


-- 특정 회원의 획득한 뱃지 개수를 조회
-- 이벤트 글의 체험단 참여 버튼을 클릭할 때 제한을 주기 위함.
select count(ach_badge_no)
from member_badge
where mem_no=?;

-- 이벤트 글 수정
-- 해당 글이 존재하는지 확인
select * from event_board
where event_no=6;
-- 수정처리
update event_board 
set event_title='클릭하고 싶게 만드는 제목2', event_con='참여하고 싶게 만드는 내용2'
where event_no=6;

INSERT INTO event_board (event_title, event_con) 
VALUES ('야 너두 갓생러 될 수 있어 제2탄', '스타터를 환영합니다! 아무나에게 응원 기프트 세트 증정! 기간: 8월 한달간');

-- too many connections 에러 파악하기 위한 쿼리문
show variables like 'max_connections';
show status like 'max_used_connections';
show status like 'Aborted%';


-- event_board에 컬럼 추가
-- article테이블에 isShow컬럼을 추가. 'Y'(노출, 기본), 'N'(비노출, 삭제효과)
ALTER TABLE event_board
	 ADD has_btn varchar(1) DEFAULT 'N' check(has_btn in('Y','N')) COMMENT '신청 버튼 유무';



-- 회원이 이벤트 글 상세 조회시에 나타나는 버튼을 클릭하면 eventmem_list에 회원 정보가 추가.
-- 참가번호랑 선정여부는 빼도 됨. (기본값들어가서.)
insert into eventmem_list (event_appli_no, event_no, mem_no, isSelector)
values(1, 5, 2, 'N');

-- 회원이 가진 뱃지 개수를 조회
select count(ach_badge_no)
from member_badge
where mem_no=2;

-- 관리자는 이벤트 글 상세 조회시, 신청 회원 목록 조회할 수 있다.
-- 근데 조인해서 볼 수 있어야 할듯. 
-- 회원 아이디랑 닉네임, 성별, 생년월일 볼 수 있어야 함 -> 이벤트 유의사항에 써놓자..
-- 수정해야함.
select e.event_appli_no, e.event_no, m.mem_no, e.isSelector, m.id, m.nickname, m.mem_email
from eventmem_list e, member m;

-- on delete cascade 속성 추가
-- 1)기존 부모 테이블에 걸려있는 Foreign Key 제거, 다시 제약 조건 걸어서 추가.
delete from event_board
where event_no=18;

-- 검색
select event_title, event_con
from event_board
where event_title like '%모%' or event_con like '%모%';

select event_title
from event_board
where event_title like  concat('%','모','%');

-- 관리자의 이벤트 신청회원 조회
select e.event_no, e.mem_no, m.id, m.mem_name, m.nickname, m.birthyear, m.gender, m.isMarketing
from eventmem_list e inner join member m
on e.mem_no = m.mem_no
where event_no=19
order by mem_no asc;

-- 신청회원 목록 리스트 조회
select count(mem_no)
from eventmem_list
where event_no=19;

-- 신청한 회원목록에서 여러명 선택 후 버튼 클릭하면 값 변경.
update eventmem_list
set isselector  = 'N'
where event_appli_no in (1, 2, 3);

select * from eventmem_list;