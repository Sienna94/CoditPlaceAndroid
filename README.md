# :computer::coffee: ​Codit.Place

> 코딩하기 좋은 카페 검색 어플리케이션(안드로이드) 
>
> : 노마드림 웹사이트를 모델로 모바일 어플리케이션 제작.



## 1. 제작 기간

- 2021.01.08 - 2021.01.18
- 개인프로젝트



## 2. 사용기술

- Java
- AndroidStudio
- Eclipse Mar
- mybatis 
- Oracle11g
- ApacheTomcat 9.0



## 3. ERD 설계

(추가 예정)



## 4. 핵심 기능

**4.1. 지역별 카페 검색**

- 검색어와 일치하는 카페 리스트 출력

  ![codit1-1](https://user-images.githubusercontent.com/69448123/110580409-fa14f400-81ab-11eb-873e-2069233d909e.png)

<br>

**4.2. 상세페이지**

- 카페 상세 설명, 주소, 지도

- 별점과 댓글, 북마크(좋아요 기능)

![codit1-2](https://user-images.githubusercontent.com/69448123/110580442-08fba680-81ac-11eb-9a27-663bd6e4a148.png)



**4.3. etc **

- 로그인 및 회원가입, 마이페이지 

  ![codit1-3](https://user-images.githubusercontent.com/69448123/110580633-68f24d00-81ac-11eb-8aeb-e20812f4fe18.png)



## 5. 트러블 슈팅

**5.1. 프래그먼트에서 구글맵 출력**

>**프래그먼트에서 구글맵 출력이 안되는 문제 발생**
>
>- Activity의 경우 구글맵 출력시 xml에서 fragment를 사용한다. 또한 Activity에서는 GoogleMap을 import하여 사용하는데 이와 동일하게 했음.
>
>  ```java
>  //예시 xml
>  <fragment
>          android:layout_width="match_parent"
>          android:layout_height="match_parent"
>          android:id="@+id/map"
>          tools:context=".MapsActivity"
>          android:name="com.google.android.gms.maps.SupportMapFragment" />
>  ```
>
>  ```java
>  //예시 Activity
>  import com.google.android.gms.maps.GoogleMap;
>  ...
>      public class MainActivity extends AppCompatActivity
>          implements OnMapReadyCallback {
>  
>      private GoogleMap mMap;
>          ...
>  ```
>
>  
>
>**Fragment에서 MapView 사용**
>
>- xml과 Fragment를 아래와 같이 변경했다. :point_right:[코드확인](https://github.com/Sienna94/CoditPlaceAndroid/blob/4d60f4381cbfcaf0200c91439031d55b33866449/app/src/main/java/com/example/coditplace2/SearchDetailFrag.java#L98)
>
>  - GoogleMap을 MapView로 변환한 것 외의 다른 것들 (ex_ LocationSource, OnMapReadyCallback, LatLng, Marker, MarkerOptions)는 모두 동일하게 사용할 수 있다.
>
>  ```java
>  <com.google.android.gms.maps.MapView
>           android:layout_centerHorizontal="true"
>           android:layout_below="@+id/tv_paddress"
>           android:layout_margin="10dp"
>           android:id="@+id/map"
>           android:layout_width="match_parent"
>           android:layout_height="match_parent"
>                      android:name="com.google.android.gms.maps.MapFragment" />
>  ```
>
>  ```java
>  import com.google.android.gms.maps.MapView;
>  ...
>      public class SearchDetailFrag extends BaseFrag implements View.OnClickListener, OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
>      //googleMap
>      private MapView mapView = null;
>          ...
>                  
>      @Nullable
>      @Override
>      public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
>          View layout = inflater.inflate(R.layout.frag_searchdetail, null);
>  		...
>              // map
>          mapView = (MapView)layout.findViewById(R.id.map);
>  ```





## 6. 회고/ 느낀 점

첫 개인프로젝트인 점, 하고 싶었던 주제였던 점 때문에 더 애착이 간다. 획기적인 기능보다는  이전에 다룬 기능들을 복습하는 차원에서 진행했다. 

또한 클론프로젝트임에도 웹사이트를 모바일 어플리케이션으로 바꿔야했다. 출시된 어플리케이션이 없어서 크롬으로 모바일 버전을 띄워놓고 참고하면서 만들었는데 **상상력을 최대한 발휘하려고 노력했다. **

![다운로드](https://user-images.githubusercontent.com/69448123/110580968-03eb2700-81ad-11eb-89fb-bc1cca343440.jpg)



그!래!도!

- **프래그먼트를 적극 활용해 봄.** :point_right:[확인](https://github.com/Sienna94/CoditPlaceAndroid/tree/master/app/src/main/java/com/example/coditplace2) 

- **상속을 통해 중복되는 코드 제거 위해 노력함.**  BaseActivity와 BaseFrag 

- volley 통신을 연습할 수 있는 기회, JSON을 원없이 다뤄봄

  

6.1. 시간 관리 및 설계 중요성

> 이후 바로 팀프로젝트를 들어가야해서 일주일 내에 완성해야 했다. (당연히 일주일을 넘긴 건 안비밀) 버릴 건 버려가면서 핵심 기능에 집중했다면 더  도움이 되는 프로젝트가 되지 않았을까. 
>
> ![timetable](https://user-images.githubusercontent.com/69448123/110573494-b4056380-819e-11eb-8104-e702ec02c3f0.png)<br>
>
> 설계 없이 만들면서 붙여가는 식이었기 때문에 일관되거나 깔끔하지 못했던 것이 아쉽다. 폴더 정리 없이 계속해서 class가 늘어나서 작업시 불편했다.



6.2. 파이어베이스 활용

> 파이어베이스를 적극적으로 활용했다면 좋았을 것. 사용자에게 새로운 장소 등록시 알림이 간다거나, 등록한 후기글에 대해 코멘트가 달렸을 때 알림을 준다거나... 그냥 광고 하나 달아봤다.



6.3. 회원가입시 확인 이메일 보내기, 토큰 활용, 여전히 로컬 서버를 사용했다는 것, 글 등록 기능을 완성하지 못한 것...
