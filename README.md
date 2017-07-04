# 서울시 무료와이파이 지도 프로젝트

## 1. 개발환경
1. 개발 툴 
    * Android Studio 2.3
2. SDK 버전
    * minSdkVersion 22
    * targetSdkVersion 25
    * compileSdkVersion 25
3. 버전관리 
    * Git

## 2. 개발 기간
    2017년 3월 27일 ~ 2017년 4월 24일
    
## 3. UI
1. 스피너 버튼을 누르면 서울시 25개의 구 이름을 보여준다. 원하는 구를 클릭한다.
    ![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/ui1.png)

1. 선택된 구의 무료 와이파이의 위치를 지도에 표시한다. 마커를 클릭하면 해당 와이파이존의 설치기관명과 장소명을 표시한다.
    ![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/ui1.png)
    
1. 상단 바 우측 버튼을 클릭하면 나의 현재 위치를 알수있다. 위치 검색을 위해 위치 접근 권한을 허용해야 한다.
    ![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/ui1.png)

1. 위치 접근 권한을 허용하고 gps을 실행한 후, 나의 현재 위치가 지도에 표시된다. 위치가 변경되더라도 실시간으로 지도에 반영되어 나타난다.
    ![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/ui1.png)



## 4. Sutructure(Xmind)
![screensh](https://github.com/HyunjuLee521/NewDiaryProject/blob/master/structure.png)

## 5. Features
1. 서울시 각 구 별로 공공 와이파이 위치 검색하기(지도에 와이파이존 위치 마커로 표시하기, 설치 기관과 장소명 띄우기) 
1. 상단 바 우측 버튼 클릭하여 나의 현재 위치 보여주기(지도에 마커로 표시하기)
1. 이동시 변경된 나의 위치 실시간으로 반영하여 보여주기

## 6. Credits
1. 레트로핏
    * square / retrofit2 : https://github.com/square/retrofit/tree/master/retrofit/src/main/java/retrofit2
    
2. 테드퍼미션
    * ParkSangGwon / TedPermission : https://github.com/ParkSangGwon/TedPermission

## 7. Api
1. 구글 지도
    * google / google maps services api : https://developers.google.com/maps/android/?hl=ko

2. 구글 위치
    * google / google maps location api : https://developers.google.com/maps/documentation/android-api/location?hl=ko
    
3. 서울시 공공와이파이 위치정보
    * 서울 열린 데이터 광장 / 서울시 공공와이파이 위치정보 : http://data.seoul.go.kr
    

## 8. License
Copyright 2017. Hyunju Lee

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.




