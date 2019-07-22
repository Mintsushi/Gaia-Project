# Gaia-Project

Plant-raising tab game based on real-time weather information  

### Application   
  - Platform : android

  - Language : java(Android Studio)
  
### Server 
  - Platform : Linux
  
  - Language : Node.js

### DataBase
  - MySQL

#
##
## 1. Game System

(1) 화면을 클릭해서 포인트를 모아 원하는 식물을 구입하고 레벨을 올려주세요.

(2) 각 식물에 따라 구입/레벨업을 위해 필요한 포인트가 다릅니다.

(3) 기상청 날씨 데이터를 실시간으로 받아와 반영

  - 각 식물의 실제 데이터를 기반으로 밸런스 설정
  
  - 각 식물에 따른 날씨의 영향이 상이함.
  
  - 날씨 별 각 식물에게서 얻을 수 있는 탭 점수 증가/감소
  
  - 날씨에 따라 해당 식물에게 좋지 않은 날씨일 때 식물의 HP 감소
  
![1](https://user-images.githubusercontent.com/22411296/61605696-ab86af00-ac81-11e9-8bc1-eaf0fcb17faa.JPG)

(4) 어플리케이션은 크게 게임 내 화면(실내)와 게임 외 화면(스마트폰 홈 화면)[실외]로 나뉘어 집니다.

  - 사용자는 자신이 가지고 있는 식물을 게임 내 화면과 스마트폰 홈 화면으로 이동시킬 수 있습니다.(방법은 6. Outdoor 기능에서 설명)
  
  - 실외(스마트폰 홈 화면)에 있는 식물은 사용자가 있는 위치에서의 실제 날씨에 영향을 받습니다.
  
  만약, 지금 비가 내리고 있다면, 휴대폰 화면에서 비를 내리면 event 표시 - 각 식물들이 밸런스 정보에 맞게 날씨에 영향을 받습니다.

  - 만약 날씨가 해당 식물에게 맞지 않는 날씨일 경우, 식물의 hp 감소 -> hp가 0이 되면 해당 식물이 시들어 죽습니다.[포인트를 얻지 못하는 상태]
  
  시들어 죽은 식물은 아이템으로 부활시키거나, 새로 구입해야 합니다.
        
  - 즉, 식물을 키우는데 영향을 주는 요인은 식물에게 물 주기, 날씨입니다.
  
![9](https://user-images.githubusercontent.com/22411296/61606268-62842a00-ac84-11e9-9caa-edd602bb58ac.JPG)

## 2. Main 화면
![2](https://user-images.githubusercontent.com/22411296/61606075-885cff00-ac83-11e9-8a68-3bb31f4f14da.JPG)

## 3. Flower 화면 : 식물 관리 화면
(1) 식물을 구입하고, 레벨을 높여 꽃을 성장 시킬 수 있습니다.

(2) 꽃을 성장시킬 수록, 해당 꽃에서 얻을 수 있는 포인트가 증가합나다.

(3) 꽃이 성장할 수록 실제 꽃과 같이 이미지가 변화합니다.

(4) 각 꽃은 최대레벨까지만 성장할 수 있습니다.

![3](https://user-images.githubusercontent.com/22411296/61606163-eb4e9600-ac83-11e9-8a38-776407e61ed9.JPG)

## 4. Skill 화면 : 스킬 사용
(1) Skill을 사용하여, 빠르고 쉽게 포인트를 얻고 식물을 키울 수 있습니다.

(2) Skill에는 재사용 시간이 있고, 이는 어플리케이션 내부와 외부에서 동일하게 작용합니다.

![4](https://user-images.githubusercontent.com/22411296/61606487-8c8a1c00-ac85-11e9-974a-6593581b7c7a.JPG)
