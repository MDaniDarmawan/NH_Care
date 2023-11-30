package com.example.nh_care.database

object DbContract {

    const val ip = "192.168.1.15"

    const val urlRegister = "http://$ip/api-mysql-main/api-Nhcare.php?function=registerDonatur"
    const val urlLogin = "http://$ip/api-mysql-main/api-Nhcare.php?function=loginUser"
    const val urlDataDon = "http://$ip/api-mysql-main/api-Nhcare.php?function=getDataDonaturByEmail"
    const val urlDonasi = "http://$ip/api-mysql-main/api-Nhcare.php?function=insertDonasi"
    const val urlAnak = "http://$ip/api-mysql-main/api-Nhcare.php?function=getAnakAsuhData"
    const val urlProgram = "http://$ip/api-mysql-main/api-Nhcare.php?function=getProgramData"
    const val urlAcara = "http://$ip/api-mysql-main/api-Nhcare.php?function=getAcaraData"
    const val urlWebsite = "http://$ip/api-mysql-main/api-Nhcare.php?function=getWebsiteData"
    const val urlProfile = "http://$ip/api-mysql-main/api-Nhcare.php?function=updateDonatur"
    const val urlVideo = "http://$ip/api-mysql-main/api-Nhcare.php?function=getVideoData"
    const val urlHistori = "http://$ip/api-mysql-main/api-Nhcare.php?function=getDonasiHistory"
    const val urlTtlDonasi = "http://$ip/api-mysql-main/api-Nhcare.php?function=getTotalDonasi"
    const val urlTtlsantunan = "http://$ip/api-mysql-main/api-Nhcare.php?function=getDataSantunan"
    const val urlTtlPembangunan = "http://$ip/api-mysql-main/api-Nhcare.php?function=getDataPembangunan"
    const val urlFaq = "http://$ip/api-mysql-main/api-Nhcare.php?function=getFaqData"
}
