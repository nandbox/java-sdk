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
public class Chat {

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
	private static final String KEY_REFERENCE = "reference";

	private String id;
	private String title;
	private String name;
	private String type;
	private String version;
	private String languageCode;
	private String regions;
	private String description;
	private Photo photo;
	private String category;
	private Integer memberCount;
	private String inviteLink;
	private TagDefination[] tagsDefinition;
	private Long reference;

	public Chat() {
	}

	public Chat(JSONObject obj) {

		this.id = (String) obj.get(KEY_ID);
		this.title = (String) obj.get(KEY_TITLE);
		this.name = (String) obj.get(KEY_NAME);
		this.type = (String) obj.get(KEY_TYPE);
		this.version = (String) obj.get(KEY_VERSION);
		this.languageCode = (String) obj.get(KEY_LANGUAGE_CODE);
		this.regions = (String) obj.get(KEY_REGIONS);
		this.description = (String) obj.get(KEY_DESCRIPTION);
		this.category = (String) obj.get(KEY_CATEGORY);
		this.memberCount = (Integer) obj.get(KEY_MEMBER_COUNT);
		this.inviteLink = (String) obj.get(KEY_INVITE_LINK);
		this.reference = obj.get(KEY_REFERENCE)==null ? null:(long) obj.get(KEY_REFERENCE);
		// this.tag = obj.get(KEY_TAGS_DEFINITION) != null
		// ? new Tag((JSONObject) obj.get(KEY_TAGS_DEFINITION))
		// : null;

		JSONArray tagsArrayObj = (JSONArray) obj.get(KEY_TAGS_DEFINITION);
		if (null !=tagsArrayObj) {
			setTagsDefinition(new TagDefination[tagsArrayObj.size()]);
			for (int i = 0; i < tagsArrayObj.size(); i++) {
				getTagsDefinition()[i] = new TagDefination((JSONObject) tagsArrayObj.get(i));
			}
		}
	}

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
		if (reference != null)
			obj.put(KEY_REFERENCE, reference);
		// if (tag != null)
		// obj.put(KEY_TAGS_DEFINITION, tag);

		// JSONArray tagsArrayObj = new JSONArray();
		// for (int i = 0; i < tag.length; i++) {
		// tagsArrayObj.add(tag[i].toJsonObject());
		// }
		// obj.put(KEY_TAGS_DEFINITION, tagsArrayObj);

		return obj;

	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the Title for channels and group chats
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the Title for channels and group chats set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the languageCode
	 */
	public String getLanguageCode() {
		return languageCode;
	}

	/**
	 * @param languageCode
	 *            the languageCode to set
	 */
	public void setLanguageCode(String languageCode) {
		this.languageCode = languageCode;
	}

	/**
	 * @return the regions
	 */
	public String getRegions() {
		return regions;
	}

	/**
	 * @param regions
	 *            the regions to set
	 */
	public void setRegions(String regions) {
		this.regions = regions;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the memberCount
	 */
	public Integer getMemberCount() {
		return memberCount;
	}

	/**
	 * @param memberCount
	 *            the memberCount to set
	 */
	public void setMemberCount(Integer memberCount) {
		this.memberCount = memberCount;
	}

	/**
	 * @return the inviteLink
	 */
	public String getInviteLink() {
		return inviteLink;
	}

	/**
	 * @param inviteLink
	 *            the inviteLink to set
	 */
	public void setInviteLink(String inviteLink) {
		this.inviteLink = inviteLink;
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
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
	 * @param name
	 *            user or bot name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public TagDefination[] getTagsDefinition() {
		return tagsDefinition;
	}

	public void setTagsDefinition(TagDefination[] tagsDefinition) {
		this.tagsDefinition = tagsDefinition;
	}

	public Long getReference() {
		return reference;
	}

	public void setReference(Long reference) {
		this.reference = reference;
	}

	// public Tag getTag() {
	// return tag;
	// }
	//
	// public void setTag(Tag tag) {
	// this.tag = tag;
	// }

}
