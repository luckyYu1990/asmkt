package com.asmkt.klw.service;

import com.asmkt.klw.bean.Activity;
import com.asmkt.klw.bean.Good;
import com.asmkt.klw.mapper.KlwActivityMapper;
import com.asmkt.klw.mapper.KlwClientAccountMapper;
import com.asmkt.klw.mapper.KlwActivityGoodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KlwService {
    @Autowired
    private KlwActivityGoodMapper klwActivityGoodMapper;
    @Autowired
    private KlwActivityMapper activityMapper;
    @Autowired
    private KlwClientAccountMapper clientAccountMapper;

    public Good getGoodById(Long id) {
        return klwActivityGoodMapper.selectById(id);
    }

    public void updateGoodCanStock(long goodId, int number) {
        klwActivityGoodMapper.updateGoodCanStock(goodId, number);
    }

    public void updateClientAccountByGoodId(Long goodId, int number) {
        Good good = getGoodById(goodId);
        if (good == null) {
            throw new RuntimeException("No good info");
        }
        Activity activity = activityMapper.selectById(good.getActivityId());
        if (activity == null) {
            throw new RuntimeException("No activity info");
        }
        clientAccountMapper.updateCurrentBalanceById(activity.getClientAccountId(), number);
    }
}
