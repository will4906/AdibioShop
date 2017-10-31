package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.Share;
import org.apache.ibatis.annotations.Param;

public interface ShareDao {

    void createShare(Share share);
}
