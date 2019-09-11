package com.nandbox.bots.api.test;

import static com.nandbox.bots.api.outmessages.OutMessage.WEB_PREVIEW_INSTANCE_VIEW;
import static com.nandbox.bots.api.util.Utils.formatDurationInMinsAndSeconds;
import static com.nandbox.bots.api.util.Utils.getUniqueId;

import java.util.ArrayList;
import java.util.HashMap;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.data.Button;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.Data;
import com.nandbox.bots.api.data.Menu;
import com.nandbox.bots.api.data.Row;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.data.WhiteListUser;
import com.nandbox.bots.api.inmessages.BlackList;
import com.nandbox.bots.api.inmessages.ChatAdministrators;
import com.nandbox.bots.api.inmessages.ChatMember;
import com.nandbox.bots.api.inmessages.ChatMenuCallback;
import com.nandbox.bots.api.inmessages.IncomingMessage;
import com.nandbox.bots.api.inmessages.IncomingMessage.MessageType;
import com.nandbox.bots.api.inmessages.InlineMessageCallback;
import com.nandbox.bots.api.inmessages.InlineSearch;
import com.nandbox.bots.api.inmessages.MessageAck;
import com.nandbox.bots.api.inmessages.PermanentUrl;
import com.nandbox.bots.api.inmessages.WhiteList;
import com.nandbox.bots.api.outmessages.ArticleOutMessage;
import com.nandbox.bots.api.outmessages.AudioOutMessage;
import com.nandbox.bots.api.outmessages.ContactOutMessage;
import com.nandbox.bots.api.outmessages.DocumentOutMessage;
import com.nandbox.bots.api.outmessages.GifOutMessage;
import com.nandbox.bots.api.outmessages.GifOutMessage.GifType;
import com.nandbox.bots.api.outmessages.LocationOutMessage;
import com.nandbox.bots.api.outmessages.OutMessage;
import com.nandbox.bots.api.outmessages.PhotoOutMessage;
import com.nandbox.bots.api.outmessages.SetChatMenuOutMessage;
import com.nandbox.bots.api.outmessages.TextOutMessage;
import com.nandbox.bots.api.outmessages.VideoOutMessage;
import com.nandbox.bots.api.outmessages.VoiceOutMessage;
import com.nandbox.bots.api.util.MediaTransfer;
import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * Test Class
 * 
 * @author Hossam Mohamed
 *
 */
public class MultipleTests {

	public static final String TOKEN = "90091783845360162:0:P1SQvGAKqp3Ka2vxOvBInVRE5hqVAR";

	private static final String MAIN_MENU_001 = "MAIN_MENU_001";

	private static HashMap<String, OutMessage> outMsgsListener = new HashMap<>();

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

				if (incomingMsg.getReplyToMessageId() != null) {
					if (MessageType.text.toString().equals(incomingMsg.getType())) {

						if (incomingMsg.getText().equalsIgnoreCase("getChatMember")) {
							api.getChatMember(incomingMsg.getChat().getId(), incomingMsg.getFrom().getId());
						} else if (incomingMsg.getText().equals("getAdmins")) {
							api.getChatAdministrators(incomingMsg.getChat().getId());
						} else if (incomingMsg.getText().equalsIgnoreCase("getChat")) {
							api.getChat(incomingMsg.getChat().getId());
						} else if (incomingMsg.getText().equals("getBlackList")) {
							api.getBlackList(incomingMsg.getChat().getId());
						} else if (incomingMsg.getText().equals("addBlacklistPatterns")) {

							ArrayList<Data> dataList = new ArrayList<>();

							Data data = new Data();
							data.setPattern("44444*");
							data.setExample("44444444");

							dataList.add(data);

							Data data2 = new Data();
							data2.setPattern("222*");
							data2.setExample("222222222");

							dataList.add(data2);

							api.addBlacklistPatterns(incomingMsg.getChat().getId(), dataList);

						} else if (incomingMsg.getText().equals("addWhitelistPatterns")) {

							ArrayList<Data> dataList = new ArrayList<>();

							Data data = new Data();
							data.setPattern("4444*");
							data.setExample("444444");

							dataList.add(data);

							api.addWhitelistPatterns(incomingMsg.getChat().getId(), dataList);

						}

						else if (incomingMsg.getText().equals("getWhiteList")) {
							api.getWhiteList(incomingMsg.getChat().getId());
						} else if (incomingMsg.getText().equals("addBlackList")) {
							ArrayList<String> users = new ArrayList<>();
							users.add("111133");
							users.add("222223");
							api.addBlackList(incomingMsg.getChat().getId(), users);
						} else if (incomingMsg.getText().equals("addWhiteList")) {

							ArrayList<String> tagsList = new ArrayList<>();
							tagsList.add("1");
							tagsList.add("2");

							ArrayList<WhiteListUser> whiteListUsersArray = new ArrayList<>();

							WhiteListUser whiteListUser = new WhiteListUser();
							whiteListUser.setSignupUser("3636526");
							whiteListUser.setTags(tagsList);

							whiteListUsersArray.add(whiteListUser);

							api.addWhiteList(incomingMsg.getChat().getId(), whiteListUsersArray);

						} else if (incomingMsg.getText().equals("deleteBlackList")) {

							ArrayList<String> users = new ArrayList<>();
							users.add("111133");

							api.deleteBlackList(incomingMsg.getChat().getId(), users);

						} else if (incomingMsg.getText().equals("deleteWhitelist")) {

							ArrayList<String> users = new ArrayList<>();
							users.add("111133");

							api.deleteWhiteList(incomingMsg.getChat().getId(), users);

						} else if (incomingMsg.getText().equals("deleteBlacklistPatterns")) {

							ArrayList<String> pattern = new ArrayList<>();
							pattern.add("222*");
							api.deleteBlackListPatterns(incomingMsg.getChat().getId(), pattern);

						} else if (incomingMsg.getText().equals("deleteWhitelistPatterns")) {

							ArrayList<String> pattern = new ArrayList<>();
							pattern.add("5555*");
							api.deleteWhiteListPatterns(incomingMsg.getChat().getId(), pattern);

						} else if (incomingMsg.getText().equalsIgnoreCase("BigText")) {
							api.sendTextWithBackground(incomingMsg.getChat().getId(), "Hi From Bot", "#EE82EE");
						}

					}
				} else {
					System.err.println("=========>> " + incomingMsg.getType() + " Message Received =========>>");
					System.out.println("incomingMsg.getMessageId() : " + incomingMsg.getMessageId());
					System.out.println("incomingMsg.getDate() : " + incomingMsg.getDate());
					System.out.println("incomingMsg.getReference() : " + incomingMsg.getReference());
					System.out.println("incomingMsg.getCaption(): " + incomingMsg.getCaption());

					if (incomingMsg.getSentTo() != null) {
						System.out.println("incomingMsg.getSentTo().getId() : " + incomingMsg.getSentTo().getId());
					}
					System.out.println("================start of Chat Object ===================");
					System.out.println("incomingMsg.getChat().getId() : " + incomingMsg.getChat().getId());
					System.out.println("incomingMsg.getChat().getTitle() :" + incomingMsg.getChat().getTitle());
					System.out.println("incomingMsg.getChat().getName() :" + incomingMsg.getChat().getName());
					System.out.println("incomingMsg.getChat().getType() :" + incomingMsg.getChat().getType());
					System.out.println("================End of Chat Object ===================");
					System.out.println("================Start of From User Object ===================");
					System.out.println("incomingMsg.getFrom().getId() : " + incomingMsg.getFrom().getId());
					System.out.println("incomingMsg.getFrom().getName() : " + incomingMsg.getFrom().getName());
					System.out.println("incomingMsg.getFrom().getStatus(): " + incomingMsg.getFrom().getStatus());
					System.out.println("incomingMsg.getFrom().getVersion() : " + incomingMsg.getFrom().getVersion());
					System.out.println("incomingMsg.getFrom().getType() : " + incomingMsg.getFrom().getType());
					System.out.println("================End of From User Object ===================");

					if (MessageType.text.toString().equals(incomingMsg.getType())) {

						if (incomingMsg.getText().equals("getMyProfile")) {
							api.getMyProfiles();
						} else if (incomingMsg.getText().equals("getChat")) {
							api.getChat(incomingMsg.getChat().getId());
						} else if (incomingMsg.getText().equals("getUser")) {
							api.getUser(incomingMsg.getFrom().getId());
						} else if ("1bc".equalsIgnoreCase(incomingMsg.getText())) {

							TextOutMessage outmsg = new TextOutMessage();
							Long reference = getUniqueId();
							outmsg.setChatId(incomingMsg.getChat().getId());
							outmsg.setReference(reference);
							outmsg.setText("https://edition.cnn.com/");
							outmsg.setWebPagePreview(WEB_PREVIEW_INSTANCE_VIEW);

							outmsg.setEcho(1);
							String menuRef = MAIN_MENU_001;
							Button oneBtn = createButton("", "oneBtnCBInWebView", 1, "RED", "White", null, null);

							oneBtn.setButtonIcon("ic_ball_ic_24dp");
							oneBtn.setButtonIconBgColor("#FFFF44");

							Row firstRow = new Row();
							firstRow.setRowOrder(1);
							firstRow.setButtons(new Button[] { oneBtn });

							Menu inlineMenu = new Menu();
							inlineMenu.setMenuRef(menuRef);
							inlineMenu.setRows(new Row[] { firstRow });
							outmsg.setMenuRef(menuRef);
							outmsg.setInlineMenu(new Menu[] { inlineMenu });
							api.send(outmsg);

						}

						else if ("3bc".equalsIgnoreCase(incomingMsg.getText())) {

							TextOutMessage outmsg = new TextOutMessage();
							Long reference = getUniqueId();
							outmsg.setChatId(incomingMsg.getChat().getId());
							outmsg.setReference(reference);
							outmsg.setText("https://edition.cnn.com/");
							outmsg.setWebPagePreview(WEB_PREVIEW_INSTANCE_VIEW);

							outmsg.setEcho(1);
							String menuRef = MAIN_MENU_001;
							Button oneBtn = createButton("Visit a Milestone", "oneBtnCBInWebView", 1, "RED", "White",
									null, null);
							Button secondBtn = createButton("Cairo Porto Mall", "secondBtn", 1, "RED", "White", null,
									null);
							Button thirdButton = createButton("Seven Stars Mall", "thirdBtn", 1, "RED", "White", null,
									null);
							oneBtn.setButtonURL("https://edition.cnn.com/");

							Row firstRow = new Row();
							firstRow.setRowOrder(1);
							firstRow.setButtons(new Button[] { oneBtn, secondBtn, thirdButton });

							Menu inlineMenu = new Menu();
							inlineMenu.setMenuRef(menuRef);
							inlineMenu.setRows(new Row[] { firstRow });
							outmsg.setMenuRef(menuRef);
							outmsg.setInlineMenu(new Menu[] { inlineMenu });
							api.send(outmsg);

						}

						else if ("buttonIcon".equalsIgnoreCase(incomingMsg.getText())) {

							TextOutMessage outmsg = new TextOutMessage();
							Long reference = getUniqueId();
							outmsg.setChatId(incomingMsg.getChat().getId());
							outmsg.setReference(reference);
							outmsg.setText("https://edition.cnn.com/");
							outmsg.setWebPagePreview(WEB_PREVIEW_INSTANCE_VIEW);

							outmsg.setEcho(1);
							String menuRef = MAIN_MENU_001;
							Button oneBtn = createButton("RSS", "oneBtnCBInWebView", 1, "RED", "White", null, null);
							oneBtn.setButtonIcon("ic_mood_bad_24dp");
							oneBtn.setButtonIconBgColor("#FFFF44");
							Button secondBtn = createButton("Calendar", "secondBtn", 1, "RED", "White", null, null);
							secondBtn.setButtonIcon("ic_hourglass_full_24dp");
							secondBtn.setButtonIconBgColor("White");
							Button thirdButton = createButton("Feed", "thirdBtn", 1, "RED", "White", null, null);
							thirdButton.setButtonIcon("ic_credit_card_24dp");
							thirdButton.setButtonIconBgColor("Yellow");
							thirdButton.setButtonURL("https://edition.cnn.com/");

							Row firstRow = new Row();
							firstRow.setRowOrder(1);
							firstRow.setButtons(new Button[] { oneBtn, secondBtn, thirdButton });

							Menu inlineMenu = new Menu();
							inlineMenu.setMenuRef(menuRef);
							inlineMenu.setRows(new Row[] { firstRow });
							outmsg.setMenuRef(menuRef);
							outmsg.setInlineMenu(new Menu[] { inlineMenu });
							api.send(outmsg);

						}

						if ("3m".equalsIgnoreCase(incomingMsg.getText())) {

							String chatId = incomingMsg.getChat().getId();

							Utils utility = new Utils();
							utility.setNavigationButton(chatId, "mainMenu", api);

							Button menuBtn1 = createButton("مصراوي", "mainCB", 1, "Gray", "Red", null, null);
							menuBtn1.setButtonIcon("ic_smoke_free_24dp");
							menuBtn1.setButtonIconBgColor("#00FFFF");
							Button menuBtn2 = createButton("Funny", "funnyCB", 1, "Gray", "Red", null, null);
							menuBtn2.setButtonIcon("ic_timeline_24dp");

							Button menuBtn3 = createButton("Option", "optionCB", 1, "Gray", "Red", null, null);
							menuBtn3.setButtonIcon("ic_pregnant_woman_24dp");
							menuBtn3.setButtonIconBgColor("orange");

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

						}

						else {
							api.sendText(incomingMsg.getChat().getId(), incomingMsg.getText());
						}
					}

					if (MessageType.text_file.toString().equals(incomingMsg.getType())) {
						handleIncomingTextFileMsg(incomingMsg);
					}

					// Incoming Photo Message
					else if (MessageType.photo.toString().equals(incomingMsg.getType())) {
						handleIncomingPhotoMsg(incomingMsg);
					}
					// Incoming Video Message
					else if (MessageType.video.toString().equals(incomingMsg.getType())) {
						handleIncomingVideoMsg(incomingMsg);
					}
					// Incoming Voice Message
					else if (MessageType.voice.toString().equals(incomingMsg.getType())) {
						handleIncomingVoiceMsg(incomingMsg);
					}

					else if (MessageType.article.toString().equals(incomingMsg.getType())) {
						handleIncomingArticleMsg(incomingMsg);
					}
					// I
					// Incoming Audio Message
					else if (MessageType.audio.toString().equals(incomingMsg.getType())) {
						handleIncomingAudioMsg(incomingMsg);
					}
					// Incoming Gif Message
					else if (MessageType.gif.toString().equals(incomingMsg.getType())) {
						handleIncomingGifMsg(incomingMsg);
					}
					// Incoming Location Message
					else if (MessageType.location.toString().equals(incomingMsg.getType())) {
						handleIncomingLocationMsg(incomingMsg);
					}
					// Incoming Contact Message
					else if (MessageType.contact.toString().equals(incomingMsg.getType())) {
						handleIncomingContactMsg(incomingMsg);
					}
					// Incoming Document Message
					else if (MessageType.document.toString().equals(incomingMsg.getType())) {
						handleIncomingDocumentMsg(incomingMsg);
					}

				}

			}

			private void handleIncomingDocumentMsg(IncomingMessage incomingMsg) {

				System.out.println("================start of Document Object ===================");
				System.out.println("incomingMsg.getDocument().getId() : " + incomingMsg.getDocument().getId());
				System.out.println("incomingMsg.getDocument().getName() : " + incomingMsg.getDocument().getName());
				System.out.println("incomingMsg.getDocument().getSize() : " + incomingMsg.getDocument().getSize());

				DocumentOutMessage documentOutMsg = new DocumentOutMessage();
				documentOutMsg.setChatId(incomingMsg.getChat().getId());
				documentOutMsg.setReference(getUniqueId());
				documentOutMsg.setDocument(incomingMsg.getDocument().getId());
				documentOutMsg.setName("Document renamed inside Bot");
				documentOutMsg.setCaption("Document From Bot");
				api.send(documentOutMsg);

				// send Document using sendDocument

				// String uploadedDocumentId = MediaTransfer.uploadFile(TOKEN,
				// "./upload/DocumentSample.docx");
				String uploadedDocumentId = MediaTransfer.uploadFile(TOKEN, "./upload/welcome.jpg");

				api.sendDocument(incomingMsg.getChat().getId(), uploadedDocumentId, "Document Caption");
				api.sendDocument(incomingMsg.getChat().getId(), uploadedDocumentId, getUniqueId(), "Send doc with ref");
				api.sendDocument(incomingMsg.getChat().getId(), uploadedDocumentId, getUniqueId(), null, null, null,
						null, "from all option send", null, null, null);

				api.sendText(incomingMsg.getChat().getId(),
						"Document size : " + incomingMsg.getDocument().getSize() + " , Document File Name is : "
								+ incomingMsg.getDocument().getName() + " , Document File ID is : "
								+ incomingMsg.getDocument().getId());
			}

			private void handleIncomingContactMsg(IncomingMessage incomingMsg) {

				System.out.println("================start of Contact Object ===================");
				System.out.println("incomingMsg.getContact()getName() : " + incomingMsg.getContact().getName());
				System.out.println(
						"incomingMsg.getContact().getPhoneNumber() : " + incomingMsg.getContact().getPhoneNumber());

				// send contatc using ContactOutMessage object
				ContactOutMessage contactOutMsg = new ContactOutMessage();
				contactOutMsg.setChatId(incomingMsg.getChat().getId());
				contactOutMsg.setReference(getUniqueId());
				contactOutMsg.setName(incomingMsg.getContact().getName());
				contactOutMsg.setPhoneNumber(incomingMsg.getContact().getPhoneNumber());
				api.send(contactOutMsg);

				// send contatc using sendContact
				api.sendContact(incomingMsg.getChat().getId(), incomingMsg.getContact().getPhoneNumber(),
						incomingMsg.getContact().getName());

				api.sendContact(incomingMsg.getChat().getId(), incomingMsg.getContact().getPhoneNumber(),
						incomingMsg.getContact().getName(), getUniqueId());

				api.sendContact(incomingMsg.getChat().getId(), incomingMsg.getContact().getPhoneNumber(),
						incomingMsg.getContact().getName(), getUniqueId(), null, null, null, null, null);

				api.sendText(incomingMsg.getChat().getId(), " Contact Name  is : " + incomingMsg.getContact().getName()
						+ " Phone number  is : " + incomingMsg.getContact().getPhoneNumber());

			}

			private void handleIncomingTextFileMsg(IncomingMessage incomingMsg) {

				String textFileId = incomingMsg.getTextFile().getId();
				System.out.println("================start of TextFile Object ===================");
				System.out.println("incomingMsg.getText() : " + incomingMsg.getText());
				System.out.println("incomingMsg.getTextFile().getId() : " + textFileId);
				System.out.println("incomingMsg.getTextFile().getSize(): " + incomingMsg.getTextFile().getSize());

				MediaTransfer.downloadFile(TOKEN, textFileId, "./download", null);

				String uploadedTextFileId = MediaTransfer.uploadFile(TOKEN, "./download/" + textFileId);

				api.sendDocument(incomingMsg.getChat().getId(), uploadedTextFileId, getUniqueId(), null, null, null,
						null, "Text File Caption", null, null, null);

			}

			private void handleIncomingLocationMsg(IncomingMessage incomingMsg) {

				System.out.println("================start of Location Object ===================");
				System.out.println("incomingMsg.getLocation().getName() : " + incomingMsg.getLocation().getName());
				System.out
						.println("incomingMsg.getLocation().getDetails() : " + incomingMsg.getLocation().getDetails());
				System.out.println(
						"incomingMsg.getLocation().getLatitude() : " + incomingMsg.getLocation().getLatitude());
				System.out.println(
						"incomingMsg.getLocation().getLongitude() : " + incomingMsg.getLocation().getLongitude());

				LocationOutMessage locationOutMsg = new LocationOutMessage();
				locationOutMsg.setChatId(incomingMsg.getChat().getId());
				locationOutMsg.setReference(getUniqueId());
				locationOutMsg.setName(incomingMsg.getLocation().getName());
				locationOutMsg.setDetails(incomingMsg.getLocation().getDetails());
				locationOutMsg.setLatitude(incomingMsg.getLocation().getLatitude());
				locationOutMsg.setLongitude(incomingMsg.getLocation().getLongitude());
				locationOutMsg.setCaption("Location From Bot");
				api.send(locationOutMsg);

				// send location using sendlocation

				api.sendlocation(incomingMsg.getChat().getId(), incomingMsg.getLocation().getLatitude(),
						incomingMsg.getLocation().getLongitude());
				api.sendlocation(incomingMsg.getChat().getId(), incomingMsg.getLocation().getLatitude(),
						incomingMsg.getLocation().getLongitude(), getUniqueId());
				api.sendlocation(incomingMsg.getChat().getId(), incomingMsg.getLocation().getLatitude(),
						incomingMsg.getLocation().getLongitude(), getUniqueId(), null, null, null, null, null, null,
						null);

				api.sendText(incomingMsg.getChat().getId(),
						" Latitude is : " + incomingMsg.getLocation().getLatitude() + " Longitude is : "
								+ incomingMsg.getLocation().getLongitude() + " and name is :"
								+ incomingMsg.getLocation().getName() + " and details is :"
								+ incomingMsg.getLocation().getDetails());
			}

			private void handleIncomingGifMsg(IncomingMessage incomingMsg) {

				System.out.println("================start of Gif Object ===================");
				System.out.println("incomingMsg.getGif().getId(): " + incomingMsg.getGif().getId());
				System.out.println("incomingMsg.getGif().getWidth(): " + incomingMsg.getGif().getWidth());
				System.out.println("incomingMsg.getGif().getHeight(): " + incomingMsg.getGif().getHeight());
				System.out.println("incomingMsg.getGif().getSize(): " + incomingMsg.getGif().getSize());
				System.out.println("================start of Gif Thumbinil  Object ===================");
				if (incomingMsg.getGif().getThumbnail() != null && incomingMsg.getGif().getId().endsWith(".gif")) {
					System.out.println("================End of Gif Thumbinil Object ===================");
					System.out.println("incomingMsg.getGif().getThumbnail().getId() "
							+ incomingMsg.getGif().getThumbnail().getId());
					System.out.println("incomingMsg.getGif().getThumbnail().getWidth(): "
							+ incomingMsg.getGif().getThumbnail().getWidth());
					System.out.println("incomingMsg.getGif().getThumbnail().getHeight(): "
							+ incomingMsg.getGif().getThumbnail().getHeight());
					System.out.println("================End of Photo Object ===================");

					api.sendText(incomingMsg.getChat().getId(),
							"Gif Size is : " + incomingMsg.getGif().getSize() + " and Gif width is :"
									+ incomingMsg.getGif().getWidth() + " and Gif height is :"
									+ incomingMsg.getGif().getHeight() + " and caption is : " + incomingMsg.getCaption()
									+ "\n\n Wait please sending you a Gif ....");

					String uploadedGifPhotoId = MediaTransfer.uploadFile(TOKEN, "./upload/gif_sample.gif");

					if (uploadedGifPhotoId != null) {

						GifOutMessage gifMsg = new GifOutMessage(GifType.PHOTO);
						gifMsg.setChatId(incomingMsg.getChat().getId());
						gifMsg.setReference(getUniqueId());
						gifMsg.setGif(uploadedGifPhotoId);
						gifMsg.setCaption("Gif From Bot");
						gifMsg.setEcho(0);
						api.send(gifMsg);

						// send GIF using sendGIF

						api.sendGIF(incomingMsg.getChat().getId(),
								"92ff95add24e1c5f9294e5bea733f1629f7636fa081cb6e16d1ec256b792528c.gif", "without ref");
					}

				} else if (incomingMsg.getGif().getThumbnail() != null
						&& incomingMsg.getGif().getId().endsWith("mp4")) {

					String uploadedGifVideoId = MediaTransfer.uploadFile(TOKEN, "./upload/CeateGroup.mov");

					if (uploadedGifVideoId != null) {

						GifOutMessage gifMsg = new GifOutMessage(GifType.VIDEO);
						gifMsg.setChatId(incomingMsg.getChat().getId());
						gifMsg.setReference(getUniqueId());
						gifMsg.setGif(uploadedGifVideoId);
						gifMsg.setCaption("Gif From Bot");
						gifMsg.setEcho(0);
						api.send(gifMsg);

						// send GIF using sendGIF

						api.sendGIFVideo(incomingMsg.getChat().getId(), uploadedGifVideoId, "without ref");
						api.sendGIFVideo(incomingMsg.getChat().getId(), uploadedGifVideoId, getUniqueId(), "with ref");
						api.sendGIFVideo(incomingMsg.getChat().getId(), uploadedGifVideoId, getUniqueId(), null, null,
								null, null, "with option", null);
					}

				} else {

					System.err.println("================No Thumbinil Object in this Phot ===================");
				}

			}

			private void handleIncomingAudioMsg(IncomingMessage incomingMsg) {

				System.out.println("================start of Voice Object ===================");
				System.out.println("incomingMsg.getAudio().getId() : " + incomingMsg.getAudio().getId());
				System.out.println("incomingMsg.getAudio().getDuration() : " + incomingMsg.getAudio().getDuration());
				System.out.println("incomingMsg.getAudio().getTitle() : " + incomingMsg.getAudio().getTitle());
				System.out.println("incomingMsg.getAudio().getSize(): " + incomingMsg.getAudio().getSize());
				System.out.println("incomingMsg.getAudio().getPerformer() : " + incomingMsg.getAudio().getPerformer());
				System.out.println("================start of Photo Thumbinil  Object ===================");

				AudioOutMessage audioOutMsg = new AudioOutMessage();
				audioOutMsg.setChatId(incomingMsg.getChat().getId());
				audioOutMsg.setReference(getUniqueId());
				audioOutMsg.setAudio(incomingMsg.getAudio().getId());
				audioOutMsg.setPerformer("Perfomer Man");
				audioOutMsg.setTitle(" Song");
				audioOutMsg.setCaption("Audio From Bot");

				api.send(audioOutMsg);
				api.sendText(incomingMsg.getChat().getId(),
						"Audio Title : " + incomingMsg.getAudio().getTitle() + " ,Audio Performer is : "
								+ incomingMsg.getAudio().getPerformer() + ", Audio Size is : "
								+ incomingMsg.getAudio().getSize() + " and Audio Duration is :"
								+ formatDurationInMinsAndSeconds(incomingMsg.getAudio().getDuration()));
			}

			private void handleIncomingVoiceMsg(IncomingMessage incomingMsg) {
				System.out.println("================start of Voice Object ===================");
				System.out.println("incomingMsg.getVoice().getId() : " + incomingMsg.getVoice().getId());
				System.out.println("incomingMsg.getVoice().getDuration(): " + incomingMsg.getVoice().getDuration());
				System.out.println("incomingMsg.getVoice().getSize(): " + incomingMsg.getVoice().getSize());
				System.out.println("================start of Photo Thumbinil  Object ===================");

				VoiceOutMessage voiceOutMsg = new VoiceOutMessage();
				voiceOutMsg.setChatId(incomingMsg.getChat().getId());
				voiceOutMsg.setReference(getUniqueId());
				voiceOutMsg.setVoice(incomingMsg.getVoice().getId());
				voiceOutMsg.setSize(700L/* incomingMsg.getVoice().getSize() */);
				voiceOutMsg.setCaption("Vocie From Bot");
				api.send(voiceOutMsg);
				api.sendText(incomingMsg.getChat().getId(),
						"Voice Size is : " + incomingMsg.getVoice().getSize() + " and Voice Duration is :"
								+ formatDurationInMinsAndSeconds(incomingMsg.getVoice().getDuration()));

			}

			private void handleIncomingArticleMsg(IncomingMessage incomingMsg) {
				System.out.println("================start of Article Object ===================");
				System.out.println("incomingMsg.getArticle().getUrl() : " + incomingMsg.getUrl());
				System.out.println("================start of Article  Object ===================");

				ArticleOutMessage articleOutMessage = new ArticleOutMessage();
				articleOutMessage.setChatId(incomingMsg.getChat().getId());
				articleOutMessage.setReference(getUniqueId());
				articleOutMessage.setUrl(incomingMsg.getUrl());

				api.send(articleOutMessage);

			}

			private void handleIncomingVideoMsg(IncomingMessage incomingMsg) {
				System.out.println("================start of Video Object ===================");
				System.out.println("incomingMsg.getVideo().getId() : " + incomingMsg.getVideo().getId());
				System.out.println("incomingMsg.getVideo().getWidth() : " + incomingMsg.getVideo().getWidth());
				System.out.println("incomingMsg.getVideo().getHeight() : " + incomingMsg.getVideo().getHeight());
				System.out.println("incomingMsg.getVideo().getSize() : " + incomingMsg.getVideo().getSize());
				System.out.println("incomingMsg.getVideo().getDuration() : " + incomingMsg.getVideo().getDuration());
				System.out.println("================start of Video Thumbinil  Object ===================");
				if (incomingMsg.getVideo().getThumbnail() != null) {
					System.out.println("================End of Photo Thumbinil Object ===================");
					System.out.println("incomingMsg.getVideo().getThumbnail().getId() "
							+ incomingMsg.getVideo().getThumbnail().getId());
					System.out.println("incomingMsg.getVideo().getThumbnail().getWidth(): "
							+ incomingMsg.getVideo().getThumbnail().getWidth());
					System.out.println("incomingMsg.getVideo().getThumbnail().getHeight(): "
							+ incomingMsg.getVideo().getThumbnail().getHeight());
					System.out.println("================End of Photo Object ===================");
				} else {
					System.err.println("================No Thumbinil Object in this Phot ===================");
				}

				String uploadedVideoId = MediaTransfer.uploadFile(TOKEN, "./upload/recallTest.mp4");

				if (uploadedVideoId != null) {
					//
					VideoOutMessage vidoMsg = new VideoOutMessage();
					vidoMsg.setChatId(incomingMsg.getChat().getId());
					vidoMsg.setReference(getUniqueId());
					vidoMsg.setVideo(uploadedVideoId);
					vidoMsg.setCaption("Video From Bot");
					vidoMsg.setEcho(0);
					api.send(vidoMsg);
					//
				}

				api.sendText(incomingMsg.getChat().getId(),
						"Video Size is : " + incomingMsg.getVideo().getSize() + " and Video width is :"
								+ incomingMsg.getVideo().getWidth() + " and Video height is :"
								+ incomingMsg.getVideo().getHeight() + " and Video duration is :"
								+ formatDurationInMinsAndSeconds(incomingMsg.getVideo().getDuration())
								+ " and caption is : " + incomingMsg.getCaption());

			}

			private void handleIncomingPhotoMsg(IncomingMessage incomingMsg) {
				System.out.println("================start of Photo Object ===================");
				System.out.println("incomingMsg.getPhoto().getId(): " + incomingMsg.getPhoto().getId());
				System.out.println("incomingMsg.getPhoto().getWidth(): " + incomingMsg.getPhoto().getWidth());
				System.out.println("incomingMsg.getPhoto().getHeight(): " + incomingMsg.getPhoto().getHeight());
				System.out.println("incomingMsg.getPhoto().getSize(): " + incomingMsg.getPhoto().getSize());
				System.out.println("================start of Photo Thumbinil  Object ===================");
				if (incomingMsg.getPhoto().getThumbnail() != null) {
					System.out.println("================End of Photo Thumbinil Object ===================");
					System.out.println("incomingMsg.getPhoto().getThumbnail().getId() "
							+ incomingMsg.getPhoto().getThumbnail().getId());
					System.out.println("incomingMsg.getPhoto().getThumbnail().getWidth(): "
							+ incomingMsg.getPhoto().getThumbnail().getWidth());
					System.out.println("incomingMsg.getPhoto().getThumbnail().getHeight(): "
							+ incomingMsg.getPhoto().getThumbnail().getHeight());
					System.out.println("================End of Photo Object ===================");
				} else {
					System.err.println("================No Thumbinil Object in this Phot ===================");
				}

				api.generatePermanentUrl(incomingMsg.getPhoto().getId(), "Any Reference");

				MediaTransfer.downloadFile(TOKEN, incomingMsg.getPhoto().getId(), "./download", null);

				api.sendText(incomingMsg.getChat().getId(),
						"Photo Size is : " + incomingMsg.getPhoto().getSize() + " and Photo width is :"
								+ incomingMsg.getPhoto().getWidth() + " and Photo height is :"
								+ incomingMsg.getPhoto().getHeight() + " and caption is : " + incomingMsg.getCaption()
								+ "\n\n Wait please sending you a photo ....");

				String uploadedPhotId = MediaTransfer.uploadFile(TOKEN, "./upload/welcome.jpg");
				if (uploadedPhotId != null) {
					PhotoOutMessage photoMsg = new PhotoOutMessage();
					photoMsg.setChatId(incomingMsg.getChat().getId());
					photoMsg.setReference(getUniqueId());
					photoMsg.setPhoto(uploadedPhotId);
					photoMsg.setCaption("Photo From Bot");
					photoMsg.setEcho(1);
					api.send(photoMsg);
				}

			}

			private Button createButton(String label, String callback, int order, String bgColor, String txtColor,
					String buttonQuery, String nextMenuRef) {
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

				System.out.println(inlineMsgCallback.toJsonObject());

			}

			@Override
			public void onMessagAckCallback(MessageAck msgAck) {
				String reference = (String) msgAck.getReference();

				OutMessage removedOutMsg = outMsgsListener.remove(reference);
				System.out.println("***** Ack for Message with Reference : " + reference);
				if (removedOutMsg != null) {
					System.out
							.println("***** Removed Out Message from Resource Listener" + removedOutMsg.toJsonObject());
				}

			}

			@Override
			public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback) {

			}

			@Override
			public void onChatMember(ChatMember chatMember) {
				System.out.println("Chat Member Details received : ");
				System.out.println("Chat Id : " + chatMember.getChat().getId());
				api.getChat(chatMember.getChat().getId());
				System.out.println("User Id : " + chatMember.getUser().getId());
				api.getUser(chatMember.getUser().getId());

			}

			@Override
			public void onChatAdministrators(ChatAdministrators chatAdministrators) {

				for (int i = 0; i < chatAdministrators.getAdministrators().length; i++) {
					User user = chatAdministrators.getAdministrators()[i];
					api.sendText(user.getId(), "Hi from Multiple tests bot");
					api.getUser(user.getId());
					System.out.println(user.toJsonObject());
				}

			}

			@Override
			public void onUserDetails(User user) {
				System.out.println("User Name : " + user.getName());
				System.out.println("User Type : " + user.getType());
			}

			@Override
			public void onUserJoinedBot(User user) {
			}

			@Override
			public void userStartedBot(User user) {

			}

			@Override
			public void onMyProfile(User user) {
				System.out.println("user.getName() : " + user.getName());
				System.out.println("user.getProfile() : " + user.getProfile());
				System.out.println("user.getIsBot() : " + user.getIsBot());
				System.out.println("user.getVersion() : " + user.getVersion());
			}

			@Override
			public void userStoppedBot(User user) {

			}

			@Override
			public void userLeftBot(User user) {

			}

			@Override
			public void permanentUrl(PermanentUrl permenantUrl) {
				System.out.println("File ID is : " + permenantUrl.getFile());
				System.out.println("File public URL  is : " + permenantUrl.getUrl());
				System.out.println("Param1  is : " + permenantUrl.getParam1());
			}

			@Override
			public void onChatDetails(Chat chat) {
				System.out.println("Chat Title : " + chat.getTitle());
			}

			@Override
			public void onInlineSearh(InlineSearch inlineSearch) {

			}

			@Override
			public void onBlackList(BlackList blackList) {

				for (int i = 0; i < blackList.getUsers().length; i++) {

					System.out.println("blackList.getUsers().getId()=" + blackList.getUsers()[i].getId());
					System.out
							.println("blackList.getUsers().getSignupUser()=" + blackList.getUsers()[i].getSignupUser());
				}

				// System.out.println("blackList.getUsers().getId()=" + blackList.getUsers();
				// System.out.println("blackList.getUsers().getMsisdn()=" +
				// blackList.getUsers().getMsisdn());
				System.out.println("blackList.getEop()=" + blackList.getEop());
				System.out.println("blackList.getChat().getId()=" + blackList.getChat().getId());
			}

			@Override
			public void onWhiteList(WhiteList whiteList) {

				for (int i = 0; i < whiteList.getUsers().length; i++) {

					System.out.println("whiteList().getId()=" + whiteList.getUsers()[i].getId());
					System.out.println("whiteList().getSignupUser()=" + whiteList.getUsers()[i].getSignupUser());

					// System.out.println(whiteList.getUsers()[i].getTags());
				}

				System.out.println("getEop()=" + whiteList.getEop());
				System.out.println("getChat().getId()=" + whiteList.getChat().getId());

			}

		});

	}

}
