package com.asmkt.sample.client;

import com.asmkt.sample.common.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "asmkt-klw", path = "test-klw")
public interface AsmktKlwClient {

    @GetMapping("/{id}")
    CommonResult selectGoodById(@PathVariable("id") Long id);

    @PostMapping("canStock/{goodId}/{number}")
    CommonResult updateGoodCanStock(@PathVariable("goodId") long goodId, @PathVariable("number") int number);

    @PostMapping("clientAccount/{goodId}/{number}")
    CommonResult updateClientAccountByGoodId(@PathVariable("goodId")Long goodId, @PathVariable("number") int number);
}
