package com.nandbox.bots.api.data;

import net.minidev.json.JSONObject;

/**
 * Object representing User
 * 
 * @author Ahmed A. El-Malatawy
 *
 */
public class User {

	private static final String KEY_ID = "id";
	private static final String KEY_NAME = "name";
	private static final String KEY_TERMINAL = "terminal";
	private static final String KEY_TYPE = "type";	
	private static final String KEY_IS_BOT = "is_bot";
	private static final String KEY_VERSION = "version";
	private static final String KEY_LAST_SEEN = "last_seen";
	private static final String KEY_STATUS = "status";
	private static final String KEY_PHOTO = "photo";
	private static final String KEY_PROFILE = "profile";
	private static final String KEY_SHORT_NAME = "short_name";

	private String id;
	private String name;
	private String version;
	private String terminal;
	private String type;	
	private Boolean isBot;
	private String lastSeen;
	private String status;
	private String profile;
	private Photo photo;
	private String shortName;

	
	public User() { 
	}

	public User(JSONObject obj) {
		this.id = (String) obj.get(KEY_ID);
		this.name = (String) obj.get(KEY_NAME);
		this.version = (String) obj.get(KEY_VERSION);
		this.terminal = (String) obj.get(KEY_TERMINAL);
		this.type = (String) obj.get(KEY_TYPE);		
		this.isBot = (Boolean) obj.get(KEY_IS_BOT);
		this.lastSeen = (String) obj.get(KEY_LAST_SEEN);
		this.status = (String) obj.get(KEY_STATUS);
		this.profile = (String) obj.get(KEY_PROFILE);

		this.photo = obj.get(KEY_PHOTO) != null ? new Photo((JSONObject) obj.get(KEY_PHOTO)) : null;
		
		this.shortName = (String) obj.get(KEY_SHORT_NAME);
	}

	public JSONObject toJsonObject() {
		JSONObject obj = new JSONObject();

		if (id != null)
			obj.put(KEY_ID, id);

		if (name != null)
			obj.put(KEY_NAME, name);

		if (version != null)
			obj.put(KEY_VERSION, version);

		if (terminal != null)
			obj.put(KEY_TERMINAL, terminal);
		
		if (type != null)
			obj.put(KEY_TYPE, type);

		if (isBot != null)
			obj.put(KEY_IS_BOT, isBot);

		if (lastSeen != null)
			obj.put(KEY_LAST_SEEN, lastSeen);

		if (status != null)
			obj.put(KEY_STATUS, status);

		if (profile != null)
			obj.put(KEY_PROFILE, profile);
		
		if (photo != null)
			obj.put(KEY_PHOTO, photo);
		
		if(shortName != null)
			obj.put(KEY_SHORT_NAME, shortName);

		return obj;

	}

	/**
	 * @return the User Id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id
	 *            the User Id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the User name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the User name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the Profile version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the Profile version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the lastSeen
	 */
	public String getLastSeen() {
		return lastSeen;
	}

	/**
	 * @param lastSeen
	 *            the lastSeen to set
	 */
	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
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
	 * @return the isBot
	 */
	public Boolean getIsBot() {
		return isBot;
	}

	/**
	 * @param isBot
	 *            the isBot to set
	 */
	public void setIsBot(Boolean isBot) {
		this.isBot = isBot;
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
	 * @return the terminal
	 */
	public String getTerminal() {
		return terminal;
	}

	/**
	 * @param terminal
	 *            the terminal to set
	 */
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	/**
	 * @return the profile
	 */
	public String getProfile() {
		return profile;
	}

	/**
	 * @param profile
	 *            the profile to set
	 */
	public void setProfile(String profile) {
		this.profile = profile;
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
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
