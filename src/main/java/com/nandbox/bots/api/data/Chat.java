package com.nandbox.bots.api.data;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;

/**
 * Object representing incoming Message source (i.e. from Group or Channel or
 * Contact ) and sender (i.e. Sender user , Group, or Channel)
 *
 * @author Hossam
 *
 */
/**
 * The Chat class represents a chat in a messaging application.
 */
public class Chat {

	// Constants for the keys in JSON objects
	private static final String KEY_ID = "id";
	private static final String KEY_TITLE = "title";
	private static final String KEY_NAME = "name";
	private static final String KEY_TYPE = "type";
	private static final String KEY_VERSION = "version";
	private static final String KEY_LANGUAGE_CODE = "language_code";
	private static final String KEY_REGIONS = "regions";
	private static final String KEY_DESCRIPTION = "description";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_CATEGORY = "category";
	private static final String KEY_MEMBER_COUNT = "member_count";
	private static final String KEY_INVITE_LINK = "invite_link";
	private static final String KEY_TAGS_DEFINITION = "tagsDefinition";

	// Fields representing the properties of the chat
	private String id;
	private String title;
	private String name;
	private String type;
	private String version;
	private Integer languageCode;
	private String regions;
	private String description;
	private Photo photo;
	private String category;
	private Integer memberCount;
	private String inviteLink;
	private TagDefination[] tagsDefinition;

	/**
	 * Default constructor for Chat objects.
	 */
	public Chat() {
	}

	/**
	 * Constructor that initializes a Chat object with the values from a JSONObject.
	 *
	 * @param obj the JSONObject to initialize the Chat object with.
	 */
	public Chat(JSONObject obj) {
		// Initialize fields with values from the JSONObject
		this.id = (String) obj.get(KEY_ID);
		this.title = (String) obj.get(KEY_TITLE);
		this.name = (String) obj.get(KEY_NAME);
		this.type = (String) obj.get(KEY_TYPE);
		this.version = (String) obj.get(KEY_VERSION);
		this.languageCode = (Integer) obj.get(KEY_LANGUAGE_CODE);
		this.regions = (String) obj.get(KEY_REGIONS);
		this.description = (String) obj.get(KEY_DESCRIPTION);
		this.category = (String) obj.get(KEY_CATEGORY);
		this.memberCount = (Integer) obj.get(KEY_MEMBER_COUNT);
		this.inviteLink = (String) obj.get(KEY_INVITE_LINK);

		JSONArray tagsArrayObj = (JSONArray) obj.get(KEY_TAGS_DEFINITION);
		if (null != tagsArrayObj) {
			setTagsDefinition(new TagDefination[tagsArrayObj.size()]);
			for (int i = 0; i < tagsArrayObj.size(); i++) {
				getTagsDefinition()[i] = new TagDefination((JSONObject) tagsArrayObj.get(i));
			}
		}
	}

	/**
	 * Returns a JSONObject representation of the Chat object.
	 *
	 * @return a JSONObject representation of the Chat object.
	 */
	public JSONObject toJsonObject() {

		JSONObject obj = new JSONObject();
		if (id != null)
			obj.put(KEY_ID, id);
		if (title != null)
			obj.put(KEY_TITLE, title);
		if (getName() != null)
			obj.put(KEY_NAME, getName());
		if (type != null)
			obj.put(KEY_TYPE, type);
		if (version != null)
			obj.put(KEY_VERSION, version);
		if (languageCode != null)
			obj.put(KEY_LANGUAGE_CODE, languageCode);
		if (regions != null)
			obj.put(KEY_REGIONS, regions);
		if (description != null)
			obj.put(KEY_DESCRIPTION, description);
		if (category != null)
			obj.put(KEY_CATEGORY, category);
		if (memberCount != null)
			obj.put(KEY_MEMBER_COUNT, memberCount);
		if (inviteLink != null)
			obj.put(KEY_INVITE_LINK, inviteLink);
		if (photo != null)
			obj.put(KEY_PHOTO, photo);
		return obj;

	}

	/**
	 * Getter method for the id field.
	 *
	 * @return the id of the chat.
	 */
	public String getId() {
		return id;
	}

	/**
	 * Setter method for the id field.
	 *
	 * @param id the id of the chat.
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Getter method for the title field.
	 *
	 * @return the title of the chat.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Setter method for the title field.
	 *
	 * @param title the title of the chat.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Getter method for the version field.
	 *
	 * @return the version of the chat.
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * Setter method for the version field.
	 *
	 * @param version the version of the chat.
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Getter method for the languageCode field.
	 *
	 * @return the language code of the chat.
	 */
	public Integer getLanguageCode() {
		return languageCode;
	}

	/**
	 * Setter method for the languageCode field.
	 *
	 * @param languageCode the language code of the chat.
	 */
	public void setLanguageCode(Integer languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * Getter method for the regions field.
	 *
	 * @return the regions of the chat.
	 */
	public String getRegions() {
		return regions;
	}

	/**
	 * Setter method for the regions field.
	 *
	 * @param regions the regions of the chat.
	 */
	public void setRegions(String regions) {
		this.regions = regions;
	}

	/**
	 * Getter method for the description field.
	 *
	 * @return the description of the chat.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Setter method for the description field.
	 *
	 * @param description the description of the chat.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Getter method for the category field.
	 *
	 * @return the category of the chat.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Setter method for the category field.
	 *
	 * @param category the category of the chat.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Getter method for the memberCount field.
	 *
	 * @return the member count of the chat.
	 */
	public Integer getMemberCount() {
		return memberCount;
	}

	/**
	 * Setter method for the memberCount field.
	 *
	 * @param memberCount the member count of the chat.
	 */
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * Getter method for the inviteLink field.
	 *
	 * @return the invite link of the chat.
	 */
	public String getInviteLink() {
		return inviteLink;
	}

	/**
	 * Setter method for the inviteLink field.
	 *
	 * @param inviteLink the invite link of the chat.
	 */
	public void setInviteLink(String inviteLink) {
		this.inviteLink = inviteLink;
	}


	/**
	 * Getter method for the photo field.
	 *
	 * @return the photo of the chat.
	 */
	public Photo getPhoto() {
		return photo;
	}

	/**
	 * Setter method for the photo field.
	 *
	 * @param photo the photo of the chat.
	 */
	public void setPhoto(Photo photo) {
		this.photo = photo;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return user or bot name to set
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name user or bot name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Getter method for the tagsDefinition field.
	 *
	 * @return the tag definitions of the chat.
	 */

	public TagDefination[] getTagsDefinition() {
		return tagsDefinition;
	}

	/**
	 * Setter method for the tagsDefinition field.
	 *
	 * @param tagsDefinition the tag definitions of the chat.
	 */
	public void setTagsDefinition(TagDefination[] tagsDefinition) {
		this.tagsDefinition = tagsDefinition;
	}

}

