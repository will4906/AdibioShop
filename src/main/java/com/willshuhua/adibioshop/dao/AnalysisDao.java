package com.willshuhua.adibioshop.dao;

import com.willshuhua.adibioshop.entity.Customer;
import com.willshuhua.adibioshop.entity.analysis.Analysis;
import com.willshuhua.adibioshop.entity.order.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AnalysisDao {

    Analysis selectAnalysis(@Param("analysis_id")String analysisId);

    Analysis selectAnalysisByOrderInfoId(@Param("order_infoid")String orderInfoid);

    Customer selectCustomerByOrderInfoId(@Param("order_infoid")String orderInfoid);

    Order selectOrderByAnalysisId(@Param("analysis_id")String analysisId);
}
