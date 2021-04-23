package com.heller.api.impl;

import com.heller.api.UploadCDN;

public class QiyiCDN implements UploadCDN {
    @Override
    public void upload(String uri) {
        System.out.println("上传到爱奇艺CDN，uri=" + uri);
    }
}
