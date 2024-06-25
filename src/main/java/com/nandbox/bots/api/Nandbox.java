package com.nandbox.bots.api;

import java.util.List;

import com.nandbox.bots.api.data.*;
import com.nandbox.bots.api.inmessages.*;
import com.nandbox.bots.api.outmessages.OutMessage;

import net.minidev.json.JSONObject;

/**
 * Nandbox Interface that include 2 sub interfaces ,
 * {@link com.nandbox.bots.api.Nandbox.Callback Callback} sub interface for
 * handling all events expected from nandbox server , these events will be used
 * to handle actions to do when the bot connected and authenticated with nandbox
 * server , also handling all errors and closing the connection with nandbox
 * server, the callback is used also to handle all incoming messages from
 * server, the other sub interface is {@link com.nandbox.bots.api.Nandbox.Api
 * Api} which is used to send any outgoing message to nandbox server
 * 
 * @author Ahmed A. El-Malatawy
 *
 *
 */
public final class Nandbox {

	/**
	 * Handling all events expected from server , these events includes actions to
	 * do when the bot connected to nandbox server
	 * {@link com.nandbox.bots.api.Nandbox.Callback#onConnect onConnect} and also
	 * handling errors and closing of the connection with nandbox server. Callback
	 * is used also to handle all incoming messages from nandbox server like
	 * {@link com.nandbox.bots.api.Nandbox.Callback#onReceive onReceive} which
	 * indicates that the bot received a message
	 * 
	 * @author Ahmed A. El-Malatawy
	 *
	 */
	public interface Callback {


		/**
		 * This event indicates that the bot is successfully connected and authenticated
		 * with nandbox server.
		 * 
		 * @param api
		 *            reference for the api interface to be used to send any message to
		 *            nandbox server
		 * 
		 */
		public void onConnect(Nandbox.Api api);

		/**
		 * This event should be used to handle incoming messages from server
		 * 
		 * @param incomingMsg
		 *            incoming message from server
		 */
		public void onReceive(IncomingMessage incomingMsg);

		/**
		 * This event should be used to handle incoming messages from server
		 * 
		 * @param obj
		 *            incoming json object from server, this will be fired only if the
		 *            bot received a message from server that is not handled in Bot API.
		 *            .
		 */
		public void onReceive(JSONObject obj);

		/**
		 * After the web socket closed , this event will be called to allow clearing and
		 * destroying any resources.
		 */
		public void onClose();

		/**
		 * When any issue happened related to web socket,this event will be called to
		 * allow handling of this error.
		 */
		public void onError();

		/**
		 * This event should be used to handle normal keypad button pressed
		 * 
		 * @param chatMenuCallback
		 *            This object represents an incoming callback query from a callback
		 *            button associated with a normal keypad menu
		 */
		public void onChatMenuCallBack(ChatMenuCallback chatMenuCallback);

		/**
		 * This event should be used to handle Inline menu keypad button pressed
		 * 
		 * @param inlineMsgCallback
		 *            This object represents an incoming callback query from a callback
		 *            button within an inline keypad menu associated with a specific
		 *            message.
		 */
		public void onInlineMessageCallback(InlineMessageCallback inlineMsgCallback);

		/**
		 * @param msgAck
		 *            Message ack object holding acknowledge message details
		 */
		public void onMessagAckCallback(MessageAck msgAck);

		/**
		 * This event should be used to handle users joining bot.
		 * 
		 * @param user
		 *            object represents a user,returned when user joined bot
		 */
		public void onUserJoinedBot(User user);

		/**
		 * This event should be used to handle users joining/leaving chat (i.e. Channel
		 * or Group) . This event will fire as a reply to
		 * getChatMember,banChatMember,unbanChatMember,removeChatMember and when user
		 * join or leaves the chat.
		 * 
		 * @param chatMember
		 *            This object represents a chat member user
		 */
		public void onChatMember(ChatMember chatMember);

		/**
		 * This event should be fired as a reply to calling
		 * {@link com.nandbox.bots.api.Nandbox.Api#getChatAdministrators(String)
		 * getChatAdministrators} .
		 * 
		 * @param chatAdministrators
		 *            This object represents chat administrator users for specific chat.
		 */
		public void onChatAdministrators(ChatAdministrators chatAdministrators);

		/**
		 * This event should be used to handle user restarting bot action
		 * 
		 * @param user
		 *            object holding information about
		 */
		public void userStartedBot(User user);

		/**
		 * @param user
		 *            the bot profile
		 */
		public void onMyProfile(User user);
		/**
		 * @param productItem
		 * 			  the product
		 */
		public void onProductItem(ProductItem productItem);
		/**
		 * @param collectionProduct
		 * 			  the collection
		 */
		public void onCollectionProduct(CollectionProduct collectionProduct);

		/**
		 * @param user
		 *            user object contain user details
		 */
		public void onUserDetails(User user);

		/**
		 * @param user
		 *            details of the usee who stopped the bot
		 */
		public void userStoppedBot(User user);

		/**
		 * @param user
		 *            details of the use who left the bot
		 */
		public void userLeftBot(User user);

		/**
		 * Call back for generating URL
		 * 
		 * @param permenantUrl
		 *            the generated permanent URL details
		 */
		public void permanentUrl(PermanentUrl permenantUrl);

		/**
		 * @param chat
		 *            chat object contain details of specific chat
		 */
		public void onChatDetails(Chat chat);

		/**
		 * @param inlineSearch
		 *            inline search object that contain inline search info
		 */
		public void onInlineSearh(InlineSearch inlineSearch);
		
		public void onBlackList(BlackList blackList);
		
		public void onWhiteList(WhiteList whiteList);
		
		/**
		 * This event should be used to handle incoming schedule message from server
		 * 
		 * @param incomingScheduleMsg
		 *           incoming Schedule message from server
		 */

		public void onScheduleMessage(IncomingMessage incomingScheduleMsg);

		public void onWorkflowDetails(WorkflowDetails workflowDetails);

		void onCreateChat(Chat chat);
	}

	/**
	 * @author Ahmed A. El-Malatawy
	 *
	 */
	public interface Api {

		/**
		 * @param message
		 *            message object
		 */
		void send(OutMessage message);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param text
		 *            text message to be send
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendText(String chatId, String text);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User id and can not set
		 *            to null
		 * @param text
		 *            text message to send
		 * @param bgColor
		 *            text message background color Hex code in format : #HHHHHH
		 * @return reference the sent message reference
		 */
		public Long sendTextWithBackground(String chatId, String text, String bgColor);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param text
		 *            text message to be send
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 */
		public void sendText(String chatId, String text, Long reference);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id
		 * @param text
		 *            text message to be send
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param bgColor
		 *            backText message Background color in hexadecimal format (Hex
		 *            triplet ) or according to its common English name . Example : Red
		 *            color can be set as #FF0000
		 * @param tab 
		 * 			tab ID to which you want to send the message
		 * 
		 */
		public void sendText(String chatId, String text, Long reference, String replyToMessageId, String toUserId,
				Integer webPagePreview, Boolean disableNotification, Integer chatSettings, String bgColor, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param photoFileId
		 *            Id of photo and can not set to null
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with Photo
		 *            "Photo caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */

		public Long sendPhoto(String chatId, String photoFileId, String caption);



		/**
		 * @param productId
		 * 			  Unique identifier for the target product and can not set to null
		 */
		public void getProductItem(String productId);
		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param photoFileId
		 *            Id of photo and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with Photo
		 *            "Photo caption 0-256 characters
		 */

		public void sendPhoto(String chatId, String photoFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param photoFileId
		 *            Id of photo and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with Photo
		 *            "Photo caption 0-256 characters "
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only .
		 * @param tab 
		 * 			tab ID to which you want to send the message
		 */
		public void sendPhoto(String chatId, String photoFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
				Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param videoFileId
		 *            Id of video and can not set to null
		 * @param caption
		 *            caption (optionally <code>null</code>) to be send with video
		 *            "Video caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendVideo(String chatId, String videoFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param videoFileId
		 *            Id of video and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with video
		 *            "video caption 0-256 characters"
		 */
		public void sendVideo(String chatId, String videoFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param videoFileId
		 *            Id of video and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with video
		 *            "video caption 0-256 characters "
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message
		 */
		public void sendVideo(String chatId, String videoFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
				Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param audioFileId
		 *            Id of audio and can not set to null
		 * @param caption
		 *            caption (optionally <code>null</code>) to be send with audio
		 *            "Audio caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendAudio(String chatId, String audioFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param audioFileId
		 *            Id of audio and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with audio
		 *            "audio caption 0-256 characters"
		 */
		public void sendAudio(String chatId, String audioFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param audioFileId
		 *            Id of audio and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with audio
		 *            "audio caption 0-256 characters "
		 *
		 * @param performer
		 *            name of audio perfromer
		 * @param title
		 *            title of the audio
		 * @param chatSettings
		 *            title of the audio
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message
		 */
		public void sendAudio(String chatId, String audioFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, String performer,
				String title, Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param phoneNumber
		 *            Contact phone number
		 * @param name
		 *            Contact full name
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendContact(String chatId, String phoneNumber, String name);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param phoneNumber
		 *            Contact phone number
		 * @param name
		 *            Contact full name
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 */
		public void sendContact(String chatId, String phoneNumber, String name, Long reference);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param phoneNumber
		 *            Contact phone number
		 * @param name
		 *            Contact full name
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 *
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message           
		 */
		public void sendContact(String chatId, String phoneNumber, String name, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param voiceFileId
		 *            Id of audio and can not set to null
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with voice
		 *            "voice caption 0-256 characters"
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendVoice(String chatId, String voiceFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param voiceFileId
		 *            Id of voice and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with voice
		 *            "voice caption 0-256 characters"
		 */
		public void sendVoice(String chatId, String voiceFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param voiceFileId
		 *            Id of voice and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with voice
		 *            "voice caption 0-256 characters "
		 *
		 * @param size
		 *            size of the voice
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message           
		 */
		public void sendVoice(String chatId, String voiceFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, Long size,
				Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param documentFileId
		 *            Id of document and can not set to null
		 * @param caption
		 *            caption (optionally <code>null</code>) to be send with document
		 *            "document caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendDocument(String chatId, String documentFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param documentFileId
		 *            Id of document and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with document
		 *            "document caption 0-256 characters"
		 */
		public void sendDocument(String chatId, String documentFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param documentFileId
		 *            Id of document and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with document
		 *            "document caption 0-256 characters "
		 * @param name
		 *            name of document
		 * @param size
		 *            size of document
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message          
		 */
		public void sendDocument(String chatId, String documentFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption, String name,
				Integer size, Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param latitude
		 *            Latitude of the location
		 * @param longitude
		 *            Longitude of the location
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendlocation(String chatId, String latitude, String longitude);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param latitude
		 *            Latitude of the location
		 * @param longitude
		 *            Longitude of the location
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 */
		public void sendlocation(String chatId, String latitude, String longitude, Long reference);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param latitude
		 *            Latitude of the location
		 * @param longitude
		 *            longitude of the location
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param name
		 *            Period in seconds for which the location will be updated (see Live
		 *            Locations, should be between 60 and 86400.
		 * @param details
		 *            details of the location
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message           
		 */
		public void sendlocation(String chatId, String latitude, String longitude, Long reference,
				String replyToMessageId, String toUserId, Integer webPagePreview, Boolean disableNotification,
				String name, String details, Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param caption
		 *            caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */

		public Long sendGIF(String chatId, String gifFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters"
		 */

		public void sendGIF(String chatId, String gifFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters "
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message           
		 */
		public void sendGIF(String chatId, String gifFileId, Long reference, String replyToMessageId, String toUserId,
				Integer webPagePreview, Boolean disableNotification, String caption, Integer chatSettings, String tab);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param caption
		 *            caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters "
		 * @return reference ( Unique local identifier for the message)
		 */
		public Long sendGIFVideo(String chatId, String gifFileId, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters"
		 */
		public void sendGIFVideo(String chatId, String gifFileId, Long reference, String caption);

		/**
		 * @param chatId
		 *            Unique identifier for the target chat or User_id and can not set
		 *            to null
		 * @param gifFileId
		 *            Id of gif and can not set to null
		 * @param reference
		 *            Unique local identifier for the target chat/user , can not set to
		 *            <code>null</code>
		 * @param replyToMessageId
		 *            If the message is a reply, ID of the original message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param webPagePreview
		 *            Disables link previews for links in this message
		 * @param disableNotification
		 *            Sends the message silently. Users will receive a notification with
		 *            no sound.
		 * @param caption
		 *            Caption (optionally <code>null</code>) to be send with gif "gif
		 *            caption 0-256 characters "
		 * @param chatSettings
		 *            <B>1</B> to send to Chat(i.e. group/Channel)super admins only
		 * @param tab 
		 * 			tab ID to which you want to send the message           
		 */
		public void sendGIFVideo(String chatId, String gifFileId, Long reference, String replyToMessageId,
				String toUserId, Integer webPagePreview, Boolean disableNotification, String caption,
				Integer chatSettings, String tab);

		/**
		 * Use this message to update existing Message sent
		 * 
		 * @param messageId
		 *            Unique identifier for the message and can not set to null
		 * @param text
		 *            The new message text
		 * @param caption
		 *            The new caption text
		 * @param toUserId
		 *            Id of Target user to receive updated message
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param tab 
		 * 			tab ID to which you want to send the message               
		 */
		void updateMessage(String messageId, String text, String caption, String toUserId, String chatId, String tab);

		/**
		 * @param collectionProductId
		  			id for the collection
		 *
		 */
		public void getCollectionProduct(String collectionProductId);
		/**
		 * @param messageId
		 *            Unique identifier for the message and can not set to null
		 * @param text
		 *            The new message text
		 * @param toUserId
		 *            Id of Target user to receive updated message
		 * @param tab
		 * 			tab ID to which you want to send the message
		 *
		 */
		void updateTextMsg(String messageId, String text, String toUserId, String tab);

		/**
		 * @param messageId
		 *            Unique identifier for the message and can not set to null
		 * @param caption
		 *            The new caption text
		 * @param toUserId
		 *            Id of Target user to receive updated message
		 * @param tab 
		 * 			tab ID to which you want to send the message               
		 */
		void updateMediaCaption(String messageId, String caption, String toUserId, String tab);

		/**
		 * @param messageId
		 *            Unique identifier for the message and can not set to null
		 * @param text
		 *            The new message text
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param tab 
		 * 			tab ID to which you want to send the message               
		 */
		void updateChatMsg(String messageId, String text, String chatId, String tab);

		/**
		 * @param messageId
		 *            Unique identifier for the message and can not set to null
		 * @param caption
		 *            The new caption text
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param tab 
		 * 			tab ID to which you want to send the message                
		 */
		void updateChatMediaCaption(String messageId, String caption, String chatId, String tab);
		
		@Deprecated
		void sendCellText(String userId, String screenId, String cellId, String text, Long reference);

		@Deprecated
		void sendCellPhoto(String userId, String screenId, String cellId, String photoFileId, Long reference);

		@Deprecated
		void sendCellVideo(String userId, String screenId, String cellId, String videoFileId, Long reference);

		/**
		 * Use this method to get profile for a user
		 * 
		 * @param userId
		 *            Id of Target user to receive updated message
		 */
		void getUser(String userId);

		/**
		 * Use this method to get Group or Channel information.
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 */
		void getChat(String chatId);

		/**
		 * Use this method to get Chat Member user public profile.
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param userId
		 *            Id of Target user to receive updated message
		 */

		void getChatMember(String chatId, String userId);

		/**
		 * Use this method to get Chat Administrators
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 */
		void getChatAdministrators(String chatId);

		/**
		 * Use this method to ban a Chat Member from accessing Chat
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param userId
		 *            Id of Target user to receive updated message
		 */

		void banChatMember(String chatId, String userId);

		/**
		 * Use this method to unban a Chat Member from accessing Chat.
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param userId
		 *            Id of Target user to receive updated message
		 */

		void unbanChatMember(String chatId, String userId);

		/**
		 * Use this method to remove a Chat Member from Chat.
		 * 
		 * @param chatId
		 *            Id of Target Group or Channel id
		 * @param userId
		 *            Id of Target user to receive updated message
		 */

		void removeChatMember(String chatId, String userId);

		/**
		 * Use this message to recall existing Message sent .
		 * 
		 * @param chatId
		 *            Id of Target
		 * @param messageId
		 *            Unique identifier for the message
		 * @param toUserId
		 *            If reply or send message to target user within a group chat or
		 *            channel, unique identifier of the target user.
		 * @param reference
		 *            Unique local identifier for the target chat/user
		 */
		void recallMessage(String chatId, String messageId, String toUserId, Long reference);

		/**
		 * Use this method to set Bot Profile
		 * 
		 * @param user
		 *            User object with unique user Id used to update User information
		 */
		void setMyProifle(User user);

		/**
		 * Use this method to set Chat Group or Channel information
		 * 
		 * @param chat
		 *            Chat object with unique chat Id used to update chat information
		 */
		void setChat(Chat chat);

		/**
		 * Use this method to get Bot Profile
		 */
		void getMyProfiles();

		/**
		 * Use this method to generate permanent URL for the file
		 * 
		 * @param file
		 *            unique file Id for the file
		 * @param param1
		 *            Generic parameter to set any local reference for the permanent
		 *            file
		 */
		void generatePermanentUrl(String file, String param1);


		void getBlackList(String chatId);

		void getWhiteList(String chatId);

		void addBlackList(String chatId, List<String> users);

//		void addWhiteList(String chatId, List<SignupUser> signupUser);

		void deleteBlackList(String chatId, List<String> users);

		void deleteWhiteList(String chatId, List<String> users);

		void addBlacklistPatterns(String chatId, List<Data> data);
		
		void addWhitelistPatterns(String chatId, List<Data> data);

		void deleteBlackListPatterns(String chatId, List<String> pattern);

		void deleteWhiteListPatterns(String chatId, List<String> pattern);


		void addWhiteList(String chatId, List<WhiteListUser> whiteListUsers);

		void setWorkflow(String userId , String screenId,String appId, List<WorkflowCell> workflowCell,Long reference,Boolean disableNotification);
		void setWorkflowAction(String userId ,String vappId, String screenId,String nextScreen,Long reference);
		void createChat(String type,String title, int isPublic,long reference);
		void addChatMember(long chatId, long userId);
		void addChatAdminMember(long chatId, long userId);
	}
}
