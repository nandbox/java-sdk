package com.nandbox.bots.api.test;

import static com.nandbox.bots.api.util.Utils.getUniqueId;

import java.util.ArrayList;
import java.util.HashMap;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.Button;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.Menu;
import com.nandbox.bots.api.data.Photo;
import com.nandbox.bots.api.data.Result;
import com.nandbox.bots.api.data.Row;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.inmessages.ChatAdministrators;
import com.nandbox.bots.api.inmessages.ChatMember;
import com.nandbox.bots.api.inmessages.ChatMenuCallback;
import com.nandbox.bots.api.inmessages.IncomingMessage;
import com.nandbox.bots.api.inmessages.InlineMessageCallback;
import com.nandbox.bots.api.inmessages.InlineSearch;
import com.nandbox.bots.api.inmessages.MessageAck;
import com.nandbox.bots.api.inmessages.PermanentUrl;
import com.nandbox.bots.api.inmessages.IncomingMessage.MessageType;
import com.nandbox.bots.api.outmessages.SetChatMenuOutMessage;
import com.nandbox.bots.api.outmessages.InlineSearchAnswer;
import com.nandbox.bots.api.outmessages.OutMessage;
import com.nandbox.bots.api.outmessages.PhotoOutMessage;
import com.nandbox.bots.api.outmessages.RecallOutMessage;
import com.nandbox.bots.api.outmessages.TextOutMessage;
import com.nandbox.bots.api.util.MediaTransfer;
import com.nandbox.bots.api.util.Utils;
import static com.nandbox.bots.api.outmessages.OutMessage.WEB_PREVIEW_DISABLE;
import static com.nandbox.bots.api.outmessages.OutMessage.WEB_PREVIEW_INSTANCE_VIEW;
import net.minidev.json.JSONObject;

/**
 * Test Class
 * 
 * @author Hossam Mohamed
 *
 */
public class TestTextMessage {
	public static final String TOKEN = "90091784169275314:0:MPgzj802RbiMZ3RL7GHpwuDp9QxVTq";// you can put your own bot token

	private static HashMap<Long, OutMessage> outMsgsListener = new HashMap<>();

	public static void main(String[] args) throws Exception {
		NandboxClient client = NandboxClient.get();
		client.connect(TOKEN, new Nandbox.Callback() {
			Nandbox.Api api = null;

			@Override
			public void onConnect(Nandbox.Api api) {
				System.out.println("ONCONNECT");
				this.api = api;
			}

			@Override
			public void onReceive(JSONObject obj) {

			}

			@Override
			public void onClose() {
				System.out.println("ONCLOSE");
			}

			@Override
			public void onError() {
				System.out.println("ONERROR");

			}

			@Override
			public void onReceive(IncomingMessage incomingMsg) {

				System.err.println(
						"=========>> " + incomingMsg.getType() + " Message Received =========>>");
				System.out.println("incomingMsg.getMessageId() : " + incomingMsg.getMessageId());
				System.out.println("incomingMsg.getDate() : " + incomingMsg.getDate());
				System.out.println("incomingMsg.getReference() : " + incomingMsg.getReference());
				System.out.println("incomingMsg.getCaption(): " + incomingMsg.getCaption());

				if (incomingMsg.getSentTo() != null) {
					System.out.println(
							"incomingMsg.getSentTo().getId() : " + incomingMsg.getSentTo().getId());
				}
				System.out.println("================start of Chat Object ===================");
				System.out.println(
						"incomingMsg.getChat().getId() : " + incomingMsg.getChat().getId());
				System.out.println(
						"incomingMsg.getChat().getTitle() :" + incomingMsg.getChat().getTitle());
				System.out.println(
						"incomingMsg.getChat().getName() :" + incomingMsg.getChat().getName());				
				System.out.println(
						"incomingMsg.getChat().getType() :" + incomingMsg.getChat().getType());
				System.out.println("================End of Chat Object ===================");
				System.out.println("================Start of From User Object ===================");
				System.out.println(
						"incomingMsg.getFrom().getId() : " + incomingMsg.getFrom().getId());
				System.out.println(
						"incomingMsg.getFrom().getName() : " + incomingMsg.getFrom().getName());
				System.out.println("incomingMsg.getFrom().getTerminal(): "
						+ incomingMsg.getFrom().getTerminal());
				System.out.println("incomingMsg.getFrom().getVersion() : "
						+ incomingMsg.getFrom().getVersion());
				System.out.println("================End of From User Object ===================");

				// Incoming Text Message
				if (MessageType.text.toString().equals(incomingMsg.getType())) {
					handleIncomingTextMsg(incomingMsg);
				}

				// if (incomingMsg.getChat().getType().equalsIgnoreCase("Channel")
				// || incomingMsg.getChat().getType().equalsIgnoreCase("Group")) {
				//
				// Long reference = getUniqueId();
				// api.sendText(incomingMsg.getChat().getId(), "replay", reference,
				// incomingMsg.getMessageId(), null, null, null);
				// }
				//api.sendText(incomingMsg.getChat().getId(), "Hello Worldz");
				// Long reference = getUniqueId();

				// api.sendText("90090684265384780", "faar", reference, null,
				// "90089584762092204",
				// null, null);
			}

			/**
			 * Handle incoming Text messages
			 * 
			 * @param incomingMsg
			 */
			private static final String MAIN_MENU_001 = "MAIN_MENU_001";

			private void handleIncomingTextMsg(IncomingMessage incomingMsg) {
				System.out.println("incomingMsg.getEdited " + incomingMsg.getStatus());
				System.out.println("incomingMsg.getText() : " + incomingMsg.getText());
				if ("3m".equalsIgnoreCase(incomingMsg.getText())) {

					String chatId = incomingMsg.getChat().getId();

					Utils utility = new Utils();
					utility.setNavigationButton(chatId, "mainMenu", api);

					Button menuBtn1 = createButton("Main", "mainCB", 1, "Gray", "Red", null, null);
					Button menuBtn2 = createButton("Funny", "funnyCB", 1, "Gray", "Red", null,
							null);
					Button menuBtn3 = createButton("Option", "optionCB", 1, "Gray", "Red", null,
							null);

					SetChatMenuOutMessage outmsg = new SetChatMenuOutMessage();

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { menuBtn1, menuBtn2, menuBtn3 });

					Menu chatMenu = new Menu();
					String menuRef = "mainMenu";
					chatMenu.setMenuRef(menuRef);
					chatMenu.setRows(new Row[] { firstRow });

					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setMenus(new Menu[] { chatMenu });

					api.send(outmsg);

				} else if ("1b".equalsIgnoreCase(incomingMsg.getText())) {

					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText(incomingMsg.getText());

					outmsg.setEcho(1);
					String menuRef = MAIN_MENU_001;
					Button oneBtn = createButton("Send me your location", "oneBtnCB", 1,
							"#A5B8BC", "yellow", Button.BUTTON_QUERY_LOCATION, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { oneBtn });

					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);

				} 
				
				else if ("1bc".equalsIgnoreCase(incomingMsg.getText())) {

					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText("https://edition.cnn.com/");
					outmsg.setWebPagePreview(WEB_PREVIEW_INSTANCE_VIEW);

					outmsg.setEcho(1);
					String menuRef = MAIN_MENU_001;
					Button oneBtn = createButton("Visit a Milestone", "oneBtnCBInWebView", 1,
							"RED", "White", null, null);
					oneBtn.setButtonURL("https://edition.cnn.com/");

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { oneBtn });

					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);

				}
				else if ("2b".equalsIgnoreCase(incomingMsg.getText())) {
					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText(incomingMsg.getText());

					String menuRef = "MAIN_MENU_001";
					Button btnOne = createButton("Please Send me your location now ", "RequestLocation",
							1, "RED", "White", Button.BUTTON_QUERY_LOCATION, null);
					Button btnTwo = createButton("Send me your Contact", "RequestContact", 2,
							"RED", "White", Button.BUTTON_QUERY_CONTACT, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { btnOne, btnTwo });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				}
				
				else if ("2bc".equalsIgnoreCase(incomingMsg.getText())) {
					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText("m.youm7.com");
					outmsg.setWebPagePreview(WEB_PREVIEW_INSTANCE_VIEW);

					String menuRef = "MAIN_MENU_001";
					Button btnOne = createButton("Send me your location", "RequestLocation",
							1, "RED", "White", Button.BUTTON_QUERY_LOCATION, null);
					Button btnTwo = createButton("Send me your Contact", "RequestContact", 2,
							"RED", "White", Button.BUTTON_QUERY_CONTACT, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { btnOne, btnTwo });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				}

				else if ("3b".equals(incomingMsg.getText())) {
					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText(incomingMsg.getText());

					outmsg.setEcho(1);
					String menuRef = "MAIN_MENU_001";
					Button btn1 = createButton("Naruto", "NarutoCB", 1, "#A5B8BC", "white", null,
							null);
					Button btn2 = createButton("Sasuke", "SasukeCB", 2, "#A5B8BC", "white", null,
							null);
					Button btn3 = createButton("Sakura", "SakuraCB", 2, "#A5B8BC", "white", null,
							null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { btn1, btn2, btn3 });

					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);

				} else if ("CHPOST".equalsIgnoreCase(incomingMsg.getText())) {
					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getFrom().getId()); // HESNBERG CHANNEL
					outmsg.setReference(reference);
					outmsg.setText("hello ");

					outmsg.setEcho(1);
					String menuRef = "Question1";
					Button aBtn = createButton("A", "A", 1, "#A5B8BC", "white", null, "AnswerA");
					Button bBtn = createButton("B", "B", 2, "#A5B8BC", "white", null, null);
					Button cBtn = createButton("C", "C", 3, "#A5B8BC", "white", null, null);
					Button dBtn = createButton("D", "D", 4, "#A5B8BC", "white", null, null);

					ArrayList<Menu> questionMenus = createOtherMenus();
					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { aBtn, bBtn });
					Row secondRow = new Row();
					secondRow.setRowOrder(2);
					secondRow.setButtons(new Button[] { cBtn, dBtn });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow, secondRow });
					questionMenus.add(inlineMenu);
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(questionMenus.toArray(new Menu[questionMenus.size()]));
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				} else if ("edit caption".equalsIgnoreCase(incomingMsg.getText())) {

					PhotoOutMessage outmsg = new PhotoOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setCaption("Old Caption");
					String uploadedPhotoId = MediaTransfer.uploadFile(TOKEN,
							"./upload/welcome.jpg");
					outmsg.setPhoto(uploadedPhotoId);
					String menuRef = "MAIN_MENU_001";
					Button editCaptionBtn = createButton("Edit Caption", "editCaptionCB", 1,
							"#A5B8BC", "white", null, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { editCaptionBtn, });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);

				} else if ("edit Text chat".equalsIgnoreCase(incomingMsg.getText())) {

					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText("Old Text");
					String menuRef = "MAIN_MENU_001";
					Button editCaptionBtn = createButton("Edit Text", "editTextChatCB", 1,
							"#A5B8BC", "white", null, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { editCaptionBtn, });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				} else if ("edit Text".equalsIgnoreCase(incomingMsg.getText())) {

					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText("Old Text");
					String menuRef = "MAIN_MENU_001";
					Button editCaptionBtn = createButton("Edit Text", "editTextCB", 1, "#A5B8BC",
							"white", null, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { editCaptionBtn, });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				} else if ("edit caption chat".equalsIgnoreCase(incomingMsg.getText())) {

					PhotoOutMessage outmsg = new PhotoOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setCaption("Old Caption");
					String uploadedPhotoId = MediaTransfer.uploadFile(TOKEN,
							"./upload/welcome.jpg");
					outmsg.setPhoto(uploadedPhotoId);
					String menuRef = "MAIN_MENU_001";
					Button editCaptionBtn = createButton("Edit Caption", "editCaptionChatCB", 1,
							"#A5B8BC", "white", null, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { editCaptionBtn, });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				}

				else if ("edit Text chat both".equalsIgnoreCase(incomingMsg.getText())) {

					TextOutMessage outmsg = new TextOutMessage();
					Long reference = getUniqueId();
					outmsg.setChatId(incomingMsg.getChat().getId());
					outmsg.setReference(reference);
					outmsg.setText("Old Text");
					String menuRef = "MAIN_MENU_001";
					Button editCaptionBtn = createButton("Edit Text", "editTextChatCBboth", 1,
							"#A5B8BC", "white", null, null);

					Row firstRow = new Row();
					firstRow.setRowOrder(1);
					firstRow.setButtons(new Button[] { editCaptionBtn, });
					Menu inlineMenu = new Menu();
					inlineMenu.setMenuRef(menuRef);
					inlineMenu.setRows(new Row[] { firstRow });
					outmsg.setMenuRef(menuRef);
					outmsg.setInlineMenu(new Menu[] { inlineMenu });
					outMsgsListener.put(reference, outmsg);
					api.send(outmsg);
				} else if ("i1".equalsIgnoreCase(incomingMsg.getText())) {
					InlineSearchAnswer inlineSearchAnswer = new InlineSearchAnswer();

					inlineSearchAnswer.setChatId(incomingMsg.getChat().getId());
					inlineSearchAnswer.setToUserId(incomingMsg.getFrom().getId());
					inlineSearchAnswer.setSearchId(1);

					Result results2 = new Result();
					results2.setId(
							"d2ba4e79f2e240d145e8be48f1ef98ece2f283193bce80f1b7ddbd0e8daae23a.gif");

					results2.setCaption("test GIF caption");
					results2.setDescription("Test GIF desc");
					results2.setTitle("Test GIF title");
					ArrayList<Result> results = new ArrayList<Result>();
					results.add(results2);
					inlineSearchAnswer.setResults(results);

					api.send(inlineSearchAnswer);
				} else if ("i3".equalsIgnoreCase(incomingMsg.getText())) {

					InlineSearchAnswer inlineSearchAnswer = new InlineSearchAnswer();

					inlineSearchAnswer.setChatId(incomingMsg.getChat().getId());
					inlineSearchAnswer.setToUserId(incomingMsg.getFrom().getId());
					inlineSearchAnswer.setSearchId(1);

					Result results = new Result();
					results.setId(
							"8b6229eefde75174b6cb5474b38e7f2f55a280a17ccc1f18a3ed6f5416890070.mp4");
					results.setCaption("test Video caption");
					results.setDescription("Test Video desc");
					results.setTitle("Test Video title");

					Result results2 = new Result();
					results2.setId(
							"d2ba4e79f2e240d145e8be48f1ef98ece2f283193bce80f1b7ddbd0e8daae23a.gif");

					results2.setCaption("test GIF caption");
					results2.setDescription("Test GIF desc");
					results2.setTitle("Test GIF title");

					Result results3 = new Result();
					results3.setId(
							"4bdb5b65838706092cff9de33694641aa0b7a02899b0884d07df2f58374bf40d.jpg");
					results3.setCaption("test Photo caption");
					results3.setDescription("Test Photo desc");
					results3.setTitle("Test Photo title");
					ArrayList<Result> multiResults = new ArrayList<Result>();
					multiResults.add(results3);
					multiResults.add(results2);
					multiResults.add(results);
					inlineSearchAnswer.setResults(multiResults);

					api.send(inlineSearchAnswer);

				} else if ("GCA".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90090684275605648";
					api.getChatAdministrators(chatId);
				} else if ("GU".equalsIgnoreCase(incomingMsg.getText())) {
					String userId = "90089584980037358";
					api.getUser(userId);

				} else if ("GC".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90089584980037358";
					api.getChat(chatId);
				}

				else if ("GCM".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90089584806788930";
					String userId = "90090684275605648";
					api.getChatMember(chatId, userId);

				} else if ("BCM".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90089584806788930";
					String userId = "90090684275605648";
					api.banChatMember(chatId, userId);

				} else if ("UCM".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90089584806788930";
					String userId = "90090684275605648";
					api.unbanChatMember(chatId, userId);

				} else if ("RCM".equalsIgnoreCase(incomingMsg.getText())) {
					String chatId = "90089584806788930";
					String userId = "90090684275605648";
					api.removeChatMember(chatId, userId);

				} else if ("SC".equalsIgnoreCase(incomingMsg.getText())) {
					Chat chat = new Chat();
					chat.setTitle("NEW TITLE");
					chat.setId("90090684268836495");
					api.setChat(chat);

				}

				else if ("RC".equalsIgnoreCase(incomingMsg.getText())) {

					RecallOutMessage recallOutMessage = new RecallOutMessage();
					recallOutMessage.setChatId("90090684265384780");
					recallOutMessage.setMessageId("d1_CIRKHD6C12617260");
					recallOutMessage.setReference(6915L);
					recallOutMessage.setFromUserId("90089584801498185");
					api.send(recallOutMessage);

				} else if ("GMP".equalsIgnoreCase(incomingMsg.getText())) {

					api.getMyProfiles();

				} else if ("SMP".equalsIgnoreCase(incomingMsg.getText())) {
					User user = new User();
					user.setProfile("other");
					user.setStatus("I am set My profile Bot 2 ");
					Photo photo = new Photo();
					photo.setId(
							"e801b7277dbd921376f26b13aeadf0ee4b49950a66641f2761863a823e035845.jpg");

					api.setMyProifle(user);

				}
				else if ("linkPreview".equalsIgnoreCase(incomingMsg.getText())) {
					try {
						Thread.sleep(5000L);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					api.sendText(incomingMsg.getChat().getId(), "Link without prview http://www.nandbox.com",getUniqueId(),null,null,WEB_PREVIEW_DISABLE,true,null,null);
					api.sendText(incomingMsg.getChat().getId(), "Link with prview http://www.nandbox.com",getUniqueId(),null,null,null,true,null,null);					
				}
				else if ("getChatAdmins".equalsIgnoreCase(incomingMsg.getText()))
						{
							api.getChatAdministrators(incomingMsg.getChat().getId());
						}
				else if ("getMyProfile".equalsIgnoreCase(incomingMsg.getText()))
				{
					api.getMyProfiles();
				}
				
				
			}
			

			private ArrayList<Menu> createOtherMenus() {
				ArrayList<Menu> otherMenus = new ArrayList<>();
				Menu answerA = new Menu();
				answerA.setMenuRef("AnswerA");
				Row aAnswerRow = new Row();
				Row backRow = new Row();
				Button youAnsweredABtn = createButton("You answered A", "AnsweredAMessage", 1,
						"#A5B8BC", "white", null, null);
				Button backBtn = createButton("Back", "AnsweredAMessage", 1, "#A5B8BC", "white",
						null, "Question1");
				aAnswerRow.setButtons(new Button[] { youAnsweredABtn });
				backRow.setButtons(new Button[] { backBtn });
				answerA.setRows(new Row[] { aAnswerRow, backRow });
				otherMenus.add(answerA);
				return otherMenus;
			}

			private Button createButton(String label, String callback, int order, String bgColor,
					String txtColor, String buttonQuery, String nextMenuRef) {
				Button btn = new Button();
				btn.setButtonLabel(label);
				btn.setButtonOrder(order);
				btn.setButtonCallBack(callback);
				btn.setButtonBgColor(bgColor);
				btn.setButtonTextColor(txtColor);
				btn.setButtonQuery(buttonQuery);
				btn.setNextMenu(nextMenuRef);
				return btn;
			}

			@Override
			public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback) {

				String msgId = inlineMsgCallback.getMessageId();
				String chatId = inlineMsgCallback.getChat().getId();
				String userId = inlineMsgCallback.getFrom().getId();

				if (inlineMsgCallback.getButtonCallback().equals("NarutoCB")) {
					api.sendText(chatId, "Rasengan !!!");
				}
				if (inlineMsgCallback.getButtonCallback().equals("SasukeCB")) {
					api.sendText(chatId, "Chidori z-z-z ");
				}
				if (inlineMsgCallback.getButtonCallback().equals("SakuraCB")) {
					api.sendText(chatId, "Shannaro (*_*)");
				}

				if (inlineMsgCallback.getButtonCallback().equals("editCaptionCB")) {
					api.updateMediaCaption(msgId, "My New Caption ", userId);
				}

				if (inlineMsgCallback.getButtonCallback().equals("editTextCB")) {
					api.updateTextMsg(msgId, "My New Text ", userId);
				}

				if (inlineMsgCallback.getButtonCallback().equals("editTextChatCB")) {
					api.updateChatMsg(msgId, "My New Text in group ", chatId);
				}

				if (inlineMsgCallback.getButtonCallback().equals("editCaptionChatCB")) {
					api.updateChatMsg(msgId, "My New Caption in group ", chatId);
				}

				if (inlineMsgCallback.getButtonCallback().equals("editTextChatCBboth")) {
					api.updateMessage(msgId, "my new message", null, userId, chatId);
				}

			}

			@Override
			public void onMessagAckCallback(MessageAck msgAck) {
				String reference = msgAck.getReference();

				OutMessage removedOutMsg = outMsgsListener.remove(Long.valueOf(reference));
				System.out.println("***** Ack for Message with Reference : " + reference);
				if (removedOutMsg != null) {
					System.out.println("***** Removed Out Message from Resource Listener : "
							+ removedOutMsg.toJsonObject());
				}

			}

			@Override
			public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {

				System.out.println("ChatMenuCB : " + chatMenuCallback.toJsonObject());

				String chatId = chatMenuCallback.getChat().getId();

				if (chatMenuCallback.getButtonCallback().equals("optionCB")) {
					api.sendText(chatId, "Coming soon");
				}

				if (chatMenuCallback.getButtonCallback().equals("mainCB")) {
					api.sendText(chatId, "Main menu");
				}

				if (chatMenuCallback.getButtonCallback().equals("funnyCB")) {
					api.sendText(chatId,
							"Why are frogs always so happy? They eat what ever bugs them");
				}

			}

			@Override
			public void onChatMember(ChatMember chatMember) {
				System.out.println("getType >>" + chatMember.getType());
				System.out.println("getStatus >>" + chatMember.getStatus());
				System.out.println("getChat().getId() >>" + chatMember.getChat().getId());
				System.out.println("getUser().getId() >>" + chatMember.getUser().getId());
				System.out.println("getMemberSince >>" + chatMember.getMemberSince());
			}

			@Override
			public void onMyProfile(User myprofile) {
				System.out.println("getUser id " + myprofile.getId());
				System.out.println("getName " + myprofile.getName());
				System.out.println("getTerminal " + myprofile.getTerminal());
				System.out.println("getProfile " + myprofile.getProfile());
				if (myprofile.getPhoto() != null) {
					System.out.println("Photo().getId " + myprofile.getPhoto().getId());
				}

			}

			@Override
			public void onChatAdministrators(ChatAdministrators chatAdministrators) {
				System.out.println("ChatAdministrators " + chatAdministrators.getChat().getId());

				for (int i = 0; i < chatAdministrators.getAdministrators().length; i++) {

					System.out.println("admin user id number" + (1 + i) + " >>>"
							+ chatAdministrators.getAdministrators()[i].getId());

				}
			}

			@Override
			public void onUserDetails(User user) {

				System.out.println("USER ID = " + user.getId());
			}

			@Override
			public void onUserJoinedBot(User user) {

				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void userStartedBot(User user) {

				System.out.println("User ID = " + user.getId());

			}

			@Override
			public void userStoppedBot(User user) {

				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void userLeftBot(User user) {
				System.out.println("User ID =" + user.getId());

			}

			@Override
			public void permanentUrl(PermanentUrl permenantUrl) {

			}

			@Override
			public void onChatDetails(Chat chat) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {
				// TODO Auto-generated method stub
				
			}
		});

	}

}
