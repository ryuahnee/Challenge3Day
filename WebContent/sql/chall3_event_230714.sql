SELECT * FROM challenge3.event_board;
-- 이벤트 테이블의 글 등록일의 데이터타입 datetime, 기본값을 current_timestamp로 수정.
ALTER TABLE challenge3.event_board MODIFY event_regdate datetime DEFAULT CURRENT_TIMESTAMP COMMENT '이벤트 등록 일';

-- 이벤트 신청목록 테이블의 회원에 대한 이벤트 선정여부 컬럼의 기본값을 'N'으로 수정. 
ALTER TABLE challenge3.eventmem_list MODIFY isSelector char(1) DEFAULT 'N' COMMENT '이벤트 선정 여부';