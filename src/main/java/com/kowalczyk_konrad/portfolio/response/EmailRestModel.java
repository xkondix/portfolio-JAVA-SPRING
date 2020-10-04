package com.kowalczyk_konrad.portfolio.response;



public class EmailRestModel {

    private final String topic;
    private final String message;
    private final String value;

    public EmailRestModel(String topic, String message, String value) {
        this.topic = topic;
        this.value = value;
        this.message = message;
    }

    public String getTop()
    {
        return this.topic;
    }

    public String getMess()
    {
        StringBuilder mes = new StringBuilder();
        mes.append(this.value);
        mes.append(" -> ");
        mes.append(this.message);
        return  mes.toString();
    }
}
