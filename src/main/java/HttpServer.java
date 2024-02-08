/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ximena.rodriguez
 */
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class HttpServer {

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        HashMap<String,String> cacheMovies = new HashMap<String,String>();

        try {
            serverSocket = new ServerSocket(35000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        boolean running = true;
        while (running) {
            Socket clientSocket = null;
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean firstLine = true;
            String uriStr ="";



            while ((inputLine = in.readLine()) != null) {
                if(firstLine){
                    uriStr = inputLine.split(" ")[1];
                    firstLine = false;
                }
                System.out.println("Received: " + inputLine);
                if (!in.ready()) {
                    break;
                }
            }



            //En esta parte validamos cuando se haga una busqueda, de acuerdo al nombre que nos provee el usuario
            if (uriStr.startsWith("/Movies")){
                //En caso de que ya se halla realizado una busqueda previa sobre esta pelicula no habra necesidad
                //de consultar la api externa  directamente retornaremos lo que se tiene en esta api fachada
                if(cacheMovies.containsKey(uriStr.split("=")[1])){
                    outputLine = cacheMovies.get(uriStr.split("=")[1]);
                    clientSocket.getOutputStream().write(outputLine.getBytes());
                    //Sino se tiene una busqueda previa esta información como es nueva se hace la busqueda en la api externa
                    //y se guarda en nuestra api fachada
                }else {
                    HttpConection httpApiExternal = new HttpConection();
                    outputLine = httpApiExternal.ResponseRequest(uriStr.split("=")[1]);
                    cacheMovies.put(uriStr.split("=")[1], outputLine);
                }
                // sino escribe el path correto no va ha mostrar la pagina donde se puede consultar las peliculas
            }else{
                try {
                    httpResponse(new URI(uriStr), clientSocket.getOutputStream());
                }catch (Exception e){
                    httpError();
                }

            }
            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }
    public static String httpError() {
         return "HTTP/1.1 400 Not found\r\n" //encabezado necesario
                    + "Content-Type:text/html\r\n"
                    + "\r\n" //retorno de carro y salto de linea
                    + "<!DOCTYPE html>"
                    + "<html>\n"
                    + "    <head>\n"
                    + "        <title>Error Not found</title>\n"
                    + "        <meta charset=\"UTF-8\">\n"
                    + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                    + "    </head>\n";
        
    }
    
    public static String httpResponse(URI requestUri, OutputStream outputStream) throws IOException {

        Path file = Paths.get("target/classes/public" + requestUri.getPath());
        File fileSrc = new File(requestUri.getPath());
        String fileType = Files.probeContentType(fileSrc.toPath());
        System.out.println("que dato estas enviaando" + requestUri.getPath());
        String outputline = "HTTP/1.1 200 OK\r\n" //encabezado necesario
                    + "Content-Type:" + fileType + "\r\n"
                    + "\r\n"; //retorno de carro y salto de linea


        if (fileType.startsWith("image")){
            byte[] Arraybytes = Files.readAllBytes(file);
            outputStream.write(outputline.getBytes());
            outputStream.write(Arraybytes);
        }else{
            Charset charset = Charset.forName("UTF-8");
            BufferedReader reader = Files.newBufferedReader(file, charset);
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.print(line);
                outputline = outputline + line;
            }
            outputStream.write(outputline.getBytes());
        }


        return outputline;


    }

}
