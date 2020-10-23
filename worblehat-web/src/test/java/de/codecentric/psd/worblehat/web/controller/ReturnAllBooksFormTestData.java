package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;

public class ReturnAllBooksFormTestData {

    private ReturnAllBooksFormTestData(){

    }

    static ReturnAllBooksFormData anEmailAddress(){
        var returnAllBooksFormData = new ReturnAllBooksFormData();
        returnAllBooksFormData.setEmailAddress("sandra@worblehat.net");
        return returnAllBooksFormData;
    }

    static ReturnAllBooksFormData emailAddress(String emailAddress) {
        var returnAllBooksFormData = new ReturnAllBooksFormData();
        returnAllBooksFormData.setEmailAddress(emailAddress);
        return returnAllBooksFormData;
    }
}
