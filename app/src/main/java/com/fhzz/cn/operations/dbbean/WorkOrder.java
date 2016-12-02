package com.fhzz.cn.operations.dbbean;

import android.os.Parcel;
import android.os.Parcelable;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Id;
import com.lidroid.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2016/11/28.
 */
@Table
public class WorkOrder implements Parcelable{

    @Id
    public int mobId;

    @Column(column = "solveState")
    public String solveState;

    @Column(column = "createTime")
    public String createTime;

    @Column(column = "reason")
    public String reason;

    @Column(column = "responsibilityType")
    public String responsibilityType;

    @Column(column = "creatorId")
    public String creatorId;

    @Column(column = "handleTime")
    public String handleTime;

    @Column(column = "handlerName")
    public String handlerName;

    @Column(column = "id")
    public String id;

    @Column(column = "serialNo")
    public String serialNo;

    @Column(column = "creatorName")
    public String creatorName;

    @Column(column = "handlerId")
    public String handlerId;

    @Column(column = "description")
    public String description;

    @Column(column = "finishState")
    public String finishState;

    @Column(column = "name")
    public String name;

    public WorkOrder(){

    }
    public WorkOrder (Parcel source) {
        mobId = source.readInt();
        solveState = source.readString();
        createTime = source.readString();
        reason = source.readString();
        responsibilityType = source.readString();
        creatorId = source.readString();
        handleTime = source.readString();
        handlerName = source.readString();
        id = source.readString();
        serialNo = source.readString();
        creatorName = source.readString();
        handlerId = source.readString();
        description = source.readString();
        finishState = source.readString();
        name = source.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mobId);
        parcel.writeString(solveState);
        parcel.writeString(createTime);
        parcel.writeString(reason);
        parcel.writeString(responsibilityType);
        parcel.writeString(creatorId);
        parcel.writeString(handleTime);
        parcel.writeString(handlerName);
        parcel.writeString(id);
        parcel.writeString(serialNo);
        parcel.writeString(creatorName);
        parcel.writeString(handlerId);
        parcel.writeString(description);
        parcel.writeString(finishState);
        parcel.writeString(name);
    }


    public static Parcelable.Creator<WorkOrder> CREATOR
            = new Parcelable.Creator<WorkOrder>()
    {
        @Override
        public WorkOrder createFromParcel(Parcel source) {
            return new WorkOrder(source);
        }
        @Override
        public WorkOrder[] newArray(int size) {
            return new WorkOrder[size];
        }

    };
}
