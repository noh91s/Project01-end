<p align="center">
    <img src="img_read/logo.png">
</p>

# 천천히가조
## 01. 쇼핑몰 &amp; 관리자 모드 기반 챗봇 구현 프로젝트
[프로젝트 소개 PPT(영상포함)](https://docs.google.com/presentation/d/1RTHzkILAz1q6PeHVgiSOzxocklXHgZ5w/edit?usp=sharing&ouid=101047900780001156857&rtpof=true&sd=true)<br>
[프로젝트 DB ERD](https://drive.google.com/file/d/1LeCbd0GKajQSz7WGXhH6tvGssVI61H9m/view?usp=sharing)<br>

## 프로젝트 기간 
- 2023.08.16 ~ 2023.09.25

## 프로젝트 소개
- 레고사이트를  참고하여 만든 판매 쇼핑몰입니다.
- Spring MVC 패턴으로 개발하였습니다.
- 기본적인 쇼핑몰 기능 외에 관리자 페이지와 챗봇 기능도 추가하였습니다.
- Github Actions와 AWS EC2를 기반으로 CI/CD(지속통합/지속배포) 환경을 구축하였습니다.

## 개발 환경
- `Language` : Java 11, HTML5, CSS3, JavaScript
- `IDE` : IntelliJ IDEA, Visual Studio Code
- `Framework` : Springboot
- `Database` : MySQL
- `Template Engine` : Thymeleaf
- `ORM` : JPA <br>

## 팀 구성 및 역할
### 팀장 : 노승준 - 댓글상세, 장바구니, 프로젝트 관리, CI/CD <br>
#### `Controller` : ReplyController / CartController <br> `Templates` : Reply / Cart <br>

<details>
<summary>데이터 모델링 및 Entity, Dto 상세보기</summary>
<br>
  <p align="center"><img src="img_read/1ckDB_reply.png"></p> 
<br>
  <p align="center">회원(user)와 게시글(board)을 중심으로 전체적인 테이블과 연관관계를 설정하였고,</p>
  <p align="center">댓글(reply)은 회원(user) 및 게시글(board)과의 연관관계를 설정</p>
<br>

<br>
  <p align="center"><img src="img_read/1ckDB_cart.png"></p> 
<br>
  <p align="center">회원(user)와 장바구니(cart)를 중심으로 전체적인 테이블과 연관관계를 설정하였고,</p>
  <p align="center">장바구니 품목(wishlist)는 상품(item) 및 장바구니(cart)와의 연관관계를 설정</p>
<br>

</details>
<br>

<details>
<summary>검색 기능 상세보기</summary>
<br>
  <p align="center"><img src="img_read/sh04.png"></p> 
<br>
  <p align="center">header에 위치한 통합 검색 기능</p>
<br>
<br>
  <p align="center"><img src="img_read/sh05.png"></p> 
<br>
<br>
  <p align="center">게시판 목록에 위치한 게시글 상세 검색 기능</p>
<br>

</details>
<br>

<details>
<br>
<summary>댓글(reply) 상세보기</summary>
<br>
  <p align="center"><img src="img_read/sh01.png"></p> 
<br>
<br>
  <p align="center">댓글 작성</p>
<br>
<br>
  <p align="center"><img src="img_read/sh03.png"></p> 
<br>
<br>
  <p align="center">댓글 수정</p>
<br>

</details>
<br>

<details>
<br>
<summary>장바구니(cart) 상세보기</summary>
<br>
  <p align="center"><img src="img_read/sh02.png"></p> 
<br>

</details>
<br>

<details>
<summary> CI/CD 구현 상세보기</summary>
<br>
  <p align="center"><img src="img_read/sh06.png"></p> 
<br>
</details>
<br>

#### 팀장 : 김** - 게시판상세 <br>
#### 팀원 : 김** - 회원상세, Naver API<br>
#### 팀원 : 박** - 회원상세, 프론트엔드 <br>
#### 팀원 : 박** - 상품상세, 관리자페이지, 챗봇 <br>