package com.heller.api.impl;

import com.heller.api.UploadCDN;

public class ChinaNetCDN implements UploadCDN {
    @Override
    public void upload(String uri) {
        System.out.println("上传到网宿CDN，uri=" + uri);
    }
}
