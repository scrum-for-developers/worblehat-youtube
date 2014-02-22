package de.codecentric.psd.worblehat.web.command;

/**
 * This class represent the form data of the return book form.
 * 
 * @author psd
 * 
 */
public class ReturnAllBooksFormData {

	
	/**
	 * Empty constructor, required by Spring Framework.
	 */
	public ReturnAllBooksFormData() {
		super();
	}

	/**
	 * @param emailAddress the user email address
	 */
	public ReturnAllBooksFormData(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	private String emailAddress;

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

}
