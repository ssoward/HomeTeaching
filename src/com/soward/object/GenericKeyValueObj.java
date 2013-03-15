package com.soward.object;

public class GenericKeyValueObj {
    public GenericKeyValueObj( String key, String value ) {
        super();
        this.key = key;
        this.value = value;
    }
    String key;
    String value;
    public String getKey() {
        return key;
    }
    public void setKey( String key ) {
        this.key = key;
    }
    public String getValue() {
        return value;
    }
    public void setValue( String value ) {
        this.value = value;
    }

}
