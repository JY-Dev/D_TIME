# D_TIME
## 스케줄 & 다이어리 관리 앱

## 제작기간 : 2020.09.07 ~ 2020.10.13 (틈틈이 개발)

## 기술스택
 - Kotlin 
 - Room 
 - LiveData 
 - Koin
 
## PLAY STORE - https://play.google.com/store/apps/details?id=com.jaeyoung.d_time

## 기능

### [HOME]
- 해야 할 일 리스트 (TODO LIST✔) 작성
- 현재 진행중인 일정 및 다음 일정 확인

### [DIARY]
- 일기 검색 & 작성
- 날짜 별 일기 확인 & 관리
- 북마크 기능 (일기를 북마크 폴더에 넣어 더 효율적으로 일기를 작성하실 수 있습니다.)
- 일기 검색 - 타이틀 검색
- 일기 작성 - 이미지 첨부 & 날씨🌤 및 기분🤯 선택!

### [Time Table]
- 날짜별 24시간 일정 관리
- 시간 별 일정 추가 & 확인
- 효율적인 시간 관리 도움

## 기본적인 구조
- Viemodel Class 에서 Dataprocess(Model)를 통해 Room 데이터베이스에 접근 
- 등록 ,수정 ,삭제와 같은 기능을 수행
- Callback으로 Viemodel Class에 있는 LiveData를 통해 데이터 변경을 알림 
- View는 LiveData를 구독하고 있기때문에 바뀐데이터로 View를 그려줌

## Application
<div>
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853092-fef6c100-4462-11eb-89ea-fea8ce9877b9.png">
&emsp;
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853096-01f1b180-4463-11eb-8251-13aff68a7076.png">
&emsp;
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853100-03bb7500-4463-11eb-9bd5-ce926fb8a96b.png">
&emsp;
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853103-04eca200-4463-11eb-882f-ec56a41e572e.png">
&emsp;
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853104-06b66580-4463-11eb-9c0a-0b9e6c3f8153.png">
&emsp;
<img width="200" src="https://user-images.githubusercontent.com/45057493/102853106-07e79280-4463-11eb-86fb-28106025e9fa.png">
</div>
