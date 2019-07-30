/**
 * 
 */
package com.nandbox.bots.api.inmessages;

import com.nandbox.bots.api.data.Article;
import com.nandbox.bots.api.data.Audio;
import com.nandbox.bots.api.data.Chat;
import com.nandbox.bots.api.data.Contact;
import com.nandbox.bots.api.data.Document;
import com.nandbox.bots.api.data.Gif;
import com.nandbox.bots.api.data.Location;
import com.nandbox.bots.api.data.Photo;
import com.nandbox.bots.api.data.Sticker;
import com.nandbox.bots.api.data.TextFile;
import com.nandbox.bots.api.data.User;
import com.nandbox.bots.api.data.Video;
import com.nandbox.bots.api.data.Voice;
import com.nandbox.bots.api.util.Utils;

import net.minidev.json.JSONObject;

/**
 * it represents Incoming message Object , the incoming message Object is
 * representing Server Messages with method : message
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class IncomingMessage {

	public enum MessageType {

		text, photo, video, audio, voice, contact, location, gif, document, text_file, sticker, article;
	}

	public static final String KEY_MESSAGE = "message";
	public static final String KEY_MESSAGE_ID = "message_id";
	public static final String KEY_TYPE = "type";
	public static final String KEY_FROM_ADMIN = "from_admin";
	public static final String KEY_DATE = "date";
	private static final String KEY_TEXT = "text";
	private static final String KEY_LOCATION = "location";
	private static final String KEY_CONTACT = "contact";
	private static final String KEY_DOCUMENT = "document";
	private static final String KEY_FROM = "from";
	private static final String KEY_CHAT = "chat";
	private static final String KEY_REFERENCE = "reference";
	private static final String KEY_SENT_TO = "sent_to";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_GIF = "gif";
	private static final String KEY_VIDEO = "video";
	private static final String KEY_AUDIO = "audio";
	private static final String KEY_VOICE = "voice";
	private static final String KEY_CAPTION = "caption";
	private static final String KEY_STICKER = "sticker";
	private static final String KEY_REPLAY_TO_MESSAGE_ID = "reply_to_message_id";
	private static final String KEY_TEXT_FILE = "text_file";
	protected static final String KEY_STATUS = "status";
	protected static final String KEY_CHAT_SETTINGS = "chat_settings";
	protected static final String KEY_BG_COLOR = "bg_color";
	private static final String KEY_ARTICLE = "article";
	private static final String KEY_URL = "url";

	private String messageId;
	private String type;
	private Long date;
	private Long reference;
	private User from;
	private String replyToMessageId;
	private String caption;
	private Integer fromAdmin;
	private Chat chat;
	private String text;
	private Location location;
	private Contact contact;
	private User sentTo;
	private Photo photo;
	private Gif gif;
	private Voice voice;
	private Video video;
	private Audio audio;
	private Document document;
	private Sticker sticker;
	private TextFile textFile;
	private String status;
	private Integer chatSettings;
	private String bgColor;
	private Article article;
	private String url;
	
	
	public IncomingMessage(JSONObject jsonObj) {

		JSONObject obj = (JSONObject) jsonObj.get(KEY_MESSAGE);

		User fromUser = new User((JSONObject) obj.get(KEY_FROM));
		User sentToUser = obj.get(KEY_SENT_TO) != null ? new User((JSONObject) obj.get(KEY_SENT_TO)) : null;
		this.chat = obj.get(KEY_CHAT) == null ? null : new Chat((JSONObject) obj.get(KEY_CHAT));
		this.location = obj.get(KEY_LOCATION) != null ? new Location((JSONObject) obj.get(KEY_LOCATION)) : null;
		this.contact = obj.get(KEY_CONTACT) != null ? new Contact((JSONObject) obj.get(KEY_CONTACT)) : null;
		this.document = obj.get(KEY_DOCUMENT) != null ? new Document((JSONObject) obj.get(KEY_DOCUMENT)) : null;
		this.photo = obj.get(KEY_PHOTO) != null ? new Photo((JSONObject) obj.get(KEY_PHOTO)) : null;
		this.gif = obj.get(KEY_GIF) != null ? new Gif((JSONObject) obj.get(KEY_GIF)) : null;
		this.voice = obj.get(KEY_VOICE) != null ? new Voice((JSONObject) obj.get(KEY_VOICE)) : null;
		this.video = obj.get(KEY_VIDEO) != null ? new Video((JSONObject) obj.get(KEY_VIDEO)) : null;
		this.audio = obj.get(KEY_AUDIO) != null ? new Audio((JSONObject) obj.get(KEY_AUDIO)) : null;
		this.article = obj.get(KEY_ARTICLE) != null ? new Article((JSONObject) obj.get(KEY_ARTICLE)) : null;

		this.sticker = obj.get(KEY_STICKER) != null ? new Sticker((JSONObject) obj.get(KEY_STICKER)) : null;

		this.textFile = obj.get(KEY_TEXT_FILE) != null ? new TextFile((JSONObject) obj.get(KEY_TEXT_FILE)) : null;

		this.text = (String) obj.get(KEY_TEXT);
		this.messageId = (String) obj.get(KEY_MESSAGE_ID);
		this.date = Long.parseLong(String.valueOf(obj.get(KEY_DATE)));
		this.reference = Long.parseLong(String.valueOf(obj.get(KEY_REFERENCE)));
		this.from = fromUser;
		this.sentTo = sentToUser;
		this.fromAdmin = (Utils.getInteger(obj.get(KEY_FROM_ADMIN)));
		this.type = (String) obj.get(KEY_TYPE);
		this.caption = (String) obj.get(KEY_CAPTION);
		this.url = (String) obj.get(KEY_URL);
		this.replyToMessageId = (String) obj.get(KEY_REPLAY_TO_MESSAGE_ID);
		this.status = (String) obj.get(KEY_STATUS);
		this.chatSettings = Utils.getInteger(obj.get(KEY_CHAT_SETTINGS));
		this.bgColor = (String) obj.get(KEY_BG_COLOR);

	}

	/**
	 * @return JSON Object of Message
	 */
	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();

		if (type != null) {
			obj.put(KEY_TYPE, type);
		}

		if (date != null) {
			obj.put(KEY_DATE, date);
		}

		if (from != null) {
			obj.put(KEY_FROM, from.toJsonObject());
		}
		if (chat != null) {
			obj.put(KEY_CHAT, chat.toJsonObject());
		}
		if (messageId != null) {

			obj.put(KEY_MESSAGE_ID, messageId);
		}
		if (fromAdmin != null)
			obj.put(KEY_FROM_ADMIN, fromAdmin);

		if (status != null)
			obj.put(KEY_STATUS, status);
		if (sentTo != null)
			obj.put(KEY_SENT_TO, sentTo);

		if (reference != null)
			obj.put(KEY_REFERENCE, reference);

		if (caption != null)
			obj.put(KEY_CAPTION, caption);

		if (url != null)
			obj.put(KEY_URL, url);
		
		if (replyToMessageId != null)
			obj.put(KEY_REPLAY_TO_MESSAGE_ID, replyToMessageId);

		if (text != null) {
			obj.put(KEY_TEXT, text);
		}

		if (location != null) {
			obj.put(KEY_LOCATION, location);
		}

		if (contact != null) {
			obj.put(KEY_CONTACT, contact);
		}

		if (document != null) {
			obj.put(KEY_DOCUMENT, document);
		}

		if (photo != null) {
			obj.put(KEY_PHOTO, photo.toJsonObject());
		}

		if (gif != null) {
			obj.put(KEY_GIF, gif.toJsonObject());
		}

		if (voice != null) {
			obj.put(KEY_VOICE, voice.toJsonObject());
		}

		if (video != null) {
			obj.put(KEY_VIDEO, video.toJsonObject());
		}

		if (audio != null) {
			obj.put(KEY_AUDIO, audio.toJsonObject());
		}

		if (article != null) {
			obj.put(KEY_ARTICLE, article.toJsonObject());
		}
		if (sticker != null) {
			obj.put(KEY_STICKER, sticker.toJsonObject());
		}

		if (textFile != null) {
			obj.put(KEY_TEXT_FILE, textFile.toJsonObject());
		}

		if (bgColor != null) {
			obj.put(KEY_BG_COLOR, bgColor);
		}

		System.out.println("to " + obj.toJSONString());
		return obj;

	}

	/**
	 * @return the messageId
	 */
	public String getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the type like (text, photo, audio, video, location, voice, contact,
	 *         or document)
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set like (text, photo, audio, video, location, voice,
	 *            contact, or document)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public Long getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Long date) {
		this.date = date;
	}

	/**
	 * @return the reference ( Unique local identifier for the target chat/user)
	 */
	public Long getReference() {
		return reference;
	}

	/**
	 * @param reference
	 *            the reference to set ( Unique local identifier for the target
	 *            chat/user)
	 */
	public void setReference(Long reference) {
		this.reference = reference;
	}

	/**
	 * @return the from (User type)
	 */
	public User getFrom() {
		return from;
	}

	/**
	 * @param from
	 *            the from to set (User type)
	 */
	public void setFrom(User from) {
		this.from = from;
	}

	/**
	 * @return the replyToMessageId
	 */
	public String getReplyToMessageId() {
		return replyToMessageId;
	}

	/**
	 * @param replyToMessageId
	 *            the replyToMessageId to set
	 */
	public void setReplyToMessageId(String replyToMessageId) {
		this.replyToMessageId = replyToMessageId;
	}

	/**
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}

	/**
	 * @param caption
	 *            the caption to set
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}

	/**
	 * @return the fromAdmin
	 */
	public Integer isFromAdmin() {
		return fromAdmin;
	}

	/**
	 * @param fromAdmin
	 *            the fromAdmin to set
	 */
	public void setFromAdmin(Integer fromAdmin) {
		this.fromAdmin = fromAdmin;
	}

	/**
	 * @return the chat
	 */
	public Chat getChat() {
		return chat;
	}

	/**
	 * @param chat
	 *            the chat to set
	 */
	public void setChat(Chat chat) {
		this.chat = chat;
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @param text
	 *            the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @return the sentTo
	 */
	public User getSentTo() {
		return sentTo;
	}

	/**
	 * @param sentTo
	 *            the sentTo to set
	 */
	public void setSentTo(User sentTo) {
		this.sentTo = sentTo;
	}

	/**
	 * @return the photo
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * @param photo
	 *            the photo to set
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	/**
	 * @return the voice
	 */
	public Voice getVoice() {
		return voice;
	}

	/**
	 * @param voice
	 *            the voice to set
	 */
	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	/**
	 * @return the video
	 */
	public Video getVideo() {
		return video;
	}

	/**
	 * @param video
	 *            the video to set
	 */
	public void setVideo(Video video) {
		this.video = video;
	}

	/**
	 * @return the audio
	 */
	public Audio getAudio() {
		return audio;
	}

	/**
	 * @param audio
	 *            the audio to set
	 */
	public void setAudio(Audio audio) {
		this.audio = audio;
	}

	/**
	 * @return the gif
	 */
	public Gif getGif() {
		return gif;
	}

	/**
	 * @param gif
	 *            the gif to set
	 */
	public void setGif(Gif gif) {
		this.gif = gif;
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location
	 *            the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact() {
		return contact;
	}

	/**
	 * @param contact
	 *            the contact to set
	 */
	public void setContact(Contact contact) {
		this.contact = contact;
	}

	/**
	 * @return the document
	 */
	public Document getDocument() {
		return document;
	}

	/**
	 * @param document
	 *            the document to set
	 */
	public void setDocument(Document document) {
		this.document = document;
	}

	/**
	 * @return the textFile
	 */
	public TextFile getTextFile() {
		return textFile;
	}

	/**
	 * @param textFile
	 *            the textFile to set
	 */
	public void setTextFile(TextFile textFile) {
		this.textFile = textFile;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the sticker
	 */
	public Sticker getSticker() {
		return sticker;
	}

	/**
	 * @param sticker
	 *            the sticker to set
	 */
	public void setSticker(Sticker sticker) {
		this.sticker = sticker;
	}

	/**
	 * @return the chatSettings
	 */
	public Integer getChatSettings() {
		return chatSettings;
	}

	/**
	 * @param chatSettings
	 *            the chatSettings to set
	 */
	public void setChatSettings(Integer chatSettings) {
		this.chatSettings = chatSettings;
	}

	/**
	 * @return the Article
	 */
	public Article getArticle() {
		return article;
	}

	/**
	 * @param article
	 */
	public void setArticle(Article article) {
		this.article = article;
	}



	public boolean isVideoMsg() {
		return isMsgWithType(MessageType.video);
	}

	public boolean isTextMsg() {
		return isMsgWithType(MessageType.text);
	}

	public boolean isPhotoMsg() {
		return isMsgWithType(MessageType.photo);
	}

	public boolean isAudioMsg() {
		return isMsgWithType(MessageType.audio);
	}

	public boolean isLocationMsg() {
		return isMsgWithType(MessageType.location);
	}

	public boolean isVoiceMsg() {
		return isMsgWithType(MessageType.voice);
	}

	public boolean isGifMsg() {
		return isMsgWithType(MessageType.gif);
	}

	public boolean isStickerMsg() {
		return isMsgWithType(MessageType.sticker);
	}

	public boolean isTextFileMsg() {
		return isMsgWithType(MessageType.text_file);
	}

	public boolean isDocumentMsg() {
		return isMsgWithType(MessageType.document);
	}

	public boolean isContactMsg() {
		return isMsgWithType(MessageType.contact);
	}

	private boolean isMsgWithType(MessageType msgType) {

		return msgType.toString().equals(this.getType());
	}

	/**
	 * @return the bgColor
	 */
	public String getBgColor() {
		return bgColor;
	}

	/**
	 * @param bgColor
	 *            the bgColor to set
	 */
	public void setBgColor(String bgColor) {
		this.bgColor = bgColor;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
