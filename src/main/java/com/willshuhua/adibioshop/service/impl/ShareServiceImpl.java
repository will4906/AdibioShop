package com.willshuhua.adibioshop.service.impl;

import com.willshuhua.adibioshop.dao.ShareDao;
import com.willshuhua.adibioshop.entity.Share;
import com.willshuhua.adibioshop.service.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShareServiceImpl implements ShareService{

    @Autowired
    ShareDao shareDao;

    @Override
    public void createShare(Share share) {
        shareDao.createShare(share);
    }
}
