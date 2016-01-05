package com.example.nazli.imessaging;

import android.os.Message;
import android.util.JsonReader;
import android.util.JsonWriter;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import static com.example.nazli.imessaging.ChatActivity.fromServer;
import static com.example.nazli.imessaging.ChatActivity.jsonObject;
import static com.example.nazli.imessaging.ChatActivity.textView;

// created to check read and write
public class ClientMessageThread extends Thread {
    InputStream in;
    OutputStream out;
    BufferedReader reader;
    PrintWriter writer;
    JSONArray jsonArray;
    Socket socket;

    Message message;

    public ClientMessageThread(Socket socket, JSONArray jsonArray) throws IOException {
        // listen on connected socket port
        try {
            this.out = socket.getOutputStream();
            this.in = socket.getInputStream();
            this.jsonArray = jsonArray;
            this.socket = socket;
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            out = socket.getOutputStream();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // in this try block the content of readFromSocket is rolled out
            try {
                while (reader!=null){
                    reader.readLine();
//                    System.out.println(reader);
                    reader.close();
                }
//                reader.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
//                readFromSocket();
            // the content of writeOnSocket is rolled out
            try {
                fromServer += reader.readLine() + "message Sent";
                writer = new PrintWriter(new OutputStreamWriter(out, "UTF-8"));
                writer.write(jsonObject.toString());
                textView.append(writer.toString());
                writer.flush();
            }catch (IOException e){
                e.printStackTrace();
            }
//            writeOnSocket();
        }

    }

    public void readFromSocket() throws IOException {
//        System.out.println(writer.toString() + "\n");
    }

    public void readJSONParser() throws IOException{
        JsonReader jsonReader;
        try {
            jsonReader = new JsonReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
            jsonReader.beginArray();
            jsonReader.beginObject();
            while (jsonReader.hasNext()) {

            }
            message = new Message();
            message.obj.notifyAll();
            message.copyFrom(message);
            jsonReader.endObject();
            jsonReader.endArray();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void writeOnSocket() {
//        JsonWriter jsonWriter;
//        jsonWriter = new JsonWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));
//            jsonWriter.setIndent(" ");
//
//            jsonWriter.beginArray();
//            while (jsonArray!= null){
//                writeJSON(jsonWriter, jsonArray);
//            }
//            jsonWriter.endArray();

    }

    private void writeJSON(JsonWriter jsonWriter, JSONArray jsonArray) throws IOException {
        jsonWriter.beginArray();
        jsonWriter.beginObject();
//        jsonWriter.name("userID").value()
    }
}
