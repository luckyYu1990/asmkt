package com.asmkt.klw.controller;

import com.asmkt.klw.bean.Good;
import com.asmkt.klw.service.KlwService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("")
@RestController
public class KlwController {

    @Autowired
    private KlwService klwService;

    @GetMapping("/{id}")
    public CommonResult queryGood(@PathVariable("id") Long id) {
        Good good = klwService.getGoodById(id);
        return CommonResult.SUCCESS(good);
    }

    @PostMapping("canStock/{goodId}/{number}")
    public CommonResult updateGoodCanStock(@PathVariable("goodId") long goodId,@PathVariable("number") int number) {
        klwService.updateGoodCanStock(goodId, number);
        return CommonResult.SUCCESS();
    }

    @PostMapping("clientAccount/{goodId}/{number}")
    CommonResult updateClientAccountByGoodId(@PathVariable("goodId")Long goodId, @PathVariable("number") int number) {
        klwService.updateClientAccountByGoodId(goodId, number);
        return CommonResult.SUCCESS();
    }
}
