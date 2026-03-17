package com.nandbox.bots.api.util;

import com.nandbox.bots.api.Nandbox;
import com.nandbox.bots.api.NandboxClient;
import com.nandbox.bots.api.outmessages.DeleteRecordOutMessage;
import com.nandbox.bots.api.outmessages.GetReordOutMessage;
import com.nandbox.bots.api.outmessages.ListRecordsOutMessage;
import com.nandbox.bots.api.outmessages.SetRecordOutMessage;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;



public class DatabaseService {

    private static DatabaseService instance;
    public static DatabaseService getInstance() {
        if (instance == null) {
            instance = new DatabaseService();
        }
        return instance;
    }


    /**
     * INSERT OR UPDATE
     */
    public void set(Nandbox.Api api, JSONObject object, String tableName, String id,String ref) {
        SetRecordOutMessage outMessage = new SetRecordOutMessage();
        outMessage.setTableName(tableName);
        outMessage.setId(id);
        outMessage.setDoc(object);
        outMessage.setRef(ref);
        api.send(outMessage);
    }

    /**
     * GET
     */
    public void get(Nandbox.Api api, String id, String tableName,String ref) {
        GetReordOutMessage outMessage = new GetReordOutMessage();
        outMessage.setTableName(tableName);
        outMessage.setId(id);
        outMessage.setRef(ref);
        api.send(outMessage);
    }

    /**
     * DELETE
     */
    public void delete(Nandbox.Api api , String id, String tableName,String ref) {
        DeleteRecordOutMessage outMessage = new DeleteRecordOutMessage();
        outMessage.setTableName(tableName);
        outMessage.setId(id);
        outMessage.setRef(ref);
        api.send(outMessage);
    }

    public void list(Nandbox.Api api, String tableName,String ref) {
        ListRecordsOutMessage outMessage = new ListRecordsOutMessage();
        outMessage.setTableName(tableName);
        outMessage.setRef(ref);
        api.send(outMessage);
    }
}