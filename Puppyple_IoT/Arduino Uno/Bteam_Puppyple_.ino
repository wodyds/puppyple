#include <SoftwareSerial.h>
#include <ArduinoJson.h>

#include "DFRobotDFPlayerMini.h"

void printResponse();
void connectWifi();

//20211214 배식시 소리 기능 추가 by JP
SoftwareSerial mp3_serial(12,13);
DFRobotDFPlayerMini player;

// 3.3V 연결
SoftwareSerial ESP8266(10, 11);  //RX:11번핀  TX:10번핀
String SSID = "hanul201";
String PASSWORD = "hanul201";

// 스텝모터
int ENA=2;
int IN1=3;
int IN2=4;
int ENB=5;
int IN3=6;
int IN4=7;

int interval = 5;
int lastMillis = 0;

int firstIndex = 0; // 받은 데이터에서 "[" 에서 시작 인덱스 얻음 
int lastIndex = 0;  // 받은 데이터에서 "]" 에서 끝 인덱스 얻음 

//20211214 LED 기능 추가 by JP
int led = 8;

void setup()
{
  Serial.begin(9600);  
  
  mp3_serial.begin(9600);
  if(player.begin(mp3_serial)){
    Serial.println("OK");
  }else{
    Serial.println("FAIL");
  }

  ESP8266.begin(9600);
  delay(500);
  
  pinMode(ENA,OUTPUT);
  pinMode(ENB,OUTPUT);
  pinMode(IN1,OUTPUT);
  pinMode(IN2,OUTPUT);
  pinMode(IN3,OUTPUT);
  pinMode(IN4,OUTPUT);
  digitalWrite(ENA,HIGH);
  digitalWrite(ENB,HIGH);

  pinMode(led,OUTPUT);

  lastMillis = millis()/1000;

  connectWifi();  

  digitalWrite(led, HIGH);
  delay(3000);
  digitalWrite(led, LOW);
  player.volume(20);
  player.play(1);
  delay(3000);
}

void loop()
{  
    if (Serial.available()) { 
      
    } // if (Serial.available()) 
     
    if (ESP8266.available()) { 
      Serial.println("ESP8266.available()!!!"); 

      String result = "";

      //Serial.println("수신 정보 << " + ESP8266.readStringUntil('?') + " >> ");       
      result = ESP8266.readStringUntil('?');
      Serial.println("수신 정보 << " + result + " >> "); 

      firstIndex = result.indexOf('[');
      lastIndex = result.indexOf(']');
     
      String data = result.substring(firstIndex, lastIndex + 1); 
      delay(200);
      Serial.println("Json 정보 << "+data+" >> "); 
      delay(200);
      
      char result_char[data.length()];
      strcpy(result_char, data.c_str());     
     
      Serial.println(result_char); 
        
      StaticJsonDocument<500> doc; 
      DeserializationError error = deserializeJson(doc, result_char);  
      if (error) {
        Serial.print(F("deserializeJson() failed: "));
        Serial.println(error.c_str());
        return;
      }

      JsonObject css0 = doc[0];
        
      const char* value = css0["value"];
              
      delay(100);
      Serial.println(value);   

      char* arduValue = "1";
  
      if(strcmp(value, arduValue) == 0){
        
        for(int i=0; i<20; i++){

          digitalWrite(led, HIGH);
           
          digitalWrite(IN1,LOW);
          digitalWrite(IN2,HIGH);
          digitalWrite(IN3,HIGH);
          digitalWrite(IN4,LOW);
          delay(40);
        
          digitalWrite(IN1,LOW);
          digitalWrite(IN2,HIGH);
          digitalWrite(IN3,LOW);
          digitalWrite(IN4,HIGH);
          delay(40);
        
          digitalWrite(IN1,HIGH);
          digitalWrite(IN2,LOW);
          digitalWrite(IN3,LOW);
          digitalWrite(IN4,HIGH);
          delay(40);
        
          digitalWrite(IN1,HIGH);
          digitalWrite(IN2,LOW);
          digitalWrite(IN3,HIGH);
          digitalWrite(IN4,LOW);
          delay(40);
        }
    
        lastMillis = millis()/1000;
        digitalWrite(led, LOW);


        // 밥 줄때 소리나기  
        Serial.println("mp3 들어움");
        player.volume(20);
        player.play(1);
        delay(3000);
        player.volume(30);
        player.play(2);
        delay(5000);
        
      }      

      return 0;
      
    } // if (ESP8266.available())

    
  
}


void printResponse() {
  while (ESP8266.available()) {
    Serial.println(ESP8266.readStringUntil('\n')); 
  }
}

// esp8266 at command 명령어 찾아보기
void connectWifi(){

  while(1){
    Serial.println("Wifi connecting ...");
//    String cmd = "AT+RST";  // 초기화     
//    ESP8266.println(cmd);
//    delay(1000);
//    printResponse(); 
    
    String cmd = "AT+CWMODE=3";  // Server로 접속     
    ESP8266.println(cmd);
    delay(1000);
    printResponse(); 
    
    cmd ="AT+CWJAP=\""+SSID+"\",\""+PASSWORD+"\""; // WIFI 접속    
    ESP8266.println(cmd);   
    delay(1000);
    printResponse();
    delay(3000);
    
    if(ESP8266.find("OK")){
      Serial.println("Wifi connected!!!"); 
       
      cmd = "AT+CIFSR";   // IP 주소 알아내기
      ESP8266.println(cmd);
      delay(1500); 
      printResponse(); 
      delay(1500);

      ESP8266.println("AT+CIPMUX=1"); // 다중접속
      delay(1500);
      printResponse();

      // start server on port 80
      Serial.println("Start the server ..."); 
      cmd = "AT+CIPSERVER=1,80";  // 0:close mode  1:open mode 
      ESP8266.println(cmd);
      printResponse();
      delay(1500);

       ESP8266.println("AT+CIPSTO=3"); // SET SERVER TIMEOUT
       delay(1000);
       printResponse();

      Serial.println("Waiting the server ..."); 
      
      break;
    }
  }
  
}
