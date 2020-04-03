package br.com.cauejannini.personagensmarvel.models;

import java.io.Serializable;

public class ResourceList implements Serializable {

    private int available;
    private int returned;
    private String collectionURI;
    private Object[] items;

}
