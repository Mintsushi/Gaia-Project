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
  
      -- 만약, 지금 비가 내리고 있다면, 휴대폰 화면에서 비를 내리면 event 표시 - 각 식물들이 밸런스 정보에 맞게 날씨에 영향을 받습니다.

  - 만약 날씨가 해당 식물에게 맞지 않는 날씨일 경우, 식물의 hp 감소 -> hp가 0이 되면 해당 식물이 시들어 죽습니다.[포인트를 얻지 못하는 상태]
  
      -- 시들어 죽은 식물은 아이템으로 부활시키거나, 새로 구입해야 합니다.
        
  - 즉, 식물을 키우는데 영향을 주는 요인은 식물에게 물 주기, 날씨입니다.
  
![9](https://user-images.githubusercontent.com/22411296/61606268-62842a00-ac84-11e9-9caa-edd602bb58ac.JPG)

## 2. Main 화면
![2](https://user-images.githubusercontent.com/22411296/61606075-885cff00-ac83-11e9-8a68-3bb31f4f14da.JPG)

## 3. Flower 화면 : 식물 관리 화면
(1) 식물을 구입하고, 레벨을 높여 꽃을 성장 시킬 수 있습니다.

(2) 꽃을 성장시킬 수록, 해당 꽃에서 얻을 수 있는 포인트가 증가합나다.

(3) 꽃이 성장할 수록 실제 꽃과 같이 이미지가 변화합니다.

(4) 각 꽃은 최대레벨까지만 성장할 수 있습니다.

(5) 앞 식물의 레벨이 일정 레벨 이상 올라야 다음 식물을 구입할 수 있습니다.

  - 예를 들어, 민들레의 레벨이 10 이상이여야 다음 꽃인 나팔꽃을 구입할 수 있고, 나팔꽃의 레벨이 15 이상이여야 다음 꽃인 장미를 구입할 수 있습니다.
  
(6) 식물의 hp는 식물 화면에서 식물의 이미지에 표시해줍니다. - 민들레 이미지를 보시면 붉은 바가 보이실 겁니다. 그것이 바로 민들레의 남은 hp(체력)입니다.
  
  - 체력은 상점에서 hp 물약으로 다시 회복시킬 수 있습니다.

![3](https://user-images.githubusercontent.com/22411296/61606163-eb4e9600-ac83-11e9-8a38-776407e61ed9.JPG)

## 4. Skill 화면 : 스킬 사용
(1) Skill을 사용하여, 빠르고 쉽게 포인트를 얻고 식물을 키울 수 있습니다.

(2) Skill에는 재사용 시간이 있고, 이는 어플리케이션 내부와 외부에서 동일하게 작용합니다.

![4](https://user-images.githubusercontent.com/22411296/61606487-8c8a1c00-ac85-11e9-974a-6593581b7c7a.JPG)

## 5. Dry Flower 화면 : 식물 박제 기능
(1) 최대 레벨까지 다 키운 꽃을 말려서 영원히 보관해보세요.

(2) 사용자의 클릭으로는 포인트를 얻을 수 없지만, 1분마다 일정 점수를 자동으로 드립니다.

![5](https://user-images.githubusercontent.com/22411296/61606695-89dbf680-ac86-11e9-9fd1-362530b6f230.JPG)

## 6. Out Door 화면 : 식물을 실외로 옮겨보세요.
(1) 바깥의 날씨가 좋을 때, 식물을 밖에 내놓으세요. 얻을 수 있는 점수가 증가합니다.

(2) 날씨가 좋지 않을 때 식물이 밖에 있다면, 식물의 hp가 점점 줄어들어 시들 수 있으니, 주의해야 합니다.

(3) ON 클릭 : 식물을 밖(휴대폰의 홈화면)으로 내보내기

(4) OFF 클릭 : 식물을 안(게임 화면)으로 가져오기

(5) 실외에 있는 식물을 드래그해서 원하는 위치에 놓을 수 있어요.

(6) 실외 화면(휴대폰 홈화면)에는 그 때의 날씨가 귀여운 이미지로 표현됩니다. -> 지금의 날씨를 쉽게 확인하고, 식물을 키우세요.

![6](https://user-images.githubusercontent.com/22411296/61606815-0a025c00-ac87-11e9-8863-ee596405ff07.JPG)

## 7. Store 화면 : 상점
(1) 상점에서는 식물을 키우는데 필요한 아이템을 구입할 수 있습니다.

    - 자동 클릭 : 일정 시간동안 자동으로 클릭
    
    - 체력 물약 : 식믈의 hp가 줄어들었을 때, hp를 채워줍니다.
    
    - 부활 물약 : 식물의 hp가 0이 되어 시들었을 때, 다시 식물을 부활시켜 줍니다.
    
    - 물 : 식물에게 물을 줄 때 필요한 아이템.
    
![7](https://user-images.githubusercontent.com/22411296/61606887-55b50580-ac87-11e9-9d3e-a37f2afc18a7.JPG)

## 8. 업적 & Out door skill 화면
(1) 업적 : 업적을 달성해 포인트를 획득할 수 있습니다.

(2) Outdoor Skill : 게임 외부에서도 게임 내에서와 같이 스킬을 사용할 수 있습니다.

    - 게임 내부나 외부에서 스킬을 사용했다면 스킬 재사용 시간은 둘 모두에게 동일하게 적용됩니다.
    
    - 사용자가 영상을 시청하거나 다른 일을 할 때 식물 이미지가 방해되지 않도록, 식물을 사라지게 할 수 있습니다.
    
 ![8](https://user-images.githubusercontent.com/22411296/61607022-e390f080-ac87-11e9-9346-73061fd0a891.JPG)
 
 ## 기능 정리
 ![11](https://user-images.githubusercontent.com/22411296/61607086-19ce7000-ac88-11e9-8413-5b027fb81bbb.JPG)
 
 ## 데모 영상
[![데모](https://user-images.githubusercontent.com/22411296/61608020-281e8b00-ac8c-11e9-9632-058c982bc512.JPG)](https://www.youtube.com/watch?v=OxtukQNEpLg&t=1s)
