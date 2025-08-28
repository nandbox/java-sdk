
# nandbox Bot Java Library

[![MIT License](http://img.shields.io/badge/license-MIT-blue.svg?style=flat)](https://github.com/nandbox/nandboxbotsapi/blob/master/LICENSE)

A simple to use library to create nandbox Bots in Java

## Usage

Just import  the library to your project with one of the following options:

  1. Using Maven Central Repository:

```xml
    <dependency>
         <groupId>com.nandbox.bots.api</groupId>
		 <artifactId>bots-api</artifactId>
		 <version>1.5.4</version>
    </dependency>
```
## Build your first bot
After you download the library, you can easily build your bot by following the below steps:

**1.Setup your configuration file** once you get your bot configuration data from nandbox app , copy it to a proprieties file and name it  `config.properties` then add it to the home directory of your project.

If you don't know how to get bot configuration data and token from nandbox 

- Open your bot in nandbox app then open  top right menu and click to `Get token` .This process explained in this [video](https://www.youtube.com/watch?v=FXb6tjOuxSc&feature=youtu.be).



You will get data like this:
``` 
token:90091783784280234234WBBPmJAnSD5ILIkc6N6QjY3ZzeY
url:wss://<SERVER>:<PORT>/nandbox/api/  
download:https://<SERVER>:<PORT>/nandbox/download/  
upload:https://<SERVER>:<PORT>/nandbox/upload/
```
save your token you will use it later and add other data to  `config.properties` in the following format :
``` 
URI=wss://<SERVER>:<PORT>/nandbox/api/
DownloadServer=https://<SERVER>:<PORT>/nandbox/download/  
UploadServer=https://<SERVER>:<PORT>/nandbox/upload/
```

**2.Create your main class :**  After you created your Main class and added your main function please do the following :

1- Get instance from NandboxClient.

2- Call `connect` function and pass the following parameters to it :

 a) token that you get in the prevouse step as String .
 
 b) New Nandbox.Callback anonyomus instance and create a local Nandbox.Api variable and set it to null.
 
 c) Implement the Nandbox.Callback() onConnect function and set Nandbox.Api local varable created in step (b) to onConnect function input parameter api .
 d) You can implment other nesseeary functions.
 
 You can simply copy the below code snippet and replace token with your bot token and implment the nesessery functions .

```java
public class MyFirstBot {

 public static void main(String[] args) throws Exception {
  NandboxClient client = NandboxClient.get();
  client.connect(TOKEN, new Nandbox.Callback() {
   Nandbox.Api api = null;

   @Override
   public void onConnect(Api api) {
    System.out.println("Authenticated");
    this.api = api;
   }

   @Override
   public void onReceive(IncomingMessage incomingMsg) {
    // Handle normal incoming messages
   }

   @Override
   public void onReceive(JSONObject obj) {
    // Handle raw JSON messages (unmapped by SDK)
   }

   @Override
   public void onClose() {
    // Called when WebSocket closes
   }

   @Override
   public void onError() {
    // Handle WebSocket errors
   }

   // === Menu Callbacks ===
   @Override
   public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {}
   @Override
   public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback) {}
   @Override
   public void onMenuCallBack(MenuCallback menuCallback) {}

   // === Message Acknowledgement ===
   @Override
   public void onMessagAckCallback(MessageAck msgAck) {}

   // === User Events ===
   @Override
   public void onUserJoinedBot(User user) {}
   @Override
   public void userStartedBot(User user) {}
   @Override
   public void userStoppedBot(User user) {}
   @Override
   public void userLeftBot(User user) {}
   @Override
   public void onUserDetails(User user, String appId) {}
   @Override
   public void onMyProfile(User user) {}

   // === Chat Events ===
   @Override
   public void onChatMember(ChatMember chatMember) {}
   @Override
   public void onChatAdministrators(ChatAdministrators chatAdministrators) {}
   @Override
   public void onChatDetails(Chat chat, String appId) {}
   @Override
   public void onCreateChat(Chat chat) {}

   // === Product & Collection Events ===
   @Override
   public void onProductDetail(ProductItemResponse productItem) {}
   @Override
   public void onCollectionProduct(GetProductCollectionResponse collectionProduct) {}
   @Override
   public void listCollectionItemResponse(ListCollectionItemResponse collections) {}

   // === Search & Workflow ===
   @Override
   public void onInlineSearh(InlineSearch inlineSearch) {}
   @Override
   public void onWorkflowDetails(WorkflowDetails workflowDetails) {}

   // === Lists & Patterns ===
   @Override
   public void onBlackListPattern(Pattern pattern) {}
   @Override
   public void onWhiteListPattern(Pattern pattern) {}
   @Override
   public void onBlackList(BlackList blackList) {}
   @Override
   public void onDeleteBlackList(List_ak blackList) {}
   @Override
   public void onWhiteList(WhiteList whiteList) {}
   @Override
   public void onDeleteWhiteList(List_ak whiteList) {}

   // === URL & Scheduled Messages ===
   @Override
   public void permanentUrl(PermanentUrl permenantUrl) {}
   @Override
   public void onScheduleMessage(IncomingMessage incomingScheduleMsg) {}
  });
 }
}

```
`client.connect(token, new Nandbox.Callback()`  : this method to connect to server please add your bot token  instead of `token`

**3.Creating Simple Echo Bot** : below is a sample code that creating a simple echo bot that echos everything sent to it.
```java
public class EchoTextMessage {

 public static final String TOKEN = "<PUT your token here>";

 public static void main(String[] args) throws Exception {
  NandboxClient client = NandboxClient.get();
  client.connect(TOKEN, new Nandbox.Callback() {
   Nandbox.Api api = null;

   @Override
   public void onConnect(Api api) {
    System.out.println("Authenticated");
    this.api = api;
   }

   @Override
   public void onReceive(IncomingMessage incomingMsg) {
    if (incomingMsg.isTextMsg()) {
     String chatId = incomingMsg.getChat().getId();
     String text = incomingMsg.getText();
     api.sendText(chatId, text); // Echo back
    }
   }

   // You can override other callbacks as needed
  });
 }
}
```
--
## License 
MIT License

Copyright (c) 2022 nandbox

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
