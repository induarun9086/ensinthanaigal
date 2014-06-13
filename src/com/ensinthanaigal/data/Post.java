package com.ensinthanaigal.data;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import com.google.appengine.api.datastore.Text;


@Entity
public class Post {

	private Long postID;
	private int category;
	@Lob
	private Text title;
	@Lob
	private Text content;
	@Lob
	private String tags;
        private boolean testMode;
	private Long postedAt;
	private Long modifiedAt;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Long getPostID() {
		return postID;
	}

	public void setPostID(Long postID) {
		this.postID = postID;
	}

	public Text getTitle() {
		return title;
	}

	public void setTitle(Text title) {
		this.title = title;
	}

	public Text getContent() {
		return content;
	}

	public void setContent(Text content) {
		this.content = content;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public Long getPostedAt() {
		return postedAt;
	}

	public void setPostedAt(Long postedAt) {
		this.postedAt = postedAt;
	}

	public Long getModifiedAt() {
		return modifiedAt;
	}

	public void setModifiedAt(Long modifiedAt) {
		this.modifiedAt = modifiedAt;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public boolean isTestMode()
	{
	    return testMode;
	}

	public void setTestMode(boolean testMode)
	{
	    this.testMode = testMode;
	}

}
