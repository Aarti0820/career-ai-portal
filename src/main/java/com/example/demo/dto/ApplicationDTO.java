package com.example.demo.dto;

public class ApplicationDTO {
	
	 private String userName;
	    private String jobTitle;

	    public ApplicationDTO(String userName, String jobTitle){
	        this.userName = userName;
	        this.jobTitle = jobTitle;
	    }

	    public String getUserName() { return userName; }
	    public String getJobTitle() { return jobTitle; }

}
