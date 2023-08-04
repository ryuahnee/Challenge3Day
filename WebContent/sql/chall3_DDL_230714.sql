-- 순환참조를 피하기 위해, 테이블 생성을 먼저 한뒤, 외래키 추가하는 것은 나중에 실행하기!

-- 테이블 순서는 관계를 고려하여 한 번에 실행해도 에러가 발생하지 않게 정렬되었습니다.

-- category Table Create SQL
-- 테이블 생성 SQL - category
CREATE TABLE category
(
    `cate_no`    INT            NOT NULL    COMMENT '카테고리 번호', 
    `cate_name`  VARCHAR(45)    NOT NULL    COMMENT '카테고리 명', 
     PRIMARY KEY (cate_no)
);

-- 테이블 Comment 설정 SQL - category
ALTER TABLE category COMMENT '카테고리';


-- member Table Create SQL
-- 테이블 생성 SQL - member
CREATE TABLE member
(
    `mem_no`       INT            NOT NULL    AUTO_INCREMENT COMMENT '회원 번호', 
    `id`           VARCHAR(15)    NOT NULL    COMMENT '아이디', 
    `pwd`          VARCHAR(50)    NOT NULL    COMMENT '비밀번호', 
    `mem_name`     VARCHAR(45)    NOT NULL    COMMENT '회원 명', 
    `nickname`     VARCHAR(10)    NOT NULL    COMMENT '닉네임', 
    `birthyear`    DATE           NOT NULL    COMMENT '생년', 
    `gender`       CHAR(5)        NOT NULL    COMMENT '성별', 
    `join_date`    DATETIME       NOT NULL    DEFAULT NOW() COMMENT '가입 일', 
    `mem_email`    VARCHAR(45)    NOT NULL    COMMENT '회원 이메일', 
    `isMarketing`  CHAR(1)        NOT NULL    COMMENT '마케팅수신여부', 
    `wannig_cnt`   INT            NOT NULL    COMMENT '누적 경고 횟수', 
     PRIMARY KEY (mem_no)
);

-- 테이블 Comment 설정 SQL - member
ALTER TABLE member COMMENT '회원';


-- challenge_board Table Create SQL
-- 테이블 생성 SQL - challenge_board
CREATE TABLE challenge_board
(
    `chall_no`             INT             NOT NULL    AUTO_INCREMENT COMMENT '챌린지 번호', 
    `cate_no`              INT             NOT NULL    COMMENT '카테고리 번호', 
    `chall_title`          VARCHAR(45)     NOT NULL    COMMENT '챌린지 제목', 
    `mem_no`               INT             NOT NULL    COMMENT '회원 번호', 
    `chall_write_date`     DATETIME        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '챌린지 작성 일', 
    `chall_con`            TEXT            NOT NULL    COMMENT '챌린지 내용', 
    `chall_hit`            INT             NOT NULL    COMMENT '챌린지 조회수', 
    `certifi_words`        VARCHAR(45)     NOT NULL    COMMENT '인증 문구', 
    `chall_orifile_name`   VARCHAR(200)    NOT NULL    COMMENT '챌린지 원본파일 명', 
    `chall_copyfile_name`  INT             NOT NULL    COMMENT '챌린지 사본파일 명', 
     PRIMARY KEY (chall_no)
);

-- 테이블 Comment 설정 SQL - challenge_board
ALTER TABLE challenge_board COMMENT '챌린지 게시판';

-- Foreign Key 설정 SQL - challenge_board(cate_no) -> category(cate_no)
ALTER TABLE challenge_board
    ADD CONSTRAINT FK_challenge_board_cate_no_category_cate_no FOREIGN KEY (cate_no)
        REFERENCES category (cate_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - challenge_board(cate_no)
-- ALTER TABLE challenge_board
-- DROP FOREIGN KEY FK_challenge_board_cate_no_category_cate_no;

-- Foreign Key 설정 SQL - challenge_board(mem_no) -> member(mem_no)
ALTER TABLE challenge_board
    ADD CONSTRAINT FK_challenge_board_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - challenge_board(mem_no)
-- ALTER TABLE challenge_board
-- DROP FOREIGN KEY FK_challenge_board_mem_no_member_mem_no;


-- badge Table Create SQL
-- 테이블 생성 SQL - badge
CREATE TABLE badge
(
    `badge_no`    INT            NOT NULL    AUTO_INCREMENT COMMENT '뱃지 번호', 
    `badge_name`  VARCHAR(45)    NOT NULL    COMMENT '뱃지 명', 
    `cate_no`     INT            NULL        COMMENT '카테고리 번호', 
     PRIMARY KEY (badge_no)
);

-- 테이블 Comment 설정 SQL - badge
ALTER TABLE badge COMMENT '뱃지';

-- Foreign Key 설정 SQL - badge(cate_no) -> category(cate_no)
ALTER TABLE badge
    ADD CONSTRAINT FK_badge_cate_no_category_cate_no FOREIGN KEY (cate_no)
        REFERENCES category (cate_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - badge(cate_no)
-- ALTER TABLE badge
-- DROP FOREIGN KEY FK_badge_cate_no_category_cate_no;


-- qna_board Table Create SQL
-- 테이블 생성 SQL - qna_board
CREATE TABLE qna_board
(
    `qna_no`             INT             NOT NULL    AUTO_INCREMENT COMMENT '문의 번호', 
    `qna_title`          VARCHAR(45)     NOT NULL    COMMENT '문의 제목', 
    `qna_con`            TEXT            NOT NULL    COMMENT '문의 내용', 
    `mem_no`             INT             NOT NULL    COMMENT '회원 번호', 
    `qna_date`           DATETIME        NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '문의 일', 
    `qna_fin_check`      VARCHAR(45)     NOT NULL    COMMENT '문의 완료 확인', 
    `qna_orifile_name`   VARCHAR(200)    NULL        COMMENT '문의 원본파일 명', 
		`qna_copyfile_name`  INT             NULL        COMMENT '문의 사본파일 명', 
     PRIMARY KEY (qna_no)
);

-- 테이블 Comment 설정 SQL - qna_board
ALTER TABLE qna_board COMMENT '1:1문의 게시판';

-- Foreign Key 설정 SQL - qna_board(mem_no) -> member(mem_no)
ALTER TABLE qna_board
    ADD CONSTRAINT FK_qna_board_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - qna_board(mem_no)
-- ALTER TABLE qna_board
-- DROP FOREIGN KEY FK_qna_board_mem_no_member_mem_no;


-- event_board Table Create SQL
-- 테이블 생성 SQL - event_board
CREATE TABLE event_board
(
    `event_no`       INT            NOT NULL    AUTO_INCREMENT COMMENT '이벤트 번호', 
    `event_title`    VARCHAR(45)    NOT NULL    COMMENT '이벤트 제목', 
    `event_con`      TEXT           NOT NULL    COMMENT '이벤트 내용', 
    `event_regdate`  DATETIME       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '이벤트 등록 일', 
     PRIMARY KEY (event_no)
);

-- 테이블 Comment 설정 SQL - event_board
ALTER TABLE event_board COMMENT '이벤트 게시판';


-- qna_answer_board Table Create SQL
-- 테이블 생성 SQL - qna_answer_board
CREATE TABLE qna_answer_board
(
    `qna_a_no`    INT         NOT NULL    AUTO_INCREMENT COMMENT '문의 답변 번호', 
    `qna_no`      INT         NOT NULL    COMMENT '문의 번호', 
    `qna_a_con`   TEXT        NOT NULL    COMMENT '문의 답변 내용', 
    `qna_a_date`  DATETIME    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '문의 답변 일', 
     PRIMARY KEY (qna_a_no)
);

-- 테이블 Comment 설정 SQL - qna_answer_board
ALTER TABLE qna_answer_board COMMENT '문의 답변 게시판';

-- Foreign Key 설정 SQL - qna_answer_board(qna_no) -> qna_board(qna_no)
ALTER TABLE qna_answer_board
    ADD CONSTRAINT FK_qna_answer_board_qna_no_qna_board_qna_no FOREIGN KEY (qna_no)
        REFERENCES qna_board (qna_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - qna_answer_board(qna_no)
-- ALTER TABLE qna_answer_board
-- DROP FOREIGN KEY FK_qna_answer_board_qna_no_qna_board_qna_no;


-- notice_board Table Create SQL
-- 테이블 생성 SQL - notice_board
CREATE TABLE notice_board
(
    `noti_no`     INT            NOT NULL    AUTO_INCREMENT COMMENT '공지 번호', 
    `noti_title`  VARCHAR(45)    NOT NULL    COMMENT '공지 제목', 
    `noti_con`    TEXT           NOT NULL    COMMENT '공지 내용', 
    `noti_date`   DATETIME       NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '공지 일', 
     PRIMARY KEY (noti_no)
);

-- 테이블 Comment 설정 SQL - notice_board
ALTER TABLE notice_board COMMENT '공지사항 게시판';


-- member_badge Table Create SQL
-- 테이블 생성 SQL - member_badge
CREATE TABLE member_badge
(
    `ach_badge_no`  INT         NOT NULL    AUTO_INCREMENT COMMENT '획득 뱃지 번호', 
    `mem_no`        INT         NOT NULL    COMMENT '회원 번호', 
    `badge_no`      INT         NOT NULL    COMMENT '뱃지 번호', 
    `ach_date`      DATETIME    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '획득 일', 
     PRIMARY KEY (ach_badge_no)
);

-- 테이블 Comment 설정 SQL - member_badge
ALTER TABLE member_badge COMMENT '회원뱃지';

-- Foreign Key 설정 SQL - member_badge(mem_no) -> member(mem_no)
ALTER TABLE member_badge
    ADD CONSTRAINT FK_member_badge_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - member_badge(mem_no)
-- ALTER TABLE member_badge
-- DROP FOREIGN KEY FK_member_badge_mem_no_member_mem_no;

-- Foreign Key 설정 SQL - member_badge(badge_no) -> badge(badge_no)
ALTER TABLE member_badge
    ADD CONSTRAINT FK_member_badge_badge_no_badge_badge_no FOREIGN KEY (badge_no)
        REFERENCES badge (badge_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - member_badge(badge_no)
-- ALTER TABLE member_badge
-- DROP FOREIGN KEY FK_member_badge_badge_no_badge_badge_no;


-- challenge_opinion Table Create SQL
-- 테이블 생성 SQL - challenge_opinion
CREATE TABLE challenge_opinion
(
    `chall_opin_no`  INT         NOT NULL    AUTO_INCREMENT COMMENT '챌린지 소감 번호', 
    `chall_no`       INT         NOT NULL    COMMENT '챌린지 번호', 
    `mem_no`         INT         NOT NULL    COMMENT '회원 번호', 
    `opin_con`       TEXT        NOT NULL    COMMENT '소감 내용', 
    `write_date`     DATETIME    NOT NULL    DEFAULT CURRENT_TIMESTAMP COMMENT '작성 일', 
     PRIMARY KEY (chall_opin_no)
);

-- 테이블 Comment 설정 SQL - challenge_opinion
ALTER TABLE challenge_opinion COMMENT '챌린지 인증 소감';

-- Foreign Key 설정 SQL - challenge_opinion(chall_no) -> challenge_board(chall_no)
ALTER TABLE challenge_opinion
    ADD CONSTRAINT FK_challenge_opinion_chall_no_challenge_board_chall_no FOREIGN KEY (chall_no)
        REFERENCES challenge_board (chall_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - challenge_opinion(chall_no)
-- ALTER TABLE challenge_opinion
-- DROP FOREIGN KEY FK_challenge_opinion_chall_no_challenge_board_chall_no;

-- Foreign Key 설정 SQL - challenge_opinion(mem_no) -> member(mem_no)
ALTER TABLE challenge_opinion
    ADD CONSTRAINT FK_challenge_opinion_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - challenge_opinion(mem_no)
-- ALTER TABLE challenge_opinion
-- DROP FOREIGN KEY FK_challenge_opinion_mem_no_member_mem_no;


-- eventMem_list Table Create SQL
-- 테이블 생성 SQL - eventMem_list
CREATE TABLE eventMem_list
(
    `event_appli_no`  INT        NULL        COMMENT '이벤트 신청 번호', 
    `event_no`        INT        NOT NULL    COMMENT '이벤트 번호', 
    `mem_no`          INT        NOT NULL    COMMENT '회원 번호', 
    `isSelector`      CHAR(1)    NOT NULL    DEFAULT 'N'    COMMENT '이벤트 선정 여부', 
     PRIMARY KEY (event_appli_no)
);

-- 테이블 Comment 설정 SQL - eventMem_list
ALTER TABLE eventMem_list COMMENT '이벤트 신청 목록';

-- Foreign Key 설정 SQL - eventMem_list(event_no) -> event_board(event_no)
ALTER TABLE eventMem_list
    ADD CONSTRAINT FK_eventMem_list_event_no_event_board_event_no FOREIGN KEY (event_no)
        REFERENCES event_board (event_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - eventMem_list(event_no)
-- ALTER TABLE eventMem_list
-- DROP FOREIGN KEY FK_eventMem_list_event_no_event_board_event_no;

-- Foreign Key 설정 SQL - eventMem_list(mem_no) -> member(mem_no)
ALTER TABLE eventMem_list
    ADD CONSTRAINT FK_eventMem_list_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - eventMem_list(mem_no)
-- ALTER TABLE eventMem_list
-- DROP FOREIGN KEY FK_eventMem_list_mem_no_member_mem_no;


-- member_chall_status Table Create SQL
-- 테이블 생성 SQL - member_chall_status
CREATE TABLE member_chall_status
(
    `chall_manual_no`   INT         NOT NULL    AUTO_INCREMENT COMMENT '챌린지 관리 번호', 
    `chall_no`          INT         NOT NULL    COMMENT '챌린지 번호', 
    `mem_no`            INT         NOT NULL    COMMENT '회원 번호', 
    `chall_ing_status`  INT         NOT NULL    COMMENT '챌린지 진행 상태', 
    `start_date`        DATETIME    NOT NULL    COMMENT '시작일', 
    `fimal_date`        DATETIME    NOT NULL    COMMENT '종료일', 
    `chall_result`      CHAR(1)     NOT NULL    COMMENT '챌린지 결과', 
     PRIMARY KEY (chall_manual_no)
);

-- 테이블 Comment 설정 SQL - member_chall_status
ALTER TABLE member_chall_status COMMENT '챌린지 상태';

-- Foreign Key 설정 SQL - member_chall_status(mem_no) -> member(mem_no)
ALTER TABLE member_chall_status
    ADD CONSTRAINT FK_member_chall_status_mem_no_member_mem_no FOREIGN KEY (mem_no)
        REFERENCES member (mem_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - member_chall_status(mem_no)
-- ALTER TABLE member_chall_status
-- DROP FOREIGN KEY FK_member_chall_status_mem_no_member_mem_no;

-- Foreign Key 설정 SQL - member_chall_status(chall_no) -> challenge_board(chall_no)
ALTER TABLE member_chall_status
    ADD CONSTRAINT FK_member_chall_status_chall_no_challenge_board_chall_no FOREIGN KEY (chall_no)
        REFERENCES challenge_board (chall_no) ON DELETE RESTRICT ON UPDATE RESTRICT;

-- Foreign Key 삭제 SQL - member_chall_status(chall_no)
-- ALTER TABLE member_chall_status
-- DROP FOREIGN KEY FK_member_chall_status_chall_no_challenge_board_chall_no;