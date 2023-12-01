package com.example.nh_care.activity.lupasandi

import android.os.Parcel
import android.os.Parcelable

data class DataSandi(val nama: String?, val no_hp: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nama)
        parcel.writeString(no_hp)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DataSandi> {
        override fun createFromParcel(parcel: Parcel): DataSandi {
            return DataSandi(parcel)
        }

        override fun newArray(size: Int): Array<DataSandi?> {
            return arrayOfNulls(size)
        }
    }
}
