/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.arepclase;


import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

/**
 *
 * @author ximena.rodriguez
 */
public class URLParser {

    public static void main(String[] args) throws MalformedURLException, URISyntaxException {
        URL misitio = new URL("http://estudiantes.escuelaing.edu.co:80/notas.html?id=5#section");
        System.out.println("Protocolo: " + misitio.getProtocol());
        System.out.println("Authority: " + misitio.getAuthority());
        System.out.println("Host: " + misitio.getHost());
        System.out.println("Port: " + misitio.getPort());
        System.out.println("Path: " + misitio.getPath());
        System.out.println("Query: " + misitio.getQuery());
        System.out.println("File: " + misitio.getFile());
        System.out.println("Ref: " + misitio.getRef());
        
        URI miuri = new URI("/cliente?t=67&o=25");
        System.out.println("Protocolo: " + miuri.getAuthority());
        
      
    }
}
