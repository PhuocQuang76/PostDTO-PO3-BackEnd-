package com.revature.dto;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import com.revature.models.Post;
import com.revature.utilites.SecurityUtil;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
*
* The class is a data transfer object for the Post model
*
* @author John Boyle
* @batch: 211129-Enterprise
*
*/
@Data
@AllArgsConstructor
public class PostDTO {

	private int psid;

	private ProfileDTO creator;

	private String body;

	private String imgURL;

	private int type;

	private Timestamp datePosted;

	private Set<Integer> likes = new LinkedHashSet<>();
	
	private GroupDTO group;

	public PostDTO() {
		super();
		psid = SecurityUtil.getId();
	}
	
	public PostDTO(Post post) {
		if (post != null) {
			psid = post.getPsid();
			creator = post.getCreator() != null ? new ProfileDTO(post.getCreator()) : null;
			body = post.getBody();
			imgURL = post.getImgURL();
			type = post.getType();
			datePosted = post.getDatePosted();
			likes = post.getLikes();
			group = post.getGroup() != null ? new GroupDTO(post.getGroup()) : null;
		}
	}
	
	public Post toPost() {
		String imageUrl;
		if (this.type == 0) {
			String[] parts = this.imgURL.split("/");
			if (parts[2].equalsIgnoreCase("youtu.be")) {
				imageUrl = "https://www.youtube.com/embed/" + parts[3];
			} else if (parts[2].equalsIgnoreCase("www.youtube.com")) {
				if (parts[3].startsWith("watch")) {
					String[] subParts = parts[3].split("=");
					imageUrl = "https://www.youtube.com/embed/" + subParts[1].substring(0, 11);
				} else imageUrl = this.imgURL;
			} else imageUrl = this.imgURL;
		} else imageUrl = this.imgURL;
		return new Post(psid, (creator != null ? creator.toProfile() : null), body, imageUrl, type,datePosted, likes,
				(group != null ? group.toGroup() : null));
	}
	
	public PostDTO(ProfileDTO creator, String body, String imgURL,int type, Timestamp datePosted, GroupDTO group) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.type = type;
		this.datePosted = datePosted;
		this.group = group;
	}

	public PostDTO(ProfileDTO creator, String body, String imgURL, int type,Timestamp datePosted, Set<Integer> likes, GroupDTO group) {
		this();
		this.creator = creator;
		this.body = body;
		this.imgURL = imgURL;
		this.type = type;
		this.datePosted = datePosted;
		this.likes = likes;
		this.group = group;
	}
	
}