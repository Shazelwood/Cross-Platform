package com.hazelwood.androidparse;

import java.io.Serializable;

/**
 * Created by Hazelwood on 2/5/15.
 */
public class Record implements Serializable {

    public static final long serialVersionUID = 1234567890L;
    String recordTitle, recordDescription;
    boolean recordComplete;

    public Record(String _title, String _descr, boolean complete){
        recordTitle = _title;
        recordDescription = _descr;
        recordComplete = complete;
    }

    public void setRecordTitle(String recordTitle) {
        this.recordTitle = recordTitle;
    }

    public void setRecordComplete(boolean recordComplete) {
        this.recordComplete = recordComplete;
    }

    public void setRecordDescription(String recordDescription) {
        this.recordDescription = recordDescription;
    }

    public String getRecordDescription() {
        return recordDescription;
    }

    public String getRecordTitle() {
        return recordTitle;
    }
}
